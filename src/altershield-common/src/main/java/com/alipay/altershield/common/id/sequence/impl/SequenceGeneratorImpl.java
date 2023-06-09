/*
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.alipay.altershield.common.id.sequence.impl;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.id.sequence.SequenceGenerator;
import com.alipay.altershield.common.id.sequence.dal.dataobject.SequenceDO;
import com.alipay.altershield.common.id.sequence.dal.mapper.SequenceMapper;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shuo.qius
 * @version May 25, 2018
 */
@Repository
public class SequenceGeneratorImpl implements SequenceGenerator, InitializingBean {
    private final Logger logger = Loggers.DAL;

    @Resource
    private TransactionTemplate confTransactionTemplate;
    @Autowired
    private SequenceMapper sequenceMapper;

    /**
     * @see InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (confTransactionTemplate == null) {
            throw new IllegalArgumentException("property 'confTransactionTemplate' is null");
        }
        if (sequenceMapper == null) {
            throw new IllegalArgumentException("property 'sequenceMapper' is null");
        }
    }

    private final Map<String, Sequencer> map = new HashMap<>();

    /**
     */
    public SequenceGeneratorImpl() {
        map.put(IdBizCodeEnum.DEFAULT.getSeqName(), new Sequencer(IdBizCodeEnum.DEFAULT.getSeqName()));
        AlterShieldLoggerManager.log("info", logger, null, "new SingleSequencer()", this.map);
    }

    /**
     * @see SequenceGenerator#getNextSeq(String)
     */
    @Override
    public String getNextSeq(String seqName) {
        long seqLong = nextValue(seqName);
        String seq = convert36(seqLong);
        return seq;
    }

    /**
     * @param seqName
     * @return
     */
    public long nextValue(String seqName) {
        Sequencer seqr = map.get(seqName);
        if (seqr == null) {
            seqr = map.get(IdBizCodeEnum.DEFAULT.getSeqName());
        }
        return seqr.getNext();
    }

    private Range applNewRange(final String seqName) {
        return confTransactionTemplate.execute(status -> {
            SequenceDO sequence = sequenceMapper.lockByName(seqName);
            if (sequence == null) {
                AlterShieldLoggerManager.log("error", logger, null, "SingleSequencer.applyNewRange",
                        "no sequenceDO found " + seqName);
            }
            long[] calc = calculate(sequence);

            sequenceMapper.update(seqName, calc[2]);
            return new Range(calc[0], calc[1]);
        });
    }

    /**
     *
     * @param sequence
     * @return [0]:from; [1]:to; [2]:value
     */
    private static long[] calculate(SequenceDO sequence) {
        final long min = sequence.getMinValue();
        final long max = sequence.getMaxValue();
        long value = sequence.getValue();

        long from = value;
        long to = from + sequence.getStep() - 1;
        if (to > max) {
            to = max;
        }
        value = to + 1;
        if (value > max) {
            value = min;
        }
        return new long[] {from, to, value};
    }

    public static String convert36(long num) {
        for (; num < 0; ) {
            num += AlterShieldConstant.CONST_SEQ_MAX_VALUE;
        }
        return CommonUtil.convertTo36FixLength(num, 8);
    }

    private class Sequencer {
        private final    String name;
        private volatile Range  range;

        public Sequencer(String name) {
            super();
            this.name = name;
            this.range = null;
        }

        public long getNext() {
            Long val = null;
            for (; val == null; ) {
                final Range range = this.range;
                val = range == null ? null : range.nextValue();
                if (val == null) {
                    synchronized (this) {
                        if (this.range == range) {
                            this.range = applNewRange(name);
                        }
                    }
                }
            }
            return val.longValue();
        }

    }

    private static class Range {
        /** inclusive */
        private final long max;
        private       long lastValue;

        public Range(long min, long max) {
            super();
            this.max = max;
            this.lastValue = min - 1;
        }

        private synchronized Long nextValue() {
            if (lastValue >= max) {
                return null;
            }
            return new Long(++lastValue);
        }

    }

    // --------------------------------setter--------------------------------

    /**
     * @param confTransactionTemplate value to be assigned to property {@link #confTransactionTemplate}
     */
    public void setConfTransactionTemplate(TransactionTemplate confTransactionTemplate) {
        this.confTransactionTemplate = confTransactionTemplate;
    }

    /**
     * @param sequenceMapper value to be assigned to property {@link #sequenceMapper}
     */
    public void setSequenceMapper(SequenceMapper sequenceMapper) {
        this.sequenceMapper = sequenceMapper;
    }

}

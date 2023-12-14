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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.common.largefield.kv;

import com.alipay.altershield.common.constant.AlterShieldConstant;
import com.alipay.altershield.common.id.IdGenerator;
import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.common.largefield.dal.dataobject.AlterShieldKeyValueDO;
import com.alipay.altershield.framework.common.util.BytesCodecUtil;
import com.alipay.altershield.framework.common.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

/**
 * @author shuo.qius
 * @version Jun 4, 2018
 */

public class KeyValueStorageImpl implements KeyValueStorage, InitializingBean {

    private KeyValueDAO           keyValueDAO;
    @Autowired
    private IdGenerator idGenerator;

    private TransactionTemplate    transactionTemplate;

    /**
     * @see InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() {
        if (keyValueDAO == null) {
            throw new IllegalArgumentException("property 'opsCloudKeyValueMapper' is null");
        }
        if (idGenerator == null) {
            throw new IllegalArgumentException("property 'idGenerator' is null");
        }
        if (transactionTemplate == null) {
            throw new IllegalArgumentException("property 'transactionTemplate' is null");
        }
    }

    /**
     * @see KeyValueStorage#getValueBase64(String)
     */
    @Override
    public byte[] getValueBase64(String key) {
        String val = getValue(key);
        return BytesCodecUtil.base64Decode(val);
    }

    /**
     * @see KeyValueStorage#putBase64Value(String, byte[], String)
     */
    @Override
    public String putBase64Value(String oldKey, byte[] value, String relatedId) {
        String val = BytesCodecUtil.base64Encode(value);
        return putValue(oldKey, val, relatedId);
    }

    /**
     * @see KeyValueStorage#getValue(String)
     */
    @Override
    public String getValue(String key) {
        if (CommonUtil.isBlank(key)) {
            return null;
        }
        List<AlterShieldKeyValueDO> list = keyValueDAO.selectByName(key);
        if (list == null || list.isEmpty()) {
            return null;
        }
        boolean nonNull = false;
        int size = 0;
        for (AlterShieldKeyValueDO d : list) {
            if (d != null && d.getValue() != null) {
                size += d.getValue().length();
                nonNull = true;
            }
        }
        if (!nonNull) {
            return null;
        }
        if (size <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(size);
        list.stream().filter(d -> d != null && d.getValue() != null)
            .sorted(Comparator.comparing(AlterShieldKeyValueDO::getSerialNum, Comparator.naturalOrder()))
            .forEach(d -> sb.append(d.getValue()));
        return sb.toString();
    }

    /**
     * @see KeyValueStorage#putValue(String, String, String)
     */
    @Override
    public String putValue(final String oldKey, final String value, String orderId) {
        if (CommonUtil.isNotBlank(oldKey)) {
            if (value == null) {
                removeValue(oldKey);
                return null;
            }
            put(oldKey, value, false);
            return oldKey;
        }

        if (value == null) {
            return null;
        }
        String key = idGenerator.generateIdByRelatedId(IdBizCodeEnum.OPSCLD_KEY_VALUE, orderId);
        put(key, value, true);
        return key;
    }

    private void put(String key, String value, final boolean newOne) {

        List<AlterShieldKeyValueDO> list;
        if (value == null || CommonUtil.isBlank(value)) {
            list = constructKVDOs(key, value == null ? null : Collections.emptyList());
        } else {
            int subSize = AlterShieldConstant.DAL_KV_VALUE_COLUMN_SIZE;
            int splitSize = (value.length() / subSize) + 1;
            List<String> values = new ArrayList<>(splitSize);
            for (int i = 0; i < splitSize; ++i) {
                String sub = StringUtils.substring(value, i * subSize, i * subSize + subSize);
                values.add(sub);
            }
            list = constructKVDOs(key, values);
        }

        transactionTemplate.execute((TransactionCallback<Void>) status -> {
            if (!newOne) {
                removeValue(key);
            }
            for (AlterShieldKeyValueDO d : list) {
                keyValueDAO.insert(d);
            }
            return null;
        });

    }

    /**
     * @param key
     * @param values might be null or empty
     * @return never null or empty
     */
    private List<AlterShieldKeyValueDO> constructKVDOs(String key, List<String> values) {
        if (values == null || values.isEmpty()) {
            List<AlterShieldKeyValueDO> list = new ArrayList<>(1);
            AlterShieldKeyValueDO d = new AlterShieldKeyValueDO();
            list.add(d);
            d.setGmtCreate(new Date());
            d.setGmtModified(new Date());
            d.setName(key);
            d.setSerialNum(1);
            d.setValue(values == null ? null : "");
            return list;
        }
        List<AlterShieldKeyValueDO> list = new ArrayList<>(values.size());
        for (int i = 0; i < values.size(); ++i) {
            AlterShieldKeyValueDO d = new AlterShieldKeyValueDO();
            list.add(d);
            d.setGmtCreate(new Date());
            d.setGmtModified(new Date());
            d.setName(key);
            d.setSerialNum(i + 1);
            d.setValue(values.get(i));
        }
        return list;
    }

    /**
     * @see KeyValueStorage#removeValue(String)
     */
    @Override
    public void removeValue(String key) {
        keyValueDAO.deleteByName(key);
    }

    public KeyValueDAO getKeyValueDAO() {
        return keyValueDAO;
    }

    public void setKeyValueDAO(KeyValueDAO keyValueDAO) {
        this.keyValueDAO = keyValueDAO;
    }

    public void setIdGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}

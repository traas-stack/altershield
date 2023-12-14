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
package com.alipay.altershield.framework.core.util;


import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 专门用于产生本地假货Id的各种方法。
 *
 * @author liushengdong
 * @version $Id: UUIDUtil.java, v 0.1 2018年12月13日 9:31 PM liushengdong Exp $
 */
public class UUIDUtil {
    /** 随机数生成器器 */
    public static final Random RANDOM = new Random();

    /***/
    public static final int ID_LEN = 36;
    /***/
    public static final int OFF_BIZ_ID_START = 13;
    /***/
    public static final int OFF_EXT_START = OFF_BIZ_ID_START + 3;
    /***/
    public static final int OFF_EXT_END = OFF_EXT_START + 8;
    public static final int RANDOM_TWO_START = OFF_EXT_END + 2;
    public static final int RANDOM_TWO_END = RANDOM_TWO_START + 2;
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>();
    private static final long CONST_SEQ_MAX_VALUE = 2821109907455L;// 36^8 = 2821109907456 因此
                                                                   // num的范围是[0,2821109907455]
    public static final String CLIENT_GENERATED = "AT";

    private static class Holder {
        static final SecureRandom numberGenerator = new SecureRandom();
    }

    /**
     * @return "yyyyMMdd"
     */
    private static String getDate() {
        DateFormat d = df.get();
        if (d == null) {
            d = new SimpleDateFormat("yyyyMMdd");
            df.set(d);
        }
        return d.format(new Date());
    }

    /**
     *
     * @param id
     * @return
     */
    public static String getEntranceCodeById(String id) {
        if (CommonUtil.isBlank(id)) {
            return "";
        }
        return getExtString(id).substring(0, 3);
    }

    /**
     *
     * @param id
     * @return
     */
    public static boolean isFakeId(String id) {
        if (CommonUtil.isBlank(id)) {
            return false;
        }
        if (id.length() != ID_LEN) {
            return false;
        }
        // 20190427 00 245 XXX ZZZXX0ZX 32XX 0009NEMS
        return CommonUtil.FAKE_ID_BIZ_CODE
                .equalsIgnoreCase(id.substring(OFF_BIZ_ID_START, OFF_EXT_START))
                && CommonUtil.getAlterShieldIdDefaultExtString().equalsIgnoreCase(getExtString(id));
    }

    /**
     *
     * @return
     */
    public static String genFakeId() {
        Random r = new Random();
        String uid = CommonUtil.alignRight(String.valueOf(r.nextInt(100)), 2, "0");
        final String date = getDate();// 8
        final String dataSystemVersion = "00";// 2
        final String sysCode = "245";// 3
        final String bizCd = CommonUtil.FAKE_ID_BIZ_CODE;// 3
        final String extCode = CommonUtil.getAlterShieldIdDefaultExtString();
        final String sharding = uid;// 2
        final String random = "XX";// 2
        final String seq = convert36(r.nextLong());// 8
        return new StringBuilder(ID_LEN).append(date).append(dataSystemVersion).append(sysCode)
                .append(bizCd).append(extCode).append(sharding).append(random).append(seq)
                .toString();
    }

    /**
     * 是否因为打开了异步本地开关后Client端自己产生的Id。(注意这种Id是正式使用的，并不是FakeId)
     *
     * @param id
     * @return
     */
    public static boolean isClientGenerated(String id) {
        if (CommonUtil.isBlank(id)) {
            return false;
        }
        if (id.length() != ID_LEN) {
            return false;
        }
        // 20190427 00 245 XXX ZZZXX0ZX 32XX 0009NEMS
        return CLIENT_GENERATED.equalsIgnoreCase(id.substring(RANDOM_TWO_START, RANDOM_TWO_END));
    }



    private static String convert36(long num) {
        num = num % CONST_SEQ_MAX_VALUE + 1;// 36^8 = 2821109907456 因此 num 的范围是 [0,2821109907455] 或者
                                            // [-2821109907455,0]
        for (; num < 0;) {
            num += CONST_SEQ_MAX_VALUE;
        }
        char[] seq = new char[8];
        for (int i = 0; i < 8; ++i) {
            char c = d2c((int) (num % 36));
            seq[8 - 1 - i] = c;
            num /= 36;
        }
        return new String(seq);
    }

    /**
     * @param i 0~35
     * @return
     */
    private static char d2c(int i) {
        if (i < 10) {
            return (char) ('0' + i);
        } else {
            return (char) ('A' + (i - 10));
        }
    }


    /**
     * 获取id中的扩展字段
     *
     * @param id
     * @return
     */
    public static String getExtString(String id) {
        if (id == null) {
            return null;
        }
        return id.substring(OFF_EXT_START, OFF_EXT_END);
    }

    /**
     * 判断是否一个正确的Id，仅判断长度合适
     *
     * @param id
     * @return
     */
    public static boolean isValidId(String id) {
        return CommonUtil.isNotBlank(id) && id.length() == ID_LEN;
    }

    /**
     * 判断这个Id所代表的变更是不是应急操作。
     *
     * @param id
     * @return
     */
    public static boolean isEmergency(String id) {
        return ChangeScenarioEnum.EMERGE.equals(getChangeScenarioEnum(id));
    }

    /**
     * 获得Id的场景码。
     *
     * @param id
     * @return
     */
    public static ChangeScenarioEnum getChangeScenarioEnum(String id) {
        ChangeScenarioEnum cs = null;
        if (isValidId(id)) {
            String extString = getExtString(id);
            String csCode = extString.substring(3, 5);
            cs = ChangeScenarioEnum.getByCode(csCode);
        }
        if (cs == null) {
            cs = ChangeScenarioEnum.DAILY;
        }
        return cs;
    }

    public static String randomDigit36(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; ++i) {
            sb.append(randChar36());
        }
        return sb.toString();
    }

    private static char randChar36() {
        SecureRandom ng = Holder.numberGenerator;
        int i = ng.nextInt(36);
        if (i < 10) {
            return (char) (i + '0');
        }
        return (char) (i - 10 + 'A');
    }

}

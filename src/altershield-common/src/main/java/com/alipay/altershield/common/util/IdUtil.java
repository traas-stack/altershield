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
package com.alipay.altershield.common.util;

import com.alipay.altershield.common.id.enums.IdBizCodeEnum;
import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.core.change.model.enums.ChangeScenarioEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * timestamp(8)  v(2)  sys(3) biz(3)      ext(8)         uid(2)  remain(3)   seq(8)
 * |_ _ _ _ _ _ _| _ _ |_ _ _ | _ _ _| _ _ _ _ _ _ _ _ _ _|_ _ | _ _ _ | _ _ _ _ _ _ _ _ _ _|
 *
 * @author shuo.qius
 * @version May 25, 2018
 */
public class IdUtil {
    private static final ThreadLocal<DateFormat> df               = new ThreadLocal<>();
    // opscloud: 247
    public static final  String                  SYS_CODE         = CommonUtil.SYS_CODE;
    public static final  String                  NO_SHARDING_CODE = "00";
    public static final  int                     ID_LEN           = 36;

    public static final int OFF_TIME_START = 0;
    public static final int OFF_TIME_LEN   = 8;
    public static final int OFF_TIME_END   = OFF_TIME_START + OFF_TIME_LEN;

    public static final int OFF_VERSION_START = OFF_TIME_END;
    public static final int OFF_VERSION_LEN   = getDataSystemVersion().length();
    public static final int OFF_VERSION_END   = OFF_VERSION_START + OFF_VERSION_LEN;

    public static final int OFF_SYS_CODE_START = OFF_VERSION_END;
    public static final int OFF_SYS_CODE_LEN   = SYS_CODE.length();
    public static final int OFF_SYS_CODE_END   = OFF_SYS_CODE_START + OFF_SYS_CODE_LEN;

    public static final int OFF_BIZ_CODE_START = OFF_SYS_CODE_END;
    public static final int OFF_BIZ_CODE_LE    = 3;
    public static final int OFF_BIZ_CODE_END   = OFF_BIZ_CODE_START + OFF_BIZ_CODE_LE;

    public static final int OFF_EXT_START = OFF_BIZ_CODE_END;
    public static final int OFF_EXT_LEN   = 8;
    public static final int OFF_EXT_END   = OFF_EXT_START + OFF_EXT_LEN;

    public static final int OFF_UID_START = OFF_EXT_END;
    public static final int OFF_UID_LEN   = 2;
    public static final int OFF_UID_END   = OFF_UID_START + OFF_UID_LEN;

    public static final int OFF_RANDOM_START = OFF_UID_END;
    public static final int OFF_RANDOM_LEN   = 2;
    public static final int OFF_RANDOM_END   = OFF_RANDOM_START + OFF_RANDOM_LEN;

    public static final int OFF_SEQ_START = OFF_RANDOM_END;
    public static final int OFF_SEQ_LEN   = 8;
    public static final int OFF_SEQ_END   = OFF_SEQ_START + OFF_SEQ_LEN;

    private final static Random random = new Random();

    /**
     * @param id
     * @return true:是一个合法的ID
     */
    public static boolean isValidId(String id) {
        return CommonUtil.isNotBlank(id) && id.length() == ID_LEN;
    }

    /**
     * @param id
     * @return
     */
    public static IdBizCodeEnum getBizCode(String id) {
        if (CommonUtil.isBlank(id)) {
            return null;
        }
        String code = id.substring(OFF_BIZ_CODE_START, OFF_BIZ_CODE_END);
        return IdBizCodeEnum.getByCode(code);
    }

    /**
     * 获取ID里面的系统代码。
     *
     * @param id
     * @return
     */
    public static String getSysCode(String id) {
        if (CommonUtil.isBlank(id) || id.length() < ID_LEN) {
            return null;
        }
        return id.substring(OFF_SYS_CODE_START, OFF_BIZ_CODE_START);
    }

    /**
     * @param anyString
     * @return
     */
    public static String hashUid(String anyString) {
        int hash = 0;
        if (anyString != null) {
            hash = anyString.hashCode();
            if (hash < 0) {
                hash = -hash;
            }
            hash = hash % 100;
        }
        if (hash < 10) {
            return new StringBuilder(2).append('0').append(hash).toString();
        }
        return String.valueOf(hash);
    }

    /**
     * @param standardId
     * @return
     */
    private static String replaceBizCode(String standardId, IdBizCodeEnum bizCode) {
        checkStandardId(standardId);
        Assert.notNull(bizCode, "bizCode is null");
        String pre = standardId.substring(0, OFF_BIZ_CODE_START);
        //扩展字段不用增加一个新的标识,因为所有表都是使用的同一个sequnce生成,所以不会出现串号
        String post = standardId.substring(OFF_EXT_START);
        return new StringBuilder(IdUtil.ID_LEN).append(pre).append(bizCode.getCode()).append(post)
                .toString();
    }

    /**
     * Check whether the standard id is inside simulator environment.
     *
     * @param standardId
     * @return
     */
    //public static boolean isSimId(String standardId) {
    //    return StringUtil.equals("SM", getRandomId(standardId));
    //}

    /**
     * @return
     */
    public static String getNoSharding() {
        return NO_SHARDING_CODE;
    }

    /**
     * @return
     */
    public static String getDefaultExtString() {
        return CommonUtil.getAlterShieldIdDefaultExtString();
    }

    /**
     * 把场景码嵌入到扩展参数中 sc    node | 0  0 | 0 0 | 0 0  | 0  0 |
     *
     * @param changeScenarioEnum
     */
    public static String getExtString(ChangeScenarioEnum changeScenarioEnum) {
        changeScenarioEnum = changeScenarioEnum == null ? ChangeScenarioEnum.DAILY : changeScenarioEnum;
        int len = changeScenarioEnum.getCode().length();

        if (len > IdUtil.OFF_EXT_LEN) {
            throw new IllegalArgumentException(
                    "illegal biz ext code. total ext string'lenght must below " + IdUtil.OFF_EXT_LEN + ",changeScenarioEnum=" + changeScenarioEnum);
        }
        String extString = changeScenarioEnum.getCode();
        return String.format("%" + IdUtil.OFF_EXT_LEN + "s", extString).replace(" ", "0");
    }

    /**
     * 获取id中的扩展字段
     *
     * @param id
     * @return
     */
    public static String getExtString(String id) {
        return StringUtils.substring(id, OFF_EXT_START, OFF_EXT_END);
    }

    /**
     * @param standardId
     * @return 2-digits
     */
    public static String getUid(String standardId) {
        checkStandardId(standardId);
        return StringUtils.substring(standardId, OFF_UID_START, OFF_UID_END);
    }

    /**
     * @param standardId
     * @return 2-digits
     */
    public static String getRandomId(String standardId) {
        checkStandardId(standardId);
        return StringUtils.substring(standardId, OFF_RANDOM_START, OFF_RANDOM_END);
    }

    /**
     * @return 返回随机的uid(2位)
     */
    public static String getRandomUid() {
        int i = random.nextInt(100);
        return CommonUtil.alignRight(String.valueOf(i), 2, "0");
    }

    /**
     * 判断这个Id所代表的变更是不是应急操作。
     *
     * @param id
     * @return
     */
    public static boolean isEmergency(String id) {
        return isValidId(id) && ChangeScenarioEnum.EMERGE.equals(getChangeScenarioEnum(id));
    }

    public static ChangeScenarioEnum getChangeScenarioEnum(String id) {
        ChangeScenarioEnum cs = null;
        if (CommonUtil.isNotBlank(id)) {
            String ext = StringUtils.right(getExtString(id), 4);
            String csCode = StringUtils.left(ext, 2);
            cs = ChangeScenarioEnum.getByCode(csCode);
        }
        if (cs == null) {
            //输出传递错误ChangeScenarioEnum的位置
            String msg = String.format("%s -> ChangeScenarioEnum#getByCode -> null", id);
            throw new RuntimeException(msg);
        }
        return cs;
    }



    /**
     * @return
     */
    public static String getDataSystemVersion() {
        return CommonUtil.getDefaultDataSystemVersion();
    }

    /**
     * @return "yyyyMMdd"
     */
    public static String getDate() {
        DateFormat d = df.get();
        if (d == null) {
            d = new SimpleDateFormat("yyyyMMdd");
            df.set(d);
        }
        return d.format(new Date());
    }

    /**
     * use constant instead of random to improve MySQL's insert performance
     *
     * @return
     */
    public static String getRandomSharding() {
        return CommonUtil.getDefaultIdRandomString();
    }

    public static void checkStandardId(String standardId) {
        if (CommonUtil.isBlank(standardId) || standardId.length() < 28) {
            throw new IllegalArgumentException("standardId not valid: " + standardId);
        }
    }

    /**
     * DO NOT INVOKE ME! just for PMD check
     */
    public void resetDf() {
        df.remove();
    }


}

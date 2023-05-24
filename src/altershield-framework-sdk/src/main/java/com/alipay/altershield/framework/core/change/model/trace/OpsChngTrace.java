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
 * MIT License
 *
 * Copyright (c) [2023]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co.,
 * Ltd. This software and all the relevant information, including but not limited to any signs,
 * images, photographs, animations, text, interface design, audios and videos, and printed
 * materials, are protected by copyright laws and other intellectual property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation
 * License Agreement/Software Use Agreement updated from time to time. Without authorization from
 * Ant Small and Micro Financial Services Group Co., Ltd., no one may conduct the following actions:
 *
 * 1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 * 2) reverse engineer, decompile the source code of this software or try to find the source code in
 * any other ways;
 *
 * 3) modify, translate and adapt this software, or develop derivative products, works, and services
 * based on this software;
 *
 * 4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this
 * software, or authorize the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.framework.core.change.model.trace;


import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.core.util.OpsChngTraceCommonUtil;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * immutable object
 *
 * @author shuo.qius
 * @author liushendong
 * @version May 14, 2018
 */
public class OpsChngTrace implements Serializable {


    private static final String MAGIC_NULL = "NIL";
    private static final long serialVersionUID = -218327696051611820L;

    /** generated at root change service */
    @NotNull
    private String traceId;
    /** coordinate of a service/node execution within {@link #traceId} 根coord是 [|0] */
    private OpsChngCoord coord;

    /**
     * Constructor.
     */
    public OpsChngTrace() {}

    /**
     * @param traceId
     * @param coord
     */
    public OpsChngTrace(String traceId, String coord) {
        this(traceId, new OpsChngCoord(coord));
    }

    /**
     * @param traceId
     * @param coord
     */
    public OpsChngTrace(String traceId, OpsChngCoord coord) {
        this.traceId = traceId;
        this.coord = coord;
    }

    /**
     * @see Object#clone()
     */
    @Override
    public OpsChngTrace clone() {
        OpsChngTrace that = new OpsChngTrace();
        that.traceId = this.traceId;
        if (this.coord == null) {
            that.coord = null;
        } else {
            that.coord = this.coord.clone();
        }
        return that;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return traceId + "-" + coord;
    }

    private String compressTraceId() {
        if (traceId == null || traceId.length() < 28) {
            return null;
        }
        // 4-digit
        String date = OpsChngTraceCommonUtil.compressDate(traceId.substring(0, 8));
        // 2-digit
        String version = traceId.substring(8, 8 + 2);
        // rest
        String sub = traceId.substring(16);
        // 20190324 00 245001 8110000099XX0006EHH6
        return new StringBuilder(4 + 2 + 20).append(date).append(version).append(sub).toString();
    }

    private static String deCompressTraceId(String traceIdComp) {
        if (CommonUtil.isBlank(traceIdComp)) {
            return null;
        }
        String yyyyMMdd = OpsChngTraceCommonUtil.deCompressDate(traceIdComp.substring(0, 4));
        String version = traceIdComp.substring(4, 4 + 2);
        String sub = traceIdComp.substring(6);
        return new StringBuilder(36).append(yyyyMMdd).append(version).append("245001").append(sub)
                .toString();
    }

    /**
     * compress into sofa-trace
     *
     * @return
     */
    public String compress() {
        StringBuilder stringBuilder = new StringBuilder();

        // traceId
        String traceIdPart = compressTraceId();
        if (traceIdPart == null) {
            return null;
        }
        stringBuilder.append(traceIdPart);

        // split
        stringBuilder.append("-");

        // coord
        String coordPart = MAGIC_NULL;
        if (coord != null && coord.getCoord() != null) {
            coordPart = coord.getCoord();
        }
        stringBuilder.append(coordPart);

        return stringBuilder.toString();
    }

    /**
     * @param compressedStr
     * @return
     */
    public static OpsChngTrace deCompress(String compressedStr) {
        if (CommonUtil.isBlank(compressedStr)) {
            return null;
        }
        List<String> list = CommonUtil.splitAsList(compressedStr, "-", true);
        if (list.isEmpty()) {
            return null;
        }

        final String traceId = deCompressTraceId(list.get(0));
        if (traceId == null) {
            return null;
        }
        final String coord = list.size() > 1 ? list.get(1) : "";
        if (MAGIC_NULL.equalsIgnoreCase(coord)) {
            return new OpsChngTrace(traceId, (String) null);
        }
        return new OpsChngTrace(traceId, coord);
    }

    /**
     * 序列化
     *
     * @return
     */
    public static String encode(OpsChngTrace opsChngTrace) {
        if (opsChngTrace == null) {
            return null;
        }
        String traceId = opsChngTrace.getTraceId();
        OpsChngCoord coordObj = opsChngTrace.getCoord();

        // 所有字段如果为null,返回""
        traceId = traceId == null ? "" : traceId;
        String coord =
                coordObj == null ? "" : (coordObj.getCoord() == null ? "" : coordObj.getCoord());

        return String.format("%s-%s-%s", traceId, coord);
    }

    /**
     * 反序列化 使用{@link #encode(OpsChngTrace)} 的str来反序列化
     *
     * @param str
     * @return
     */
    public static OpsChngTrace decode(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        do {
            int i = str.indexOf("-");
            if (i >= 0) {
                String txt = str.substring(0, i);
                list.add(txt);
                if (i + 1 >= str.length()) {
                    break;
                }
                str = str.substring(i + 1, str.length());
            } else {
                list.add(str);
                break;
            }
        } while (true);

        String traceId = "";
        String coord = "";

        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                traceId = list.get(0);
            } else if (i == 1) {
                coord = list.get(1);
            }
        }

        return new OpsChngTrace(traceId, coord);
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coord == null) ? 0 : coord.hashCode());
        result = prime * result + ((traceId == null) ? 0 : traceId.hashCode());
        return result;
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OpsChngTrace other = (OpsChngTrace) obj;
        if (coord == null) {
            if (other.coord != null)
                return false;
        } else if (!coord.equals(other.coord))
            return false;
        if (traceId == null) {
            if (other.traceId != null)
                return false;
        } else if (!traceId.equals(other.traceId))
            return false;
        return true;
    }

    /**
     * @param traceId value to be assigned to property {@link #traceId}
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * @param coord value to be assigned to property {@link #coord}
     */
    public void setCoord(OpsChngCoord coord) {
        this.coord = coord;
    }


    /**
     * @return property value of {@link #traceId}
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * @return property value of {@link #coord}
     */
    public OpsChngCoord getCoord() {
        return coord;
    }

    /**
     * @return
     */
    public String getCoordStr() {
        return this.coord == null ? "" : this.coord.getCoord();
    }

}

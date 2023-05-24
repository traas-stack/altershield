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

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class OpsChngCoord implements Serializable {

    private static final long serialVersionUID = -8625195825688420950L;
    public static final char SUFFIX_NODE = ':';
    public static final char SUFFIX_SERVICE = '|';
    public static final char SUFFIX_BETWEEN = '.';

    private static Set<Character> splitCharSet = new HashSet<Character>();
    static {
        splitCharSet.add(SUFFIX_NODE);
        splitCharSet.add(SUFFIX_SERVICE);
        splitCharSet.add(SUFFIX_BETWEEN);
    }

    /**
     * @param splitChar
     * @return
     */
    public static boolean isSplitChar(char splitChar) {
        return splitCharSet.contains(splitChar);
    }

    /**
     * e.g. <code>task1.node1:gray1.retry2|task4.node1:rev</code>
     */
    private String coord;

    public OpsChngCoord() {}

    /**
     * @param coord {@link #coord}
     */
    public OpsChngCoord(String coord) {
        this();
        this.coord = coord;
    }

    /**
     * @see Object#clone()
     */
    @Override
    public OpsChngCoord clone() {
        OpsChngCoord that = new OpsChngCoord();
        that.coord = this.coord;
        return that;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return coord;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coord == null) ? 0 : coord.hashCode());
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
        OpsChngCoord other = (OpsChngCoord) obj;
        if (coord == null) {
            if (other.coord != null)
                return false;
        } else if (!coord.equals(other.coord))
            return false;
        return true;
    }
}

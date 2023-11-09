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
package com.alipay.altershield.common.util;


import com.alipay.altershield.framework.core.change.model.trace.OpsChngCoord;
import com.alipay.altershield.framework.core.change.model.trace.OpsChngTrace;
import org.apache.commons.lang3.StringUtils;

/**
 * @author shuo.qius
 * @version Jun 8, 2018
 */
public class TraceUtil {

    /**
     * @param trace
     * @return
     */
    public static String composeTraceCoord2PbTraceId(OpsChngTrace trace) {
        if (trace == null) {
            return "";
        }
        String traceId = trace.getTraceId();
        return traceId + "-" + trace.getCoord().getCoord();
    }

    /**
     *
     * @param trace
     * @return OpsChngTrace
     */
    public static OpsChngTrace composePbTraceId2TraceCoord(String trace) {
        if (CommonUtil.isNotBlank(trace)) {
            String[] strs = StringUtils.split(trace, "-");
            switch (strs.length) {
                case 1:
                    return new OpsChngTrace(strs[0], "");
                case 2:
                    return new OpsChngTrace(strs[0], strs[1]);
                default:
                    throw new IllegalArgumentException("Invalid trace format: " + trace);
            }
        } else {
            return null;
        }
    }

    /**
     * @param myCoord
     * @return
     */
    public static String getLastSuffixFromNodeOrService(String myCoord) {
        if (CommonUtil.isBlank(myCoord)) {
            return "";
        }
        for (int i = myCoord.length() - 1; i >= 0; --i) {
            char c = myCoord.charAt(i);
            switch (c) {
                case OpsChngCoord.SUFFIX_NODE:
                case OpsChngCoord.SUFFIX_SERVICE:
                    return myCoord.substring(i + 1);
                default:
                    break;
            }
        }
        return "";
    }

    /**
     * anthor是child的祖先,或者就是自己
     * 例如:
     * child: "|0:0.0.2" , anthor: "|0"  return true
     * child: "|0:0.0|2" , anthor: "|0:0.0"  return true
     * child: "|0:0.0.2" , anthor: "|0:0.0"  return true
     * child: "|0:0.0.2" , anthor: "|0:0.0.2"  return true
     * child: "|0:0.0.2" , anthor: "|0:0.0.21"  return false
     * child: "|0:0.0.2" , anthor: "|0:1.0"  return false
     * child: "|0:0.0.2" , anthor: ""  return false
     * child: "" , anthor: "|0"  return false
     * @param child
     * @param anchor
     * @return
     */
    public static boolean isAncestor(String child, String anchor) {
        if (CommonUtil.isBlank(child) || CommonUtil.isBlank(anchor)) {
            return false;
        }

        if (child.length() < anchor.length()) {
            return false;
        }

        //不是以
        if (!child.startsWith(anchor)) {
            return false;
        }

        if (child.length() == anchor.length()) {
            return true;
        }

        String firstChar = child.substring(anchor.length(), anchor.length() + 1);

        return OpsChngCoord.isSplitChar(firstChar.charAt(0));
    }

    /**
     * @param myCoord
     * @return "" for root
     */
    public static String getParentNodeOrServiceCoord(String myCoord) {
        if (CommonUtil.isBlank(myCoord)) {
            return "";
        }
        for (int i = myCoord.length() - 1; i >= 0; --i) {
            char c = myCoord.charAt(i);
            switch (c) {
                case OpsChngCoord.SUFFIX_NODE:
                case OpsChngCoord.SUFFIX_SERVICE:
                    return myCoord.substring(0, i);
                default:
                    break;
            }
        }
        return "";
    }

    /**
     * @param originSrv
     * @return
     */
    public static OpsChngTrace genTraceRbService(OpsChngTrace originSrv) {
        OpsChngCoord coord = originSrv.getCoord();
        String myCoord = composeCoord(coord.getCoord(), "R", OpsChngCoord.SUFFIX_BETWEEN);
        return new OpsChngTrace(originSrv.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @see #genTraceRbService(OpsChngTrace)
     *
     * @param coord
     * @return
     */
    public static boolean isRollbackTrace(String coord) {
        if (CommonUtil.isBlank(coord)) {
            return false;
        }
        if (coord.contains(OpsChngCoord.SUFFIX_BETWEEN + "R")) {
            return true;
        }
        return false;
    }

    /**
     * @param upperTier
     * @param innerTier
     * @return
     */
    public static OpsChngTrace genTraceService(OpsChngTrace upperTier, Object innerTier) {
        String innerTierStr = innerTier == null ? "" : String.valueOf(innerTier);
        String parentCoord = upperTier.getCoord() == null ? "" : upperTier.getCoord().getCoord();
        String myCoord = composeCoord(parentCoord, innerTierStr, OpsChngCoord.SUFFIX_BETWEEN);
        return new OpsChngTrace(upperTier.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @param parentTrace
     * @return
     */
    public static OpsChngTrace composeStackTrace(OpsChngTrace parentTrace, int idx) {
        String parentCoord = parentTrace.getCoord() == null ? "" : parentTrace.getCoord().getCoord();
        String myCoord = composeCoord(parentCoord, idx + "-2p", OpsChngCoord.SUFFIX_BETWEEN);
        return new OpsChngTrace(parentTrace.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @param parentNode
     * @param upperTier
     * @param myTag
     * @return
     */
    public static OpsChngTrace genTraceTier(OpsChngTrace parentNode, OpsChngTrace upperTier, Object myTag) {
        String myTagStr = myTag == null ? "" : String.valueOf(myTag);

        OpsChngTrace parent = upperTier;
        char suffix = OpsChngCoord.SUFFIX_BETWEEN;
        if (upperTier == null) {
            parent = parentNode;
            suffix = OpsChngCoord.SUFFIX_NODE;
        }

        OpsChngCoord parentCoord = parent.getCoord();
        String myCoord = composeCoord(parentCoord.getCoord(), myTagStr, suffix);
        return new OpsChngTrace(parent.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @param parentTrace
     * @param nodeNo
     * @return
     */
    public static OpsChngTrace genTraceNode(OpsChngTrace parentTrace, String nodeNo) {
        OpsChngCoord parentCoord = parentTrace.getCoord();
        String myCoord = composeCoord(parentCoord.getCoord(), nodeNo, OpsChngCoord.SUFFIX_BETWEEN);
        return new OpsChngTrace(parentTrace.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @param parentTrace
     * @param nodeNo
     * @return
     */
    public static OpsChngTrace genTraceNodeByService(OpsChngTrace parentTrace, Object nodeNo) {
        OpsChngCoord parentCoord = parentTrace.getCoord();
        String myCoord = composeCoord(parentCoord.getCoord(), nodeNo == null ? "" : String.valueOf(nodeNo),
            OpsChngCoord.SUFFIX_SERVICE);
        return new OpsChngTrace(parentTrace.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @param parentNode
     * @return
     */
    public static OpsChngTrace genTraceServiceDirectFromNode(OpsChngTrace parentNode) {
        return genTraceServiceDirectFromNode(parentNode, null);
    }

    /**
     * @param parentNode
     * @return
     */
    public static OpsChngTrace genTraceServiceDirectFromNode(OpsChngTrace parentNode, Object myTag) {
        String myTagStr = myTag == null ? "0" : String.valueOf(myTag);
        String myCoord = composeCoord(parentNode.getCoord().getCoord(), myTagStr, OpsChngCoord.SUFFIX_NODE);
        return new OpsChngTrace(parentNode.getTraceId(), new OpsChngCoord(myCoord));
    }

    /**
     * @param parentService
     * @return
     */
    public static OpsChngTrace genTraceFlow(OpsChngTrace parentService) {
        if (parentService == null) {
            return null;
        }
        return parentService.clone();
    }

    /**
     * <code>task1.node1:gray1.retry2|task4.node1:rev</code>
     *
     * @param parentCoord
     * @param myTag
     * @param split
     * @return
     */
    static String composeCoord(String parentCoord, String myTag, char split) {
        if (parentCoord == null) {
            parentCoord = "";
        }
        if (myTag == null) {
            myTag = "";
        }
        StringBuilder sb = new StringBuilder(parentCoord.length() + myTag.length() + 1);
        //parentCoord suffix myTag
        sb.append(parentCoord).append(split).append(myTag);
        return sb.toString();
    }



    /**
     * 当前这个service在.同属一个nodeId的所有service中的index
     * @param coord exeService的coord
     * @return -1:说明当前coord不是service; >=0:被调动的service在同一个NodeId的调用中的index.从0开始,每次调用依次累加
     */
    public static int getCurrServiceIndex(String coord) {
        try {
            int indexOf1 = coord.lastIndexOf(".");
            int indexOf2 = coord.lastIndexOf(":");
            int indexOf = Math.max(indexOf1, indexOf2);
            if(indexOf == -1){
                return -1;
            }
            String indexStr = coord.substring(indexOf+1);
            return Integer.parseInt(indexStr);
        } catch (Exception e) {
            return -1;
        }
    }

}

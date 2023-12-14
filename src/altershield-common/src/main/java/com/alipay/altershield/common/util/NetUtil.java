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


import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalErrorCode;
import com.alipay.altershield.framework.common.util.exception.AlterShieldInternalException;

import java.net.InetAddress;
import java.util.List;

/**
 * @author shuo.qius
 * @version Oct 13, 2017
 */
public class NetUtil {


    /**
     * @return
     */
    public static String getLocalIp() {
        try {
            InetAddress add = InetAddress.getLocalHost();
            return add.getHostAddress();
        } catch (Throwable e) {
            throw new AlterShieldInternalException(AlterShieldInternalErrorCode.UNKNOWN_HOST, "found no address for localIp", e);
        }
    }

    /**
     * @param address e.g. "11.234.37.110" or "acctrans-30-5842.gtj.alipay.com"
     * @return e.g. "11.234.37.110"
     */
    public static String getIp(String address) {
        try {
            InetAddress ipaddress = InetAddress.getByName(address);
            return ipaddress.getHostAddress();
        } catch (Throwable e) {
            throw new AlterShieldInternalException(AlterShieldInternalErrorCode.UNKNOWN_HOST, "cannot convert ip " + address + ": " + e.toString(), e);
        }
    }

    /**
     * @param address e.g. "11.234.37.110" or "acctrans-30-5842.gtj.alipay.com" or "acctrans-30-5842"
     * @return e.g. "acctrans-30-5842"
     */
    public static String getHostName(String address) {
        String host = address;
        if (isIp(address)) {
            host = getHost(address);
        }
        return host2HostName(host);
    }

    /**
     * @param address e.g. "11.234.37.110" or "acctrans-30-5842.gtj.alipay.com"
     * @return e.g. "acctrans-30-5842.gtj.alipay.com"
     */
    public static String getHost(String address) {

        try {
            if (!isIp(address)) {
                InetAddress inetAddr = InetAddress.getByName(address);
                return inetAddr.getHostAddress();
            }
            //IP地址转换为字符串数组
            String[] ipStr = address.split("[.]");
            //声明存储转换后IP地址的字节数组
            byte[] ipBytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                //转换为整数
                int m = Integer.parseInt(ipStr[i]);
                //转换为字节
                byte b = (byte) (m & 0xff);
                ipBytes[i] = b;
            }
            InetAddress inetAddr = InetAddress.getByAddress(ipBytes); //创建InetAddress对象
            String host = inetAddr.getHostName();//获取域名
            if (host.length() == address.length() && host.equals(address)) {
                throw new AlterShieldInternalException(AlterShieldInternalErrorCode.UNKNOWN_HOST, "cannot convert " + address + " to host ");
            }
            return host;
        } catch (AlterShieldInternalException sdke) {
            throw sdke;
        } catch (Throwable e) {
            throw new AlterShieldInternalException(AlterShieldInternalErrorCode.UNKNOWN_HOST, "cannot convert " + address + " to host: " + e.toString(),
                e);
        }
    }

    /**
     * @param host e.g. "acctrans-30-5842.gtj.alipay.com"
     * @return e.g. "acctrans-30-5842"
     */
    public static String host2HostName(String host) {
        if (CommonUtil.isBlank(host)) {
            throw new AlterShieldInternalException(AlterShieldInternalErrorCode.UNKNOWN_HOST, "empty host " + host);
        }
        if (isIp(host)) {
            throw new AlterShieldInternalException(AlterShieldInternalErrorCode.UNKNOWN_HOST, "no host found for " + host);
        }
        int i = host.indexOf('.');
        if (i >= 0) {
            return host.substring(0, i);
        }
        return host;
    }

    /**
     * whether is a string with ip format (not necessarily a existed ip address)
     *
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {
        if (CommonUtil.isBlank(ip)) {
            return false;
        }
        List<String> toks = CommonUtil.splitAsList(ip, ".", true);
        if (toks.size() != 4) {
            return false;
        }
        for (String tok : toks) {
            for (int i = 0; i < tok.length(); ++i) {
                char c = tok.charAt(i);
                if (c > '9' || c < '0') {
                    return false;
                }
            }
            int num = Integer.parseInt(tok);
            if (num > 255 || num < 0) {
                return false;
            }
        }
        return true;
    }

}

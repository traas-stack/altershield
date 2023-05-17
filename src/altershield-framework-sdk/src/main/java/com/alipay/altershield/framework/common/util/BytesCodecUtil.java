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
package com.alipay.altershield.framework.common.util;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BytesCodecUtil {

    /**
     * Gzip compress then base 64 encode string.
     *
     * @param bytes original bytes
     * @return first compress input, then base64 encode compressed bytes
     */
    public static String gzipCompressThenBase64Encode(byte[] bytes) {
        return base64Encode(gzipEncode(bytes));
    }

    /**
     * Base 64 decode then gzip decompress byte [ ].
     *
     * @param str base64 encoded compressed content
     * @return first base64 decode, then decompressed bytes
     */
    public static byte[] base64DecodeThenGzipDecompress(String str) {
        return gzipDecode(base64Decode(str));

    }

    /**
     * Base 64 decode byte [ ].
     *
     * @param str base64 encoded string
     * @return byte [ ]
     */
    public static byte[] base64Decode(String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return new byte[0];
        }
        return DatatypeConverter.parseBase64Binary(str);
    }

    /**
     * Base 64 encode string.
     *
     * @param bs byte[] to be encoded
     * @return base64 encoded string
     */
    public static String base64Encode(byte[] bs) {
        if (bs == null) {
            return null;
        }
        if (bs.length == 0) {
            return "";
        }
        return DatatypeConverter.printBase64Binary(bs);
    }

    /**
     * Gzip decode byte [ ].
     *
     * @param bytes compressed bytes
     * @return decompressed bytes
     */
    public static byte[] gzipDecode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length == 0) {
            return new byte[0];
        }
        try {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int b = 0; (b = gis.read()) >= 0;) {
                out.write(b);
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot decode bytes as gzip", e);
        }
    }

    /**
     * Gzip encode byte [ ].
     *
     * @param bytes original bytes
     * @return compressed bytes
     */
    public static byte[] gzipEncode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length <= 0) {
            return new byte[0];
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
            gzip.close();
        } catch (IOException e) {
            // NOPMD
        }
        return out.toByteArray();
    }
}

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
package com.alipay.altershield.schedule;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @author jinyalong
 * @version : UidToolkit.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public class UidToolkit {

    private String[]       uidArray;
    private final int maxUid;
    private int       uidIndex = 0;

    /**
     * Constructor.
     *
     * @param maxUid the max uid
     */
    public UidToolkit(int maxUid) {
        if(maxUid <= 0)
        {
            throw new RuntimeException("max uid must above 0");
        }
        this.maxUid = maxUid;
        init();
    }

    private void init() {
        initUidArray();
        initUidStartFetchIndex();
    }

    private void initUidArray() {
        uidArray = new String[maxUid];
        for (int i = 0; i < maxUid; ++i) {
            String uid = StringUtils.leftPad(String.valueOf(i), 2, '0');
            uidArray[i] = uid;
        }
    }

    private void initUidStartFetchIndex() {
        uidIndex = new Random().nextInt(maxUid);
    }

    /**
     * Gets get next uid.
     *
     * @return the get next uid
     */
    public synchronized String getNextUid() {
        ++uidIndex;
        if (uidIndex >= maxUid) {
            uidIndex = 0;
        }
        return uidArray[uidIndex];
    }

    /**
     * Get uid array string [ ].
     *
     * @return the string [ ]
     */
    public String[] getUidArray() {
        return uidArray;
    }

}
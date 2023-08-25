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
package com.alipay.altershiled.schedule.exception;

import com.alipay.altershiled.schedule.enums.SchedulerEventHandleStatus;

/**
 * dispatcher exception
 *
 * @author jinyalong
 * @version : SchedulerEventDispatcherException.java, v 0.1 2023-08-25 xiangyue Exp $$
 */
public class SchedulerEventDispatcherException extends RuntimeException{

    private static final long serialVersionUID = 2077175079315108684L;
    private final SchedulerEventHandleStatus status;

    /**
     * Instantiates a new Scheduler point dispatcher exception.
     *
     * @param message the message
     * @param status  the status
     */
    public SchedulerEventDispatcherException(String message, SchedulerEventHandleStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public SchedulerEventHandleStatus getStatus() {
        return status;
    }
}
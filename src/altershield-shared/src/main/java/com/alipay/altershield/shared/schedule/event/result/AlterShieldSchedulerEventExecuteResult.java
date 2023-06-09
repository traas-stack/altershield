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
package com.alipay.altershield.shared.schedule.event.result;

import com.alipay.altershield.shared.schedule.enums.AlterShieldScheduleEventResultStatus;
import lombok.Data;

/**
 * The type Scheduler event execute result.
 *
 * @author yuanji
 * @version : SchedulerPointEventExecuteResult.java, v 0.1 2022年08月11日 16:49 yuanji Exp $
 */
@Data
public class AlterShieldSchedulerEventExecuteResult {

    private String msg;

    private AlterShieldScheduleEventResultStatus status;


    /**
     * Instantiates a new Scheduler event execute result.
     *
     * @param msg    the msg
     * @param status the status
     */
    public AlterShieldSchedulerEventExecuteResult(String msg, AlterShieldScheduleEventResultStatus status) {
        this.msg = msg;
        this.status = status;
    }

    /**
     * Instantiates a new Scheduler event execute result.
     */
    public AlterShieldSchedulerEventExecuteResult() {
    }

    public static AlterShieldSchedulerEventExecuteResult success(String msg)
    {
        return new AlterShieldSchedulerEventExecuteResult(msg, AlterShieldScheduleEventResultStatus.SUCCESS);
    }




}
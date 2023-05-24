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
package com.alipay.altershield.framework.common.util.exception;

import com.alipay.altershield.framework.common.util.CommonUtil;
import lombok.Getter;

@Getter
public class AlterShieldInternalException extends RuntimeException {
    private static final long serialVersionUID = 3977367911310548985L;
    private final AlterShieldInternalErrorCode errorCode;

    /**
     * Instantiates a new Ops cloud internal exception.
     *
     * @param errorCode the error code
     * @param message the message
     * @param cause the cause
     */
    public AlterShieldInternalException(AlterShieldInternalErrorCode errorCode, String message,
            Throwable cause) {
        super(CommonUtil.isBlank(message) ? errorCode.toString() : errorCode.toString() + message,
                cause);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Ops cloud internal exception.
     *
     * @param errorCode the error code
     * @param cause the cause
     */
    public AlterShieldInternalException(AlterShieldInternalErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Ops cloud internal exception.
     *
     * @param errorCode the error code
     * @param message the message
     */
    public AlterShieldInternalException(AlterShieldInternalErrorCode errorCode, String message) {
        super(CommonUtil.isBlank(message) ? errorCode.toString() : errorCode.toString() + message);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new Ops cloud internal exception.
     *
     * @param errorCode the error code
     */
    public AlterShieldInternalException(AlterShieldInternalErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

}

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
package com.alipay.altershield.web.common.handler;

import com.alipay.altershield.framework.core.change.facade.result.AlterShieldResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未知异常处理
     *
     * @param ex 异常对象
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public AlterShieldResult<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult exceptions = ex.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<FieldError> errors = exceptions.getFieldErrors();
            if (!errors.isEmpty()) {
                StringBuilder builder = new StringBuilder();

                int i = 0;
                for (FieldError fieldError : errors) {
                    if (i != 0) {
                        builder.append("\n");
                    }
                    builder.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage());
                    i++;
                }

                return AlterShieldResult.illegalArgument(builder.toString());
            }
        }
        return AlterShieldResult.illegalArgument("invalid param error");
    }

}
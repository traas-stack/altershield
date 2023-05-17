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
package com.alipay.altershield.framework.common.validate;

import com.alipay.altershield.framework.common.validate.validator.StringLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * utf8编码后的string的长度范围
 *
 * @author shuo.qius
 * @version Jan 27, 2019
 */
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = {StringLengthValidator.class})
public @interface StringLength {
    /**
     * @return 最小值 >=min(>0的情况等价于>=1)
     */
    int min() default 0;

    /**
     * @return 最大值 <=max
     */
    int max() default Integer.MAX_VALUE;

    /**
     * message
     *
     * @return
     */
    String message() default "字符utf8编码后的长度必须大于 {min} 且小于 {max}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



}

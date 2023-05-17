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
package com.alipay.altershield.framework.common.validate.validator;

import com.alipay.altershield.framework.common.util.JSONUtil;
import com.alipay.altershield.framework.common.validate.JsonStringLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yuanji
 * @version : JsonStringLengthValidator.java, v 0.1 2022年02月15日 7:53 下午 yuanji Exp $
 */
public class JsonStringLengthValidator implements ConstraintValidator<JsonStringLength, String> {

    private JsonStringLength jsonStringLength;

    @Override
    public void initialize(JsonStringLength constraintAnnotation) {
        this.jsonStringLength = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        boolean isJson = JSONUtil.isValidJSON(s);
        if (!isJson) {
            return false;
        }
        int len;
        try {
            byte[] b = s.getBytes("UTF-8");
            len = b.length;
        } catch (Exception e) {
            len = s.length();
        }
        if (len > jsonStringLength.max()) {
            return false;
        }
        return true;
    }
}

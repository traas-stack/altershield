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

import com.alipay.altershield.framework.common.util.CommonUtil;
import com.alipay.altershield.framework.common.validate.UserInfoFormat;
import com.alipay.altershield.framework.common.validate.validator.spi.UserInfoFormatChecker;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用户格式校验。 需要使用自己使用 {@link UserInfoFormatChecker} 检查接口
 *
 * @author yuanji
 * @version : UserInfoFormatValidator.java, v 0.1 2022年02月22日 4:53 下午 yuanji Exp $
 */
public class UserInfoFormatValidator implements ConstraintValidator<UserInfoFormat, String> {


    private UserInfoFormatChecker userInfoFormatChecker;
    private UserInfoFormat userInfoFormat;

    @Override
    public void initialize(UserInfoFormat constraintAnnotation) {
        this.userInfoFormat = constraintAnnotation;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (userInfoFormatChecker == null) {
            return true;
        }
        String message = userInfoFormatChecker.check(s);
        if (CommonUtil.isBlank(message)) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
        return false;
    }

    public void setUserInfoFormatChecker(UserInfoFormatChecker userInfoFormatChecker) {
        this.userInfoFormatChecker = userInfoFormatChecker;
    }
}

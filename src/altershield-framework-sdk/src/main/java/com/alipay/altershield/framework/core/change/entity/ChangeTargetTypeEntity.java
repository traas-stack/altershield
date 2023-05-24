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
package com.alipay.altershield.framework.core.change.entity;

import com.alipay.altershield.framework.core.base.AbstractBasicEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 变更目标类型实体
 * @author liushengdong
 * @version $Id: ChangeTargetTypeEntity.java, v 0.1 2018年10月29日 10:44 AM liushengdong Exp $
 */
@Data
@ToString(callSuper = true)
public class ChangeTargetTypeEntity extends AbstractBasicEntity {

    private static final long serialVersionUID = -3065815872734234972L;
    /**
     * change target type
     */
    private String type;
    /**
     * target type entity name
     */
    private String name;

    /**
     * 类型名称描述
     */
    private String desc;

    /**
     * 父节点类型,如果没有父节点为空
     */
    private List<String> parentTypes;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 定义该变更实体的平台
     */
    private String platformName;

}
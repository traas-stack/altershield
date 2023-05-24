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
package com.alipay.altershield.framework.core.change.model.enums;

/**
 * 变更场景代G元模型
 *
 * @author yuanji
 * @version : OpsCloudChangeSceneGenerationEnum.java, v 0.1 2022年02月09日 4:37 下午 yuanji Exp $
 */
public enum MetaChangeSceneGenerationEnum {
    /**
     * G0，变更时仅通知
     */
    G0("G0"),
    /**
     * G1，变更时可以做前置防御
     */
    G1("G1"),

    /**
     * G2，变更满足标准分批的灰度流程
     */
    G2("G2"),
    /**
     * G 3 meta change scene generation enum.
     */
    G3("G3"),
    /**
     * G 4 meta change scene generation enum.
     */
    G4("G4");

    /**
     * 代G名
     */
    private String name;

    MetaChangeSceneGenerationEnum(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    public static MetaChangeSceneGenerationEnum getByName(String name) {
        for (MetaChangeSceneGenerationEnum metaChangeSceneGenerationEnum : values()) {
            if (metaChangeSceneGenerationEnum.getName().equals(name)) {
                return metaChangeSceneGenerationEnum;
            }
        }
        return null;
    }

}
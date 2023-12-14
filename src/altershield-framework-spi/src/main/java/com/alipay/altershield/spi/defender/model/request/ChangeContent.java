/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender.model.request;

import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * Structure definition of change content
 *
 * @author yhaoxuan
 * @version ChangeContent.java, v 0.1 2022年08月08日 4:37 下午 yhaoxuan
 */
public class ChangeContent {

    /**
     * Change content type (configuration item/application system/container...)
     */
    @NotNull
    private String changeContentType;

    /**
     * Change content instance (take configuration change as an example: the name of the configuration item)
     */
    @NotNull
    private List<String> changeContentInstance;

    /**
     * Change content value (take configuration change as an example: the value of the configuration item to push)
     */
    private String changeContentValue;

    /**
     * Getter method for property <tt>changeContentType</tt>.
     *
     * @return property value of changeContentType
     */
    public String getChangeContentType() {
        return changeContentType;
    }

    /**
     * Setter method for property <tt>changeContentType</tt>.
     *
     * @param changeContentType value to be assigned to property changeContentType
     */
    public void setChangeContentType(String changeContentType) {
        this.changeContentType = changeContentType;
    }

    /**
     * Getter method for property <tt>changeContentInstance</tt>.
     *
     * @return property value of changeContentInstance
     */
    public List<String> getChangeContentInstance() {
        return changeContentInstance;
    }

    /**
     * Setter method for property <tt>changeContentInstance</tt>.
     *
     * @param changeContentInstance value to be assigned to property changeContentInstance
     */
    public void setChangeContentInstance(List<String> changeContentInstance) {
        this.changeContentInstance = changeContentInstance;
    }

    /**
     * Getter method for property <tt>changeContentValue</tt>.
     *
     * @return property value of changeContentValue
     */
    public String getChangeContentValue() {
        return changeContentValue;
    }

    /**
     * Setter method for property <tt>changeContentValue</tt>.
     *
     * @param changeContentValue value to be assigned to property changeContentValue
     */
    public void setChangeContentValue(String changeContentValue) {
        this.changeContentValue = changeContentValue;
    }
}
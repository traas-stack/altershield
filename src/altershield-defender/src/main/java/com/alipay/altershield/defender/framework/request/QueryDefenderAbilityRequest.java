/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.defender.framework.request;

/**
 * Query defense service request structure
 *
 * @author yhaoxuan
 * @version QueryDefenderAbilityRequest.java, v 0.1 2022年11月22日 8:28 下午 yhaoxuan
 */
public class QueryDefenderAbilityRequest {

    /**
     * owner
     */
    private String owner;

    /**
     * service name
     */
    private String serviceName;

    /**
     * control service key
     */
    private String controlKey;

    /**
     * Getter method for property <tt>owner</tt>.
     *
     * @return property value of owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Setter method for property <tt>owner</tt>.
     *
     * @param owner value to be assigned to property owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Getter method for property <tt>serviceName</tt>.
     *
     * @return property value of serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Setter method for property <tt>serviceName</tt>.
     *
     * @param serviceName value to be assigned to property serviceName
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Getter method for property <tt>controlKey</tt>.
     *
     * @return property value of controlKey
     */
    public String getControlKey() {
        return controlKey;
    }

    /**
     * Setter method for property <tt>controlKey</tt>.
     *
     * @param controlKey value to be assigned to property controlKey
     */
    public void setControlKey(String controlKey) {
        this.controlKey = controlKey;
    }
}
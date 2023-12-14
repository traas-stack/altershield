/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.spi.defender.model.request;

import java.util.Map;
import java.util.Set;

/**
 * Structure of change Influence
 *
 * @author yhaoxuan
 * @version ChangeInfluenceInfo.java, v 0.1 2022年08月09日 5:05 下午 yhaoxuan
 */
public class ChangeInfluenceInfo {

    /**
     * List of application systems affected by this change
     */
    private Set<String> apps;

    /**
     * List of tenants affected by this change
     */
    private Set<String> tenants;

    /**
     * List of environments affected by this change
     */
    private Set<String> envs;

    /**
     * List of logical deployment units affected by this change
     */
    private Set<String> logicDeployCells;

    /**
     * List of physical deployment units affected by this change
     */
    private Set<String> physicDeployCells;

    /**
     * List of servers affected by this change
     */
    private Set<String> hosts;

    /**
     * extension field
     */
    private Map<String, Object> extInfo;

    /**
     * Getter method for property <tt>apps</tt>.
     *
     * @return property value of apps
     */
    public Set<String> getApps() {
        return apps;
    }

    /**
     * Setter method for property <tt>apps</tt>.
     *
     * @param apps value to be assigned to property apps
     */
    public void setApps(Set<String> apps) {
        this.apps = apps;
    }

    /**
     * Getter method for property <tt>tenants</tt>.
     *
     * @return property value of tenants
     */
    public Set<String> getTenants() {
        return tenants;
    }

    /**
     * Setter method for property <tt>tenants</tt>.
     *
     * @param tenants value to be assigned to property tenants
     */
    public void setTenants(Set<String> tenants) {
        this.tenants = tenants;
    }

    /**
     * Getter method for property <tt>envs</tt>.
     *
     * @return property value of envs
     */
    public Set<String> getEnvs() {
        return envs;
    }

    /**
     * Setter method for property <tt>envs</tt>.
     *
     * @param envs value to be assigned to property envs
     */
    public void setEnvs(Set<String> envs) {
        this.envs = envs;
    }

    /**
     * Getter method for property <tt>logicDeployCells</tt>.
     *
     * @return property value of logicDeployCells
     */
    public Set<String> getLogicDeployCells() {
        return logicDeployCells;
    }

    /**
     * Setter method for property <tt>logicDeployCells</tt>.
     *
     * @param logicDeployCells value to be assigned to property logicDeployCells
     */
    public void setLogicDeployCells(Set<String> logicDeployCells) {
        this.logicDeployCells = logicDeployCells;
    }

    /**
     * Getter method for property <tt>physicDeployCells</tt>.
     *
     * @return property value of physicDeployCells
     */
    public Set<String> getPhysicDeployCells() {
        return physicDeployCells;
    }

    /**
     * Setter method for property <tt>physicDeployCells</tt>.
     *
     * @param physicDeployCells value to be assigned to property physicDeployCells
     */
    public void setPhysicDeployCells(Set<String> physicDeployCells) {
        this.physicDeployCells = physicDeployCells;
    }

    /**
     * Getter method for property <tt>hosts</tt>.
     *
     * @return property value of hosts
     */
    public Set<String> getHosts() {
        return hosts;
    }

    /**
     * Setter method for property <tt>hosts</tt>.
     *
     * @param hosts value to be assigned to property hosts
     */
    public void setHosts(Set<String> hosts) {
        this.hosts = hosts;
    }

    /**
     * Getter method for property <tt>extInfo</tt>.
     *
     * @return property value of extInfo
     */
    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    /**
     * Setter method for property <tt>extInfo</tt>.
     *
     * @param extInfo value to be assigned to property extInfo
     */
    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }
}
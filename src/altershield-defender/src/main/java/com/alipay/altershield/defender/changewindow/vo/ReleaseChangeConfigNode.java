/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.altershield.defender.changewindow.vo;

/**
 * 破网申请配置节点
 * @author zhijian.leixiao
 * @version 1.0: ReleaseChangeConfigNode, v 0.1 2019-08-01 15:09 lx207087 Exp $
 */
public class ReleaseChangeConfigNode {
    /**
     * 变更平台
     */
    private String platform;
    /**
     * 变更类型
     */
    private String type;
    /**
     * 变更类型 名字
     */
    private String name;
    /**
     * 工单/发布id
     */
    private String id;

    /**
     * 关联应用,多个英文逗号分割
     */
    private String apps;

    /**
     * 扩展信息
     */
    private ReleaseChangeConfigNodeExt ext;

    /**
     * Getter method for property <tt>platform</tt>.
     *
     * @return property value of platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param platform value to be assigned to property platform
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>apps</tt>.
     *
     * @return property value of apps
     */
    public String getApps() {
        return apps;
    }

    /**
     * Setter method for property <tt>apps</tt>.
     *
     * @param apps value to be assigned to property apps
     */
    public void setApps(String apps) {
        this.apps = apps;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>ext</tt>.
     *
     * @return property value of ext
     */
    public ReleaseChangeConfigNodeExt getExt() {
        return ext;
    }

    /**
     * Setter method for property <tt>ext</tt>.
     *
     * @param ext value to be assigned to property ext
     */
    public void setExt(ReleaseChangeConfigNodeExt ext) {
        this.ext = ext;
    }
}

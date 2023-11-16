/*
 * Ant Group
 * Copyright (c) 2004-2022 All Rights Reserved.
 */
package com.alipay.altershield.shared.defender.request;

import java.util.Set;

/**
 * Defense rule matching request structure
 *
 * @author yuefan.wyf
 * @version $Id: FilterRequest.java, v 0.1 2019年11月18日 下午1:07 yuefan.wyf Exp $
 */
public class ChangeFilterRequest {

    /**
     * Change source platform
     */
    private String sourcePlatform;

    /**
     * Change title
     */
    private String changeTile;

    /**
     * Change operator
     */
    private String operator;

    /**
     * Change parameters
     */
    private String changeParam;

    /**
     * Change results
     */
    private String changeResult;

    /**
     * Change phase stages
     */
    private String changePhase;

    /**
     * Name of current change batch
     */
    private String changePhaseBatchName;

    /**
     * Number of current change batch
     */
    private int changePhaseBatchNo = -1;

    /**
     * Is the last batch of the change
     */
    private boolean lastBatch;

    /**
     * Is the last batch of the phase stage
     */
    private boolean lastBatchInPhase;

    /**
     * Total batch number
     */
    private int totalBatchNum;

    /**
     * Total batch number in current phase
     */
    private int totalBatchNumInPhase;

    /**
     * Change target instances
     */
    private Set<String> changeTargetInstances;

    /**
     * Change target types
     */
    private Set<String> changeTargetTypes;

    /**
     * Application systems influenced by the change
     */
    private Set<String> changeApps;

    /**
     * Logical cells influenced by the change
     */
    private Set<String> logicalCells;

    /**
     * IDCs influenced by the change
     */
    private Set<String> idcs;

    /**
     * Servers influenced by the change
     */
    private Set<String> hosts;

    /**
     * Interface services influenced by the change
     */
    private Set<String> affectedServices;

    /**
     * Influenced environment
     */
    private Set<String> envs;

    /**
     * Influenced tenants
     */
    private Set<String> tenants;

    /**
     * Entrance codes of the change
     */
    private Set<String> entranceCodes;

    /**
     * Scenario codes of the change
     */
    private Set<String> sceneCodes;

    /**
     * Getter method for property <tt>sourcePlatform</tt>.
     *
     * @return property value of sourcePlatform
     */
    public String getSourcePlatform() {
        return sourcePlatform;
    }

    /**
     * Setter method for property <tt>sourcePlatform</tt>.
     *
     * @param sourcePlatform value to be assigned to property sourcePlatform
     */
    public void setSourcePlatform(String sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    /**
     * Getter method for property <tt>changeTile</tt>.
     *
     * @return property value of changeTile
     */
    public String getChangeTile() {
        return changeTile;
    }

    /**
     * Setter method for property <tt>changeTile</tt>.
     *
     * @param changeTile value to be assigned to property changeTile
     */
    public void setChangeTile(String changeTile) {
        this.changeTile = changeTile;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     *
     * @return property value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     *
     * @param operator value to be assigned to property operator
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>changeParam</tt>.
     *
     * @return property value of changeParam
     */
    public String getChangeParam() {
        return changeParam;
    }

    /**
     * Setter method for property <tt>changeParam</tt>.
     *
     * @param changeParam value to be assigned to property changeParam
     */
    public void setChangeParam(String changeParam) {
        this.changeParam = changeParam;
    }

    /**
     * Getter method for property <tt>changeResult</tt>.
     *
     * @return property value of changeResult
     */
    public String getChangeResult() {
        return changeResult;
    }

    /**
     * Setter method for property <tt>changeResult</tt>.
     *
     * @param changeResult value to be assigned to property changeResult
     */
    public void setChangeResult(String changeResult) {
        this.changeResult = changeResult;
    }

    /**
     * Getter method for property <tt>changePhase</tt>.
     *
     * @return property value of changePhase
     */
    public String getChangePhase() {
        return changePhase;
    }

    /**
     * Setter method for property <tt>changePhase</tt>.
     *
     * @param changePhase value to be assigned to property changePhase
     */
    public void setChangePhase(String changePhase) {
        this.changePhase = changePhase;
    }

    /**
     * Getter method for property <tt>changePhaseBatchName</tt>.
     *
     * @return property value of changePhaseBatchName
     */
    public String getChangePhaseBatchName() {
        return changePhaseBatchName;
    }

    /**
     * Setter method for property <tt>changePhaseBatchName</tt>.
     *
     * @param changePhaseBatchName value to be assigned to property changePhaseBatchName
     */
    public void setChangePhaseBatchName(String changePhaseBatchName) {
        this.changePhaseBatchName = changePhaseBatchName;
    }

    /**
     * Getter method for property <tt>changePhaseBatchNo</tt>.
     *
     * @return property value of changePhaseBatchNo
     */
    public int getChangePhaseBatchNo() {
        return changePhaseBatchNo;
    }

    /**
     * Setter method for property <tt>changePhaseBatchNo</tt>.
     *
     * @param changePhaseBatchNo value to be assigned to property changePhaseBatchNo
     */
    public void setChangePhaseBatchNo(int changePhaseBatchNo) {
        this.changePhaseBatchNo = changePhaseBatchNo;
    }

    /**
     * Getter method for property <tt>lastBatch</tt>.
     *
     * @return property value of lastBatch
     */
    public boolean isLastBatch() {
        return lastBatch;
    }

    /**
     * Setter method for property <tt>lastBatch</tt>.
     *
     * @param lastBatch value to be assigned to property lastBatch
     */
    public void setLastBatch(boolean lastBatch) {
        this.lastBatch = lastBatch;
    }

    /**
     * Getter method for property <tt>lastBatchInPhase</tt>.
     *
     * @return property value of lastBatchInPhase
     */
    public boolean isLastBatchInPhase() {
        return lastBatchInPhase;
    }

    /**
     * Setter method for property <tt>lastBatchInPhase</tt>.
     *
     * @param lastBatchInPhase value to be assigned to property lastBatchInPhase
     */
    public void setLastBatchInPhase(boolean lastBatchInPhase) {
        this.lastBatchInPhase = lastBatchInPhase;
    }

    /**
     * Getter method for property <tt>totalBatchNum</tt>.
     *
     * @return property value of totalBatchNum
     */
    public int getTotalBatchNum() {
        return totalBatchNum;
    }

    /**
     * Setter method for property <tt>totalBatchNum</tt>.
     *
     * @param totalBatchNum value to be assigned to property totalBatchNum
     */
    public void setTotalBatchNum(int totalBatchNum) {
        this.totalBatchNum = totalBatchNum;
    }

    /**
     * Getter method for property <tt>totalBatchNumInPhase</tt>.
     *
     * @return property value of totalBatchNumInPhase
     */
    public int getTotalBatchNumInPhase() {
        return totalBatchNumInPhase;
    }

    /**
     * Setter method for property <tt>totalBatchNumInPhase</tt>.
     *
     * @param totalBatchNumInPhase value to be assigned to property totalBatchNumInPhase
     */
    public void setTotalBatchNumInPhase(int totalBatchNumInPhase) {
        this.totalBatchNumInPhase = totalBatchNumInPhase;
    }

    /**
     * Getter method for property <tt>changeTargetInstances</tt>.
     *
     * @return property value of changeTargetInstances
     */
    public Set<String> getChangeTargetInstances() {
        return changeTargetInstances;
    }

    /**
     * Setter method for property <tt>changeTargetInstances</tt>.
     *
     * @param changeTargetInstances value to be assigned to property changeTargetInstances
     */
    public void setChangeTargetInstances(Set<String> changeTargetInstances) {
        this.changeTargetInstances = changeTargetInstances;
    }

    /**
     * Getter method for property <tt>changeTargetTypes</tt>.
     *
     * @return property value of changeTargetTypes
     */
    public Set<String> getChangeTargetTypes() {
        return changeTargetTypes;
    }

    /**
     * Setter method for property <tt>changeTargetTypes</tt>.
     *
     * @param changeTargetTypes value to be assigned to property changeTargetTypes
     */
    public void setChangeTargetTypes(Set<String> changeTargetTypes) {
        this.changeTargetTypes = changeTargetTypes;
    }

    /**
     * Getter method for property <tt>changeApps</tt>.
     *
     * @return property value of changeApps
     */
    public Set<String> getChangeApps() {
        return changeApps;
    }

    /**
     * Setter method for property <tt>changeApps</tt>.
     *
     * @param changeApps value to be assigned to property changeApps
     */
    public void setChangeApps(Set<String> changeApps) {
        this.changeApps = changeApps;
    }

    /**
     * Getter method for property <tt>logicalCells</tt>.
     *
     * @return property value of logicalCells
     */
    public Set<String> getLogicalCells() {
        return logicalCells;
    }

    /**
     * Setter method for property <tt>logicalCells</tt>.
     *
     * @param logicalCells value to be assigned to property logicalCells
     */
    public void setLogicalCells(Set<String> logicalCells) {
        this.logicalCells = logicalCells;
    }

    /**
     * Getter method for property <tt>idcs</tt>.
     *
     * @return property value of idcs
     */
    public Set<String> getIdcs() {
        return idcs;
    }

    /**
     * Setter method for property <tt>idcs</tt>.
     *
     * @param idcs value to be assigned to property idcs
     */
    public void setIdcs(Set<String> idcs) {
        this.idcs = idcs;
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
     * Getter method for property <tt>affectedServices</tt>.
     *
     * @return property value of affectedServices
     */
    public Set<String> getAffectedServices() {
        return affectedServices;
    }

    /**
     * Setter method for property <tt>affectedServices</tt>.
     *
     * @param affectedServices value to be assigned to property affectedServices
     */
    public void setAffectedServices(Set<String> affectedServices) {
        this.affectedServices = affectedServices;
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
     * Getter method for property <tt>entranceCodes</tt>.
     *
     * @return property value of entranceCodes
     */
    public Set<String> getEntranceCodes() {
        return entranceCodes;
    }

    /**
     * Setter method for property <tt>entranceCodes</tt>.
     *
     * @param entranceCodes value to be assigned to property entranceCodes
     */
    public void setEntranceCodes(Set<String> entranceCodes) {
        this.entranceCodes = entranceCodes;
    }

    /**
     * Getter method for property <tt>sceneCodes</tt>.
     *
     * @return property value of sceneCodes
     */
    public Set<String> getSceneCodes() {
        return sceneCodes;
    }

    /**
     * Setter method for property <tt>sceneCodes</tt>.
     *
     * @param sceneCodes value to be assigned to property sceneCodes
     */
    public void setSceneCodes(Set<String> sceneCodes) {
        this.sceneCodes = sceneCodes;
    }
}
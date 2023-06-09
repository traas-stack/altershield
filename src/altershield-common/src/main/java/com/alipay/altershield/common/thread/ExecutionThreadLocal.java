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
/*
 * Copyright Notice: This software is developed by Ant Small and Micro Financial Services Group Co., Ltd. This software and
 *  all the relevant information, including but not limited to any signs, images, photographs, animations, text,
 *  interface design, audios and videos, and printed materials, are protected by copyright laws and other intellectual
 *  property laws and treaties.
 *
 * The use of this software shall abide by the laws and regulations as well as Software Installation License
 * Agreement/Software Use Agreement updated from time to time. Without authorization from Ant Small and Micro Financial
 *  Services Group Co., Ltd., no one may conduct the following actions:
 *
 *   1) reproduce, spread, present, set up a mirror of, upload, download this software;
 *
 *   2) reverse engineer, decompile the source code of this software or try to find the source code in any other ways;
 *
 *   3) modify, translate and adapt this software, or develop derivative products, works, and services based on this
 *    software;
 *
 *   4) distribute, lease, rent, sub-license, demise or transfer any rights in relation to this software, or authorize
 *    the reproduction of this software on other’s computers.
 */
package com.alipay.altershield.common.thread;

/**
 * @author shuo.qius
 * @version Aug 31, 2018
 */
public class ExecutionThreadLocal {
    private static final ThreadLocal<ExecutionThreadLocalObj> local = new ThreadLocal<>();

    /**
     * @return
     */
    public static ExecutionThreadLocalObj get() {
        return local.get();
    }

    /**
     * Make a copy of the current thread local context if it exists, otherwise return null.
     *
     * @return
     */
    public static ExecutionThreadLocalObj cloneContext() {
        ExecutionThreadLocalObj other = local.get();
        if (other != null) {
            return new ExecutionThreadLocalObj(other);
        } else {
            return null;
        }
    }

    /**
     * @param state
     */
    public static void addTraceState(String state) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return;
        }
        obj.setTraceState(state);
    }

    /**
     * @param traceId
     */
    public static void addTraceId(String traceId) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return;
        }
        obj.setTraceId(traceId);
    }

    /**
     * @param coord
     */
    public static void addCoord(String coord) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return;
        }
        obj.setCoord(coord);
    }

    /**
     * @param pluginKey
     */
    public static void addPluginKey(String pluginKey) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            local.set(obj = new ExecutionThreadLocalObj());
        }
        obj.setPluginKey(pluginKey);
    }

    /**
     * @param plugigId
     */
    public static void addPluginId(String plugigId) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            local.set(obj = new ExecutionThreadLocalObj());
        }
        obj.setPluginId(plugigId);
    }

    /**
     * @param metaServiceKey
     */
    public static void addMetaServiceKey(String metaServiceKey) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            local.set(obj = new ExecutionThreadLocalObj());
        }
        obj.setMetaServiceKey(metaServiceKey);
    }

    /**
     * @param exeSrvId
     */
    public static void addExeSrvId(String exeSrvId) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            local.set(obj = new ExecutionThreadLocalObj());
        }
        obj.setExeSrvId(exeSrvId);
    }

    /**
     * @param exeNodeId
     */
    public static void addExeNodeId(String exeNodeId) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            local.set(obj = new ExecutionThreadLocalObj());
        }
        obj.setExeNodeId(exeNodeId);
    }

    /**
     * 直接获取机器的zoneName设置到schdPoint有个弊端:当手工单作为入口进入时,很可能进入的机器的zoneName不是原来执行的schdPoint的zoneName.
     * 因此统一将这个zoneName设置到线程上下文中
     * @param zoneName
     */
    public static void addZoneName(String zoneName){
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return;
        }
        obj.setZoneName(zoneName);
    }

    /**
     * @return
     */
    public static String getTraceId() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getTraceId();
    }

    public static String getTraceState() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getTraceState();
    }

    /**
     * @return
     */
    public static String getCoord() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getCoord();
    }

    /**
     *
     * @return
     */
    public static String getPluginKey() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getPluginKey();
    }

    /**
     *
     * @return
     */
    public static String getPluginId() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getPluginId();
    }

    /**
     *
     * @return
     */
    public static String getMetaServiceKey() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getMetaServiceKey();
    }

    /**
     * @return
     */
    public static String getExeSrvId() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getExeSrvId();
    }

    /**
     * @return
     */
    public static String getExeNodeId() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getExeNodeId();
    }

    /**
     * 直接获取机器的zoneName设置到schdPoint有个弊端:当手工单作为入口进入时,很可能进入的机器的zoneName不是原来执行的schdPoint的zoneName.
     * 因此统一将这个zoneName设置到线程上下文中
     * PS:除了异步调用的入口和手工单执行的入口,其他地方都可以从线程变量中获取zoneName
     * @return
     */
    public static String getZoneName() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getZoneName();
    }

    /**
     * 获得当前执行的SchedulePoint的类型
     */
    public static String getSchedulePointType() {
        ExecutionThreadLocalObj obj = local.get();
        if (obj == null) {
            return null;
        }
        return obj.getSchedulePointType();
    }

    /**
     * 设置当前执行的SchedulePoint的类型
     */
    public static void setSchedulePointType(String schedulePointType) {
        ExecutionThreadLocalObj obj = local.get();
        if (obj != null) {
            obj.setSchedulePointType(schedulePointType);
        }
    }

    /**
     * @param obj
     */
    public static void set(ExecutionThreadLocalObj obj) {
        if (obj == null) {
            local.remove();
        } else {
            local.set(obj);
        }
    }

    public static class ExecutionThreadLocalObj {
        private String traceId;
        private String coord;
        private String traceState;
        private String pluginKey;
        private String pluginId;
        private String metaServiceKey;
        private String exeSrvId;
        private String exeNodeId;
        private String zoneName;
        private String schedulePointType;

        public ExecutionThreadLocalObj() {
            super();
        }

        /**
         * Copy constructor.
         *
         * @param other
         */
        public ExecutionThreadLocalObj(ExecutionThreadLocalObj other) {
            this.traceId = other.traceId;
            this.coord = other.coord;
            this.traceState = other.traceState;
            this.pluginKey = other.pluginKey;
            this.pluginId = other.pluginId;
            this.metaServiceKey = other.metaServiceKey;
            this.exeSrvId = other.exeSrvId;
            this.exeNodeId = other.exeNodeId;
            this.zoneName = other.zoneName;
            this.schedulePointType = other.schedulePointType;
        }

        /**
         * @return property value of {@link #traceId}
         */
        public String getTraceId() {
            return traceId;
        }

        /**
         * @param traceId value to be assigned to property {@link #traceId}
         */
        public ExecutionThreadLocalObj setTraceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        /**
         * @return property value of {@link #traceState}
         */
        public String getTraceState() {
            return traceState;
        }

        /**
         * @param traceState value to be assigned to property {@link #traceState}
         */
        public ExecutionThreadLocalObj setTraceState(String traceState) {
            this.traceState = traceState;
            return this;
        }

        /**
         * @return property value of {@link #coord}
         */
        public String getCoord() {
            return coord;
        }

        /**
         * @param coord value to be assigned to property {@link #coord}
         */
        public ExecutionThreadLocalObj setCoord(String coord) {
            this.coord = coord;
            return this;
        }

        /**
         * @return property value of {@link #pluginKey}
         */
        public String getPluginKey() {
            return pluginKey;
        }

        /**
         * @param pluginKey value to be assigned to property {@link #pluginKey}
         */
        public ExecutionThreadLocalObj setPluginKey(String pluginKey) {
            this.pluginKey = pluginKey;
            return this;
        }

        /**
         * @param pluginId value to be assigned to property {@link #pluginId}
         */
        public ExecutionThreadLocalObj setPluginId(String pluginId) {
            this.pluginId = pluginId;
            return this;
        }

        /**
         * @param metaServiceKey value to be assigned to property {@link #metaServiceKey}
         */
        public ExecutionThreadLocalObj setMetaServiceKey(String metaServiceKey) {
            this.metaServiceKey = metaServiceKey;
            return this;
        }

        /**
         * @return property value of {@link #pluginId}
         */
        public String getPluginId() {
            return pluginId;
        }

        /**
         * Getter method for property <tt>metaServiceKey</tt>.
         *
         * @return property value of metaServiceKey
         */
        public String getMetaServiceKey() {
            return metaServiceKey;
        }

        /**
         * @return property value of {@link #exeSrvId}
         */
        public String getExeSrvId() {
            return exeSrvId;
        }

        /**
         * @param exeSrvId value to be assigned to property {@link #exeSrvId}
         */
        public ExecutionThreadLocalObj setExeSrvId(String exeSrvId) {
            this.exeSrvId = exeSrvId;
            return this;
        }

        /**
         * @return property value of {@link #exeNodeId}
         */
        public String getExeNodeId() {
            return exeNodeId;
        }

        /**
         * @param exeNodeId value to be assigned to property {@link #exeNodeId}
         */
        public ExecutionThreadLocalObj setExeNodeId(String exeNodeId) {
            this.exeNodeId = exeNodeId;
            return this;
        }

        /**
         * Getter method for property <tt>zoneName</tt>.
         *
         * @return property value of zoneName
         */
        public String getZoneName() {
            return zoneName;
        }

        /**
         * Setter method for property <tt>zoneName</tt>.
         *
         * @param zoneName  value to be assigned to property zoneName
         */
        public ExecutionThreadLocalObj setZoneName(String zoneName) {
            this.zoneName = zoneName;
            return this;
        }

        /**
         * Getter method for property <tt>schedulePointType</tt>.
         *
         * @return property value of schedulePointType
         */
        public String getSchedulePointType() {
            return schedulePointType;
        }

        /**
         * Setter method for property <tt>schedulePointType</tt>.
         *
         * @param schedulePointType value to be assigned to property schedulePointType
         */
        public ExecutionThreadLocalObj setSchedulePointType(String schedulePointType) {
            this.schedulePointType = schedulePointType;
            return this;
        }
    }
}

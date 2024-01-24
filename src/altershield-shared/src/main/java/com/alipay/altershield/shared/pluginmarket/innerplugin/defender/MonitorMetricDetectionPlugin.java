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
 * Ant Group
 * Copyright (c) 2004-2023 All Rights Reserved.
 */
package com.alipay.altershield.shared.pluginmarket.innerplugin.defender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.altershield.common.logger.Loggers;
import com.alipay.altershield.common.util.HttpUtils;
import com.alipay.altershield.framework.common.util.logger.AlterShieldLoggerManager;
import com.alipay.altershield.shared.pluginmarket.annotations.PluginAutowired;
import com.alipay.altershield.spi.defender.DefenderAsyncDetectPlugin;
import com.alipay.altershield.spi.defender.model.enums.DefenderStatusEnum;
import com.alipay.altershield.spi.defender.model.request.ChangeExecuteInfo;
import com.alipay.altershield.spi.defender.model.request.ChangeInfluenceInfo;
import com.alipay.altershield.spi.defender.model.request.DefenderDetectPluginRequest;
import com.alipay.altershield.spi.defender.model.result.DefenderDetectPluginResult;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Time series monitor metric abnormally detection plugin
 *
 * @author yhaoxuan
 * @version MonitorMetricDetectionPlugin.java, v 0.1 2023年11月30日 10:55 yhaoxuan
 */
public class MonitorMetricDetectionPlugin implements DefenderAsyncDetectPlugin {

    @PluginAutowired("defenderThreadPool")
    private ThreadPoolTaskExecutor defenderThreadPool;

    /**
     * Constants
     */
    private static final String VALUE = "value";
    private static final String NAMESPACE = "namespace";
    private static final String WORKLOAD_TYPE = "workloadType";
    private static final String WORKLOAD_NAME = "workloadName";
    private static final String SERIES_QUERY_TYPE = "workloadExternal";
    private static final String POINTS = "points";
    private static final String TIMESTAMP = "timestamp";
    private static final String DATA = "data";
    private static final String RESULT_CODE = "resultCode";
    private static final String VERDICT = "verdict";
    private static final String PASS = "PASS";
    private static final String FAIL = "FAIL";
    private static final String ALGORITHM_MESSAGE = "algorithmMessage";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String DETECT_ROUTER_TYPE = "detectRouterType";
    private static final String STATISTIC_MULTI_BY_ATOMIC = "statisticMultiByAtomic";

    private static final String QUERY_SERIES_DATA_URL = "/openapi/metric/query";
    private static final String ALGORITHM_DETECT_URL = "/api/check/batch_monitor_detect";
    private static final long DEFAULT_BACKTRACE_TIME = 30 * 60 * 1000;
    private static final long DEFAULT_QUERY_INTERVAL_TIME = 30 * 1000;

    private final String algorithmHost = "http://192.168.103.242:8083";

    private final String monitorClientHost = "http://192.168.123.218:8080";

    @Override
    public DefenderDetectPluginResult submitDetectTask(DefenderDetectPluginRequest request) {
        return DefenderDetectPluginResult.success();
    }

    @Override
    public DefenderDetectPluginResult retrieveDetectResult(DefenderDetectPluginRequest request) {
        DefenderDetectPluginResult result = paramCheck(request);
        if (result != null) {
            return result;
        }

        long checkTime = System.currentTimeMillis();
        Map<MetricFieldEnum, JSONArray> seriesDatas = queryTimeSeriesData(request, checkTime);
        if (CollectionUtils.isEmpty(seriesDatas)) {
            return DefenderDetectPluginResult.exception("Cannot detect with empty time series data");
        }
        long changeEndTime = request.getChangeExecuteInfo().getChangeFinishTime().getTime();
        return invokeAlgorithmDetection(request, changeEndTime, seriesDatas);
    }

    private Map<MetricFieldEnum, JSONArray> queryTimeSeriesData(DefenderDetectPluginRequest req, long checkEndTime) {
        ConcurrentHashMap<MetricFieldEnum, JSONArray> resultMap = new ConcurrentHashMap<>();
        CountDownLatch latch = new CountDownLatch(MetricFieldEnum.values().length);
        for (MetricFieldEnum item : MetricFieldEnum.values()) {
            QueryTimeSeriesDataRequest request = new QueryTimeSeriesDataRequest();
            request.setType(SERIES_QUERY_TYPE);

            long startTime = req.getChangeExecuteInfo().getChangeStartTime().getTime() - DEFAULT_BACKTRACE_TIME;
            request.setStart(startTime);
            request.setEnd(checkEndTime);
            request.setDuration(DEFAULT_QUERY_INTERVAL_TIME);

            ChangeExecuteInfo executeInfo = req.getChangeExecuteInfo();
            Map<String, Object> extInfo = executeInfo.getExtInfo();
            String namespace = String.valueOf(extInfo.getOrDefault(NAMESPACE, null));
            String workloadType = String.valueOf(extInfo.getOrDefault(WORKLOAD_TYPE, null));
            String workloadName = String.valueOf(extInfo.getOrDefault(WORKLOAD_NAME, null));
            if (namespace == null || workloadType == null || workloadName == null) {
                AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                        "queryTimeSeriesData", "The namespace or workload type or workload name cannot be null", req.getNodeId());
                return null;
            }

            WorkloadQueryDetail queryDetail = new WorkloadQueryDetail();
            queryDetail.setKind(workloadType);
            queryDetail.setNamespace(namespace);
            queryDetail.setName(workloadName);

            Metric metric = new Metric();
            metric.setName(item.getField());
            queryDetail.setMetric(metric);
            request.setWorkloadExternalQuery(queryDetail);

            defenderThreadPool.execute(() -> {
                try {
                    String response = HttpUtils.doPost(monitorClientHost + QUERY_SERIES_DATA_URL,
                            JSONObject.parseObject(JSONObject.toJSONString(request)), null);
                    if (StringUtils.isBlank(response)) {
                        AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                                "queryTimeSeriesData", "Query result is empty", req.getNodeId(), item.getField());
                    }

                    JSONObject result = JSON.parseArray(response).getJSONObject(0);
                    if (CollectionUtils.isEmpty(result)) {
                        AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                                "queryTimeSeriesData", "Query result is empty", req.getNodeId(), item.getField(), response);
                    }

                    if (!result.containsKey(POINTS)) {
                        AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                                "queryTimeSeriesData", "Invalid result", req.getNodeId(), item.getField(), response);
                    }
                    resultMap.put(item, result.getJSONArray(POINTS));
                } catch (Exception e) {
                    AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, e,
                            "MonitorMetricDetectionPlugin", "queryTimeSeriesData", "fail");
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();  // Wait for all tasks to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new RuntimeException("Thread was interrupted", e);
        }
        return resultMap;
    }

    public DefenderDetectPluginResult invokeAlgorithmDetection(DefenderDetectPluginRequest req, long checkEndTime,
                                                                Map<MetricFieldEnum, JSONArray> seriesDatas) {
        AlgorithmDetectRequest request = new AlgorithmDetectRequest();
        request.setChangeStart(req.getChangeExecuteInfo().getChangeStartTime().getTime());
        request.setChangeEnd(req.getChangeExecuteInfo().getChangeFinishTime().getTime());
        request.setPeriod(checkEndTime);
        ChangeExecuteInfo executeInfo = req.getChangeExecuteInfo();
        Map<String, Object> extInfo = executeInfo.getExtInfo();
        String workloadName = String.valueOf(extInfo.getOrDefault(WORKLOAD_NAME, null));
        if (workloadName == null) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                    "invokeAlgorithmDetection", "The workload name cannot be null", req.getNodeId());
            return DefenderDetectPluginResult.exception("The workload name cannot be null");
        }

        List<BaseKeySeries> baseKeySeriesList = new ArrayList<>(seriesDatas.size());
        for (Entry<MetricFieldEnum, JSONArray> entry : seriesDatas.entrySet()) {
            BaseKeySeries baseKeySeries = new BaseKeySeries();
            baseKeySeries.setDsId(entry.getKey().getMetric().getMetric());
            baseKeySeries.setField(entry.getKey().getField());
            baseKeySeries.setTagStr("app$=$" + workloadName);

            JSONArray timeSeries = entry.getValue();
            Map<Long, Float> series = new HashMap<>();
            for (int i = 0; i < timeSeries.size(); i++) {
                try {
                    JSONObject data = timeSeries.getJSONObject(i);
                    if (!data.containsKey(TIMESTAMP) || !data.containsKey(VALUE)) {
                        continue;
                    }
                    series.put(data.getLong(TIMESTAMP) * 1000, data.getFloatValue(VALUE));
                } catch (Exception e) {
                    AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                            "invokeAlgorithmDetection", "Parse time series data got an exception", req.getNodeId(), timeSeries);
                }
            }
            baseKeySeries.setSeries(series);
            baseKeySeriesList.add(baseKeySeries);
        }
        DetectSeries detectSeries = new DetectSeries();
        detectSeries.setKeySeriesList(baseKeySeriesList);
        request.setDetectSeries(detectSeries);

        Map<String, String> args = new HashMap<>();
        args.put(DETECT_ROUTER_TYPE, STATISTIC_MULTI_BY_ATOMIC);
        request.setBizAlgKargs(args);

        String response = HttpUtils.doPost(algorithmHost + ALGORITHM_DETECT_URL,
                JSONObject.parseObject(JSONObject.toJSONString(request)), null);
        if (StringUtils.isBlank(response)) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                    "invokeAlgorithmDetection", "Detection response is empty", req.getNodeId());
            return DefenderDetectPluginResult.exception("Detection response is empty");
        }

        JSONObject respJsn = JSON.parseObject(response);
        JSONObject result = respJsn.getJSONObject(DATA);

        if (result.getInteger(RESULT_CODE) != 0) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin",
                    "invokeAlgorithmDetection", "Invoke algorithm failed", req.getNodeId(), response);
            return DefenderDetectPluginResult.exception(result.getString(ERROR_MESSAGE));
        }

        String verdict = result.getString(VERDICT);
        DefenderDetectPluginResult rst = new DefenderDetectPluginResult();
        rst.setDefensed(true);
        rst.setDetectGroupId(req.getDetectGroupId());
        rst.setStatus(PASS.equalsIgnoreCase(verdict) ? DefenderStatusEnum.PASS : DefenderStatusEnum.FAIL);
        rst.setMsg(result.getString(ALGORITHM_MESSAGE));
        rst.setResultJson(response);
        return rst;
    }

    /**
     * Parameter check
     *
     * @param request detection request structure
     * @return return null when check pass, otherwise return detection result structure
     */
    private DefenderDetectPluginResult paramCheck(DefenderDetectPluginRequest request) {
        if (request == null) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin", "paramCheck",
                    "The detection request cannot be null");
            return DefenderDetectPluginResult.exception("The detection request cannot be null");
        }

        if (StringUtils.isBlank(request.getNodeId())) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin", "paramCheck",
                    "The change node id cannot be empty");
            return DefenderDetectPluginResult.exception("The change node id cannot be empty");
        }

        if (request.getChangeExecuteInfo() == null) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin", "paramCheck",
                    "The change execution information cannot be null", request.getNodeId());
            return DefenderDetectPluginResult.exception("The change execution information cannot be null");
        }

        if (request.getChangeExecuteInfo().getChangeStartTime() == null) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin", "paramCheck",
                    "The change start time cannot be null", request.getNodeId());
            return DefenderDetectPluginResult.exception("The change start time cannot be null");
        }

        if (request.getChangeExecuteInfo().getChangeFinishTime() == null) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin", "paramCheck",
                    "The change finish time cannot be null", request.getNodeId());
            return DefenderDetectPluginResult.exception("The change finish time cannot be null");
        }

        if (request.getChangeInfluenceInfo() == null) {
            AlterShieldLoggerManager.log("error", Loggers.DEFENDER_PLUGIN, "MonitorMetricDetectionPlugin", "paramCheck",
                    "The change influence information cannot be null", request.getNodeId());
            return DefenderDetectPluginResult.exception("The change influence information cannot be null");
        }

        return null;
    }

    @Data
    @ToString
    private class QueryTimeSeriesDataRequest {

        /**
         * query type
         * podMetric or workloadExternal
         */
        private String type;

        /**
         * workload type query detail
         */
        private WorkloadQueryDetail workloadExternalQuery;

        /**
         * Data queried start time, millisecond
         */
        private long start;

        /**
         * Data queried end time, millisecond
         */
        private long end;

        /**
         * Interval time, unit: millisecond
         * e.g. 10000
         */
        private long duration;
    }

    @Data
    @ToString
    private class WorkloadQueryDetail {

        /**
         * workload type
         */
        private String kind;

        /**
         * namespace
         */
        private String namespace;

        /**
         * The name of the target pod
         * Choose one from the labels field, when this field is specified, only the metrics of this pod are queried.
         */
        private String name;

        /**
         * metric to query
         */
        private Metric metric;
    }

    @Data
    @ToString
    private class Metric {

        /**
         * metric name
         */
        private String name;

        /**
         * label selector
         */
        private String selector;
    }

    @Data
    @ToString
    private class AlgorithmDetectRequest {

        /**
         * Change start time
         */
        private long changeStart;

        /**
         * Change finish time
         */
        private long changeEnd;

        /**
         * Recent time to detect
         */
        private long period;

        /**
         * Time series data to detect
         */
        private DetectSeries detectSeries;

        /**
         * Time series data to detect for long time
         */
        private DetectSeries longSeries;

        /**
         * [Optional] Algorithm configuration
         */
        private Map<String, String> bizAlgKargs;

        /**
         * [Optional] Data parameters are used to specify the detection of configuration data.
         */
        private Map<String, String> datrasourceKargs;
    }

    @Data
    @ToString
    private class DetectSeries {

        /**
         * time series data list
         */
        private List<BaseKeySeries> keySeriesList;
    }

    @Data
    @ToString
    private class BaseKeySeries {

        /**
         * time series data
         * key: timestamp, value: correspond time series value
         */
        private Map<Long, Float> series;

        /**
         * metric field
         * e.g. cost / fail
         */
        private String field;

        /**
         * monitor item name
         * e.g. app$=$appName1$&$pod$=$podName1
         */
        private String tagStr;

        /**
         * monitor datasource id
         */
        private String dsId;
    }

    private enum MetricEnum {
        SYSTEM_POD("systemPod"),
        JVM_GC_POD("jvmgcPod"),
        ;

        private final String metric;

        MetricEnum(String metric) {
            this.metric = metric;
        }

        /**
         * Getter method for property <tt>metric</tt>.
         *
         * @return property value of metric
         */
        public String getMetric() {
            return metric;
        }
    }

    public enum MetricFieldEnum {
        CPU_UTIL("cpu_util", MetricEnum.SYSTEM_POD),
        MEM_UTIL("mem_util", MetricEnum.SYSTEM_POD),
//        LOAD_LOAD1("load_load1", MetricEnum.SYSTEM_POD),
//        LOAD_LOAD5("load_load5", MetricEnum.SYSTEM_POD),
//        LOAD_LOAD15("load_load15", MetricEnum.SYSTEM_POD),
//        FGC_COUNT("fgc_count", MetricEnum.JVM_GC_POD),
//        FGC_TIME("fgc_time", MetricEnum.JVM_GC_POD),
//        YGC_COUNT("ygc_count", MetricEnum.JVM_GC_POD),
//        YGC_TIME("ygc_time", MetricEnum.JVM_GC_POD),
        ;

        private final String field;
        private final MetricEnum metric;

        MetricFieldEnum(String field,
                        MetricEnum metric) {
            this.field = field;
            this.metric = metric;
        }

        /**
         * Getter method for property <tt>field</tt>.
         *
         * @return property value of field
         */
        public String getField() {
            return field;
        }

        /**
         * Getter method for property <tt>metric</tt>.
         *
         * @return property value of metric
         */
        public MetricEnum getMetric() {
            return metric;
        }
    }
}
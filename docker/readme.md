## 背景
AlterShield基本接入流程。
一个变更平台想要接入AlterShield变更管控主要分为两个部分。
1. 确定接入场景
2. 在AlterShield注册变更场景、变更平台、变更类型等元数据
3. 按照OCMS SDK接入变更
```bash
INSERT INTO altershield.altershield_sequence
(name, gmt_create, gmt_modified, value, min_value, max_value, step)
VALUES ('seq_default', '2023-11-30 20:40:59', '2023-12-04 16:17:04',
14696907001, 0, 2821109907455, 1000);
```
+ 2、创建一个platform-接入平台
```bash
curl --location '{{yourServerUrl}}/meta/platform/create' \
--header 'Content-Type: application/json' \
--data '{
    "platformName":"kubernetes",
    "owner":"system",
    "token":"altershield-operator"
}'
```
+ 3、创建变更类型
```bash
curl --location '{{yourServerUrl}}/meta/change_type/insert' \
--header 'Content-Type: application/json' \
--data '{
    "type":"pass.pod",
    "name":"pod",
    "description":"pod",
    "category":"contentType"
}'
curl --location '{{yourServerUrl}}/meta/change_type/insert' \
--header 'Content-Type: application/json' \
--data '{
    "type":"pass.pod",
    "name":"pod",
    "description":"pod",
    "category":"effectiveTargetType"
}'

curl --location '{{yourServerUrl}}/meta/change_type/insert' \
--header 'Content-Type: application/json' \
--data '{
    "type":"pass.pod",
    "name":"pod",
    "description":"pod",
    "category":"changeTargetType"
}'
```
+ 4、创建变更场景
```bash
# step 1
curl --location '{{yourServerUrl}}/meta/change_scene/create/standard' \
--header 'Content-Type: application/json' \
--data '{
    "name": "kubernetes分批发布",
    "changeSceneKey": "com.alipay.altershield.kubernetes.rollingupdate",
    "platformName": "kubernetes",
    "changeTargetType": "paas.pod",
    "effectiveTargetType": "paas.pod",
    "changeContentType": "paas.pod",
    "tenantCode": "default",
    "serverTenantCode": "default",
    "owner": "system",
    "generation": "G2",
    "changeEffectiveConfig": {
        "changeGrayModeType": "PIPELINE",
        "changeGrayEnvType": "ENV",
        "changeProgress": {
            "enableBatchStep": true,
            "enableOrderStep": true
        }
    }
}'

# step2
curl --location '{{yourServerUrl}}/meta/change_scene/create2/standard' \
--header 'Content-Type: application/json' \
--data '{
    "callbackFlag": "yes",
    "callbackConfig": {
        "callbackConfig": {
            "DEFAULT": "{{yourOperatorServerUrl}}/openapi/altershield/callback"
        }
    },
    "id": "20231204012470200000000000XX06R25Q0V",
    "defenceConfigs": [
        {
            "id": "20230315012470210000000000XX00DMMCZM",
            "interfaceInfo": "ChangeClient.submitChangeStartNotify(ChangeExecOrderStartNotifyRequest)",
            "changeStepName": "kubernetes滚动发布变更工单",
            "changeKey": "com.alipay.alitershield.kubernetes.rollingupdate",
            "preCheckTimeout": 1000,
            "postCheckTimeout": 10000,
            "interfaceType": "async",
            "displayOnly": false,
            "canConfig": true
        },
        {
            "id": "20231204012470210000000000XX01QL2U6L",
            "interfaceInfo": "ChangeClient.submitChangeStartNotify(ChangeExecBatchStartNotifyRequest)",
            "changeStepName": "kubernetes滚动发布变更分批",
            "changeKey": "com.alipay.alitershield.kubernetes.rollingupdate._batch",
            "preCheckTimeout": 1000,
            "postCheckTimeout": 10000,
            "interfaceType": "async",
            "displayOnly": false,
            "canConfig": true
        }
    ]
}'
```
+ 5、创建变更工单
+ 6、提交变更工单前置防御
+ 7、提交变更批次前置防御
+ 8、提交变更批次后置防御
+ 9、重复步骤7&8（多个批次）
+ 10、提交变更工单后置防御
<br />
<div align="center">
  <!-- <a href="https://github.com/traas-stack/altershield">
    <img src="docs/logo/logo.png" alt="Logo" width="80" height="80"/>
  </a> -->

<h1 align="center">AlterShield</h1>

  <p align="center">
    开源变更管控信息技术标准
    <br />
    <a href="https://traas-stack.github.io/altershield-docs/"><strong>查看文档 »</strong></a>
    <br />
    <br />
    <a href="https://github.com/traas-stack/altershield">源码</a>
    ·
    <a href="https://github.com/traas-stack/altershield/issues/new?template=bug_report.md">提交Bug</a>
    ·
    <a href="https://github.com/traas-stack/altershield/blob/main/README.md">English</a>
  </p>
</div>

<p align="center">
  <a href="https://altershield.slack.com/"><img src="https://img.shields.io/badge/slack-AlterShield-0abd59?logo=slack" alt="slack" /></a>
  <a href="https://github.com/traas-stack/AlterShield"><img src="https://img.shields.io/github/stars/traas-stack/AlterShield?style=flat-square"></a>
  <a href="https://github.com/traas-stack/AlterShield/issues"><img src="https://img.shields.io/github/issues/traas-stack/AlterShield"></a>
  <a href=""><img src="https://img.shields.io/badge/license-Apache--2.0-green.svg"></a>
</p>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>目录</summary>
  <ol>
    <li><a href="#项目介绍">项目介绍</a></li>
    <li><a href="#快速开始">快速开始</a></li>
    <li><a href="#RoadMap">RoadMap</a></li>
    <li><a href="#共建">共建</a></li>
    <li><a href="#开源许可">开源许可</a></li>
    <li><a href="#联系我们">联系我们</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## 项目介绍
AlterShield 是一款能够有效进行变更风险防控，预防变更引发生产环境故障的变更管控解决方案。[什么是变更和变更管控](https://traas-stack.github.io/altershield-docs/zh-CN/introduction/what-is-change-management)

它是蚂蚁集团内部变更管控平台 OpsCloud 的开源版本。它凝聚了蚂蚁集团在公司大规模变更下积累的变更管控技术、产品以及方法论。在复杂业务场景下，提供变更过程中的生命周期感知、变更异常识别（变更防御）、变更熔断能力。

AlterShield 在此背景下，提出了变更管控技术协议标准（Open Change Management Specification，即OCMS），帮助不同业务、技术背景下的变更，进行统一的感知收集，使得后续的变更防御可以基于一套统一的结构信息来进行，不必为每种变更进行深度定制，极大的降低了研发成本。此协议标准在蚂蚁集团内部运行了长达6年之久，管控了数十亿级别的变更。

### 什么是Open Change Management Specification

Open Change Management Specification (OCMS) 在目前阶段包含了以下内容：
- 一套变更信息模型，定义了一种变更想要进行管控，需要具备的信息结构，该结构用于后续的变更信息感知、变更防御、变更度量等流程
- 一套变更接入的SDK，以前后置切面的形式，在每个变更步骤生效的前、后进行扩展，进行变更防御的异常检测
- 一套基于插件的变更防御框架，将不同场景下需要的异常识别能力（如：监控告警检测、日志异常检测等）以插件的形式实现并集成到框架中，在SDK中的前后置切面中调度执行

如果您想要了解更多，可以访问我们的文档库获取更多信息模型及SDK的定义：
- [变更信息模型定义](https://traas-stack.github.io/altershield-docs/zh-CN/open-change-management-specification/change-model)
- [变更接入SDK](https://traas-stack.github.io/altershield-docs/zh-CN/open-change-management-specification/change-access-sdk)

### 我们的目标及后续的计划

#### 已支持
- 上述 Open Change Management Specification 所提到的内容，变更防御框架目前还在调整中，会在下个版本开源出来
- 云原生Kubernetes Deployment下的代码发布部署的变更管控解决方案

#### 规划中
- 一套插件化的变更风险分析框架，帮助风险评估人员在变更提单阶段，可视化关注到变更危险程度、可能出现的风险信息以及配套的风险防控充分度，从而降低人工分析成本。
- 一套插件化的变更风险防控框架，使风险防控人员可以将在日常工作中积累的经验通过代码的形式沉淀下来。同时，框架中也沉淀了我们之前在实际业务中复用度较高的防控能力及在变更管控场景下风险可观测性的信息标准。

#### 长远目标
- 基于风险分析和风险防控，最终我们提供了一套变更无人值守的框架方案，来帮助业务释放更多的人工执行、观测、恢复成本，提升研发效率。

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- QUICK START -->
## 快速开始

### 项目工程结构
我们的工程代码主要在源代码的src目录下：
- altershield-framework-sdk：前文所提到的变更信息模型及接入SDK，您可以在这里找到相关的信息模型定义及HTTP协议下的接入方案。
    - 其中变更信息模型在core/change/facade/request目录下；HTTP SDK在sdk/change/client目录下
- altershield-defender：前文所提到的变更防御框架，目前暂无内容，后续变更防御相关内容会陆续开源到此目录下

云原生下的Operator管控机制，请跳转至 [altershield-opreator](https://github.com/traas-stack/altershield-operator) 项目查看


### 在使用之前，您还需要了解：
1. AlterShield 不是变更执行系统，本身不具备变更执行能力。
2. 作为一套技术框架，它定义了变更接入、风险分析、风险防控的标准。但在实际使用之前，还需要***把这套技术框架，嵌入到变更执行平台中，做切面管控***。
3. 即将推出变更管控提供的风险分析框架，需要结合您的CMDB（或其他元数据存储）来获取数据推导变更影响面。
4. 变更管控即将推出通用的风险防控能力。除此之外，如果您想更贴合您的业务背景解决变更风险问题，需要自定义风险防控插件来沉淀您的经验。

_更多信息，请前往 [AlterShield文档](https://traas-stack.github.io/altershield-docs/zh-CN/)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## RoadMap
- [ ] 开源变更防御框架完整内容
- [ ] 开源基于 Prometheus 的监控指标数据获取及异常检测插件
- [ ] 增加安装使用说明
- [ ] 增加变更平台接入SDK进行切面管控的案例
- [ ] 增加通用的变更防御插件，如：变更窗口管控、变更参数校验等

我们会持续增加RoadMap。

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTRIBUTING -->
## 共建

贡献是使开源社区成为一个学习、启发和创造的令人惊奇的地方的原因。您所做的任何贡献都将受到极大的赞赏。
如果您有任何建议可以使它变得更好，请分支该存储库并创建拉取请求。您也可以打开一个带有“增强”标签的问题。
别忘了给项目点个赞！再次感谢！



1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## 开源许可

根据Apache2.0许可证分发。更多信息请查看 LICENSE。

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact
- 邮箱地址: traas_stack@antgroup.com / altershield.io@gmail.com
- 钉钉群 [二维码](./docs/dingtalk.png)
- 微信公众号 [二维码](./docs/wechat.jpg)
- <a href="https://altershield.slack.com/"><img src="https://img.shields.io/badge/slack-AlterShield-0abd59?logo=slack" alt="slack" /></a>




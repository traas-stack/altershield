<br />
<div align="center">
  <!-- <a href="https://github.com/traas-stack/altershield">
    <img src="docs/logo/logo.png" alt="Logo" width="80" height="80"/>
  </a> -->

<h1 align="center">AlterShield</h1>

  <p align="center">
    变更风险防控平台，有效防控变更故障
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
如果您想了解更多关于 AlterShield 在变更管控领域的实践，请阅读 [文章]()

AlterShield 是一款能够有效进行变更风险防控，预防变更引发生产环境故障的变更管控解决方案。

它是蚂蚁集团内部变更管控平台 OpsCloud 的开源版本。它凝聚了蚂蚁集团在公司大规模变更下积累的变更管控技术、产品以及方法论。在复杂业务场景下，提供变更过程中的生命周期感知、变更防御、变更熔断能力。

AlterShield 提出了变更管控信息技术协议（Open Change Management Specification，即OCMS），帮助不同业务、技术背景下的变更，进行统一的感知收集，使得后续的变更防御可以基于一套统一的结构信息来进行，不必为每种变更进行深度定制，极大的降低了研发成本。

### 什么是Open Change Management Specification

Open Change Management Specification (OCMS) 是 AlterShield 为了进行变更信息定义、变更统一的感知和管控而制定的一份信息技术协议。 此协议基于蚂蚁集团的上千种变更场景总结归纳而来，非常初期的一个版本，这部分也欢迎大家来一起演进。它在目前阶段包含了以下内容：
- 一套变更信息模型，定义了一种变更想要进行管控，需要具备的信息结构，该结构用于后续的变更信息感知、变更防御、变更度量等流程
- 一套变更接入的SDK，以前后置切面的形式，在每个变更步骤生效的前、后进行扩展，进行变更防御的异常检测

如果您想要了解更多，可以访问我们的文档库获取更多信息模型及SDK的定义：
- [变更信息模型定义](https://traas-stack.github.io/altershield-docs/zh-CN/open-change-management-specification/change-model)
- [变更接入SDK](https://traas-stack.github.io/altershield-docs/zh-CN/open-change-management-specification/change-access-sdk)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- QUICK START -->
## 快速开始

目前项目还处于0.1版本，后续我们会完善更多"快速开始"的内容。

### 项目工程结构
我们的工程代码主要在源代码的src目录下：
- altershield-framework-sdk：前文所提到的 OCMS SDK 相关内容，您可以在这里找到相关的信息模型定义及HTTP协议下的接入方案。
    - 其中变更信息模型在core/change/facade/request目录下；HTTP SDK在sdk/change/client目录下
- altershield-defender：前文所提到的变更防御框架，目前暂无内容，后续变更防御相关内容会陆续开源到此目录下

云原生下的Operator管控机制，请跳转至 [altershield-opreator](https://github.com/traas-stack/altershield-operator) 项目查看


### 在使用之前，您还需要了解：
1. AlterShield 不是变更执行系统，本身不具备变更执行能力。
2. 作为一套技术框架，它定义了变更接入、风险分析、风险防控的标准。但在实际使用之前，还需要***把这套技术框架，嵌入到变更执行平台中，做切面管控***。
3. 后续计划开放变更防御框架。如果您想更贴合您的业务背景解决变更风险问题，需要在防御框架中扩展Plugin或SPI的方式来沉淀您的经验。
4. 后续计划推出变更管控提供的风险分析框架，需要结合您的CMDB（或其他元数据存储）来获取数据推导变更影响面。

_更多信息，请前往 [AlterShield文档](https://traas-stack.github.io/altershield-docs/zh-CN/)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## RoadMap
- [ ] 补充更多关于"快速开始"和"Q&A"相关内容。
- [ ] 在项目中增加变更平台接入 OCMS SDK 进行变更管控的案例。
- [ ] 开源完整的 Defender 模块：包含防御框架、防御能力及开放扩展部分。
- [ ] 开源完整的 Analyser 模块：包含分析框架、影响面分析、风险分析、可观测性分析及变更分级部分。
- [ ] 完善可观测性异常检测生态：集成更多的开源监控工具，提供异常检测能力。
- [ ] 开放独立的防御校验能力：使防御框架独立于 OCMS SDK，无需接入改造，即可进行变更防控校验。

我们会持续更新RoadMap。

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTRIBUTING -->
## 社区建设

作为开源社区，我们欢迎各种形式的贡献，您可以参与到社区的共建的形式包括但不限于：
- 错别字修正：帮助我们指正文档中的错误。 
- 文档翻译：帮助我们将文档翻译成您国家的语言，包含英文。
- 问题及案例探讨：您公司中的变更故障案例，脱敏后可参与讨论，一期探讨解决方案。 
- Bug提交：帮助我们指出 AlterShield 中逻辑错误的地方。 
- 新功能场景探讨：任何 AlterShield 还不具备的变更领域功能，都可以一起讨论。 
- 完善 OCMS 协议：目前 OCMS 开源还处于0.1版本，如果在您的场景下有不能适配的情况，您可以直接参与讨论及扩充。 
- 对接更多监控工具：您可以将您所使用的监控工具对接到 AlterShield 提供的可观测性防御能力中，扩展 AlterShield 的检测能力范围。 
- 沉淀您的变更防御专家经验：您可以以Plugin、SPI扩展的形式，将您的变更防御专家经验沉淀到 AlterShield 中。

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




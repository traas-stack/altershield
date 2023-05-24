<br />
<div align="center">
  <!-- <a href="https://github.com/traas-stack/altershield">
    <img src="docs/logo/logo.png" alt="Logo" width="80" height="80"/>
  </a> -->

<h1 align="center">AlterShield</h1>

  <p align="center">
    开放的变更管控规范
    <br />
    <a href="https://traas-stack.github.io/altershield-docs/"><strong>查看文档 »</strong></a>
    <br />
    <br />
    <a href="https://github.com/traas-stack/altershield">源码</a>
    ·
    <a href="https://github.com/traas-stack/altershield/issues/new?template=bug_report.md">提交Bug</a>
    ·
    <a href="https://github.com/traas-stack/altershield/blob/main/README-CN.md">中文</a>
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
    <li>
      <a href="#about-the-project">关于本项目</a>
      <ul>
        <li><a href="#what-dose-it-provide">提供了什么</a></li>
      </ul>
    </li>
    <li><a href="#usage">使用说明</a></li>
    <li><a href="#contributing">共建</a></li>
    <li><a href="#license">开源许可</a></li>
    <li><a href="#contact">联系我们</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## 什么是变更管控

Open Change Management Specification (OCMS)是一套可适用于SRE和DevOps模式的变更管理技术标准。OCMS以数据驱动的方式，提供了普适性的变更管控方案，可整合来自不同部门或企业的变更数据。OCMS通过整合标准化，帮助用户实现对变更全生命周期的感知、干预、风险分析和风险防控等能力，从而降低变更造成的业务影响。OCMS旨在为信息技术相关企业提供一种高效的变更管理解决方案，并帮助用户在SRE和DevOps模式下优化系统表现，提高可靠性和稳定性。

在此之前，您可能还需要了解： [什么是变更管控](https://traas-stack.github.io/altershield-docs/zh-CN/introduction/what-is-change-management)。

## 它提供了什么


### 已推行
- 一套变更信息模型，可将不同业务或不同技术背景下的变更进行统一的结构化定义，是后续做变更感知、干预、风险分析和风险防控的前提。
- 一套分代际的变更生命周期接入框架，打入上游变更执行系统的各个阶段，为变更管控提供切面入口。同时，分代际的接入标准允许管理人员根据不同变更场景制定不同的管控标准，提升灵活性。

### 规划中
- 一套插件化的变更风险分析框架，帮助风险评估人员在变更提单阶段，可视化关注到变更危险程度、可能出现的风险信息以及配套的风险防控充分度，从而降低人工分析成本。
- 一套插件化的变更风险防控框架，使风险防控人员可以将在日常工作中积累的经验通过代码的形式沉淀下来。同时，框架中也沉淀了我们之前在实际业务中复用度较高的防控能力及在变更管控场景下风险可观测性的信息标准。

### 终局
基于风险分析和风险防控，最终我们提供了一套变更无人值守的框架方案，来帮助业务释放更多的人工执行、观测、恢复成本，提升研发效率。


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## 在使用之前，您还需要了解：
1. 它不是变更执行系统，本身不具备变更执行能力。
2. 作为一套技术框架，它定义了变更接入、风险分析、风险防控的标准。但在实际使用之前，还需要***把这套技术框架，嵌入到变更执行平台中，做切面管控***。
3. 即将推出变更管控提供的风险分析框架，需要结合您的CMDB（或其他元数据存储）来获取数据推导变更影响面。
4. 变更管控即将推出通用的风险防控能力。除此之外，如果您想更贴合您的业务背景解决变更风险问题，需要自定义风险防控插件来沉淀您的经验。

_更多信息，请前往 [AlterShield文档](https://traas-stack.github.io/altershield-docs/zh-CN/)_

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




<br />
<div align="center">
  <!-- <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="docs/logo/logo.png" alt="Logo" width="80" height="80"/>
  </a> -->

<h1 align="center">AlterShield</h1>

  <p align="center">
    Open Change Management Specification
    <br />
    <a href="https://traas-stack.github.io/altershield-docs/"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/traas-stack/altershield">View Source</a>
    ·
    <a href="https://github.com/traas-stack/altershield/issues/new?template=bug_report.md">Report Bug</a>
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
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#About The Project">About The Project</a></li>
    <li><a href="#Quick Start">Quick Start</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

If you want to learn more about AlterShield's practice in the field of change management, please read [article]().

AlterShield is a change management solution that effectively prevents change-related risks and production environment failures. 

It is an open-source version of Ant Group's internal change management platform OpsCloud. It incorporates Ant Group's accumulated change management technology, products, and methodologies in large-scale corporate changes. In complex business scenarios, it provides lifecycle awareness, change defense, and change interruption capabilities during the change process.

AlterShield proposes the Open Change Management Specification (OCMS), which helps to unify the perception and collection of changes across different business and technical backgrounds, enabling subsequent change defense based on a unified structured information set without the need for deep customization for each type of change, greatly reducing development costs.

### What is Open Change Management Specification

Open Change Management Specification (OCMS) is an information technology agreement developed by AlterShield for defining change information and unifying perception and control of changes. This protocol is based on the summary and induction of thousands of change scenarios in Ant Group and is still in its early stages. This part is also open to everyone for further development. At this stage, it includes the following content: 

- A set of change information models defining the information structure required for change control, which is used in subsequent processes such as change information sensing, change defense, and change measurement
- A set of change access SDKs that extend a pre- or post-step in each change step for abnormal detection, thus ensuring effective change defense.

If you would like to learn more, please visit our documentation library for more information on the definition of the Change Model and SDK:
- [Definition of the Change Model](https://traas-stack.github.io/altershield-docs/open-change-management-specification/change-model/)
- [Change Access SDK](https://traas-stack.github.io/altershield-docs/zh-CN/open-change-management-specification/change-access-sdk)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- QUICK START -->
## Quick Start

The OCMS open-source standard is currently at version 0.1. If it cannot address your specific scenarios, you are welcome to participate in discussions to help improve and extend it.

### Project Structure
Our project code is mainly located in the "src" directory of the source code.

- altershield-framework-sdk: The previously mentioned change information model and access SDK can be found here. You can also find the relevant information model definitions and access solutions based on HTTP protocol.
  - The Change Model is located in the "core/change/facade/request" directory, and the HTTP SDK is located in the "sdk/change/client" directory.
- altershield-defender: The previously mentioned change defense framework currently has no content. However, related content regarding change defense will be gradually open-sourced in this directory later.

For the Operator control mechanism in cloud-native environments, please refer to the [altershield-operator](https://github.com/traas-stack/altershield-operator) project.

### Before using, you also need to know:
1. AlterShield is not a change execution system and does not have the ability to execute changes.
2. As a technical framework, it defines the standards for change access, risk analysis, and risk prevention. However, before actual use, this technical framework needs to be embedded in the change execution platform for aspect control.
3. A change control risk analysis framework will soon be launched, which needs to be combined with your CMDB (or other metadata storage) to obtain data to infer the scope of change impact.
4. A general risk prevention capability will soon be launched for change control. In addition, if you want to solve change risk issues that are more tailored to your business background, you need to customize risk prevention plugins to accumulate your experience.

_For more information, please refer to the [Documentation](https://traas-stack.github.io/altershield-docs/)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap
- [ ] Supplement more "quick start" and "Q&A" related content.
- [ ] Add a case of integrating the OCMS SDK for change management in the project.
- [ ] Open-source the complete Defender module: including defense framework, defense capabilities, and open extension parts.
- [ ] Open-source the complete Analyser module: including analysis framework, impact analysis, risk analysis, observability analysis, and change grading parts.
- [ ] Improve the observability anomaly detection ecosystem: integrate more open-source monitoring tools to provide anomaly detection capabilities.
- [ ] Open up independent defense verification capabilities: make the defense framework independent of the OCMS SDK, without the need for integration and transformation, to perform change defense verification.

We will continue to add to the RoadMap.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

As an open-source community, we welcome all forms of contribution, including but not limited to:
- Correction of typos: help us correct any errors in the documentation.
- Document translation: help us translate the documentation into your native language, including English.
- Discussion of issues and cases: discuss change failure cases in your company, which can be desensitized and participate in discussions to find solutions.
- Bug submission: help us identify and address any logical issues in AlterShield.
- Discussion of new feature scenarios: any change-related functions that AlterShield currently lacks can be discussed together.
- Improvement of the OCMS agreement: currently, the OCMS open-source is still at version 0.1. If there are situations that cannot be adapted in your scenario, you can directly participate in discussions and extensions.
- Integration of more monitoring tools: you can integrate the monitoring tools you use into the observability defense capabilities provided by AlterShield to expand AlterShield's detection capabilities.
- Share your change defense expert experience: you can use the form of Plugin or SPI extension to share your change defense expert experience with AlterShield.

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- LICENSE -->
## License

Distributed under the Apache2.0 License. See `LICENSE` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact
- Contact us via Email: traas_stack@antgroup.com / altershield.io@gmail.com
- Ding Talk Group [QR code](./docs/dingtalk.png)
- WeChat Official Account [QR code](./docs/wechat.jpg)
- <a href="https://altershield.slack.com/"><img src="https://img.shields.io/badge/slack-AlterShield-0abd59?logo=slack" alt="slack" /></a>




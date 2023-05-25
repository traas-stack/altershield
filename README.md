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

AlterShield is a change control solution that effectively manages change risks and prevents production environment failures caused by changes. [What is change and change management](https://traas-stack.github.io/altershield-docs/introduction/what-is-change-management)

It is an open-source version of the internal change control platform OpsCloud of Ant Group. It incorporates the change control technologies, products, and methodologies accumulated by Ant Group during large-scale changes. It provides lifecycle awareness, change anomaly detection (change defense), and change circuit-breaking capabilities in complex business scenarios. 

Under this background, AlterShield proposes the Open Change Management Specification (OCMS) for change control technology protocols to help unify the sensing and collection of changes from different business and technical backgrounds. This enables subsequent change defense to be based on a set of unified structural information, making it unnecessary to deeply customize each change, greatly reducing R&D costs. This protocol standard has been running internally at Ant Group for up to 6 years, managing billions of changes.

### What is Open Change Management Specification
Open Change Management Specification (OCMS) currently includes the following:

- A set of change information models defining the information structure required for change control, which is used in subsequent processes such as change information sensing, change defense, and change measurement
- A set of change access SDKs that extend a pre- or post-step in each change step for abnormal detection, thus ensuring effective change defense.
- A plugin-based change defense framework that integrates different abnormal recognition capabilities required in different scenarios (such as monitoring alert detection, log abnormality detection, etc.) in the form of plugins and schedules them to execute in the pre- or post-step in the SDK.

If you would like to learn more, please visit our documentation library for more information on the definition of the Change Model and SDK:
- [Definition of the Change Model](https://traas-stack.github.io/altershield-docs/open-change-management-specification/change-model/)
- [Change Access SDK](https://traas-stack.github.io/altershield-docs/zh-CN/open-change-management-specification/change-access-sdk)

### Our Goals and Future Plans

#### Currently Supported
- The contents mentioned in the Open Change Management Specification are currently supported. The change defense framework is still being adjusted and will be open-sourced in the next version. 
- Change management solutions for code deployment under cloud-native Kubernetes.

#### Planned
- A plugin-based change risk analysis framework that helps risk evaluators visualize the degree of change danger, potential risk information, and the adequacy of supporting risk prevention and control measures during the change request stage, thereby reducing manual analysis costs.
- A plugin-based change risk prevention and control framework that enables risk prevention and control personnel to codify their accumulated experience in daily work. The framework also codifies the risk observability information standards for risk prevention and control capabilities that we have high reuse rates for in actual business scenarios.

#### Long-term Goals
- Based on risk analysis and risk prevention and control, we ultimately provide a framework solution for unmanned change management to help businesses release more human execution, observation, and restoration costs and improve research and development efficiency.


<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- QUICK START -->
## Quick Start

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
- [ ] Complete content of open source change defense framework
- [ ] Open source code for monitoring metric data acquisition and anomaly detection plugin based on Prometheus
- [ ] Add installation and usage instructions
- [ ] Add case study for integrating change platform access SDK to achieve aspect control
- [ ] Add universal change defense plugins such as change window control and change parameter validation

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

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




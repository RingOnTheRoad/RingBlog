

# RingBlog

一个基于Vue+SSM的简单博客后台管理系统

<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />

## 目录

- [上手指南](#上手指南)
  - [开发前的配置要求](#开发前的配置要求)
  - [安装步骤](#安装步骤)
- [文件目录说明](#文件目录说明)
- [部署](#部署)
- [使用到的框架](#使用到的框架)
- [预览](#预览)
- [版本控制](#版本控制)
- [作者](#作者)
- [鸣谢](#鸣谢)

### 上手指南

克隆本仓库的两文件,ring-blog-ssm&ring-blog-vue

###### 开发前的配置要求

1. Java1.8
2. Node.js v20.11.1
3. 确保全局安装pnpm

###### **安装步骤**

1. Clone the repo

```sh
git clone https://github.com/RingOnTheRoad/RingBlog.git
```

### 文件目录说明
```
ring-blog-vue
├─.husky
│  └─_
├─.vscode
├─public
└─src
    ├─api
    ├─assets
    ├─router
    ├─stores
    │  └─modules
    ├─utils
    └─views
        ├─blog
        │  └─components
        ├─index
        ├─login
        └─user
            └─components
```

```
ring-blog-ssm
├─.idea
│  ├─dictionaries
│  ├─httpRequests
│  └─inspectionProfiles
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─ring
│  │  │          └─ringblogbackend
│  │  │              ├─config
│  │  │              ├─controller
│  │  │              ├─dao
│  │  │              ├─domain
│  │  │              ├─serivce
│  │  │              │  └─impl
│  │  │              └─utils
│  │  └─resources
│  │      └─static
│  └─test
│      └─java
│          └─com
│              └─ring
│                  └─ringblogbackend
└─target
    ├─classes
    │  └─com
    │      └─ring
    │          └─ringblogbackend
    │              ├─config
    │              ├─controller
    │              ├─dao
    │              ├─domain
    │              ├─serivce
    │              │  └─impl
    │              └─utils
    ├─generated-sources
    │  └─annotations
    ├─generated-test-sources
    │  └─test-annotations
    └─test-classes
        └─com
            └─ring
                └─ringblogbackend
```

### 部署

ssm:RingBlobBackEndApplication

vue:pnpm i/pnpm dev

### 使用到的框架

- [Spring](https://spring.io/)
- [MybatisPlus](https://baomidou.com/)
- [Vue](https://vuejs.org/)
- [ElementPlus](https://element-plus.gitee.io/zh-CN/)

#### 预览

![login](https://github.com/RingOnTheRoad/RingBlog/blob/main/imgs/login.png)

![cate](https://github.com/RingOnTheRoad/RingBlog/blob/main/imgs/cate.png)

![mag](https://github.com/RingOnTheRoad/RingBlog/blob/main/imgs/mag.png)

![my](https://github.com/RingOnTheRoad/RingBlog/blob/main/imgs/my.png)

### 版本控制

该项目使用Git进行版本管理。您可以在repository参看当前可用版本。

### 作者

Ring

bilibili:风带铃音

### 版权说明

该项目签署了MIT授权许可，详情请参阅 [LICENSE.txt](https://github.com/RingOnTheRoad/RingBlog/blob/main/LICENSE.txt)

### 鸣谢


- [Img Shields](https://shields.io)
- [GitHub Pages](https://pages.github.com)
- [Best_README_template](https://github.com/shaojintian/Best_README_template)

<!-- links -->

[your-project-path]:RingOnTheRoad/RingBlog
[contributors-shield]: https://img.shields.io/github/contributors/RingOnTheRoad/RingBlog.svg?style=flat-square
[contributors-url]: https://github.com/RingOnTheRoad/RingBlog/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/RingOnTheRoad/RingBlog.svg?style=flat-square
[forks-url]: https://github.com/RingOnTheRoad/RingBlog/network/members
[stars-shield]: https://img.shields.io/github/stars/RingOnTheRoad/RingBlog.svg?style=flat-square
[stars-url]: https://github.com/RingOnTheRoad/RingBlog/stargazers
[issues-shield]: https://img.shields.io/github/issues/RingOnTheRoad/RingBlog.svg?style=flat-square
[issues-url]: https://img.shields.io/github/issues/RingOnTheRoad/RingBlog.svg
[license-shield]: https://img.shields.io/github/license/RingOnTheRoad/RingBlog.svg?style=flat-square
[license-url]: https://github.com/RingOnTheRoad/RingBlog/blob/main/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/RingOnTheRoad


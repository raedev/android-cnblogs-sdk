<img src="./doc/images/logo.jpg" alt="logo" width="100%"/>

博客园网站Android SDK，提供[https://www.cnblogs.com](https://www.cnblogs.com) 网站大部分数据接口。通过该接口可以自行实现一个博客类客户端。

该接口应用于开源的 [博客园Android客户端](https://github.com/raedev/android-cnblogs) 。

## 运行环境

- 开发环境：`Android Studio 2021.1.1 +`
- Java版本：`JDK11`
- Gradle版本： `gradle-7.3`

## 项目说明

> 接口数据获取方式

- 1、通过对官方网站类爬虫模式抓取网页`html`代码进行数据解析。接口核心类为：`WebApiProvider`
- 2、通过官网开发接口[https://api.cnblogs.com/](https://api.cnblogs.com/) OpenAPI方式访问。当前`OpenApiProvider`
  接口未实现，大部分数据通过第一种方式已经获取得到。有兴趣的同学可以自行`fork`下来实现。

## 项目架构说明

## 技术选型

- `Kotlin` + `MVVM`
- `Retrofit + OkHttp3 + Coroutines`
- `HtmlParser + JavaCookie`

## 开源协议（License）

遵守`Apache2.0` 开源协议，完全免费使用。

```text
Copyright 2023 RAE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```





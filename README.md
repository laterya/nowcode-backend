<h1 align="center">NowCode 综合平台</h1>
<div align="center">
<a target="_blank" href="https://github.com/laterya/nowcode-backend">
    <img alt="" src="https://github.com/laterya/nowcode-backend/badge/star.svg?theme=gvp"/>
</a>
<a target="_blank" href="https://github.com/laterya/nowcode-backend">
    <img alt="" src="https://img.shields.io/github/stars/laterya/nowcode-backend.svg?style=social&label=Stars"/>
</a>
    <img alt="Maven" src="https://raster.shields.io/badge/Maven-3.8.1-red.svg"/>
<a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
        <img alt="" src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
</a>
    <img alt="SpringBoot" src="https://raster.shields.io/badge/SpringBoot-2.7+-green.svg"/>
<a href="https://github.com/laterya/nowcode-backend" target="_blank">
    <img src='https://img.shields.io/github/forks/laterya/nowcode-backend' alt='GitHub forks' class="no-zoom">
</a>
<a href="https://github.com/laterya/nowcode-backend" target="_blank"><img src='https://img.shields.io/github/stars/laterya/nowcode-backend' alt='GitHub stars' class="no-zoom">
</a>
</div>

## 项目介绍 🙋

**项目介绍：基于SpringBoot + Dubbo + Gateway + MQ + AIGC的综合平台。**

**😀 作为用户您可以通过注册登录账户，获取接口调用权限，并根据自己的需求浏览和选择适合的接口。您可以在线进行接口调试，快速验证接口的功能和效果。
**

**💻 作为开发者 我们提供了客户端SDK， 可将轻松集成接口到您的项目中，实现更高效的开发和调用。**

**🏁 用户可以导入原始数据集并输入分析目标，就能自动生成可视化图表及分析结论，实现数据分析的降本增效。**

**🚀 作为管理员 我们提供了管理后台，可以管理用户、接口、数据分析结果等。**

## 网站导航 🧭

- [**NowCode 后端 🏘️**](https://github.com/laterya/nowcode-backend)
- [**NowCode 前端 🏘**️](https://github.com/laterya/nowcode-frontend)
- [在线体验](http://api.laterya.top/#/)

## 目录结构 📑

| 目录                                                | 描述            |
|---------------------------------------------------|---------------|
| **🏘️ [nowcode-core](./nowcode-core)**            | NowCode后端服务模块 |
| **🏘️ [nowcode-common](./nowcode-common)**        | 公共服务模块        |
| **🕸️ [nowcode-gateway](./nowcode-gateway)**      | 网关模块          |
| **🔗 [nowcode-interfaces](./nowcode-interfaces)** | 接口模块          |
| **🛠 [nowapi-sdk](./nowapi-sdk)**                 | 开发者调用sdk      |

## 快速启动 🚀

### 前端

环境要求：Node.js >= 16

安装依赖：

```bash
yarn or  npm install
```

启动：

```bash
yarn run dev or npm run start:dev
```

部署：

```bash
yarn build or npm run build
```

### 后端

1. 执行[sql目录](./nowcode-core/sql)下create.sql

## 项目选型 🎯

### **后端**

- Spring Boot 2.7.6
- MySQL 数据库
- 腾讯云COS存储
- Dubbo 分布式（RPC、Nacos）
- Spring Cloud Gateway 微服务网关
- API 签名认证（Http 调用）
- RattitMQ 消息队列
- Spring AOP 切面
- Redisson 限流
- Swagger + Knife4j 接口文档
- Spring Boot Starter（SDK 开发）
- Spring Session Redis 分布式登录
- Apache Commons Lang3 工具类
- MyBatis-Plus 及 MyBatis X 自动生成
- Hutool、Apache Common Utils、Gson 等工具库

### 前端

- Vue3 + TypeScript
- Vue-cli 脚手架
- Anco Design Vue 组件库
- OpenAPI 前端代码生成

## 项目功能 💻

1. 提供平台注册和三方授权注册登陆
2. 管理员可以对用户、接口进行管理
3. 用户可以在线调试接口，并通过导入SDK进行调用，仅需要几行代码
4. 用户通过平台分配的ak, sk进行调用鉴权，注册时赠送30积分，可通过每日签到获取10积分
5. 可以查看接口的使用频率
6. 用户可以通过输入excel或者csv数据，平台会分析并生成图表和结论

## 功能展示 ✨

### 在线接口

![image-20240303143132897](https://picgo-imgs8.oss-cn-shenzhen.aliyuncs.com/img/image-20240303143132897.png)

### 在线调用

![image-20240303143156745](https://picgo-imgs8.oss-cn-shenzhen.aliyuncs.com/img/image-20240303143156745.png)

### 智能分析结论

![image-20240303143411764](https://picgo-imgs8.oss-cn-shenzhen.aliyuncs.com/img/image-20240303143411764.png)

## 注意事项

1. 因为sonatype暂停了创建项目，故SDK无法推送至maven中央仓库，需要用户自行拉到本地进行打包调试

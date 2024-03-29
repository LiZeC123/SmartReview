Smart-Review 复习管理器
========================
![](https://img.shields.io/github/license/LiZeC123/SmartReview)
![](https://img.shields.io/github/issues/LiZeC123/SmartReview)
![](https://img.shields.io/github/v/tag/LiZeC123/SmartReview)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/LiZeC123/SmartReview)
![GitHub last commit](https://img.shields.io/github/last-commit/LiZeC123/SmartReview)


Smart-Review是一个基于人工智能的复习管理工具, 其核心功能是帮助用户维护一个自定义的知识库, 并且在适当的时间间隔下提示用户对相关知识点进行复习.

Smart-Review采用强化学习技术来学习用户的记忆规律, 通过用户的不断反馈来调整复习的时间间隔.

Smart-Review使用Web架构, 从而可以跨平台使用, 用户可以使用PC端添加内容, 并在移动端复习.

> 项目目前处于前期开发阶段, 还在探索具体要开发什么功能, 随着后续开发的进行, 各部分都可能发生改变


部署方案
------------

本项目采用Docker进行部署，下载整个项目后只需要执行

```
docker-compose up -d
```

即可编译整个项目并在后台运行。项目默认的服务端口为6080，具体配置可在`docker-compose.yml`文件中修改。


项目架构规划
------------

### 技术方案

本项目是Java Web项目, 使用JDK 11作为开发环境, 后端使用Spring Boot框架, 前端使用Vue.js + Bootstrap框架. 初步考虑使用如下的技术

1. Spring + MyBatis + MySQL : 业务核心框架
2. Redis : 对部分访问频率高的接口, 使用Redis进行缓存
3. RabbitMQ: 导入与导出等批处理任务考虑使用消息队列
4. Bootstrap & Vue.js : 在Bootstrap的静态页面的基础上, 通过Vue.js实现数据的绑定和渲染

### 数据结构

目前考虑将每一个知识点表示为一个卡片的样式, 在每个卡片上存在如下的一些元素

1. 标题: 本卡片的主题
2. 正文: 具体的知识点
3. 链接: 此知识点的延伸知识
4. 标签: 此知识点对应的标签

其中标题, 正文和标签是必备的内容, 链接是可选内容. 标签可以具有层次结构, 例如`算法/排序算法`或者`数据结构/栈的性质`.

根据卡片对应的层次标签以及标题, 即可将相关的知识点汇集成一片文章.


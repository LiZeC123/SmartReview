Smart-Review 复习管理器
========================

Smart-Review是一个基于人工智能的复习管理工具, 其核心功能是帮助用户维护一个自定义的知识库, 并且在适当的时间间隔下提示用户对相关知识点进行复习.

Smart-Review采用强化学习技术来学习用户的记忆规律, 通过用户的不断反馈来调整复习的时间间隔.

Smart-Review使用Web架构, 从而可以跨平台使用, 用户可以使用PC端添加内容, 并在移动端复习.

> 项目目前处于前期开发阶段, 还在探索具体要开发什么功能, 随着后续开发的进行, 各部分都可能发生改变


项目架构规划
------------

### 技术方案

本项目是Java Web项目, 使用JDK 11作为开发环境, 后端使用Spring Boot框架, 前端使用Vue.js + Bootstrap框架. 初步考虑使用如下的技术

1. Spring + MyBatis + MySQL : 业务核心框架
2. Redis : 对部分访问频率高的接口, 使用Redis进行缓存
3. RabbitMQ: 导入与导出等批处理任务考虑使用消息队列
4. Bootstrap & Vue.js : 在Bootstrap的静态页面的基础上, 通过Vue.js实现数据的绑定和渲染

> 前端代码请访问 https://github.com/LiZeC123/SmartReviewWeb

### 数据结构

目前考虑将每一个知识点表示为一个卡片的样式, 在每个卡片上存在如下的一些元素

1. 标题: 本卡片的主题
2. 正文: 具体的知识点
3. 链接: 此知识点的延伸知识
4. 标签: 此知识点对应的标签

其中标题, 正文和标签是必备的内容, 链接是可选内容. 标签可以具有层次结构, 例如`算法/排序算法`或者`数据结构/栈的性质`.

根据卡片对应的层次标签以及标题, 即可将相关的知识点汇集成一片文章.


参考资料
--------------------

### Spring Security

Spring Security 在 Spring Boot 2.0 版本中对默认运行的路径进行了修改, 针对静态文件, 需要进行配置才能被直接访问, 具体可以参考下面的两篇文章

- [Serving static web resources in Spring Boot & Spring Security application](https://stackoverflow.com/a/49506180)
- [Security changes in Spring Boot 2.0 M4](https://spring.io/blog/2017/09/15/security-changes-in-spring-boot-2-0-m4)
- [Spring Boot 2.X 实战--Spring Security (Token)登录和注册](https://my.oschina.net/RyenAng/blog/3230602)

---

- [Retrieve User Information in Spring Security](https://www.baeldung.com/get-user-in-spring-security)

### MySQL

- [Spring Boot MySql Access denied for user 'root'@'localhost'](https://stackoverflow.com/questions/58260870/spring-boot-mysql-access-denied-for-user-rootlocalhost)
- [MySQL 执行.sql文件](https://www.jianshu.com/p/e603abae317d)

### Java

- [Zipping and Unzipping in Java](https://www.baeldung.com/java-compress-and-uncompress)

### Java监控工具

- [java高分局之jstat命令使用](https://blog.csdn.net/maosijunzi/article/details/46049117)

### Swagger UI

- [java swagger ui 添加header请求头参数](https://blog.csdn.net/uncle_david/article/details/79283422)

### JUnit 5

- [JUnit 5 Tutorial](https://howtodoinjava.com/junit-5-tutorial/)
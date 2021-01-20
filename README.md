Smart-Review 复习管理器
========================

Smart-Review是一个基于人工智能的复习管理工具, 其核心功能是帮助用户维护一个自定义的知识库, 并且在适当的时间间隔下提示用户对相关知识点进行复习.

Smart-Review采用强化学习技术来学习用户的记忆规律, 通过用户的不断反馈来调整复习的时间间隔.

Smart-Review使用Web架构, 从而可以跨平台使用, 用户可以使用PC端添加内容, 并在移动端复习.

> 项目目前处于前期开发阶段, 还在探索具体要开发什么功能, 随着后续开发的进行, 各部分都可能发生改变 



需求规划
-----------

### 功能需求规划

- [ ] 页面开发
  - [x] 复习页面
  - [ ] 添加知识点页面
    - [ ] 单词添加页面
    - [ ] LeetCode添加页面
  - [ ] 查询页面
- [ ] 基本功能开发
  - [ ] 创建知识点
  - [ ] 更新标题与正文
  - [ ] 更新标签
  - [ ] 更新URL
- [ ] 提醒功能开发
  - [ ] 根据用户反馈调整复习间隔
- [ ] 导出功能开发
  - [ ] 将知识点导出为Markdown笔记
- [ ] 技术需求规划
  - [ ] 支持多用户多平台同时使用功能
  - [ ] 对存储数据实现备份

项目架构规划
------------

### 技术方案

本项目是Java Web项目, 使用JDK 11作为开发环境, 后端使用Spring Boot框架, 前端使用Bootstrap框架.  初步考虑使用如下的技术

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


项目算法规划
-------------

感觉这个间隔时间可能与强化学习存在一定的相关性, 但具体如何实现还需要进一步完善. 以下是一些设想

- 状态: 从知识点被记录到现在的时间 t, 单词被复习过的次数 n 
- 动作: 立即复习 / 延迟X时间后复习
- 奖励: 最优目标是知识点有点遗忘但又不是非常遗忘时提示用户复习,
    - 例如可以设定 非常熟悉 + 0.25分  有点熟悉 +1分  有点陌生 -0.25分 陌生 - 10分

此外, 还可以考虑引入信息熵的概念来计算输入的知识点的信息量, 并作为计算复习间隔的辅助信息

> 在引入强化学习之前, 本系统可以使用固定时间间隔的提醒模式, 或者采取时间序列相关的技术, 结合以往的统计结果计算间隔时间



引用的开源项目项目
-------------------

感谢以下的开源项目和免费组件: 

- [My Login](https://github.com/nauvalazhar/bootstrap-4-login-page)
- [人工智能图标](https://www.iconfont.cn/collections/detail?spm=a313x.7781069.0.da5a778a4&cid=8217)
- [动物图标](https://www.iconfont.cn/collections/detail?spm=a313x.7781069.0.da5a778a4&cid=25660)

参考资料
--------------------

### Spring Security

Spring Security 在 Spring Boot 2.0 版本中对默认运行的路径进行了修改, 
针对静态文件, 需要进行配置才能被直接访问, 具体可以参考下面的两篇文章

- [Serving static web resources in Spring Boot & Spring Security application](https://stackoverflow.com/a/49506180)
- [Security changes in Spring Boot 2.0 M4](https://spring.io/blog/2017/09/15/security-changes-in-spring-boot-2-0-m4)

### MySQL

- [Spring Boot MySql Access denied for user 'root'@'localhost'](https://stackoverflow.com/questions/58260870/spring-boot-mysql-access-denied-for-user-rootlocalhost)


###  Java监控工具

- [java高分局之jstat命令使用](https://blog.csdn.net/maosijunzi/article/details/46049117)
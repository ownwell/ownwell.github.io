title: "Jenkin+Git+Gradle为Android项目搭建CI环境"
date: 2015-08-16 10:10:58
tags: [Jenkins,Android]
categories: [Tools]
---

# 前言

在平时的项目开发中，我们需要将最新的代码及时打包（尽管是debug版本的）供公司内部人员及时的查看工作进度，这个怎么才能做到呢。

我的上家公司是创业公司，总是用一些很前卫的技术：Android Studio、Gradle、Git、Material等。这些在去年的Google IO大会后，在我们的项目中，已经开始全面的使用（其实Android项目就我一个工程师）。当时就有同事提出了CI这个自动化集成环境来处理，后来果然搭了一个Jenkins的CI环境，每次只需要提交代码，CI会从GitLab上拉最新的代码，自动打包，同时推到我们内部的下载/发布页面上。而我们内部的员工在下载页面上下载最新的安转文件，用起来很爽啊。

对于工程师来说，你就只管提交代码，不用关心怎么打包之类的。
对于产品或者测试，我只需要从发布页面下载APK安装文件，不必每次都跑工程师那，安装个最新的包之类。
这中间是CI帮我们处理了，怎么更新代码，怎么打包，怎么发版，他就是神器。

**Do U think so。**

就简单说下CI的一些作用吧：
![图1](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-3@2x.png)

我每次提交一次代码（Git/SVN作为代码的版本控制）到develop分支上，CI检测到有版本需要更新，就会在一定的时间内打出一个到指定的目录下，而这个目录就是我们可以访问下载的页面。


这周我也通过Jenkins搭建了一个简单的CI（Continuous Integration）环境，以供大家使用。

# 准备

1. PC（mac or Linux机器）
2. Java +Gradle环境 +Android SDK
3. Tomcat 7+
4. Jenkins（最好是war文件）

5. 配置好tomcat，能启动访问，将Jenkins放到Tomcat的WebAPP下，可以通过本地访问到jenkins。![图2](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-1@2x.png)

# 全局配置
## 下载Git和Gradle的相关插件
 在Jenkins首页（图1）-- 系统管理 -- 插件管理 ，搜索[Gitlab](https://wiki.jenkins-ci.org/display/JENKINS/GitLab+Plugin)和Gradle的插件。
 
## 全局配置
在Jenkins首页（图1）-- 系统管理 -- 系统管理：
配置JDK、Android SDK、Git、Gradle这些环境

### 配置Android SDK环境
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-4@2x.png)

### 配置JDK环境
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-5@2x.png)

### 配置Git环境
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-16@2x.png)

### 配置Gradle环境
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-7@2x.png)

# 配置Job
## 新建一个Job

对于Jenkins，每一个任务都是job，所以我们需要把自己的job映射到jenkins上。


在Jenkins首页（图1）--左侧的新建 

![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-8@2x.png)

再回到jenjins的首页，就会发现多了一个项目。

## 配置一个Job

根据**Jenkins首页（图1）-- 项目 -- 配置**进入到配置页面。

1.填写Git的相关信息和分支。
![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-12@2x.png)


>git的话，我现在用的是https协议的，用ssh协议的话，记得填写key以private key.

2.SCM配置
![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-9@2x.png)
scm就会每5分钟更新一次，若是远程仓库的指定分支上更新

![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-11@2x.png)


这样配置完成了。
## 构建
回到你的项目的首页，点击左侧的立即构建。
![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-13@2x.png)
若是失败了点击本次的构建历史。

![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-14@2x.png)

![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-15@2x.png)

## 发版到指定服务器
在构建完成后，我们不仅仅可以用gradle的命令，还可以使用shell、python等，这样我们就可以模拟出一个发版的Server了，这个就不再啰嗦了。

只要更新在5分钟内就可以在后台看见jenkins在非常卖力的工作给我们打包。

这个就是构建历史，还可以看见每次的状态啊
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-17@2x.png)

发布页面，这个页面下，公司内部人员就可先尝鲜了。
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150816-18@2x.png)

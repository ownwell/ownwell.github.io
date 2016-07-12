title: "Mac 下安装配置Tomcat"
date: 2015-07-29 22:08:47
tags: [Tomcat]
categories: Java
---

# 前言
这几天手痒痒，一直在想着自己的APP（虽然公司也有项目，但是自己还是挺想做一个自己特希望的项目--一个学习站点应用）。

这不，大热天，就想着怎么抓取数据，于是乎，有了这个博客---在Mac Os 下安装tomcat。

# 安装

去[Apache Tomcat](http://tomcat.apache.org/download-70.cgi)上下载一个Tomcat，记得以前就用Tomcat 7 ，有感情了。

下载完，放到你的根目录下；/Library/Tomcat

给你的运行脚本权限，这个在window是没有这个的：

```Shell
sudo chmod 755  /Library/Tomcat/bin/*.sh
```

# 启动Tomacat
```Shell
sudo sh /Library/Tomcat/bin/startup.sh
```

 成功的话会出现：
 ```Shell
 Using CATALINA_BASE:   /Library/Tomcat
Using CATALINA_HOME:   /Library/Tomcat
Using CATALINA_TMPDIR: /Library/Tomcat/temp
Using JRE_HOME:        /Library/Java/JavaVirtualMachines/jdk1.8.0_31.jdk/Contents/Home
Using CLASSPATH:       /Library/Tomcat/bin/bootstrap.jar:/Library/Tomcat/bin/tomcat-juli.jar
```

OK，打开浏览器输入：localhost8080
就可以看见可爱的tom猫了。

![](http://7xj9f0.com1.z0.glb.clouddn.com/tomcat_20150729.png)

# 配置
这样每次启动太麻烦了，没事都要跑到library去启动tomcat的shel脚本，麻烦死了。

在终端的包含路径下（如/usr/bin），下新建一个tomcat的文件：

```Shell
➜ /usr/bin >sudo touch tomcat
```

打开这个文件，配置shell脚本如下：
```shell
#!/bin/bash

case $1 in
start)
sh /Library/Tomcat/bin/startup.sh
;;
stop)
sh /Library/Tomcat/bin/shutdown.sh
;;
restart)
sh /Library/Tomcat/bin/shutdown.sh
sh /Library/Tomcat/bin/startup.sh
;;
*)
echo “Usage: start|stop|restart”
;;
esac

exit 0
```
保存，好了这样，你就可以在任何位置启动或者关闭tomat了：
快捷命令如下：

1. tomcat start 
2. tomcat stop
3. tomcat restart 



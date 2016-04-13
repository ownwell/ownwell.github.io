title: "关于Mysql允许远程连接"
date: 2015-08-11 22:19:04
tags: [JavaEE]
categories: [Java]
---

>Happy to be a good **Developer** ,not a coder.

课外之余，在DigitalOcean上买了一个VPS空间，供自己的开发的项目准备，我安装`Tomcat` 和`Mysql`。Tomcat是没有问题，安装之后就可以了，但是mysql在远程访问总是提示权限不够，下班之后折腾了下，发现在Navicat打开居然Ok了。

就做个简单的总结，防止下次使用时又忘了。

# 配置

## 登录你的Mysql服务器

在你的DigitalOcean上登录你的mysql

```Shell
mysql -uName -pPassword
``

把Name替换你的mysql的名字，Password也是一样，如你的root密码是123456
就这么写
```Shell
mysql -uroot -p123456
```

## 进入你的mysql的管理的数据下的下

```shell
mysql> USE mysql;
>>Database changed
``

 切换到 mysql DB
 ------------
 ```Shell
 mysql> SELECT User, Password, Host FROM user;
 ```
查看现有用户,密码及允许连接的主机
-----------
```shell
mysql> GRANT ALL PRIVILEGES ON *.* TO 'root'@'192.168.1.100' IDENTIFIED BY '' WITH GRANT OPTION;
mysql> -- @'192.168.1.100' #可以替换为@‘%’就可任意ip访问，当然我们也可以直接用 UPDATE     更新 root 用户 Host, 但不推荐, SQL如下:
mysql> -- UPDATE user SET Host='192.168.1.100' WHERE User='root' AND Host='localhost' LIMIT 1;
mysql> flush privileges;
Query OK, 0 rows affected (0.00 sec)

````




---
title: Python学习-pip和virtualenv
date: 2016-04-16 21:57:04
tags: [Python]
categories: [Python]
---

# 前言

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Python可以作为运维人员的工具，无论是windows、mac和linux，都可以跑起来，比shell脚本方便，可以作为web的开发工具，搭建网站，在大数据方面Python也不弱，所以选择Python作为开发语言的工程师可能是一个全栈工程师。

![](http://7xj9f0.com1.z0.glb.clouddn.com/308085d7cbf0dfed3d87bd686d564143_r.jpg)

<!-- more -->

那么`Python`作为一个热门语言，肯定有很多开发者，也有很多第三方库，那么我们怎么导入这些呢，就像我们通过`maven center`来获取第三方的库呢，不要担心`Python`也有。
就请出我们的男猪脚吧 ----`pip`。
对了我的电脑上有好几个版本的`Python`，`Python2.7`和`Python3`都有，搞得眼花缭乱，怎么办，不要担心今天的女猪脚也来了 ---`virtualenv`。

双剑在手，`Python`无愁。

# `Pip`

## 安装`pip`
`mac`系统为例，`brew`安装`wget`

```Shell
wget https://raw.github.com/pypa/pip/master/contrib/get-pip.py

python get-pip.py
```

## pip 常用命令

###  pip升级自己

```Python
pip install --upgrade pip
```

###  使用search、install这两个参数

```Python
pip install pack  

pip install package
```

###  查看已经安装的库

```Python
pip list

```

###  获取过期的库：

```Python
pip list --outdated

例如：
pip list --outdated | grep Jinja2
```

# virtualenv

## `virtualenv`安装
```shell
pip install virtualenv

python3下
pip install virtualenv
```

## `virtualenv`使用

###  创建目录

```shell
Mac:~ cyning$ mkdir myproject
Mac:~ cyning$ cd myproject/
Mac:myproject cyning$
```

###  创建一个独立的`Python`运行环境，命名为`venv`

```shell
Mac:myproject cyning$ virtualenv --no-site-packages venv
Using base prefix '/usr/local/.../Python.framework/Versions/3.4'
New python executable in venv/bin/python3.4
Also creating executable in venv/bin/python
Installing setuptools, pip, wheel...done.
```
参数`--no-site-packages`，这样，已经安装到系统`Python`环境中的所有第三方包都不会复制过来，这样，我们就得到了一个不带任何第三方包的“干净”的`Python`运行环境。

有了`venv`这个Python环境，可以用source进入该环境：

```shell
Mac:myproject cyning$ source venv/bin/activate
(venv)Mac:myproject cyning$
```
有个(venv)前缀，表示当前环境是一个名为`venv`的`Python`环境。


###  在自己的`Python`环境下管理自己的`Python`的环境
下面正常安装各种第三方包，并运行`Python`命令:
```python
(venv)Mac:myproject cyning$ pip install jinja2
...
Successfully installed jinja2-2.7.3 markupsafe-0.23
(venv)Mac:myproject cyning$ python myapp.py
```

在`venv`环境下，用`pip`安装的包都被安装到`venv`这个环境下，系统`Python`环境不受任何影响。也就是说，`venv`环境是专门针对myproject这个应用创建的。

退出当前的`venv`环境，使用`deactivate`命令:

```shell
(venv)Mac:myproject cyning$ deactivate
Mac:myproject cyning$
```
在`venv`和系统的`python`间自由的飞翔吧。

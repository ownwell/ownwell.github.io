title: "SublimeText的小技巧"
date: 2015-06-17 23:37:01
tags: [Tools,Sublime Text]
categories: [Tools]
---
# 简介

Sublime Text是一个跨平台的文本工具，对于大多数的开发者，大家并不陌生，我只是将我自己长常用的记录下来，好记性不如懒笔头。

SublimeText分为 Sublime Text2和Sublime Text3，其中3是收费版，会定时弹广告，不过我更喜欢3。其官网：[http://www.sublimetext.com/](http://www.sublimetext.com/)

# 安装Package Control

[Package Control](https://packagecontrol.io/)是一个subltext的Package管理工具，通过它可以安装很多的插件。

可以通过其[官网](https://packagecontrol.io/)来搜索，相关插件。

安装步骤

打开控制台页面(windows是 ctrl +\` mac是command+\`)

在控制太输入安装Package Control的命令：

**Sublime Text 3**

```Python
import urllib.request,os,hashlib; h = 'eb2297e1a458f27d836c04bb0cbaf282' + 'd0e7a3098092775ccb37ca9d6b2e4b7d'; pf = 'Package Control.sublime-package'; ipp = sublime.installed_packages_path(); urllib.request.install_opener( urllib.request.build_opener( urllib.request.ProxyHandler()) ); by = urllib.request.urlopen( 'http://packagecontrol.io/' + pf.replace(' ', '%20')).read(); dh = hashlib.sha256(by).hexdigest(); print('Error validating download (got %s instead of %s), please try manual install' % (dh, h)) if dh != h else open(os.path.join( ipp, pf), 'wb' ).write(by)

```
**Sublime Text 2**

```Python

import urllib2,os,hashlib; h = 'eb2297e1a458f27d836c04bb0cbaf282' + 'd0e7a3098092775ccb37ca9d6b2e4b7d'; pf = 'Package Control.sublime-package'; ipp = sublime.installed_packages_path(); os.makedirs( ipp ) if not os.path.exists(ipp) else None; urllib2.install_opener( urllib2.build_opener( urllib2.ProxyHandler()) ); by = urllib2.urlopen( 'http://packagecontrol.io/' + pf.replace(' ', '%20')).read(); dh = hashlib.sha256(by).hexdigest(); open( os.path.join( ipp, pf), 'wb' ).write(by) if dh == h else None; print('Error validating download (got %s instead of %s), please try manual install' % (dh, h) if dh != h else 'Please restart Sublime Text to finish installation')
```

安装完，restart。

# 插件

通过弹出package control（command + shift+ P）-->输入install,选择Package Control 输入你要安装的插件。

插件很多，我就把别人总结好的汇总如下：

[Sublime Text 2 入门及技巧](http://lucifr.com/2011/08/31/sublime-text-2-tricks-and-tips/#package_control)











---
layout: post
title: "[Android]Android 版本兼容库"
tags: [Android]
categories: [Android]
---


#1. 概述--Android的碎片化 #
---

相对于IOS封闭的生态，从上游的操作系统到下端的App发布审核，Android是一个开放的天地，它生来就是Open Source，其底层就是Linux系统，其系统每个版本都有相关的源码开放，同时 Android只是一个基带系统，任何厂商都可以在认证许可下进行自己的系统的定制，如三星、华为、小米等这些厂商都有自己的基于Android开发的生态系统，不同的厂商又在用不同的标准使用不同的硬件，却在做一个手机系统--Android。
  
现在Android碎片越来越严重，Google也需要从源头控制，叫停一些可能碎片化的合作，将新版本发布时间间隔不再这么频繁，这些措施在一定程度上也在减缓碎片化。

![](https://ownwell.github.io/image/support.png)
![](http://img.blog.csdn.net/20140730180338183)
在[兴业的博客](http://www.cnblogs.com/xyzlmn/p/3884078.html)中已经很好的分析了分辨率、屏幕大小（没事，大家多看看他的博客，很牛逼）。

先在主要是从开发者角度来解决系统版本的碎片化的一些问题。

#2. google support-library
---
google提供了Android Support Library package 系列的包来保证来高版本sdk开发的向下兼容性，即我们用4.x开发时，在1.6等版本上，可以使用高版本的有些特性，如Fragement,ViewPager等，下面，简单说明下这几个版本间的区别：

1. Android Support v4:  这个包是为了照顾1.6及更高版本而设计的，这个包是使用最广泛的，eclipse新建工程时，都默认带有了。常用的Fragment是在3.0之后才出现的，但是有了V4包，1.6以上的也可用Fragment、FragmentActivity，同时也有[SwipeRefreshLayout](http://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html)、[Navigation Drawer](http://developer.android.com/training/implementing-navigation/nav-drawer.html)、[NotificationCompat](http://developer.android.com/reference/android/support/v4/app/NotificationCompat.html).等一些低版本可以的组件.
	
	参考链接：[http://developer.android.com/tools/support-library/index.html](http://developer.android.com/tools/support-library/index.html)
[http://developer.android.com/tools/support-library/features.html#v4](http://developer.android.com/tools/support-library/features.html#v4).

2. Android Support v7:  这个包是为了考虑照顾2.1及以上版本实现4.0一些新的API而设计的，但不包含更低，故如果不考虑1.6,我们可以采用再加上这个包，另外注意，v7是要依赖v4这个包的，即，两个得同时被包含。
 这个包目前我们使用比较多的是ActionBar、ActionBarActivity等一些和ActionBar依赖的东西.
 
	参考链接：
	[http://developer.android.com/tools/support-library/features.html#v7](http://developer.android.com/tools/support-library/features.html#v7)

	[http://developer.android.com/reference/android/support/v7/app/ctionBar.html
](http://developer.android.com/reference/android/support/v7/app/ActionBar.html)

3. Android Support v13:这个包的设计是为了android 3.2及更高版本的，一般我们都不常用，平板开发中能用到。
这个使用较少。


以后有时间我会写一些Fragment、ActionBar一些文章。这个在[兴业博客](http://blog.csdn.net/xyz_lmn)中也可以找得到。


---
title: 站在大神肩膀上看RxJava
date: 2016-04-13 21:44:49
tags:
---

#概述

![](http://upload-images.jianshu.io/upload_images/675733-2254c982389d3d93.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

RxJava是作为Android开发中的新贵， 越来越多的人参与到RxJava的拓展和使用上，如JakeWharton参与了多个RxJava项目。国内呢，一大批（如扔物线、小邓子、代码家等）对RxJava推广，你要不没听过它，你都觉得不好意思，因为它太火了。

今天我们就是要踩在这些过来人的肩膀上，来学习RxJava，这个主要是针对不熟悉RxJava的，若是已经很熟练使用了，这篇可能就不太适合你。


大概是在14年底，我在一个创业公司，从yahoo来的曾Sir就给我们分享RxJava但是当时资料甚少，加上项目赶紧，就大概知道了RxJava这种响应式处理数据太好了，但是使用起来需要自己写一堆配套的库，所以就搁置了。但是随着Retrofit 2.0、RxVolley等这些好用的第三方RxJava拓展库 —-用一次就觉得爽到爆—我决定在公司的项目中使用RxJava了。

RxJava是一个针对JVM通用的工具库，在Github上你会发现一堆的扩展库，在简书、CSDN等技术论坛上，你会发现有N多篇RxJava的文章，对于一个新手或者进阶的开发者，我就以过来人的身份为大家列出一些入门的文章和项目，本人不会有什么实质性的介绍或者解释，会给大家列出一系列的文章和书，一步步给大家知道。

# 什么是RxJava以及RxJava能干什么

在Flipboard工作的扔物线同学用通俗的描述为我们讲述什么是Rxjava，为什么要用RxJava — [给 Android 开发者的 RxJava ](http://gank.io/post/560e15be2dca930e00da1083)详解。很喜欢他的文章风格，图文都是让你过目不忘。还是建议你直接跳转去看看扔物线的[文章](http://gank.io/post/560e15be2dca930e00da1083)。


# 该怎么用RxJava

其实我以前是先看的大头鬼翻译的文章，觉得翻译的很好（英文的，看着多多少少有时不太理解），深入浅出告诉你怎么使用RxJava。

1. [深入浅出RxJava（一：基础篇）](http://blog.csdn.net/lzyzsd/article/details/41833541)
2. [深入浅出RxJava(二：操作符)](http://blog.csdn.net/lzyzsd/article/details/44094895)
3. [深入浅出RxJava三--响应式的好处](http://blog.csdn.net/lzyzsd/article/details/44891933)
4. [深入浅出RxJava四-在Android中使用响应式编程](http://blog.csdn.net/lzyzsd/article/details/45033611)


看完了这些你或许对RxJava有个大概的印象和使用常见的操作符，那你可以试试用RxJava和RxAndroid来一个简单的项目。
[MovieListApp](https://github.com/jpetitto/MovieListApp)没事可以看看这个项目，将请求的服务器远程数据请求后，通过Observable.just(）后，可以处理这些数据，通过map转换、onNext（）里缓存，线程切换、最后通过subcriber交给我们需要展示的UI组件上。

是不是有点意思了。
还有一个[Demo](https://github.com/kaushikgopal/RxJava-Android-Samples)。

等等，我感觉我还是不熟练啊。没关系，我是雷锋，继续给你发放福利。


1. [ReactiveX文档中文翻译](https://github.com/mcxiaoke/RxDocs)

2. [各种操作符的示例图](http://rxmarbles.com/)，满足你的视觉。
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20160413-1.png)

3. [泡网相关的RxJava总结](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0430/2815.html)，泡网里决定是都良心博文。



# 拓展库

RxJava的拓展库太多了，如JakeWharton参与或者主导的项目就参与了RxBinding、RxAndroid等项目，现在越来越多的人参与RxJava的推广中。


我就项目中可能会用到的一些库给大家推荐几款。


## [Retrofit](http://square.github.io/retrofit/)

一个懒人必备神器，可以简化我们对网络请求的封装，用它可能也有一些问题，需要你再开发中解决。建议使用Retrofit 2.0。

1. [Retrofit 2.0：有史以来最大的改进](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0915/3460.html)

2. [RxJava 与 Retrofit 结合的最佳实践](http://gank.io/post/56e80c2c677659311bed9841) Gank.io的匠心写作系列文章。

3. [Retrofit的使用手册](https://futurestud.io/blog/retrofit-getting-started-and-android-client)，必备手册。


## [RxBinding](https://github.com/JakeWharton/RxBinding)

JakeWharton大神操刀项目，主要是各种组件绑定，将Onclick、TextWatcher等时间作为一个事件源，通过RxJava可以改善用户的交互体验，避免了快速点击会出现两个点击事件。另外，还支持kotlin。
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20160413-2.png)

## [RxLifecycle](https://github.com/trello/RxLifecycle)

用来严格控制由于发布了一个订阅后，由于没有及时取消，导致Activity/Fragment无法销毁导致的内存泄露。

![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20160413-3.png)
## [Rx-preferences](https://github.com/f2prateek/rx-preferences)
用RxJava实现Android中的SharedPreferences

![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20160413-4.png))

## [Storio](https://github.com/pushtorefresh/storio)
支持RxJava的数据库


# 参考文章

1. [Awesome-RxJava](https://github.com/lzyzsd/Awesome-RxJava)
2. [RxJava常见的使用场景总结](http://www.jianshu.com/p/6917510b0e4c)


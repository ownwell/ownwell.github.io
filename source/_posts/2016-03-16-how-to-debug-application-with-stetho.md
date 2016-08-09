title: "用chrome调试Android程序"
date: 2016-03-16 22:02:50
tags: [Android]
categories: [Android]
---

原文出处：[cyning的博客](http://ownwell.github.io/2016/03/16/how-to-debug-application-with-stetho/)     

在开发过程中，我们经常喊着我们需要一个root的手机，为什么呢？
![](http://pic.baike.soso.com/p/20090712/20090712210314-622536671.jpg)

<!-- more -->


因为有时我们需要查看手机/data/data里面的数据，如数据库、SharedPreferences，不过有的是否真的没有root过的手机，有人说你可以用虚拟机啊，不过虚拟机不友好的操作，让我很是不习惯。若是有一个工具是通过chrome浏览器就可以帮我们查看数据库、SharedPreferences是不是会让我们欣喜若狂啊。好了，FaceBook退出的[Stetho](https://github.com/facebook/stetho)满足你这个小小的愿望。

~不得不感叹FB真是良心企业~。
![facebook](http://7xj9f0.com1.z0.glb.clouddn.com/mdfacebook.png-blog)
FaceBook的东西总是采用比较流行的一些工具，`Stetho`编译也是Android Studio的，可以直接通过gradle来编译，那就开始使用Stetho吧。

## 引入stetho

Java```
dependencies {
    //debug工具
    compile 'com.facebook.stetho:stetho:1.2.0'
}
```
## 配置Stetho
在项目的Application下也需要配置下Stetho。

Java```

    private void initStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this))
                .build());
    }
```

好了编译下，你的手机就启动了（废话一句，USB调试开关这时肯定是打开的）。

## 在chrome查看

![](http://7xj9f0.com1.z0.glb.clouddn.com/mdstetho_inspect.png-blog)

打开chrome浏览器，在地址栏输入[chrome://inspect](chrome://inspect)，好了你就可以可以在resource的tab下看到你想要的东西，还不快快体验下。
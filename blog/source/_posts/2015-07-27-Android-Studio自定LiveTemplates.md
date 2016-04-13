title: "Android Studio自定LiveTemplates"
date: 2015-07-27 13:25:35
tags: [Android]
categories: [Tools]
---

# 介绍

 Android Studio已经被越来越多的人任何，继承了IDEA的一切有点。我很喜欢代码提示，但是我们也需要自己的定义的live Temple，就是自己的一些快捷键。
 如：
 
 ```Java
 System.err.println(); 
 ```
 
 只需要：`serr`+`Tab`
 
 
 查看的方法很简单：
 在Prefrence--> Editor-->Live Templates, 有系统已经定义的可以去尝鲜吧。
 
#  自定义

我定义的一个单例模式

## 添加一个LiveTemplate

![添加一个LiveTemplate](http://7xj9f0.com1.z0.glb.clouddn.com/live.png)

## 添加快速提示的字符

![image](http://7xj9f0.com1.z0.glb.clouddn.com/live_template_2.png)

这样就会在输入inst按下Tab键就会出来我们要定义的代码片段

## 添加代码片段

```Java
private static $EXCEPTION$ mInstance = null;

public static $EXCEPTION$ getInstance() {
    if (mInstance == null) {
        synchronized ($EXCEPTION$.class) {
            if (mInstance == null) {
                mInstance = new $EXCEPTION$();
            }
        }
    }
    return mInstance;
}

```
>
>`$EXCEPTION$`代表表达式

>`$SELECTION$` 选中的代码

>`$END$`  光标结束时，所在的位置

EXCEPTION是没有意义的，编译它所代表的表达式。

![image](http://7xj9f0.com1.z0.glb.clouddn.com/live_template_3.png)

## 选择代码作用的区域
![image](http://7xj9f0.com1.z0.glb.clouddn.com/live_template_4.png)

确认，应用!打工告成~~



 
 
 
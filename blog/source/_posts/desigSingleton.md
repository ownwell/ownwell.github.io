title: "设计模式之单例模式"
tags: [设计模式,Java,单例]
categories: [编程]
date: 2014-07-02
---

#介绍#

   单例模式是一种常用的软件设计模式，其定义是单例对象的类只能允许一个实例存在。

　　许多时候整个系统只需要拥有一个的全局对象，这样有利于我们协调系统整体的行为。比如在某个服务器程序中，该服务器的配置信息存放在一个文件中，这些配置数据由一个单例对象统一读取，然后服务进程中的其他对象再通过这个单例对象获取这些配置信息。这种方式简化了在复杂环境下的配置管理。

   For example，就是一个王国(Kingdom)里，应该只有一位国王(King)，每次调用国王就应该是同一个国王。

#基本的实现思路#

   对于一个类，每次都只有一个实例，这个就要确保单例模式要求类能够有返回对象一个引用(永远是同一个)和一个获得该实例的方法（必须是静态方法，通常使用getInstance这个名称）。

  单例的实现主要是通过以下两个步骤：

将该类的构造方法定义为私有方法，这样其他处的代码就无法通过调用该类的构造方法来实例化该类的对象，只有通过该类提供的静态方法来得到该类的唯一实例；
在该类内提供一个静态方法，当我们调用这个方法时，如果类持有的引用不为空就返回这个引用，如果类保持的引用为空就创建该类的实例并将实例的引用赋予该类保持的引用。
常见的实现方式

##饿汉式##
```Java

public class Singleton {
    
    private final static Singleton INSTANCE = new Singleton();
    
    private Singleton(){}
    
    public static Singleton getInstance(){
        return INSTANCE;
    }
}

```
当然了，这个也可以写一个静态代码块。

##懒汉式(双重检查)##

```Java
public class Singleton {

    private static volatile Singleton singleton;

    private Singleton() {}

       public static Singleton getInstance() {
         if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
         }
         return singleton;
       }
}

```

这个很少见到的这面这种静态类的到时很少见吧.

##静态内部类##

```Java
public class Singleton {

      private Singleton() {}
    
      private static class SingletonInstance {
          private static final Singleton INSTANCE = new Singleton();
      }

      public static Singleton getInstance() {
          return SingletonInstance.INSTANCE;
      }
}
```
   这种方式跟饿汉式方式采用的机制类似，但又有不同。两者都是采用了类装载的机制来保证初始化实例时只有一个线程。不同的地方在饿汉式方式是只要Singleton类被装载就会实例化，没有Lazy-Loading的作用，而静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成Singleton的实例化。

　类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。

　优点：避免了线程不安全，延迟加载，效率高。　
　
##枚举##

```Java

  public enum Singleton {
      INSTANCE;
      public void whateverMethod() {
        
       }
  }
```

借助JDK1.5中添加的枚举来实现单例模式。不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。可能是因为枚举在JDK1.5中才添加，所以在实际项目开发中，很少见人这么写过。

优点

　　系统内存中该类只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高系统性能。

缺点

　　当想实例化一个单例类的时候，必须要记住使用相应的获取对象的方法，而不是使用new，可能会给其他开发人员造成困扰，特别是看不到源码的时候。

 

# 适用场合 #

1. 需要频繁的进行创建和销毁的对象；
2. 对象时耗时过多或耗费资源过多，但又经常用到的对象；
3. 工具类对象；
4. 频繁访问数据库或文件的对象。

[link](newsapp://topic/S1438933687931)

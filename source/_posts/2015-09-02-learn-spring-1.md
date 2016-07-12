title: "Spring学习笔记（1）"
date: 2015-09-02 23:21:02
tags: [Java]
categories: [Java]
---

![Spring](http://7xj9f0.com1.z0.glb.clouddn.com/1362969045_index.jpg)

# Maven的配置

## spring的配置

>我把spring的配置文件放到了resource下。

![spring配置](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150902-1@2x.png-blog)



## pom依赖

```Java
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.2.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
 ```
 
 
# 实例化Bean的三种方式

* 使用类构造器直接实例化

  
* 使用静态工厂的方法实例化
  
* 使用实例工厂方法实例化

```Java
  <!--使用类构造器直接实例化 -->
 <bean id="student" class="com.cyning.chart01.Student"/>

    <!-- 使用静态工厂的方法实例化 -->
    <bean id="userBean2" class="com.cyning.chart01.BeanFactory" factory-method="personService"/>
    <!-- 使用实例工厂方法实例化 -->
    <bean id="factory" class="com.cyning.chart01.BeanFactory"/>
    <bean id="userBean3" factory-bean="factory" factory-method="getPerson"/>
 ```
 
 
>对于第一种用类构造器示例的，就直接知道bean下的class，完成初始化。

>静态工厂实例化则是，通过BeanFactory的factory-method这个静态方式实例化一个bean。

>第三种则完全是先示例一个BeanFactory对象，再调用BeanFactory对象对象的factory-method。


# Bean节点信息配置

>Spring的Ioc容器启动时会初始化bean，但是我们可以指定Bean节点的lazy-init="true"，来延迟初始化bean。这时候，只有第一次获取bean才会初始化bean.

```Xml
 <bean id="student" class="com.cyning.chart01.Student" lazy-init="true"/>
```
如果想对所有的bean有应用延迟初始化，可以在跟节点beans设置default-lazy-init="true":

```Java
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
        default-lazy-init="true">
```
>[IDEA](http://www.jetbrains.com/idea/)下很爽的代码提示:
>![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150902-2@2x.png-blog)

当然了还有其他方法如销毁：
```Java
 <bean id="student" class="com.cyning.chart01.Student"  init-method="init" destroy-method="show"/>
 ```
 
 通过配置获取的bean默认的是一个单例。
 若是像配置成可以作为其他类的属性的，可以在bean的scope配置：
 ```Java 
  <bean id="student" class="com.cyning.chart01.Student"    scope="prototype"/>
```

>其实scode就两个属性：singleton 和prototype


# 依赖注入的简单实现

Spring的核心机制是依赖注入。依赖注入让bean与bean之间以配置文件组织在一起，而不是以硬编码的方式耦合在一起。依赖注入(Dependency Injection)和控制反转(Inversion of Control)是同一个概念。

具体含义是:当某个角色(可能是一个Java实例，调用者)需要另一个角色(另一个Java实例，被调用者)的协助时，在传统的程序设计过程中，通常由调用者来创建被调用者的实例。但在Spring里，创建被调用者的工作不再由调用者来完成，因此称为控制反转;创建被调用者实例的工作通常由Spring容器来完成，然后注入调用者，因此也称为依赖注入。不管是依赖注入，还是控制反转，都说明**Spring采用动态、灵活的方式来管理各种对象。对象与对象之间的具体实现互相透明**。

举个例子：
明天就要开始为抗日胜利70周年纪念日而举行盛大的阅兵仪式，其中环节是代表讲话，但是这个代表是个什么角色（士兵、抗日老兵……），我们不知道，他会将什么内容，我们也不知道，怎么办呢。

我们就可以模拟这个来一段依赖注入的代码实现：
加入我们把发表演讲的人，抽象成一个接口：
```Java
/**
 * @Author: cyning
 * @Date : 2015.09.02
 * @Time : 下午11:56
 * @Desc : 发表演讲的人，这个是个接口
 */
public interface IActor {
    public void speak();
}
```

而我们的阅兵仪式又必须有这个一个演讲者：
```Java
/**
 * @Author: cyning
 * @Date : 2015.09.03
 * @Time : 上午12:13
 * @Desc : 抗日胜利纪念
 */
public class AntiJapaneseParadeService {

    /**  发言的人*/
    private IActor actor;

    public  void speak(){
        actor.speak();
    }

    public IActor getActor() {
        return actor;
    }

    public AntiJapaneseParadeService setActor(IActor IActor) {
        actor = IActor;
        return this;
    }
}
```


可能有哪些人呢，士兵、曾抗日的老战士：

```Java
/**
 * @Author: cyning
 * @Date : 2015.09.03
 * @Time : 上午12:09
 * @Desc : 士兵
 */
public class Soldier  implements  IActor{
    public void speak() {
        System.out.println("我是士兵，保家卫国，驱除日寇");
    }
}
```


```Java
/**
 * @Author: cyning
 * @Date : 2015.09.03
 * @Time : 上午12:10
 * @Desc : 抗日老战士
 */
public class AntiJapaneseVeterans implements IActor {
    public void speak() {
        System.out.println("我们曾经经历人类历史最悲惨的战争之一，所以我们珍爱和平");
    }
}

```

好了，假设早上名单出来了，是我们的抗日老战士，直接就可以把AntiJapaneseParadeService中的actor给指向我们的老战士对象，不是有set方法么。

但是这种是需要我们手动维护这个set方法，耦合度很高，为什么不通过DI（依赖注入）可注入式的配置呢。

spring.xml:

```Java
    <bean id="soldier" class="com.cyning.chart02.Soldier"/>
    <bean id="veterans" class="com.cyning.chart02.AntiJapaneseVeterans"/>
    <bean id="parade" class="com.cyning.chart02.AntiJapaneseParadeService">
        <property name="actor" ref="veterans"/>
    </bean>
```


test一下：
```
@Test
    public void testSpring1(){
        ApplicationContext mContext = new ClassPathXmlApplicationContext("spring.xml");
        AntiJapaneseParadeService mService = (AntiJapaneseParadeService)mContext.getBean("parade");
        mService.speak();
    }
```
这样只需要维护spring.xml 这个配置文件即可。




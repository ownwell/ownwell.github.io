---
layout: post
title: "[Androoid]开源自己的项目到JCenter"
tags:  [JCenter]
date: 2016-07-14 11:15:17
categories: [Android]
---

# Maven

一个完整的系统，模块与模块之间，尽可能的使其独立存在。也就是说，让每个模块，尽可能的独立完成某个特定的子功能。模块与模块之间的接口，尽量的少而简单。
<!-- more -->

在写文章之前先吊书袋子了一下，告诉大家软件设计中的低耦合，所以我们会把一个个模块分开，模块之前相互独立，即插即用，这个书袋子吊的还可以吧。
![maven](http://img4.imgtn.bdimg.com/it/u=835084131,3132971467&fm=21&gp=0.jpg)

所以maven在一个大型系统是经常见到的，如各个模块做成独立的压缩文件如jar，放在服务器上，所有需要的都可以直接DownLoad到本地，拿来用，这是一种很爽的体验吧。



所以Maven使我们解决依赖的一种最常用的方法，当然了你也可以通过maven来构建项目。但是这不是我们的重点，我们要讲的是对于android开发者如何上传自己的项目到JCenter(一个供程序员玩耍的Maven仓库)，世界上所有的程序员都可以通过JCenter上传自己的项目或者下载别人的项目来使用。

<th>![藐视](http://qq.yh31.com/tp/qq/ZJBQ/201111/201111041444379709.gif)</th>

# 准备工作

## 注册
JCenter作为一个规模相当大的中心仓库，我们需要到[https://bintray.com/#](https://bintray.com/#)，去注册的账号，若你有账号跳过这一步。注册一般通过邮箱验证，试下登录是否注册成功。



## <span id="apikey">获取API key</span>
 注册完成后我们需要查看自己的key，进入到[https://bintray.com/profile/edit](https://bintray.com/profile/edit)，看到有个API KEY输入密码就可以见到，下面会用到这个key。

## 认识GAV
为了确定你的包唯一，我们使用GAV来保证，如下面的配置：
```
<dependency>    
  	<groupId>org.apache.struts</groupId>    
	<artifactId>struts2-core</artifactId>    
	<version>2.0.11</version>    
</dependency>
```

groupId 是你的Group的唯一标示   
artifactId  你的项目名称    
version   项目的版本    

所以你要确定自己的groupId和artifactId。

# 配置gradle上传maven的配置

### 在更项目的build.gradle添加如下几句：

```Java
 dependencies {
        ……
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.4'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.3'
        ……
}
```   


### 准备脚本和配置
 新建一个发布到Jcenter的gradle脚本到你的library,这个脚本是单独处理和JCenter上传的操作


bintray.gradle文件
```apply plugin: 'com.github.dcendents.android-maven'
apply plugin: 'com.jfrog.bintray'

Properties properties = new Properties()
properties.load(project.file('bintray.properties').newDataInputStream())
def siteUrl = properties.getProperty("bintray.siteUrl")

def desc = properties.getProperty("bintray.descrip")

// Maven Group ID for the artifact，一般填你唯一的包名
group = properties.getProperty("bintray.group")
version = properties.getProperty("bintray.version")

def giturl = properties.getProperty("bintray.vcs")
def userid = properties.getProperty("bintray.userid")
def username = properties.getProperty("bintray.username")
def useremail = properties.getProperty("bintray.useremail")

def licensename = properties.getProperty("bintray.licensename")
def licenseurl = properties.getProperty("bintray.licenseurl")
install {
    repositories.mavenInstaller {
         // This generates POM.xml with proper parameters
        pom {
            project {
                packaging 'aar'
                // Add your description here
                name desc //项目描述

               url siteUrl
               // Set your license
               licenses {
                   license {
                       name licensename
                       url licenseurl
                    }
                }

                developers {
                    developer {
                        id userid
                        name username
                        email useremail
                    }
                }
                scm {
                    connection giturl
                    developerConnection giturl
                    url siteUrl
                }
            }
        }
    }
}
task sourcesJar(type: Jar) {
    from android.sourceSets.main.java.srcDirs
    classifier = 'sources'

}

task javadoc(type: Javadoc) {
    source = android.sourceSets.main.java.srcDirs
    classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
}

task javadocJar(type: Jar, dependsOn: javadoc) {
    classifier = 'javadoc'
    from javadoc.destinationDir
}
artifacts {
//    archives javadocJar
    archives sourcesJar

}
bintray {
    user = properties.getProperty("bintray.user")
    key = properties.getProperty("bintray.key")
    configurations = ['archives']
    pkg {
        repo = "maven"
        name = properties.getProperty("bintray.name") //发布到JCenter上的项目名字
        websiteUrl = properties.getProperty("bintray.siteUrl")
        vcsUrl = properties.getProperty("bintray.vcs")
        licenses =[properties.getProperty("bintray.licenses")]
                publish = true

    }
}
```
你看到的那些`bintray.licenses`都是我们需要新建另外一个配置文件的：

-------------------
bintray.properties文件
```Java
bintray.key= #填写刚才获取[API key](#apikey)中的key
bintray.user=#填写刚才登录时的昵称

bintray.userid=
bintray.username=#需要显示在maven中的作者昵称
bintray.useremail=#需要显示在maven中的作者邮箱

bintray.licensename=The Apache Software License, Version 2.0
bintray.licenseurl=http://www.apache.org/licenses/LICENSE-2.0.txt

bintray.group=#项目的groupId
bintray.name=#项目名称
bintray.version=#项目版本号
bintray.siteUrl=#项目的对应网站
bintray.vcs=#项目的地址如githun等代码托管的链接
bintray.licenses=#开源协议如mit等
bintray.descrip=项目描述
````

准备妥当了，需要把gradle脚本放到你的libray的`build.gradle`,因为项目执行会去执行`build.gradle`,我们把刚才的bintray.gradle

apply from: "bintray.gradle"


整个项目结构我们可以这么看

library
--main
   --java
--build.gradle 在结尾记得加apply from: "bintray.gradle"
--bintray.gradle
--bintray.properties


### 上传
./gradlew clean bintrayUpload

这样就可以上传到jcenter上，去你的jcenter账号下看看有没有文件上传成功吧。



### 申请同步到jcenter

会看到左侧使我们可以使用的GAV
<img src="http://7xj9f0.com1.z0.glb.clouddn.com/md/1470416953314.png" width="685"/>

右侧是我们需要自己填写同步到jcenter的，否则只是私有库不能直接通过jcenter访问。

layout: post
title: "关于多渠道打包"
tags: [Tools]
categories: [Tools]
date: 2014-06-24
---

 &emsp;&emsp;由于项目需要,需要使用友盟的Sdk给多个渠道打包，什么豌豆荚、小米、GooglePlay、QQ应用宝等等七八个渠道，Android这几个渠道一个都没少（说明我们公司渠道广，哈哈），每次都给他们打包是个纠结的事情，你想想每次都要停下来一个个该改配置，那个痛苦。作为一个程序员为这事烦恼，不该，我就上网准备学Ant打包，结果，无意间看到一个神奇的利器————兰贝壳儿（Eclipse的插件），结果一试，太好了。现在就将这个分享给大家。

&emsp;&emsp;可以去官网[http://www.orchidshell.com/](http://www.orchidshell.com/)看下，里面有这个插件的现在地址。
下载兰贝壳儿Eclipse插件


### 下载后解压缩OrchidShell.rar ###

确认文件夹路径为OrchidShell---eclipse---plugins
![](http://www.orchidshell.com/Instructions/OchidShellInstructions.files/image008.jpg)
 
### 安装兰贝壳儿插件，建立Android工程 ###
安装Eclipse插件，将OrchidShell文件夹拷贝到Eclipse的dropins文件夹下后，启动（重启）Eclipse

 
### 建立Android工程 ###

在工程上点击右键可看到菜单 “兰贝壳儿”，说明安装成功

 
### 使用”在资源管理器中打开”功能 ###
点击兰贝壳儿菜单中的在资源管理器中打开菜单，可以将当前选中的资源所在的文件夹打开。

如果选中的资源是一个文件夹，那么插件会直接打开这个文件夹

如果选中的资源是一个文件，那么插件会打开文件的父文件夹，并且选中该文件

### 使用配置文件、默认配置文件 ###
兰贝壳儿使用xml作为插件配置文件，当工程中不存在配置文件，而插件的某些功能需要用到配置文件的时候，插件会新建一个默认的配置文件。

该配置文件引用了一个通用的安卓开发工具包。配置了一个签名用的密钥库。你需要根据自己的实际情况修改这个配置文件。在下面两个功能中，有详细介绍。
### 使用”依赖更新”功能 ###
 
点击插件右键中的“依赖更新”菜单，可以对工程的第三方依赖jar包进行更新管理。

 
这里使用的第三方jar包，使用maven管理jar包的组织形式

 
需要指出的是，这里的jar包必须有一个统一的下载网站。该网站可以是任何一家maven库或者maven库镜像。如果您自己有Team的服务器，当然也可以自己建一个Maven库来管理常用的通用组件。

 
点击菜单之后，会在Eclipse的控制台打出更新的过程。

注意，如果配置文件配置不正确，可能会报出一些错误提示，根据错误提示进行修改配置文件即可。
 
### 使用”打包发布”功能 ###
点击兰贝壳儿菜单中的“打包发布”菜单，可以对工程按渠道分别打包，打包的过程中可以对apk文件进行自动签名。

 
注意打包发布前，需要修改配置文件中对应的项目：

其中channels 的keyname=””是指在你的AndroidManifest.xml文件中配置的Application级的<Meta-data>的名称。例如：

    <meta-data android:value="andao007.com" android:name="UMENG_CHANNEL"/>
    <meta-data android:value="andao007.com" android:name="gfan_cpid"/>

 
这里使用的是友盟的统计SDK和机锋网的统计SDK，所以名称为“UMENG_CHANNEL,gfan_cpid”，你可以修改为你自己应用的统计SDK的渠道项目标识符，如果有多个，使用半角逗号隔开即可。
 
 
     <channel value=”andao007.com”/>
    
是对应于你要打包的每个渠道名称，也就是统计SDK中渠道的标识符。对于写在这里的每个渠道，插件会自动为其修改meta-data中的value值，并且打包为一个apk文件。然后为这个apk文件进行签名。
 
<keystore path="F:\Repos\android\keystore\common\common.key" passwd="123456">
<alias name="marsor_common" passwd="123456" />
</keystore>
这里的keystore就是你对apk文件进行签名时使用的个人密钥。需要你将path修改为你的密钥库路径，passwd为密钥库的密码。
下面的alias是指每个密钥对应的别名，以及对应的密码。如果你有多个alias，可以在这里指定，插件会选取第一个可用的alias以及密码对应的密钥Key对你的apk文件进行签名。
 
<outpath value="F:\Repos\android\keystore\output\" />
这个路径就是你的apk文件自动打包生成出来以后的存放路径。注意插件会在这个路径下创建以你的工程名为名称的文件夹。
http://www.orchidshell.com/Instructions/OchidShellInstructions.htm
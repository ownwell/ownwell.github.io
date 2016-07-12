title: "一个犀利的抓包工具---Charles"
date: 2015-06-15 20:36:57
tags: [抓包,Tools]
categories: [Tools]
---

![charles](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150918-1@2x.png)

NBA总决赛，如火如荼进行，是不是觉得很过瘾啊，看库里的三分，手起刀落，打得骑士丢盔卸甲，一个字--爽！库里有三分利器，我们也来一个在开发中常用的利器---抓包工具Charles。

# 介绍：


在移动开发中，我们可能需要监听我们手持设备怎么请求网络请求，如我们检测请求网易新闻、网易财经或者其他APP的数据，这就需要我们在网络请求时，有一个中间类似拦截、监听的代理工具，这就是charles。




先来打开Charles的[官网](http://www.charlesproxy.com/)
>Charles is an HTTP proxy / HTTP monitor / Reverse Proxy that enables a developer to view all of the HTTP and SSL / HTTPS traffic between their machine and the Internet. This includes requests, responses and the HTTP headers (which contain the cookies and caching information).

Charles是一个代理工具，它可以时刻记录着我们通过它的网络请求request、响应respone以及他们的http header（像cookie、cache 信息等）。


# 监听网络请求和响应

它是一个收费软件，很值得拥有，默认你下载安装完毕。

## Charles代理设置


设置Charles的设置

![image](http://ownwell.github.io/image/charles_setting.png)

![image](http://ownwell.github.io/image/charles_setting_port.png)

确保你的手机和电脑连接的是同一个AP热点。

看先你电脑的ip吧。

![image](http://ownwell.github.io/image/charles_ip.png)

摁住你的option键，点击你的wifi小图标，或者直接ifconfig或者ipconfig查看ip。

为了不必要的网络数据，将你的proxy--> Mac OS X proxy 和FIrfox代理给取消。

## 手机端设置

点击你的Setting--> WLan 长按你连接的WIfi热点-->修改网络(你连接的热点一定要和Pc一致)

![image](http://ownwell.github.io/image/charles_phone-setting1.png)



确保你输入的代理服务器的主机名为你的Pc 的ip地址，端口为刚才charles设置的端口（就是图片中一个8888端口，你要根据自己的设置修改）。

确认点击保存。

### 连接代理服务器

手机现在的网络请求就开始走Charles，但是有一个提示：

![image](http://ownwell.github.io/image/charles_network.png)

Allow就可以看到请求的网易新闻客户端的一个接口：


![image](http://ownwell.github.io/image/charles_resquet_respone.png)





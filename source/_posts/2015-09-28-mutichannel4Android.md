title: "Android的快速多渠道打包"
date: 2015-09-28 23:09:32
tags: [多渠道,python]
categories: [Android]
---

![](http://7xj9f0.com1.z0.glb.clouddn.com/b1f9ddc451da81cb302607745666d0160b2431d9.jpg)

# 概要

听说Google play快来中国了，不知道这个消息对于奋战在一线的Android开发者来说是不是个福音，终于可以不用翻墙下载Android SDK、下载Android Studio（避免XCodeGhost闹剧），不过也有一个威胁，对国内的引用市场什么360、百度、豌豆荚等渠道市场是不是会有影响呢？就让我们拭目以待吧。
<!-- more -->


其实这篇博客应该在上周就要写的，因为本人搬家的缘故一直此次往后退，今天终于可以腾出时间来写这篇博客了----Android的多渠道打包。

<!-- more -->
# 多渠道打包
1. [插件打包法：](http://ownwell.github.io/2014/06/24/package4Android/)

2. [Android的productFlavors打包法](http://ownwell.github.io/2014/11/17/GradlewMutilChannelInAndroid/)


不过现在你在product Flavor这个打包仍旧很慢，网易财经30多个渠道一个小时，这个是我们难以忍受的，怎么办怎么办？

# 快速打包方案

这个问题也困扰着美团的一群工程师们，美团的[多渠道打包](http://tech.meituan.com/mt-apk-packaging.html)。

第一种是maven打包和Gradle的product Flavor差不多，替换一次渠道名称，打一个包，100渠道，100*3分钟。

第二种其实就是现在有些黑心公司，利用反编译技术改变一些文件、重新签名，再打包，很是不正规。

第三种就是今天的重头戏，就是将mate-inf下新建一个空文件，这文件的名称中有渠道名称，每次只需要读取渠道名称即可，这个脚本用的是Python。


说起来简单，就来一步步分析吧。


## 渠道 
准备好各个渠道名，放到一个文件里，每个渠道一行：

```Python
wandoujia
hiapk
91
QQ
360
miliao
goapk
```

## Python脚本

创建一个空文件
```Python
 空文件 便于写入此空文件到apk包中作为channel文件
src_empty_file = 'info/imoney.txt'
# 创建一个空文件（不存在则创建）
f = open(src_empty_file, 'w') 
f.close()

```


```Python

# 获取当前目录中所有的apk源包
src_apks = []
# python3 : os.listdir()即可，这里使用兼容Python2的os.listdir('.')
for file in os.listdir('.'):
    if os.path.isfile(file):
        extension = os.path.splitext(file)[1][1:]
        if extension in 'apk':
            src_apks.append(file)

```

```Python

# 获取渠道列表
channel_file = 'info/channel.txt'
f = open(channel_file)
lines = f.readlines()
f.close()
````


```Python

for src_apk in src_apks:
    # file name (with extension)
    src_apk_file_name = os.path.basename(src_apk)
    # 分割文件名与后缀
    temp_list = os.path.splitext(src_apk_file_name)
    # name without extension
    src_apk_name = temp_list[0]
    # 后缀名，包含.   例如: ".apk "
    src_apk_extension = temp_list[1]
    
    # 创建生成目录,与文件名相关
    output_dir = 'output_' + src_apk_name + '/'
    # 目录不存在则创建
    if not os.path.exists(output_dir):
        os.mkdir(output_dir)
        
    # 遍历渠道号并创建对应渠道号的apk文件
    for line in lines:
        # 获取当前渠道号，因为从渠道文件中获得带有\n,所有strip一下
        target_channel = line.strip()
        # 拼接对应渠道号的apk
        target_apk = output_dir + src_apk_name + "_" + target_channel + src_apk_extension  
        # 拷贝建立新apk
        shutil.copy(src_apk,  target_apk)
        # zip获取新建立的apk文件
        zipped = zipfile.ZipFile(target_apk, 'a', zipfile.ZIP_DEFLATED)
        # 初始化渠道信息
        empty_channel_file = "META-INF/channel_{channel}".format(channel = target_channel)
        # 写入渠道信息
        zipped.write(src_empty_file, empty_channel_file)
        # 关闭zip流
        zipped.close()

```

**
![](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150928-1@2x.png)

通过python可以直接运行，20s 30多个渠道就出来。
## 读取渠道名称
由于我们的将渠道信息放到了meta-inf下，而不是以前的那个放到Manifest文件，所以需要在meta-inf获取渠道名称，并将这个渠道手动设置到对应统计的SDK（如Umeng，百度等）中去。

```Java

/**
 * @author Cyning
 * @since 2015.09.17
 * Time    下午9:07
 */

public class MutiChannelConfig {

    public static final String Version         = "version";

    public static final String Channel         = "channel";

    public static final String DEFAULT_CHANNEL = "netease";

    public static final String Channel_File = "channel";

    public static String getChannelFromMeta(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF") && entryName.contains("channel_")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        if (split != null && split.length >= 2) {
            return ret.substring(split[0].length() + 1);
        } else {
            return DEFAULT_CHANNEL;
        }
    }

    /**
     * 得到渠道名
     * @param mContext
     *
     * @return
     */
    public static String getChannel(Context mContext) {
        String channel = "";
        if (isNewVersion(mContext)) {//是新版本
            LayzLog.e("isNewVersion  %s", "isNewVersion");
            saveChannel(mContext);//保存当前版本
            channel = getChannelFromMeta(mContext);
        } else {
            channel = getCachChannel(mContext);
        }
        return channel;
    }

    /**
     * 保存当前的版本号和渠道名
     * @param mContext
     */
    public static void saveChannel(Context mContext) {
        SharedPreferences mSettinsSP = mContext.getSharedPreferences(Channel_File, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mSettinsEd = mSettinsSP.edit();
        mSettinsEd.putString(Version, APPUtils.getAppVersion(mContext));
        mSettinsEd.putString(Channel, getChannelFromMeta(mContext));
        //提交保存
        mSettinsEd.commit();
    }

    private static boolean isNewVersion(Context mContext) {
        SharedPreferences mSettinsSP = mContext.getSharedPreferences(Channel_File, Activity.MODE_PRIVATE);
        String version = APPUtils.getAppVersion(mContext);
        LayzLog.e("version%s", version);
        return !mSettinsSP.getString(Version, "").equals(version);
    }

    private static String getCachChannel(Context mContext) {
        SharedPreferences mSettinsSP = mContext.getSharedPreferences(Channel_File, Activity.MODE_PRIVATE);
        return mSettinsSP.getString(Channel, DEFAULT_CHANNEL);
    }
}

```

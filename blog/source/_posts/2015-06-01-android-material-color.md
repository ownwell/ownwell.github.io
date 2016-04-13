title: "Android Material的主题"
date: 2015-06-01 23:41:42
tags: [Material]
categories: [Android]
---

5月28日，一年一度的Google IO在美国旧金山召开，Android M、 Brillo等众多亮点值得开发者关注。

但是本文要说的是Material Design，有点落后了，不过对于Andoid开发者，App的Material化是我们必须经历的事，那么会有那些问题呢？颜色 --- 是我们必须要改的。

# Activity

首先要明白的是我们的Activity不再是`ActionBarActivity`，而是`AppCompatActivity`,ActionBar不够灵活，以前就发现这个问题，`ToolBar`是个很好的替代,同时Toolbar可以作为ActionBar，具有更好的灵活性。

```xml
      <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="?attr/colorPrimary"
            android:minHeight="?attr/actionBarSize"
            app:popupTheme="@style/Theme.AppCompat.Light.DarkActionBar"
            app:theme="@style/Toolbar" />
```

>切记：Toolbar的背景色`android:background="?attr/colorPrimary"`一定要设置。


```xml

	setContentView(R.layout.main);

  setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
```


> 其实Activity是有默认的ActionBar，若是我们想通过Toolbar自己设置，记得将theme继承自Theme.AppCompat.Light.NoActionBar。

>Theme.AppCompat.Light.DarkActionBar 默认的有个黑色的ActiionBar





# color


以前设置actionbar的那一套记得替换：

![image](http://img0.tuicool.com/nMbi63.png)


提供一个UI设计的颜色配置模板：[link](http://www.materialpalette.com/red/red)这个是网易红。

>推荐阅读： 

>1. [sam的Material专题](http://blog.isming.me/tags/material-design/)
>2.  [Android Material Design之Toolbar与Palette实践](http://blog.csdn.net/bbld_/article/details/41439715)
>3. [Android Material Design](http://blog.csdn.net/bbld_/article/details/40400343)




title: "SwipeBackLayout的一个让人揪心的问题"
date: 2015-09-30 19:30:15
tags: [UI]
categories: [Android]
---

# 概述
Google 新品发布会，我就不想说了，太烂了，其他的不多说。

今天愣是被一个问题给搞蒙，虽然待会还要坐车回家过十一，但是还是趁着现在的时间，总结一下自己的问题。

看到网易财经的交互不是很友好，对于习惯了IOS手机操作的人来说，滑动就关掉当前页面是个很好的交互，对于单手操作尤其方便，但是Android系统是不提供这种交互的，所以有些APP就做了，如网易新闻、简书等，这能极大方便用户。
<!-- more -->


# SwipeBackLayout
这不我也给财经APP加上，用的是[SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)这个还蛮好用的，所以就重点推荐它吧。

使用就不用说了吧，直接继承`SwipeBackActivity`这个类,


```Java
public class SwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null) {
            return mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}

```
其他的不变，不过这样可以左右滑动，但是下面的页面是黑乎乎的，那是因为你只是滑动了Activity的contentView，至于他们的之间的关系，建议你google下Activity，Window，DecorView就知道了（要看最新的，DecorView以前是个线性布局，现在最新的是FrameLayout）。

所以需要将window设置为透明：
```Java
 <item name="android:windowIsTranslucent">true</item>
 ```
# 问题
 这样问题就来了，我以前是给activity设置了`windowAnimationStyle`,现在全部失效，我给跪了。
 
```Java
  <item name="android:activityOpenExitAnimation">@anim/hold</item>
  <item name="android:activityCloseEnterAnimation">@anim/hold</item>
  <item name="android:activityOpenEnterAnimation">@anim/slide_in_from_bottom</item>
  ……
 ```  
      
 这个也有人遇到了：[issue](https://github.com/ikew0ng/SwipeBackLayout/issues/3)。
 
 参照下面的问题，我找到这个了，发现居然又解决方案：[方案](http://blog.csdn.net/xuewater/article/details/36398803)。不过我们需要为Activity的task的其他属性。
 
 好了说多了多是泪，还是有时间自己也模仿着写个吧。
 
 十一了，快回家了，先祝媳妇十一快乐，感觉最对不起的就是她。
 
 


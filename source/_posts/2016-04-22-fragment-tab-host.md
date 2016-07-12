---
title: Fragment+Tab实现懒加载
date: 2016-04-22 23:12:58
tags: [Android]
categories: [Android]
---
# 前言
现在Google已经在新的APP UI规范中开始提倡使用底部导航栏（`BottomNavigation`），而不是以前的侧拉导航栏（`NavigationView`,对于大屏手机，这个规范是很人性的。`BottomNavigation`分为底部的Tab导航栏和上面的内容展示部分，实现上，每个Tab对应一个`Fragment`，同时也需要我们正确处理每个`Fragment`在Tab间进行切换，同时每次打开那个Tab时，那个Tab对应的`Fragment`才加载。
![tab](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20160422-1.png)
 >如上图：
  1. 当选中下面的Tab时,对应的Tab显示（其实在代码实现上就是Fragment能加载显示）
  2. 对于第一次加载的Tab，需要进行初始化和加载工作，而对于已经加载过的Tab,只需要加载即可，但是不能再次进行初始化工作。

就是说每一个tab对应一个`Fragemnt`，切换Tab时能正确切换到`Fragment`上。

Tab 1  ----> TabFragment1    
Tab 2  ----> TabFragment2

  # 方案一  FragmentTabHost
  在新的v4 support下有个类叫`FragemntTabHost`，是替代以前那种`TabHost`的方案，每个Tab就是`Fragment`。

  ```Xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"

    tools:context=".SendActivity"
    >

  <android.support.v7.widget.Toolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?attr/actionBarSize"
      android:background="?attr/colorPrimary"
      app:popupTheme="@style/AppTheme.PopupOverlay"
      >

  </android.support.v7.widget.Toolbar>

  <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:layout_below="@+id/toolbar"
      android:orientation="vertical" >

    <!-- 把FragmentLayout放在FragmentTabHost上面，这样tabs就在底部了，注意，id要自己添加了
     android:id="@+id/realtabcontent"
    -->
    <FrameLayout
        android:id="@+id/realtabcontent"
        android:layout_width="match_parent"
        android:layout_height="0dip"
        android:layout_weight="1" />
    <!--<View-->
        <!--android:id="@android:id/tabhost"-->
        <!--android:layout_width="match_parent"-->
        <!--android:layout_height="48dp"-->
        <!--&gt;-->
    <!--</>-->
    <android.support.v4.app.FragmentTabHost
        android:id="@android:id/tabhost"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        >
    </android.support.v4.app.FragmentTabHost>

  </LinearLayout>


</RelativeLayout>
```
可能会这么设置布局，为每个tab设置一个TabView。
```java
    View tab_msg1 = initTabItem(1);
    View tab_msg2 = initTabItem(2);
    View tab_msg3 = initTabItem(3);
    View tab_msg4 = initTabItem(4);

    //addTab(标题，跳转的Fragment，传递参数的Bundle)
    //ContactFragment自己定义一个extends Fragment的类就行了

    mTabHost.addTab(mTabHost.newTabSpec("Tag1").setIndicator(tab_msg1), ContactFragment.class, null);
    mTabHost.addTab(mTabHost.newTabSpec("Tag2").setIndicator(tab_msg2), ContactFragment.class, null);
    mTabHost.addTab(mTabHost.newTabSpec("Tag3").setIndicator(tab_msg3), ContactFragment.class, null);
    mTabHost.addTab(mTabHost.newTabSpec("Tag4").setIndicator(tab_msg4), ContactFragment.class, null);
    //设置tabs之间的分隔线不显示
    mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);


    mTabHost.setOnTabChangedListener(this);
```
只要监听每次Tab的切换事件`OnTabChangeListener`就可以了，至于每个`Fragment`怎么切换交给了`FragmentTabHost`来处理。

等等让我实验下。

----
coding 20分钟等待中。

-----
但(提)是(示)大家Tab有没有真正应用到项目中，我能说我用了么，为了`Fragemnt`我是操碎了心了啊，每次切换Tab，对应的`Fragment`都会回调`onCreateView`,`OnResume`等`Fragment`的方法，这个每次都会创建的`Fragment`的，很多初始化工作也会重新走一遍。好了，还是看看`FragmentTabHost`的source code吧。

```
private FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
       TabInfo newTab = null;
       for (int i=0; i<mTabs.size(); i++) {
           TabInfo tab = mTabs.get(i);
           if (tab.tag.equals(tabId)) {
               newTab = tab;
           }
       }
       if (newTab == null) {
           throw new IllegalStateException("No tab known for tag " + tabId);
       }
       if (mLastTab != newTab) {
           if (ft == null) {
               ft = mFragmentManager.beginTransaction();
           }
           if (mLastTab != null) {
               if (mLastTab.fragment != null) {
                   ft.detach(mLastTab.fragment);
               }
           }
           if (newTab != null) {
               if (newTab.fragment == null) {
                   newTab.fragment = Fragment.instantiate(mContext,
                           newTab.clss.getName(), newTab.args);
                   ft.add(mContainerId, newTab.fragment, newTab.tag);
               } else {
                   ft.attach(newTab.fragment);
               }
           }

           mLastTab = newTab;
       }
       return ft;
   }
```
源码很清晰解释了为什么，当切换Tab时，有两个Fragemnt，一个是
newTab，另一是mLastTab，mLastTab是被detach，newTab是add或者被attach，似乎这些有点蒙。
>detach()
会将view从UI中移除,和remove()不同,此时fragment的状态依然由FragmentManager维护。    
attach()
重建view视图，附加到UI上并显示。
一个Fragment的生命周期难道你忘了么，翠花上图：

![Fragment的生命周期](http://img.my.csdn.net/uploads/201211/29/1354170699_6619.png)。
被attach就会走`onAttach`后面的`onCreateView`等方法，被detach就会走`onDestoryView`等方法，好了知道为什么会出现Tab切换，切到目标`Fragemnt`上时`Fragemnt`会不断调用`onCreateView`、`onResume`等方法了。

这么控制每个`Fragemnt`真的好累，为了能更好控制`Fragment`我们是可以自己定义类似`FragemntTabHost`的东西，使得每次加载已经加载过的`Fragemnt`能只是显示个View，不要一次次调用`onCreateView`、`onResume`等方法。

# Hide() Or Show（）的方式
在使用Tab切换时，很多人在提倡`用FragmentManager`的hide()和show()的方法。

>transaction.hide()
隐藏当前的Fragment，仅仅是设为不可见，并不会销毁    
transaction.show()
显示之前隐藏的Fragment   

这个就稍微简单了点，对于一个`Fragment`，没add到transaction是就add，已经add了就show，其他不该显示的`Fragemnt`全部hide。就是这个简单残暴：


```java
private void switchFragemnt(String tag) {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    if (Tag1.equals(tag)){
      if (fragment2 != null && fragment2.isAdded()){
        ft.hide(fragment2);
      }
      if (fragment1 == null){
        fragment1 = ContactFragment.newInstance(1);
      }
      if (!fragment1.isAdded()){
        ft.add(R.id.contenter,fragment1,Tag1);
      }else {
        ft.show(fragment1);
      }


    }
    if (Tag2.equals(tag)){
      if (fragment1 != null && fragment1.isAdded()){
        ft.hide(fragment1);
      }
      if (fragment2 == null){
        fragment2 = ContactFragment.newInstance(2);
      }
      if (!fragment2.isAdded()){
        ft.add(R.id.contenter,fragment2,Tag2);
      }else {
        ft.show(fragment2);
      }


    }

    ft.commitAllowingStateLoss();
  }
  ```

  这个只是简单的逻辑代码，傻了点不过肯定你能看懂，这个再切换过程中，只是隐藏但是依然可以流畅切。
  后来总结这些自己也抽象了一个简单的类：

  ```java
  import java.util.HashMap;

  import android.content.Context;
  import android.os.Bundle;
  import android.support.v4.app.Fragment;
  import android.support.v4.app.FragmentActivity;
  import android.support.v4.app.FragmentTransaction;
  import android.view.View;
  import android.widget.TabHost;

  public class TabManager implements TabHost.OnTabChangeListener {

  	private final FragmentActivity mActivity;
  	private final TabHost          mTabHost;
  	private final int              mContainerId;
  	private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();

  	TabHost.OnTabChangeListener tabChangeListener;
  	TabInfo mLastTab;

  	static final class TabInfo {
  		private final String   tag;
  		private final Class<?> clss;
  		private final Bundle   args;
  		private       Fragment fragment;

  		TabInfo(String _tag, Class<?> _class, Bundle _args) {
  			tag = _tag;
  			clss = _class;
  			args = _args;
  		}
  	}

  	static class DummyTabFactory implements TabHost.TabContentFactory {
  		private final Context mContext;

  		public DummyTabFactory(Context context) {
  			mContext = context;
  		}

  		@Override
  		public View createTabContent(String tag) {
  			View v = new View(mContext);
  			v.setMinimumWidth(0);
  			v.setMinimumHeight(0);
  			return v;
  		}
  	}

  	public TabManager setTabChangeListener(TabHost.OnTabChangeListener tabChangeListener) {
  		this.tabChangeListener = tabChangeListener;
  		return this;
  	}

  	public TabManager(FragmentActivity activity, TabHost tabHost,
  			int containerId) {
  		mActivity = activity;
  		mTabHost = tabHost;
  		mContainerId = containerId;
  		mTabHost.setOnTabChangedListener(this);
  	}

  	public TabManager addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
  		tabSpec.setContent(new DummyTabFactory(mActivity));
  		String tag = tabSpec.getTag();

  		TabInfo info = new TabInfo(tag, clss, args);

  		// Check to see if we already have a fragment for this tab, probably
  		// from a previously saved state. If so, deactivate it, because our
  		// initial state is that a tab isn't shown.
  		info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
  		if (info.fragment != null && !info.fragment.isDetached()) {
  			FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
  			ft.hide(info.fragment);
  			ft.commit();
  		}

  		mTabs.put(tag, info);
  		mTabHost.addTab(tabSpec);
  		return this;
  	}

  	@Override
  	public void onTabChanged(String tabId) {

  		TabInfo newTab = mTabs.get(tabId);
  		if (mLastTab != newTab) {
  			FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
  			if (mLastTab != null) {
  				if (mLastTab.fragment != null) {
  					ft.hide(mLastTab.fragment);
  				}
  			}
  			if (newTab != null) {
  				if (newTab.fragment == null) {
  					newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
  					ft.add(mContainerId, newTab.fragment, newTab.tag);
  				} else {
  					ft.show(newTab.fragment);
  				}
  			}

  			mLastTab = newTab;
  			ft.commitAllowingStateLoss(); // 如果使用commit()，则从牛人协议页点同意后程序崩溃; java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
  			mActivity.getSupportFragmentManager().executePendingTransactions();
  			if (tabChangeListener != null){
  				tabChangeListener.onTabChanged(tabId);
  			}
  		}
  	}
  }
  ```


  使用：
  ```java
  mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
  View tab1 = addTab(mInflater, Constants.INDEX_1, R.drawable.ic_tab_1, R.string.1);
        mTabManager.addTab(mTabHost.newTabSpec(Constants.TAB_1).setIndicator(tabMarket), MainFragment.class, null);
```

title: "Activity生命周期的回调妙用"
date: 2015-05-28 20:08:16
tags: [Android源码]
categories: [Android]
---

今天闲来无事，查看源码，发现一个有意思的接口`Application.ActivityLifecycleCallbacks`

```Java
public interface ActivityLifecycleCallbacks {
        void onActivityCreated(Activity activity, Bundle savedInstanceState);
        void onActivityStarted(Activity activity);
        void onActivityResumed(Activity activity);
        void onActivityPaused(Activity activity);
        void onActivityStopped(Activity activity);
        void onActivitySaveInstanceState(Activity activity, Bundle outState);
        void onActivityDestroyed(Activity activity);
    }
```
这就让我想起一个以前经历过的事，要求怎么判断APP进入后台。
看你的招商银行客户端，进入后台（就是这个APP所有页面不可见）就会一个Notication提示APP在后台运行。那么问题来了，我们需要怎么样判断，这个APP进入后台？

其实很简单，就是为每个Activity都注册一个事件，有一个计数器来记录所有的Activity是可见的：当Acitivity在onResume时，计数器加一，当Activity在onStop时，计数器减一，当计数器为零就可以判断这个APP不在我们手机的可见页面内，也就是我们说的进入了后台。

怎么监听Activity的onResume和onStop，我记得以前我们是自己写了一个BaseActivity，里面有相关的生命周期的监听，所有的Activity都要继承BaseActivity。但是在Acitivity代码我们我们发现这几句：

`Activity`源码
```Java

 protected void onCreate(@Nullable Bundle savedInstanceState) {
        
        ……
        mFragments.dispatchCreate();
        getApplication().dispatchActivityCreated(this, savedInstanceState);
        
        ……
    }
    
    
  protected void onStop() {
        getApplication().dispatchActivityStopped(this);
  }
  
  ……
```

在Application里我们也去看看这个dispatchActivity*****（Create、Start、Resume等其他生命周期函数）是干嘛的：

```Java

 void dispatchActivityStopped(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (int i=0; i<callbacks.length; i++) {
                ((ActivityLifecycleCallbacks)callbacks[i]).onActivityStopped(activity);
            }
        }
    }
    
    private Object[] collectActivityLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (mActivityLifecycleCallbacks) {
            if (mActivityLifecycleCallbacks.size() > 0) {
                callbacks = mActivityLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
        
      }
      
      public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (mActivityLifecycleCallbacks) {
            mActivityLifecycleCallbacks.remove(callback);
        }
    }

      
      
   private ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks =
            new ArrayList<ActivityLifecycleCallbacks>();
```

这就是说我们在`Application`放了一个监听器的数组ArrayList<ActivityLifecycleCallbacks>，当在activity生命周期有变化出发dispatchActivity*****时，这里面的监听器都可以监听到，所有我们就可以通过注册和注销注册来坚挺所有的Activity的处理过程。

这也是一个妙用。

希望对大家有用。
    


   
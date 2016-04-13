---
layout: post
title: "[Android]Android UI线程和非UI线程"
tags: [Android]
categories: [Android]
---
# 概述--UI线程及Android的单线程模型原则 #
-----


   >当应用启动，系统会创建一个主线程（main thread）。

   这个主线程负责向UI组件分发事件（包括绘制事件），也是在这个主线程里，你的应用和Android的UI组件（components from the Android UI toolkit (components from the `android.widget` and `android.view packages`)）发生交互。

   所以main thread也叫UI thread也即UI线程。

  
   系统不会为每个组件单独创建线程，在同一个进程里的UI组件都会在UI线程里实例化，系统对每一个组件的调用都从UI线程分发出去。

   结果就是，响应系统回调的方法（比如响应用户动作的`onKeyDown`()和各种生命周期回调）永远都是在UI线程里运行。

 

   当App做一些比较重（intensive）的工作的时候，除非你合理地实现，否则单线程模型的performance会很poor。

  特别的是，如果所有的工作都在UI线程，做一些比较耗时的工作比如访问网络或者数据库查询，都会阻塞UI线程，导致事件停止分发（包括绘制事件）。对于用户来说，应用看起来像是卡住了，更坏的情况是，如果UI线程blocked的时间太长（大约超过5秒），用户就会看到ANR（application not responding）的对话框。

  另外，Andoid UI toolkit并不是线程安全的，所以你不能从非UI线程来操纵UI组件。你必须把所有的UI操作放在UI线程里，所以Android的单线程模型有两条原则：　　

1. 不要阻塞ui线程。

2. 不要在UI线程之外访问Android UI toolkit（主要是这两个包中的组件：android.widget and android.view）。

 

#使用Worker线程#
-----

　　根据单线程模型的两条原则，首先，要保证应用的响应性，不能阻塞UI线程，所以当你的操作不是即时的那种（not instantaneous），你应该把他们放进单另的线程中（叫做background或者叫worker线程）。

　　比如点击按钮后，下载一个图片然后在`ImageView`中展示：

```Java
 	public void onClick(View v) {
    new Thread(new Runnable() {
        public void run() {
            Bitmap b = loadImageFromNetwork("http://example.com/image.png");
            mImageView.setImageBitmap(b);
        }
    }).start();
    }
``` 
 

　　这段代码用新的线程来处理网络操作，但是它违反了第二条原则：

>Do not access the Android UI toolkit from outside the UI thread.</br>
**从非ui线程访问ui组件会导致未定义和不能预料的行为。**

 

　　为了解决这个问题，Android提供了一些方法，从其他线程访问UI线程：
```Java
	Activity.runOnUiThread(Runnable)
	View.post(Runnable)
	View.postDelayed(Runnable, long)
```

　　比如，上面这段代码可以这么改：

```Java
	public void onClick(View v) {
   		new Thread(new Runnable() {
        public void run() {
            final Bitmap bitmap = loadImageFromNetwork("http://example.com/image.png");
            mImageView.post(new Runnable() {
                public void run() {
                    mImageView.setImageBitmap(bitmap);
                }
            });
        }
    }).start();
	}
```
 

　　这么改之后就是线程安全的了。

　　但是，当操作变得复杂的时候，这种代码会变得非常复杂，为了处理非UI线程和UI线程之间更加复杂的交互，可以考虑在worker线程中使用一个`Handler`，来处理UI线程中传来的消息。

　　也可以继承这个类`AsyncTask` 。

 

<h1>Communicating with the UI Thread</h1>
-----


　　只有在UI线程中的对象才能操作UI线程中的对象，为了将非UI线程中的数据传送到UI线程，可以使用一个 Handler运行在UI线程中。

　　`Handler`是Android framework中管理线程的部分，一个`Handler`对象负责接收消息然后处理消息。

　　你可以为一个新的线程创建一个`Handler`，也可以创建一个`Handler`然后将它和已有线程连接。

　　如果你将一个Handler和你的UI线程连接，处理消息的代码就将会在UI线程中执行。

 

　　可以在你创建线程池的类的构造方法中实例化`Handler`的对象，然后用全局变量存储这个对象。

　　要和UI线程连接，实例化`Handler`的时候应该使用`Handler`(`Looper`) 这个构造方法。

　　这个构造方法使用了一个 `Looper` 对象，这是Android系统中线程管理的framework的另一个部分。

　　当你用一个特定的 `Looper`实例来创建一个 Handler`时，这个 `Handler`就运行在这个 `Loope`r的线程中。

 

　　在`Handle`r中，要覆写`handleMessage()` 方法。Android系统会在`Handler`管理的相应线程收到新消息时调用这个方法。

　　一个特定线程的所有`Handler`对象都会收到同样的方法。（这是一个“一对多”的关系）。

 

#参考资料#

　　官方Training: 与UI线程通信：

　　http://developer.android.com/training/multiple-threads/communicate-ui.html

　　Guides: Processes and Threads

　　http://developer.android.com/guide/components/processes-and-threads.html

 

　　类参考：

　　http://developer.android.com/reference/android/os/Looper.html

　　http://developer.android.com/reference/android/os/Handler.html

　　http://developer.android.com/reference/android/os/HandlerThread.html

 

　　博客：

　　Android的线程使用来更新UI----Thread、Handler、Looper、TimerTask等：

　　http://www.cnblogs.com/playing/archive/2011/03/24/1993583.html
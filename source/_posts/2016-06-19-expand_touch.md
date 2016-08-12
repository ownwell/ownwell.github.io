---
title: 扩大view的点击区域
tags: [View]
categories: [Android]
date: 2016-06-19 17:54:20
---
# 前言
再做一个一个页面，发现有些小Button实在是很难击中,可能你会想各种办法，比如在外面套一个大的容器、把Padding设置的大一点等等，今天给你提供一个新思路。
<!-- more-->

# 扩大view的点击区域

今天在看View代码时，发现有个方法叫`setTouchDelegate`，后来查阅了一下发现这个实际上是个代理类，我们可以通过它的父组件来扩大点击区域。

<img src="http://7xj9f0.com1.z0.glb.clouddn.com/md/1470736727319.png" width="299"/>

代码很简单，直接贴上吧：

```Java
/**
     * 扩大View的触摸和点击响应范围,最大不超过其父View范围
     *
     * @param view
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void expandViewTouchDelegate(final View view, final int top,
                                               final int bottom, final int left, final int right) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                view.setEnabled(true);
                view.getHitRect(bounds);

                bounds.top -= top;
                bounds.bottom += bottom;
                bounds.left -= left;
                bounds.right += right;

                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }

    /**
     * 还原View的触摸和点击响应范围,最小不小于View自身范围
     *
     * @param view
     */
    public static void restoreViewTouchDelegate(final View view) {

        ((View) view.getParent()).post(new Runnable() {
            @Override
            public void run() {
                Rect bounds = new Rect();
                bounds.setEmpty();
                TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                if (View.class.isInstance(view.getParent())) {
                    ((View) view.getParent()).setTouchDelegate(touchDelegate);
                }
            }
        });
    }
 ```
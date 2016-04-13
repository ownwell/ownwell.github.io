---
layout: post
title: "[Android]如何实现无限滚动的ListViw/GridView"
tags: [Android]
categories: [Android]
---
ListView和GridView已经成为原生的Android应用实现中两个最流行的设计模式。目前，这些模式被大量的开发者使用，主要是因为他们是简单而直接的实现，同时他们提供了一个良好，整洁的用户体验。

对于ListView和GridView一个共同的需求就是在用户不断向下滚动，组件仍能动态的加载更多地加载更多数据。这篇博客就将带领大家实现在ListView和GridView中这个功能。

我们需要的主要组件就是我们的InfiniteScrollListener类，这个类是继承于OnScrollListener，就让我们来看下具体的代码实现：
# 实现代码 #

    public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private int bufferItemCount = 10;
    private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true; 
    public InfiniteScrollListener(int bufferItemCount) {
        this.bufferItemCount = bufferItemCount;
    } 
    public abstract void loadMore(int page, int totalItemsCount);
 
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }
 
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        if (totalItemCount < itemCount) {
            this.itemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.isLoading = true; }
        }
 
        if (isLoading && (totalItemCount > itemCount)) {
            isLoading = false;
            itemCount = totalItemCount;
            currentPage++;
        }
 
        if (!isLoading && (totalItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)) {
            loadMore(currentPage + 1, totalItemCount);
            isLoading = true;
        }
      } 
    }


添加到事件到ListView中：

    yourListView.setOnScrollListener(new InfiniteScrollListener(5) {
  
		@Override
		public void loadMore(int page, int totalItemsCount) {
			List<HashMap<String, String>> newData = loader.loadData();
			dataList.addAll(newData);
			adapter.notifyDataSetChanged();
		 }
	});
    
如上，我们已经将这个类作为抽象类，这是一个很好的设计。我们的InfiniteScrollListener类实现了onScroll（）方法，但是没有实现loadMore（），而是又给实现类来实现。

onScroll()方法在我们滚动时，程序会自动调用。所以我们推荐在这个方法里，不要进行过重的处理和数据计算，因为滚动是很频繁，这个方法调用也是很频繁的。

为了实现这个效果，我们只需要在实现InfiniteScrollListener，并将这个实现类设置给ListView/GridView的setOnScrollListener()方法，就像是第二个代码片段的匿名类。

我们实现了InfiniteScrollListener类，我们也需要实现loadMore（）方法，在这个方法里，我们可以给ListView/GridView添加Item并通过notifyDataSetChanged()通知ListView/GridView数据发生改变。当然我们可以手动添加本地数据，也可以l从数据库或者RESTful service来Load更多的数据。

这就是我们在Android中实现的在ListView/GridView的可以无限滚动的所作的。对于有大量信息，ListView/GridView无疑有着很好的出处用户体验。

 

原文:http://www.avocarrot.com/blog/implement-infinitely-scrolling-list-android/
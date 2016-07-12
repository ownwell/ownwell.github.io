title: "初尝Ruby"
date: 2015-05-23 17:27:38
tags: [Ruby]
categories: [编程]
---

Ruby作为一个小众语言，很适合没事玩玩，这就上手了，开始吧。

首先是安装Ruby，这个是个很蛋疼的问题，不过没关系，咱们可以通过[HomeBrew来安装Ruby](http://blog.csdn.net/maojudong/article/details/7920578)，

安装成功了就看下版本吧：

```Ruby

ruby -v

```

出来相关版本吧。

开始第一个程序吧。

```Shell
 $ cd workSpace
 $ mk Ruby
 $ touch test.rb
 $ vi test.rb
```

开始一个HellWorl吧

```Ruby 
#!/usr/bin/ruby -w

puts "Hello, Ruby!";
```

跳出vi的编辑模式

```Shell

 $ chmod a+x test.rb
 $ Ruby ./test.rb
 
```




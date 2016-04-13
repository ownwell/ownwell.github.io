title: "Git学习笔记"
date: 2015-06-08 21:04:47
tags: [Git]
categories:	[Tools]
---


Git的常用命令

## 准备工作
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Git 提供了一个叫做 git config 的工具（注：实际是 `git-config` 命令，只不过可以通过 git 加一个名字来呼叫此命令。），专门用来配置或读取相应的工作环境变量。而正是由这些环境变量，决定了 Git 在各个环节的具体工作方式和行为。这些变量可以存放在以下三个不同的地方：

* /etc/gitconfig 文件：系统中对所有用户都普遍适用的配置。若使用 git config 时用 --system 选项，读写的就是这个文件。
* ~/.gitconfig 文件：用户目录下的配置文件只适用于该用户。若使用 git config 时用 --global 选项，读写的就是这个文件。
* 当前项目的 Git 目录中的配置文件（也就是工作目录中的 .git/config 文件）：这里的配置仅仅针对当前项目有效。每一个级别的配置都会覆盖上层的相同配置，所以 .git/config 里的配置会覆盖 /etc/gitconfig 中的同名变量。
在 

### 用户信息
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一般在新的系统上，我们都需要先配置下自己的 Git 工作环境。配置工作只需一次，以后升级时还会沿用现在的配置。当然，如果需要，你随时可以用相同的命令修改已有的配置。

```Shell
$ git config --global user.name "John Doe"
$ git config --global user.email johndoe@example.com
```

>如果用了 --global 选项，那么更改的配置文件就是位于你用户主目录下的那个，以后你所有的项目都会默认使用这里配置的用户信息。如果要在某个特定的项目中使用其他名字或者电邮，只要去掉 --global 选项重新配置即可，新的设定保存在当前项目的 .git/config 文件里。

### 文本编辑器
默认的是vi或者vim

```Shell
$ git config --global core.editor emacs

```

### 差异分析工具

```Shell
$ git config --global merge.tool vimdiff
```


### 查看配置信息

```Shell

$ git config --list
>user.name=Scott Chacon
>user.email=schacon@gmail.com
>color.status=auto
>color.branch=auto
>color.interactive=auto
>color.diff=auto
...

```

查看用户名：

```Shell
git config user.name
>Scott Chacon
```

帮助文档

想查看帮助，可以通过一下三种方法：

```Shell
$ git help <verb>
$ git <verb> --help
$ man git-<verb>
````

如 你想查看Git config
只需要如下命令：

```Shell

$ git config --help

```


# 取得项目的 Git 仓库
有两种取得 Git 项目仓库的方法。第一种是在现存的目录下，通过导入所有文件来创建新的 Git 仓库(本地仓库)。第二种是从已有的 Git 仓库克隆出一个新的镜像仓库来（远程仓库）。




参考文章

[git入门指南使用资料汇总及文章推荐
](http://ixirong.com/2014/11/19/the-way-to-learn-git/)

[手把手教你使用Git](http://blog.jobbole.com/78960/)

[pro git](http://git-scm.com/book/zh/v1)



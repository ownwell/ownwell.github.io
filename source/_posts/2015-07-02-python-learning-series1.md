title: "Python学习笔记(2)"
date: 2015-07-02 22:34:58
tags: [Python]
categories: [编程,Python]
---

# 概述
记得以前学习C++时，首先学的就是面向过程编程，所有的操作都是通过一个个的函数实现，一个好的函数，可以被导出调用，省时省力，函数是面向过程的一个主要元素。

那么函数在Python有什么好玩的呢？

# 定义一个函数

可以定义一个由自己想要功能的函数，以下是简单的规则：

1. 函数代码块以def关键词开头，后接函数标识符名称和圆括号()。
2. 任何传入参数和自变量必须放在圆括号中间。圆括号之间可以用于定义参数。
3. 函数的第一行语句可以选择性地使用文档字符串—用于存放函数说明。
4. 函数内容以冒号起始，并且缩进。
5. Return[expression]结束函数，选择性地返回一个值给调用方。不带表达式的return相当于返回 None。

```Python
def functionname( parameters ):
   "函数_文档字符串"
   function_suite
   return [expression]
``


来个例子吧：
```Python
#!/usr/bin/python

def add(num1,num2):
    return num1+num2;

num1 = 1
num2 = 2

total = add(num1,num2)

print "total = ",total
```

打印结果：
```Pyhon
total =  3
```
# 参数

## 默认参数
函数名唯一，不像java中一个参数和两个参数的同名参数是同一个，但是Python不是，因为Python是弱语言。
函数传参，传引用改变引用值，跳出函数应用是改变的，但是对于一般的参数这是不可以的，同时参数允许默认值,对于Python参数是可以带上原有的参数名字的，顺序可与定义函数的顺序不同。

```Python

def printStuInfo(name="name",age=2,stuNo=2009,className="软件工程"):
    print "name=%s,age=%d,stuNo=%d,className=%s"%(name,age,stuNo,className)


printStuInfo(name="Cyning",stuNo=2009,age=25

```

## 关键字参数

可变参数允许你传入0个或任意个参数，这些可变参数在函数调用时自动组装为一个tuple。而关键字参数允许你传入0个或任意个含参数名的参数，这些关键字参数在函数内部自动组装为一个dict。

```Python
def person(name, age, **kw):
    print 'name:', name, 'age:', age, 'other:', kw
```

函数person除了必选参数name和age外，还接受关键字参数kw。在调用该函数时，可以只传入必选参数.

```Python
>>> person('Michael', 30)
name: Michael age: 30 other: {}
```

```Python
>>> person('Bob', 35, city='Beijing')
name: Bob age: 35 other: {'city': 'Beijing'}
>>> person('Adam', 45, gender='M', job='Engineer')
name: Adam age: 45 other: {'gender': 'M', 'job': 'Engineer'}
```

关键字参数有什么用？它可以扩展函数的功能。比如，在person函数里，我们保证能接收到name和age这两个参数，但是，如果调用者愿意提供更多的参数，我们也能收到。试想你正在做一个用户注册的功能，除了用户名和年龄是必填项外，其他都是可选项，利用关键字参数来定义这个函数就能满足注册的需求。

和可变参数类似，也可以先组装出一个dict，然后，把该dict转换为关键字参数传进去：

```Python
>>> kw = {'city': 'Beijing', 'job': 'Engineer'}
>>> person('Jack', 24, city=kw['city'], job=kw['job'])
name: Jack age: 24 other: {'city': 'Beijing', 'job': 'Engineer'}
```
当然，上面复杂的调用可以用简化的写法：

```Python
>>> kw = {'city': 'Beijing', 'job': 'Engineer'}
>>> person('Jack', 24, **kw)
name: Jack age: 24 other: {'city': 'Beijing', 'job': 'Engineer'}
```
## 参数组合

在Python中定义函数，可以用必选参数、默认参数、可变参数和关键字参数，这4种参数都可以一起使用，或者只用其中某些，但是请注意，参数定义的顺序必须是：必选参数、默认参数、可变参数和关键字参数。

比如定义一个函数，包含上述4种参数：

```Python
def func(a, b, c=0, *args, **kw):
    print 'a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw
```
args是一个tuple，kw是dict.

在函数调用的时候，Python解释器自动按照参数位置和参数名把对应的参数传进去。

```Python
>>> func(1, 2)
a = 1 b = 2 c = 0 args = () kw = {}
>>> func(1, 2, c=3)
a = 1 b = 2 c = 3 args = () kw = {}
>>> func(1, 2, 3, 'a', 'b')
a = 1 b = 2 c = 3 args = ('a', 'b') kw = {}
>>> func(1, 2, 3, 'a', 'b', x=99)
a = 1 b = 2 c = 3 args = ('a', 'b') kw = {'x': 99}
```
最神奇的是通过一个tuple和dict，你也可以调用该函数：

```Python
>>> args = (1, 2, 3, 4)
>>> kw = {'x': 99}
>>> func(*args, **kw)
a = 1 b = 2 c = 3 args = (4,) kw = {'x': 99}
```
所以，对于任意函数，都可以通过类似func(*args, **kw)的形式调用它，无论它的参数是如何定义的。


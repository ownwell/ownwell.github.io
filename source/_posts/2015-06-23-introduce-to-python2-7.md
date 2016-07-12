title: "Python学习笔记(1)"
date: 2015-06-23 22:38:00
tags: [Python]
categories: [编程,Python]
---

python是一门很方便的脚本程序，在开发过程中，可以编写一些python脚本来作为工具使用。我也很感兴趣，想通过它来实现自己的爬虫。

教程参考：

1. [廖学峰的Python教程](http://www.liaoxuefeng.com/wiki/001374738125095c955c1e6d8bb493182103fac9270762a000)
2. [3wSchool Python教程](http://www.w3cschool.cc/python/python-tutorial.html)



# python的基本数据结构

在python有Number(数字)、String（字符串）、列表（List）、元组（tuple）、Dictionary（字典）。


## Python数字

Python支持四种不同的数值类型：

1. int（有符号整型）
2. long（长整型[也可以代表八进制和十六进制]）
3. float（浮点型）
4. complex（复数）

其数字的类型示例如下：
![image](http://7xj9f0.com1.z0.glb.clouddn.com/QQ20150623-1@2x.png)


## Python字符串

字符串或串(String)是由数字、字母、下划线组成的一串字符。

python的字串列表有2种取值顺序:

1. 从左到右索引默认0开始的，最大范围是字符串长度少1
1. 从右到左索引默认-1开始的，最大范围是字符串开头

当使用以冒号分隔的字符串，python返回一个新的对象，结果包含了以这对偏移标识的连续的内容，左边的开始是包含了下边界。

```Python

#coding=utf-8
#!/usr/bin/python

str = 'Hello World!'

print str # 输出完整字符串
print str[0] # 输出字符串中的第一个字符
print str[2:5] # 输出字符串中第三个至第五个之间的字符串
print str[2:] # 输出从第三个字符开始的字符串
print str * 2 # 输出字符串两次
print str + "TEST" # 输出连接的字符串

```

输出结果：
```Python

Hello World!
H
llo
llo World!
Hello World!Hello World!
Hello World!TEST

```

## Python列表

List（列表） 是 Python 中使用最频繁的数据类型。和**Java中的List**有点类似，但是不同的是里面的元素可以是不同类型的。
列表可以完成大多数集合类的数据结构实现。它支持字符，数字，字符串甚至可以包含列表（所谓嵌套）。

列表用[ ]标识。是python最通用的复合数据类型。看这段代码就明白。

```Python
coding=utf-8
#!/usr/bin/python

list = [ 'abcd', 786 , 2.23, 'john', 70.2 ]
tinylist = [123, 'john']

print list # 输出完整列表
print list[0] # 输出列表的第一个元素
print list[1:3] # 输出第二个至第三个的元素 
print list[2:] # 输出从第三个开始至列表末尾的所有元素
print tinylist * 2 # 输出列表两次
print list + tinylist # 打印组合的列表
```

输出：

```Python
['abcd', 786, 2.23, 'john', 70.2]
abcd
[786, 2.23]
[2.23, 'john', 70.2]
[123, 'john', 123, 'john']
['abcd', 786, 2.23, 'john', 70.2, 123, 'john']
```


## Python元组

元组用"()"标识。内部元素用逗号隔开。但是元素**不能二次赋值，相当于只读列表**。

```Python
#coding=utf-8
#!/usr/bin/python

tuple = ( 'abcd', 786 , 2.23, 'john', 70.2 )
tinytuple = (123, 'john')

print tuple # 输出完整元组
print tuple[0] # 输出元组的第一个元素
print tuple[1:3] # 输出第二个至第三个的元素 
print tuple[2:] # 输出从第三个开始至列表末尾的所有元素
print tinytuple * 2 # 输出元组两次
print tuple + tinytuple # 打印组合的元组

```

## Python元字典

字典(dictionary)是除列表以外python之中最灵活的内置数据结构类型。列表是有序的对象结合，字典是无序的对象集合。

两者之间的区别在于：字典当中的元素是通过键来存取的，而不是通过偏移存取。

字典用"{ }"标识。字典由索引(key)和它对应的值value组成。和Java中的**Map**是不是很类似。
```Python
#coding=utf-8
#!/usr/bin/python

dict = {}
dict['one'] = "This is one"
dict[2] = "This is two"

tinydict = {'name': 'john','code':6734, 'dept': 'sales'}


print dict['one'] # 输出键为'one' 的值
print dict[2] # 输出键为 2 的值
print tinydict # 输出完整的字典
print tinydict.keys() # 输出所有键
print tinydict.values() # 输出所有值
```


```Python
This is one
This is two
{'dept': 'sales', 'code': 6734, 'name': 'john'}
['dept', 'code', 'name']
['sales', 6734, 'john']
```







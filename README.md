# Naive Skip List

跳表是William Pugh于1990年提出的可替代自平衡树的数据结构[^1]。Naive Skip List是我实现的一个简单的skip list数据结构。

## Usages

### Initialization

Naive Skip list提供了一个默认参数的初始化方法：

```java
Skiplist list = new Skiplist();
```

### Insertion

向跳表中插入元素需调用`add(Integer data)`方法。如下面的示例：

```
list.add(data);
```

### Search

Naive Skip List支持两种形式的查找操作：

+ 寻找某个特定值
+ 寻找某个区间内的值

```java
list.search(data); // 寻找某个特定值
list.searchRange(begin, end); // 寻找某个区间内的值
```

## Simple Benchmark

测试对比了Naive Skip List和jdk中的TreeMap，具体结果如下：

> **Skiplist** benchmark:<br/>
> --- 200000 times INSERTATION: 0.1593991sec<br/>
> --- 200000 times SEARCH     : 0.1267432sec<br/>
> --- 200000 times ERASE      : 0.1214688sec<br/>
>
> **TreeMap** benchmark:<br/>
> --- 200000 times INSERTATION: 0.0836352sec<br/>
> --- 200000 times SEARCH     : 0.0659163sec<br/>
> --- 200000 times ERASE      : 0.0858767sec<br/>

测试代码为Main类中的benchmark方法：

```java
benchmark(200000);// 参数为测试的规模
```

[^ 1]: William Pugh (1990). *[Skip Lists: A Probabilistic Alternative to Balanced Trees](https://www.epaperpress.com/sortsearch/download/skiplist.pdf)*. <br/>
[^ 2]: ticki's bolg (2016). *Skiplist done right*. [OL]  http://ticki.github.io/blog/skip-lists-done-right <br/>

[^ 3]: somnzz (2019) 跳表这种高效的数据结构，值得每一个程序员掌握. [OL] https://zhuanlan.zhihu.com/p/54869087<br/>
[^ 4]: Dragon Energy (2015). Answer #34003558. [OL] https://stackoverflow.com/questions/31580869/skip-lists-are-they-really-performing-as-good-as-pugh-paper-claim/34003558#34003558<br/>


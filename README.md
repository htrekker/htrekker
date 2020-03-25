# 跳表

什么是跳表？

跳表是一种随机化的链式数据结构。



跳表在

## A Pretty Naive Implementation

这个实现主要参考了作者的原论文[^ 1]和<u>@somenzz的博客</u>[^ 3]在这里先表示感谢！

### 数据结构的设计

```java
class SkiplistNode {
	int value;
	SkiplistNode[] next;// 使用链式的存储方式不仅效率低下而且非常浪费空间

	public SkiplistNode(int level, int value) {
		next = new SkiplistNode[level];
		this.value = value;
	}

    /**
    * 返回这个node的高度
    */
	public int level() {
		return next.length;
	}
}
```

```java
private SkiplistNode root;
	private static final int MAX_LEVEL = 32;
	private int currentMaxLevel = 0;

	public int[] levels = new int[MAX_LEVEL + 1];
	private int size = 0;

	public Skiplist() {
		root = new SkiplistNode(32, -1);
	}
 
	...

}
```

### 搜索操作

```java
public boolean search(int target) {
	SkiplistNode cur = root;
	for (int i = currentMaxLevel - 1; i >= 0; i--) {
		// move along the level
		while (cur.next[i] != null && cur.next[i].value < target)
			cur = cur.next[i];
		// if target is found, stop search
		if (cur.next[i] != null && cur.next[i].value == target)
			return true;
        // move down the node
	}
	return false;
}
```

### 插入操作

```java
public void findPath(SkiplistNode[] path, int num, int level) {
	SkiplistNode cur = root;
	for (int i = Math.max(level, currentMaxLevel) - 1; i >= 0; i--) {
		while (cur.next[i] != null && cur.next[i].value < num)
			cur = cur.next[i];
		path[i] = cur;
	}
}
```



### 删除操作

```java
public boolean erase(int num) {
	SkiplistNode update = new SkiplistNode[currentMaxLevel];
	findPath(path, num, 0);

	SkiplistNode eraseable = path[0].next[0];
	if (eraseable == null || eraseable.value != num)
		return false;
	else {
		int level = eraseable.level();
		size--;
		levels[level]--;
		for (int i = 0; i < level; i++)
			path[i].next[i] = eraseable.next[i];
		return true;
	}
}
```



### 获取一个区间内的数

```java
public List<Integer> findRange(int begin, int end) {
	List<Integer> list = new ArrayList<>();
	
	SkiplistNode cur = root;
	for(int i = currentMaxLevel - 1; i >= 0; i--) {
		while(cur.next[i] != null && cur.next[i].value < begin)
			cur = cur.next[i];
		if(cur.next[i] != null && cur.next[i].value == begin) {
			cur = cur.next[i];
			break;
		}
	}
    while(cur != null && cur.value <= end) {
		list.add(cur.value);
		cur = cur.next[0];
	}
		
	return list;
}
```











skiptlist的一些优化技巧 [^ 2]

### O(1)复杂度的level generator

参考<u>这篇博客</u>[^ 2]所写的内容，一般在实现随机生成的`level`时，会重复调用随机数生成器。如原论文[^ 1]中提到的`level`的生成算法：

```c
randomLevel()
	lvl := 1
	-- random() that returns a random value in [0...1)
	while random() < p and lvl < MaxLevel do
		lvl := lvl + 1
	return lvl
```

由于随机数生成的随机数是均匀分布的。所以，只需要调用一次随机数生成器就可以完成。从而做到$O(1)$的时间复杂度。具体实现如下：

```java
public static int getLevel() {
	int flips = rand.nextInt();
	int level = 0;
	while (level++ < MAX_LEVEL && (flips & 1) != 0)
		flips >>= 1;

	return level;
}
```

StackOverFlow上的一篇回答[^ 4]对C++标准库中的set (底层是红黑树) 和其自己实现的一个跳表进行了benchmark。同时，这个回答还展示了其对自己实现的skiplist进行优化的过程。







[^ 1]: William Pugh (1990). *Skip Lists: A Probabilistic Alternative to Balanced Trees*. https://www.epaperpress.com/sortsearch/download/skiplist.pdf
[^ 2]: ticki's bolg (2016). *Skiplist done right*. [OL]  http://ticki.github.io/blog/skip-lists-done-right 

[^ 3]: somnzz (2019) *跳表这种高效的数据结构，值得每一个程序员掌握*. [OL] https://zhuanlan.zhihu.com/p/54869087
[^ 4]: Dragon Energy (2015). *Answer #34003558*. [OL] https://stackoverflow.com/questions/31580869/skip-lists-are-they-really-performing-as-good-as-pugh-paper-claim/34003558#34003558


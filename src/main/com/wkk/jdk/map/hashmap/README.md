## HashMap 源码解读

### 概述

HashMap主要用来存放键值对, 其基于哈希表的Map接口实现, 是常用的Java集合之一.JDK1.8之前HashMap由数组+链表组成, 其使用的是数组进行存储, 之后使用**链接法**解决哈希冲突. JDK1.8之后HashMap在解决哈希冲突时, 与之前不同的是, 当链表长度大于阈值(默认是8), 将链表转化为红黑树, 避免过长的链表从而降低性能(过长的话, 就相当于使用的是链表存储的了,每次的查找都会导致O(n)的时间), 同时转化为红黑树的时候也会进行判断（将链表转换成红黑树前会判断，如果当前数组的长度小于 64，那么会选择先进行数组扩容，而不是转换为红黑树），以减少搜索时间

HashMap的源码中，充斥个各种位运算代替常规运算的地方，以提升效率：
* 与运算替代模运算。用 hash & (table.length-1) 替代 hash % (table.length)
* 用if ((e.hash & oldCap) == 0)判断扩容后，节点e处于低区还是高区。


**形象理解**:

HashMap的底层为数组,  可以将其称之为**哈希桶** 每个桶里放的是链表.

其是**线程不安全的**允许**key为null, value为null**遍历时无序.

### 依赖

![](https://raw.githubusercontent.com/KongWiki/cloudImg/master/hashMap.png)

### 阅读套路

按照正常的逻辑， 首先基础的数据构造, 然后了解其构造方法， 之后了解常用的API(CRUD)操作即可。

* [ ] 增
* [ ] 删
* [ ] 改
* [ ] 查



### 从节点的构造开始

#### **HashMap**整体的构造

![](https://raw.githubusercontent.com/KongWiki/cloudImg/master/hashMap%E8%8A%82%E7%82%B9.jpeg)

#### **单个节点的构造**

```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash; // 哈希值
    final K key; // key
    V value; // value
    Node<K,V> next; // 链表中该元素指向的下一个节点
    // 单个节点包含的信息
    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
    public final K getKey()        { return key; }
    public final V getValue()      { return value; }
    public final String toString() { return key + "=" + value; }
    // 哈希函数
    // 一个节点的hash值是将key的哈希值和value的哈希值异或得到
    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }
    // 设置新的value 同时返回旧的value
    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }
    public final boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof Map.Entry) {
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            if (Objects.equals(key, e.getKey()) &&
                Objects.equals(value, e.getValue()))
                return true;
        }
        return false;
    }
}
```

#### 总结: 

1. **一个节点的hash值是将key的哈希值和value的哈希值异或得到**
2. 每个节点包含的信息
   1. key
   2. value
   3. hash值
   4. next指针（指向下一个元素）

#### 树节点的构造

```java
static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }
```

### 类的属性

```java
//初始容量为16 注释中说明了 必须为2的幂次
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
// 最大容量 2^30
static final int MAXIMUM_CAPACITY = 1 << 30;

// 默认负载因子0.75(若是构造函数中未说明)
static final float DEFAULT_LOAD_FACTOR = 0.75f;
// 桶中的节点数量大于8时转为红黑树
static final int TREEIFY_THRESHOLD = 8;
// 桶中的节点数量小于6时 转为链表
static final int UNTREEIFY_THRESHOLD = 6;
// 桶中结构转化为红黑树对应的table的最小大小
static final int MIN_TREEIFY_CAPACITY = 64;

// 哈希桶 存放链表 总是2的倍数
transient Node<K,V>[] table;
// 存放具体元素的集
transient Set<Map.Entry<K,V>> entrySet;
// 存放元素的个数，注意这个不等于数组的长度。
transient int size;
// 每次扩容和更改map结构的计数器
transient int modCount;
// threshold = table.length * loadFactor;
int threshold;
// 加载因子，用于计算哈希表元素数量的阈值。  
final float loadFactor;
```

#### 总结：

重点的属性：

1. table
   1. Node<K,V>数组
2. size
   1. 存放元素的个数，注意这个不等于数组的长度。
3. threshold
   1. threshold = 哈希桶.length * loadFactor;
4. loadFactor
   1. 加载因子

### 构造方法

#### HashMap()

```java
public HashMap() {
    // 默认构造方法 将负载因子设置为默认值 0.75
    // 假设默认初始化空间大小为16, 则元素数量达到16*0.75=12时, 会进行扩容.
    this.loadFactor = DEFAULT_LOAD_FACTOR; 
}
```

#### HashMap(int)

```java
public HashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}
```

#### HashMap(int , float)

```java
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    //  MAXIMUM_CAPACITY = 1 << 30;
    //  传入的初始化容量 <=最大容量
    if (initialCapacity > MAXIMUM_CAPACITY)
        
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    this.threshold = tableSizeFor(initialCapacity);
}
// 取与cap最近的且大于cap的2的次方值
// 比如cap为10， 则返回为16
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    // 执行或运算
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

#### HashMap(Map<?extends K, ?extends V>)

```java
// 新建一个哈希表，同时将另一个map m里的所有元素加入表中
public HashMap(Map<? extends K, ? extends V> m) {
    this.loadFactor = DEFAULT_LOAD_FACTOR;
    // 核心
    putMapEntries(m, false);
}

final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {
            // ①
            // 如果当前的表为空
            if (table == null) { // pre-size
                // 根据当前的元素的数量 获取阀值
                //  1.0F 的目的，是因为下面 (int) 直接取整，避免不够
                float ft = ((float)s / loadFactor) + 1.0F;
                // 修正阀值的边界, 阀值<= MAXIMUM_CAPACITY
                int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                         (int)ft : MAXIMUM_CAPACITY);
                // 返回一个新的阀值
                if (t > threshold)
                    threshold = tableSizeFor(t);
            }
            // ②
            // 若是当前元素的数量大于阀值
            else if (s > threshold)
                // 扩容
                resize();
            // ③
            // 遍历m 依次放入当前的表中
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
       }
}

```

##### **过程分析**

保证table容量足够

1. 为空--->①
2. 不为空
   1. 当前元素个数大于阀值 ---> ②
   2. 正常 ---> ③

###### ①

1. 如果当前的table的为空， 也就是说是将一个hashMap放入一个新的hashMap（初始化一个hashMap）

2. 然后对threshold进行修改
   1. 利用公式计算当前的threshold，并确保其≤2^30（MAXIMUM_CAPACITY）
   2. 如果计算出的threshold大于原先的threshold，则再次使用tableSizeFor进行计算

###### ②

扩容

* resize()

  ```java
  // 扩容方法
  final Node<K,V>[] resize() {
      	// oldTab 表示当前的哈希桶
          Node<K,V>[] oldTab = table;
      	// oldCap表示当前哈希桶的容量 length
          int oldCap = (oldTab == null) ? 0 : oldTab.length;
      	// 当前哈希桶的阀值
          int oldThr = threshold; 
      	// 初始化新的容量和阀值
          int newCap, newThr = 0;
      	// 1. 如果当前的容量不为空
          if (oldCap > 0) {
              // 1.1如果当前的容量已经达到了上限
              if (oldCap >= MAXIMUM_CAPACITY) {
                  // 阀值为2^32-1
                  threshold = Integer.MAX_VALUE;
                  // 返回当前的哈希桶不再扩容
                  return oldTab;
              }
              // 1.2否者新的容量为原先容量的两倍
              else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                       oldCap >= DEFAULT_INITIAL_CAPACITY)// 如果旧的容量大于等于默认的初始容量						16
                  // 新的阀值也为原先的两倍
                  newThr = oldThr << 1; // double threshold
          }
      	// 2. 如果当前表是空的，但是有阈值。代表是初始化时指定了容量、阈值的情况(上述的第二、三种构造方法)
          else if (oldThr > 0) // initial capacity was placed in threshold
              newCap = oldThr; // 新的容量为旧的阀值
          // 3. 此处的判断用于初始化 
          else {               // zero initial threshold signifies using defaults
              newCap = DEFAULT_INITIAL_CAPACITY;
              newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
          }
      	//如果新的阈值是0，对应的是  当前表是空的，但是有阈值的情况
          if (newThr == 0) {
              //根据新表容量 和 加载因子 求出新的阈值
              float ft = (float)newCap * loadFactor;
              // 进行越界修复
              newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                        (int)ft : Integer.MAX_VALUE);
          }
      	// 更新阀值
          threshold = newThr;
          @SuppressWarnings({"rawtypes","unchecked"})
              Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
          // 更新哈希桶的引用
          table = newTab;
      	// 如果以前的哈希桶有元素
      	// 一下执行将原哈希桶中的元素转入新的哈希桶中
          if (oldTab != null) {
              // 遍历老的哈希桶
              for (int j = 0; j < oldCap; ++j) {
                  // 取出当前的节点e
                  Node<K,V> e;
                  // 如果当前桶中有元素, 则将链表复制给e
                  if ((e = oldTab[j]) != null) {
                      // 将原哈希桶置空以便GC
                      oldTab[j] = null;
                      // 当前链表中只有一个元素, 没有发生哈希碰撞
                      if (e.next == null)
                          //直接将这个元素放置在新的哈希桶里。
                          //注意这里取下标 是用 哈希值 与 桶的长度-1 。 由于桶的长度是2的n次方，这么做其实是等于 一个模运算。但是效率更高
                          newTab[e.hash & (newCap - 1)] = e;
                      // 发生碰撞, 而且节点的个数超过8, 则转化为红黑树
                      else if (e instanceof TreeNode)
                          ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                      // 发生碰撞, 节点个数＜8 则进行处理, 依次放入新的哈希桶对应的下标位置
                      //因为扩容是容量翻倍，所以原链表上的每个节点，现在可能存放在原来的下								//标，即low位， 或者扩容后的下标，即high位。 high位=low位+原哈希桶							    //容量
                      else { // preserve order
                           //低位链表的头结点、尾节点
                          Node<K,V> loHead = null, loTail = null;
                           //高位链表的头结点、尾节点
                          Node<K,V> hiHead = null, hiTail = null;
                          Node<K,V> next;
                          do {
                              next = e.next;
                                //这里又是一个利用位运算 代替常规运算的高效点： 利用哈希值 与 旧的容量，可以得到哈希值去模后，是大于等于oldCap还是小于oldCap，等于0代表小于oldCap，应该存放在低位，否则存放在高位
                              if ((e.hash & oldCap) == 0) {
                                 
                                  if (loTail == null)
                                      loHead = e;
                                  else
                                      loTail.next = e;
                                  loTail = e;
                              }
                              else {
                                  if (hiTail == null)
                                      hiHead = e;
                                  else
                                      hiTail.next = e;
                                  hiTail = e;
                              }
                          } while ((e = next) != null);
                          if (loTail != null) {
                              loTail.next = null;
                              newTab[j] = loHead;
                          }
                          if (hiTail != null) {
                              hiTail.next = null;
                              newTab[j + oldCap] = hiHead;
                          }
                      }
                  }
              }
          }
          return newTab;
      }
  ```

过程分析：

表是否为空

1. 不为空时

   1. 容量已达到上限
      1. 阀值：2^32-1
      2. 容量：不再扩容
   2. 没有达到上限
      1. 阀值： 原来的两倍
      2. 容量： 原来的两倍

2. 为空时

   1. 阀值：12
   2. 容量：16

   

3. 表为空，但阀值不为空

   1. 阀值：容量*0.75
   2. 容量：阀值 



###### ③

添加元素

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```



#### 总结

![hashmap构造函数.png](https://i.loli.net/2020/03/13/5poAV8jQqEJs1Hw.png)

- 初始化 `loadFactor` 为 `DEFAULT_LOAD_FACTOR = 0.75` 。

- 在这些构造方法上，我们并没有看到 `table` 数组的初始化。它是**延迟**初始化，在我们开始往 HashMap 中添加 key-value 键值对时，在 `resize()` 方法中才真正初始化。

- 为什么`threshold` 要返回大于等于 `initialCapacity` 的最小 2 的 N 次方？

  - 在put方法中，计算table数组对应的容量，因为性能问题，都是做的位运算，计算 `table` 数组对应的位置，逻辑是 `(n - 1) & hash` ，正常情况下预想的是 `hash % (n - 1)` ，而这两者在2的N的情况下是等同的， 所以`threshold`返回的是`initialCapacity`的最下2的N次方

  

### 增  put(K key, V value)

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}
```

#### **putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict)**

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```







### Note

#### 负数的二进制表示:

1. 取绝对值求二进制 获得原码
2. 之后各个位取反获得反码
3. 反码在最后一位＋1获得补码
4. 补码极为负数的表示

\>>>和\>>的区别

\>>>适合高位的运算低位运算两者无区别

#### hashCode与equals的深入理解

>
>
>hashCode 的常规协定是： 
>在 Java 应用程序执行期间，在同一对象上多次调用 hashCode 方法时，必须一致地返回相同的整数，==前提是对象上 equals 比较中所用的信息没有被修改。==从某一应用程序的一次执行到同一应用程序的另一次执行，该整数无需保持一致。 
>==如果根据 equals(Object) 方法，两个对象是相等的，那么在两个对象中的每个对象上调用 hashCode 方法都必须生成相同的整数结果。==
>以下情况不是必需的：如果根据 equals(java.lang.Object) 方法，两个对象不相等，那么在两个对象中的任一对象上调用 hashCode 方法必定会生成不同的整数结果。但是，程序员应该知道，为不相等的对象生成不同整数结果可以提高哈希表的性能。 
>实际上，由 Object 类定义的 hashCode 方法确实会针对不同的对象返回不同的整数。（这一般是通过将该对象的内部地址转换成一个整数来实现的，但是 JavaTM 编程语言不需要这种实现技巧。） 
>
>==当equals方法被重写时，通常有必要重写 hashCode 方法，以维护 hashCode 方法的常规协定，该协定声明相等对象必须具有相等的哈希码。==

*  hashCode的存在主要是用于查找的快捷性,如HashTable, HashMap等. 用来在散列存储数据结构中确定对象的存储地址的.
* 若是两个对象相同,那么equals(java.lang.Object)方法为true, 那么两个对象的hashCode必须相同.

* 如果对象的equals方法被重写，那么对象的hashCode也尽量重写，并且产生hashCode使用的对象，一定要和equals方法中使用的一致，否则就会违反上一条.

* 两个对象的hashCode相同，并不一定表示两个对象就相同，也就是不一定适用于equals(java.lang.Object) 方法，只能够说明这两个对象在散列存储结构中，如Hashtable，他们“存放在同一个篮子里”。
  

==hashCode是用于查找使用的，而equals是用于比较两个对象的是否相等的==



#### 底层数据结构分析

##### 哈希法(JDK1.8)

```java
 static final int hash(Object key) {
        int h;
        // ^ (h >>> 16) 高 16 位与自身进行异或计算，保证计算出来的 hash 更加离散
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
 }
```

**一些操作符号解析**

* ^ 异或
* \>>> 向右移动xxx位, 无视符号, 高位填0
* \>\> 



## reference

* 数据结构与算法之美
* 芋道源码
## CopyOnWriteArrayList源码解读

## 依赖

![CopyOnWriteArrayList依赖](https://i.loli.net/2020/06/19/TXhnMmIkzA2Horu.png)

## 字段&属性

```java
// 用于修改数组的时候加锁
final transient ReentrantLock lock = new ReentrantLock();
// 真正存储元素的地方  只能通过get set访问 
// 使用volatile修饰 保证可见性(一个线程的修改 对其他线程可见)
private transient volatile Object[] array;


final Object[] getArray() {
    return array;
}

final void setArray(Object[] a) {
    array = a;
}
```



## 构造方法

```java
// 创建空数组
public CopyOnWriteArrayList() {
    setArray(new Object[0]);
}
// 
public CopyOnWriteArrayList(Collection<? extends E> c) {
    Object[] elements;
    // 如果c也是CopyOnWriteArrayList类型
    // 则直接将其的array拿来用, 属于浅拷贝
    // c和新的CopyOnWriteArrayList共享同一个数组
    if (c.getClass() == CopyOnWriteArrayList.class)
        elements = ((CopyOnWriteArrayList<?>)c).getArray();
    else {
        elements = c.toArray();
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elements.getClass() != Object[].class)
            elements = Arrays.copyOf(elements, elements.length, Object[].class);
    }
    setArray(elements);
}
// 把toCopyIn的元素拷贝给当前list的数组。
public CopyOnWriteArrayList(E[] toCopyIn) {
    setArray(Arrays.copyOf(toCopyIn, toCopyIn.length, Object[].class));
}
```



## 增

### add(E e)

```java
// 添加一个元素在末尾
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    // 修改数组之前加锁
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        // 将数组拷贝到新数组中, 新数组长度比旧数组长度多1
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        // array指向新的数组
        setArray(newElements);
        return true;
    } finally {
        // 释放锁
        lock.unlock();
    }
}
```

#### 总结

在尾部添加一个元素步骤:

1. 先加锁
2. 获取元素数组
3. 创建一个新的数组, 长度比原数组长度多1, 并把原数组的内容拷贝到新数组
4. 在新数组末尾添加元素
5. 把新数组赋值给当前对象的array属性，覆盖原数组
6. 解锁

### add(int index, E element)

```java
// 指定位置添加元素
public void add(int index, E element) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        // 判断添加位置是否合法
        if (index > len || index < 0)
            throw new IndexOutOfBoundsException("Index: "+index+
                                                ", Size: "+len);
        Object[] newElements;
        // 需要移动元素的个数
        int numMoved = len - index;
        if (numMoved == 0)
            newElements = Arrays.copyOf(elements, len + 1);
        else {
            // 新数组的长度为元素组的长度+1
            newElements = new Object[len + 1];
            // 先将原数组index之前的元素复制到新数组
            System.arraycopy(elements, 0, newElements, 0, index);
            // 再将原数组index之后的元素复制到新数组
            System.arraycopy(elements, index, newElements, index + 1,
                             numMoved);
        }
        // 新数组中index的位置赋值element
        newElements[index] = element;
        // array指向新的数组
        setArray(newElements);
    } finally {
        // 解锁
        lock.unlock();
    }
}
```

#### 总结

1. 执行步骤:

   1. 先加锁
   2. 判断位置是否合法
   3. 计算移动的位置,
      1. 若为0, 等同于在末尾添加元素
      2. 若是不为0, 则分步骤添加原数组元素到新数组, 先是index之前, 再是index之后, 最后是index
   4. 把新数组赋值给当前对象的array属性，覆盖原数组
   5. 解锁

   

2. 为什么这里使用`System.arraycopy()`, 而不是`copyOf()`

   1. 因为新数组已经被初始化了, 直接将原数组中的元素复制过来即可, 若是使用`copyOf()`实现起来麻烦,而且需要创建两次新的数组, 还有局部temp数组,  总之, 怎么实现起来方便怎么来, 如前一个`add(E e)`方法, 直接使用`copyOf()`方法就能实现, 使用`System.arraycopy()`需要先初始化, 然后再复制, 反而麻烦了

### addAll(Collection<? extends E> c)

```java
public boolean addAll(Collection<? extends E> c) {
    Object[] cs = (c.getClass() == CopyOnWriteArrayList.class) ?
        ((CopyOnWriteArrayList<?>)c).getArray() : c.toArray();
    if (cs.length == 0)
        return false;
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        // 原数组长度为0
        if (len == 0 && cs.getClass() == Object[].class)
            setArray(cs);
        else {
            // 创建新数组, 长度为原数组长度 + c的元素个数
            // 并将元素组的元素拷贝进去
            Object[] newElements = Arrays.copyOf(elements, len + cs.length);
            // 将c的元素拷贝到新数组中, 从len位置开始
            System.arraycopy(cs, 0, newElements, len, cs.length);
            setArray(newElements);
        }
        return true;
    } finally {
        lock.unlock();
    }
}
```

### addAll(int index, Collection<? extends E> c)

同理



## 查

### get()

就是简单的根据索引获取数组元素

```java
public E get(int index) {
    return get(getArray(), index);
}

private E get(Object[] a, int index) {
    return (E) a[index];
}
```





## 删

### remove(int index)

```java
public E remove(int index) {
    final ReentrantLock lock = this.lock;
    // 加锁
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        // 获取旧值
        E oldValue = get(elements, index);
        // 移动元素个数
        int numMoved = len - index - 1;
        // 如果删除的是最后一个元素, 直接拷贝出一个新的len-1的数组即可
        if (numMoved == 0)
            setArray(Arrays.copyOf(elements, len - 1));
        else {
            Object[] newElements = new Object[len - 1];
            System.arraycopy(elements, 0, newElements, 0, index);
            System.arraycopy(elements, index + 1, newElements, index,
                             numMoved);
            setArray(newElements);
        }
        return oldValue;
    } finally {
        lock.unlock();
    }
}
```

#### 总结

1. 步骤:
   1. 加锁
   2. 获取原数组及长度
   3. 获取旧值
   4. 计算需要移动元素的个数
      1. 若是移动次数=0, 即删除末尾元素, 直接复制一个长度为原数组长度-1的数组, 将原数组len-1的元素放入即可
      2. 若是移动次数>0, 则现将index前的元素复制到新数组(新数组确定长度为原长度-1), 然后复制index之后的元素
   5. 返回旧值
   6. 解锁
2. 为什么移动元素个数的计算和add`方法不一样
   1. 因为添加元素到指定位置时, 原数组的对应位置上的元素`a`不删除, 移动的时候`a`也需要移动
   2. 删除的时候, 直接原数组对应位置的上的元素直接删除, 所以不需要移动 



## 数组长度

在之前的`ArrayList`, `LinkedList` 中 size都是成员变量, 在对数组做修改的时候, size执行++ 或--即可, 但是这里却不是

```java
public int size() {
    return getArray().length;
}
```

我们发现, 每次修改都是拷贝一份正好可以**存储目标个数元素的数组**，所以不需要size属性了，数组的长度就是集合的大小，而不像ArrayList数组的长度实际是要大于集合的大小的。



## 总结

1. 使用可重入锁保证线程安全
2. 写操作都需要加锁, 然后在此期间创建新的数组, 并在新数组中做修改, 之后再用新数组替原数组, 空间复杂度为$O(n)$
3. 读操作不需要加锁, 支持随机访问, 空间复杂度为$O(1)$
4. 采用读写分离思想(读写者问题), 但是写操作占用大量的内存空间, 适用于读多, 写少的情景
5. 只能保证最终一致性, 不能保证实时一致性

## 面试相关问题

1. CopyOnWriteArrayList是怎么保证并发安全的？
2. CopyOnWriteArrayList的实现采用了什么思想？
3. CopyOnWriteArrayList是不是强一致性的？
4. CopyOnWriteArrayList适用于什么样的场景？
5. CopyOnWriteArrayList插入、删除、查询元素的时间复杂度各是多少？
6. CopyOnWriteArrayList为什么没有size属性？
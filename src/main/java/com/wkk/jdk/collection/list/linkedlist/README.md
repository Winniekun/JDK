## LinkedList源码解读

### 概述

LinkedList底层数据结构是链表，是实现了List接口和Deque接口的双端链表，其能够高效的实现插入删除操作, 而且也拥有了队列所拥有的特性。和ArrayList相比，因为其没有实现RandomAccess，是以下标进行访问元素， 所以对于元素访问不及ArrayList，随机访问元素慢。同时需要注意的是, **LinkedList不是线程安全的**，需要使用的别的方式实现线程安全。

### 依赖

![image](https://raw.githubusercontent.com/KongWiki/cloudImg/master/LInkedList%E7%BB%93%E6%9E%84.png)

### 阅读套路

按照正常的逻辑，首先了解其构造方法， 之后了解常用的API(CRUD)操作即可。

* [x] addAll();
* [x] add();
* [x] remove();
* [x] get();

### 从节点数据结构开始

![image](https://raw.githubusercontent.com/KongWiki/cloudImg/master/linkedlistNode.jpeg)

按照正常的逻辑， 首先基础的数据构造, 然后了解其构造方法， 之后了解常用的API(CRUD)操作即可。

其实一个很明显的双向链表的数据结构。

### 构造函数

1. 空的构造方法

   ```java
    public LinkedList() {
       }
   ```

   用于构造一个空的链表。然后自行处理

2. 含有参数的构造方法(用已有的集合创建链表)

   ```java
    public LinkedList(Collection<? extends E> c) {
           this();
           addAll(c);
       }
   
   ```

   将已有的linkedlist添加到一个新的linkedlist中(尾插法，下文有提及)。其类似于copy()

### 增：add相关的方法

可以看到， 初始化之后，使用无参的构造方法，返回的为一个空的链表，若是为含有参数的构造方法，会调用addAll()方法将一个链表的所有元素都拷贝到一个新的元素。

这个部分涉及了大量的双向链表的插入删除，可先理解了双向链表的操作（3、 4步骤的操作顺序在于采用的是哪个节点作为基础节点：也就是选择待插入位置的节点还是该节点的前驱节点，或者是两个都使用，若是两个都是用则3、 4无顺序先后）。

![](https://raw.githubusercontent.com/KongWiki/cloudImg/master/%E5%8F%8C%E5%90%91%E9%93%BE%E8%A1%A8-%E6%8F%92%E5%85%A5%E6%93%8D%E4%BD%9C.png)

####  addAll()

```java
 public boolean addAll(Collection<? extends E> c) {
     	// 以size为插入下标，插入集合c中所有元素
        return addAll(size, c);
    }
 public boolean addAll(int index, Collection<? extends E> c) {
 		// 判断当前的索引是否越界， 若是越界，
        checkPositionIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0)
            return false;
		// pred: 插入位置对应的节点的前驱节点
     	// succ: 插入位置对应的节点
     	// 以下为双向链表的插入操作需要的预备工作
        Node<E> pred, succ;
        if (index == size) {
            // 在链表的尾部插入节点需要的预备工作
            succ = null;
            pred = last;
        } else {
            // 在链表中间插入节点需要的预备工作
            // 1. 确定插入位置的节点
            // 2. 确定插入位置的节点的前驱节点
            succ = node(index);
            pred = succ.prev;
        }
		// 插入节点 可画图理解
        for (Object o : a) {
            @SuppressWarnings("unchecked") E e = (E) o;
            Node<E> newNode = new Node<>(pred, e, null);
            if (pred == null)
                first = newNode;
            else
                pred.next = newNode;
            pred = newNode;
        }
		
        if (succ == null) {
            last = pred;
        } else {
            pred.next = succ;
            succ.prev = pred;
        }

        size += numNew;
        modCount++;
        return true;
    }
```

`addAll()`主要为就是依次的遍历已有集合的所有元素，然后依次进行插入(尾插法，保证插入的顺序不会和最后的顺序不变)

#### 判断越界方法

```java
private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

 private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
```

直接在`if`后跑出异常，可直接跑出异常信息，并且可以根据自己定义信息跑出异常。

#### add(E e)

```java
public boolean add(E e) {
        linkLast(e);
        return true;
}
void linkLast(E e) {
    // 在链表尾部插入元素
        final Node<E> l = last; // 定义一个新的节点l, 指向尾节点，方便后续插入
        final Node<E> newNode = new Node<>(l, e, null);// 创建插入元素的节点
    	// 以下为插入操作，可画图理解
        last = newNode;
        if (l == null) // 说明原链表为空，更新头节点
            first = newNode;
        else // 原链表不为空， 在尾部进行插入操作
            l.next = newNode;
        size++;
        modCount++;
}

```

#### add(int index, E element)

```java
public void add(int index, E element) {
        checkPositionIndex(index);
    	// 链表尾部插入元素
        if (index == size)
            linkLast(element);
   		// 在链表的中间部分插入元素
        else
            linkBefore(element, node(index));
}
void linkLast(E e) {
    // 在链表尾部插入元素
        final Node<E> l = last; // 定义一个新的节点l, 指向尾节点，方便后续插入
        final Node<E> newNode = new Node<>(l, e, null);// 创建插入元素的节点
    	// 以下为插入操作，可画图理解
        last = newNode;
        if (l == null) // 说明原链表为空，更新头节点
            first = newNode;
        else // 原链表不为空， 在尾部进行插入操作
            l.next = newNode;
        size++;
        modCount++;
}
void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
    	// 一下为双端链表的插入操作
        succ.prev = newNode;
    	// 若是在第一个节点前插入， 则插入节点为第一个节点
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        modCount++;
}
Node<E> node(int index) {
        // assert isElementIndex(index);
		// 根据索引创建节点
    	// 此处会有判断，判断是在链表的前半段还是后半段
    	// 加快查找效率
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
}
```

#### 小结

- 链表批量增加，是**靠for循环遍历原数组，依次执行插入节点操作**。对比ArrayList是通过System.arraycopy完成批量增加的
- 通过下标获取某个node 的时候，（add select），**会根据index处于前半段还是后半段 进行一个折半**，以**提升查询效率**

### 删： remove相关的方法

#### remove()

```java
 public E remove() {
     // 默认是从链表的头部开始删除元素	
        return removeFirst();
 }
 public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
 }

private E unlinkFirst(Node<E> f) {
        // assert f == first && f != null;
    	// 删除第一个节点
        final E element = f.item;
        final Node<E> next = f.next; // 防止删除时导致的断链
        f.item = null;
        f.next = null; // help GC
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        size--;
        modCount++;
        return element;
    }
```

#### remove(int index)

下图为双向链表的删除操作，仅做参考，具体的写法依据个人采用基础节点。

![mage](https://raw.githubusercontent.com/KongWiki/cloudImg/master/%E5%8F%8C%E5%90%91%E9%93%BE%E8%A1%A8%E7%9A%84%E5%88%A0%E9%99%A4.png)

```java
 public E remove(int index) {
     	// 判断是否越界
        checkElementIndex(index);
        return unlink(node(index));
 }
 private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
 }
 private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
 }
 E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next; // 后继节点
        final Node<E> prev = x.prev; // 前驱节点
		// 删除的为第一个节点
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
		// 删除的为最后一个节点
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }
```

#### 判断越界方法

其实是和增里面的判断是一样的

```java
 private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
 }
 private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
 }
```

#### remove(Object o)

```java
public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }
E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next; // 后继节点
        final Node<E> prev = x.prev; // 前驱节点
		// 删除的为第一个节点
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }
		// 删除的为最后一个节点
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        modCount++;
        return element;
    }

```

#### 小结

**删也一定会修改modCount。 按下标删，也是先根据index找到Node，然后去链表上unlink掉这个Node。 按元素删，会先去遍历链表寻找是否有该Node，考虑到允许null值，所以会遍历两遍，然后再去unlink它。**

### 改：set()相关的方法

#### set(int index, E element)

```java
 public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldVal = x.item;
        x.item = element;
        return oldVal; //返回原先的值
    }
 private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
 }
 private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
 }
Node<E> node(int index) {
        // assert isElementIndex(index);
		// 根据索引创建节点
    	// 此处会有判断，判断是在链表的前半段还是后半段
    	// 加快查找效率
        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
}
```

#### 小结

set()也是根据索引去寻找对应Node()，之后替换，但是不会修改modCount

### 查： get相关方法

#### get(int index)

```java
public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }
Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

```

#### 判断越界方法

其实是和增里面的判断是一样的

```java
 private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
 }
 private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
 }
```

#### 小结

按照索引查找对应的节点，之前的增、删、改都用到了

## 总结

LinkedList是双向链表

* 链表的批量添加(addAll())是转化为数组之后使用for-each进行添加，默认在链表尾部添加， 会修改modCount
* 根据index获取节点时，会根据index处于后半段还是前半段进行查找，提升查找速率
* 所有的CRUD中都有涉及根据index去寻找节点

**LinkedList**中还有很多的API没有写，只是简单的写了基础的CRUD

## reference

* [JavaGuid---LinkedList](https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/collection/LinkedList.md)
* [面试必备：LinkedList源码解读(JDK8)](https://blog.csdn.net/zxt0601/article/details/77341098)
* 王道考研数据结构---链表一章
  * 考研的时候看的，各种花式插入删除整合骚操作。
* 大话数据结构
## ArrayList 源码解读

### 从构造函数开始

```java
    /**
     * Default initial capacity.
     * 默认初始化容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for empty instances
     * 空实例的共享数组实例
     * 若是无参构造方法 使用 DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
     * 若是含参构造方法 使用 EMPTY_ELEMENTDATA 
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     * 
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    /**
     * 构造函数1 带初始容量参数的构造函数, (用户自己定义容量)
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {// >0
            // 创建initialCapacity容量
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {// =0
            // 创建空数组
            this.elementData = EMPTY_ELEMENTDATA;
        } else { // ＜0 抛出异常
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

    /**
     * 构造函数2: 默认构造函数, 
     * DEFAULTCAPACITY_EMPTY_ELEMENTDATA = 0, 初始化为10
     * 初始是一个空数组, 当添加一个元素的时候, 扩充为10
     * 原因详见 transient Object[] elementData;注释
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     * 构造包含指定collection元素的列表 这些元素利用该集合的迭代按顺序返回
     * 如果指定的集合为null, 抛出 NullPointerException
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
```

**总结**

1. **常量EMPTY_ELEMENTDATA和DEFAULTCAPACITY_EMPTY_ELEMENTDATA是为了初始化elementData的。如果为无参构造函数，使用DEFAULTCAPACITY_EMPTY_ELEMENTDATA；如果为含参构造函数，使用EMPTY_ELEMENTDATA**
2. 以无参构造方法创建arraylist时, 实际上初始化的值是一个空数组. 添加第一个元素时 扩容为10

### ArrayList扩容机制

####  add 方法

```java
/**
* 将指定的元素添加到列表末尾
*/
public boolean add(E e) {
    //在添加元素之前 预先调用该方法
        ensureCapacityInternal(size + 1);  // Increments modCount!!
    // 最简单的给数组进行赋值
    // size++ 先赋值 后自增
        elementData[size++] = e;
        return true;
    }

```

#### ensureCapacityInternal 方法

在查看该方法时, 发现其调用了其他的方法, 然后其他的方法也调用了另外的方法, 所有全部粘上

```java
    
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 获取默认的容量和传入参数较大的值
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }
    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```

**逻辑分析**

* 当使用`add`方法 添加了第一个元素, `elementData.length() == 0`;  (当前还是一个空的list) , 因为执行了`ensureCapacityInternal(size + 1)`方法,  通过`calculateCapacity()`方法, 使得`minCapacity`为10,

  同时`ensureCapacityInternal()`方法调用了` ensureExplicitCapacity()`方法, 因为`minCapacity - elementData.length > 0` 所以调用了`grow()`方法. 

* 当添加第二个元素的时候, `minCapacity=2`, 之后的步骤和添加第一个元素一样, 但是因为不满足`minCapacity - elementData.length > 0`所以不会执行`grow`方法.

* 当添加第三个元素的时候, 同理

* 最后添加第11个元素的时候, 才重新开始执行`grow`方法.

#### grow 方法

```java
 private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     * 扩容核心代码
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        // 旧容量(并不是当前的该列表包含了多少的元素, 而是内部给正在使用的列表的容量大小)
        int oldCapacity = elementData.length;
		// 新容量(1 + 0.5)* 旧容量
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```

**核心**

当当前的容量满了之后, 每次的扩容都会变为原来的1.5倍.`int newCapacity = oldCapacity + (oldCapacity >> 1);`

> ">>"（移位运算符）：>>1 右移一位相当于除2，右移n位相当于除以 2 的 n 次方。这里 oldCapacity 明显右移了1位所以相当于oldCapacity /2。对于大数据的2进制运算,位移运算符比那些普通运算符的运算要快很多,因为程序仅仅移动一下而已,不去计算,这样提高了效率,节省了资源 　

- 当add第1个元素时，oldCapacity 为0，经比较后第一个if判断成立，newCapacity = minCapacity(为10)。但是第二个if判断不会成立，即newCapacity 不比 MAX_ARRAY_SIZE大，则不会进入 `hugeCapacity` 方法。数组容量为10，add方法中 return true,size增为1。
- 当add第11个元素进入grow方法时，newCapacity为15，比minCapacity（为11）大，第一个if判断不成立。新容量没有大于数组最大size，不会进入hugeCapacity方法。数组容量扩为15，add方法中return true,size增为11。
- 以此类推··

#### hugeCapacity 方法

```java
   private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
```

只有`newCapacity` >`MAX_ARRAY_SIZE` 才回调用该方法, 方法中使用三元运算, 意思是若果`minCapacity`> `MAX_ARRAY_SIZE`   则`newCapacity`的容量大小为`Integer.MAX_VALUE`也就是2**31次方, 若是`minCapacity`< `MAX_ARRAY_SIZE`   则`newCapacity`的容量大小为`Integer.MAX_VALUE-8`



#### ensureCapacity用法
Arraylist源码中有一个ensureCapacity方法
```java
/***
* 如有必要，增加此 ArrayList 实例的容量，
* 以确保它至少可以容纳由minimum capacity参数指定的元素数。
*/
 public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            // any size if not default element table
            ? 0
            // larger than default for default empty table. It's already
            // supposed to be at default size.
            : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }
```
**该方法适用于添加大量的元素, 以减少上文的增量重新分配的次数**
```java
 ArrayList<Object> list = new ArrayList<>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapaticy方法之前: "+ (endTime - startTime));

        list = new ArrayList<>();
        list.ensureCapacity(N);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);

        }

        endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapaticy方法之后: "+ (endTime -startTime));


```

```
使用ensureCapaticy方法之前: 3345
使用ensureCapaticy方法之后: 257
```
## JDK源码解读

刷LeetCode的时候，突然发现JDK里面本身就包含了很数据结构的实现和花式操作，还有一些算法的实现，能从很多地方发现leetcode中一些题目实现。譬如TreeMap中就有5道我遇到的关于二叉树的题目。。。。

### 集合

![image](https://i.loli.net/2020/03/13/OYNcWr78AxQfqau.png)



#### collection

1. list
   1. * [x] [ArrayList](https://github.com/KongWiki/JDK/tree/master/src/com/wkk/jdk/collection/arraylist)
   2. * [x] [LinkedList](https://github.com/KongWiki/JDK/tree/master/src/com/wkk/jdk/collection/linkedlist)
   3. * [ ] Vector
2. set
   1. HashSet
   2. TreeSet
   3. LinkedHashSet

#### map

1. HashMap
2. HashTable
3. * [x] [TreeMap](https://github.com/KongWiki/JDK/tree/master/src/com/wkk/jdk/map/treemap)


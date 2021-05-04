package com.wkk.jdk.map.treemap.bstAVL;


import org.junit.Assert;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Time: 20-2-25上午9:11
 * @Author: kongwiki
 * @Email: kongwiki@163.com
 */
public class AVLMap<K, V> implements Iterable<AVLEntry<K, V>> {
    private int size;
    private AVLEntry<K, V> root;
    private Comparator<K> comp;
    private LinkedList<AVLEntry<K, V>> stack = new LinkedList<AVLEntry<K, V>>();

    @SuppressWarnings("unchecked")
    private int compare(K a, K b) {
        if (comp != null) {
            return comp.compare(a, b);
        } else {
            Comparable<K> c = (Comparable<K>) a;
            return c.compareTo(b);
        }
    }

    public AVLMap(Comparator<K> comp) {
        super();
        this.comp = comp;
    }

    public AVLMap() {
        super();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }
    /*
    ===================================================================
    * 增
    * */

    public V put(K key, V value) {
        if (root == null) {
            root = new AVLEntry<K, V>(key, value);
            stack.push(root);
            size++;
        } else {
            AVLEntry<K, V> p = root;
            while (p != null) {
                stack.push(p);
                int compareResult = compare(key, p.key);
                if (compareResult == 0) {
                    p.setValue(value);
                    break;
                } else if (compareResult < 0) {
                    if (p.left == null) {
                        p.left = new AVLEntry<K, V>(key, value);
                        size++;
                        stack.push(p.left);
                        break;
                    } else {
                        p = p.left;
                    }
                } else {
                    if (p.right == null) {
                        p.right = new AVLEntry<K, V>(key, value);
                        size++;
                        stack.push(p.right);
                        break;
                    } else {
                        p = p.right;
                    }
                }
            }
        }
        // 调整 将二叉搜索树调整为平衡二叉搜索树
        fixAfterInsertion(key);
        return value;
    }

    @Override
    public Iterator<AVLEntry<K, V>> iterator() {
        return new AVLIterator<K, V>(root);
    }

    /*
    ===================================================================
    * 查
    * */
    // 主要查找逻辑

    private AVLEntry<K, V> getEntry(K key) {
        AVLEntry<K, V> p = root;
        while (p != null) {
            int compareResult = compare(key, p.key);
            if (compareResult == 0) {
                return p;
            } else if (compareResult < 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        return null;
    }

    // 是否含有key
    public boolean containsKey(K key) {
        AVLEntry<K, V> p = getEntry(key);
        return p != null;
    }

    public V get(K key) {
        AVLEntry<K, V> p = getEntry(key);
        return p != null ? p.getValue() : null;
    }
    // 是否含有value
    public boolean containsValue(V value) {
        Iterator<AVLEntry<K, V>> itr = this.iterator();
        while (itr.hasNext()) {
            if (itr.next().getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
    /*
    ===================================================================
    * 删
    * */
    // 获取最小节点 也就是中序遍历的第一个节点
    public AVLEntry<K, V> getFirstEntry(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }
    // 获取最大节点 也就是中序遍历的最后的节点
    public AVLEntry<K, V> getLastEntry(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        }
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }
    // 主逻辑
    private AVLEntry<K, V> deleteEntry(AVLEntry<K, V> p, K key) {
        if (p == null) {
            return null;
        } else {
            int compareResult = compare(key, p.key);
            // 查找成功
            if (compareResult == 0) {
                // 三种情况讨论
                //1 p为叶子节点
                if (p.left == null && p.right == null) {
                    p = null;
                }
                //2 p仅有左子树（右子树）
                else if (p.left != null && p.right == null) {
                    p = p.left;
                } else if (p.left == null && p.right != null) {
                    p = p.right;
                }
                //3 p既有左子树又有右子树
                else {
                    // 只要为用于随机使用rightMax || leftMin
                    if ((size & 1) == 0) {
                        // 1. 先寻找右子树的firstEntry
                        AVLEntry<K, V> rightMin = getFirstEntry(p.right);
                        // 2. 修改p的值
                        p.key = rightMin.key;
                        p.value = rightMin.value;
                        // 3. 递归删除rightMin(先找节点再删除)
                        AVLEntry<K, V> newRight = deleteEntry(p.right, p.key);
                        p.right = newRight;
                    } else {
                        // 1. 先寻找右子树的firstEntry
                        AVLEntry<K, V> leftMax = getLastEntry(p.left);
                        // 2. 修改p的值
                        p.key = leftMax.key;
                        p.value = leftMax.value;
                        // 3. 递归删除rightMin(先找节点再删除)
                        AVLEntry<K, V> newLeft = deleteEntry(p.left, p.key);
                        p.left = newLeft;
                    }
                }
            }
            // 不相等 和查找算法相似
            else if (compareResult < 0) {
                AVLEntry<K, V> newLeft = deleteEntry(p.left, key);
                p.left = newLeft;
            } else {
                AVLEntry<K, V> newRight = deleteEntry(p.right, key);
                p.right = newRight;
            }
            p = fixAfterDeletion(p);
            return p;
        }
    }

    public int getHeight(AVLEntry<K, V> p) {
        return p == null ? 0 : p.height;
    }
    /*
      ===================================================================
      * 右旋
      * */
    private AVLEntry<K, V> rotateRight(AVLEntry<K, V> p) {
        AVLEntry<K, V> left = p.left;
        p.left = left.right;
        left.right = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        left.height = Math.max(getHeight(left.left), p.height) + 1;
        return left;
    }
    /*
      ===================================================================
      * 左旋
      * */
    private AVLEntry<K, V> rotateLeft(AVLEntry<K, V> p) {
        AVLEntry<K, V> right = p.right;
        p.right = right.left;
        right.left = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        right.height = Math.max(p.height, getHeight(right.right)) + 1;
        return right;
    }

    private AVLEntry<K, V> firstLeftThenRight(AVLEntry<K, V> p) {
        p.left = rotateLeft(p.left);
        p = rotateRight(p);
        return p;
    }

    private AVLEntry<K, V> firstRightThenLeft(AVLEntry<K, V> p) {
        p.right = rotateRight(p.right);
        p = rotateLeft(p);
        return p;
    }
    // 插入后的调整 改为AVL
    private void fixAfterInsertion(K key) {
        AVLEntry<K, V> p = root;
        while (!stack.isEmpty()) {
            p = stack.pop();
            int newHeight = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
            if (p.height > 1 && newHeight == p.height) {
                stack.clear();
                return;
            }
            p.height = newHeight;
            int d = getHeight(p.left) - getHeight(p.right);
            if (Math.abs(d) <= 1) {
                continue;
            } else {
                if (d == 2) {
                    if (compare(key, p.left.key) < 0) {
                        p = rotateRight(p);
                    } else {
                        p = firstLeftThenRight(p);
                    }
                } else {
                    if (compare(key, p.right.key) > 0) {
                        p = rotateLeft(p);
                    } else {
                        p = firstRightThenLeft(p);
                    }
                }
                if (!stack.isEmpty()) {
                    if (compare(key, stack.peek().key) < 0) {
                        stack.peek().left = p;
                    } else {
                        stack.peek().right = p;
                    }
                }
            }
        }
        root = p;
    }

    public void checkBalance() {
        postOrderCheckBalance(root);
    }

    private void postOrderCheckBalance(AVLEntry<K, V> p) {
        if (p != null) {
            postOrderCheckBalance(p.left);
            postOrderCheckBalance(p.right);
            Assert.assertTrue(Math.abs(getHeight(p.left) - getHeight(p.right)) <= 1);
        }
    }

    public V remove(K key) {
        // 先查找
        AVLEntry<K, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        // 再删除
        V oldValue = entry.getValue();
        root = deleteEntry(root, key);
        size--;
        return oldValue;
    }

    public void levelOrder() {
        Queue<AVLEntry<K, V>> queue = new LinkedList<AVLEntry<K, V>>();
        queue.offer(root);
        int preCount = 1;
        int pCount = 0;
        while (!queue.isEmpty()) {
            preCount--;
            AVLEntry<K, V> p = queue.poll();
            System.out.print(p + " ");
            if (p.left != null) {
                queue.offer(p.left);
                pCount++;
            }
            if (p.right != null) {
                queue.offer(p.right);
                pCount++;
            }
            if (preCount == 0) {
                preCount = pCount;
                pCount = 0;
                System.out.println();
            }
        }
    }

    // 删除后的调整 改为AVL
    public AVLEntry<K, V> fixAfterDeletion(AVLEntry<K, V> p) {
        if (p == null) {
            return null;
        } else {
            p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
            int d = getHeight(p.left) - getHeight(p.right);
            if (d == 2) {
                if (getHeight(p.left.left) - getHeight(p.left.right) >= 0) {
                    p = rotateRight(p);
                } else {
                    p = firstLeftThenRight(p);
                }
            } else if (d == -2) {
                if (getHeight(p.right.right) - getHeight(p.right.left) >= 0) {
                    p = rotateLeft(p);
                } else {
                    p = firstRightThenLeft(p);
                }
            }
            return p;
        }
    }
}

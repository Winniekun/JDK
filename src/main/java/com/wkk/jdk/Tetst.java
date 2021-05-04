package com.wkk.jdk;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author weikunkun
 * @since 2021/4/7
 */
public class Tetst {
    public boolean search(int[] nums, int target) {
        // 思路 二分查找
        int left = 0;
        int right = nums.length - 1;
        // 先查找到拐点
        // 然后在拐点左右寻找
        while (left < right && nums[0] == nums[right]) {
            right--;
        }
        int lower = fingSmallest(nums);
        if (nums[lower] > target) {
            return false;
        } else if (nums[lower] == target) {
            return true;
        }
        if (nums[nums.length - 1] >= target) {
            return findTarget(nums, lower + 1, right, target);
        } else {
            return findTarget(nums, 0, lower - 1, target);
        }
    }

    private boolean findTarget(int[] nums, int left, int right, int target) {
        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (nums[mid] < target) {
                right--;
            } else {
                left = mid;
            }
        }
        return nums[left] == target;
    }

    private int fingSmallest(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            int mid = (i + j) / 2;
            if (nums[mid] > nums[j]) {
                i = mid + 1;
            } else if (nums[mid] < nums[j]) {
                j = mid;
            } else {
                j -= 1;
            }
        }
        return i;

    }


    static final class Node {
        /**
         * Marker to indicate a node is waiting in shared mode
         */
        static final Node SHARED = new Node();
        /**
         * Marker to indicate a node is waiting in exclusive mode
         */
        static final Node EXCLUSIVE = null;
        /**
         * waitStatus value to indicate thread has cancelled
         */
        static final int CANCELLED = 1;
        /**
         * waitStatus value to indicate successor's thread needs unparking
         */
        static final int SIGNAL = -1;
        /**
         * waitStatus value to indicate thread is waiting on condition
         */
        static final int CONDITION = -2;
        /**
         * waitStatus value to indicate the next acquireShared should
         * unconditionally propagate
         */
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;

        public static void main(String[] args) {
            Tetst tetst = new Tetst();
            int[] arr = {1, 1, 1, 1, 1, 2, 1};
            tetst.search(arr, 2);
            Lock lock = new ReentrantLock();
            lock.lock();
            lock.tryLock();
        }
    }
}

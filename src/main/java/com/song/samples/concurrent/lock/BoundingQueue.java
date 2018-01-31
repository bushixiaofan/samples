package com.song.samples.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundingQueue<T> {
    // 存储队列中的元素
    private Object[] items;

    // 添加的下标，删除的下标，数组当前的数量
    private int addIndex, removeIndex, count;

    private Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundingQueue(int size) {
        this.items = new Object[size];
    }

    /**
     * 添加一个元素，如果队列已满则等待
     * @param t
     */
    public void add(T t) {
        lock.lock();
            try {
                while (count == items.length) {
                    notFull.await();
                }
                items[addIndex] = t;
                if (++addIndex == items.length) {
                    addIndex = 0;
                }
                ++count;
                notEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
    }

    /**
     * 删除一个元素并返回
     * @return
     */
    @SuppressWarnings("unchecked")
    public T remove() {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object o = items[removeIndex];
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            --count;
            notFull.signal();
            return (T) o;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }
}

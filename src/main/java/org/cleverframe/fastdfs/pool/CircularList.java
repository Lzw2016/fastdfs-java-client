package org.cleverframe.fastdfs.pool;

import java.util.ArrayList;

/**
 * 基于ArrayList的循环链表类<br/>
 * 第一次调用next()将返回第一个元素，调用previous()将返回最后一个元素<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 20:01 <br/>
 */
public class CircularList<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1L;
    private int index = -1;

    /**
     * 重置，之后第一次调用next()将返回第一个元素，调用previous()将返回最后一个元素
     */
    public void reset() {
        synchronized (this) {
            index = -1;
        }
    }

    /**
     * 检验集合状态
     */
    private void check() {
        if (this.size() == 0) {
            throw new IndexOutOfBoundsException("空的列表，无法获取元素");
        }
    }

    /**
     * 下一个对象
     */
    public E next() {
        check();
        synchronized (this) {
            index++;
            if (index >= this.size()) {
                index = 0;
            }
            return this.get(index);
        }

    }

    /**
     * 上一个元素
     */
    public E previous() {
        check();
        synchronized (this) {
            index--;
            if (index < 0) {
                index = this.size() - 1;
            }
            return this.get(index);
        }
    }

    /**
     * 获取当前对象
     */
    public E current() {
        check();
        synchronized (this) {
            if (index < 0) {
                index = 0;
            }
            return this.get(index);
        }
    }
}
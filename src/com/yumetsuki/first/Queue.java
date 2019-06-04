package com.yumetsuki.first;

public interface Queue<T> {

    /**
     * 向队列中添加一个数据
     * @param t 添加的数据项
     * @return 添加成功则返回true，否则返回false
     * */
    boolean add(T t);

    /**
     * 从队列中弹出一个数据，该方法会删除队列的第一个数据
     * @return 队列的第一个数据
     * */
    T poll();

    /**
     * 从队列中弹出一个数据，该方法不会将队列中的数据删除
     * @return 队列的第一个数据
     * */
    T peek();

    /**
     * 返回队列是否为空
     * @return 为空则返回true，否则返回false
     * */
    boolean isEmpty();

    /**
     * 返回当前队列的所包含数据的大小
     * @return 队列所包含数据的大小
     * */
    int size();

    /**
     * 返回当前队列是否已满
     * @return 队列已满则返回true，否则返回false
     * */
    boolean isFull();
}

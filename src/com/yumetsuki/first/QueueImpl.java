package com.yumetsuki.first;

/**
 * 队列实现类实现一个简单的循环队列
 * @author yumetsuki
 * @version 1.0
 * */
public class QueueImpl<T> implements Queue<T>{

    private T [] data;

    private static final int DEFAULT_SIZE = 10;

    private int first = 0;

    private int end = 0;

    /**
     * 构造方法，构造一个具有默认长度的循环队列
     * */
    public QueueImpl(){
        data = (T[]) new Object[DEFAULT_SIZE];
    }

    /**
     * 构造方法，构造一个具有指定长度的循环队列
     * */
    public QueueImpl(int size){
        data = (T[]) new Object[size];
    }

    /**
     * 构造方法，构造一个默认长度的循环队列并向其中添加一个数据
     * */
    public QueueImpl(T data){
        this();
        insert(data);
    }


    /**
     * 构造方法，构造一个具有默认长度的循环队列，并向其中添加一组数据
     * */
    public QueueImpl(T [] data){
        this();
        for (T t : data) {
            insert(t);
        }
    }

    /**
     * 构造方法，构造一个具有制定长度的循环队列，并向其中添加一个数据
     * */
    public QueueImpl(T data, int size){
        this(size);
        insert(data);
    }

    /**
     * 构造方法，构造一个具有制定长度的循环队列，并向其中添加一组数据
     * */
    public QueueImpl(T [] data, int size){
        this(size);
        for (T t : data) {
            insert(t);
        }
    }


    @Override
    public boolean add(T t) {
        return insert(t);
    }

    @Override
    public T poll() {
        T t = data[first];
        data[first++] = null;
        return t;
    }

    @Override
    public T peek() {
        return data[first];
    }

    @Override
    public boolean isEmpty() {
        return first == end;
    }

    @Override
    public int size() {
        return (end < first)? (end + data.length - first) : (end - first);
    }

    @Override
    public boolean isFull() {
        return (end + 1)%data.length == first;
    }

    private boolean insert(T t){
        if (isFull()) throw new IndexOutOfBoundsException();
        data[end] = t;
        end = (end + 1)%data.length;
        return true;
    }



}

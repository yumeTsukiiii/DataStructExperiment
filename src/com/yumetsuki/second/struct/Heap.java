package com.yumetsuki.second.struct;

import java.util.List;

public class Heap<T extends Comparable> {

    private static final int DEFAULT_SIZE = 10;

    private Object []array;

    private int size = 0;

    public Heap() {
        array = new Object[DEFAULT_SIZE];
    }

    public Heap(List<T> list) {
        array = new Object[list.size()*2 + 1];
        list.forEach(this::insert);
    }

    public Heap(T [] array){
        this.array = new Object[array.length*2 + 1];
        for (T t : array) {
            insert(t);
        }
        System.out.println(2333);
    }


    public void insert(T t){
        size++;
        upFloat(t);
    }

    @SuppressWarnings("unchecked")
    public T pop(){
        if (size == 0) return null;
        T t = (T) array[0];
        Object floatObj = array[size - 1];
        array[size - 1] = null;
        size--;
        if (size != 0)
            downFloat((T) floatObj);
        return t;
    }

    @SuppressWarnings("unchecked")
    private void upFloat(T t){
        for (int i = size - 1; i > 0; i/=2) {
            int parentIndex = (i%2) == 0 ? i/2 - 1 : i/2;
            T parent = (T) array[parentIndex];
            if (t.compareTo(parent) < 0){
                array[i] = array[parentIndex];
            } else {
                array[i] = t;
                return;
            }
        }
        array[0] = t;
    }

    @SuppressWarnings("unchecked")
    private void downFloat(T t){
        int i;
        for (i = 0; (i*2 + 2) < size;) {
            T firstChild = (T) array[i*2 + 1];
            T secondChild = (T) array[i*2 + 2];
            if (firstChild.compareTo(secondChild) <= 0){
                if (t.compareTo(firstChild) > 0){
                    array[i] = array[i*2 + 1];
                    i = i*2 + 1;
                } else {
                    array[i] = t;
                    return;
                }
            } else {
                if (t.compareTo(secondChild) > 0){
                    array[i] = array[i*2 + 2];
                    i = i*2 + 2;
                } else {
                    array[i] = t;
                    return;
                }
            }
        }
        array[i] = t;
    }

}

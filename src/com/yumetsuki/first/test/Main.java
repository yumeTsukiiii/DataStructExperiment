package com.yumetsuki.first.test;

import com.yumetsuki.first.Fifo;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        test1();
        test2();
    }

    /**
     * 测试一
     * */
    private static void test1() throws IOException{
        System.out.println("-------------Test1--------------\n");
        File file = new File("out/production/DataStructExperiment/com/yumetsuki/first/file/arbitrary.run");
        Fifo fifo = new Fifo();
        fifo.loadWorkLoad(file);
        fifo.Simulation();
        System.out.println("-------------End Test1-------------\n");
    }

    /**
     * 测试二
     * */
    private static void test2() throws IOException{
        System.out.println("-------------Test2--------------\n");
        File file = new File("out/production/DataStructExperiment/com/yumetsuki/first/file/bigfirst.run");
        Fifo fifo = new Fifo();
        fifo.loadWorkLoad(file);
        fifo.Simulation();
        System.out.println("-------------End Test2-------------\n");
    }
}

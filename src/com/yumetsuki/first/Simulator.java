package com.yumetsuki.first;

import com.yumetsuki.first.util.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 模拟器类
 * */
public class Simulator {

    protected int secondsPerPage = 2;

    protected Queue<Event> workLoad;

    public Simulator(){
        workLoad = new QueueImpl<>(20);
    }

    /**
     * 该方法从文件加载出模拟打印机的数据
     * @param file 储存模拟数据的文件
     * */
    public void loadWorkLoad(File file) throws IOException{
        /*从文件中获取每条数据*/
        ArrayList<String> list = readLines(file);

        /*对每条数据进行映射处理*/
        list.stream().map(this::mapToEventJob).forEach(jobPair -> {
            Event event = new Event();
            event.setArrivalTime(jobPair.getFirst());
            event.setJob(jobPair.getSecond());
            workLoad.add(event);
        });

    }

    /**
     * 从文件中读取每一行
     * */
    private ArrayList<String> readLines(File file) throws IOException {
        return FileUtil.readLines(file);
    }

    /**
     * 将数据映射为Job对象。
     * */
    private Pair<Integer, Job> mapToEventJob(String data){
        Pair<Integer, Job> pair = new Pair<>();
        String [] arr = data.split(" ");
        pair.setFirst(Integer.valueOf(arr[0]));
        Job job = new Job();
        job.setNumberOfPages(Integer.parseInt(arr[1]));
        job.setUser(arr[2]);
        pair.setSecond(job);
        return pair;
    }

}

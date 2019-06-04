package com.yumetsuki.second;

import com.yumetsuki.second.view.Activity;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("out/production/DataStructExperiment/com/yumetsuki/second/file/services.txt");
        Activity activity = new Activity(file);
        activity.start();
    }
}

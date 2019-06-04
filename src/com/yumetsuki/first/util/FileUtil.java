package com.yumetsuki.first.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtil {

    public static ArrayList<String> readLines(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> list = new ArrayList<>();
        String s;
        while ((s = reader.readLine()) != null){
            list.add(s);
        }
        reader.close();
        return list;
    }
}

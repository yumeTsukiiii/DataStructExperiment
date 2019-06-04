package com.yumetsuki.second.view;

import java.io.PrintStream;

public final class Logger {

    public Logger(){}

    public void info(String tag, String message){
        printMessage(System.out, tag, message);
    }

    public void singleLineInfo(String tag, String message){
        printSingleLineMessage(System.out, tag, message);
    }

    public void error(String tag, String message){
        printMessage(System.err, tag, message);
    }

    private void printMessage(PrintStream stream, String tag, String message){
        System.out.println();
           stream.println("[" + tag + "]" + ": " + message);
    }

    public void printSingleLineMessage(PrintStream stream, String tag, String message){
        stream.print("[" + tag + "]" + ": " + message);
    }
}

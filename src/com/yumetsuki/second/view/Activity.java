package com.yumetsuki.second.view;

import com.yumetsuki.second.model.City;
import com.yumetsuki.second.model.Path;
import com.yumetsuki.second.model.RailSystem;
import com.yumetsuki.second.model.Service;
import com.yumetsuki.second.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Activity {

    private RailSystem railSystem = RailSystem.getInstance();

    private Logger logger = new Logger();

    public Activity(File file) throws IOException{
        try {
            init(loadService(file));
        } catch (IOException e){
            showError("数据加载失败！");
            throw e;
        }
    }

    public void start(){
        startActivity();
    }

    private void startActivity(){
        enterSystem();
    }

    private void init(List<Service> services){
        for (Service service : services) {
            City city1 = new City(service.getStart());
            City city2 = new City(service.getEnd());
            railSystem.addCity(city1);
            railSystem.addCity(city2);
            railSystem.addRoute(service);
        }
    }

    private void enterSystem(){
        showTitle("Welcome to railSystem");
        while (true){
            showTip("Enter a start and destination city: <'quit' to exit>");
            showSingleLineInfo("input: ");
            String [] input = waitInput();
            if (input == null){
                showError("Please input correct message");
                showDividingLine();
                continue;
            }
            if (input.length == 1){
                break;
            }
            try {
                Path path = calculatePath(input[0], input[1]);
                showResult(path, input[0], input[1]);
            } catch (NoSuchElementException e){
                showError(e.getMessage());
            }
        }
    }

    private void showTitle(String message){
        System.out.println("----------" + message + "----------");
    }

    private void showTip(String message){
        System.out.println("***       " + message + "       ***");
    }

    private void showEnd(String message){
        System.out.println("----------" + message + "----------");
    }

    private String [] waitInput(){
        try {
            Scanner scanner = new Scanner(System.in);
            String []origin = scanner.nextLine().split(" ");
            String a = origin[0];
            if (a.equals("quit")){
                showEnd("Exit railSystem");
                return origin;
            }
            if (origin.length != 2) return null;
            String b = origin[1];
            return new String[]{a, b};
        } catch (Exception e){
            showError("Please input correct message");
            showDividingLine();
            return null;
        }
    }

    private Path calculatePath(String start, String end){
        return railSystem.searchBestPath(start, end);
    }

    private void showResult(Path path, String city1, String city2){
        if (path == null){
            showInfo("No route between these two cities");
            showDividingLine();
            return;
        }
        Path current = path;
        double cost = 0;
        double distance = 0;
        while (current != null){
            cost += current.getCost();
            distance += current.getDistance();
            current = current.getNextPath();
        }

        showResultInfo("The cheapest route from " + city1 + "to " + city2);
        showResultInfo("Cost: " + cost + " euros abd spans " + distance +
                " kilometers");
        showResultInfo(path.toString());
        showDividingLine();
    }

    private void showInfo(String message){
        logger.info("Info", message);
    }

    private void showSingleLineInfo(String message) {
        logger.singleLineInfo("Info", message);
    }

    private void showResultInfo(String message){
        logger.info("result", message);
    }

    private void showError(String message){
        logger.error("Error", message);
    }

    private void showDividingLine(){
        System.out.println();
    }

    private List<Service> loadService(File file) throws IOException{
        ArrayList<String> strings = readLines(file);
        return strings.stream().map(this::mapToService).collect(Collectors.toList());
    }

    private Service mapToService(String s){
        String []arr = s.split(" ");
        return new Service(
                arr[0],
                arr[1],
                Double.parseDouble(arr[2]),
                Double.parseDouble(arr[3])
        );
    }

    private ArrayList<String> readLines(File file) throws IOException {
        return FileUtil.readLines(file);
    }

}

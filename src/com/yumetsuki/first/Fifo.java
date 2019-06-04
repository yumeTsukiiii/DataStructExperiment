package com.yumetsuki.first;

/**
 * 打印机模拟类
 * */
public class Fifo extends Simulator {

    private Queue<Event> work;

    private int workUtilTime = 0;

    public Fifo(){
        super();
        work = new QueueImpl<>(20);
    }

    /**
     * 模拟打印机打印过程
     * */
    public void Simulation(){
        System.out.println("FIFO Simulation\n");
        int totalJobs = 0;
        int aggregateLatency = 0;
        double meanLatency;
        /*模拟过程，用time进行循环模拟时间*/
        for (int time = 0; !workLoad.isEmpty()||!work.isEmpty() ; time++){
            /*从workload中加载event到打印事件中*/
            addWork(time);
            /*该条件确定是否可以进行下一个打印任务*/
            if (time >= workUtilTime && !work.isEmpty()){
                aggregateLatency += nextWork(time);
                totalJobs++;
            }
        }

        meanLatency = (double) Math.round(((double)aggregateLatency/7) * 10000)/10000;

        System.out.println();
        info("totalJobs", String.valueOf(totalJobs));
        info("Aggregate latency",aggregateLatency + " seconds");
        info("Mean latency",meanLatency + " seconds");
    }

    /**
     * 该方法进行下一个工作
     * */
    private int nextWork(int time){
        Event event = work.poll();
        workUtilTime = event.getJob().getNumberOfPages() * secondsPerPage + time;
        info("Servicing",buildJobMsg(event.getJob(), time));
        return time - event.getArrivalTime();
    }

    /**
     * 该方法加入一个待进行工作
     * */
    private void addWork(int time){
        if (workLoad.isEmpty()) return;
        if (workLoad.peek().getArrivalTime() == time){
            Event event = workLoad.poll();
            work.add(event);
            info("Arriving", buildJobMsg(event.getJob(), time));
            addWork(time);
        }
    }

    /**
     * 展示信息
     * */
    private void info(String tag, String msg){
        System.out.println(
                tag + ": " + msg
        );
    }

    /**
     * job的信息
     * */
    private String buildJobMsg(Job job, int time){
        return job.getNumberOfPages() + " pages from " + job.getUser() + " at " + time + " seconds";
    }

}

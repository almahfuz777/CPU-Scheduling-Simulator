package com.example.cpu_scheduling_simulator;

public class ProcessRow {
    private String processID;
    private int arrivalTime;
    private int finishTime;
    private int burstTime;
    private int priority;

    public ProcessRow(){
        this.processID = "";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
        this.finishTime = -1;
    }
    public ProcessRow(String processID, int arrivalTime, int burstTime, int priority) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.finishTime = -1;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getFinishTime() {
        return finishTime;
    }
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}

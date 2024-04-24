package com.example.cpu_scheduling_simulator;

public class ProcessRow {
    private String processID;
    private double arrivalTime;
    private double burstTime;
    private int priority;

    public ProcessRow(){
        this.processID = "";
        this.arrivalTime = 0;
        this.burstTime = 0;
        this.priority = 0;
    }
    public ProcessRow(String processID, double arrivalTime, double burstTime, int priority) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}

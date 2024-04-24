package com.example.cpu_scheduling_simulator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solve {
    private String algorithmType;
    private String algorithm;
    private double timeQuantum;
    private ArrayList<ProcessRow> processList;

    public Solve(){
        this.algorithmType = "";
        this.algorithm = "";
        this.timeQuantum = 0;
        this.processList = new ArrayList<>();
    }
    public Solve(String algorithmType, String algorithm, double timeQuantum, ArrayList<ProcessRow> processList){
        this.algorithmType = algorithmType;
        this.algorithm = algorithm;
        this.timeQuantum = timeQuantum;
        this.processList = processList;
    }

    // getters & setters
    public String getAlgorithmType() {
        return algorithmType;
    }
    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    public String getAlgorithm() {
        return algorithm;
    }
    public double getTimeQuantum() {
        return timeQuantum;
    }
    public void setTimeQuantum(double timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
    public ArrayList<ProcessRow> getProcessList() {
        return processList;
    }
    public void setProcessList(ArrayList<ProcessRow> processList) {
        this.processList = processList;
    }

    // Add row to processList
    public void addProcess(ProcessRow processRow) {
        processList.add(processRow);
    }

    // calculations
    private ArrayList<String> processSequence = new ArrayList<>();
    private ArrayList<Double> timeSequence = new ArrayList<>();

    public ArrayList<String> getProcessSequence(){
        // referring to the appropriate algorithm
        switch (algorithm){
            case "FCFS (non-Preemptive)":
                FCFS_calulation();
                break;
            case "Round Robin (Preemptive)":
//                RR_calculation();
                break;
            case "SJF":
//                SJF_calculation();
                break;
            case "Priority":
//                Priority_calculation();
                break;
            default:
                Log.d("Process Selection error", "getProcessSequence: default");
                break;
        }

        Log.d("processSequence", String.valueOf(processSequence));
        return processSequence;
    }
    public ArrayList<Double> getTimeSequence() {
        Log.d("timeSequence", String.valueOf(timeSequence));
        return timeSequence;
    }

    // algorithm types
    private ArrayList<String> FCFS_calulation() {
        // declaring arrays
        ArrayList<String> p_seq = new ArrayList<>();
        ArrayList<Double> t_seq = new ArrayList<>();
        // Sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Double.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        double time = processList.get(0).getArrivalTime();
        t_seq.add(time);

        for(ProcessRow x:processList){
            p_seq.add(x.getProcessID());
            time+=x.getBurstTime();
            t_seq.add(time);
        }
        processSequence = p_seq;
        timeSequence = t_seq;
        return processSequence;
    }
    /*
    private ArrayList<String> RR_calculation() {

    }
    private ArrayList<String> SJF_calculation() {

    }
    private ArrayList<String> Priority_calculation() {

    }
*/
}

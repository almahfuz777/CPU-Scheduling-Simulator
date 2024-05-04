package com.example.cpu_scheduling_simulator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solve {
    private String algorithmType;
    private String algorithm;
    private int timeQuantum;
    private ArrayList<ProcessRow> processList;
    private ArrayList<Integer> cycle;

    public Solve(){
        this.algorithmType = "";
        this.algorithm = "";
        this.timeQuantum = -1;
        this.processList = new ArrayList<>();
        this.cycle = new ArrayList<>();
    }
    public Solve(String algorithmType, String algorithm, int timeQuantum, ArrayList<ProcessRow> processList){
        this.algorithmType = algorithmType;
        this.algorithm = algorithm;
        this.timeQuantum = timeQuantum;
        this.processList = processList;
        this.cycle = new ArrayList<>();
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
    public int getTimeQuantum() {
        return timeQuantum;
    }
    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }
    public ArrayList<ProcessRow> getProcessList() {
        return processList;
    }
    public void setProcessList(ArrayList<ProcessRow> processList) {
        this.processList = processList;
    }
    public ArrayList<Integer> getCycle() {
        return cycle;
    }

    public void setCycle(ArrayList<Integer> cycle) {
        this.cycle = cycle;
    }

    // Add row to processList
    public void addProcess(ProcessRow processRow) {
        processList.add(processRow);
    }

    // calculations
    public void getSequence(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        // referring to the appropriate algorithm
        if(algorithmType.equals("non-Preemptive")){
            switch (algorithm){
                case "FCFS":
                    FCFS_nonPreemptive(processSequence, timeSequence);
                    break;
                case "SJF":
                    SJF_nonPreemptive(processSequence, timeSequence);
                    break;
                case "Priority":
                    Priority_nonPreemptive(processSequence, timeSequence);
                    break;
                default:
                    Log.d("Process Selection error", "getProcessSequence: default");
                    break;
            }
        }
        else if(algorithmType.equals("preemptive")){
            switch (algorithm){
                case "Round Robin":
                    try {
                        RR_preemptive(processSequence, timeSequence);
                    }catch (Exception e){
                        Log.d("Error", "RR_preempive error ");
                    }
                    break;
                case "SJF":
                    SJF_preemptive(processSequence, timeSequence);
                    break;
                case "Priority":
                    Priority_preemptive(processSequence, timeSequence);
                    break;
                default:
                    Log.d("Process Selection error", "getProcessSequence: default");
                    break;
            }
        }
        else{
            Log.d("process selection error", "selected wrong processType");
        }

        Log.d("processSequence", String.valueOf(processSequence));
        Log.d("timeSequence", String.valueOf(timeSequence));
    }

    private int waitingTime;
    public int getWaitingTime() {
        return waitingTime;
    }
    public double getAvgWaitingTime(){
        Log.d("waiting time", String.valueOf(waitingTime));
        Log.d("awt", String.valueOf(waitingTime/processList.size()));
        return ((double) (waitingTime))/processList.size();
    }

    // non-Preemptive algorithms
    private void FCFS_nonPreemptive(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        // sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // getting the initial start time
        int currentTime = processList.get(0).getArrivalTime();
        timeSequence.add(currentTime);

        waitingTime = 0;
        // iterating and executing processes
        for(ProcessRow x:processList){
            processSequence.add(x.getProcessID());
            waitingTime+=(currentTime-x.getArrivalTime());
            currentTime+=x.getBurstTime();
            timeSequence.add(currentTime);
        }

    }

    private void SJF_nonPreemptive(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        // sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // copying the original ArrayList to a temporary List
        List<ProcessRow> rows = new ArrayList<>(processList);

        // adding first appeared process
        waitingTime = 0;
        int currentTime = rows.get(0).getArrivalTime();
        timeSequence.add(currentTime);

        while(!rows.isEmpty()){
            List<ProcessRow> availableRows = new ArrayList<>();
            // populating available rows
            for (ProcessRow x: rows){
                if(x.getArrivalTime() <= currentTime)
                    availableRows.add(x);
            }
            // sort them according to burst time
            Collections.sort(availableRows, new Comparator<ProcessRow>() {
                @Override
                public int compare(ProcessRow p1, ProcessRow p2) {
                    return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
                }
            });

            // executing one process
            ProcessRow row = availableRows.get(0);
            processSequence.add(row.getProcessID());
            waitingTime+=(currentTime-row.getArrivalTime());    // waiting time
            currentTime+=row.getBurstTime();
            timeSequence.add(currentTime); // end time for the process

            // deleting the process from list
            for (int i=0;i<rows.size();i++){
                if(rows.get(i).getProcessID().equals(row.getProcessID())){
                    rows.remove(i);
                    break;
                }
            }

        }
    }

    private void Priority_nonPreemptive(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        // sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // copying the original ArrayList to a temporary List
        List<ProcessRow> rows = new ArrayList<>(processList);

        // adding first appeared process
        waitingTime = 0;
        int currentTime = rows.get(0).getArrivalTime();
        timeSequence.add(currentTime);

        while (!rows.isEmpty()) {
            List<ProcessRow> availableRows = new ArrayList<>();
            // populating available rows
            for (ProcessRow x : rows) {
                if (x.getArrivalTime() <= currentTime)
                    availableRows.add(x);
            }
            // sort them according to priority
            Collections.sort(availableRows, new Comparator<ProcessRow>() {
                @Override
                public int compare(ProcessRow p1, ProcessRow p2) {
                    return Integer.compare(p1.getPriority(), p2.getPriority());
                }
            });

            // executing one process
            ProcessRow row = availableRows.get(0);
            processSequence.add(row.getProcessID());
            waitingTime += (currentTime - row.getArrivalTime());    // waiting time
            currentTime += row.getBurstTime();
            timeSequence.add(currentTime); // end time for the process

            // deleting the process from list
            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getProcessID().equals(row.getProcessID())) {
                    rows.remove(i);
                    break;
                }
            }


        }
    }

    // preemptive algorithms
    private void RR_preemptive(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        Log.d("Entered the function RR", "RR_preemptive: ");
        // sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // copying the original ArrayList to a temporary List
        List<ProcessRow> rows = new ArrayList<>(processList);
        List<ProcessRow> waitingList = new ArrayList<>();

        // adding first appeared process
        waitingTime = 0;
        int currentTime = rows.get(0).getArrivalTime();
        timeSequence.add(currentTime);
        cycle.add(currentTime); // first cycle starts
//        int cnt = 0;

        while (!rows.isEmpty()) {
//            Log.d(String.valueOf(cnt), "loop"+cnt);
//            cnt++;
            List<ProcessRow> availableRows = new ArrayList<>();
            // populating available rows
            for (ProcessRow x : rows) {
                if (x.getArrivalTime() <= currentTime)
                    availableRows.add(x);
            }

            //

            // executing the first process (most priority)
            ProcessRow row = availableRows.get(0);
            processSequence.add(row.getProcessID());

            int duration = Math.min(row.getBurstTime(), timeQuantum);

            row.setBurstTime(row.getBurstTime()-duration);
            timeSequence.add(currentTime + duration);//


            // waiting time
            if(row.getFinishTime()==-1){
                waitingTime+=(currentTime-row.getArrivalTime());
                row.setFinishTime(currentTime+duration);
            }
            else {
                waitingTime += (currentTime - row.getFinishTime());
            }

            if(row.getBurstTime()>0)
                waitingList.add(row);
            rows.remove(row);

            currentTime+=duration;

            if(rows.isEmpty()){
                cycle.add(currentTime); //end of a cycle
//                availableRows = new ArrayList<>(waitingList);
                rows.addAll(waitingList);
                waitingList.clear();
            }

        }

    }
    private void SJF_preemptive(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        // sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // copying the original ArrayList to a temporary List
        List<ProcessRow> rows = new ArrayList<>(processList);

        // adding first appeared process
        waitingTime = 0;
        int currentTime = rows.get(0).getArrivalTime();
        timeSequence.add(currentTime);

        String lastAppeared = "";
        int skip=0;

        while (!rows.isEmpty()) {
            List<ProcessRow> availableRows = new ArrayList<>();
            // populating available rows
            for (ProcessRow x : rows) {
                if (x.getArrivalTime() <= currentTime)
                    availableRows.add(x);
            }

            // sort them according to burst time
            Collections.sort(availableRows, new Comparator<ProcessRow>() {
                @Override
                public int compare(ProcessRow p1, ProcessRow p2) {
                    return Integer.compare(p1.getBurstTime(), p2.getBurstTime());
                }
            });

            // executing the first process (most priority)
            ProcessRow row = availableRows.get(0);

            if(!row.getProcessID().equals(lastAppeared)) {
                processSequence.add(row.getProcessID());
                lastAppeared = row.getProcessID();
                skip++; // different process from the last one. skips first one.
                if(skip>1) timeSequence.add(currentTime);   // end time of the last process

                // waiting time
                if (row.getFinishTime() != -1)
                    waitingTime += (currentTime - row.getFinishTime());
                else
                    waitingTime += (currentTime - row.getArrivalTime());
            }

            row.setFinishTime(currentTime+1);
            row.setBurstTime(row.getBurstTime() - 1);
            currentTime++;

            // if process is over
            if (row.getBurstTime() == 0) {
                for (int i = 0; i < rows.size(); i++) {
                    if (rows.get(i).getProcessID().equals(row.getProcessID())) {
                        rows.remove(i); // removing from main list
                        break;
                    }
                }
            }

        }
        timeSequence.add(currentTime);

    }
    private void Priority_preemptive(ArrayList<String> processSequence, ArrayList<Integer> timeSequence) {
        // sorting processList according to ArrivalTime
        Collections.sort(processList, new Comparator<ProcessRow>() {
            @Override
            public int compare(ProcessRow p1, ProcessRow p2) {
                return Integer.compare(p1.getArrivalTime(), p2.getArrivalTime());
            }
        });

        // copying the original ArrayList to a temporary List
        List<ProcessRow> rows = new ArrayList<>(processList);

        // adding first appeared process
        waitingTime = 0;
        int currentTime = rows.get(0).getArrivalTime();
        timeSequence.add(currentTime);

        String lastAppeared = "";
        int skip=0;

        while (!rows.isEmpty()) {
            List<ProcessRow> availableRows = new ArrayList<>();
            // populating available rows
            for (ProcessRow x : rows) {
                if (x.getArrivalTime() <= currentTime)
                    availableRows.add(x);
            }

            // sort them according to priority
            Collections.sort(availableRows, new Comparator<ProcessRow>() {
                @Override
                public int compare(ProcessRow p1, ProcessRow p2) {
                    return Integer.compare(p1.getPriority(), p2.getPriority());
                }
            });

            // executing the first process (most priority)
            ProcessRow row = availableRows.get(0);

            if(!row.getProcessID().equals(lastAppeared)) {
                processSequence.add(row.getProcessID());
                lastAppeared = row.getProcessID();
                skip++; // different process from the last one. skips first one.
                if(skip>1) timeSequence.add(currentTime);   // end time of the last process

                // waiting time
                if (row.getFinishTime() != -1)
                    waitingTime += (currentTime - row.getFinishTime());
                else
                    waitingTime += (currentTime - row.getArrivalTime());
            }

            row.setFinishTime(currentTime+1);
            row.setBurstTime(row.getBurstTime() - 1);
            currentTime++;

            // if process is over
            if (row.getBurstTime() == 0) {
                for (int i = 0; i < rows.size(); i++) {
                    if (rows.get(i).getProcessID().equals(row.getProcessID())) {
                        rows.remove(i); // removing from main list
                        break;
                    }
                }
            }

        }
        timeSequence.add(currentTime);

    }



}


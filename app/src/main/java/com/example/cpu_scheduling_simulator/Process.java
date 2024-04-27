package com.example.cpu_scheduling_simulator;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class Process extends AppCompatActivity {
    private Solve solve;
    //
    private EditText pid;
    private EditText arrivalTime;
    private EditText burstTime;
    private EditText priority;
    private Spinner spinner;

    private ArrayList<String> algorithms;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.process);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.process_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // solve
        solve = new Solve();

        // find views
        pid = findViewById(R.id.pid);
        arrivalTime = findViewById(R.id.arrivalTime);
        burstTime = findViewById(R.id.burstTime);
        priority = findViewById(R.id.priority);
        spinner = findViewById(R.id.algorithm);

        // Adding listener to "ADD PROCESS"
        Button addButton = findViewById(R.id.addProcess);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adding new process
                try {
                    addProcess();
                }catch (Exception e){
                    Toast.makeText(Process.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up a listener for the Spinner
        spinner.setEnabled(false);
        spinner.setClickable(false);

        algorithms = new ArrayList<>(Arrays.asList("Select Algorithm", "FCFS", "Round Robin", "SJF", "Priority"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, algorithms);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);   // checkbox
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                solve.setAlgorithm(selectedItem);

                if(!selectedItem.equals("Select Algorithm"))
                    Toast.makeText(Process.this, "Algorithm: "+selectedItem, Toast.LENGTH_SHORT).show();

                // Round Robin
                EditText timeQuantumEditText = findViewById(R.id.timeQuantum);
                if (!selectedItem.equals("Round Robin (Preemptive)")) {
                    solve.setTimeQuantum(-1);
                    timeQuantumEditText.setEnabled(false);
                    timeQuantumEditText.setBackgroundResource(R.drawable.btn_disabled);
                } else {
                    // setting time Quantum
                    String tq = timeQuantumEditText.getText().toString();
                    if(!tq.isEmpty())
                        solve.setTimeQuantum(Integer.parseInt(tq));
                    timeQuantumEditText.setEnabled(true);
                    timeQuantumEditText.setBackgroundResource(R.drawable.edittext_bg);
                }

                // priority
                EditText priorityEditText = findViewById(R.id.priority);
                if (!selectedItem.equals("Priority")) {
                    priorityEditText.setEnabled(false);
                    priorityEditText.setBackgroundResource(R.drawable.btn_disabled);
                } else {
                    priorityEditText.setEnabled(true);
                    priorityEditText.setBackgroundResource(R.drawable.edittext_bg);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected
            }
        });

    }


    // ADD PROCESS
    int rowID=0;
    private void addProcess() {
        // Get the text entered by the user in the EditText fields
        String cross = "❌";
        String processID = pid.getText().toString();
        int arrivalT = Integer.parseInt(arrivalTime.getText().toString());
        int burstT = Integer.parseInt(burstTime.getText().toString());
        int priorityy = 0;

        if(!(priority.getText().toString().isEmpty()))
            priorityy = Integer.parseInt(priority.getText().toString());

        if (!processID.isEmpty() && !(arrivalTime.getText().toString().isEmpty()) && !(burstTime.getText().toString().isEmpty())) {
            // add new row to array
            ProcessRow processRow = new ProcessRow(processID, arrivalT, burstT, priorityy);
            solve.addProcess(processRow);

            // add row to process.xml
            // Create a new row for the table
            rowID++;
            TableRow newRow = new TableRow(this);
            newRow.setId(rowID);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.weight = .4f;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // Delete
            TextView delete = (new TextView(this));
            delete.setText(cross);
            delete.setGravity(Gravity.CENTER);
            delete.setLayoutParams(params);
            delete.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            delete.setId(rowID + 10);
            delete.setClickable(true);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRow((int) v.getId() - 10);
                }
            });

            params = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1f;

            // processID
            TextView pidTextView = new TextView(this);
            pidTextView.setText(processID);
            pidTextView.setGravity(Gravity.CENTER);
            pidTextView.setLayoutParams(params);
            pidTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pidTextView.setTextColor(getResources().getColor(R.color.white));
            pidTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            pidTextView.setTypeface(null, Typeface.BOLD);

            // arrival Time
            TextView arrivalTimeTextView = new TextView(this);
            arrivalTimeTextView.setText(String.valueOf(arrivalT));
            arrivalTimeTextView.setGravity(Gravity.CENTER);
            arrivalTimeTextView.setLayoutParams(params);
            arrivalTimeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            arrivalTimeTextView.setTextColor(getResources().getColor(R.color.white));
            arrivalTimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            arrivalTimeTextView.setTypeface(null, Typeface.BOLD);

            // Burst Time
            TextView burstTimeTextView = new TextView(this);
            burstTimeTextView.setText(String.valueOf(burstT));
            burstTimeTextView.setGravity(Gravity.CENTER);
            burstTimeTextView.setLayoutParams(params);
            burstTimeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            burstTimeTextView.setTextColor(getResources().getColor(R.color.white));
            burstTimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            burstTimeTextView.setTypeface(null, Typeface.BOLD);

            // priority
            TextView priorityTextView = new TextView(this);
            priorityTextView.setText(String.valueOf(priorityy));
//            Log.d("priorityy = "+priorityy, ""+String.valueOf(priorityy));
            priorityTextView.setGravity(Gravity.CENTER);
            priorityTextView.setLayoutParams(params);
            priorityTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            priorityTextView.setTextColor(getResources().getColor(R.color.white));
            priorityTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            priorityTextView.setTypeface(null, Typeface.BOLD);

            // Add TextViews to the new row
            newRow.addView(delete);
            newRow.addView(pidTextView);
            newRow.addView(arrivalTimeTextView);
            newRow.addView(burstTimeTextView);
            newRow.addView(priorityTextView);

            // Add the new row to the table layout
            TableLayout tableLayout = findViewById(R.id.tableLayout);
            tableLayout.addView(newRow);
            Toast.makeText(this, "Process added", Toast.LENGTH_SHORT).show();

            Button solveButton = findViewById(R.id.solve);
            solveButton.setVisibility(View.VISIBLE); // visible only if there's data in the array

            TableLayout table = findViewById(R.id.tableLayout);
            table.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(this, "Insert values first", Toast.LENGTH_SHORT).show();
        }

    }
    // DELETE PROCESS
    public void deleteRow(int rowID) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        View row = tableLayout.findViewById(rowID);
        if (row instanceof TableRow) {
            tableLayout.removeView(row);
            Toast.makeText(this, "Process Deleted", Toast.LENGTH_SHORT).show();
        }
        // remove from array
        for (ProcessRow x : solve.getProcessList()) {
            if (row.getId() == rowID) {
                solve.getProcessList().remove(x);
                break;
            }
        }
        // change visibility of table and button in case of empty array
        if(solve.getProcessList().isEmpty()){
            Button solveButton = findViewById(R.id.solve);
            solveButton.setVisibility(View.GONE); // visible only if there's data in the array

            TableLayout table = findViewById(R.id.tableLayout);
            table.setVisibility(View.GONE);
        }
        View res = findViewById(R.id.result);
        res.setVisibility(View.GONE);
        Log.d("deleteRow called", "new arary size: "+ String.valueOf(solve.getProcessList().size()));
    }

    // clicked non-Preemptive
    public void nonPreemptive(View view) {
        solve.setAlgorithmType("non-Preemptive");
        view.setBackgroundResource(R.drawable.btn_left_selected);
        View rightBtn = findViewById(R.id.preemptive);
        rightBtn.setBackgroundResource(R.drawable.btn_right);
        Toast.makeText(this, "Non-Preemptive selected", Toast.LENGTH_SHORT).show();

        algorithms.remove("Round Robin");
        if (!algorithms.contains("FCFS"))
            algorithms.add(1, "FCFS");

        spinner.setEnabled(true);
        spinner.setClickable(true);
    }
    public void preemptive(View view) {
        solve.setAlgorithmType("preemptive");
        view.setBackgroundResource(R.drawable.btn_right_selected);
        View leftBtn = findViewById(R.id.non_preemptive);
        leftBtn.setBackgroundResource(R.drawable.btn_left);
        Toast.makeText(this, "Preemptive selected", Toast.LENGTH_SHORT).show();

        algorithms.remove("FCFS");
        if (!algorithms.contains("Round Robin"))
            algorithms.add(1, "Round Robin");

        spinner.setEnabled(true);
        spinner.setClickable(true);
    }
    // clicked solved
    public void solve(View view) {
        Toast.makeText(this, "Solved clicked", Toast.LENGTH_SHORT).show();
        ArrayList<String> processSequence = new ArrayList<>();
        ArrayList<Integer> timeSequence = new ArrayList<>();

        // get sequences
        solve.getSequence(processSequence, timeSequence);
        double awt = solve.getAvgWaitingTime();

        // build the gantt chart
        TextView res = findViewById(R.id.result);
        res.setVisibility(View.VISIBLE);
        res.setText("processSequence = "+ processSequence+"\n"+"timeSequence = "+String.valueOf(timeSequence)+"\nawt= "+awt);

    }

}
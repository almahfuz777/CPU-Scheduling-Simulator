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
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;


public class Process extends AppCompatActivity {
    private EditText pid;
    private EditText arrivalTime;
    private EditText burstTime;
    private Spinner algorithm;

    int rowID=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.process);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.process_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Collect User Inputs
        pid = findViewById(R.id.pid);
        arrivalTime = findViewById(R.id.arrivalTime);
        burstTime = findViewById(R.id.burstTime);
        algorithm = findViewById(R.id.algorithm);

        // Find the "Add Process" button by its ID
        Button addButton = findViewById(R.id.addProcess);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProcess();
            }
        });
        // Set up a listener for the Spinner
        algorithm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the Spinner
                String selectedItem = parent.getItemAtPosition(position).toString();
                // You can use the selected item as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected
            }
        });
    }

    // Delete rows from process table
    public void deleteRow(int rowID) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        View row = tableLayout.findViewById(rowID);
        if (row instanceof TableRow) {
            tableLayout.removeView(row);
        }
    }

    // Method to handle adding a new process
    private void addProcess() {
        rowID++;
        // Get the text entered by the user in the EditText fields
        String cross = "❌";
        String processID = pid.getText().toString();
        String arrivalT = arrivalTime.getText().toString();
        String burstT = burstTime.getText().toString();

        // Create a new row for the table
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
        delete.setId(rowID+10);
        delete.setClickable(true);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRow((int)v.getId()-10);
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
        arrivalTimeTextView.setText(arrivalT);
        arrivalTimeTextView.setGravity(Gravity.CENTER);
        arrivalTimeTextView.setLayoutParams(params);
        arrivalTimeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        arrivalTimeTextView.setTextColor(getResources().getColor(R.color.white));
        arrivalTimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        arrivalTimeTextView.setTypeface(null, Typeface.BOLD);

        // Burst Time
        TextView burstTimeTextView = new TextView(this);
        burstTimeTextView.setText(burstT);
        burstTimeTextView.setGravity(Gravity.CENTER);
        burstTimeTextView.setLayoutParams(params);
        burstTimeTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        burstTimeTextView.setTextColor(getResources().getColor(R.color.white));
        burstTimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        burstTimeTextView.setTypeface(null, Typeface.BOLD);

        // Add TextViews to the new row
        newRow.addView(delete);
        newRow.addView(pidTextView);
        newRow.addView(arrivalTimeTextView);
        newRow.addView(burstTimeTextView);

        // Add the new row to the table layout
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        tableLayout.addView(newRow);
    }

}
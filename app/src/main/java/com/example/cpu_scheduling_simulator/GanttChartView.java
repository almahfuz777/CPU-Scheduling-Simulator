package com.example.cpu_scheduling_simulator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GanttChartView extends View {
    private List<String> processSequence;
    private List<Integer> timeSequence;
    private Paint paint;
    private Map<String, Integer> processColors;
    private Random random;

    public GanttChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(50); // Increased font size for processID
        paint.setStrokeWidth(3);
        processColors = new HashMap<>();
        random = new Random();
    }

    public void setSequences(List<String> processSequence, List<Integer> timeSequence) {
        this.processSequence = processSequence;
        this.timeSequence = timeSequence;
        // Assign random colors to processes
        for (String process : processSequence) {
            if (!processColors.containsKey(process)) {
                processColors.put(process, getRandomColor());
            }
        }
        invalidate();
    }

    private int getRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (processSequence == null || processSequence.isEmpty() || timeSequence == null || timeSequence.isEmpty()) return;

        int chartHeight = getHeight();
        int chartWidth = getWidth();
        int margin = 50;
        int processHeight = (chartHeight - 2 * margin) / 2; // Reduced height of the chart
        int totalDuration = timeSequence.get(timeSequence.size() - 1);

        // Draw each process
        for (int i = 0; i < processSequence.size(); i++) {
            String process = processSequence.get(i);
            int startTime = timeSequence.get(i);
            int endTime = timeSequence.get(i + 1);
            int left = margin + (int) ((startTime / (float) totalDuration) * (chartWidth - 2 * margin));
            int right = margin + (int) ((endTime / (float) totalDuration) * (chartWidth - 2 * margin));
            int top = margin;
            int bottom = top + processHeight;

            // Draw the rectangle
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(processColors.get(process));
            canvas.drawRect(left, top, right, bottom, paint);

            // Draw the process name
            paint.setColor(Color.WHITE);
            paint.setTextSize(50); // Increased font size for processID
            canvas.drawText(process, left + 10, top + processHeight / 2 + 15, paint);

            // Draw the time labels
            paint.setColor(Color.WHITE);
            paint.setTextSize(40); // Increased font size for time labels
            canvas.drawText(String.valueOf(startTime), left, bottom + 40, paint);
            if(i==processSequence.size()-1){
                canvas.drawText(String.valueOf(endTime), right - 50, bottom + 40, paint);
            }
        }

    }
}

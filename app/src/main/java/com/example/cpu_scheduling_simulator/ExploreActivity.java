package com.example.cpu_scheduling_simulator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void algorithmOverview(View view){
        Intent intent = new Intent(ExploreActivity.this, AlgorithmsOverviewActivity.class);
        startActivity(intent);
    }
    public void howToUse(View view){
        Intent intent = new Intent(ExploreActivity.this, howToUseActivity.class);
        startActivity(intent);
    }
    public void futureUpdates(View view){
        Intent intent = new Intent(ExploreActivity.this, futureUpdatesActivity.class);
        startActivity(intent);
    }
    public void contribute(View view){
        Intent intent = new Intent(ExploreActivity.this, contributeActivity.class);
        startActivity(intent);
    }

}

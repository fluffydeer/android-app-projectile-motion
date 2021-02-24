package com.example.projectilemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {
    final String KEY_HEIGHT = "KEY_HEIGHT";
    final String KEY_DISTANCE = "KEY_DISTANCE";
    final String KEY_TIME = "KEY_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        getSupportActionBar().hide();

        createTable();
    }

    public void createTable(){
        Bundle bundle = getIntent().getExtras();
        ArrayList<Double> xValues = (ArrayList<Double>) bundle.get(KEY_DISTANCE);
        ArrayList<Double> yValues = (ArrayList<Double>) bundle.get(KEY_HEIGHT);
        ArrayList<Double> timeValues = (ArrayList<Double>) bundle.get(KEY_TIME);

        TableWithBorders tableHeader = new TableWithBorders(
                this, new String[][] {
                        new String[]{"TIME", "X-AXIS", "Y-AXIS"}});

        TableWithBorders table = new TableWithBorders(
                this, timeValues, xValues, yValues);

        TableLayout tableLayout = findViewById(R.id.tableLayoutTableHeader);
        tableLayout.addView(tableHeader);

        ScrollView main = (ScrollView) findViewById(R.id.scrollViewTable);
        main.addView(table);
    }
}
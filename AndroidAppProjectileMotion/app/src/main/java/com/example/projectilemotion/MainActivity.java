package com.example.projectilemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private ProjectileMotion projectileMotion = new ProjectileMotion();
    private int timeInMills = 50;

    private static final String KEY_HEIGHT = "KEY_HEIGHT";
    private static final String KEY_DISTANCE = "KEY_DISTANCE";
    private static final String KEY_TIME = "KEY_TIME";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        setSeekBarListeners();
        setButtonListeners();
        setCheckBoxListener(R.id.checkBox);
    }

    public void calculateCoordinates(final Class classToInvoke) throws InterruptedException {
        int angle = ((SeekBar) findViewById(R.id.seekBarAngle)).getProgress();
        int force = ((SeekBar) findViewById(R.id.seekBarForce)).getProgress();
        if (((CheckBox) findViewById(R.id.checkBox)).isChecked()) {
            fetchDataFromServer(angle, force);
        }//ak sa zmenili hodnoty, vypocitaj na novo
        else if ((angle != projectileMotion.getAngle()) || (force != projectileMotion.getForce())) {
            fetchDataLocally(angle, force);
        }
        initializeIntent(classToInvoke);
    }

    public void fetchDataFromServer(double angle, double force) throws InterruptedException {
        Log.i(TAG, "Fetching data from server");
        String serverIPAddress = ((EditText)findViewById(R.id.editTextIPAddress)).getText().toString();
        projectileMotion.getCoordinatesFromServer(angle, force, serverIPAddress);
        while (!projectileMotion.getCommunicationDone()) {
            Thread.sleep(timeInMills);
        }
        projectileMotion.setCommunicationDone(false);
    }

    public void fetchDataLocally(double angle, double force){
        Log.i(TAG, "Fetching data locally");
        projectileMotion.getCoordinates(angle, force);
    }

    public void initializeIntent(Class classToInvoke) {
        Intent intent = new Intent(getBaseContext(), classToInvoke);
        intent.putExtra(KEY_TIME, projectileMotion.getTimes());
        intent.putExtra(KEY_DISTANCE, projectileMotion.getDistances());
        intent.putExtra(KEY_HEIGHT, projectileMotion.getHeights());
        startActivity(intent);
    }

    public void setSeekBarListeners() {
        setSeekBarListener(R.id.seekBarAngle, R.id.textViewSeekBarAngle);
        setSeekBarListener(R.id.seekBarForce, R.id.textViewSeekBarForce);
    }

    public void setSeekBarListener(int seekBarId, int textViewId) {
        SeekBar seekBar = (SeekBar) findViewById(seekBarId);
        final TextView textViewSeekBar = (TextView) findViewById(textViewId);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textViewSeekBar.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void setCheckBoxListener(int id) {
        CheckBox checkBox = ((CheckBox) findViewById(id));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                EditText editText = (EditText)findViewById(R.id.editTextIPAddress);
                if(isChecked){
                    editText.setVisibility(View.VISIBLE);
                }else{
                    editText.setVisibility(View.GONE);
                }
            }
        });
    }

    public void setButtonListeners() {
        setButtonListener(R.id.buttonTable, TableActivity.class);
        setButtonListener(R.id.buttonAnimation, AnimationActivity.class);
        setButtonListener(R.id.buttonGraph, GraphActivity.class);
    }

    public void setButtonListener(int id, final Class classToInvoke) {
        Button button = findViewById(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    calculateCoordinates(classToInvoke);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}




package com.example.projectilemotion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;

//pridat button na zopakovanie animacie?
public class AnimationActivity extends AppCompatActivity {
    final String KEY_HEIGHT = "KEY_HEIGHT";
    final String KEY_TIME = "KEY_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        getSupportActionBar().hide();

        AnimatedView animatedView = (AnimatedView) findViewById(R.id.anim_view);
        Bundle bundle = getIntent().getExtras();
        //nie je to nastovanie animacie nebezpecne? je to vobec dobra dizajnova praktika?
        animatedView.setHeightValues((ArrayList<Double>) bundle.get(KEY_HEIGHT));
        animatedView.setTimeValues((ArrayList<Double>) bundle.get(KEY_TIME));
    }
}
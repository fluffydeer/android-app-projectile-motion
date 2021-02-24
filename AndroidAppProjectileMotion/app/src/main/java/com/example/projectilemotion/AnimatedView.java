package com.example.projectilemotion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;

import java.util.ArrayList;

//angle 45 a force 60 - pekna animacia
//angle: 20, force 90

public class AnimatedView extends androidx.appcompat.widget.AppCompatImageView{
    private final int MULTIPLIER_FOR_X = 100, MULTIPLIER_FOR_Y = 10;
    private final int FRAME_RATE = 30;
    private int x, y, i = -20;
    private ArrayList<Double> heightValues;
    private ArrayList<Double> timeValues;
    private Handler h;
    private BitmapDrawable ufo;

    public void setTimeValues(ArrayList<Double> timeValues) {
        this.timeValues = timeValues;
    }


    public void setHeightValues(ArrayList<Double> heightValues) {
        this.heightValues = heightValues;
    }

    public AnimatedView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        h = new Handler();
        ufo = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ufo);
        int newWidth = 200, newHeight = 200;
        Bitmap bitmapResized = Bitmap.createScaledBitmap(((BitmapDrawable) ufo).getBitmap(), newWidth, newHeight, true);
        ufo = new BitmapDrawable(getResources(), bitmapResized);
    }
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    protected void onDraw(Canvas c){
        int baseHeight = 3*(this.getHeight()/4);
        if(i < 0){      //kvoli tomuto to chvilu pocka
            x = 0;
            y = baseHeight - ufo.getBitmap().getHeight();
            i++;
        }
        else if(i < timeValues.size()){
            x = (int)(timeValues.get(i)* MULTIPLIER_FOR_X);
            y = (int)(baseHeight - ufo.getBitmap().getHeight() - (heightValues.get(i)* MULTIPLIER_FOR_Y));
            i++;
        }
        else {          //ak uz nie su hodnoty tak to zostane na obrazovke
            x = (int)(timeValues.get(timeValues.size()-1)* MULTIPLIER_FOR_X);
            y = baseHeight - ufo.getBitmap().getHeight();
        }
        c.drawBitmap(ufo.getBitmap(), x, y, null);
        h.postDelayed(r, FRAME_RATE);
    }
}
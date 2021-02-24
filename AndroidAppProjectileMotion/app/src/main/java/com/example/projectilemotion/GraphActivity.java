//achartengine: https://stackoverflow.com/questions/25014369/achartengine-and-android-studio

package com.example.projectilemotion;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;


public class GraphActivity extends Activity {
    private XYMultipleSeriesRenderer myRenderer;
    private XYSeries graphSeries;
    private XYMultipleSeriesDataset dataset;
    private ArrayList<Double> xValues;
    private ArrayList<Double> yValues;
    final String KEY_HEIGHT = "KEY_HEIGHT";
    final String KEY_TIME = "KEY_TIME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle bundle = getIntent().getExtras();
        this.xValues = (ArrayList<Double>) bundle.get(KEY_TIME);
        this.yValues = (ArrayList<Double>) bundle.get(KEY_HEIGHT);

        createLineChart(this.xValues, yValues);
    }

    private void createLineChart(ArrayList<Double> xValues, ArrayList<Double> yValues) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
        initializeChart();
        customizeChart();
        addData(xValues, yValues);
        View myChart = ChartFactory.getLineChartView(this, dataset, myRenderer);
        layout.addView(myChart);
    }

    private void initializeChart() {
        // Initialize renderers for customizing the series
        XYSeriesRenderer graphRenderer = new XYSeriesRenderer();
        graphRenderer.setLineWidth(3f);
        graphRenderer.setColor(Color.WHITE);
        graphRenderer.setFillPoints(true);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        myRenderer=new XYMultipleSeriesRenderer();
        myRenderer.setXLabels(0);
        myRenderer.setChartTitle("Trajectory of projectile motion ");
        myRenderer.setXTitle("Time [s]");
        myRenderer.setYTitle("Height [m]");
        myRenderer.addSeriesRenderer(graphRenderer);
        // Initialize series - ten dany graf
        graphSeries =new XYSeries("");
        dataset =new XYMultipleSeriesDataset();
        dataset.addSeries(graphSeries);
    }


    private void customizeChart() {
        myRenderer.setXLabelsColor(Color.WHITE);
        myRenderer.setYLabelsColor(0, Color.WHITE);
        myRenderer.setLabelsColor(Color.WHITE);
        myRenderer.setChartTitleTextSize(50);
        myRenderer.setAxisTitleTextSize(40);
        myRenderer.setLabelsTextSize(40);
        // setting pan ability which uses graph to move on both axis
        myRenderer.setPanEnabled(true, true);
        myRenderer.setShowGridX(true);
        myRenderer.setShowGridY(true);
        myRenderer.setShowGrid(true);
        myRenderer.setAntialiasing(true);
        // setting to in scroll to false
        myRenderer.setInScroll(false);
        myRenderer.setXLabelsAlign(Align.CENTER);
        myRenderer.setYLabelsAlign(Align.LEFT);
        myRenderer.setTextTypeface("sans-serif-black", Typeface.NORMAL);
        // setting number of values to display in x and y axis
        myRenderer.setXLabels(5);
        myRenderer.setYLabels(20);
        myRenderer.setYAxisMin(0);
        int middleValue = ((yValues.size()/2)+1);
        myRenderer.setYAxisMax((int) Math.round(yValues.get(middleValue)+0.2*middleValue));
        myRenderer.setXAxisMin(0);
        myRenderer.setXAxisMax((int) Math.round(xValues.get(xValues.size()-1)
                + 0.1* xValues.get(xValues.size()-1)));
        myRenderer.setBackgroundColor(Color.TRANSPARENT);
        // Setting margin color of the graph to transparent
        myRenderer.setMarginsColor(Color.argb(1, 0, 0, 0));
        myRenderer.setApplyBackgroundColor(true);
        // margin size for the graph in the order top, left, bottom,right
        myRenderer.setMargins(new int[]{0, 70, 50, 50});
        myRenderer.setShowLegend(false);
    }


    private void addData(ArrayList<Double> xValues, ArrayList<Double> yValues) {
        for(int i = 0; i < xValues.size(); i++){
            graphSeries.add(xValues.get(i), yValues.get(i));
        }
    }
}
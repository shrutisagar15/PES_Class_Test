package com.example.bloom.pes_sensors;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RealTimeGraph extends AppCompatActivity {
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    GraphView graph_x;
    Viewport viewport;
    float x=0,y=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph_x);
        // data
        graph_x.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph_x.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport

        series = new LineGraphSeries<DataPoint>();
        series.setTitle("X");
        series.setColor(Color.BLUE);

        graph_x.getLegendRenderer().setVisible(true);
        graph_x.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(10);
        viewport.setScrollable(true);
    }

    private void addEntry(){
        series.appendData(new DataPoint(x, y), false, 100);
        x+=0.01;
        y+=0.01;
    };

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });


                }
            }
        })};
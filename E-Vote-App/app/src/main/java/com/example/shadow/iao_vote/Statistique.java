package com.example.shadow.iao_vote;

/**
 * Created by Hamza on 2/10/2017.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistique extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statest);
        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Résultat");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        for(int i=0;i<Data.prop.size();i++)
        {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(Data.prop.get(i).vote), i); // Jan
            Log.e("statttttttttttttt",Data.prop.get(i).vote);
            valueSet1.add(v1e1);
        }


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Candidats");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        for(int i=0;i<Data.prop.size();i++)
        {
            xAxis.add(Data.prop.get(i).nom);
        }
        return xAxis;
    }
}
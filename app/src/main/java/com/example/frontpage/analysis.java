package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class analysis extends AppCompatActivity {
    ImageButton btn,btn1,btn2,btn3,btn5;
    private BarChart barChart;
    private BarChart barChart1;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        barChart = findViewById(R.id.barchart);
        barChart1 = findViewById(R.id.barchart1);

        // same design for both charts
        setupChart(barChart);
        setupChart(barChart1);

        FirebaseDatabase.getInstance()
                .getReference("date").child("d1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) return;

                        reference = FirebaseDatabase.getInstance()
                                .getReference("slot_count")
                                .child(snapshot.getValue(String.class));

                        loadSlotChart();
                        loadTotalChart();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        btn5 = findViewById(R.id.btn5);
        btn1 = findViewById(R.id.btn1);
        btn3 = findViewById(R.id.btn3);

        btn.setOnClickListener(v1 -> {
            startActivity(new Intent(analysis.this, MainActivity.class));
        });
        btn1.setOnClickListener(v1 -> {
            startActivity(new Intent(analysis.this, prasadbooking.class));
        });

        btn2.setOnClickListener(v1 -> {
            startActivity(new Intent(analysis.this, MainActivity.class));
        });
        btn3.setOnClickListener(v1 -> {
            startActivity(new Intent(analysis.this, ticketviewadded.class));
        });
        btn5.setOnClickListener(v1 -> {
            startActivity(new Intent(analysis.this, emergency.class));
        });

    }

    // ---------- SLOT CHART ----------
    private void loadSlotChart() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot s) {

                ArrayList<BarEntry> entries = new ArrayList<>();
                entries.add(new BarEntry(1f, getInt(s, "s1")));
                entries.add(new BarEntry(2f, getInt(s, "s2")));
                entries.add(new BarEntry(3f, getInt(s, "s3")));
                entries.add(new BarEntry(4f, getInt(s, "s4")));

                BarDataSet set = new BarDataSet(entries, "");
                set.setValueTextSize(14f);

                BarData data = new BarData(set);
                data.setBarWidth(0.6f);

                barChart.setData(data);
                barChart.animateY(1000);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // ---------- TOTAL PER DATE CHART ----------
    private void loadTotalChart() {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("slot_count");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<BarEntry> entries = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                int index = 0;

                for (DataSnapshot dateSnap : snapshot.getChildren()) {

                    Integer total = dateSnap.child("total").getValue(Integer.class);
                    if (total == null) total = 0;

                    entries.add(new BarEntry(index, total));
                    labels.add("Day " + (index + 1));
                    index++;
                }


                BarDataSet dataSet = new BarDataSet(entries, "");
                dataSet.setValueTextSize(12f);

                BarData data = new BarData(dataSet);
                data.setBarWidth(0.6f);

                barChart1.setData(data);

                XAxis xAxis = barChart1.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

                barChart1.animateY(1000);
                barChart1.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // ---------- COMMON CHART DESIGN ----------
    private void setupChart(BarChart chart) {

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);

        YAxis left = chart.getAxisLeft();
        left.setAxisMinimum(0f);
        left.setDrawGridLines(false);
        left.setDrawAxisLine(false);

        chart.getAxisRight().setEnabled(false);

        XAxis x = chart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(false);
    }

    private int getInt(DataSnapshot s, String key) {
        Long v = s.child(key).getValue(Long.class);
        return v == null ? 0 : v.intValue();


    }


    }


package com.example.frontpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView c1, c2, c3, c4;
    ImageButton btn, btn1, btn2, btn3, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CardViews
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);

        // Bottom buttons
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn5 = findViewById(R.id.btn5);

        // Card click
        c1.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, event.class));
        });
        c2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, darshan.class));
        });
        c3.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, bookslot.class));
        });
        c4.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, about.class));
        });

        btn.setOnClickListener(v ->
        {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        });
        btn1.setOnClickListener(v ->
        {
            startActivity(new Intent(MainActivity.this, prasadbooking.class));
        });

        btn2.setOnClickListener(v ->
        {
            startActivity(new Intent(MainActivity.this, analysis.class));
        });
        btn5.setOnClickListener(v ->
        {
            startActivity(new Intent(MainActivity.this, emergency.class));
        });
        // Ticket button click
        btn3.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, ticketviewadded.class));
            });


        }
}

package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class darshan extends AppCompatActivity {
    CardView c1,c2,c3,c4;
    ImageButton btn,btn1,btn2,btn3,btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_darshan);

        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);

        c1.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this, aarti.class));
        });
        c2.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this, darshandetail.class));
        });
        c3.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this, prasadbooking.class));
        });
        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);
        // Card click
        btn.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this,MainActivity.class));
        });

        //
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this, prasadbooking.class));
        });


        // Ticket button click
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this,ticketviewadded.class));
        });

        btn2.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this, analysis.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(darshan.this,emergency.class));
        });
    }
}
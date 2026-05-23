package com.example.frontpage;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class about extends AppCompatActivity {
TextView heading ,content;
    ImageButton btn,btn1,btn2,btn3,btn5;
    CardView btngame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        heading=findViewById(R.id.textView15);
        content=findViewById(R.id.tvAbout);

        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("about");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String headingValue = snapshot.child("heading").getValue(String.class);
                String paraValue = snapshot.child("content").getValue(String.class);
                heading.setText(headingValue);
                content.setText(paraValue);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Card click
        btn.setOnClickListener(v -> {
            startActivity(new Intent(about.this,MainActivity.class));
        });

        //
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(about.this, prasadbooking.class));
        });

        btn2.setOnClickListener(v -> {
            startActivity(new Intent(about.this, analysis.class));
        });
        // Ticket button click
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(about.this,ticketviewadded.class));
        });

        btn5.setOnClickListener(v -> {
            startActivity(new Intent(about.this,emergency.class));
        });


    }
}
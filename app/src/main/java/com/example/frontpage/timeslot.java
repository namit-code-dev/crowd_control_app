package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class timeslot extends AppCompatActivity {

    ImageView btn, btn1, btn2, btn3, btn4;
    TextView s1, s2, s3, s4;
    CardView c1, c2, c3, c4;

    int arr[] = {R.id.t1, R.id.t2, R.id.t3, R.id.t4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslot);

        // ---------- INIT ----------
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);

        s1 = findViewById(R.id.slot1);
        s2 = findViewById(R.id.slot2);
        s3 = findViewById(R.id.slot3);
        s4 = findViewById(R.id.slot4);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

        String selectedDate = getIntent().getStringExtra("data");
        if (selectedDate == null) return;

        // ---------- CARD CLICK ----------
        View.OnClickListener listener = v -> {
            TextView tv = null;

            if (v.getId() == R.id.c1) tv = findViewById(R.id.t1);
            else if (v.getId() == R.id.c2) tv = findViewById(R.id.t2);
            else if (v.getId() == R.id.c3) tv = findViewById(R.id.t3);
            else if (v.getId() == R.id.c4) tv = findViewById(R.id.t4);

            if (tv != null) {
                Intent intent = new Intent(timeslot.this, ticketshow.class);
                intent.putExtra("data", selectedDate);
                intent.putExtra("slotTime", tv.getText().toString());
                startActivity(intent);
            }
        };

        c1.setOnClickListener(listener);
        c2.setOnClickListener(listener);
        c3.setOnClickListener(listener);
        c4.setOnClickListener(listener);

        // ---------- LOAD SLOT TIMES ----------
        DatabaseReference slotRef = FirebaseDatabase.getInstance().getReference("slot");
        slotRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int i = 1; i <= 4; i++) {
                    String val = snapshot.child("s" + i).getValue(String.class);
                    if (val != null) {
                        ((TextView) findViewById(arr[i - 1])).setText(val);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

//

        // ---------- SHOW COUNTS ----------
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("slot_count").child(selectedDate);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s1.setText("ALREADY BOOKED : " + snapshot.child("s1").getValue(Integer.class));
                s2.setText("ALREADY BOOKED : " + snapshot.child("s2").getValue(Integer.class));
                s3.setText("ALREADY BOOKED : " + snapshot.child("s3").getValue(Integer.class));
                s4.setText("ALREADY BOOKED : " + snapshot.child("s4").getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // ---------- BUTTONS ----------
        btn.setOnClickListener(v ->
                startActivity(new Intent(timeslot.this, MainActivity.class)));
        btn1.setOnClickListener(v ->
                startActivity(new Intent(timeslot.this, prasadbooking.class)));
        btn2.setOnClickListener(v ->
                startActivity(new Intent(timeslot.this, analysis.class)));
        btn3.setOnClickListener(v ->
                startActivity(new Intent(timeslot.this, ticketviewadded.class)));
        btn4.setOnClickListener(v ->
                startActivity(new Intent(timeslot.this, emergency.class)));
    }
}
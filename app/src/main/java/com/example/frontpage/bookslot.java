package com.example.frontpage;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class bookslot extends AppCompatActivity {
    CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11;
    ImageButton btn,btn1,btn2,btn3,btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookslot);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        c6=findViewById(R.id.c6);
        c7=findViewById(R.id.c7);
        c8=findViewById(R.id.c8);
        c9=findViewById(R.id.c9);
        c10=findViewById(R.id.c10);
        c11=findViewById(R.id.c11);
        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);

      View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) ((CardView) v).getChildAt(0);
                String text = tv.getText().toString();
                Intent intent = new Intent(bookslot.this, timeslot.class);
                intent.putExtra("data", text);
                startActivity(intent);
            }
        };
        c1.setOnClickListener(listener);
        c2.setOnClickListener(listener);
        c3.setOnClickListener(listener);
        c4.setOnClickListener(listener);
        c5.setOnClickListener(listener);
        c6.setOnClickListener(listener);
        c7.setOnClickListener(listener);
        c8.setOnClickListener(listener);
        c9.setOnClickListener(listener);
        c10.setOnClickListener(listener);
        c11.setOnClickListener(listener);
        // TextView IDs
        int arr[] = {R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6,
                R.id.t7, R.id.t8, R.id.t9, R.id.t10, R.id.t11};


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("date");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (int i = 1; i <= 11; i++) {
                    String key = "d" + i;
                    String value = snapshot.child(key).getValue(String.class);
                    TextView tv = findViewById(arr[i - 1]);
                    tv.setText(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Card click
        btn.setOnClickListener(v -> {
            startActivity(new Intent(bookslot.this,MainActivity.class));
        });
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(bookslot.this, prasadbooking.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(bookslot.this,emergency.class));
        });

        // Ticket button click
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(bookslot.this,ticketviewadded.class));
        });

        btn2.setOnClickListener(v -> {
            startActivity(new Intent(bookslot.this, analysis.class));
        });
    }



}


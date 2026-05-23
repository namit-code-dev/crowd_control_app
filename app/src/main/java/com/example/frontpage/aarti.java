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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class aarti extends AppCompatActivity {
 TextView t25,t31,t33,t36;
    ImageButton btn,btn1,btn2,btn3,btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aarti);
        t25 = findViewById(R.id.textView25);
        t31 = findViewById(R.id.textView31);
        t33 = findViewById(R.id.textView33);
        t36 = findViewById(R.id.textView36);

        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);



        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("aarti");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String afternoonAarti = snapshot.child( "Afternoon Aarti").getValue(String.class);
                String morningAarti = snapshot.child("Morning Aarti").getValue(String.class);
                String eveningAarti = snapshot.child("Evening Aarti").getValue(String.class);
                String nightAarti = snapshot.child("Night Aarti").getValue(String.class);

                t25.setText(afternoonAarti);
                t31.setText(morningAarti);
                t33.setText(eveningAarti);
                t36.setText(nightAarti);


            }


            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Card click
        btn.setOnClickListener(v -> {
            startActivity(new Intent(aarti.this,MainActivity.class));
        });

        //
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(aarti.this, prasadbooking.class));
        });
        btn2.setOnClickListener(v -> {
            startActivity(new Intent(aarti.this, analysis.class));
        });

        // Ticket button click
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(aarti.this,ticketviewadded.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(aarti.this,emergency.class));
        });



    }
}
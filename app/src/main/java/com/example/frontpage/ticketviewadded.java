package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class ticketviewadded extends AppCompatActivity {
    ImageView btn,btn1,btn2,btn3,btn5;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketviewadded);

        container = findViewById(R.id.container);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ticketsRef = database.getReference("tickets");

        LayoutInflater inflater = LayoutInflater.from(this);

        ticketsRef.orderByKey().limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                container.removeAllViews();

                for (DataSnapshot ticketSnap : snapshot.getChildren()) {

                    View ticketView = inflater.inflate(
                            R.layout.activity_ticketshowfinal,
                            container,
                            false
                    );

                    TextView etName = ticketView.findViewById(R.id.etName);
                    TextView gender = ticketView.findViewById(R.id.gender);
                    TextView people = ticketView.findViewById(R.id.people);
                    TextView dob = ticketView.findViewById(R.id.dob);
                    TextView aadhar = ticketView.findViewById(R.id.aadharno);
                    TextView address = ticketView.findViewById(R.id.etAddress);
                    TextView etslot = ticketView.findViewById(R.id.etslot);



                    etName.setText(String.valueOf(ticketSnap.child("name").getValue()));
                    gender.setText(String.valueOf(ticketSnap.child("gender").getValue()));
                    people.setText(String.valueOf(ticketSnap.child("people").getValue()));
                    dob.setText(String.valueOf(ticketSnap.child("dob").getValue()));
                    aadhar.setText(String.valueOf(ticketSnap.child("aadhar").getValue()));
                    address.setText(String.valueOf(ticketSnap.child("address").getValue()));
                    etslot.setText(String.valueOf(ticketSnap.child("date").getValue()) + " " + String.valueOf(ticketSnap.child("time").getValue()));


                    etName.setEnabled(false);
                    gender.setEnabled(false);
                    people.setEnabled(false);
                    dob.setEnabled(false);
                    aadhar.setEnabled(false);
                    address.setEnabled(false);

                    container.addView(ticketView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ticketviewadded.this,
                        "Error: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);


        btn.setOnClickListener(v -> {
            startActivity(new Intent(ticketviewadded.this,MainActivity.class));
        });
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(ticketviewadded.this, prasadbooking.class));
        });
        btn2.setOnClickListener(v -> {
            startActivity(new Intent(ticketviewadded.this, analysis.class));
        });
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(ticketviewadded.this, ticketviewadded.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(ticketviewadded.this, emergency.class));
        });

    }
}

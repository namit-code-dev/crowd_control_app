package com.example.frontpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class event extends AppCompatActivity {

    ImageButton btn, btn1, btn2, btn3, btn5;
    CardView btnGame;
    TextView eventData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge to edge
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events);

        // ✅ Safe check for main layout (no crash)
        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // 🔹 TextView
        eventData = findViewById(R.id.eventData);

        // 🔥 Firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (!snapshot.exists()) {
                    eventData.setText("No events available");
                    return;
                }

                StringBuilder data = new StringBuilder();

                for (DataSnapshot ds : snapshot.getChildren()) {

                    String name = ds.child("name").getValue(String.class);
                    String date = ds.child("date").getValue(String.class);
                    String crowd = ds.child("crowd").getValue(String.class);

                    // ✅ Null safety
                    if (name == null) name = "N/A";
                    if (date == null) date = "N/A";
                    if (crowd == null) crowd = "N/A";

                    data.append("📅 ").append(name)
                            .append("\n🗓 Date: ").append(date)
                            .append("\n👥 Crowd: ").append(crowd)
                            .append("\n\n");
                }

                eventData.setText(data.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                eventData.setText("Failed to load data");
            }
        });

        // 🔘 Buttons init
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn5 = findViewById(R.id.btn5);
        btnGame = findViewById(R.id.c2);

        // 🔘 Click events
        if (btn != null) {
            btn.setOnClickListener(v ->
                    startActivity(new Intent(event.this, MainActivity.class)));
        }

        if (btn1 != null) {
            btn1.setOnClickListener(v ->
                    startActivity(new Intent(event.this, prasadbooking.class)));
        }

        if (btn2 != null) {
            btn2.setOnClickListener(v ->
                    startActivity(new Intent(event.this, analysis.class)));
        }

        if (btn3 != null) {
            btn3.setOnClickListener(v ->
                    startActivity(new Intent(event.this, ticketviewadded.class)));
        }
        if (btn5 != null) {
            btn5.setOnClickListener(v ->
                    startActivity(new Intent(event.this, emergency.class)));

            // 🎮 Game button
            if (btnGame != null) {
                btnGame.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://poki.com"));
                    startActivity(intent);
                });
            }
        }
    }
}
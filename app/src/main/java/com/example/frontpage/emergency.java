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

public class emergency extends AppCompatActivity {

    ImageButton btn, btn1, btn2, btn3, btn5;
    CardView c1, c2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emergency);

        // ✅ Proper Insets Handling (sirf UI ke liye)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Buttons initialize
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn5 = findViewById(R.id.btn5);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);

        c1.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, helpline.class))
        );

        c2.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, genai.class))
        );

        // ✅ Click Listeners

        // Home
        btn.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, MainActivity.class))
        );

        // Prasad Booking
        btn1.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, prasadbooking.class))
        );

        // Emergency (reload same page - optional)
        btn5.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, emergency.class))
        );

        // Ticket View
        btn3.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, ticketviewadded.class))
        );

        // Analysis (FIXED context)
        btn2.setOnClickListener(v ->
                startActivity(new Intent(emergency.this, analysis.class))
        );
    }
}
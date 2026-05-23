package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class prasadbooking extends AppCompatActivity {

    EditText etName, etMobile, etQuantity, etDate, etAddress;
    Spinner spinnerPayment;
    Button btnBook;
    ImageButton btn, btn1, btn2, btn3, btn5;

    DatabaseReference bookingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prasadbooking);


        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etQuantity = findViewById(R.id.etQuantity);
        etDate = findViewById(R.id.etDate);
        etAddress = findViewById(R.id.etAddress);
        spinnerPayment = findViewById(R.id.spinnerPayment);
        btnBook = findViewById(R.id.btnBook);

        //buttton

        // Bottom buttons
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn5 = findViewById(R.id.btn5);

        //Firebase Reference
        bookingRef = FirebaseDatabase.getInstance().getReference("prasadBookings");

        //  Payment Spinner (Cash only)
        String[] paymentOptions = {"Cash"};

        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                paymentOptions
        );
        spinnerPayment.setAdapter(paymentAdapter);

        //  Button Click
        btnBook.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String mobile = etMobile.getText().toString();
            String qty = etQuantity.getText().toString();
            String date = etDate.getText().toString();
            String address = etAddress.getText().toString();
            String payment = spinnerPayment.getSelectedItem().toString();

            //  Validation
            if (name.isEmpty() || mobile.isEmpty() || qty.isEmpty()) {
                Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
                return;
            }

            //  Unique ID
            String bookingId = bookingRef.push().getKey();

            //  HashMap Data
            HashMap<String, Object> bookingData = new HashMap<>();
            bookingData.put("name", name);
            bookingData.put("mobile", mobile);
            bookingData.put("quantity", qty);
            bookingData.put("date", date);
            bookingData.put("address", address);
            bookingData.put("payment", payment);

            //  Store in Firebase
            bookingRef.child(bookingId).setValue(bookingData)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Booking Successful 🙏", Toast.LENGTH_SHORT).show();

                        // 🧹 Clear fields
                        etName.setText("");
                        etMobile.setText("");
                        etQuantity.setText("");
                        etDate.setText("");
                        etAddress.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        });

        // Card click
        btn.setOnClickListener(v -> {
            startActivity(new Intent(prasadbooking.this,MainActivity.class));
        });

        //
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(prasadbooking.this, prasadbooking.class));
        });


        // Ticket button click
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(prasadbooking.this,ticketviewadded.class));
        });

        btn2.setOnClickListener(v -> {
            startActivity(new Intent(prasadbooking.this, analysis.class));
        });
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(prasadbooking.this,emergency.class));
        });
    }
}
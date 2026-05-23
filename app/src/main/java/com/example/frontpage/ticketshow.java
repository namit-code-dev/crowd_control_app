package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ticketshow extends AppCompatActivity {
    ImageView btn, btn1, btn2, btn3, btn5;

    EditText etName, etPeople, etDob, etAadhar, etAddress,etcontact;
    RadioButton rbMale, rbFemale;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketshow);

        String date = getIntent().getStringExtra("data");
        String time = getIntent().getStringExtra("slotTime");
        // Initialize views
        etName = findViewById(R.id.etName);
        etPeople = findViewById(R.id.etPeople);
        etDob = findViewById(R.id.etDob);
        etAadhar = findViewById(R.id.etAadhar);
        etAddress = findViewById(R.id.etAddress);
        etcontact = findViewById(R.id.etcontact);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnSubmit = findViewById(R.id.btnSubmit);
        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);

        btnSubmit.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String peopleText = etPeople.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String contactText = etcontact.getText().toString().trim();
            String aadharText = etAadhar.getText().toString().trim();

            //  1. Empty validation
            if (name.isEmpty()) {
                etName.setError("Enter full name");
                return;
            }

            if (peopleText.isEmpty()) {
                etPeople.setError("Enter number of people");
                return;
            }

            if (dob.isEmpty()) {
                etDob.setError("Enter DOB");
                return;
            }

            if (address.isEmpty()) {
                etAddress.setError("Enter address");
                return;
            }

            if (aadharText.isEmpty()) {
                etAadhar.setError("Enter Aadhar number");
                return;
            }

            //  2. Name validation (only letters)
            if (!name.matches("[a-zA-Z ]+")) {
                etName.setError("Only alphabets allowed");
                return;
            }

            //  3. Aadhar validation (12 digits)
            if (!aadharText.matches("\\d{12}")) {
                etAadhar.setError("Aadhar must be 12 digits");
                return;
            }

            int people = 0;

            try {
                people = Integer.parseInt(peopleText);
            } catch (NumberFormatException e) {

            }


            //  5. DOB validation (simple format check)
            if (!dob.matches("\\d{2}-\\d{2}-\\d{4}")) {
                etDob.setError("Format: DD-MM-YYYY");
                return;
            }

            //  6. Address length
            if (address.length() < 5) {
                etAddress.setError("Address too short");
                return;
            }

            //  7. Gender validation
            String gender = "";
            if (rbMale.isChecked()) {
                gender = "M";
            } else if (rbFemale.isChecked()) {
                gender = "F";
            } else {
                Toast.makeText(this, "Select gender", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contactText.isEmpty()) {
                etcontact.setError("Enter contact number");
                return;
            }

            if (!contactText.matches("\\d{10}")) {
                etcontact.setError("Enter valid 10 digit number");
                return;
            }
            //  Create HashMap
            HashMap<String, Object> ticketMap = new HashMap<>();
            ticketMap.put("name", name);
            ticketMap.put("people", people);
            ticketMap.put("dob", dob);
            ticketMap.put("aadhar", aadharText);
            ticketMap.put("address", address);
            ticketMap.put("contact", contactText);
            ticketMap.put("gender", gender);
            ticketMap.put("date", date);
            ticketMap.put("time", time);




            // Store in Firebase
            DatabaseReference database = FirebaseDatabase.getInstance().getReference("tickets");
            String id = database.push().getKey();
            if (id != null) {
                database.child(id).setValue(ticketMap)
                        .addOnSuccessListener(aVoid -> {
                            // Success message
                            new TicketCounter().countTickets();
                            Toast.makeText(ticketshow.this, "Ticket saved successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ticketshow.this, ticketviewadded.class);
                            startActivity(intent);

                        })
                        .addOnFailureListener(e -> {
                            // Failure message
                            Toast.makeText(ticketshow.this, "Failed to save ticket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Card click
        btn.setOnClickListener(v1 -> {
            startActivity(new Intent(ticketshow.this,MainActivity.class));
        });
        btn1.setOnClickListener(v1 -> {
            startActivity(new Intent(ticketshow.this, prasadbooking.class));
        });

        btn2.setOnClickListener(v1 -> {
            startActivity(new Intent(ticketshow.this, analysis.class));
        });
        // Ticket button click
        btn3.setOnClickListener(v2 -> {
            startActivity(new Intent(ticketshow.this, ticketviewadded.class));
        });
        btn5.setOnClickListener(v2 -> {
            startActivity(new Intent(ticketshow.this, emergency.class));
        });
    }
}

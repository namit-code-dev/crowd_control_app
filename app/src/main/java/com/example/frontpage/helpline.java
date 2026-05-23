package com.example.frontpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class helpline extends AppCompatActivity {

    // Views
    private EditText etName, etContact, etEmail, etDescription;
    private Spinner spinnerHelpType;
    private Button btnSubmit;
    private ImageButton btn, btn1, btn2, btn3, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        // Initialize views
        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etEmail = findViewById(R.id.etEmail);
        etDescription = findViewById(R.id.etDescription);
        spinnerHelpType = findViewById(R.id.spinnerHelpType);
        btnSubmit = findViewById(R.id.btnSubmit);

        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn5 = findViewById(R.id.btn5);

        // Submit button click
        btnSubmit.setOnClickListener(v -> submitForm());

        // Navigation buttons
        btn.setOnClickListener(v -> startActivity(new Intent(helpline.this, MainActivity.class)));
        btn1.setOnClickListener(v -> startActivity(new Intent(helpline.this, prasadbooking.class)));
        btn2.setOnClickListener(v -> startActivity(new Intent(helpline.this, analysis.class)));
        btn3.setOnClickListener(v -> startActivity(new Intent(helpline.this, ticketviewadded.class)));
        btn5.setOnClickListener(v -> {
            startActivity(new Intent(helpline.this, emergency.class));
        });
    }

    private void submitForm() {
        String name = etName.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String helpType = spinnerHelpType.getSelectedItem().toString();

        // Validation
        if (name.isEmpty()) {
            etName.setError("Enter your name");
            return;
        }
        if (!contact.matches("\\d{10}")) {
            etContact.setError("Enter valid 10-digit number");
            return;
        }
        if (!email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter valid email");
            return;
        }
        if (description.isEmpty()) {
            etDescription.setError("Provide a description");
            return;
        }

        // Prepare data to save in Firebase
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("contact", contact);
        data.put("email", email);
        data.put("helpType", helpType);
        data.put("description", description);

        // Save to Firebase
        FirebaseDatabase.getInstance().getReference("help_requests")
                .push()
                .setValue(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(helpline.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                    clearForm();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(helpline.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void clearForm() {
        etName.setText("");
        etContact.setText("");
        etEmail.setText("");
        etDescription.setText("");
        spinnerHelpType.setSelection(0);
    }
}
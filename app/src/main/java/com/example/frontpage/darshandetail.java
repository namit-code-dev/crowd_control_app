package com.example.frontpage;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class darshandetail extends AppCompatActivity {
    TextView textView40,textView41,textView42,textView43,textView44,textView45;
    ImageButton btn,btn1,btn2,btn3,btn5;
    ImageView imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_darshandetail);

        textView40 = findViewById(R.id.textView40);
        textView41 = findViewById(R.id.textView41);
        textView42 = findViewById(R.id.textView42);
        textView43 = findViewById(R.id.textView43);
        textView44 = findViewById(R.id.textView44);
        textView45 = findViewById(R.id.textView45);
        imageView6=findViewById(R.id.imageView6);

        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn5=findViewById(R.id.btn5);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("mukha_darshan");
        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("charn_sparash");
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("vip");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String darshantype = snapshot.child( "type").getValue(String.class);
                String gateno = snapshot.child("gateno").getValue(String.class);


                textView41.setText(darshantype);;
                textView40.setText(gateno);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String darshantype = snapshot.child( "type").getValue(String.class);
                String gateno = snapshot.child("gateno").getValue(String.class);


                textView43.setText(darshantype);;
                textView42.setText(gateno);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String darshantype = snapshot.child( "type").getValue(String.class);
                String gateno = snapshot.child("gateno").getValue(String.class);


                textView45.setText(darshantype);;
                textView44.setText(gateno);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        imageView6.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://m.youtube.com/user/LalbaugRaja?fbclid=PAb21jcAQtGr5leHRuA2FlbQIxMQBzcnRjBmFwcF9pZA81NjcwNjczNDMzNTI0MjcAAafYeYNIHv6WvhKNQVPhgQoxTxRjTGk6aPHKiKUEX7pL8Zj38tAKS97r_iNiWA_aem_8L3gS1wMA7PAZsWpLzQmLw"));
            startActivity(intent);
        });

        // Card click
        btn.setOnClickListener(v -> {
            startActivity(new Intent(darshandetail.this,MainActivity.class));
        });

        //
        btn1.setOnClickListener(v -> {
            startActivity(new Intent(darshandetail.this, prasadbooking.class));
        });


        // Ticket button click
        btn3.setOnClickListener(v -> {
            startActivity(new Intent(darshandetail.this,ticketviewadded.class));
        });

        btn2.setOnClickListener(v -> {
            startActivity(new Intent(darshandetail.this, analysis.class));
        });
       btn5.setOnClickListener(v -> {
            startActivity(new Intent(darshandetail.this,emergency.class));
        });

    }
}
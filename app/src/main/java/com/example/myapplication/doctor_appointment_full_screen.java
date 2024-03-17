package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class doctor_appointment_full_screen extends AppCompatActivity {

    TextView doctor_name;
    TextView doctor_specialist;
    ImageView doc_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_appointment_full_screen);

        doctor_name = findViewById(R.id.doc_namee);
        doctor_specialist = findViewById(R.id.doc_specialistt);
        doc_profile = findViewById(R.id.doc_dp);
        doctor_name.setText(getIntent().getStringExtra("username"));
        doctor_specialist.setText(getIntent().getStringExtra("specialist"));
        Glide.with(doctor_appointment_full_screen.this).load(getIntent().getStringExtra("Image")).into(doc_profile);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
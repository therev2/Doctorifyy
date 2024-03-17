package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class doc_landing_page extends AppCompatActivity {

    RecyclerView recyclerView_doc;
    DatabaseReference database;
    Myadapter_Doc myadapter_doc;
    ArrayList<HelperClass3> list_doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doc_landing_page);



        recyclerView_doc = findViewById(R.id.recyclerView_doc);
        database = FirebaseDatabase.getInstance().getReference("appointment");
        recyclerView_doc.setHasFixedSize(true);
        recyclerView_doc.setLayoutManager(new LinearLayoutManager(this));

        list_doc = new ArrayList<>();
        myadapter_doc = new Myadapter_Doc(this,list_doc);
        recyclerView_doc.setAdapter(myadapter_doc);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HelperClass3 helper = dataSnapshot.getValue(HelperClass3.class);
                    list_doc.add(helper);

                }
                myadapter_doc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
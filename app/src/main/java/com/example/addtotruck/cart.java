package com.example.addtotruck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class cart extends AppCompatActivity {

    RecyclerView recyclerView;
    SubAdapter subAdapter;
    private DatabaseReference database;
    TextView total;

    Button checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        recyclerView = (RecyclerView) findViewById(R.id.orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseRecyclerOptions<Orders> options =

                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Orders"), Orders.class)
                        .build();

        database = FirebaseDatabase.getInstance().getReference("Accounts").child(uid).child("Orders");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int t =0;

                for (DataSnapshot ds : snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) ds.getValue();
                    Object sub = map.get("subtotal");
                    int subt =Integer.parseInt(String.valueOf(sub));
                    t += subt;
                    total.setText(String.valueOf(t));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        subAdapter = new SubAdapter(options);
        recyclerView.setAdapter(subAdapter);
        recyclerView.setItemAnimator(null);


        checkout = (Button) findViewById(R.id.checkout);
        total = findViewById(R.id.Total);



        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Address.class));
            }
        });






    }

    @Override
    protected void onStart() {
        super.onStart();
        subAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subAdapter.stopListening();
    }



}
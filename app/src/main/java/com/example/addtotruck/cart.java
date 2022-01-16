package com.example.addtotruck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class cart extends AppCompatActivity {

    RecyclerView recyclerView;
    SubAdapter subAdapter;

    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);


        recyclerView = (RecyclerView) findViewById(R.id.orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Orders"), Orders.class)
                        .build();

        subAdapter = new SubAdapter(options);
        recyclerView.setAdapter(subAdapter);
        recyclerView.setItemAnimator(null);


        checkout = (Button) findViewById(R.id.checkout);
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
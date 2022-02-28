package com.example.addtotruck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Address extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
    }
    public void Maintenance(View view) {
        Intent intent= new Intent(Address.this, Maintence.class);
        startActivity(intent);
    }
}
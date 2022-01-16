package com.example.addtotruck;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.addtotruck.Utility.Network;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Network network = new Network();

    EditText username,password;

    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Auth=FirebaseAuth.getInstance();
    }

    public void goToCreate(View view) {
        Intent intent= new Intent(MainActivity.this, create.class);
        startActivity(intent);
    }

    public void goToLogin(View view) {
        String UName,Pword;

        UName = username.getText().toString().trim();
        Pword = password.getText().toString().trim();

        if(UName.isEmpty()) {
            username.setError("Email is Required");
            username.requestFocus();
            return;
        }

        if(Pword.isEmpty()) {
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }

        Auth.signInWithEmailAndPassword(UName, Pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent= new Intent(getApplicationContext(), shop.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Invalid Password/Username", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goToForgot(View view) {
        Intent intent= new Intent(MainActivity.this, Forgot.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(network, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(network);
        super.onStop();
    }
}
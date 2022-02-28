package com.example.addtotruck;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class shop extends AppCompatActivity {


    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);


        recyclerView = findViewById(R.id.items);
        int numberOfColumns = 1;
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, numberOfColumns );
        recyclerView.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items"), Items.class)
                        .build();




        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        recyclerView.setItemAnimator(null);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.AddItem);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),cart.class));

            }
        });
        floatingActionButton = (FloatingActionButton) findViewById(R.id.Ordered);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), History.class));
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str)
    {

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()

                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Items").orderByChild("name").startAt(str).endAt(str +"~"), Items.class)
                        .build();

        mainAdapter = new com.example.addtotruck.MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }






}

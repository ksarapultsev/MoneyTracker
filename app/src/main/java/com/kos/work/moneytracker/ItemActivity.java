package com.kos.work.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ItemActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ItemsAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        adapter = new ItemsAdapter();

        recycler = findViewById(R.id.list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        //mRecyclerView.addItemDecoration();

    }




}

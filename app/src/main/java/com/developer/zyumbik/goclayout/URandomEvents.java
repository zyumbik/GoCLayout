package com.developer.zyumbik.goclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.developer.zyumbik.goclayout.userrandom.URandomAdapter;
import com.developer.zyumbik.goclayout.userrandom.URandomEvent;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Random;

public class URandomEvents extends AppCompatActivity {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private ArrayList<URandomEvent> events;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_events);

//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Firebase
		Firebase rootRef = new Firebase("https://the-game-of-chance.firebaseio.com/");

		// Temporarily filling arrays with data
		events = new ArrayList<>();

		// Recycler view setup
		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new URandomAdapter(events);
		recyclerView.setAdapter(adapter);

	}

}

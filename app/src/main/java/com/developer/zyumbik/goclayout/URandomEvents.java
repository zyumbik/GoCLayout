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

		// Firebase
		Firebase rootRef = new Firebase("https://the-game-of-chance.firebaseio.com/");

		// Temporarily filling arrays with data
		events = new ArrayList<>();
		fillList();

		// Recycler view setup
		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new URandomAdapter(events);
		recyclerView.setAdapter(adapter);

	}

	private void fillList() {
		Random rnd = new Random();
		for (int i = 0; i < rnd.nextInt(20); i++) {
			events.add(new URandomEvent(generateStatement(3), generateStatement(10),
					generateStatement(20), rnd.nextInt(100), rnd.nextInt(100)));
		}
	}

	private String generateStatement(int words) {
		StringBuilder sb = new StringBuilder();
		Random rnd = new Random();
		String[] keywords = {"random", "probability", "event", "user", "is", "where",
				"new", "list", "good", "answer", "game", "of", "chance"};
		for (int i = 0; i < words; i++) {
			sb.append(keywords[rnd.nextInt(13)]);
			sb.append(" ");
		}
		return sb.toString();
	}

}

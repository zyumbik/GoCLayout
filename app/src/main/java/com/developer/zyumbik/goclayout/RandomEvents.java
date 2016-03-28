package com.developer.zyumbik.goclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.developer.zyumbik.goclayout.userrandom.URandomAdapter;
import com.developer.zyumbik.goclayout.userrandom.URandomEventBrief;
import com.developer.zyumbik.goclayout.userrandom.URandomEventFull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomEvents extends AppCompatActivity {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private ArrayList<URandomEventBrief> eventBriefs;
	private ArrayList<URandomEventFull> eventFulls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_events);

		// Temporarily filling arrays with data
		eventBriefs = new ArrayList<>();
		eventFulls = new ArrayList<>();
		fillList();

		// Recycler view setup
		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new URandomAdapter(eventBriefs);
		recyclerView.setAdapter(adapter);
	}

	private void fillList() {
		Random rnd = new Random();
		for (int i = 0; i < rnd.nextInt(20); i++) {
			eventBriefs.add(new URandomEventBrief(generateStatement(3), generateStatement(10),
					rnd.nextInt(100), rnd.nextInt(100)));
			eventFulls.add(new URandomEventFull(generateStatement(15)));
		}
	}

	private String generateStatement(int words) {
		StringBuilder sb = new StringBuilder();
		Random rnd = new Random();
		String[] keywords = {"random", "probability", "event", "user", "is", "where",
				"new", "list", "good", "answer", "game", "of", "chance"};
		for (int i = 0; i < words; i++) {
			sb.append(keywords[rnd.nextInt(13)] + " ");
		}
		return sb.toString();
	}

}

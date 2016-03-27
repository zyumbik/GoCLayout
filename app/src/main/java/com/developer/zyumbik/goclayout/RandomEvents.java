package com.developer.zyumbik.goclayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.developer.zyumbik.goclayout.userrandom.URandomAdapter;
import com.developer.zyumbik.goclayout.userrandom.URandomEventBrief;
import com.developer.zyumbik.goclayout.userrandom.URandomEventFull;

import java.util.ArrayList;

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

		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new URandomAdapter(eventBriefs);
	}
}

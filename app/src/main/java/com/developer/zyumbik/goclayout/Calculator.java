package com.developer.zyumbik.goclayout;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.zyumbik.goclayout.calculator.CalculatorAdapter;
import com.developer.zyumbik.goclayout.calculator.Probability;

import java.util.ArrayList;

public class Calculator extends AppCompatActivity {

	FloatingActionButton floatingActionButton;
	EditText etOutcomes, etEvents;
	private RecyclerView.Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		// Toolbar setting up
		Toolbar toolbar = (Toolbar) findViewById(R.id.calculator_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Initializing all views
		floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddProbability);
		etEvents = (EditText) findViewById(R.id.inputEvents);
		etOutcomes = (EditText) findViewById(R.id.inputOutcomes);

		// Scrolling list of probabilities
		final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.calcRecyclerProbabilities);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new CalculatorAdapter();
		recyclerView.setAdapter(adapter);

		// Adding new elements to the list
		floatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					int outcomes = Integer.parseInt(etOutcomes.getText().toString());
					int events = Integer.parseInt(etEvents.getText().toString());
					if (events > outcomes) {
						Toast.makeText(Calculator.this, "You can't have more events than outcomes", Toast.LENGTH_LONG).show();
					} else {
						((CalculatorAdapter)adapter).addProbability(outcomes, events);
					}
				} catch (NumberFormatException e) {
					Toast.makeText(Calculator.this, "Can't parse the number", Toast.LENGTH_LONG).show();
				}
			}
		});
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

}

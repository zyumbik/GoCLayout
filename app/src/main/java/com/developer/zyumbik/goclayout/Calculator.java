package com.developer.zyumbik.goclayout;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.zyumbik.goclayout.calculator.CalculatorAdapterProbabilities;
import com.developer.zyumbik.goclayout.calculator.RandomNumberGenerator;

public class Calculator extends AppCompatActivity {

	private FloatingActionButton floatingActionButton;
	private EditText etOutcomes, etEvents;
	private RecyclerView.Adapter adapter;
	private RecyclerView recyclerViewProbabilities;
	private TextView listHeader;

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

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
		listHeader = (TextView) findViewById(R.id.calculator_list_header);
		listHeader.setVisibility(View.INVISIBLE);

		// Scrolling list of probabilities
		recyclerViewProbabilities = (RecyclerView) findViewById(R.id.calcRecyclerProbabilities);
		recyclerViewProbabilities.setHasFixedSize(true);
		recyclerViewProbabilities.setLayoutManager(new LinearLayoutManager(this));
		adapter = new CalculatorAdapterProbabilities();
		recyclerViewProbabilities.setAdapter(adapter);

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
						((CalculatorAdapterProbabilities)adapter).addProbability(outcomes, events);
						listHeader.setVisibility(View.VISIBLE);
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
		getMenuInflater().inflate(R.menu.menu_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_random_generator:
				RandomNumberGenerator generator = new RandomNumberGenerator();
				generator.show(getSupportFragmentManager(), "random_generator");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

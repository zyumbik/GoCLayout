package com.developer.zyumbik.goclayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.developer.zyumbik.goclayout.userrandom.URandomAdapter;
import com.developer.zyumbik.goclayout.userrandom.URandomEvent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class URandomEvents extends AppCompatActivity {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<URandomEvent> events;
	private ProgressDialog progressDialog;

	@Override
	public void onBackPressed() {
		finishActivityIn(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_events);

		final Context context = this;
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgress(0);
		progressDialog.setTitle("Loading list");
		progressDialog.show();

//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Recycler View initialization with non-empty array list
		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		setRecyclerView();

		// Getting data for list from Firebase
		Firebase ref = new Firebase("https://the-game-of-chance.firebaseio.com/userRandomEvents");
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				final long children = dataSnapshot.getChildrenCount();
				for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
					URandomEvent event = postSnapshot.getValue(URandomEvent.class);
					events.add(event);
//					System.out.println("Data received: " + event.getHeader());
					progressDialog.setProgress((int)(events.size() / children));
				}
				setRecyclerView();
				progressDialog.dismiss();
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				progressDialog.setMessage(getString(R.string.firebase_fetch_data_error_message)
						+ firebaseError.getMessage());
				finishActivityIn(3500);
			}
		});

	}

	private void finishActivityIn(final long milliseconds) {
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(milliseconds * 1000);
					progressDialog.dismiss();
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	private void setRecyclerView() {
		if (events != null) {
			adapter = new URandomAdapter(events);
			recyclerView.setAdapter(adapter);
		} else {
			events = new ArrayList<>();
			events.add(new URandomEvent("", "", "", 0, 1));
			// Recursive method invocation
			setRecyclerView();
			events.remove(0);
		}
	}

}

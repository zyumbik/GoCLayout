package com.developer.zyumbik.goclayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.developer.zyumbik.goclayout.system.AppClass;
import com.developer.zyumbik.goclayout.userrandom.FragmentRandomEventDetails;
import com.developer.zyumbik.goclayout.userrandom.FragmentRandomEventPoll;
import com.developer.zyumbik.goclayout.userrandom.URandomEventListItem;
import com.developer.zyumbik.goclayout.userrandom.URandomListAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class URandomEvents extends AppCompatActivity {

	private RecyclerView recyclerView;
	private URandomListAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<URandomEventListItem> events;
	private boolean[] answeredEvents;
	private ProgressDialog progressDialog;
	private FragmentRandomEventDetails details;
	private FragmentRandomEventPoll poll;
//	private CoordinatorLayout coordinatorLayout;

	@Override
	public void onBackPressed() {
		finishActivityIn(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_events);

//		coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

		// TODO: Action bar and back button
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_random_events);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final Context context = this;
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Loading data");
		progressDialog.setCancelable(false);
		progressDialog.show();

		// Recycler View initialization with non-empty array list
		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		setRecyclerView();

		fetchFromFirebase();

	}

	private void fetchFromFirebase() {
		// Get list of events from Firebase
		Firebase ref = new Firebase(AppClass.PROBABILITIES_LIST_URL);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
//				final long children = dataSnapshot.getChildrenCount();
				for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
					URandomEventListItem event = postSnapshot.getValue(URandomEventListItem.class);
					events.add(event);
					// TODO: set progress to be shown in the dialog
//					progressDialog.setMessage((int)(events.size() / children) + "%");
//					progressDialog.setProgress((int)(events.size() / children));
				}
//				progressDialog.setMessage("100%");
				setRecyclerView();
				answeredEvents = new boolean[events.size()];
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
			adapter = new URandomListAdapter(events);
			recyclerView.setAdapter(adapter);

			adapter.setListener(new URandomListAdapter.ItemClickListener() {
				@Override
				public void onItemClick(URandomEventListItem item, int id) {
					showBottomSheet(id);
				}
			});

		} else {
			events = new ArrayList<>();
			events.add(new URandomEventListItem("", "", 0, 1));
			// Recursive method call
			setRecyclerView();
			events.remove(0);
		}
	}

	private void showBottomSheet(final int id) {
		if (details != null) {
			details.dismiss();
		}
		if (poll != null) {
			poll.dismiss();
		}
		if (answeredEvents[id]) {
			details = FragmentRandomEventDetails.newInstance(events.get(id));
			details.show(getSupportFragmentManager(), "dialog_event_details");
		} else {
			poll = FragmentRandomEventPoll.newInstance(events.get(id));
			poll.setListener(new FragmentRandomEventPoll.PollButtonClickListener() {
				@Override
				public void onPollButtonClickSkip() {
					answeredEvents[id] = true;
					showBottomSheet(id);
				}

				@Override
				public void onPollButtonClickPositive() {
					answeredEvents[id] = true;
					events.get(id).addPplYes();
					adapter.replaceListItem(events.get(id), id);
					showBottomSheet(id);
				}

				@Override
				public void onPollButtonClickNegative() {
					answeredEvents[id] = true;
					events.get(id).addPplNo();
					showBottomSheet(id);
				}
			});
			poll.show(getSupportFragmentManager(), "dialog_event_poll");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}
}

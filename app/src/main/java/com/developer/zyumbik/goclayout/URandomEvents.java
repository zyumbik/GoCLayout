package com.developer.zyumbik.goclayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.developer.zyumbik.goclayout.auth.FragmentAuthentication;
import com.developer.zyumbik.goclayout.system.AppClass;
import com.developer.zyumbik.goclayout.userrandom.FragmentRandomEventDetails;
import com.developer.zyumbik.goclayout.userrandom.FragmentRandomEventPoll;
import com.developer.zyumbik.goclayout.userrandom.URandomEventListItem;
import com.developer.zyumbik.goclayout.userrandom.URandomListAdapter;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class URandomEvents extends AppCompatActivity {

	private RecyclerView recyclerView;
	private URandomListAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<URandomEventListItem> events;
	private List<Boolean> answeredEvents;
	private ProgressDialog progressDialog;
	private FragmentRandomEventDetails details;
	private FragmentRandomEventPoll poll;
	private FragmentAuthentication fragmentAuthentication;
	private Firebase ref = new Firebase(AppClass.FIREBASE_ADDRESS);
	private Firebase refList = new Firebase(AppClass.PROBABILITIES_LIST_URL);
	private Firebase.AuthResultHandler authResultHandler;

	@Override
	public void onBackPressed() {
		finishActivityIn(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_random_events);
		ref.unauth();

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

		setAuthResultHandler();
		fetchEventsList();
	}

	private void showAuthDialog() {
		fragmentAuthentication = FragmentAuthentication.newInstance();
		fragmentAuthentication.setListener(new FragmentAuthentication.OnAuthFragmentInteractionListener() {
			@Override
			public void onSubmitClicked(String email, String password, boolean registered) {
				if (registered) {
					userLogin(email, password);
				} else {
					userSignUp(email, password);
				}
			}

			@Override
			public void onForgotClicked(String email) {
				// Reassure in password reset
			}

			@Override
			public void onCancelled() {
				// Ask whether user wants to log in anonymously
				ref.authAnonymously(authResultHandler);
			}
		});
		fragmentAuthentication.show(this.getSupportFragmentManager(), "dialog_authentication");
	}

	private void setAuthResultHandler() {
		authResultHandler = new Firebase.AuthResultHandler() {
			@Override
			public void onAuthenticated(AuthData authData) {
				// Authenticated successfully with payload authData
				if(!ref.getAuth().getProviderData().containsKey("list_answered")){
					putAnswersList();
					Log.d("Authentication", "wtf!");
				} else {
					fetchAnswersList();
					Log.d("Authentication", "success!");
				}
			}
			@Override
			public void onAuthenticationError(FirebaseError firebaseError) {
				// Authenticated failed with error firebaseError
				Log.e("Firebase", "authentication error: " + firebaseError);
			}
		};
	}

	private void userLogin(final String email, final String password) {
		if (password.length() == 0) {
			// Ask if user wants to use default password
		} else {
			ref.authWithPassword(email, password, authResultHandler);
		}
	}

	private void userSignUp(final String email, final String password) {
		if (password.length() == 0) {
			// Ask if user wants to use default password
		} else {
			ref.createUser(email, password, new Firebase.ResultHandler() {
				@Override
				public void onSuccess() {
					userLogin(email, password);
					putAnswersList();
					Log.d("SignUp", "success!");
				}

				@Override
				public void onError(FirebaseError firebaseError) {
					Log.e("Firebase", "create user error: " + firebaseError);
				}
			});
		}
	}

	private void fetchEventsList() {
		// Get list of events from Firebase
		refList.keepSynced(true);
		refList.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
//				final long children = dataSnapshot.getChildrenCount();
				for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
					URandomEventListItem event = postSnapshot.getValue(URandomEventListItem.class);
					events.add(event);
					// TODO: set progress to be shown in the dialog
				}
				fillAnswersListIfNull();
				setRecyclerView();
				progressDialog.dismiss();
				if (ref.getAuth() == null) {
					showAuthDialog();
				} else {
					fetchAnswersList();
				}
			}

			@Override
			public void onCancelled(FirebaseError firebaseError) {
				progressDialog.setMessage(getString(R.string.firebase_fetch_data_error_message)
						+ firebaseError.getMessage());
				finishActivityIn(3500);
			}

		});
	}

	private void fetchAnswersList() {
		answeredEvents = new ArrayList<>();
		ref.child("users").child(ref.getAuth().getUid()).child("list_answered").addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
					answeredEvents.add(postSnapshot.getValue(Boolean.class));
				}
			}
			@Override
			public void onCancelled(FirebaseError firebaseError) {
				Log.e("Firebase", "Can't fetch answers list: " + firebaseError.getMessage());
			}
		});
	}

	private void fillAnswersListIfNull() {
		if (answeredEvents == null) {
			answeredEvents = new ArrayList<>();
			for (int i = 0; i < events.size(); i++) {
				answeredEvents.add(false);
			}
		}
	}

	private void putAnswersList() {
		Map<String, Object> map = new HashMap<>();
		fillAnswersListIfNull();
		map.put("list_answered", answeredEvents);
		ref.child("users").child(ref.getAuth().getUid()).setValue(map);
		Log.d("List first element", ref.child("users").child(ref.getAuth().getUid()).toString());
	}

	private void finishActivityIn(final long milliseconds) {
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(milliseconds);
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

	private void incrementValue(String toIncrease, final int id) {
		refList.child(String.valueOf(id)).child(toIncrease).runTransaction(new Transaction.Handler() {
			@Override
			public Transaction.Result doTransaction(MutableData currentData) {
				if(currentData.getValue() == null) {
					currentData.setValue(1);
				} else {
					currentData.setValue((Long) currentData.getValue() + 1);
				}
				return Transaction.success(currentData);
			}

			@Override
			public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {

			}
		});
	}

	private void showBottomSheet(final int id) {
		if (details != null) {
			details.dismiss();
		}
		if (poll != null) {
			poll.dismiss();
		}
		if (answeredEvents.get(id)) {
			details = FragmentRandomEventDetails.newInstance(events.get(id));
			details.show(getSupportFragmentManager(), "dialog_event_details");
		} else {
			poll = FragmentRandomEventPoll.newInstance(events.get(id));
			poll.setListener(new FragmentRandomEventPoll.PollButtonClickListener() {

				@Override
				public void onAnyButtonClick() {
					answeredEvents.set(id, true);
					showBottomSheet(id);
					ref.child("users").child(ref.getAuth().getUid()).child("list_answered").child(String.valueOf(id)).setValue(true);
				}

				@Override
				public void onPollButtonClickSkip() {

				}

				@Override
				public void onPollButtonClickPositive() {
					incrementValue("pplYes", id);
					events.get(id).addPplYes();
					adapter.replaceListItem(events.get(id), id);
				}

				@Override
				public void onPollButtonClickNegative() {
					incrementValue("pplNo", id);
					events.get(id).addPplNo();
					adapter.replaceListItem(events.get(id), id);
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

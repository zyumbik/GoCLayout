package com.developer.zyumbik.goclayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

	private Context context;
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
	private boolean registered = false;

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

		context = this;
		showDialogLoading();

		// Recycler View initialization with non-empty array list
		recyclerView = (RecyclerView) findViewById(R.id.uRnd_list);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(layoutManager);
		setRecyclerView();

		registered = (ref.getAuth() != null);
		setAuthResultHandler();
		fetchEventsList();
	}

	private void onSuccessfulAuth() {
		if (registered) {
			Toast.makeText(URandomEvents.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
		} else {
			showDialogError("Failed to login. Try again");
			finishActivityIn(2000);
		}
	}

	private void showAuthDialog() {
		fragmentAuthentication = FragmentAuthentication.newInstance();
		fragmentAuthentication.setListener(new FragmentAuthentication.OnAuthFragmentInteractionListener() {
			@Override
			public void onSubmitClicked(String email, String password, boolean registered) {
				URandomEvents.this.registered = registered;
				if (registered) {
					userLogin(email, password);
				} else {
					userSignUp(email, password);
				}
			}

			@Override
			public void onForgotClicked(String email) {
				showDialogConfirmation(2);
			}

			@Override
			public void onCancelled() {
				// Ask whether user wants to log in anonymously
				showDialogConfirmation(1);
			}
		});
		fragmentAuthentication.show(this.getSupportFragmentManager(), "dialog_authentication");
	}

	private void dismissAuthDialog() {
		if (fragmentAuthentication != null) {
			fragmentAuthentication.dismiss();
		}
	}

	private void showDialogConfirmation(final String email, final boolean login) {
		// Confirm whether user wants to sign in using default password
		final AlertDialog dialog;
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.dialog_alert_default_password_title).setMessage(R.string.dialog_alert_default_password_message);
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (login) {
					userLogin(email, "password");
				} else {
					userSignUp(email, "password");
				}
				dialog.dismiss();
				showDialogLoading();
			}
		});
		dialog = builder.create();
		dialog.show();
	}

	private void showDialogConfirmation(final int type) {
		// Multi-purpose dialog
		final AlertDialog dialog;
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		switch (type) {
			case 1:
				// Confirm whether user wants to auth anonymously
				builder.setTitle(R.string.dialog_anonymous_auth_title).setMessage(R.string.dialog_anonymous_auth_message);
				break;
			case 2:
				// Password reset confirmation
				builder.setTitle(R.string.dialog_reset_password_title).setMessage(R.string.dialog_reset_password_message);
				break;
			case 3:
				// User log out confirmation
				break;
			default:
				break;
		}
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (type) {
					case 1:
						// Authenticate anonymously
						showDialogLoading();
						ref.authAnonymously(authResultHandler);
						break;
					case 2:
						ref.resetPassword(fragmentAuthentication.getEmail(), new Firebase.ResultHandler() {
							@Override
							public void onSuccess() {
								Toast.makeText(URandomEvents.this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show();
							}
							@Override
							public void onError(FirebaseError firebaseError) {
								showDialogError("Can not reset password: " + firebaseError.getMessage());
							}
						});
						break;
					case 3:
						break;
					default:
						break;
				}
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (type) {
					case 1:
						showAuthDialog();
						break;
					case 2:
						break;
					case 3:
						break;
					default:
						break;
				}
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.show();
	}

	private void showDialogLoading() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Loading data");
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	private void dismissDialogLoading() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	private void showDialogError(String message) {
		dismissDialogLoading();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Error").setMessage(message);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void setAuthResultHandler() {
		authResultHandler = new Firebase.AuthResultHandler() {
			@Override
			public void onAuthenticated(AuthData authData) {
				// Authenticated successfully with payload authData
				if (registered) {
					fetchAnswersList();
				} else {
					putAnswersList();
					registered = (authData != null);
					onSuccessfulAuth();
				}
				dismissDialogLoading();
				dismissAuthDialog();
			}
			@Override
			public void onAuthenticationError(FirebaseError firebaseError) {
				showDialogError("Can not authenticate: " + firebaseError.getMessage());
			}
		};
	}

	private void userLogin(final String email, final String password) {
		if (password.length() == 0) {
			// Ask if user wants to use default password
			showDialogConfirmation(email, true);
		} else {
			ref.authWithPassword(email, password, authResultHandler);
		}
	}

	private void userSignUp(final String email, final String password) {
		if (password.length() == 0) {
			// Ask if user wants to use default password
			showDialogConfirmation(email, false);
		} else {
			ref.createUser(email, password, new Firebase.ResultHandler() {
				@Override
				public void onSuccess() {
					userLogin(email, password);
				}
				@Override
				public void onError(FirebaseError firebaseError) {
					showDialogError("Can not create an account: " + firebaseError.getMessage());
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
				dismissDialogLoading();
				if (registered) {
					fetchAnswersList();
				} else {
					showAuthDialog();
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
		if (registered) {
			answeredEvents = new ArrayList<>();
			ref.child("users").child(ref.getAuth().getUid()).child("list_answered").
					addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
								answeredEvents.add(postSnapshot.getValue(Boolean.class));
							}
							onSuccessfulAuth();
						}

						@Override
						public void onCancelled(FirebaseError firebaseError) {
							showDialogError("Can not get list of events: " + firebaseError.getMessage());
						}
					});
		}
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
	}

	private void finishActivityIn(final long milliseconds) {
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(milliseconds);
					dismissDialogLoading();
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
					if (registered) {
						showBottomSheet(id);
					} else {
						Toast.makeText(URandomEvents.this, "Not yet signed in", Toast.LENGTH_SHORT).show();
					}
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
					ref.child("users").child(ref.getAuth().getUid())
							.child("list_answered").child(String.valueOf(id)).setValue(true);
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
		getMenuInflater().inflate(R.menu.menu_urnd_events, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_add_event:
				return true;
			case R.id.menu_change_password:
				return true;
			case R.id.menu_log_out:
				showDialogConfirmation(3);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

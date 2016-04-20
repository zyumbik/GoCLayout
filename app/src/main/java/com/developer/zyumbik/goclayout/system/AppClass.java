package com.developer.zyumbik.goclayout.system;

import android.app.Application;

import com.firebase.client.Firebase;

/** Created by glebsabirzanov on 28/03/16. */
public class AppClass extends Application {

	// Database key names
	public static final String PROBABILITIES_LIST_URL = "https://the-game-of-chance.firebaseio.com/userRandomEvents";
//	public static final String DB_HEADER = "header", DB_DESCRIPTION = "description",
//			DB_PPL_YES = "pplYes", DB_PPL_NO = "pplNo";

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
	}

}

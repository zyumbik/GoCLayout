package com.developer.zyumbik.goclayout.system;

import android.app.Application;

import com.firebase.client.Firebase;

/** Created by glebsabirzanov on 28/03/16. */
public class AppClass extends Application {

	// Database key names
//	public final String DB_HEADER = "header", DB_BRIEF_DESCRIPTION = "briefDescription",
//			DB_FULL_DESCRIPTION = "fullDescription", DB_PPL_YES = "pplYes", DB_PPL_NO = "pplNo";

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
	}

}

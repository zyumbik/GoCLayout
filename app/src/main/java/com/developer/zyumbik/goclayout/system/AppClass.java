package com.developer.zyumbik.goclayout.system;

import android.app.Application;

import com.firebase.client.Firebase;

/** Created by glebsabirzanov on 28/03/16. */
public class AppClass extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Firebase.setAndroidContext(this);
	}

}

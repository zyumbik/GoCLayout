package com.developer.zyumbik.goclayout.eventsrandom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/** Created by glebsabirzanov on 09/05/16. */
public class EventsViewPagerAdapter extends FragmentPagerAdapter {

	private FragmentURandomEvents fragmentURandomEvents;
	private FragmentVRandomEvents fragmentVRandomEvents;
	private String vTitle, uTitle;

	public void setFragment(FragmentURandomEvents fragment, String title) {
		fragmentURandomEvents = fragment;
		uTitle = title;
	}

	public void setFragment(FragmentVRandomEvents fragment, String title) {
		fragmentVRandomEvents = fragment;
		vTitle = title;
	}

	public EventsViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return fragmentURandomEvents;
			case 1:
				return fragmentVRandomEvents;
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
			case 0:
				return uTitle;
			case 1:
				return vTitle;
			default:
				return "UNTITLED";
		}
	}
}

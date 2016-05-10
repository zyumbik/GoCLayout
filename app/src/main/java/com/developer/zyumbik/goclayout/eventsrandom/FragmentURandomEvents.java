package com.developer.zyumbik.goclayout.eventsrandom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.zyumbik.goclayout.R;
import com.developer.zyumbik.goclayout.eventsrandom.userrandom.URandomEventListItem;

import java.util.ArrayList;
import java.util.List;

public class FragmentURandomEvents extends Fragment {

	private OnFragmentInteractionListener listener;
	private List<URandomEventListItem> events;
	private List<Boolean> answeredEvents;

	public void setListener(OnFragmentInteractionListener listener) {
		this.listener = listener;
	}

	public static FragmentURandomEvents newInstance(List<URandomEventListItem> events, List<Boolean> answeredEvents) {
		FragmentURandomEvents fragment = new FragmentURandomEvents();
		fragment.events = events;
		fragment.answeredEvents = answeredEvents;
		return fragment;
	}

	public FragmentURandomEvents() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_vrandom_events, container, false);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	public interface OnFragmentInteractionListener {
		void onClickAnswered();
		void onClickNotAnswered();
	}
}

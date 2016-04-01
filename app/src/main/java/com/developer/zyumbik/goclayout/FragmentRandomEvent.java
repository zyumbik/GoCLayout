package com.developer.zyumbik.goclayout;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRandomEvent extends Fragment {

	public static FragmentRandomEvent newInstance() {
		FragmentRandomEvent fragment = new FragmentRandomEvent();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentRandomEvent() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_random_event, container, false);

		return v;
	}

	public void onButtonPressed() {

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	public interface OnFragmentInteractionListener {

	}
}

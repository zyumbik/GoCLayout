package com.developer.zyumbik.goclayout.userrandom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.developer.zyumbik.goclayout.R;
import com.developer.zyumbik.goclayout.calculator.Probability;
import com.firebase.client.annotations.NotNull;

public class FragmentRandomEventPoll extends BottomSheetDialogFragment {

	public static FragmentRandomEventPoll newInstance(Probability probability) {
		FragmentRandomEventPoll fragment = new FragmentRandomEventPoll();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@NotNull @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				BottomSheetDialog d = (BottomSheetDialog) dialog;

				FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
				BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
			}
		});
		dialog.setContentView(R.layout.fragment_random_event_poll);
		return dialog;
	}

	public FragmentRandomEventPoll() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_random_event_poll, container, false);
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

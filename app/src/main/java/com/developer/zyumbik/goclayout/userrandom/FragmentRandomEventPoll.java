package com.developer.zyumbik.goclayout.userrandom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;
import com.firebase.client.annotations.NotNull;

public class FragmentRandomEventPoll extends BottomSheetDialogFragment {

	TextView header;

	public static FragmentRandomEventPoll newInstance(URandomEventListItem item) {
		FragmentRandomEventPoll fragment = new FragmentRandomEventPoll();
		fragment.header.setText(item.getHeader());
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
		header = (TextView) v.findViewById(R.id.uRnd_fragment_bottom_sheet_poll_header);
		return v;
	}

}

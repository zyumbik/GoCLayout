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

/** Created by glebsabirzanov on 20/04/16. */
public class FragmentRandomEventDetails extends BottomSheetDialogFragment {

	private static URandomEventListItem eventItem;
	private TextView header, description, percentage;

	public FragmentRandomEventDetails() {
		// Required empty constructor
	}

	public static FragmentRandomEventDetails newInstance(URandomEventListItem item) {
		FragmentRandomEventDetails fragment = new FragmentRandomEventDetails();
		eventItem = item;
		return fragment;
	}

	@NotNull
	@Override
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
		dialog.setContentView(R.layout.fragment_random_event_details);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_random_event_details, container, false);
		header = (TextView) v.findViewById(R.id.uRnd_fragment_bottom_sheet_details_header);
		description = (TextView) v.findViewById(R.id.uRnd_fragment_bottom_sheet_details_description);
		percentage = (TextView) v.findViewById(R.id.uRnd_fragment_bottom_sheet_graph_percentage);
		header.setText(eventItem.getHeader());
		description.setText(eventItem.getDescription());
		percentage.setText(eventItem.getProbabilityDouble() + "%");
		return v;
	}

}

package com.developer.zyumbik.goclayout.userrandom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;
import com.firebase.client.annotations.NotNull;
import com.firebase.client.realtime.util.StringListReader;

public class FragmentRandomEventPoll extends BottomSheetDialogFragment {

	private TextView header;
	private Button skip, positive, negative;
	private static String headerItem;

	public static FragmentRandomEventPoll newInstance(URandomEventListItem item) {
		FragmentRandomEventPoll fragment = new FragmentRandomEventPoll();
		headerItem = item.getHeader();
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
		header.setText(headerItem);
		skip = (Button) v.findViewById(R.id.uRnd_fragment_bottom_sheet_button_skip);
		positive = (Button) v.findViewById(R.id.uRnd_fragment_bottom_sheet_button_positive);
		negative = (Button) v.findViewById(R.id.uRnd_fragment_bottom_sheet_button_negative);

		skip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("onClick", "skip");
			}
		});

		positive.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("onClick", "positive");

			}
		});

		negative.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("onClick", "negative");

			}
		});

		return v;
	}

	public interface pollButtonPressed {

	}

}

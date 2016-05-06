package com.developer.zyumbik.goclayout.userrandom;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;

public class FragmentRandomEventPoll extends BottomSheetDialogFragment implements View.OnClickListener {

	private TextView header;
	private Button skip, positive, negative;
	private static String headerItem;
	private PollButtonClickListener listener;

	public FragmentRandomEventPoll() {
		// Required empty public constructor
	}

	public void setListener(PollButtonClickListener listener) {
		this.listener = listener;
	}

	public static FragmentRandomEventPoll newInstance(URandomEventListItem item) {
		FragmentRandomEventPoll fragment = new FragmentRandomEventPoll();
		headerItem = item.getHeader();
		return fragment;
	}

//	@NonNull @Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
//		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//			@Override
//			public void onShow(DialogInterface dialog) {
//				BottomSheetDialog d = (BottomSheetDialog) dialog;
//				FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
//				BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
//			}
//		});
//		dialog.setContentView(R.layout.fragment_random_event_poll);
//		return dialog;
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_random_event_poll, container, false);
		header = (TextView) v.findViewById(R.id.uRnd_fragment_bottom_sheet_poll_header);
		skip = (Button) v.findViewById(R.id.uRnd_fragment_bottom_sheet_button_skip);
		positive = (Button) v.findViewById(R.id.uRnd_fragment_bottom_sheet_button_positive);
		negative = (Button) v.findViewById(R.id.uRnd_fragment_bottom_sheet_button_negative);

		header.setText(headerItem);
		skip.setOnClickListener(this);
		positive.setOnClickListener(this);
		negative.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		if (listener != null) {
			if (v.equals(skip)) {
				listener.onPollButtonClickSkip();
			} else if (v.equals(positive)) {
				listener.onPollButtonClickPositive();
			} else if (v.equals(negative)) {
				listener.onPollButtonClickNegative();
			}
			listener.onAnyButtonClick();
			dismiss();
		}
	}

	public interface PollButtonClickListener {
		void onAnyButtonClick();
		void onPollButtonClickSkip();
		void onPollButtonClickPositive();
		void onPollButtonClickNegative();
	}

}

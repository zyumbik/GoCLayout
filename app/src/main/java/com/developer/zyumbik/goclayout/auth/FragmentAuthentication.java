package com.developer.zyumbik.goclayout.auth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.developer.zyumbik.goclayout.R;

public class FragmentAuthentication extends AppCompatDialogFragment implements DialogInterface.OnCancelListener {

	private OnAuthFragmentInteractionListener listener;

	public void setListener(OnAuthFragmentInteractionListener listener) {
		this.listener = listener;
	}

	public static FragmentAuthentication newInstance() {
		FragmentAuthentication fragment = new FragmentAuthentication();
		return fragment;
	}

	public FragmentAuthentication() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_authentication, container, false);
		Button submit = (Button) v.findViewById(R.id.fragment_auth_button_submit);
		final EditText email = (EditText) v.findViewById(R.id.fragment_auth_input_email);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: add email address input verification
				if (listener != null) {
					listener.onSubmitClicked(email.getText().toString());
					dismiss();
				}
			}
		});
		return v;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		if (listener != null) {
			listener.onCancelled();
		}
	}

	public interface OnAuthFragmentInteractionListener {
		void onSubmitClicked(String email);
		void onCancelled();
	}
}
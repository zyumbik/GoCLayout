package com.developer.zyumbik.goclayout.auth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.developer.zyumbik.goclayout.R;

public class FragmentAuthentication extends AppCompatDialogFragment implements DialogInterface.OnCancelListener {

	private TextInputEditText email;
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
		Button forgot = (Button) v.findViewById(R.id.fragment_auth_button_forgot);
		email = (TextInputEditText) v.findViewById(R.id.fragment_auth_input_email);
		final TextInputEditText password = (TextInputEditText) v.findViewById(R.id.fragment_auth_input_password);
		final CheckBox checkBoxRegistered = (CheckBox) v.findViewById(R.id.fragment_auth_checkbox_registered);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: add email address input verification
				if (listener != null) {
					listener.onSubmitClicked(email.getText().toString(), password.getText().toString(), checkBoxRegistered.isChecked());
				}
			}
		});
		forgot.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onForgotClicked(email.getText().toString());
				}
			}
		});
		return v;
	}

	public String getEmail() {
		return email.getText().toString();
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		if (listener != null) {
			listener.onCancelled();
		}
	}

	public interface OnAuthFragmentInteractionListener {
		void onSubmitClicked(String email, String password, boolean registered);
		void onForgotClicked(String email);
		void onCancelled();
	}
}
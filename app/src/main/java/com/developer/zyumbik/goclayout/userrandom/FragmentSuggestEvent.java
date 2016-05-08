package com.developer.zyumbik.goclayout.userrandom;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.developer.zyumbik.goclayout.R;

/** Created by glebsabirzanov on 08/05/16. */

public class FragmentSuggestEvent extends AppCompatDialogFragment{

	EditText title, description;
	Toolbar toolbar;
	OnInteractionListener listener;

	public void setListener(OnInteractionListener listener) {
		this.listener = listener;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setStyle(AppCompatDialogFragment.STYLE_NO_FRAME, R.style.AppTheme_NoActionBar);
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_suggest_event, container);
		title = (TextInputEditText) v.findViewById(R.id.fragment_suggestion_input_title);
		description = (TextInputEditText) v.findViewById(R.id.fragment_suggestion_input_description);
		toolbar = (Toolbar) v.findViewById(R.id.fragment_suggestion_action_bar);
		setToolbar();
		return v;
	}

	private void setToolbar() {
		toolbar.setTitle("Add new event");
		toolbar.setNavigationIcon(R.drawable.ic_clear_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onDiscard();
			}
		});
		toolbar.inflateMenu(R.menu.menu_fragment_suggestion);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_save_suggestion:
						if (title.getText().toString().length() <= 35 && description.getText().toString().length() <= 250) {
							if (listener != null) {
								listener.onSaveClicked(title.getText().toString(), title.getText().toString());
							}
						}
						break;
				}
				return false;
			}
		});
	}

	public interface OnInteractionListener {
		void onSaveClicked(String title, String description);
		void onDiscard();
	}

}

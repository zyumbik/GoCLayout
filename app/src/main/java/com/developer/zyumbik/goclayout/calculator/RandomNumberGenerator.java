package com.developer.zyumbik.goclayout.calculator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.developer.zyumbik.goclayout.R;

import java.util.Random;

public class RandomNumberGenerator extends AppCompatDialogFragment {

	Toolbar toolbar;
	TextInputEditText limit, output;
	SeekBar slider;
	Button button;
	Random rnd = new Random();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_random_number_generator, container);
		toolbar = (Toolbar)v.findViewById(R.id.fragment_random_number_toolbar);
		limit = (TextInputEditText) v.findViewById(R.id.fragment_random_number_limit);
		output = (TextInputEditText) v.findViewById(R.id.fragment_random_number_output);
		slider = (SeekBar) v.findViewById(R.id.fragment_random_number_slider);
		button = (Button) v.findViewById(R.id.fragment_random_number_button_generate);
		setButton();
		setLimitField();
		setSlider();
		setToolbar();
		return v;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setStyle(AppCompatDialogFragment.STYLE_NO_FRAME, R.style.AppTheme_NoActionBar);
		return super.onCreateDialog(savedInstanceState);
	}

	public void setButton() {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				output.setText(String.valueOf(rnd.nextInt(Integer.valueOf(limit.getText().toString()))));
			}
		});
	}

	private void setLimitField() {
		limit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				limit.requestLayout();
				slider.requestLayout();
				if (Double.valueOf(s.toString()) > Integer.MAX_VALUE) {
					limit.setText(String.valueOf(Integer.MAX_VALUE));
					Toast.makeText(getContext(), "Isn't it too much for you?", Toast.LENGTH_SHORT).show();
				} else {
					slider.setProgress((int)Math.log(Double.valueOf(limit.getText().toString())));
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void setSlider() {
		slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					if (progress == 31) {
						limit.setText(String.valueOf((int)Math.pow(2, progress - 1) - 1));
					} else {
						limit.setText(String.valueOf((int)Math.pow(2, progress - 1)));
					}
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}

	private void setToolbar() {
		toolbar.setTitle("Random number generator");
		toolbar.setNavigationIcon(R.drawable.ic_clear_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}

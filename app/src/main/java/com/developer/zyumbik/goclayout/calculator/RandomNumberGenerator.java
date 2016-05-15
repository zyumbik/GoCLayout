package com.developer.zyumbik.goclayout.calculator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.developer.zyumbik.goclayout.R;

import java.util.Random;

public class RandomNumberGenerator extends AppCompatDialogFragment {

	Toolbar toolbar;
	TextInputEditText output;
	EditText limit;
	SeekBar slider;
	Button button;
	Random rnd = new Random();
	boolean userInteraction = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_random_number_generator, container);
		toolbar = (Toolbar)v.findViewById(R.id.fragment_random_number_toolbar);
		limit = (EditText) v.findViewById(R.id.fragment_random_number_limit);
		output = (TextInputEditText) v.findViewById(R.id.fragment_random_number_output);
		slider = (SeekBar) v.findViewById(R.id.fragment_random_number_slider);
		button = (Button) v.findViewById(R.id.fragment_random_number_button_generate);
		setRandomLimit();
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
				int currentLimit = getLimitValue();
				if (currentLimit != Integer.MAX_VALUE) {
					output.setText(String.valueOf(rnd.nextInt(currentLimit + 1)));
				} else {
					output.setText(String.valueOf(rnd.nextInt(currentLimit)));
				}
			}
		});
	}

	private void setLimitField() {
		limit.requestFocus();
		limit.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!userInteraction) {
					if (limitValueAcceptable()) {
						setProgressFromLimit();
					}
				}
				limit.requestLayout();
				slider.requestLayout();
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		limit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					getLimitValue();
				}
			}
		});
	}

	private void setSlider() {
		slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					setLimitFromProgress(progress);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				if (!limit.isFocused()) {
					limit.requestFocus();
				}
				limit.setInputType(InputType.TYPE_NULL);
				userInteraction = true;
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				userInteraction = false;
				limit.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
		});
	}

	private void setRandomLimit() {
		limit.setText(String.valueOf(rnd.nextInt((int) Math.pow(2, 14))));
		setProgressFromLimit();
	}

	private boolean limitValueAcceptable() {
		String lim = limit.getText().toString();
		return !(lim.equals("") || Long.valueOf(lim) <= 0 || Long.valueOf(lim) > Integer.MAX_VALUE);
	}

	private int getLimitValue() {
		if (!limitValueAcceptable()) {
			setLimitFromProgress(slider.getProgress());
		}
		return Integer.valueOf(limit.getText().toString());
	}

	private void setLimitFromProgress(int progress) {
		if (progress == 32) {
			limit.setText(String.valueOf(Integer.MAX_VALUE));
		} else {
			limit.setText(String.valueOf((int)Math.pow(2, progress)));
		}
	}

	private void setProgressFromLimit() {
		if (Long.valueOf(limit.getText().toString()) > Integer.MAX_VALUE) {
			limit.setText(String.valueOf(Integer.MAX_VALUE));
			Toast.makeText(getContext(), "Isn't it too much for you?", Toast.LENGTH_SHORT).show();
		} else {
			int a = binlog(Integer.valueOf(limit.getText().toString()) + 1);
			slider.setProgress(a);
		}
	}

	public int binlog( int bits ) {
		int log = 0;
		if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
		if( bits >= 256 ) { bits >>>= 8; log += 8; }
		if( bits >= 16  ) { bits >>>= 4; log += 4; }
		if( bits >= 4   ) { bits >>>= 2; log += 2; }
		return log + ( bits >>> 1 );
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

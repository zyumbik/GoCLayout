package com.developer.zyumbik.goclayout.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.Calculator;
import com.developer.zyumbik.goclayout.R;

import java.util.ArrayList;

/** Created by glebsabirzanov on 23/03/16. */

public class CalculatorAdapter extends RecyclerView.Adapter {

	ArrayList<Probability> list = new ArrayList<>(26);


	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
		// each data item is just a string in this case
		public TextView textView;
		public CheckBox checkBox;
		public ImageButton removeButton;
		public ViewHolder(View v) {
			super(v);
			textView = (TextView) v.findViewById(R.id.calcListText);
			checkBox = (CheckBox) v.findViewById(R.id.calcListCheckbox);
			removeButton = (ImageButton) v.findViewById(R.id.calcListButtonRemove);
			removeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getAdapterPosition();
				}
			});

			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				}
			});
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			return false;
		}
	}

	//TODO: implement constructor
	public CalculatorAdapter() {}

	@Override
	public CalculatorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		// create a new view
		View v =  LayoutInflater.from(parent.getContext())
				.inflate(R.layout.calculator_list_item, parent, false);

		// set the view's size, margins, paddings and layout parameters

		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
	}

	@Override
	public int getItemCount() {
		return 0;
	}
}

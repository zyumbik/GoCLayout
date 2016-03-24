package com.developer.zyumbik.goclayout.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;

import java.util.ArrayList;

/** Created by glebsabirzanov on 23/03/16. */

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> {

	private ArrayList<Probability> list;

	public void addProbability(int outcomes, int events) {
		list.add(new Probability(list.size() - 1, outcomes, events));
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
		public void onClick(View v) {
			if (v.equals(removeButton)) {
				removeAt(getAdapterPosition());
			}
		}

	}

	private void removeAt(int position) {
		list.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, list.size());
	}

	public CalculatorAdapter() {
		list = new ArrayList<>(26);
	}

	@Override
	public CalculatorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

		// create a new view
		View v =  LayoutInflater.from(parent.getContext())
				.inflate(R.layout.calculator_list_item, parent, false);

		// set the view's size, margins, paddings and layout parameters

		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.textView.setText(list.get(position).getDouble());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
}

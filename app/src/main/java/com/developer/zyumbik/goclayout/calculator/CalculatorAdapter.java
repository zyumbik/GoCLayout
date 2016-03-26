package com.developer.zyumbik.goclayout.calculator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;

import java.util.ArrayList;

/** Created by glebsabirzanov on 23/03/16. */

public class CalculatorAdapter extends RecyclerView.Adapter<CalculatorAdapter.ViewHolder> {

	private ArrayList<Probability> list;
	private static int typeOfData = 0, maxIndex = 0;

	public void addProbability(int outcomes, int events) {
		// TODO: put a restriction on adding new elements
		if (maxIndex < 27) {
			list.add(new Probability(maxIndex++, outcomes, events));

			// TODO: implement smooth animation
			notifyDataSetChanged();
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView textValue;
		public TextView textIndex;
		public CheckBox checkBox;
		public ImageButton removeButton;
		public RelativeLayout layout;

		public ViewHolder(View v) {
			super(v);
			textValue = (TextView) v.findViewById(R.id.calcListValue);
			textIndex = (TextView) v.findViewById(R.id.calcListIndex);
			checkBox = (CheckBox) v.findViewById(R.id.calcListCheckbox);
			removeButton = (ImageButton) v.findViewById(R.id.calcListButtonRemove);
			layout = (RelativeLayout) v.findViewById(R.id.calcListLayout);

			removeButton.setOnClickListener(this);
			layout.setOnClickListener(this);

			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				}
			});
		}

		private void changeTypeOfData() {
			if (typeOfData != 2) {
				typeOfData++;
			} else {
				typeOfData = 0;
			}
		}

		@Override
		public void onClick(View v) {
			if (v.equals(removeButton)) {
				removeAt(getAdapterPosition());
			} else {
				changeTypeOfData();
//				notifyItemChanged(getAdapterPosition());
				notifyDataSetChanged();
			}
		}

	}

	private void removeAt(int position) {
		list.remove(position);
//		notifyItemRemoved(position);
//		notifyItemRangeChanged(position, list.size());

		// TODO: implement smooth animation
		notifyDataSetChanged();
	}

	public CalculatorAdapter() {
		this.list = new ArrayList<>();
	}

	@Override
	public CalculatorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

		// create a new view
		View v =  LayoutInflater.from(parent.getContext())
				.inflate(R.layout.calculator_list_item, parent, false);

		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.textIndex.setText(list.get(position).getIndex());
		switch (typeOfData) {
			case 1:
				holder.textValue.setText(list.get(position).getDouble());
				break;
			case 0:
				holder.textValue.setText(list.get(position).getPercent());
				break;
			default:
				holder.textValue.setText(list.get(position).getFraction());
				break;
		}
	}

	@Override
	public int getItemCount() {
		return list.size();
	}
}

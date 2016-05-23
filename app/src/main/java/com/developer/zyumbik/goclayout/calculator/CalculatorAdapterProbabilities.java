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

public class CalculatorAdapterProbabilities extends RecyclerView.Adapter<CalculatorAdapterProbabilities.ViewHolder> {

	private ArrayList<Probability> list;
	private int maxIndex = 0;
	private enum DataRepresentationType {DOUBLE, PERCENT, FRACTION}
	private static DataRepresentationType typeOfData = DataRepresentationType.DOUBLE;

	public void addProbability(int outcomes, int events) {
		list.add(new Probability(maxIndex++, outcomes, events));
		// TODO: implement smooth animation
		notifyDataSetChanged();
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView textValue;
		public TextView textIndex;
		public ImageButton removeButton;
		public RelativeLayout layout;

		public ViewHolder(View v) {
			super(v);
			textValue = (TextView) v.findViewById(R.id.calcListValue);
			textIndex = (TextView) v.findViewById(R.id.calcListIndex);
			removeButton = (ImageButton) v.findViewById(R.id.calcListButtonRemove);
			layout = (RelativeLayout) v.findViewById(R.id.calcListLayout);

			removeButton.setOnClickListener(this);
			layout.setOnClickListener(this);

		}

		private void changeTypeOfData() {
			// TODO: change to "Flip probability"
			switch (typeOfData) {
				case DOUBLE:
					typeOfData = DataRepresentationType.PERCENT;
					break;
				case PERCENT:
					typeOfData = DataRepresentationType.FRACTION;
					break;
				default:
					typeOfData = DataRepresentationType.DOUBLE;
					break;
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

	public CalculatorAdapterProbabilities() {
		this.list = new ArrayList<>();
	}

	@Override
	public CalculatorAdapterProbabilities.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {

		// create a new view
		View v =  LayoutInflater.from(parent.getContext())
				.inflate(R.layout.calculator_probabilitiy_list_item, parent, false);

		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.textIndex.setText(list.get(position).getIndex());
		switch (typeOfData) {
			case DOUBLE:
				holder.textValue.setText(list.get(position).getDouble());
				break;
			case PERCENT:
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

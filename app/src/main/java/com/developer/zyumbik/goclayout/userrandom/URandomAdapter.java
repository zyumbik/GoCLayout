package com.developer.zyumbik.goclayout.userrandom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;

import java.util.ArrayList;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomAdapter extends RecyclerView.Adapter<URandomAdapter.ViewHolder> {

	private ArrayList<URandomEventBrief> listBrief;
	private ArrayList<URandomEventFull> listFull;

	public URandomAdapter(ArrayList<URandomEventBrief> listBrief) {
		this.listBrief = listBrief;
	}

	public void setListFull(ArrayList<URandomEventFull> listFull) {
		this.listFull = listFull;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public TextView header, description, percentage;
		public ViewHolder(View v) {
			super(v);
			header = (TextView) v.findViewById(R.id.uRnd_list_item_header);
			percentage = (TextView) v.findViewById(R.id.uRnd_list_item_probability);
			description = (TextView) v.findViewById(R.id.uRnd_list_item_description);
		}

		@Override
		public void onClick(View v) {

		}
	}

	@Override
	public URandomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.user_random_list_item, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(URandomAdapter.ViewHolder holder, int position) {
		holder.header.setText(listBrief.get(position).getHeader());
		holder.description.setText(listBrief.get(position).getDescription());
		holder.percentage.setText(listBrief.get(position).getProbabilityInteger());
	}

	@Override
	public int getItemCount() {
		return listBrief.size();
	}
}

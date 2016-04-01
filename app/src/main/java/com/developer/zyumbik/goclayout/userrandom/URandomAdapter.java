package com.developer.zyumbik.goclayout.userrandom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;

import java.util.List;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomAdapter extends RecyclerView.Adapter<URandomAdapter.ViewHolder> {

	private List<URandomEvent> events;

	public URandomAdapter(List<URandomEvent> events) {
		this.events = events;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public TextView header, description, percentage;
		public ViewHolder(View v) {
			super(v);
			header = (TextView) v.findViewById(R.id.uRnd_list_item_header);
			percentage = (TextView) v.findViewById(R.id.uRnd_list_item_percentage);
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
		if (events.get(position).hashCode() != 0) {
			holder.header.setText(events.get(position).getHeader());
			holder.description.setText(events.get(position).getDescription());
			holder.percentage.setText(events.get(position).getProbabilityInteger());
		} else {
			events.remove(position);
		}
	}

	@Override
	public int getItemCount() {
		return events.size();
	}
}

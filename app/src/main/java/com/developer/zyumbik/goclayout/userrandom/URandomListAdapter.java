package com.developer.zyumbik.goclayout.userrandom;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.zyumbik.goclayout.R;
import com.developer.zyumbik.goclayout.URandomEvents;

import java.util.List;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomListAdapter extends RecyclerView.Adapter<URandomListAdapter.ViewHolder> {

	private List<URandomEventListItem> events;
	private ItemClickListener listener;

	public URandomListAdapter(List<URandomEventListItem> events) {
		this.events = events;
	}

	public void setListener(ItemClickListener listener) {
		this.listener = listener;
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		public TextView header, description, percentage;
		public URandomEventListItem item;

		public ViewHolder(View v) {
			super(v);
			v.setOnClickListener(this);
			header = (TextView) v.findViewById(R.id.uRnd_list_item_header);
			percentage = (TextView) v.findViewById(R.id.uRnd_list_item_percentage);
			description = (TextView) v.findViewById(R.id.uRnd_list_item_description);
		}

		public void initializeHolder(URandomEventListItem item) {
			header.setText(item.getHeader());
			description.setText(item.getDescription());
			percentage.setText(item.getProbabilityInteger());
		}

		@Override
		public void onClick(View v) {
			if (listener != null && item != null) {
				listener.onItemClick(item, getAdapterPosition());
			}
		}
	}

	@Override
	public URandomListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.user_random_list_item, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(URandomListAdapter.ViewHolder holder, int position) {
		if (events.get(position).hashCode() != 0) {
			holder.initializeHolder(events.get(position));
			holder.item = events.get(position);
		} else {
			events.remove(position);
		}
	}

	@Override
	public int getItemCount() {
		return events.size();
	}

	public interface ItemClickListener {
		void onItemClick(URandomEventListItem item, int id);
	}

}

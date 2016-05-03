package com.developer.zyumbik.goclayout.userrandom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.zyumbik.goclayout.R;

import java.util.List;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomListAdapter extends RecyclerView.Adapter<URandomListAdapter.ViewHolder> {

	private List<URandomEventListItem> events;
	private List<Boolean> answeredEvents;
	private ItemClickListener listener;

	public URandomListAdapter(List<URandomEventListItem> events) {
		this.events = events;
	}

	public URandomListAdapter(List<URandomEventListItem> events, List<Boolean> answeredEvents) {
		this.events = events;
		this.answeredEvents = answeredEvents;
	}

	public void setListener(ItemClickListener listener) {
		this.listener = listener;
	}

	public void setAnsweredEvent(Boolean event, int id) {
		answeredEvents.set(id, event);
		notifyItemChanged(id);
	}

	public void replaceListItem(URandomEventListItem item, int index) {
		events.set(index, item);
		notifyItemChanged(index);
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

		public void initializeHolder(int position) {
			item = events.get(position);
			header.setText(item.getHeader());
			description.setText(item.getDescription());
			if (answeredEvents != null) {
				if (answeredEvents.get(position)) {
					percentage.setText(item.getProbabilityPercentRound());
					return;
				}
			}
			percentage.setText(R.string.hidden_percentage);
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
			holder.initializeHolder(position);
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

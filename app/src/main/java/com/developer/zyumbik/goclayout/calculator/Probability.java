package com.developer.zyumbik.goclayout.calculator;

import java.text.DecimalFormat;

/** Created by glebsabirzanov on 23/03/16. */
public class Probability {

	private int outcomes, events;
	private String index;
	private static final char[] indexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	public Probability(int index, int outcomes, int events) {
		this.outcomes = outcomes;
		this.events = events;
		newIndex(index);
	}

	// Generate alphabetic indexes with overflow
	private void newIndex(int index) {
		StringBuilder sb = new StringBuilder();
		while (index >= indexes.length) {
			sb.append(indexes[index % indexes.length]);
			index /= indexes.length;
		}
		sb.append(indexes[index]);
		sb.reverse();
		this.index = sb.toString();
	}

	public String getIndex() {
		return String.valueOf(this.index);
	}

	private float getValue() {
		return events / (float) outcomes;
	}

	public String getPercent() {
		// TODO: change to ##.(*), where (*) is the repeating part (for repeating decimals)
		return new DecimalFormat("#.##%").format(getValue());
	}

	public String getDouble() {
		return new DecimalFormat("0.##").format(getValue());
	}

	public String getFraction() {
		return outcomes + "/" + events ;
	}

}

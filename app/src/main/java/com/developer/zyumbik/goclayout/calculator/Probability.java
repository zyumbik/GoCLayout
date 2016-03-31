package com.developer.zyumbik.goclayout.calculator;

import java.text.DecimalFormat;

/** Created by glebsabirzanov on 23/03/16. */
public class Probability {

	private int outcomes, events;
	private char index;
	private static final char[] indexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	public Probability(int index, int outcomes, int events) {
		this.outcomes = outcomes;
		this.events = events;
		this.index = indexes[index];
//		newIndex(index);
	}

	// Generate alphabetic indexes with overflow
//	private void newIndex(int index) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < (index / indexes.length) + 1; i++) {
//			sb.append((char)indexes[index % indexes.length]);
//		}
//		this.index = sb.toString();
//	}

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

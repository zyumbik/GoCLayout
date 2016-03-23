package com.developer.zyumbik.goclayout.calculator;

import java.text.DecimalFormat;

/** Created by glebsabirzanov on 23/03/16. */
public class Probability {

	private int outcomes, events, index;
	private static final int[] indexes = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

	public Probability(int index, int outcomes, int events) {
		this.outcomes = outcomes;
		this.events = events;
		this.index = index;
	}

	private float getValue() {
		return events / (float) outcomes;
	}

	public String getPercent() {
		return indexes[index] + ": " + new DecimalFormat("%#.##").format(getValue());
	}

	public String getDouble() {
		return indexes[index] + ": " + new DecimalFormat("0.##").format(getValue());
	}

}

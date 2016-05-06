package com.developer.zyumbik.goclayout.userrandom;

import java.text.DecimalFormat;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomEventListItem {

	private String header, description;
	private int pplYes, pplNo;

	public URandomEventListItem() {
		// Required empty constructor
	}

	public void addPplNo() {
		this.pplNo++;
	}

	public void addPplYes() {
		this.pplYes++;
	}

	public URandomEventListItem(String header, String description, int pplYes, int pplNo) {
		this.header = header;
		this.description = description;
		this.pplYes = pplYes;
		this.pplNo = pplNo;
	}

	public String getHeader() {
		return header;
	}

	public String getDescription() {
		return description;
	}

	public long getProbabilityLong() {
		return Math.round(getProbabilityDouble() * 100);
	}

	public double getProbabilityDouble() {
		return pplYes / (double)(pplNo + pplYes);
	}

	public String getProbabilityPercentRound() {
		return getProbabilityLong() + "%";
	}

	public String getProbabilityPercentFull() {
		// TODO: make it work better with repeating decimals
		return Double.valueOf(new DecimalFormat("##.##").format(getProbabilityDouble() * 100)) + "%";
	}

	public int getPplYes() {
		return pplYes;
	}

	public int getPplNo() {
		return pplNo;
	}

	@Override
	public int hashCode() {
		if (header.length() == 0) {
			return 0;
		} else {
			if(super.hashCode() != 0) {
				return super.hashCode();
			}
			return (header.length() + description.length()) *
					(description.length()) /
					(pplYes == 0 ? pplNo : pplYes);
		}
	}
}

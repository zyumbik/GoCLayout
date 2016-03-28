package com.developer.zyumbik.goclayout.userrandom;

import java.text.DecimalFormat;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomEventBrief {

	private String header, description;
	private int pplYes, pplNo;

	public URandomEventBrief(String header, String description, int pplYes, int pplNo) {
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

	public String getProbabilityInteger() {
		return (pplYes * 100) / (pplYes + pplNo) + "%";
	}

	public double getProbabilityDouble() {
		// TODO: make it work better with repeating decimals
		return Double.valueOf(new DecimalFormat("##.##").format(pplYes / (double)(pplNo + pplYes)));
	}

	public int getPplYes() {
		return pplYes;
	}

	public int getPplNo() {
		return pplNo;
	}
}

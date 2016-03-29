package com.developer.zyumbik.goclayout.userrandom;

import java.text.DecimalFormat;

/** Created by glebsabirzanov on 27/03/16. */
public class URandomEvent {

	private String header, briefDescription, fullDescription;
	private int pplYes, pplNo;

	public URandomEvent() {
	}

	public URandomEvent(String header, String briefDescription, String fullDescription, int pplYes, int pplNo) {
		this.header = header;
		this.briefDescription = briefDescription;
		this.fullDescription = fullDescription;
		this.pplYes = pplYes;
		this.pplNo = pplNo;
	}

	public String getHeader() {
		return header;
	}

	public String getBriefDescription() {
		return briefDescription;
	}

	public String getFullDescription() {
		return fullDescription;
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

	@Override
	public int hashCode() {
		if (header.length() == 0) {
			return 0;
		} else {
			return super.hashCode();
		}
	}
}

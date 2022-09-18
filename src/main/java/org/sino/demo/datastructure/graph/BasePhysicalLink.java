package org.sino.demo.datastructure.graph;

public class BasePhysicalLink {
	private String linkName;
	private String srcNeServiceKey;
	private String sinkNeServiceKey;
	private Float cost;

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getSrcNeServiceKey() {
		return srcNeServiceKey;
	}

	public void setSrcNeServiceKey(String srcNeServiceKey) {
		this.srcNeServiceKey = srcNeServiceKey;
	}

	public String getSinkNeServiceKey() {
		return sinkNeServiceKey;
	}

	public void setSinkNeServiceKey(String sinkNeServiceKey) {
		this.sinkNeServiceKey = sinkNeServiceKey;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}

package org.sino.demo.datastructure.graph;

public class BaseLogicalConnection {
	private String connectionName;
	private String srcNeServiceKey;
	private String sinkNeServiceKey;
	private Float cost;

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
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

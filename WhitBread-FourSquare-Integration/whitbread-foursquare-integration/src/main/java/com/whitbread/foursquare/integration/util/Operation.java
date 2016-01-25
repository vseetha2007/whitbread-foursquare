package com.whitbread.foursquare.integration.util;

public enum Operation {
	
	EXPLORE("/explore");
	
	private String path;
	
	private Operation(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

}

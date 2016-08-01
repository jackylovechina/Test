package com.domain;

public class Pic {
	private String name;
	private String filepath;

	public Pic(String name, String filepath) {
		super();
		this.name = name;
		this.filepath = filepath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}

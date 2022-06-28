package com.tracker.corona.modal;

import lombok.Data;

@Data
public class CoronaAllStats {

	private String state;
	private String country;
	private int confirmed;
	private int recovered;
	private int death;
}

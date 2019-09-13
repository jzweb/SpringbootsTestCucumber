package com.integrationTestCucumber.model;

import java.util.Random;

import javax.validation.constraints.NotNull;

public class Person {
	private int id= createRandonNumber();

	@NotNull
	private String name;

	@NotNull
	private String lastname;
	
	private int createRandonNumber() {
		Random rnd = new Random();
		int n = 100000000 + rnd.nextInt(900000000);
		return n;
	}
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String name, String lastname) {
		super();
		this.id = this.createRandonNumber();
		this.name = name;
		this.lastname = lastname;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}

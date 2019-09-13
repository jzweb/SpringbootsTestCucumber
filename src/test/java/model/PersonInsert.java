package model;

public class PersonInsert {
	
	private String name;
	private String lastname;
	
	
	public PersonInsert() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PersonInsert(String name, String lastname) {
		super();
		this.name = name;
		this.lastname = lastname;
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

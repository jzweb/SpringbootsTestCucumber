package com.integrationTestCucumber.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.integrationTestCucumber.model.Person;

@Service
public class PersonService {
	
	ObjectMapper mapper = null;
	ObjectNode node = null;
	
	ArrayList<Person> listPerson = new ArrayList<Person>();
	
	public int addPerson(Person person) {
		int result = 0;
		boolean isAdded = listPerson.add(person);
		if(isAdded) {
			result = person.getId();
		}
		return result;
	}
	
	public Person findPersonById(int personId) {
		Person foundPerson = null;
		for(Person person:listPerson) {
			if(person.getId() == personId) {
				foundPerson = person;
			}
		}
		return foundPerson;
	}
	
	public List<Person> getPersons(){
		return listPerson;
	}
}



package com.integrationTestCucumber.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.integrationTestCucumber.model.Person;
import com.integrationTestCucumber.service.PersonService;

@RestController
@RequestMapping("/persona")
public class PersonController {
	
	@Autowired
	PersonService personService;
	ObjectMapper mapper = null;
	ObjectNode response = null;
	
	private  ObjectNode getInsertJsonResponse(int id) {
		mapper = new ObjectMapper();
		response = mapper.createObjectNode();
		
		if(id!=0) {
			response.put("status", true);
			response.put("personId", id);
		} else {
			JsonNode jsonId = mapper.convertValue(null, JsonNode.class);
			response.put("status", false);
			response.put("personId",jsonId);
		}
		
		return response;
	}
	
	private  ObjectNode getFinPersonJsonResponse(Person person) {
		mapper = new ObjectMapper();
		response = mapper.createObjectNode();
		JsonNode jsonPerson = null;
		
		if(person == null) {
			jsonPerson = mapper.convertValue(null, JsonNode.class);
			response.put("status", false);
			response.put("message", "Person is not registered.");
			response.put("person",jsonPerson);
		} else {
			jsonPerson = mapper.convertValue(person, JsonNode.class);
			response.put("status", true);
			response.put("message", "Person founded.");
			response.put("person",jsonPerson);
		}
		
		return response;
	} 
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST)
	public ResponseEntity<Object> addPerson(@Valid @RequestBody Person person){

		int result = personService.addPerson(person);
		try {
			return new ResponseEntity<Object>(getInsertJsonResponse(result),HttpStatus.OK);
		}catch (Exception e) {
			// In case something wrong happen
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@RequestMapping(value="/obtener/{personId}", method=RequestMethod.GET)
	public ResponseEntity<Object> findPersonById(@PathVariable(required=true) int personId){
		
		Person person = personService.findPersonById(personId);
		try {
			return new ResponseEntity<Object>(getFinPersonJsonResponse(person),HttpStatus.OK);
		} catch (Exception e) {
			// In case something wrong happen
			return new ResponseEntity<Object>("Some Error Occur",HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/obtener", method=RequestMethod.GET)
	public ResponseEntity<Object> getPersons(){
		 return new ResponseEntity<Object>(personService.getPersons(),HttpStatus.OK);
	}

}

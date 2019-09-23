package com.integrationTestCucumber.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.integrationTestCucumber.model.Note;

@RestController
public class NoteController {
	
	@RequestMapping(value="agregar/nota",method=RequestMethod.GET)
	public ResponseEntity<Object> addNote(@RequestBody Note note){
		
		return new ResponseEntity<Object>(null, HttpStatus.CREATED);
		
	}

}

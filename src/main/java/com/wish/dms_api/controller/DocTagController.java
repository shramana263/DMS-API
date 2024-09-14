package com.wish.dms_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wish.dms_api.dto.DocTagDto;
import com.wish.dms_api.service.IDocTagService;

@RestController
@RequestMapping("/api/tag")
public class DocTagController {
	
	@Autowired IDocTagService docTagService;

	@PostMapping("/savetag")
	public ResponseEntity<String> store(@RequestBody DocTagDto docTagDto){
		
		docTagService.addTag(docTagDto);
		return new ResponseEntity<>("Tag created",HttpStatus.CREATED);
	}
	
}

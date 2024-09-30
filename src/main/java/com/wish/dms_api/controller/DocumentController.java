package com.wish.dms_api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

//import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

//import com.wish.dms_api.dto.DocumentByDocTypeDto;
import com.wish.dms_api.dto.DocumentByUserDto;
import com.wish.dms_api.dto.DocumentRequestDto;
import com.wish.dms_api.dto.DocumentResponseDto;
import com.wish.dms_api.dto.DocumentUpdateDto;
import com.wish.dms_api.entity.Document;
//import com.wish.dms_api.entity.DocumentType;
//import com.wish.dms_api.entity.Document;
import com.wish.dms_api.service.IDocumentService;

@RestController
@RequestMapping("/api/doc")

public class DocumentController {
	
	@Autowired IDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<List<DocumentResponseDto>> store(
            @ModelAttribute DocumentRequestDto documentRequestDto,
            @RequestParam("files") List<MultipartFile> files) {
        
    	   if (files.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
    	
    	try {
            List<DocumentResponseDto> doc = documentService.uploadDocument(documentRequestDto);
            return new ResponseEntity<>(doc,HttpStatus.CREATED);
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Not found");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<DocumentResponseDto>> index(){
		List<DocumentResponseDto> docs= documentService.getAllDocs();
		return new ResponseEntity<>(docs,HttpStatus.OK);
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
    	documentService.deleteDocument(id);
		return new ResponseEntity<>("Document Deleted SuccessFully",HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DocumentResponseDto> update(@Validated DocumentUpdateDto documentUpdateDto, @PathVariable Long id){
    	try {
    		
    		DocumentResponseDto doc = documentService.updateDocument(documentUpdateDto, id);
    		return new ResponseEntity<>(doc, HttpStatus.CREATED);
    		
    	}catch(Exception ex) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    //get documents against a specific user
    @GetMapping("/getbyuser/{id}")
    public ResponseEntity<List<DocumentResponseDto>> getDocByUserId(@PathVariable Long id){
    	System.out.println("get all documents of user");
    	try {
    		List<DocumentResponseDto> docs= documentService.getDocByUserId(id);
    		return new ResponseEntity<>(docs,HttpStatus.ACCEPTED);
    	}catch(Exception ex) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    }
    
    //get documents against a specific tag
    @GetMapping("/getbytag/{tag}") 
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByTag(@PathVariable String tag) {
    	try {
    		
    		System.out.println(tag);
    		List<DocumentResponseDto> documents = documentService.getDocumentByTag(tag);
    		return new ResponseEntity<>(documents,HttpStatus.OK);
    	}catch(Exception ex) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @GetMapping("/getbytype/{id}")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentByDocumentType(@PathVariable Long id){
    	List<DocumentResponseDto> documents= documentService.getDocumentByDocumentType(id);
    	System.out.println(id);
    	return new ResponseEntity<>(documents,HttpStatus.OK);
    }
    
    @GetMapping("/by-date/{date}")
    public ResponseEntity<List<DocumentResponseDto>> getDocumentsByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
//        List<Document> documents = documentService.findByDate(date);
    	try {
		      System.out.println("hello"+ date);
		    List<DocumentResponseDto> documents = documentService.getDocumentByDate(date);
		    return new ResponseEntity<>(documents , HttpStatus.OK);
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    
  //get documents against a specific user
    @GetMapping("/getbytoken")
    public ResponseEntity<List<DocumentResponseDto>> getDocByUser(){
    	System.out.println("get all documents of user");
    	try {
    		List<DocumentResponseDto> docs= documentService.getDocumentByUser();
    		return new ResponseEntity<>(docs,HttpStatus.ACCEPTED);
    	}catch(Exception ex) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    }
}
	



 package com.wish.dms_api.controller;


 import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

import com.wish.dms_api.entity.Folder;
import com.wish.dms_api.service.IFolderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


 @RestController
 @CrossOrigin
 @RequestMapping("/folder")
 public class FolderController {

     @Autowired
     IFolderService folderService;
     
     @GetMapping("/files")
     public List<Folder> get() throws IOException{
    	 List<Folder> files = folderService.getAll();
		return files;
     }
     
     @PostMapping("/create")
     public String createFolder(@RequestBody Folder folder) throws IOException {
    	 folderService.addToFolder(folder);
		 return "Uploaded Successfully";
     }
     
     @DeleteMapping("/delete/{id}")
 	public String delete(@PathVariable String id) throws IOException {
 		long id1 = Long.parseLong(id);
		return folderService.delete(id1);

 	}

 	@GetMapping("/get/{id}")
 	public Optional<Folder> getById(@PathVariable String id) {
 		long id1 = Long.parseLong(id);
 		return folderService.getFolderById(id1);
 	}
 	
 	@GetMapping("/get/file/{id}")
 	public List<Folder> getFileByFolderId(@PathVariable String id)
 	{
 		long id1 = Long.parseLong(id);
 		return folderService.getFileByFolderId(id1);
 	}
 	
 	@GetMapping("/move/{id}")
 	public String moveToOtherFolder(@PathVariable Long id, @RequestBody Folder folder)
 	{
 		return folderService.moveToOtherFolder(folder.getDocument_id(), id, folder.getFolderId());
 	}
 	
    
 }

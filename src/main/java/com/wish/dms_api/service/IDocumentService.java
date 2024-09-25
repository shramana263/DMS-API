package com.wish.dms_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
//import java.io.IOException;
import java.util.List;

//import com.wish.dms_api.dto.DocumentByDocTypeDto;
import com.wish.dms_api.dto.DocumentByTagDto;
import com.wish.dms_api.dto.DocumentByUserDto;

//import org.springframework.web.multipart.MultipartFile;

import com.wish.dms_api.dto.DocumentRequestDto;
import com.wish.dms_api.dto.DocumentResponseDto;
import com.wish.dms_api.dto.DocumentUpdateDto;
//import com.wish.dms_api.entity.DocumentType;
//import com.wish.dms_api.entity.Document;

public interface IDocumentService {
	
	
//	 Document uploadDocument(MultipartFile file) throws IOException;
	DocumentResponseDto uploadDocument(DocumentRequestDto documentRequestDto);
	
	List<DocumentResponseDto> getAllDocs();
	
	void deleteDocument(Long id);
	
	DocumentResponseDto updateDocument(DocumentUpdateDto documentUpdateDto, Long id );
	
	List<DocumentResponseDto> getDocByUser(Long userId);
	
	List<DocumentResponseDto> getDocumentByTag(String tag);
	
	List<DocumentResponseDto> getDocumentByDocumentType(Long id);
	
	List<DocumentResponseDto> getDocumentByDate(LocalDate date);

//	List<DocumentResponseDto> getDocumentByDate(String date);
	
	
}

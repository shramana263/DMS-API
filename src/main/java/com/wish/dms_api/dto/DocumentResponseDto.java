package com.wish.dms_api.dto;

import java.util.Date;
import java.util.List;

import com.wish.dms_api.entity.Document;
import com.wish.dms_api.entity.DocumentTag;
import com.wish.dms_api.entity.DocumentType;
//import com.wish.dms_api.entity.DocumentType;
import com.wish.dms_api.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

//import jakarta.persistence.Column;

public class DocumentResponseDto {

	private long id;
	private String file_name;
	private String original_name;
	private String path;
	private String extension;
	private String mime_type;
//	private User user;
	private long user_id;
//	private DocumentType documentType;
	private String document_type;
	
}

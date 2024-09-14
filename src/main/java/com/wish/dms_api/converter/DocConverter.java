package com.wish.dms_api.converter;

import com.wish.dms_api.dto.DocumentByUserDto;
import com.wish.dms_api.entity.Document;

public class DocConverter {

	public DocumentByUserDto convertToDto(Document document) {
		DocumentByUserDto documentByUserDto= new DocumentByUserDto();
		documentByUserDto.setId(document.getId());
		documentByUserDto.setName(document.getFile_name());
		documentByUserDto.setFilePath(document.getPath());
		
		return documentByUserDto;
	}
}

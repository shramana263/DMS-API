package com.wish.dms_api.service.implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wish.dms_api.dto.DocTagDto;
import com.wish.dms_api.entity.DocumentTag;
import com.wish.dms_api.service.IDocTagService;

@Service
public class DocTagService implements IDocTagService{

	@Autowired ModelMapper mapper;
	@Override
	public String addTag(DocTagDto docTagDto) {
		
//		DocTag docTag= new DocTag();
		mapper.map(docTagDto, DocumentTag.class);
		return " Tag Successfully Created";
	}

	
	
}

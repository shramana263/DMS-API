package com.wish.dms_api.dto;

import java.util.List;

import com.wish.dms_api.entity.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DocumentByUserDto {

	private long id;
	private String name;
	private String filePath;
}

package com.wish.dms_api.dto;


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

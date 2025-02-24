package com.wish.dms_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentFolderDto {
	private long userId;
	private String original_name;
	private String file_name;
}

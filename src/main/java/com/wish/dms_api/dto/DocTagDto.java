package com.wish.dms_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocTagDto {

	private long id;
	private String name;
	private long doc_id;
}

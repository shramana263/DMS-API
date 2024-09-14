package com.wish.dms_api.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequestDto {
	
	@NotNull(message="No file is selected")
    private MultipartFile file;
	
	@NotNull(message="Atleast one tag must be given")
    private String tags;
//    private int user_id;
//    private String tag_name;
}

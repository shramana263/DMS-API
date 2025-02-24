package com.wish.dms_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "folder")
public class Folder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long document_id;
	@Column(name="folder_id")
	private long folderId;
	private long user_id;

}

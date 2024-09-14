package com.wish.dms_api.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;

//import com.wish.dms_api.security.UserSingleton;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="documents")
public class Document {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column
	private String file_name;
	@Column
	private String original_name;
	@Column
	private String path;
	@Column
	private String extension;
	@Column
	private String mime_type;
	@Column 
	private Date createdat;
	@Column
	private Date updated_at;

	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Transient
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocumentTag> tags=new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "document_type_id", referencedColumnName = "id")
	private DocumentType documentType;
	
}

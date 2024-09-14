//package com.wish.dms_api.entity;
//
//import java.util.Date;
//
//import org.hibernate.annotations.Immutable;
//import org.hibernate.annotations.Subselect;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Immutable
//@Table(name="document_view")
//public class DocumentView {
//
//	@Id
//	@Column(insertable=false)
//	private long id;
//	
//	@ManyToOne
//	@JoinColumn(name = "document_id", referencedColumnName = "id")
//	private Document document_id;
//	
//	@ManyToOne
//	@JoinColumn(name = "file_name", referencedColumnName = "file_name")
//	private Document filename;
//	
//	@ManyToOne
//	@JoinColumn(name = "original_name", referencedColumnName = "original_name")
//	private Document originalname;
//	
//	@ManyToOne
//	@JoinColumn(name = "path", referencedColumnName = "path")
//	private Document filepath;
//	
//	@ManyToOne
//	@JoinColumn(name = "extension", referencedColumnName = "extension")
//	private Document fileextension;
//	
//	@ManyToOne
//	@JoinColumn(name = "mime_type", referencedColumnName = "mime_type")
//	private Document file_mime_type;
//	
//	@ManyToOne
//	@JoinColumn(name = "tag_id", referencedColumnName = "id")
//	private DocumentTag tag_id;
//	
//	@ManyToOne
//	@JoinColumn(name = "tag_name", referencedColumnName = "name")
//	private DocumentTag tag_name;
//	
//	
//
//	
//
//	
//}

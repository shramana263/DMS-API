package com.wish.dms_api.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import com.wish.dms_api.dto.DocumentByDocTypeDto;
import com.wish.dms_api.entity.Document;
import com.wish.dms_api.entity.DocumentType;
//import com.wish.dms_api.entity.DocumentType;
import com.wish.dms_api.entity.User;

@Repository
public interface IDocumentRepository extends JpaRepository<Document, Long>{

//	Optional<User> findByUserId(Long userId);
	List<Document> findByUserId(Long userId);
	
//	@Query("SELECT d FROM Document d LEFT JOIN DocumentTag dt ON dt.document=d WHERE dt.name = :tagName AND d.user=:user")
//	List<Document> findByTags(@Param("tagName") String tagName, User user);

	@Query("SELECT d FROM Document d LEFT JOIN DocumentTag dt ON dt.document=d WHERE dt.name = :tagName AND d.user=:user")
	List<Document> findByTags(@Param("tagName") String tagName, User user);

	List<Document> findByDocumentType(DocumentType documentType);

//	List<Document> findByCreatedat(LocalDateTime localDateTime);
	@Query("SELECT d FROM Document d WHERE DATE(d.createdat) = :date AND d.user= :user")
	List<Document> findByCreatedat(@Param("date") LocalDate date, User user);

//	List<Document> findByDocumentType(Long id);

}

package com.wish.dms_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wish.dms_api.entity.DocumentType;

@Repository
public interface IDocumentTypeRepository extends JpaRepository<DocumentType, Long>{

//	char[] findById = null;

	DocumentType findByName(String name);

}

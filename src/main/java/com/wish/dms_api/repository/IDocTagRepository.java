package com.wish.dms_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wish.dms_api.entity.DocumentTag;

@Repository

public interface IDocTagRepository extends JpaRepository<DocumentTag, Long>{

}

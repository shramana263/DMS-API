package com.wish.dms_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wish.dms_api.entity.Folder;
@Repository
public interface IFolderRepository extends JpaRepository<Folder, Long>{
    List<Folder> findByFolderId(Long folderId);
}

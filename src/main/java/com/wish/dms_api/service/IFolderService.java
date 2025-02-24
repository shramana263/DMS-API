package com.wish.dms_api.service;

import java.util.List;
import java.util.Optional;

import com.wish.dms_api.entity.Folder;

public interface IFolderService {

    public Folder addToFolder(Folder folder);
	public List<Folder> getAll();
	public String delete(Long id);
	public Optional<Folder> getFolderById(Long id);
	public List<Folder> getFileByFolderId(Long id);
	public String moveToOtherFolder(Long docId,Long fromId, Long toId);

}

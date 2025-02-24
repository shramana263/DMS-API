package com.wish.dms_api.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wish.dms_api.entity.Document;
import com.wish.dms_api.entity.Folder;
import com.wish.dms_api.repository.IDocumentRepository;
import com.wish.dms_api.repository.IFolderRepository;
import com.wish.dms_api.service.IFolderService;

@Service
public class FolderService  implements IFolderService{

    @Autowired
    private IFolderRepository folderRepository;
    
    @Autowired
    private IDocumentRepository documentRepository;

    @Override
    public List<Folder> getAll(){
        return folderRepository.findAll();
    }

    @Override
    public Folder addToFolder(Folder folder){
        Folder fold= new Folder();
        fold.setDocument_id(folder.getDocument_id());
        fold.setFolderId(folder.getFolderId());
        fold.setUser_id(folder.getUser_id());
        folderRepository.save(fold);
        
        return fold;
    }

	@Override
	public String delete(Long id) {
		folderRepository.deleteById(id);
		return "Folder Deleted Successfully";
	}

	@Override
	public Optional<Folder> getFolderById(Long id) {
		return folderRepository.findById(id);
	}

	@Override
	public List<Folder> getFileByFolderId(Long id) {
		Document doc=documentRepository.findById(id).orElseThrow();
		List<Folder> folders= folderRepository.findByFolderId(doc.getId());
		
		return folders;
	}

	@Override
	public String moveToOtherFolder(Long docId, Long fromId, Long toId) {
		Folder fold= new Folder();
		fold.setDocument_id(docId);
		fold.setFolderId(toId);
		folderRepository.save(fold);
		folderRepository.deleteById(fromId);
		return "Move Completed";
	}
    
    

}

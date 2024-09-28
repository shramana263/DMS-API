package com.wish.dms_api.service.implement;

//import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.wish.dms_api.dto.DocumentByUserDto;
import com.wish.dms_api.dto.DocumentRequestDto;
import com.wish.dms_api.dto.DocumentResponseDto;
import com.wish.dms_api.dto.DocumentUpdateDto;
//import com.wish.dms_api.entity.DocumentType;
import com.wish.dms_api.entity.DocumentTag;
import com.wish.dms_api.entity.DocumentType;
import com.wish.dms_api.entity.Document;
import com.wish.dms_api.entity.User;
import com.wish.dms_api.repository.IDocTagRepository;
import com.wish.dms_api.repository.IDocumentRepository;
import com.wish.dms_api.repository.IDocumentTypeRepository;
//import com.wish.dms_api.repository.IDocumentTypeRepository;
import com.wish.dms_api.repository.IUserRepository;
import com.wish.dms_api.security.JwtService;
import com.wish.dms_api.service.IDocumentService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class DocumentService implements IDocumentService{

	@Autowired ModelMapper mapper;
	
	@Autowired IDocumentRepository documentRepository;
	@Autowired IUserRepository userRepository;
	
	@Autowired IDocTagRepository docTagRepository;
	@Autowired IDocumentTypeRepository documentTypeRepository;
	@Autowired JwtService jwtService;
	
//	@Autowired DocConverter docConverter;
    private final String uploadDir = "D:/springboot/dms-api/src/main/resources/static/documents/";
    
    @Value("${document.path}")
    private String DOCUMENT_PATH;


    @Value("${app.base-url}") // Assuming you have a base URL configured
    private String baseUrl;

	@Override
	public DocumentResponseDto uploadDocument(DocumentRequestDto documentRequestDto) {

		MultipartFile file= documentRequestDto.getFile();
		String[] tagNames= documentRequestDto.getTags().split(",");
//		System.out.println(file);
		if(file==null) {
			return null;
		}
		try {
			String time= String.valueOf(System.currentTimeMillis());
		Files.createDirectories(Paths.get(uploadDir));
		Path filePath= Paths.get(uploadDir+time+file.getOriginalFilename());
		Files.write(filePath, file.getBytes());
		
		Document document= new Document();
		document.setOriginal_name(file.getOriginalFilename());
		document.setPath(filePath.toString());
		document.setFile_name(time+file.getOriginalFilename());
		document.setExtension(getFileExtension(file.getOriginalFilename()));
		document.setMime_type(file.getContentType());
		document.setCreatedat(new Date());
		document.setUpdated_at(new Date());
//		System.out.println(getDocType(getFileExtension));
		DocumentType documentType= documentTypeRepository.findByName(getDocType(getFileExtension(file.getOriginalFilename())));
		document.setDocumentType(documentType);
//		document.setDocumentType(null);
//		System.out.println(documentTypeRepository.findByName(getDocType(getFileExtension(file.getOriginalFilename()))));
//		System.out.println(document.getDocType());
//		getDocType(getFileExtension(file.getOriginalFilename()));
//		UserSingleton customUserDetails = (UserSingleton)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
		document.setUser(user);		
			
		
		documentRepository.save(document);
		
		
		for(String tagName: tagNames) {
			DocumentTag tag= new DocumentTag();
			tag.setName(tagName.trim());
			tag.setDocument(document);
//			System.out.println(tag);
			docTagRepository.save(tag);
		}
		
		DocumentResponseDto documentResponseDto= mapper.map(document, DocumentResponseDto.class);
		documentResponseDto.setUser_id(document.getUser().getId());
		documentResponseDto.setDocument_type(document.getDocumentType().getName());
		documentResponseDto.setUrl(baseUrl);
		System.out.println(documentResponseDto);
		return documentResponseDto;
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}



	private String getDocType(String ext) {
//		System.out.println(ext);
		if(ext.equals("jpg")||ext.equals("jpeg")||ext.equals("png")||ext.equals("webp")||ext.equals("gif")||ext.equals("jfif")||ext.equals("svg")||ext.equals("bmp")) {
//			System.out.println("hello");
//			System.out.println(DocumentType.IMAGE);
			return "IMAGE";
		}
		else if(ext.equals("pdf")) {
			return "PDF";
		}
		else if(ext.equals("txt")) {
			return "TEXT";
		}
		else if(ext.equals("xlsx")||ext.equals("xls")) {
			return "EXCEL";
		}
		else if(ext.equals("csv")) {
			return "CSV";
		}
		else if(ext.equals("pptx")||ext.equals("ppt")) {
			return "POWERPOINT";
		}
		else if(ext.equals("zip")) {
			return "ZIP";
		}
		else if(ext.equals("doc")||ext.equals("docx")) {
			return "WORD";
		}
		else if(ext.equals("mp3")||ext.equals("wav")) {
			return "AUDIO";
		}
		else if(ext.equals("mp4")||ext.equals("avi")||ext=="mov") {
			return "VIDEO";
		}
		else if(ext.equals("xml")) {
			return "XML";
		}
		return "OTHER";
		
	}

	private String getFileExtension(String originalFilename) {


		if(originalFilename==null || originalFilename.lastIndexOf('.')==-1) {
			return null;
		}
		
		return originalFilename.substring(originalFilename.lastIndexOf('.')+1);
	}

	@Override
	public List<DocumentResponseDto> getAllDocs() {
		
		List<Document> documents= documentRepository.findAll();
		
		List<DocumentResponseDto> docs= documents.stream().map(doc->{
			DocumentResponseDto documentResponseDto= mapper.map(doc, DocumentResponseDto.class);
			return documentResponseDto;
		}).collect(Collectors.toList());
		return docs;
	}

	@Override
	public void deleteDocument(Long id) {
		documentRepository.deleteById(id);
		
	}

	@Override
	public DocumentResponseDto updateDocument(DocumentUpdateDto documentUpdateDto, Long id) {
		
		MultipartFile file= documentUpdateDto.getFile();
		if(file==null) {
			return null;
		}
		try {
			String time= String.valueOf(System.currentTimeMillis());
			Document document= documentRepository.findById(id).get();
			Files.createDirectories(Paths.get(uploadDir));
			Path filePath= Paths.get(uploadDir+time+file.getOriginalFilename());
			document.setOriginal_name(file.getOriginalFilename());
			document.setPath(filePath.toString());
			document.setFile_name(time+file.getOriginalFilename());
			document.setExtension(getFileExtension(file.getOriginalFilename()));
			document.setMime_type(file.getContentType());
			document.setCreatedat(new Date());
			document.setUpdated_at(new Date());
			DocumentType documentType= documentTypeRepository.findByName(getDocType(getFileExtension(file.getOriginalFilename())));
			document.setDocumentType(documentType);
			User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
			document.setUser(user);	
			
			documentRepository.save(document);
			DocumentResponseDto documentResponseDto= mapper.map(document, DocumentResponseDto.class);
			return documentResponseDto;
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}



	@Override
	 public List<DocumentResponseDto> getDocByUserId(Long userId) {
        List<Document> documents = documentRepository.findByUserId(userId);
        List<DocumentResponseDto> documentDTOs = new ArrayList<>();
        for (Document document : documents) {
        	DocumentResponseDto documentDTO = new DocumentResponseDto();
            documentDTO.setId(document.getId());
            documentDTO.setFile_name(document.getFile_name());
            documentDTO.setDocument_type(document.getDocumentType().getName());
            documentDTO.setExtension(document.getExtension());
            documentDTO.setMime_type(document.getMime_type());
            documentDTO.setOriginal_name(document.getOriginal_name());
            documentDTO.setPath(document.getPath());
            documentDTO.setUser_id(document.getUser().getId());
            documentDTO.setUrl(baseUrl);
//            documentDTO.setType(document.getDocType());
            documentDTOs.add(documentDTO);
        }
        return documentDTOs;
    }



	@Override
	public List<DocumentResponseDto> getDocumentByTag(String tag) {
		System.out.println("hello by tag");
//		
//		UserSingleton userSingleton = (UserSingleton)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		var user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
		
		
		HttpServletRequest request = ((ServletRequestAttributes) Objects
              .requireNonNull(RequestContextHolder.getRequestAttributes()))
              .getRequest();
      String token = request.getHeader("Authorization");

      if (token != null && token.startsWith("Bearer ")) {
          token = token.substring(7); // Remove "Bearer " from the token
      }

      String username = jwtService.extractUsername(token);
      User user = userRepository.findByUsername(username).orElseThrow();

		
		
		
		List<Document> documents=documentRepository.findByTags(tag, user);
		List<DocumentResponseDto> documentDtos= new ArrayList<>();
//		System.out.println("document" + documents);
		for(Document document: documents) {
			DocumentResponseDto res= new DocumentResponseDto();
			res.setId(document.getId());
			res.setFile_name(document.getFile_name());
			res.setOriginal_name(document.getOriginal_name());
			res.setPath(document.getPath());
			res.setMime_type(document.getMime_type());
			res.setExtension(document.getExtension());
			res.setUser_id(document.getUser().getId());
			res.setDocument_type(document.getDocumentType().getName());
			res.setUrl(baseUrl);
			
			documentDtos.add(res);
		}
		return documentDtos;
	}


	@Override
	public List<DocumentResponseDto> getDocumentByDocumentType(Long id) {

		DocumentType documentType= documentTypeRepository.findById(id).get();
		List<Document> documents= documentRepository.findByDocumentType(documentType);
		List<DocumentResponseDto> documentDtos= new ArrayList<>();
		for(Document document: documents) {
			DocumentResponseDto res= new DocumentResponseDto();
			res.setId(document.getId());
			res.setFile_name(document.getFile_name());
			res.setOriginal_name(document.getOriginal_name());
			res.setPath(document.getPath());
			res.setMime_type(document.getMime_type());
			res.setExtension(document.getExtension());
			res.setUser_id(document.getUser().getId());
			res.setDocument_type(document.getDocumentType().getName());
			res.setUrl(baseUrl);
			
			documentDtos.add(res);
		}
		return documentDtos;
	}



	@Override
	public List<DocumentResponseDto> getDocumentByDate(LocalDate date) {
		System.out.println("service"+ date);
//		List<Document> documents= documentRepository.findByCreatedat(date);
		
//		UserSingleton userSingleton = (UserSingleton)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
		
		List<Document> documents= documentRepository.findByCreatedat(date, user);
		
		List<DocumentResponseDto> documentDtos= new ArrayList<>();
		for(Document document: documents) {
//			System.out.println(document);
			DocumentResponseDto res= new DocumentResponseDto();
			res.setId(document.getId());
			res.setFile_name(document.getFile_name());
			res.setOriginal_name(document.getOriginal_name());
			res.setPath(document.getPath());
			res.setMime_type(document.getMime_type());
			res.setExtension(document.getExtension());
			res.setUser_id(document.getUser().getId());
//			res.setDocument_type(document.getDocumentType().getName());
			res.setDocument_type(document.getDocumentType().getName());
			res.setUrl(baseUrl);
			
			
			documentDtos.add(res);
		}
		return documentDtos;
	}



	@Override
	public List<DocumentResponseDto> getDocumentByUser() {
		
		User currentUserDetails= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user= userRepository.findById(currentUserDetails.getId()).orElseThrow();
		
		List<Document> documents = documentRepository.findByUserId(user.getId());
        List<DocumentResponseDto> documentDTOs = new ArrayList<>();
        for (Document document : documents) {
        	DocumentResponseDto documentDTO = new DocumentResponseDto();
            documentDTO.setId(document.getId());
            documentDTO.setFile_name(document.getFile_name());
            documentDTO.setDocument_type(document.getDocumentType().getName());
            documentDTO.setExtension(document.getExtension());
            documentDTO.setMime_type(document.getMime_type());
            documentDTO.setOriginal_name(document.getOriginal_name());
            documentDTO.setPath(document.getPath());
            documentDTO.setUser_id(document.getUser().getId());
            documentDTO.setUrl(baseUrl);
//            documentDTO.setType(document.getDocType());
            documentDTOs.add(documentDTO);
        }
        return documentDTOs;
	}
	
	
	
	
	
	

	
}

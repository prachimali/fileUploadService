package com.fileupload.service;

import org.springframework.web.multipart.MultipartFile;

import com.fileupload.model.FileMetadata;

public interface FileUploadService {
	FileMetadata uploadFile(MultipartFile file) throws Exception;
}

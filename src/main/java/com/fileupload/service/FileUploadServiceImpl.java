package com.fileupload.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileupload.model.FileMetadata;
import com.fileupload.repository.FileUploadRepository;
import com.fileupload.utility.ProcessingException;

@Service
@PropertySource("classpath:/paths.properties")
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private Environment environment;

	@Autowired
	private FileUploadRepository fileUploadRepository;

	@Override
	@Transactional
	public FileMetadata uploadFile(final MultipartFile file) throws Exception {
		final FileMetadata fileMetadata = new FileMetadata();
		final File destFile = Paths.get(environment.getProperty("destination.file.path"), file.getOriginalFilename()).toFile();
		fileMetadata.setFileName(file.getOriginalFilename());
		fileMetadata.setFileContentType(file.getContentType());
		fileMetadata.setFileSize(file.getSize());
		fileMetadata.setCreateDate(new Timestamp(System.currentTimeMillis()));

		try {
			file.transferTo(destFile);
		} catch (final IOException e) {
			throw new ProcessingException("IOException copying file", e);
		}

		return this.fileUploadRepository.save(fileMetadata);
	}
}

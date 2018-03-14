package com.fileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileupload.model.FileMetadata;

@Repository
public interface FileUploadRepository extends JpaRepository<FileMetadata, Integer> {
}

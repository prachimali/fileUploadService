package com.fileupload.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "file_details")
public class FileMetadata {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "file_id")
	private Integer fileId;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_size")
	private long fileSize;

	@Column(name = "file_content_type")
	private String fileContentType;

	@Column(name = "create_date")
	private Timestamp createDate;
}

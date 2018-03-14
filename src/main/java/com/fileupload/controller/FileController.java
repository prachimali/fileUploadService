package com.fileupload.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileupload.service.FileUploadService;
import com.fileupload.utility.APIResponse;

@RestController
@RequestMapping(value = "/file")
public class FileController {

	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping(method = RequestMethod.POST, value = "/upload",
			consumes = "multipart/form-data")
	public APIResponse uploadFile(@RequestParam("file") final MultipartFile file) {
		try {
			return new APIResponse(this.fileUploadService.uploadFile(file), null);
		} catch (final Exception e) {
			return new APIResponse(null, Arrays.asList(e.getMessage()));
		}
	}
}

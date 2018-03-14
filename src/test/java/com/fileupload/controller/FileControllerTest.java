package com.fileupload.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fileupload.model.FileMetadata;
import com.fileupload.service.FileUploadService;
import com.fileupload.utility.APIResponse;

public class FileControllerTest {

	@Test
	public void testControllerSuccessful() throws Exception {
		final TestContext testContext = new TestContext();

		when(testContext.fileUploadService.uploadFile(any(MultipartFile.class))).thenReturn(new FileMetadata());

		final APIResponse apiResponse = testContext.fileController
				.uploadFile(new MockMultipartFile("abc.txt", "This is a file".getBytes()));
		assertNotNull(apiResponse);
		assertNotNull(apiResponse.getObjectDetails());
		assertTrue(apiResponse.getObjectDetails().getClass().isAssignableFrom(FileMetadata.class));
		assertNull(apiResponse.getErrors());

		verify(testContext.fileUploadService, times(1)).uploadFile(any(MultipartFile.class));
	}

	@Test
	public void testControllerException() throws Exception {
		final TestContext mockContext = new TestContext();

		when(mockContext.fileUploadService.uploadFile(any(MultipartFile.class))).thenThrow(new Exception("Some Exception"));

		final APIResponse apiResponse = mockContext.fileController
				.uploadFile(new MockMultipartFile("abc.txt", "This is a file".getBytes()));
		assertNotNull(apiResponse);
		assertNull(apiResponse.getObjectDetails());
		assertNotNull(apiResponse.getErrors());
		assertTrue(apiResponse.getErrors().get(0).equals("Some Exception"));

		verify(mockContext.fileUploadService, times(1)).uploadFile(any(MultipartFile.class));
	}

	private class TestContext {

		@Mock
		private FileUploadService fileUploadService;

		@InjectMocks
		private FileController fileController;

		private TestContext() {
			initMocks(this);
		}
	}
}

package com.fileupload.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import com.fileupload.model.FileMetadata;
import com.fileupload.repository.FileUploadRepository;
import com.fileupload.utility.ProcessingException;

public class FileUploadServiceImplTest {

	@Test
	public void testFileUploadServiceImplSuccessful() throws Exception {
		final TestContext testContext = new TestContext();
		final MultipartFile mockMultipartFile = mock(MultipartFile.class);

		final FileMetadata fileMetadata1 = new FileMetadata();
		fileMetadata1.setFileName("abc.txt");
		fileMetadata1.setFileSize(1098L);
		fileMetadata1.setFileContentType("text");
		fileMetadata1.setFileId(2);
		fileMetadata1.setCreateDate(new Timestamp(System.currentTimeMillis()));

		when(testContext.environment.getProperty("destination.file.path")).thenReturn("C:\\Move_To_Other_Drive\\");
		when(mockMultipartFile.getOriginalFilename()).thenReturn("abc.txt");
		when(mockMultipartFile.getSize()).thenReturn(1098L);
		when(mockMultipartFile.getContentType()).thenReturn("text");

		doNothing().when(mockMultipartFile).transferTo(any(File.class));
		when(testContext.fileUploadRepository.save(any(FileMetadata.class))).thenReturn(fileMetadata1);

		final FileMetadata fileMetadata = testContext.fileUploadServiceImpl.uploadFile(mockMultipartFile);
		assertNotNull(fileMetadata);
		assertNotNull(fileMetadata.getFileId());
		assertNotNull(fileMetadata.getFileName());
		assertNotNull(fileMetadata.getFileSize());
		assertNotNull(fileMetadata.getFileContentType());
		assertNotNull(fileMetadata.getCreateDate());
		verify(testContext.fileUploadRepository, times(1)).save(any(FileMetadata.class));
	}

	@Test(expected = ProcessingException.class)
	public void testFileUploadServiceImplUnsuccessful() throws Exception {
		final TestContext testContext = new TestContext();
		final MultipartFile mockMultipartFile = mock(MultipartFile.class);

		when(testContext.environment.getProperty("destination.file.path")).thenReturn("C:\\Move_To_Other_Drive\\");
		when(mockMultipartFile.getOriginalFilename()).thenReturn("abc.txt");
		when(mockMultipartFile.getSize()).thenReturn(1098L);
		when(mockMultipartFile.getContentType()).thenReturn("text");

		doThrow(new IOException()).when(mockMultipartFile).transferTo(any(File.class));

		try {
			testContext.fileUploadServiceImpl.uploadFile(mockMultipartFile);
			fail("Expected a ProcessingExcepion");
		} finally {
			verifyZeroInteractions(testContext.fileUploadRepository);
		}
	}

	private class TestContext {

		@Mock
		private Environment environment;

		@Mock
		private FileUploadRepository fileUploadRepository;

		@InjectMocks
		private FileUploadServiceImpl fileUploadServiceImpl;

		private TestContext() {
			initMocks(this);
		}
	}
}

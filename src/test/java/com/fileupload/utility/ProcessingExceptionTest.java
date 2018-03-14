package com.fileupload.utility;

import java.io.IOException;

import org.junit.Test;

public class ProcessingExceptionTest {

	@Test(expected = ProcessingException.class)
	public void ProcessingExceptionMessageTest() throws ProcessingException {
		throw new ProcessingException("Exception Test");
	}

	@Test(expected = ProcessingException.class)
	public void ProcessingExceptionMessageCosntructorTest() throws ProcessingException {
		throw new ProcessingException("Exception Test", new IOException("File not found"));
	}
}

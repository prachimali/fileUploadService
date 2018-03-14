package com.fileupload;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import com.fileupload.utility.APIResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FileUploadFunctionalTest {

	/**
     * The ReST template.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testFunction1() throws Exception {
    	final File file = Files.createTempFile("fileupload", "test.txt").toFile();
    	FileWriter writer = new FileWriter(file);
    	writer.write("This is a sample file");
    	writer.close();

        final LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("file", new FileSystemResource(file));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        final HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        final ResponseEntity<APIResponse> body = this.testRestTemplate.postForEntity("/file/upload",
        		entity, APIResponse.class);

        assertEquals(200, body.getStatusCodeValue());
        final APIResponse apiResponse = body.getBody();
        final Map<String, String> responseMap = (Map<String, String>) apiResponse.getObjectDetails();
        assertEquals(file.getName(), responseMap.get("fileName"));
        assertEquals(String.valueOf(file.length()), String.valueOf(responseMap.get("fileSize")));
    }

}

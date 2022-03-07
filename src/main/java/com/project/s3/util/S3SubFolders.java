package com.project.s3.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3SubFolders {

	public void createSubFolders(String userName) {
		String com = "src/com/";

		AWSCredentials credentials = new BasicAWSCredentials("AKIA5AJGT243IQUAMZOB",
				"gSKTahUd7nzYz92gv2jwAGOGglKBaV4v1dGXTVgr");
		AmazonS3 client = new AmazonS3Client(credentials);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		String userDict = "springcrudgenerator/" + userName;

		client.putObject(new PutObjectRequest(userDict, com + "dao" + "/", emptyContent, metadata));
		client.putObject(new PutObjectRequest(userDict, com + "vo" + "/", emptyContent, metadata));
		client.putObject(new PutObjectRequest(userDict, com + "controller" + "/", emptyContent, metadata));
		client.putObject(new PutObjectRequest(userDict, "WebContent" + "/", emptyContent, metadata));
	}
}

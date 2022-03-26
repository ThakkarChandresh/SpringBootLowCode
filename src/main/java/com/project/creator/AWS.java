package com.project.creator;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

public class AWS {
	public static void createFile() {
		Regions clientRegion = Regions.US_EAST_1;
		String bucketName = "userprojects";
		String stringObjKeyName = "project/src/main/java/com/project/dao/CountryDAO.java";

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("public class myClass{\n}");

			// Upload a text string as a new object.
			s3Client.putObject(bucketName, stringObjKeyName, stringBuilder.toString());
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	public static void readFile() {
		Regions clientRegion = Regions.US_EAST_1;
		String bucketName = "baseassets";
		String objKeyName = "jsp/header.jsp";

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

			S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, objKeyName));
			InputStream objectData = object.getObjectContent();
			System.out.println(IOUtils.toString(objectData));
			objectData.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	public static void copyfile() {
		Regions clientRegion = Regions.US_EAST_1;
		String sourceBucketName = "baseassets";
		String sourceObjKeyName = "jsp/menu.jsp";
		String destinationBucketName = "userprojects";
		String destinationObjKeyName = "jsp/demo/menu.jsp";

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();

			s3Client.copyObject(sourceBucketName, sourceObjKeyName, destinationBucketName, destinationObjKeyName);

		} catch (AmazonServiceException e) {
			e.printStackTrace();
		} catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		copyfile();
	}
}

package com.project.s3.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.project.enums.ConstantEnum;

@Component
public class ObjectUtil {
	private static final Logger LOGGER = LogManager.getLogger(ObjectUtil.class);

	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final String BUCKET_NAME = "userprojects";
	private static final AmazonS3 S3 = AmazonS3ClientBuilder.standard().withRegion(CLIENT_REGION).build();

	public List<String> getObject(String prefix) {
		List<String> list = new ArrayList<String>();
		try {
			ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(BUCKET_NAME).withPrefix(prefix);

			ListObjectsV2Result result = S3.listObjectsV2(req);

			List<S3ObjectSummary> objects = result.getObjectSummaries();

			for (S3ObjectSummary os : objects) {
				list.add(os.getKey().split(prefix)[1]);
			}
		} catch (Exception e) {
			LOGGER.error(ConstantEnum.EXCEPTION_MESSAGE, e);
		}
		return list;
	}
}

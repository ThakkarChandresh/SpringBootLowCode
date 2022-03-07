package com.project.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.project.enums.ConstantEnum;

@Component
public class Aws {
	private static final Logger LOGGER = LogManager.getLogger(Aws.class);

	public static void getFolderSize(String username, String role) {
		try {
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
					.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIA5AJGT243ELCUZYUS",
							"WcDMhiizpL8p3c2xLoY6BeanpeAAZqUecheWiCIf")))
					.build();

			List<String> ans = new ArrayList<String>();

			if (role.equals("ROLE_ADMIN")) {
				ListObjectsV2Request req = new ListObjectsV2Request().withBucketName("springcrudgenerator")
						.withPrefix("").withDelimiter("/");

				ListObjectsV2Result result = s3.listObjectsV2(req);

				List<String> s = result.getCommonPrefixes();
				for (String s1 : s) {
					ans.add(s1.replace("/", "") + "..." + convertSize(getObjectSize(s1, s3)));
				}
			} else if (role.equals("ROLE_USER")) {
				ans.add(username + "..." + convertSize(getObjectSize(username + "/", s3)));
			}

			for (String a : ans) {
				LOGGER.debug(a);
			}

		} catch (Exception e) {
			LOGGER.error(ConstantEnum.EXCEPTION_MESSAGE, e);
		}
	}

	public static Long getObjectSize(String username, AmazonS3 s3) {
		Long totalSize = 0L;

		try {
			ListObjectsV2Request req = new ListObjectsV2Request().withBucketName("springcrudgenerator")
					.withPrefix(username);

			ListObjectsV2Result result = s3.listObjectsV2(req);

			List<S3ObjectSummary> objects = result.getObjectSummaries();
			for (S3ObjectSummary os : objects) {
				totalSize += os.getSize();
			}
		} catch (Exception e) {
			LOGGER.error(ConstantEnum.EXCEPTION_MESSAGE, e);
		}
		return totalSize;
	}

	public static String convertSize(Long size) {
		double kb = (size / 1024.0);
		double mb = (kb / 1024.0);
		double gb = (mb / 1024.0);

		DecimalFormat dec = new DecimalFormat("0.00");

		if (gb > 1) {
			return dec.format(gb).concat("GB");
		} else if (mb > 1) {
			return dec.format(mb).concat("MB");
		} else {
			return dec.format(size).concat("KB");
		}
	}

	public static void main(String[] args) {
		Aws.getFolderSize("anishkuldeepjain@gmail.com/src", "ROLE_USER");
	}
}

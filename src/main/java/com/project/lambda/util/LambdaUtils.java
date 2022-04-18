package com.project.lambda.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.google.gson.Gson;

@Component
public class LambdaUtils {
	private static final Regions CLIENT_REGION = Regions.US_EAST_1;
	private static final AWSLambda LAMBDA = AWSLambdaClientBuilder.standard().withRegion(CLIENT_REGION).build();

	public String invokeLmabda(List<String> files, String userName, String projectName, String type) {
		Map<String, Object> mp = new HashMap<String, Object>();

		mp.put("type", type);
		mp.put("files", files);
		mp.put("username", userName);
		mp.put("projectname", projectName);

		String payload = new Gson().toJson(mp);

		// ARN
		String functionName = "arn:aws:lambda:us-east-1:893971060534:function:demoSpringFun";

		InvokeRequest lmbRequest = new InvokeRequest().withFunctionName(functionName);
		lmbRequest.setPayload(payload);

		lmbRequest.setInvocationType(InvocationType.RequestResponse);

		InvokeResult lmbResult = LAMBDA.invoke(lmbRequest);

		return new String(lmbResult.getPayload().array(), Charset.forName("UTF-8"));
	}
}

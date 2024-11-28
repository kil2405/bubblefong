package com.bubbleShooter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {

	@Value("${bubblefong.aws.access_key_id}")
	private String awsId;

	@Value("${bubblefong.aws.secret_access_key}")
	private String awsKey;
	
	@Value("${bubblefong.s3.region}")
	private String region;	
	
	@Value("${bubblefong.aws.s3_url}")
	private String replayUrl;
	
	@Bean
	public AmazonS3 s3client() {
		
		AmazonS3 s3Client = null;
		AWSCredentials credentials = new BasicAWSCredentials(awsId, awsKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");
        
        s3Client = AmazonS3ClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(replayUrl, region))
				.withPathStyleAccessEnabled(true).withClientConfiguration(clientConfiguration)
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        
        System.out.println("S3 Config Load Complete");
        
        return s3Client;
	}
}

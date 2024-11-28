package com.bubbleShooter.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bubbleShooter.common.bubbleFilter;

@Service
public class S3Service {
	private final static Logger LOGGER = LoggerFactory.getLogger(bubbleFilter.class);
	
	@Autowired
	private AmazonS3 s3client;
	
	@Value("${bubblefong.aws.access_key_id}")
	private String awsId;

	@Value("${bubblefong.aws.secret_access_key}")
	private String awsKey;
	
	@Value("${bubblefong.aws.s3_url}")
	private String downloadUrl;
	
	@Value("${bubblefong.s3.bucket}")
	private String bucketName;
	
	public boolean uploadFile(String keyName, byte[] content)
	{
		try (InputStream is = new ByteArrayInputStream(content)) {
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(content.length);

			AccessControlList acl = new AccessControlList();
			acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

			s3client.putObject(new PutObjectRequest(bucketName, keyName, is, objectMetadata).withAccessControlList(acl));
		} catch (AmazonServiceException ase) {
			LOGGER.error("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			LOGGER.error("Error Message:    " + ase.getMessage());
			LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
			LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
			LOGGER.error("Error Type:       " + ase.getErrorType());
			LOGGER.error("Request ID:       " + ase.getRequestId());
			return false;
        } catch (AmazonClientException ace) {
        	LOGGER.error("Caught an AmazonClientException: ");
        	LOGGER.error("Error Message: " + ace.getMessage());
        	return false;
        } catch (IOException ioe) {
        	LOGGER.error("IOE Error Message: " + ioe.getMessage());
        	return false;
        }
		
		return true;
	}
	
	public boolean checkFile(String keyName)
	{
		return s3client.doesObjectExist(bucketName, keyName);
	}
	
	public String getDownLoadUrl(String keyName)
	{
		return downloadUrl + "notice/" + keyName;
	}
}

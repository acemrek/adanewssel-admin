package com.ac.newsadmin.service.impl;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.AccessControlList;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.MultipleDeleteResult;
import org.jets3t.service.model.MultipleDeleteResult.ErrorResult;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.StorageObject;
import org.jets3t.service.security.AWSCredentials;
import org.jets3t.service.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.ac.common.utility.CommonUtils;
import com.ac.newsadmin.AmazonConstants;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.transfer.VideoBinary;
import com.ac.newsadmin.model.transfer.WillBeStoredFileItem;
import com.ac.newsadmin.service.BinaryStorageService;

@SuppressWarnings("serial")
@Service
public class BinaryStorageS3ServiceImpl implements BinaryStorageService, InitializingBean, Serializable{

	protected Logger logger = LoggerFactory.getLogger(BinaryStorageS3ServiceImpl.class);
	
	@Resource(name="awsProperties") private Properties awsProperties;
	
	private S3Service s3Service = null;
	private static String s3BucketName="newsportal";
	private S3Bucket bucket = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		String awsAccessKey = awsProperties.getProperty(AmazonConstants.PROP_KEY_ACCESS_KEY);
		String awsSecretKey = awsProperties.getProperty(AmazonConstants.PROP_KEY_SECRET_KEY);
		
		s3Service = new RestS3Service(new AWSCredentials(awsAccessKey, awsSecretKey));
	}
	
	public void storeBinary(Site site, WillBeStoredFileItem willBeStoredFileItem) throws Exception{
		S3Object obj = new S3Object();
		ByteArrayInputStream stream = new ByteArrayInputStream(willBeStoredFileItem.getData());
		
		byte[] md5Hash = ServiceUtils.computeMD5Hash(stream);
		stream.reset(); 
		obj.setMd5Hash(md5Hash);   
		
		obj.setDataInputStream(stream);
		obj.setContentType(willBeStoredFileItem.getContentType());
		
		obj.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ); 
		
		obj.setKey(getObjectKey(site, willBeStoredFileItem.getFullStorePath()));
	
		obj = s3Service.putObject(getBucket(), obj);
	}
	
	public void deleteAllPhotoAlbum(Site site, List<Photo> photos, String albumPath) throws ServiceException{
		List<String> photoBinaryKeys = new ArrayList<String>();
		for(Photo photo : photos){
			photoBinaryKeys.addAll(getBinaryKeysOfPhoto(site, photo));
		}
		doMultipleDelete(photoBinaryKeys);
		
		String albumBinaryKey = getObjectKey(site, albumPath);
		deleteSingleObject(albumBinaryKey);
	}
	
	public void deleteColumnist(Site site, Columnist columnist) throws ServiceException{
		if(columnist.getPhoto().getLargePath().contains("default") == false){
			List<String> photoBinaryKeys = getBinaryKeysOfPhoto(site, columnist.getPhoto());
			doMultipleDelete(photoBinaryKeys);
		}

		deleteSingleObject(getObjectKey(site, "columnists/" + columnist.getId() + "/" ));
	}
	
	public void deletePhotoAssets(Site site, Photo photo) throws ServiceException{
		doMultipleDelete(getBinaryKeysOfPhoto(site, photo));
	}
	
	private List<String> getBinaryKeysOfPhoto(Site site, Photo photo){
		List<String> photoBinaryKeys = new ArrayList<String>();

		CommonUtils.addIfNotNull(photoBinaryKeys, getObjectKey(site, photo.getOriginalPath()));
		CommonUtils.addIfNotNull(photoBinaryKeys, getObjectKey(site, photo.getLargePath()));
		CommonUtils.addIfNotNull(photoBinaryKeys, getObjectKey(site, photo.getMediumPath()));
		CommonUtils.addIfNotNull(photoBinaryKeys, getObjectKey(site, photo.getSmallPath()));
		CommonUtils.addIfNotNull(photoBinaryKeys, getObjectKey(site, photo.getThumbnailPath()));
		
		return photoBinaryKeys;
	}
	
	private void deleteSingleObject(String objectKey) throws ServiceException{
		s3Service.deleteObject(s3BucketName, objectKey);
	}
	
	private void doMultipleDelete(List<String> objKeys) throws ServiceException{
		MultipleDeleteResult multipleDeleteResult = s3Service.deleteMultipleObjects(s3BucketName, objKeys.toArray(new String[objKeys.size()]));
		if(multipleDeleteResult.hasErrors()){
			List <ErrorResult> errors =  multipleDeleteResult.getErrorResults();
			for(ErrorResult errorResult : errors){
				logger.error("error while multiple delete code:[{}], key:[{}], msg[{}]", new Object[]{errorResult.getErrorCode(), errorResult.getKey(), errorResult.getMessage()});
			}
			throw new ServiceException();
		}		
	}
	
	public void moveObject(Site site, String oldKey, String newKey) throws ServiceException{
		StorageObject object = new StorageObject(getObjectKey(site, newKey));
		object.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
		s3Service.moveObject(s3BucketName, getObjectKey(site, oldKey), s3BucketName, object, false);
	}
	
	public VideoBinary moveVideo(Site site, VideoBinary video, String underPath) throws ServiceException{
		String newMp4Path = underPath + video.getMp4Path().substring(video.getMp4Path().lastIndexOf('/') + 1);
		this.moveObject(site, video.getMp4Path(), newMp4Path);
		video.setMp4Path(newMp4Path);
		
		String newOggTheoraPath = underPath + video.getOggTheoraPath().substring(video.getOggTheoraPath().lastIndexOf('/') + 1);
		this.moveObject(site, video.getOggTheoraPath(), newOggTheoraPath);
		video.setOggTheoraPath(newOggTheoraPath);
		
		String newWebmPath = underPath + video.getWebMPath().substring(video.getWebMPath().lastIndexOf('/') + 1);
		this.moveObject(site, video.getWebMPath(), newWebmPath);
		video.setWebMPath(newWebmPath);
		 
		movePhoto(site, video.getVideoThumbnail(), underPath + "thumbnail/");
		
		return video;
	}
	
	public Photo movePhoto(Site site, Photo photo, String underPath) throws ServiceException{

		String newOrgPath = underPath + photo.getOriginalPath().substring(photo.getOriginalPath().lastIndexOf('/') + 1);
		this.moveObject(site, photo.getOriginalPath(), newOrgPath);
		photo.setOriginalPath(newOrgPath);

		String newLargePath = underPath + photo.getLargePath().substring(photo.getLargePath().lastIndexOf('/') + 1);
		this.moveObject(site, photo.getLargePath(), newLargePath);
		photo.setLargePath(newLargePath);

		String newMediumPath = underPath + photo.getMediumPath().substring(photo.getMediumPath().lastIndexOf('/') + 1);
		this.moveObject(site, photo.getMediumPath(), newMediumPath);
		photo.setMediumPath(newMediumPath);

		String newSmallPath = underPath + photo.getSmallPath().substring(photo.getSmallPath().lastIndexOf('/') + 1);
		this.moveObject(site, photo.getSmallPath(), newSmallPath);
		photo.setSmallPath(newSmallPath);

		String newThumbPath = underPath + photo.getThumbnailPath().substring(photo.getThumbnailPath().lastIndexOf('/') + 1);
		this.moveObject(site, photo.getThumbnailPath(), newThumbPath);
		photo.setThumbnailPath(newThumbPath);

		return photo;
	}
	
	private S3Bucket getBucket() throws Exception{
		if(bucket == null){
			bucket = s3Service.getBucket(s3BucketName);
		}
		if(bucket == null){
			bucket = s3Service.createBucket(s3BucketName);
		}
		
		return bucket;
	}
	
	private String getObjectKey(Site site, String relativeKey){
		if(StringUtils.isBlank(relativeKey)){
			return null;
		}
		return site.getDomainName() + "/" + relativeKey;
	}
}

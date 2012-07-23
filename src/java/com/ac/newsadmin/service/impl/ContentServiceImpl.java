package com.ac.newsadmin.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jets3t.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ac.newsadmin.dao.ContentDao;
import com.ac.newsadmin.enums.PublishStatus;
import com.ac.newsadmin.model.entity.Audio;
import com.ac.newsadmin.model.entity.Category;
import com.ac.newsadmin.model.entity.Column;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.GaleryPhoto;
import com.ac.newsadmin.model.entity.HeadLine;
import com.ac.newsadmin.model.entity.News;
import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.PhotoGalery;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.entity.Video;
import com.ac.newsadmin.service.BinaryStorageService;
import com.ac.newsadmin.service.ContentService;

@SuppressWarnings("serial")
@Service
public class ContentServiceImpl implements ContentService, Serializable{

	protected Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
	
	@Resource private ContentDao contentDao;
	@Resource private BinaryStorageService binaryStorageService;
	
	public Photo getPhotoById(Long photoId){
		return contentDao.findPhotoById(photoId);
	}
	
	@Transactional
	public void savePhoto(Photo photo){
		contentDao.savePhoto(photo);
	}
	
	@Transactional
	public void updatePhoto(Photo photo){
		contentDao.updatePhoto(photo);
	}
	
	@Transactional
	public void mergePhoto(Photo photo){
		contentDao.mergePhoto(photo);
	}
	
	@Transactional
	public void saveVideo(Site site, Video video){
		video.setSite(site);
		video.setCreateDate(new Date());
		contentDao.saveVideo(video);
	}
	
	@Transactional
	public void updateVideo(Video video){
		contentDao.updateVideo(video);
	}
	
	@Transactional
	public void mergeVideo(Video video){
		contentDao.mergeVideo(video);
	}
	
	@Transactional
	public void deleteVideo(Video video){
		contentDao.deleteVideo(video);
	}

	public Video getVideoById(long id) {
		return contentDao.findVideoById(id);
	}	
	
	public List<Video> getVidepoBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		return contentDao.findVidepoBySiteAndPublishStatus(site, publishStatus);
	}
	
	public List<Video> getVideoBySite(Site site){
		return contentDao.findVideoBySite(site);		
	}

	@Transactional
	public void saveAudio(Site site, Audio audio){
		audio.setSite(site);
		audio.setCreateDate(new Date());
		contentDao.saveAudio(audio);
	}
	
	@Transactional
	public void updateVideo(Audio audio){
		contentDao.updateAudio(audio);
	}
	
	@Transactional
	public void mergeAudio(Audio audio){
		contentDao.mergeAudio(audio);
	}
	
	@Transactional
	public void deleteAudio(Audio audio){
		contentDao.deleteAudio(audio);
	}

	public Audio getAudioById(long id) {
		return contentDao.findAudioById(id);
	}	
	
	public List<Audio> getAudioBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		return contentDao.findAudioBySiteAndPublishStatus(site, publishStatus);
	}
	
	public List<Audio> getAudioBySite(Site site){
		return contentDao.findAudioBySite(site);		
	}
	
	public Column getColumnById(long columnId){
		return contentDao.findColumnById(columnId);
	}
	
	@Transactional
	public void saveColumn(Column column, Site site){
		column.setSite(site);
		column.setCreateDate(new Date());
		contentDao.saveColumn(column);
	}
	
	@Transactional
	public void updateColumn(Column column){
		contentDao.updateColumn(column);
	}
	
	@Transactional
	public void mergeColumn(Column column){
		contentDao.mergeColumn(column);
	}
	
	public List<Column> getColumnsByColumnist(Columnist columnist){
		return contentDao.findColumnsByColumnist(columnist);
	}

	@Override
	public void deleteColumn(Column column) {
		contentDao.deleteColumn(column);
	}
	
	@Override
	public void deleteColumnByColumnist(Columnist columnist) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Column> getActiveColumns(Site site){
		return contentDao.findColumnsBySiteAndPublishStatus(site, PublishStatus.ON_THE_AIR);
	}
	
	@Transactional
	public void orderActiveColumns(List<Column> activeColumns){
		int i = 0;
		for(Column column : activeColumns){
			if(column.getListOrder() != i){
				column.setListOrder(i);
				this.updateColumn(column);
			}
			i++;
		}
	}
	
	@Transactional
	public void addCategory(String name, String i18nKey, Site site){
		Category category = new Category();
		category.setName(name);
		category.setI18nKey(i18nKey);
		category.setSite(site);
		contentDao.saveNewCategory(category);
	}
	
	public Category getCategoryById(long categoryId){
		return contentDao.findCategoryById(categoryId);
	}
	
	public Category getCategoryByName(String name, Site site){
		return contentDao.findCategoryByName(name, site);
	}
	
	public List<Category> getAllCategoriesBySite(Site site){
		return contentDao.findAllCategoriesBySite(site);
	}
	
	@Transactional
	public void addNewPhotoGalery(PhotoGalery photoGalery, Site site){
		photoGalery.setSite(site);
		contentDao.saveNewPhotoGalery(photoGalery);
	}

	@Transactional
	public void updatePhotoGalery(PhotoGalery photoGalery) {
		contentDao.updatePhotoGalery(photoGalery);
		
	}
	
	@Transactional
	public void mergePhotoGalery(PhotoGalery photoGalery) {
		contentDao.mergePhotoGalery(photoGalery);
	}

	@Transactional
	public void orderGaleryPhotos(List<GaleryPhoto> galeryPhotos){
		int i = 0;
		for(GaleryPhoto galeryPhoto : galeryPhotos){
			if(galeryPhoto.getListOrder() != i){
				galeryPhoto.setListOrder(i);
				this.updateGaleryPhoto(galeryPhoto);
			}
			i++;
		}
	}
	
	public PhotoGalery getPhotoGaleryById(long photoGaleryId){
		return contentDao.findPhotoGaleryById(photoGaleryId);
	}
	
	@Transactional
	public void deletePhoto(Photo photo){
		contentDao.deletePhoto(photo);
	}
	
	@Transactional
	public void removePhotoGalery(PhotoGalery photoGalery){
		contentDao.deletePhotoGalery(photoGalery);
	}
	
	public List<PhotoGalery> getPhotoGaleryBySite(Site site){
		return contentDao.findPhotoGaleryBySite(site);
	}
	
	public List<PhotoGalery> getPhotoGaleryBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		return contentDao.findPhotoGaleryBySiteAndPublishStatus(site, publishStatus);
	}
	
	@Transactional
	public void addNewGaleryPhoto(GaleryPhoto galeryPhoto){
		contentDao.saveNewGaleryPhoto(galeryPhoto);
	}
	
	@Transactional
	public void updateGaleryPhoto(GaleryPhoto galeryPhoto){
		contentDao.updateGaleryPhoto(galeryPhoto);
	}

	@Transactional
	public void mergeGaleryPhoto(GaleryPhoto galeryPhoto){
		contentDao.mergeGaleryPhoto(galeryPhoto);
	}
	
	public GaleryPhoto getGaleryPhotoById(long galeryPhotoId){
		return contentDao.findGaleryPhotoById(galeryPhotoId);
	}
	
	@Transactional
	public void removeGaleryPhoto(GaleryPhoto galeryPhoto){
		contentDao.deleteGaleryPhoto(galeryPhoto);
	}
	
	@Transactional
	public boolean deleteGaleryPhoto(Site site, PhotoGalery photoGalery, GaleryPhoto galeryPhoto){
		try {
			Photo photo = galeryPhoto.getPhoto();
			
			binaryStorageService.deletePhotoAssets(site, photo);
			contentDao.deletePhoto(photo);
			
			photoGalery.getPhotos().remove(galeryPhoto);
			contentDao.updatePhotoGalery(photoGalery);
			
			contentDao.deleteGaleryPhoto(galeryPhoto);
			return true;
		} 
		catch (ServiceException e) {
			logger.error("deleting galery photo error siteId:[{}], photoGaleryId:[{}], galeryPhotoId:[{}]", new Object[]{site.getId(), photoGalery.getId(), galeryPhoto.getId()});
			return false;
		}
	}

	@Transactional
	public boolean deletePhotoGalery(Site site, PhotoGalery photoGalery){
		try {
			//to reattach to hibernate session
			//contentDao.updatePhotoGalery(photoGalery);
					
			List<Photo> photos = new ArrayList<Photo>();
			
			List<GaleryPhoto> galeryPhotos = photoGalery.getPhotos();
			for(GaleryPhoto galeryPhoto : galeryPhotos){
				photos.add(galeryPhoto.getPhoto());
			}
			
			String albumPath = "photoGalery/" + photoGalery.getId() + "/";
			binaryStorageService.deleteAllPhotoAlbum(site, photos, albumPath);
			
			contentDao.deletePhotoGalery(photoGalery);
			contentDao.deleteGaleryPhotos(photoGalery.getPhotos());
			contentDao.deletePhotos(photos);
			
			return true;
		}
		catch (ServiceException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean deleteColumnPhoto(Column column, Photo willBeDeletedPhoto, Site site){
		try{
			binaryStorageService.deletePhotoAssets(site, willBeDeletedPhoto);
			
			column.getPhotos().remove(willBeDeletedPhoto);
			this.mergeColumn(column);
			
			this.mergePhoto(willBeDeletedPhoto);
			this.deletePhoto(willBeDeletedPhoto);
			
			return true;
		}
		catch(ServiceException e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public void addNews(News news){
		contentDao.saveNews(news);
	}

	@Transactional
	public void updateNews(News news){
		contentDao.updateNews(news);
	}
	
	@Transactional
	public void deleteNews(News news){
		contentDao.deleteNews(news);
	}
	
	public News getNewsById(long newsId){
		return contentDao.findNewsById(newsId);
	}
	
	public List<News> getNewsBySite(Site site){
		return contentDao.findNewsBySite(site);		
	}
	
	@Transactional
	public void addHeadLine(HeadLine headLine){
		contentDao.saveHeadLine(headLine);
	}
	
	@Transactional
	public void updateHeadLine(HeadLine headLine){
		contentDao.updateHeadLine(headLine);
	}
	
	@Transactional
	public void deleteHeadLine(HeadLine headLine){
		contentDao.deleteHeadLine(headLine);
	}
	
	
	public HeadLine getHeadLineById(long headLineId){
		return contentDao.findHeadLineById(headLineId);
	}
	
	public List<HeadLine> getHeadLinesBySite(Site site){
		return contentDao.findHeadLinesBySite(site);		
	}


}

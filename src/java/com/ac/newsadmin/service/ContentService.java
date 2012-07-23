package com.ac.newsadmin.service;

import java.util.List;

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

public interface ContentService {
	
	Photo getPhotoById(Long photoId);
	void savePhoto(Photo photo);
	void updatePhoto(Photo photo);
	void deletePhoto(Photo photo);
	
	void saveVideo(Site site, Video video);
	void updateVideo(Video video);
	void mergeVideo(Video video);
	void deleteVideo(Video video);
	Video getVideoById(long id);
	List<Video> getVidepoBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	List<Video> getVideoBySite(Site site);
	
	void saveAudio(Site site, Audio audio);
	void updateVideo(Audio audio);
	void mergeAudio(Audio audio);
	void deleteAudio(Audio audio);
	Audio getAudioById(long id);
	List<Audio> getAudioBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	List<Audio> getAudioBySite(Site site);
	
	Column getColumnById(long columnId);
	void saveColumn(Column column, Site site);
	void updateColumn(Column column);
	void mergeColumn(Column column);
	void deleteColumn(Column column);
	List<Column> getColumnsByColumnist(Columnist columnist);
	List<Column> getActiveColumns(Site site);
	void orderActiveColumns(List<Column> activeColumns);
	void deleteColumnByColumnist(Columnist columnist);
	
	void addCategory(String name, String i18nKey, Site site);
	Category getCategoryByName(String name, Site site);
	Category getCategoryById(long categoryId);
	List<Category> getAllCategoriesBySite(Site site);
	
	void addNewPhotoGalery(PhotoGalery photoGalery, Site site);
	void updatePhotoGalery(PhotoGalery photoGalery);
	void mergePhotoGalery(PhotoGalery photoGalery);
	PhotoGalery getPhotoGaleryById(long photoGaleryId);
	void removePhotoGalery(PhotoGalery photoGalery);
	List<PhotoGalery> getPhotoGaleryBySite(Site site);
	List<PhotoGalery> getPhotoGaleryBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	void orderGaleryPhotos(List<GaleryPhoto> galeryPhotos);
	
	void addNewGaleryPhoto(GaleryPhoto galeryPhoto);
	void updateGaleryPhoto(GaleryPhoto galeryPhoto);
	void mergeGaleryPhoto(GaleryPhoto galeryPhoto);
	GaleryPhoto getGaleryPhotoById(long galeryPhotoId);
	void removeGaleryPhoto(GaleryPhoto galeryPhoto);
	
	boolean deleteGaleryPhoto(Site site, PhotoGalery photoGalery, GaleryPhoto galeryPhoto);
	boolean deletePhotoGalery(Site site, PhotoGalery photoGalery);
	
	void addNews(News news);
	void updateNews(News news);
	void deleteNews(News news);
	News getNewsById(long newsId);
	List<News> getNewsBySite(Site site);
	
	void addHeadLine(HeadLine headLine);
	void updateHeadLine(HeadLine headLine);
	void deleteHeadLine(HeadLine headLine);
	HeadLine getHeadLineById(long headLineId);
	List<HeadLine> getHeadLinesBySite(Site site);
	
	boolean deleteColumnPhoto(Column column, Photo willBeDeletedPhoto, Site site);
}

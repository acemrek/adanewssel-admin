package com.ac.newsadmin.dao;

import java.util.Collection;
import java.util.List;

import com.ac.common.dao.BaseDao;
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

public interface ContentDao extends BaseDao{
	void savePhoto(Photo photo);
	void updatePhoto(Photo photo);
	void mergePhoto(Photo photo);
	void deletePhoto(Photo photo);
	void deletePhotos(Collection<Photo> photos);
	Photo findPhotoById(long id);
	
	void saveVideo(Video video);
	void updateVideo(Video video);
	void mergeVideo(Video video);
	void deleteVideo(Video video);
	Video findVideoById(long id);
	List<Video> findVidepoBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	List<Video> findVideoBySite(Site site);
	
	void saveAudio(Audio audio);
	void updateAudio(Audio audio);
	void mergeAudio(Audio audio);
	void deleteAudio(Audio audio);
	Audio findAudioById(long id);
	List<Audio> findAudioBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	List<Audio> findAudioBySite(Site site);
	
	void saveColumn(Column column);
	void updateColumn(Column column);
	void mergeColumn(Column column);
	Column findColumnById(long id);
	List<Column> findColumnsByColumnist(Columnist columnist);
	void deleteColumn(Column column);
	void deleteColumnByColumnist(Columnist columnist);
	
	List<Column> findColumnsBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	
	void saveNewCategory(Category category);
	Category findCategoryById(long categoryId);
	Category findCategoryByName(String name, Site site);
	List<Category> findAllCategoriesBySite(Site site);
	
	void saveNewPhotoGalery(PhotoGalery photoGalery);
	void updatePhotoGalery(PhotoGalery photoGalery);
	void mergePhotoGalery(PhotoGalery photoGalery);
	PhotoGalery findPhotoGaleryById(long photoGaleryId);
	void deletePhotoGalery(PhotoGalery photoGalery);
	List<PhotoGalery> findPhotoGaleryBySiteAndPublishStatus(Site site, PublishStatus publishStatus);
	List<PhotoGalery> findPhotoGaleryBySite(Site site);
	void saveNewGaleryPhoto(GaleryPhoto galeryPhoto);
	void updateGaleryPhoto(GaleryPhoto galeryPhoto);
	void mergeGaleryPhoto(GaleryPhoto galeryPhoto);
	GaleryPhoto findGaleryPhotoById(long galeryPhotoId);
	void deleteGaleryPhoto(GaleryPhoto galeryPhoto);
	void deleteGaleryPhotos(Collection<GaleryPhoto> galeryPhotos);
	
	void saveNews(News news);
	void updateNews(News news);
	void deleteNews(News news);
	News findNewsById(long newsId);
	List<News> findNewsBySite(Site site);
	
	void saveHeadLine(HeadLine headLine);
	void updateHeadLine(HeadLine headLine);
	void deleteHeadLine(HeadLine headLine);
	HeadLine findHeadLineById(long headLineId);
	List<HeadLine> findHeadLinesBySite(Site site);
}

package com.ac.newsadmin.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ac.common.dao.BaseDaoImpl;
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

@SuppressWarnings("serial")
public class ContentDaoImpl extends BaseDaoImpl implements ContentDao, Serializable{

	public void savePhoto(Photo photo) {
		super.saveOrUpdate(photo);
	}
	
	public void updatePhoto(Photo photo){
		super.update(photo);
	}
	
	public void mergePhoto(Photo photo){
		super.getSession().merge(photo);
	}

	public void deletePhoto(Photo photo){
		photo = (Photo)getSession().merge(photo);
		super.delete(photo);
	}

	public void deletePhotos(Collection<Photo> photos){
		super.deleteAll(photos);
	}
	
	public Photo findPhotoById(long id) {
		return super.findById(Photo.class, id);
	}
	
	public void saveVideo(Video video){
		super.saveOrUpdate(video);
	}
	
	public void updateVideo(Video video){
		super.update(video);
	}
	
	public void mergeVideo(Video video){
		getSession().merge(video);
	}
	
	public void deleteVideo(Video video){
		super.delete(video);
	}

	public Video findVideoById(long id) {
		return super.findById(Video.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Video> findVidepoBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		Criteria criteria = getSession().createCriteria(Video.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.add(Restrictions.eq("publishStatus", publishStatus));
		criteria.addOrder(Order.asc("createDate"));
		return criteria.list();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Video> findVideoBySite(Site site){
		Criteria criteria = getSession().createCriteria(Video.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();		
	}

	public void saveAudio(Audio audio){
		super.saveOrUpdate(audio);
	}
	
	public void updateAudio(Audio audio){
		super.update(audio);
	}
	
	public void mergeAudio(Audio audio){
		getSession().merge(audio);
	}
	
	public void deleteAudio(Audio audio){
		super.delete(audio);
	}

	public Audio findAudioById(long id) {
		return super.findById(Audio.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Audio> findAudioBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		Criteria criteria = getSession().createCriteria(Audio.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.add(Restrictions.eq("publishStatus", publishStatus));
		criteria.addOrder(Order.asc("createDate"));
		return criteria.list();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Audio> findAudioBySite(Site site){
		Criteria criteria = getSession().createCriteria(Audio.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();		
	}

	public void saveColumn(Column column){
		column.setCreateDate(new Date());
		super.saveOrUpdate(column);
	}
	
	public void updateColumn(Column column){
		super.update(column);
	}
	
	public void mergeColumn(Column column){
		super.getSession().merge(column);
	}
	
	public Column findColumnById(long id){
		return super.findById(Column.class, id);
	}
	
	public List<Column> findColumnsByColumnist(Columnist columnist){
		return super.findEntityByProperty(Column.class, "columnist", columnist);
	}
	
	public void deleteColumn(Column column){
		super.delete(column);
	}
	
	public void deleteColumnByColumnist(Columnist columnist){
		String hql = "delete from Column where columnist= :columnist";
		getSession().createQuery(hql).setParameter("columnist", columnist).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Column> findColumnsBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		Criteria criteria = getSession().createCriteria(Column.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.add(Restrictions.eq("publishStatus", publishStatus));
		criteria.addOrder(Order.asc("listOrder"));
		return criteria.list();
	}

	@Override
	public void saveNewCategory(Category category) {
		super.saveOrUpdate(category);
	}
	
	public Category findCategoryById(long categoryId){
		return super.findById(Category.class, categoryId);
	}
	
	public Category findCategoryByName(String name, Site site){
		return super.findEntityByPropertyUnique(Category.class, new String[]{"name","site"}, new Object[]{name,site});
	}
	
	public List<Category> findAllCategoriesBySite(Site site){
		return super.findEntityByProperty(Category.class, "site", site);
	}
	
	public void saveNewPhotoGalery(PhotoGalery photoGalery){
		photoGalery.setCreateDate(new Date());
		super.saveOrUpdate(photoGalery);
	}
	
	public void updatePhotoGalery(PhotoGalery photoGalery){
		super.saveOrUpdate(photoGalery);
	}
	
	public void mergePhotoGalery(PhotoGalery photoGalery){
		getSession().merge(photoGalery);
	}

	public PhotoGalery findPhotoGaleryById(long photoGaleryId){
		return super.findById(PhotoGalery.class, photoGaleryId);
	}
	
	public void deletePhotoGalery(PhotoGalery photoGalery){
		super.delete(photoGalery);
	}
	
	@SuppressWarnings("unchecked")
	public List<PhotoGalery> findPhotoGaleryBySiteAndPublishStatus(Site site, PublishStatus publishStatus){
		Criteria criteria = getSession().createCriteria(PhotoGalery.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.add(Restrictions.eq("publishStatus", publishStatus));
		criteria.addOrder(Order.asc("createDate"));
		return criteria.list();		
	}
	
	@SuppressWarnings("unchecked")
	public List<PhotoGalery> findPhotoGaleryBySite(Site site){
		Criteria criteria = getSession().createCriteria(PhotoGalery.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();		
	}
	
	public void saveNewGaleryPhoto(GaleryPhoto galeryPhoto){
		super.saveOrUpdate(galeryPhoto);
	}

	public void updateGaleryPhoto(GaleryPhoto galeryPhoto){
		super.saveOrUpdate(galeryPhoto);
	}

	public void mergeGaleryPhoto(GaleryPhoto galeryPhoto){
		super.getSession().merge(galeryPhoto);
	}
	
	public GaleryPhoto findGaleryPhotoById(long galeryPhotoId){
		return super.findById(GaleryPhoto.class, galeryPhotoId);
	}
	
	public void deleteGaleryPhoto(GaleryPhoto galeryPhoto){
		super.delete(galeryPhoto);
	}
	
	public void deleteGaleryPhotos(Collection<GaleryPhoto> galeryPhotos){
		super.deleteAll(galeryPhotos);
	}
	
	public void saveNews(News news){
		news.setCreateDate(new Date());
		super.saveOrUpdate(news);
	}

	public void updateNews(News news){
		super.saveOrUpdate(news);
	}
	
	public void deleteNews(News news){
		super.delete(news);
	}
	public News findNewsById(long newsId){
		return super.findById(News.class, newsId);
	}
	@SuppressWarnings("unchecked")
	public List<News> findNewsBySite(Site site){
		Criteria criteria = getSession().createCriteria(News.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.addOrder(Order.desc("createDate"));
		return criteria.list();		
	}
	
	public void saveHeadLine(HeadLine headLine){
		super.saveOrUpdate(headLine);
	}
	
	public void updateHeadLine(HeadLine headLine){
		super.saveOrUpdate(headLine);
	}
	
	public void deleteHeadLine(HeadLine headLine){
		super.delete(headLine);
	}
	
	public HeadLine findHeadLineById(long headLineId){
		return super.findById(HeadLine.class, headLineId);
	}
	
	@SuppressWarnings("unchecked")
	public List<HeadLine> findHeadLinesBySite(Site site){
		Criteria criteria = getSession().createCriteria(HeadLine.class);
		criteria.add(Restrictions.eq("site", site));
		criteria.addOrder(Order.desc("orderInTheLine"));
		return criteria.list();		
	}

}
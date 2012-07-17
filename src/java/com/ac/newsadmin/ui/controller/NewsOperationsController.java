package com.ac.newsadmin.ui.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.newsadmin.model.entity.News;

@SuppressWarnings("serial")
@Component("newsOperationsBean")
@Scope("view")
public class NewsOperationsController extends AbstractOperationController implements Serializable{

	private static final String NEWS_OPERATION_CREATE_NEW_NEWS = "createNewNews";
	private static final String NEWS_OPERATION_EDIT_NEWS = "editNews";
	private static final String NEWS_OPERATION_MANAGE_HEADLINES = "manageHeadlines";
	
	private News willBeCreatedNews;
	private News willBeEditedNews;
	private News willBeDeletedNews;
	
	public void showAddNewsForm(){
		if(willBeCreatedNews == null){
			willBeCreatedNews = new News();
		}
		setSelectedOperation(NEWS_OPERATION_CREATE_NEW_NEWS);
	}
	
	public void createNewNews(){
		contentService.addNews(willBeCreatedNews);
		willBeCreatedNews = null;
		showEditNewsForm();
	}
	
	public void showEditNewsForm(){
		setSelectedOperation(NEWS_OPERATION_EDIT_NEWS);	
	}
	
	public void selectWillBeEditedNews(News news){
		this.willBeEditedNews = news;
	}
	
	public void editNews(){
		contentService.updateNews(willBeEditedNews);
	}
	
	public void showManageHeadlinesForm(){
		setSelectedOperation(NEWS_OPERATION_MANAGE_HEADLINES);	
	}
	
	public void selectWillBeDeletedNews(News news){
		this.willBeDeletedNews = news;
	}
	
	public void deleteNews(){
		
	}

	public News getWillBeCreatedNews() {
		return willBeCreatedNews;
	}

	public void setWillBeCreatedNews(News willBeCreatedNews) {
		this.willBeCreatedNews = willBeCreatedNews;
	}

	public List<News> getAllNews(){
		return contentService.getNewsBySite(sessionData.getCurrentSite());
	}

	public News getWillBeEditedNews() {
		return willBeEditedNews;
	}

	public void setWillBeEditedNews(News willBeEditedNews) {
		this.willBeEditedNews = willBeEditedNews;
	}

	public News getWillBeDeletedNews() {
		return willBeDeletedNews;
	}

	public void setWillBeDeletedNews(News willBeDeletedNews) {
		this.willBeDeletedNews = willBeDeletedNews;
	}
	
	@Override
	public String getDiscriminator() {
		return "newsGaleryOperations";
	}

}

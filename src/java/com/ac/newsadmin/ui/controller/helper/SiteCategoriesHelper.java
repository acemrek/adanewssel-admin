package com.ac.newsadmin.ui.controller.helper;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.newsadmin.model.entity.Category;
import com.ac.newsadmin.service.ContentService;
import com.ac.newsadmin.ui.SessionData;

@SuppressWarnings("serial")
@Component("siteCategoriesHelper")
@Scope("view")
public class SiteCategoriesHelper implements Serializable{
	
	@Resource private ContentService contentService;
	@Resource private SessionData sessionData;
	
	private List<Category> siteCategories;
	
	@PostConstruct
	public void init(){
		siteCategories = contentService.getAllCategoriesBySite(sessionData.getCurrentSite());
	}
	
	public List<Category> getSiteCategories() {
		return siteCategories;
	}
}

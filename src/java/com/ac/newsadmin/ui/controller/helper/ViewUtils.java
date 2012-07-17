package com.ac.newsadmin.ui.controller.helper;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ac.common.utility.CommonUtils;
import com.ac.common.utility.FacesUtils;
import com.ac.newsadmin.enums.PhotoSize;
import com.ac.newsadmin.service.PortalPreferencesService;
import com.ac.newsadmin.ui.GeneralConstants;
import com.ac.newsadmin.ui.SessionData;

@SuppressWarnings("serial")
@Component("viewUtils")
@Scope("session")
public class ViewUtils implements Serializable{
	
	@Resource private SessionData sessionData;
	@Resource private PortalPreferencesService portalPreferencesService;
	
	public String getFullBinaryUrl(String relativeUrl, String... reqKeyValueParams){
		
		StringBuffer paramsBuf = new StringBuffer();
		if(reqKeyValueParams.length > 0 && CommonUtils.isNumberEven(reqKeyValueParams.length)){
			int i = 0;
			paramsBuf.append('?');
			for(String chunk : reqKeyValueParams){
				paramsBuf.append(chunk);
				if(CommonUtils.isNumberEven(i)){
					paramsBuf.append('=');
				}
				i++;
			}
		}
		
		return new StringBuffer()
					.append(GeneralConstants.BINARY_BASE_URL)
					.append(sessionData.getCurrentSite().getDomainName())
					.append('/')
					.append(relativeUrl)
					.append(paramsBuf.toString())
					.toString();
	}
	
	public int getVideoThumbnailMediumWidth(){
		return portalPreferencesService.getVideoThumbnailImageSize(sessionData.getCurrentSite(), PhotoSize.MEDIUM).getWidth();
	}
	
	public String getParametrizedMsg(String key){
		return FacesUtils.getI18nMessage(key);
	}
	
	public String getParametrizedMsg(String key, String arg1){
		return FacesUtils.getI18nMessage(key, new Object[]{arg1});
	}

	public String getParametrizedMsg(String key, String arg1, String arg2){
		return FacesUtils.getI18nMessage(key, new Object[]{arg1, arg2});
	}
	
	public String getParametrizedMsg(String key, String arg1, String arg2, String arg3){
		return FacesUtils.getI18nMessage(key, new Object[]{arg1, arg2, arg3});
	}
	
	public String getParametrizedErr(String key){
		return FacesUtils.getI18nError(key);
	}
	
	public String getParametrizedErr(String key, String arg1){
		return FacesUtils.getI18nError(key, arg1);
	}
	
	public String getParametrizedErr(String key, String arg1, String arg2){
		return FacesUtils.getI18nError(key, arg1, arg2);
	}
	
	public String getParametrizedErr(String key, String arg1, String arg2, String arg3){
		return FacesUtils.getI18nError(key, arg1, arg2, arg3);
	}
}

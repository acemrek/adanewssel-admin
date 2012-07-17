package com.ac.newsadmin.ui.converter;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.ac.newsadmin.model.entity.GaleryPhoto;
import com.ac.newsadmin.service.ContentService;

@Component("galeryPhotoConverter")
public class GaleryPhotoConverter extends AbstractConverter{

	@Resource ContentService contentService;
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		return super.isSubmitedStringEmpty(submittedValue) ? null : contentService.getGaleryPhotoById(NumberUtils.toLong(submittedValue));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return super.isValueEmpty(value) ? null : ((GaleryPhoto) value).getId() + StringUtils.EMPTY;
	}

}

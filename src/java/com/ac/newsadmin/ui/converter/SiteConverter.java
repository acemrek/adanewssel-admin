package com.ac.newsadmin.ui.converter;

import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.service.SiteService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Component("siteConverter")
public class SiteConverter extends AbstractConverter {

	@Resource SiteService siteService;
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        return super.isSubmitedStringEmpty(submittedValue) ? null : siteService.getSiteById(NumberUtils.toLong(submittedValue));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return super.isValueEmpty(value) ? null : ((Site) value).getId() + StringUtils.EMPTY;
	}

}

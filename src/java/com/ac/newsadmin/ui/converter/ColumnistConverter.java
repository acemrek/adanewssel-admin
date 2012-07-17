package com.ac.newsadmin.ui.converter;

import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Component("columnistConverter")
public class ColumnistConverter extends AbstractConverter{

	@Resource UserService userService;
	
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		return super.isSubmitedStringEmpty(submittedValue) ? null : userService.getColumnistById(NumberUtils.toLong(submittedValue));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return super.isValueEmpty(value) ? null : ((Columnist) value).getId() + StringUtils.EMPTY;
	}

}

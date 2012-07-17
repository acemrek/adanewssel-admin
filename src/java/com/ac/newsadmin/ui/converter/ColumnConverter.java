package com.ac.newsadmin.ui.converter;

import com.ac.newsadmin.model.entity.Column;
import com.ac.newsadmin.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Component("columnConverter")
public class ColumnConverter extends AbstractConverter{

	@Resource ContentService contentService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        return super.isSubmitedStringEmpty(submittedValue) ? null : contentService.getColumnById(NumberUtils.toLong(submittedValue));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return super.isValueEmpty(value) ? null : ((Column) value).getId() + StringUtils.EMPTY;
    }

}

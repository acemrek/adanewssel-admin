package com.ac.newsadmin.ui.converter;

import com.ac.newsadmin.ui.tools.photoUpload.AspectRatio;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Component("aspectRatioConverter")
public class AspectRatioConverter extends AbstractConverter{

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		return super.isSubmitedStringEmpty(submittedValue) ? null : AspectRatio.fromDobule(NumberUtils.toDouble(submittedValue));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		return super.isValueEmpty(value) ? null : ((AspectRatio)value).getValue()+"";
    }

}

package com.ac.newsadmin.ui.converter;

import com.ac.newsadmin.enums.Gender;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@Component("genderConverter")
public class GenderConverter extends AbstractConverter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		return super.isSubmitedStringEmpty(submittedValue) ? null : Gender.fromString(submittedValue);
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        return super.isValueEmpty(value) ? null : ((Gender) value).getValue();
	}

}

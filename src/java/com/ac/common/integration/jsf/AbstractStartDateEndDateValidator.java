package com.ac.common.integration.jsf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;

import com.ac.common.utility.FacesUtils;

public abstract class AbstractStartDateEndDateValidator implements Validator{
	
	public abstract String getEndDateId();
	public abstract String getDateFormat();
	public abstract String getI18NErrorKey();
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		try {
			Date starDate = (Date)value;
			Date endDate = null;
			
			if(starDate == null){
				return;
			}
			
			String endDateId = getEndDateId();
			UIInput endDateInput = (UIInput) component.getParent().findComponent(endDateId);
			String endDateStr = (String)endDateInput.getSubmittedValue();
			
			if(StringUtils.isBlank(endDateStr)){
				return;
			}
			
			SimpleDateFormat format = new SimpleDateFormat(getDateFormat());
			endDate = format.parse(endDateStr);
			
			if(endDate.after(starDate) == false){
				String message = FacesUtils.getI18nError(getI18NErrorKey());
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
				throw new ValidatorException(facesMessage);
			}
			
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
}

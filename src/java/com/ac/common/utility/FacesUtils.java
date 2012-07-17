package com.ac.common.utility;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FacesUtils {
	
	public static String getI18nMessage(String key, Object ...args){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		String message = bundle.getString(key);
		message = MessageFormat.format(message, args);
		return message;
	}
	
	public static String getI18nError(String key, Object ...args){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "err");
		String message = bundle.getString(key);
		message = MessageFormat.format(message, args);
		return message;
	}
	
	public static void addMessage(String summaryKey, String detailKey, String componentId, Object...detailKeyArgs){
		FacesMessage facesMessage = getFacesInfoMessage(summaryKey, detailKey, detailKeyArgs);
		FacesContext.getCurrentInstance().addMessage(componentId, facesMessage); 
	}
	
	public static void addError(String summaryKey, String detailKey, String componentId, Object...detailKeyArgs){
		FacesMessage facesMessage = getFacesErrorMessage(summaryKey, detailKey, detailKeyArgs);
		FacesContext.getCurrentInstance().addMessage(componentId, facesMessage); 
	}
	
	public static FacesMessage getFacesErrorMessage(String summaryKey, String detailKey, Object []detailArgs){
		return getFacesMessage(FacesMessage.SEVERITY_ERROR, summaryKey, detailKey, detailArgs);
	}
	
	public static FacesMessage getFacesInfoMessage(String summaryKey, String detailKey, Object []detailArgs){
		return getFacesMessage(FacesMessage.SEVERITY_INFO, summaryKey, detailKey, detailArgs);
	}
	
	private static FacesMessage getFacesMessage(Severity severity, String summaryKey, String detailKey, Object []detailArgs){
		String summary = StringUtils.isNotBlank(summaryKey) ? ((severity == FacesMessage.SEVERITY_ERROR || severity == FacesMessage.SEVERITY_FATAL) ? getI18nError(summaryKey) : getI18nMessage(summaryKey)) : StringUtils.EMPTY;
		String detail  = StringUtils.isNotBlank(detailKey)  ? ((severity == FacesMessage.SEVERITY_ERROR || severity == FacesMessage.SEVERITY_FATAL) ? getI18nError(detailKey, detailArgs)  : getI18nMessage(detailKey, detailArgs)) : StringUtils.EMPTY;
		return new FacesMessage(severity, summary, detail);		
	}
	
	public static FacesContext getFacesContext(){
		FacesContext fc = FacesContext.getCurrentInstance();
		return fc;
	}
	
	public static ExternalContext getExternalContext(){
		FacesContext fc = getFacesContext();
		ExternalContext ec = fc.getExternalContext();
		return ec;
	}
	
	public static HttpServletRequest getHttpServletRequest(){
		FacesContext fc = getFacesContext();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest)ec.getRequest();
		return request;
	}
	
	public static String getHttpParamAsString(String key){
		return getHttpServletRequest().getParameter(key);
	}
	
	public static Long getHttpParamAsLong(String key){
		return new Long(getHttpServletRequest().getParameter(key));
	}
	
	public static void resetForm(UIComponent form) {  
	    for (UIComponent uic : form.getChildren()) {  
	      if (uic instanceof EditableValueHolder) {  
	        EditableValueHolder evh=(EditableValueHolder)uic;  
	        evh.resetValue();  
	      }  
	      resetForm(uic);  
	    }  
	  }  
	
	@SuppressWarnings("unchecked")
	public static <T> T getSpringBean(String beanId){
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		T bean = (T) wac.getBean(beanId);
		return bean;
	}
}

package com.ac.newsadmin.ui.validator;

import javax.faces.validator.FacesValidator;

import com.ac.common.integration.jsf.AbstractStartDateEndDateValidator;
import com.ac.newsadmin.ui.GeneralConstants;

@FacesValidator("siteStartEndDateValidator")
public class SiteStartEndDateValidator extends AbstractStartDateEndDateValidator{

	@Override
	public String getEndDateId() {
		return "serviceEndDate";
	}

	@Override
	public String getDateFormat() {
		return GeneralConstants.DATE_FORMAT;
	}

	@Override
	public String getI18NErrorKey() {
		return "site.serviceStartDateEndDate.inconsistency";
	}

}

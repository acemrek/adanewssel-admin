package com.ac.newsadmin.ui.converter;

import org.apache.commons.lang.StringUtils;
import javax.faces.convert.Converter;

public abstract class AbstractConverter implements Converter {

    public boolean isSubmitedStringEmpty(String submitedValue){
        return StringUtils.isEmpty(submitedValue);
    }

    public boolean isValueEmpty(Object value){
        return value instanceof String && org.apache.commons.lang3.StringUtils.isEmpty((String) value);
    }

}

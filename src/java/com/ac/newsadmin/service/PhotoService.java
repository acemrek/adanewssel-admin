package com.ac.newsadmin.service;

import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;

public interface PhotoService {
	Photo createNewPhoto(Site site, WillBeStoredImg willBeStoredImg)  throws Exception;
	String storeTempImage(Site site, WillBeStoredImg willBeStoredImg)  throws Exception;
}

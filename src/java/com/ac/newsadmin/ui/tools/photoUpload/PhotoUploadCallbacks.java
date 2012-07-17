package com.ac.newsadmin.ui.tools.photoUpload;

import com.ac.newsadmin.model.entity.Photo;
import com.ac.newsadmin.model.transfer.WillBeStoredImg;

public interface PhotoUploadCallbacks {
	String getFullStorePath();
	AspectRatio[] getPossibleAspectRatios();
	void setAssetDimensions(WillBeStoredImg willBeStoredImg, int originalImageWidth, int originalImageHeight);
	void finish(boolean success, Photo photo);
}

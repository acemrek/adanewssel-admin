package com.ac.newsadmin.service;

public interface ImageResizer {
	byte[] scaleImg(byte []imgData, int width, int heigth, String extension);
}

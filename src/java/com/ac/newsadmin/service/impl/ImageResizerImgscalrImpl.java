package com.ac.newsadmin.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import com.ac.newsadmin.service.ImageResizer;

@Service
public class ImageResizerImgscalrImpl implements ImageResizer{

	public byte[] scaleImg(byte []imgData, int width, int heigth, String extension){
		ByteArrayInputStream in = null;
		ByteArrayOutputStream baos = null;
		try {
			in = new ByteArrayInputStream(imgData);
			BufferedImage img = ImageIO.read(in);
			BufferedImage scaledImg =  Scalr.resize(img, width, heigth);
		
			baos = new ByteArrayOutputStream();
			ImageIO.write( scaledImg, extension, baos );
			baos.flush();
			
			byte[] scaledImageInByte = baos.toByteArray();
			
			
			return scaledImageInByte;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(baos);
		}
		
		return null;
	}
	
	
}

package com.ac.newsadmin.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.jets3t.service.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ac.newsadmin.dao.UserDao;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;
import com.ac.newsadmin.service.BinaryStorageService;
import com.ac.newsadmin.service.ContentService;
import com.ac.newsadmin.service.UserService;

@SuppressWarnings("serial")
@Service("userService")
public class UserServiceImpl implements UserService, Serializable{

	@Resource private UserDao userDao;
	@Resource private BinaryStorageService binaryStorageService;
	@Resource private ContentService contentService;
	
	@Transactional
	public void saveNewPortalAdmin(PortalAdmin portalAdmin) {
		userDao.saveOrUpdatePortalAdmin(portalAdmin);
	}
	
	@Transactional
	public void updatePortalAdmin(PortalAdmin portalAdmin) {
		userDao.saveOrUpdatePortalAdmin(portalAdmin);
	}

	@Transactional
	public void mergePortalAdmin(PortalAdmin portalAdmin) {
		userDao.mergePortalAdmin(portalAdmin);
	}
	
	public List<PortalAdmin> getAllPortalAdmins(){
		return userDao.findAllPortalAdmins();
	}

	@Transactional
	public void deletePortalAdmin(PortalAdmin portalAdmin){
		userDao.deletePortalAdmin(portalAdmin);
	}

	public PortalAdmin getPortalAdminById(long id) {
		return userDao.findPortalAdminById(id);
	}

	public PortalAdmin getPortalAdminByEmail(String email){
		return userDao.findPortalAdminByEmail(email);
	}
	
	public PortalAdmin getPortalAdminByUsername(String username) {
		return userDao.findPortalAdminByUsername(username);
	}
	
	@Transactional
	public void saveNewColumnist(Columnist columnist, Site site) {
		columnist.setSite(site);
		userDao.saveOrUpdateColumnist(columnist);
	}
	
	@Transactional
	public void updateColumnist(Columnist columnist) {
		userDao.saveOrUpdateColumnist(columnist);
	}
	
	@Transactional
	public void mergeColumnist(Columnist columnist) {
		userDao.mergeColumnist(columnist);
	}

	public List<Columnist> getAllColumnists(Site site){
		return userDao.findAllColumnists(site);
	}

	@Transactional
	public void deleteColumnist(Columnist columnist) throws ServiceException{
		contentService.deleteColumnByColumnist(columnist);
		contentService.deletePhoto(columnist.getPhoto());
		userDao.deleteColumnist(columnist);
		binaryStorageService.deleteColumnist(columnist.getSite(), columnist);
	}

	public Columnist getColumnistById(long id) {
		return userDao.findColumnistById(id);
	}

	public Columnist getColumnistByUsername(String username, Site site) {
		return userDao.findColumnistByUsername(username, site);
	}

	public Columnist getColumnistByEmail(String email, Site site) {
		return userDao.findColumnistByEmail(email, site);
	}
	
}

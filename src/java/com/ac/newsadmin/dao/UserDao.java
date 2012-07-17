package com.ac.newsadmin.dao;

import java.util.List;

import com.ac.common.dao.BaseDao;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;

public interface UserDao extends BaseDao{
	void saveOrUpdatePortalAdmin(PortalAdmin portalAdmin);
	void mergePortalAdmin(PortalAdmin portalAdmin);
	PortalAdmin findPortalAdminByEmail(String email);
	PortalAdmin findPortalAdminByUsername(String username);
	PortalAdmin findPortalAdminById(long id);
	List<PortalAdmin> findAllPortalAdmins();
	void deletePortalAdmin(PortalAdmin portalAdmin);
	void saveOrUpdateColumnist(Columnist columnist);
	void mergeColumnist(Columnist columnist);
	Columnist findColumnistByUsername(String username, Site site);
	Columnist findColumnistByEmail(String email, Site site);
	Columnist findColumnistById(long id);
	List<Columnist> findAllColumnists(Site site);
	void deleteColumnist(Columnist columnist);
}

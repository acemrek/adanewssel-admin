package com.ac.newsadmin.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.ac.common.dao.BaseDaoImpl;
import com.ac.newsadmin.dao.UserDao;
import com.ac.newsadmin.model.entity.Columnist;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.model.entity.Site;

@SuppressWarnings("serial")
public class UserDaoImpl extends BaseDaoImpl implements UserDao, Serializable{

	public void saveOrUpdatePortalAdmin(PortalAdmin portalAdmin) {
		super.saveOrUpdate(portalAdmin);
	}
	
	public void mergePortalAdmin(PortalAdmin portalAdmin) {
		getSession().merge(portalAdmin);
	}

	public PortalAdmin findPortalAdminByUsername(String username) {
		return super.findEntityByPropertyUnique(PortalAdmin.class, "username", username);
	}

	public PortalAdmin findPortalAdminByEmail(String email) {
		return super.findEntityByPropertyUnique(PortalAdmin.class, "email", email);
	}
	
	public PortalAdmin findPortalAdminById(long id) {
		return (PortalAdmin)super.findById(PortalAdmin.class, id);
	}
	
	public List<PortalAdmin> findAllPortalAdmins(){
		return super.findAllEntities(PortalAdmin.class, true);
	}
	
	public void deletePortalAdmin(PortalAdmin portalAdmin){
		getSession().delete(portalAdmin);
	}
	
	public void saveOrUpdateColumnist(Columnist columnist) {
		if(columnist.getId() == 0){
			super.saveOrUpdate(columnist);
		}
		else{
			getSession().merge(columnist);
		}
	}
	
	public void mergeColumnist(Columnist columnist){
		getSession().merge(columnist);
	}

	public Columnist findColumnistByUsername(String username, Site site) {
		return super.findEntityByPropertyUnique(Columnist.class, new String[]{"username","site"}, new Object[]{username, site});
	}
	
	public Columnist findColumnistByEmail(String email, Site site) {
		return super.findEntityByPropertyUnique(Columnist.class, new String[]{"email","site"}, new Object[]{email, site});
	}
	
	public Columnist findColumnistById(long id) {
		return (Columnist)super.findById(Columnist.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Columnist> findAllColumnists(Site site){
		Criteria criteria = getSession().createCriteria(Columnist.class);
		criteria.setCacheable(true);
		criteria.add(Restrictions.eq("site", site));
		return criteria.list();
	}
	
	public void deleteColumnist(Columnist columnist){
		getSession().delete(columnist);
	}
}

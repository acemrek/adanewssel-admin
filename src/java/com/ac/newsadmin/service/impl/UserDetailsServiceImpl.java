package com.ac.newsadmin.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ac.newsadmin.GlobalConstants;
import com.ac.newsadmin.model.entity.PortalAdmin;
import com.ac.newsadmin.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Resource private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		PortalAdmin portalAdmin = userService.getPortalAdminByUsername(username);
		
		if(portalAdmin == null){
			throw new UsernameNotFoundException(username + " not found.");
		}
		
		if(CollectionUtils.isEmpty(portalAdmin.getSites())){
			throw new UsernameNotFoundException(username + " not found.");
		}
		
		Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
		userAuthorities.add(new SimpleGrantedAuthority(GlobalConstants.ROLE_SITE_ADMIN));
		
		if(portalAdmin.getUsername().equalsIgnoreCase("acemrek")){
			userAuthorities.add(new SimpleGrantedAuthority(GlobalConstants.ROLE_SUPER_ADMIN));
		}
		
		return new User(portalAdmin.getUsername(), portalAdmin.getPassword(), userAuthorities);
	}

}

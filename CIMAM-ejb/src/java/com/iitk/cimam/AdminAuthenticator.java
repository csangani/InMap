/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam;

import com.iitk.cimam.data.Admin;
import com.iitk.cimam.data.AdminFacade;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Chirag Sangani
 */
@Stateless
@LocalBean
public class AdminAuthenticator {
    
    public boolean Authenticate (String Username, String Password, AdminFacade AF) {
	int usernameHash = Username.hashCode();
	int passwordHash = Password.hashCode();
	Admin AdminUser = AF.find(usernameHash);
	if(AdminUser != null){
	    if (AdminUser.getPassword() == passwordHash) {
		return true;
	    }
	}
	return false;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.data;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Chirag Sangani
 */
@Stateless
public class AdminFacade extends AbstractFacade<Admin> {
    @PersistenceContext(unitName = "CIMAM-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
	return em;
    }

    public AdminFacade() {
	super(Admin.class);
    }
    
}

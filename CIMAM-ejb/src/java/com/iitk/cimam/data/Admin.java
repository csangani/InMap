/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chirag Sangani
 */
@Entity
@Table(name = "admin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findByUsername", query = "SELECT a FROM Admin a WHERE a.username = :username"),
    @NamedQuery(name = "Admin.findByPassword", query = "SELECT a FROM Admin a WHERE a.password = :password")})
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "username")
    private Integer username;
    @Basic(optional = false)
    @NotNull
    @Column(name = "password")
    private int password;

    public Admin() {
    }

    public Admin(Integer username) {
	this.username = username;
    }

    public Admin(Integer username, int password) {
	this.username = username;
	this.password = password;
    }

    public Integer getUsername() {
	return username;
    }

    public void setUsername(Integer username) {
	this.username = username;
    }

    public int getPassword() {
	return password;
    }

    public void setPassword(int password) {
	this.password = password;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (username != null ? username.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Admin)) {
	    return false;
	}
	Admin other = (Admin) object;
	if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.iitk.cimam.Admin[ username=" + username + " ]";
    }
    
}

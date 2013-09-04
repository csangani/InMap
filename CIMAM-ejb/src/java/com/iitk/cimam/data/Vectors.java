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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Chirag Sangani
 */
@Entity
@Table(name = "vectors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vectors.findAll", query = "SELECT v FROM Vectors v"),
    @NamedQuery(name = "Vectors.findByUid", query = "SELECT v FROM Vectors v WHERE v.uid = :uid"),
    @NamedQuery(name = "Vectors.findByX", query = "SELECT v FROM Vectors v WHERE v.x = :x"),
    @NamedQuery(name = "Vectors.findByY", query = "SELECT v FROM Vectors v WHERE v.y = :y"),
    @NamedQuery(name = "Vectors.findByZ", query = "SELECT v FROM Vectors v WHERE v.z = :z"),
    @NamedQuery(name = "Vectors.findByWidth", query = "SELECT v FROM Vectors v WHERE v.width = :width"),
    @NamedQuery(name = "Vectors.findByHeight", query = "SELECT v FROM Vectors v WHERE v.height = :height")})
public class Vectors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UID")
    private Integer uid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "tagList")
    private String tagList;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "SVG")
    private String svg;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "x")
    private Double x;
    @Column(name = "y")
    private Double y;
    @Column(name = "z")
    private Integer z;
    @Column(name = "width")
    private Double width;
    @Column(name = "height")
    private Double height;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Lob
    @Size(max = 65535)
    @Column(name = "highlight")
    private String highlight;
    @Lob
    @Size(max = 65535)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "attributes")
    private String attributes;

    public Vectors() {
    }

    public Vectors(Integer uid) {
	this.uid = uid;
    }

    public Vectors(Integer uid, String tagList, String svg) {
	this.uid = uid;
	this.tagList = tagList;
	this.svg = svg;
    }

    public Integer getUid() {
	return uid;
    }

    public void setUid(Integer uid) {
	this.uid = uid;
    }

    public String getTagList() {
	return tagList;
    }

    public void setTagList(String tagList) {
	this.tagList = tagList;
    }

    public String getSvg() {
	return svg;
    }

    public void setSvg(String svg) {
	this.svg = svg;
    }

    public Double getX() {
	return x;
    }

    public void setX(Double x) {
	this.x = x;
    }

    public Double getY() {
	return y;
    }

    public void setY(Double y) {
	this.y = y;
    }

    public Integer getZ() {
	return z;
    }

    public void setZ(Integer z) {
	this.z = z;
    }

    public Double getWidth() {
	return width;
    }

    public void setWidth(Double width) {
	this.width = width;
    }

    public Double getHeight() {
	return height;
    }

    public void setHeight(Double height) {
	this.height = height;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getHighlight() {
	return highlight;
    }

    public void setHighlight(String highlight) {
	this.highlight = highlight;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAttributes() {
	return attributes;
    }

    public void setAttributes(String attributes) {
	this.attributes = attributes;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (uid != null ? uid.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Vectors)) {
	    return false;
	}
	Vectors other = (Vectors) object;
	if ((this.uid == null && other.uid != null) || (this.uid != null && !this.uid.equals(other.uid))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.iitk.cimam.data.Vectors[ uid=" + uid + " ]";
    }
    
}

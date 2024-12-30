/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfaris.ipsh.authservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 *
 * @author priya
 */
@Entity
@Table(name="EFTSCREEN", schema = "IPSH")
public class Screen implements Serializable{
    
    @Id
    @Column(name = "screen_id")
    private String  screenId;
    @Column(name = "screen_no")
    private String  screenNo;
    @Column(name = "screen_name")
    private String  screenName;
    @Column(name = "screen_name_arabic")
    private String  screenNameArabic;
    @Column(name = "category_no")
    private int  categoryNo;
    @Column(name = "category_name")
    private String  categoryNanme;
    @Column(name = "link_type")
    private String  linkTYpe;
    @Column(name = "link_scr")
    private String  linkScr;
    @Column(name = "link_scr_ar")
    private String  linkScrAr;
    @Column(name = "pro_id")
    private String  proID;
    @Column(name = "ver_id")
    private String  verID;
    @Column(name = "status")
    private String  status;
    @Column(name = "date_cre")
    private String  dateCre;

    
    public String getScreenNameArabic() {
		return screenNameArabic;
	}

	public void setScreenNameArabic(String screenNameArabic) {
		this.screenNameArabic = screenNameArabic;
	}

	public Screen() {
    }

    public String getScreenNo() {
        return screenNo;
    }

    
    public String getScreenName() {
        return screenName;
    }

   

    public String getCategoryNanme() {
        return categoryNanme;
    }

    public String getLinkTYpe() {
        return linkTYpe;
    }

    public String getLinkScr() {
        return linkScr;
    }

    public String getLinkScrAr() {
        return linkScrAr;
    }

    public String getProID() {
        return proID;
    }

    public String getVerID() {
        return verID;
    }

    public String getStatus() {
        return status;
    }

    public String getDateCre() {
        return dateCre;
    }

    public void setScreenNo(String screenNo) {
        this.screenNo = screenNo;
    }

   
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    

    public void setCategoryNanme(String categoryNanme) {
        this.categoryNanme = categoryNanme;
    }

    public void setLinkTYpe(String linkTYpe) {
        this.linkTYpe = linkTYpe;
    }

    public void setLinkScr(String linkScr) {
        this.linkScr = linkScr;
    }

    public void setLinkScrAr(String linkScrAr) {
        this.linkScrAr = linkScrAr;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public void setVerID(String verID) {
        this.verID = verID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDateCre(String dateCre) {
        this.dateCre = dateCre;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((screenId == null) ? 0 : screenId.hashCode());
		result = prime * result + ((screenNo == null) ? 0 : screenNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Screen other = (Screen) obj;
		if (screenId == null) {
			if (other.screenId != null)
				return false;
		} else if (!screenId.equals(other.screenId))
			return false;
		if (screenNo == null) {
			if (other.screenNo != null)
				return false;
		} else if (!screenNo.equals(other.screenNo))
			return false;
		return true;
	}
    
    

    
}

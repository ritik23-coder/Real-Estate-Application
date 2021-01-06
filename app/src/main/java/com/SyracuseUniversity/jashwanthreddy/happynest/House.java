package com.SyracuseUniversity.jashwanthreddy.happynest;

/**
 * Created by jashwanthreddy on 4/8/17.
 */
import java.io.Serializable;
import java.util.ArrayList;

public class House implements Serializable {
    String ownerName;
    String description;
    String id;
    String primaryID;
    String projectname;
    String url;
    String mailid;
    String addressLine1;
    String addressLine2;
    String ownerCity;
    String bedroom;
    String cost;
    String contactnum;
    ArrayList<String> uploadpiclist;
    ArrayList<String> amenities;
    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(ArrayList<String> amenities) {
        this.amenities = amenities;
    }


    public ArrayList<String> getUploadpiclist() {
        return uploadpiclist;
    }

    public void setUploadpiclist(ArrayList<String> uploadpiclist) {
        this.uploadpiclist = uploadpiclist;
    }



    public String getOwnerCity() {
        return ownerCity;
    }

    public void setOwnerCity(String ownerCity) {
        this.ownerCity = ownerCity;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public House() {}

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerNamename) {
        this.ownerName = ownerName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryID() {
        return primaryID;
    }
    public void setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
    }

    public String getProjectname() {return projectname; }
    public void setProjectname(String projectname) {this.projectname = projectname; }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) { this.url = url; }

    public String getMailid() { return mailid; }
    public void setMailid(String mailid) { this.mailid = mailid; }

    public String getBedroom() { return bedroom; }
    public void setBedroom(String bedroom) {this.bedroom = bedroom; }

    public String getCost() {return cost; }
    public void setCost(String cost) { this.cost = cost; }


    public String getContactnum() {return contactnum; }
    public void setContactnum(String contactnum) {this.contactnum = contactnum; }

}

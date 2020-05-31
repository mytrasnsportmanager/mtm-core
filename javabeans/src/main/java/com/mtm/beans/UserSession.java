package com.mtm.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/9/2020.
 */
public class UserSession {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Long> getVehicleIdList() {
        return vehicleIdList;
    }

    public void setVehicleIdList(List<Long> vehicleIdList) {
        this.vehicleIdList = vehicleIdList;
    }

    public List<Long> getAssociatedDriversList() {
        return associatedDriversList;
    }

    public void setAssociatedDriversList(List<Long> associatedDriversList) {
        this.associatedDriversList = associatedDriversList;
    }

    public List<Long> getAssociatedConsigersList() {
        return associatedConsigersList;
    }

    public void setAssociatedConsigersList(List<Long> associatedConsigersList) {
        this.associatedConsigersList = associatedConsigersList;
    }

    private long id;
    private UserType userType;
    private List<Long> vehicleIdList = new ArrayList();
    private List<Long> associatedDriversList= new ArrayList();
    private List<Long> associatedConsigersList= new ArrayList();

    public long getAssociatedOwnerId() {
        return associatedOwnerId;
    }

    public void setAssociatedOwnerId(long associatedOwnerId) {
        this.associatedOwnerId = associatedOwnerId;
    }

    public long getAssociatedOwnerContact() {
        return associatedOwnerContact;
    }

    public void setAssociatedOwnerContact(long associatedOwnerContact) {
        this.associatedOwnerContact = associatedOwnerContact;
    }

    private long associatedOwnerId;
    private long associatedOwnerContact;

    public List<Long> getAssociatedOwnerList() {
        return associatedOwnerList;
    }

    public void setAssociatedOwnerList(List<Long> associatedOwnerList) {
        this.associatedOwnerList = associatedOwnerList;
    }

    private List<Long> associatedOwnerList = new ArrayList<>();


}

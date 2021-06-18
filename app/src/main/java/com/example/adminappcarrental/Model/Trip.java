package com.example.adminappcarrental.Model;

import java.io.Serializable;

public class Trip implements Serializable {
    private String tripId, uid;
    private String carIdTrip;
    private Long priceCartrip;
    private String startDateTrip;
    private String endDateTrip;
    private Boolean hasDriverTrip;
    private String statusTrip;

    public Trip(){ }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCarIdTrip() {
        return carIdTrip;
    }

    public void setCarIdTrip(String carIdTrip) {
        this.carIdTrip = carIdTrip;
    }

    public Long getPriceCartrip() {
        return priceCartrip;
    }

    public void setPriceCartrip(Long priceCartrip) {
        this.priceCartrip = priceCartrip;
    }

    public String getStartDateTrip() {
        return startDateTrip;
    }

    public void setStartDateTrip(String startDateTrip) {
        this.startDateTrip = startDateTrip;
    }

    public String getEndDateTrip() {
        return endDateTrip;
    }

    public void setEndDateTrip(String endDateTrip) {
        this.endDateTrip = endDateTrip;
    }

    public Boolean getHasDriverTrip() {
        return hasDriverTrip;
    }

    public void setHasDriverTrip(Boolean hasDriverTrip) {
        this.hasDriverTrip = hasDriverTrip;
    }

    public String getStatusTrip() {
        return statusTrip;
    }

    public void setStatusTrip(String statusTrip) {
        this.statusTrip = statusTrip;
    }

    public Trip(String tripId, String uId, String carIdTrip, Long priceCartrip, String startDateTrip, String endDateTrip, Boolean hasDriverTrip, String statusTrip) {
        this.tripId = tripId;
        this.uid = uId;
        this.carIdTrip = carIdTrip;
        this.priceCartrip = priceCartrip;
        this.startDateTrip = startDateTrip;
        this.endDateTrip = endDateTrip;
        this.hasDriverTrip = hasDriverTrip;
        this.statusTrip = statusTrip;
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author pramo
 */
public class Booking {
    
    private int bid;
    private String bcode;
    private int cid; //customer id from user table
    private int did; //driver id from user table
    private Date bdate;//booking date
    private int vid;//vehicle id
    private int pickupid;//destination pickup location id
    private int dropid;//destination drop location id
    private Double distance;
    private Double fare;
    private Double serviceCharge;
    private Double fixedCharge;
    private Double chargePerKM;
    private boolean Approved;
    private boolean paid;
    private String Action;

    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean Approved) {
        this.Approved = Approved;
    }
    

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getPickupid() {
        return pickupid;
    }

    public void setPickupid(int pickupid) {
        this.pickupid = pickupid;
    }

    public int getDropid() {
        return dropid;
    }

    public void setDropid(int dropid) {
        this.dropid = dropid;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(Double fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public Double getChargePerKM() {
        return chargePerKM;
    }

    public void setChargePerKM(Double chargePerKM) {
        this.chargePerKM = chargePerKM;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }
    
    
    
}

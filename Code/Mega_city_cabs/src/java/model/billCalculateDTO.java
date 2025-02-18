/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author pramo
 */
public class billCalculateDTO {

    private Double km;
    private Double serviceCharge;
    private Double taxiFare;
    private Double fixedCharge;
    private Double chargePerKM;
    
    public Double getFixedCharge() {
        return fixedCharge;
    }

    public void setFixedCharge(Double fixedCharge) {
        this.fixedCharge = fixedCharge;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getTaxiFare() {
        return taxiFare;
    }

    public void setTaxiFare(Double taxiFare) {
        this.taxiFare = taxiFare;
    }

    public Double getChargePerKM() {
        return chargePerKM;
    }

    public void setChargePerKM(Double chargePerKM) {
        this.chargePerKM = chargePerKM;
    }
    
    
    
    
    
    
}

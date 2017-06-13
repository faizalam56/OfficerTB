package zmq.com.model;

import java.io.Serializable;

/**

 * Created by zmq162 on 2/2/16.
 */

public class PatientInfo implements Serializable{
    private String pId;
    private String pName;
    private String pState;
    private String pCity;
    private String pVillage;
    private String pAddress;
    private String pSex;
    private String pAge;
    private String pType;
    private String pRegDate;

    public String getpCompliance() {
        return pCompliance;
    }

    public void setpCompliance(String pCompliance) {
        this.pCompliance = pCompliance;
    }

    private String pCompliance;



    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getpVillage() {
        return pVillage;
    }

    public void setpVillage(String pVillage) {
        this.pVillage = pVillage;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpSex() {
        return pSex;
    }

    public void setpSex(String pSex) {
        this.pSex = pSex;
    }

    public String getpAge() {
        return pAge;
    }

    public void setpAge(String pAge) {
        this.pAge = pAge;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpRegDate() {
        return pRegDate;
    }

    public void setpRegDate(String pRegDate) {
        this.pRegDate = pRegDate;
    }
}

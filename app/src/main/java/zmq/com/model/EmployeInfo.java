package zmq.com.model;

import java.io.Serializable;

/**
 * Created by zmq162 on 24/2/16.
 */
public class EmployeInfo implements Serializable{

    private String empName;
    private String empId;
    private String ashaId;
    private String providerVillage;
    private String observerVillage;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpName() {
        return empName;

    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getAshaId() {
        return ashaId;
    }

    public void setAshaId(String ashaId) {
        this.ashaId = ashaId;
    }

    public String getProviderVillage() {
        return providerVillage;
    }

    public void setProviderVillage(String providerVillage) {
        this.providerVillage = providerVillage;
    }

    public String getObserverVillage() {
        return observerVillage;
    }

    public void setObserverVillage(String observerVillage) {
        this.observerVillage = observerVillage;
    }
}

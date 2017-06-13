package zmq.com.model;

import java.util.ArrayList;

/**
 * Created by zmq162 on 30/12/15.
 */
public class OfficerInfo {

    public String getTbUnitId() {
        return tbUnitId;
    }

    public void setTbUnitId(String tbUnitId) {
        this.tbUnitId = tbUnitId;
    }

    private String empId;
    private String officerId;
    private String tbUnitId;
    private String empName;
    private String contactNo;
    private String joiningDate;
    private String empDesignation;

    private ArrayList<StateInfo> stateInfo;

    public ArrayList<StateInfo> getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(ArrayList<StateInfo> stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }


}

package zmq.com.model;

import java.util.ArrayList;

/**
 * Created by zmq162 on 30/12/15.
 */

public class StateInfo {

    private String stateName;
    private String stateId;
    private ArrayList<DistrictInfo> districtInfo;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public ArrayList<DistrictInfo> getDistrictInfo() {
        return districtInfo;
    }

    public void setDistrictInfo(ArrayList<DistrictInfo> districtInfo) {
        this.districtInfo = districtInfo;
    }
}

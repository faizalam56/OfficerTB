package zmq.com.model;

import java.util.ArrayList;

/**
 * Created by zmq162 on 30/12/15.
 */
public class DistrictInfo {

    private String districtName;
    private String districtId;

    public ArrayList<BlockInfo> getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(ArrayList<BlockInfo> blockInfo) {
        this.blockInfo = blockInfo;
    }

    private String stateId;
    private ArrayList<BlockInfo> blockInfo;

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }
}

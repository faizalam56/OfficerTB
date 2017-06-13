package zmq.com.model;

import java.util.ArrayList;

/**
 * Created by zmq162 on 30/12/15.
 */
public class BlockInfo {

    private String blockName;
    private String blockId;

    public ArrayList<VillageInfo> getVillageInfo() {
        return villageInfo;
    }

    public void setVillageInfo(ArrayList<VillageInfo> villageInfo) {
        this.villageInfo = villageInfo;
    }

    private String districtId;
    private ArrayList<VillageInfo> villageInfo;

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
}

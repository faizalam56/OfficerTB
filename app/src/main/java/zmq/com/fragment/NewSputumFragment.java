package zmq.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import zmq.com.activity.MainActivity;
import zmq.com.floatlable.R;
import zmq.com.model.BlockInfo;
import zmq.com.model.DistrictInfo;
import zmq.com.model.StateInfo;
import zmq.com.model.VillageInfo;

public class NewSputumFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolbar;
    private EditText edt_refferFac,edt_pName,edt_address,patient_contact_no,guardian_name,guardian_no;
    private Button btnRegister;
    private Spinner spn_state,spn_district,spn_block,spn_village,spn_disease,spinner_relatinship_list;

    private String disease;
    int relationshipIndexForServer;
    private HashMap<String,String> valueMap;


    String []stateId = null ;
    String []stateName = null;
    int stateIndex;
    ArrayList<StateInfo> stateBundle;

    String []districtId = null ;
    String []districtName = null;
    int districtIndex;
    ArrayList<DistrictInfo> districtBundle;

    String []blockId = null ;
    String []blockName = null;
    int blockIndex;
    ArrayList<BlockInfo> blockBundle;

    String []villageId = null ;
    String []villageName = null;
    int villageIndex;
    ArrayList<VillageInfo> villageBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_sputum, container, false);
    }


    NewSputumCallbacks comm;



    public interface NewSputumCallbacks{

        public void registerClicked(HashMap map);
    }

    public void setNewSputumCallbacks(NewSputumCallbacks comm) {
        this.comm = comm;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    //    toolbar.setBackgroundColor(getResources().getColor(R.color.colorDMCtoolbar));
        toolbar.setTitle("Patient Registration");

        valueMap = new HashMap<String,String>();

        btnRegister = (Button) getActivity().findViewById(R.id.btn_register);
        edt_refferFac = (EditText) getActivity().findViewById(R.id.edit_reffer);
        patient_contact_no= (EditText) getActivity().findViewById(R.id.patient_contact_no);
        guardian_name=(EditText) getActivity().findViewById(R.id.guardian_name);
        guardian_no=(EditText) getActivity().findViewById(R.id.guardian_no);
        edt_pName = (EditText) getActivity().findViewById(R.id.edit_P_name);
        edt_address = (EditText) getActivity().findViewById(R.id.edit_address);

        spn_state = (Spinner) getActivity().findViewById(R.id.spinner_state);
        spn_district = (Spinner) getActivity().findViewById(R.id.spinner_district);
        spn_block = (Spinner) getActivity().findViewById(R.id.spinner_block);
        spn_village = (Spinner) getActivity().findViewById(R.id.spinner_village);
        spn_disease = (Spinner) getActivity().findViewById(R.id.spinner_disease);
        spinner_relatinship_list=(Spinner) getActivity().findViewById(R.id.spinner_relatinship_list);
        btnRegister.setOnClickListener(this);


//        ArrayList<StateInfo> stateBundle = MainActivity.officerInfo.getStateInfo();
//
//        System.out.println("total match states "+stateBundle.size());
//
//        String [] state = {"state 1","state 2","state 3"};
//
//        String []stateId = new String[stateBundle.size()] ;
//        String []stateName = new String[stateBundle.size()] ;
//        String []districtName=null;
//        String []blockName=null;
//        String []villageName=null;


        stateBundle = MainActivity.officerInfo.getStateInfo();

        stateId = new String[stateBundle.size()] ;
        stateName = new String[stateBundle.size()] ;

        for (int i = 0; i < stateBundle.size(); i++) {

            StateInfo stateInfo = stateBundle.get(i);
            stateId[i] = stateInfo.getStateId();
            stateName[i] = stateInfo.getStateName();
        }



        StateInfo stateInfo = stateBundle.get(0);

        districtBundle = stateInfo.getDistrictInfo();
        DistrictInfo districtInfo =  districtBundle.get(stateIndex);

        blockBundle = districtInfo.getBlockInfo();
        BlockInfo blockInfo = blockBundle.get(districtIndex);

        villageBundle = blockInfo.getVillageInfo();
        VillageInfo villageInfo = villageBundle.get(blockIndex);















            /////////////////////////////////////////////////////


        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateName);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_state.setAdapter(adapter_state);
        spn_state.setOnItemSelectedListener(this);
//
//        ArrayAdapter<String> adapter_distt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, districtName);
//        adapter_distt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn_district.setAdapter(adapter_distt);
//        spn_district.setOnItemSelectedListener(this);
//
//        ArrayAdapter<String> adapter_block = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, blockName);
//        adapter_block.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn_block.setAdapter(adapter_block);
//        spn_block.setOnItemSelectedListener(this);
////
////
//        ArrayAdapter<String> adapter_village = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, villageName);
//        adapter_village.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn_village.setAdapter(adapter_village);
//        spn_village.setOnItemSelectedListener(this);

        spn_disease.setOnItemSelectedListener(this);
        spinner_relatinship_list.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()){

            case R.id.spinner_state:

//                System.out.println("onItemSelected  R.id.spinner_state  "+ parent + " ## "+position);
//                state = parent.getItemAtPosition(position).toString();

                stateIndex = position;
                StateInfo stateInfo = stateBundle.get(stateIndex);

                districtBundle = stateInfo.getDistrictInfo();

                for (int j = 0; j < districtBundle.size(); j++) {

                    DistrictInfo districtInfo = districtBundle.get(j);
                    //String[] districtStateId = new String[districtBundle.size()];
                    districtId = new String[districtBundle.size()];
                    districtName = new String[districtBundle.size()];

                   // districtStateId[j] = districtInfo.getStateId();
                    districtId[j] = districtInfo.getDistrictId();
                    districtName[j] = districtInfo.getDistrictName();

                    String dsttStr = districtInfo.getDistrictName();

                    System.out.println("DISTRICT @@ " + dsttStr);

//                    ArrayList<BlockInfo> blockBundle = districtInfo.getBlockInfo();

                    System.out.println("Block Bundle Size === " + blockBundle.size());

                //    blockName = new String[blockBundle.size()];
                }

                ArrayAdapter<String> adapter_distt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, districtName);
                adapter_distt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_district.setAdapter(adapter_distt);
                spn_district.setOnItemSelectedListener(this);

                System.out.println("Select State Id : " + stateId[stateIndex]);

                break;
            case R.id.spinner_district:
//                System.out.println("onItemSelected  spinner_district  "+ parent + " ## "+position);
//                district = parent.getItemAtPosition(position).toString();
                districtIndex = position;
                DistrictInfo districtInfo =  districtBundle.get(districtIndex);
                blockBundle = districtInfo.getBlockInfo();

                blockId = new String[blockBundle.size()];
                blockName = new String[blockBundle.size()] ;

                for (int k = 0; k < blockBundle.size(); k++) {

                    BlockInfo blockInfo = blockBundle.get(k);
                    blockId[k] = blockInfo.getBlockId();
                    blockName[k] = blockInfo.getBlockName();

                    String blkStr = blockInfo.getBlockName();

                    System.out.println("BLOCK ## " + blkStr);
//                    ArrayList<VillageInfo> villageBundle = blockInfo.getVillageInfo();
                    System.out.println("Village Bundle Size === " + villageBundle.size());
//                    villageName = new String[villageBundle.size()];
                }

                ArrayAdapter<String> adapter_block = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, blockName);
                adapter_block.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_block.setAdapter(adapter_block);
                spn_block.setOnItemSelectedListener(this);

                System.out.println("Select District Id : " + districtId[districtIndex]);
                break;
            case R.id.spinner_block:
//                System.out.println("onItemSelected  R.id.spinner_block  "+ parent + " ## "+position);
//                block = parent.getItemAtPosition(position).toString();
                blockIndex = position;
System.out.println(position+ "first element selected in block");
                BlockInfo blockInfo =  blockBundle.get(blockIndex);
                villageBundle = blockInfo.getVillageInfo();

                villageId = new String[villageBundle.size()];
                villageName = new String[villageBundle.size()] ;

                for (int l = 0; l <villageBundle.size() ; l++) {


                    VillageInfo villageInfo = villageBundle.get(l);
                    villageId[l] = villageInfo.getVillageId();
                    villageName[l] = villageInfo.getVillageName();

                    String vlgStr = villageInfo.getVillageName();

                    System.out.println("VILLAGE ** "+vlgStr);
                }

                ArrayAdapter<String> adapter_village = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, villageName);
                adapter_village.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_village.setAdapter(adapter_village);
                spn_village.setOnItemSelectedListener(this);

                System.out.println("Select block Id : " + blockId[blockIndex]);
                break;
            case R.id.spinner_village:
//                System.out.println("onItemSelected  R.id.spinner_village  "+ parent + " ## "+position);
//                villages = parent.getItemAtPosition(position).toString();
                villageIndex = position;


                System.out.println("Select Village Id : "+villageId[villageIndex] );
                break;
            case R.id.spinner_disease:
              //  System.out.println("onItemSelected  R.id.spinner_village  "+ parent + " ## "+position);
                disease = parent.getItemAtPosition(position).toString();

                break;
            case R.id.spinner_relatinship_list:
                //relationshipId=parent.getItemAtPosition(position).toString();
                relationshipIndexForServer=position+1;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("onNothingSelected    "+parent );

        switch (parent.getId()){

            case R.id.spinner_state:
                System.out.println("onNothingSelected  R.id.spinner_state  "+ parent );
                break;
            case R.id.spinner_district:
                System.out.println("onNothingSelected  spinner_district  "+ parent );
                break;
            case R.id.spinner_block:
                System.out.println("onNothingSelected  R.id.spinner_block  "+ parent );
                break;
            case R.id.spinner_village:
                System.out.println("onNothingSelected  R.id.spinner_village  "+ parent );
                break;
            case R.id.spinner_disease:
                System.out.println("onNothingSelected  R.id.spinner_disease  " + parent );

                break;
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.btn_register:

                valueMap.put("referringFacilityName", edt_refferFac.getText().toString());
                valueMap.put("patientName", edt_pName.getText().toString());
                valueMap.put("stateId", stateId[stateIndex]);//"2"
                valueMap.put("districtId", districtId[districtIndex]);//"2"
                valueMap.put("blockId",blockId[blockIndex]);// "7"
                valueMap.put("villageId", villageId[villageIndex]);//"32"
                valueMap.put("completeAddress", edt_address.getText().toString());
                valueMap.put("phoneNo", patient_contact_no.getText().toString());

                valueMap.put("guardianname", guardian_name.getText().toString());
                valueMap.put("guardianNo", guardian_no.getText().toString());
                valueMap.put("relationship", String.valueOf(relationshipIndexForServer));
                valueMap.put("diseaseType", disease);


                valueMap.put("empId", MainActivity.officerInfo.getEmpId());
                valueMap.put("dmcId", MainActivity.officerInfo.getOfficerId());
//                String todayDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());    //commented by bilal



                String todayDate = new SimpleDateFormat("yyyy/MM/dd").format(MainActivity.ServerTodaydate);
                valueMap.put("sputumTakenDate", todayDate);


//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                String dateString = dateFormat.format(MainActivity.ServerTodaydate);
//                sputumDate.setText(dateString);
//




                valueMap.put("specimen", "a");

                System.out.println("sputumTakenDate first  eeeeeeeeeeeeee  " + todayDate);
                System.out.println("Finally Select State Id : " + stateId[stateIndex]);
                System.out.println("Finally Select District Id : " + districtId[districtIndex]);
                System.out.println("Finally Select block Id : " + blockId[blockIndex]);
                System.out.println("Finally Select Village Id : " + villageId[villageIndex]);

                System.out.println("Finally Select Contact No : " + MainActivity.officerInfo.getContactNo());
                System.out.println("Finally Select Emp Id : " + MainActivity.officerInfo.getEmpId());
                System.out.println("Finally Select Officer Id : " + MainActivity.officerInfo.getOfficerId());
                System.out.println("Finally Select Today : " + todayDate);


                System.out.println("Block index without selecting " + blockIndex);
                System.out.println("Block index without selecting " + relationshipIndexForServer);


                if(!edt_refferFac.getText().toString().equals("") && !edt_pName.getText().toString().equals("") && !edt_address.getText().toString().equals("")) {
                    comm.registerClicked(valueMap);
                }
                else{

                    Toast.makeText(getContext(),"Please fill all the fields first",Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }
}


/*for (int i = 0; i < stateBundle.size(); i++) {

           StateInfo stateInfo =  stateBundle.get(i);
            stateId[i] =  stateInfo.getStateId();
            stateName[i] = stateInfo.getStateName();

            ArrayList<DistrictInfo> disttBundle = stateInfo.getDistrictInfo();

            for (int j = 0; j < disttBundle.size(); j++) {

                DistrictInfo districtInfo = disttBundle.get(j);
                String []districtStateId = new String[disttBundle.size()] ;
                String []districtId = new String[disttBundle.size()] ;
                districtName = new String[disttBundle.size()] ;

                districtStateId[j] = districtInfo.getStateId();
                districtId[j] = districtInfo.getDistrictId();
                districtName[j] = districtInfo.getDistrictName();

                String dsttStr = districtInfo.getDistrictName();

                System.out.println("DISTRICT @@ "+dsttStr);

                ArrayList<BlockInfo> blockBundle = districtInfo.getBlockInfo();

                System.out.println("Block Bundle Size === " + blockBundle.size());

                blockName = new String[blockBundle.size()] ;
                for (int k = 0; k < blockBundle.size(); k++) {

                    BlockInfo blockInfo = blockBundle.get(k);

                    String []blockDistrictId = new String[blockBundle.size()] ;
                    String []blockId = new String[blockBundle.size()] ;
                //    blockName = new String[blockBundle.size()] ;

                    blockDistrictId[k] = blockInfo.getDistrictId();
                    blockId[k] = blockInfo.getBlockId();
                    blockName[k] = blockInfo.getBlockName();

                    String blkStr = blockInfo.getBlockName();

                    System.out.println("BLOCK ## "+blkStr);
                    ArrayList<VillageInfo> villageBundle = blockInfo.getVillageInfo();
                    System.out.println("Village Bundle Size === " + villageBundle.size());
                    villageName = new String[villageBundle.size()] ;
                    for (int l = 0; l <villageBundle.size() ; l++) {


                        VillageInfo villageInfo = villageBundle.get(l);

                        String []villageBlockId = new String[villageBundle.size()] ;
                        String []villageId = new String[villageBundle.size()] ;


                        villageBlockId[l] = villageInfo.getBlockId();
                        villageId[l] = villageInfo.getVillageId();
                        villageName[l] = villageInfo.getVillageName();

                        String vlgStr = villageInfo.getVillageName();

                        System.out.println("VILLAGE ** "+vlgStr);
                    }
                }
            }
        }*/
package zmq.com.activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import zmq.com.floatlable.R;
import zmq.com.fragment.AssignCompPatientListFragment;
import zmq.com.fragment.AssignComplianceFragment;
import zmq.com.fragment.AssignHealthWorkerFragment;
import zmq.com.fragment.AssignHealthWorkerPatientListFragment;
import zmq.com.fragment.ExistingPatientSputumSubmitFragment;
import zmq.com.fragment.ExtendDosePatientListFragment;
import zmq.com.fragment.LoginFragment;
import zmq.com.fragment.MOPHIpatientListFragment;
import zmq.com.fragment.NewSputumFragment;
import zmq.com.fragment.OfficerMenu;
import zmq.com.fragment.PatientListFragment;
import zmq.com.fragment.PatientPermanentRegistrationFragment;
import zmq.com.fragment.ProgressFragment;
import zmq.com.fragment.SplashFragment;
import zmq.com.fragment.SputumReportSubmitFragment;
import zmq.com.fragment.StsFragmentMenu;
import zmq.com.fragment.TestReportFragment;
import zmq.com.fragment.VerifyPetientAddressFragment;
import zmq.com.model.BlockInfo;
import zmq.com.model.DistrictInfo;
import zmq.com.model.EmployeInfo;
import zmq.com.model.OfficerInfo;
import zmq.com.model.PatientInfo;
import zmq.com.model.SputumInfo;
import zmq.com.model.StateInfo;
import zmq.com.model.VillageInfo;
import zmq.com.networks.MyAsyncTask;
import zmq.com.networks.Utility;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginCallbacks,OfficerMenu.OfficerMenuCallbacks,NewSputumFragment.NewSputumCallbacks, ExistingPatientSputumSubmitFragment.ExistingPatientSputumSubmitCallbacks, SputumReportSubmitFragment.SputumReportSubmitCallbacks,PatientListFragment.PatientListFragmentCallbacks, FragmentManager.OnBackStackChangedListener, StsFragmentMenu.OfficerMenuCallbacks, VerifyPetientAddressFragment.VerifyAddressCallbacks, MOPHIpatientListFragment.MOPHIpatientCallbacks ,PatientPermanentRegistrationFragment.OnPermanentRegListener, AssignComplianceFragment.OnAssignComplianceListener, AssignCompPatientListFragment.AssignCompPatientCallbacks, AssignHealthWorkerPatientListFragment.AssignHealthWorkerPatientCallbacks, AssignHealthWorkerFragment.OnAssignHealthWorkerListener, ExtendDosePatientListFragment.ExtendDosePatientCallbacks, SplashFragment.SplashCallbacks {

  private FragmentManager manager;
    Handler mHandler;
    JSONObject jObject;
    public static OfficerInfo officerInfo;
    public static ArrayList <SputumInfo> sputumInfos;
    int designation;
    String patientPermanentID;
    public static String PhiID,EmpID;

   public static  Date ServerTodaydate = null;
   public  static  Date  ServerdateTwoDaysPrevious=null;
    private String screenTag="NA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(this);

        if(savedInstanceState == null){

            FragmentTransaction transaction = manager.beginTransaction();
            SplashFragment mSplashFragment = new SplashFragment();
            transaction.add(R.id.main_container, mSplashFragment, "SplashFragment");
            transaction.commit();
            mSplashFragment.setSplashCallbacks(this);

//            FragmentTransaction transaction = manager.beginTransaction();
//            LoginFragment mLoginFragment = new LoginFragment();
//            transaction.add(R.id.main_container, mLoginFragment, "LoginFragment");
//         //   transaction.addToBackStack("LoginScreen");
//            transaction.commit();
//            mLoginFragment.setLoginCallbacks(this);
        }

    }

    @Override
    public void splashTouch() {

            FragmentTransaction transaction = manager.beginTransaction();
            LoginFragment mLoginFragment = new LoginFragment();
            transaction.replace(R.id.main_container, mLoginFragment, "LoginFragment");
            transaction.commit();
            mLoginFragment.setLoginCallbacks(this);
    }

    @Override
    public void loginClicked(String userId,  String password) {

        loadingFragment( getResources().getString(R.string.progress_txt_1));

        String serviceName = "officerLogin";
        HashMap<String,String> paramsValue = new HashMap<String,String>();
        paramsValue.put("username", userId);
        paramsValue.put("password", password);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        if(jObject.has("msg")){

                            Toast.makeText(MainActivity.this, "User or password does not exist", Toast.LENGTH_SHORT).show();
                            manager.popBackStack();
                        }else {

                            designation = Integer.parseInt(jObject.getString("designation"));


                            switch (designation) {


                                case 1:

                                /* perform the work of DMC*/
                                    getDataForDMC(jObject);
                                    System.out.println("Parse succesfully");
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    OfficerMenu mOfficerMenu = new OfficerMenu();
                                    transaction.replace(R.id.main_container, mOfficerMenu, "OfficerMenu");
                                    transaction.addToBackStack("DMCscreen");
                                    transaction.commit();
                                    mOfficerMenu.setOfficerMenuCallbacks(MainActivity.this);
                                    setTheme(R.style.DMCtheme);
                                    break;
                                case 2:
                                    /* perform the work of STS*/
                                    getDataForSTSandMOPHI(jObject);
                                    System.out.println("STS : " + jObject.toString());
                                    System.out.println("Parse succesfully");
                                    FragmentTransaction transaction1 = manager.beginTransaction();
                                    OfficerMenu mOfficerMenu1 = new OfficerMenu();
                                    transaction1.replace(R.id.main_container, mOfficerMenu1, "OfficerMenu");
                                    transaction1.addToBackStack("STSscreen");
                                    transaction1.commit();
                                    mOfficerMenu1.setOfficerMenuCallbacks(MainActivity.this);
                                    setTheme(R.style.STStheme);
                                    break;
                                case 3:
                                    /* perform the work of MOPHI*/
                                    getDataForSTSandMOPHI(jObject);
                                    System.out.println("MOPHI : " + jObject.toString());
                                    System.out.println("Parse succesfully");
                                    FragmentTransaction transaction2 = manager.beginTransaction();
                                    OfficerMenu mOfficerMenu2 = new OfficerMenu();
                                    transaction2.replace(R.id.main_container, mOfficerMenu2, "OfficerMenu");
                                    transaction2.addToBackStack("MOPHIscreen");
                                    transaction2.commit();
                                    mOfficerMenu2.setOfficerMenuCallbacks(MainActivity.this);
                                    setTheme(R.style.MOPHItheme);
                                    break;
                            }
                        }
                    }
                    else{
                       screenTag="LoginFragment";
                        showErrorDialog();
//                        AlertDialog.Builder dialog = myAlertDialog(1,"ALERT", Utility.EXCEPTION_STRING );
//                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//                                manager.popBackStack("LoginFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//                                manager.popBackStack();
//                            }
//                        });
//                        dialog.show();

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * loginClicked * JSONException ");
                    e.printStackTrace();

                    Utility.EXCEPTION_STRING=e.getMessage();
                    screenTag="LoginFragment";
                    showErrorDialog();
//                    AlertDialog.Builder dialog = myAlertDialog(1,"ALERT", Utility.EXCEPTION_STRING );
//                    dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            manager.popBackStack("LoginFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//                            manager.popBackStack();
//                        }
//                    });
//                    dialog.show();

                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    private void showErrorDialog(){
        AlertDialog.Builder dialog = myAlertDialog(1,"ALERT", Utility.EXCEPTION_STRING );
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                manager.popBackStack(screenTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                manager.popBackStack();
            }
        });
        dialog.show();
    }

    private void getDataForSTSandMOPHI(JSONObject jObject) {

        Iterator iterator = jObject.keys();// return the all keys
        officerInfo = new OfficerInfo();

        while (iterator.hasNext()) {

            Object obj = iterator.next();

            if (obj != null) {

                try {//tbUnitId

//                    if (obj.equals("tbUnitId")) {
//                        officerInfo.setTbUnitId(jObject.getString("tbUnitId"));
//                    }
                    if (obj.equals("phiId")) {
                        PhiID=jObject.getString("phiId");
                        System.out.println(PhiID+" vvvvvvvvvvvvvvvvvvvvvvvvv");
                        officerInfo.setOfficerId(jObject.getString("phiId"));
                    }
                    if (obj.equals("empId")) {
                        officerInfo.setEmpId(jObject.getString("empId"));
                        EmpID=jObject.getString("empId");
                        System.out.println(EmpID + "ssfsdfgsdggggggggggggggggg");
                    }
                    if (obj.equals("empName")) {
                        officerInfo.setEmpName(jObject.getString("empName"));
                    }
                    if (obj.equals("contactNo")) {
                        officerInfo.setContactNo(jObject.getString("contactNo"));
                    }
                    if (obj.equals("designation")) {
                        officerInfo.setEmpDesignation(jObject.getString("designation"));
                    }
                    if (obj.equals("dateOfJoining")) {
                        officerInfo.setJoiningDate(jObject.getString("dateOfJoining"));
                    }
                    if (obj.equals("serverDate")) {
                        System.out.println(jObject.getString("serverDate") + " serverrrrrrrrrrrdateeeeee as Stringg");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {

                            ServerTodaydate = formatter.parse(jObject.getString("serverDate").toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        System.out.println(ServerTodaydate+" serverrrrrrrrrrrdateeeeee as Date");
                     //   ServerTodaydate

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(ServerTodaydate); // convert your date to Calendar object
                        int daysToDecrement = -2;
                        cal.add(Calendar.DATE, daysToDecrement);
                        ServerdateTwoDaysPrevious = cal.getTime(); // again get back your date object
                        System.out.println(ServerdateTwoDaysPrevious+" serverrrrrrrrrrrdateeeeee as Date 2 day previous");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = dateFormat.format(ServerdateTwoDaysPrevious);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


    private void getDataForDMC(JSONObject jObject) {




        Iterator iterator = jObject.keys();// return the all keys

        officerInfo = new OfficerInfo();

        ArrayList<StateInfo> stateList = new ArrayList<StateInfo>();
        ArrayList<DistrictInfo> disttList = new ArrayList<DistrictInfo>();
        ArrayList<BlockInfo> blockList = new ArrayList<BlockInfo>();
        ArrayList<VillageInfo> villageList = new ArrayList<VillageInfo>();

        while (iterator.hasNext()) {

            Object obj = iterator.next();

            if (obj != null) {


                try {

                        if (obj.equals("empId")) {
                            officerInfo.setEmpId(jObject.getString("empId"));
                        }
                        if (obj.equals("dmcId")) {
                            officerInfo.setOfficerId(jObject.getString("dmcId"));
                        }
                        if (obj.equals("empName")) {
                            officerInfo.setEmpName(jObject.getString("empName"));
                        }
                        if (obj.equals("contactNo")) {
                            officerInfo.setContactNo(jObject.getString("contactNo"));
                        }
                        if (obj.equals("designation")) {
                            officerInfo.setEmpDesignation(jObject.getString("designation"));
                        }
                        if (obj.equals("dateOfJoining")) {
                            officerInfo.setJoiningDate(jObject.getString("dateOfJoining"));
                        }
                        if (jObject.getString("stateInfo") != null && obj.equals("stateInfo")) {
                            JSONArray jArray = jObject.getJSONArray("stateInfo");
                            System.out.println("Total records stateInfo :  " + jArray.length());

                            for (int i=0; i < jArray.length();i++) {

                                StateInfo stateInfo = new StateInfo();
                                JSONObject obj2 = (JSONObject) jArray.get(i);
                                stateInfo.setStateId(obj2.getString("Sid"));
                                stateInfo.setStateName(obj2.getString("Sname"));
                                stateList.add(stateInfo);
                                //officerInfo.setStateInfo(stateList);
                            }
                        }
                        if (jObject.getString("districtInfo") != null && obj.equals("districtInfo")) {

                            JSONArray jArray = jObject.getJSONArray("districtInfo");
                            System.out.println("Total records districtInfo : "+jArray.length());

                            for (int i=0; i < jArray.length();i++) {

                                DistrictInfo districtInfo = new DistrictInfo();
                                JSONObject obj2 = (JSONObject) jArray.get(i);
                                districtInfo.setDistrictId(obj2.getString("Did"));
                                districtInfo.setDistrictName(obj2.getString("Dname"));
                                districtInfo.setStateId(obj2.getString("Sid"));
                                disttList.add(districtInfo);
                            }
                        }
                        if (jObject.getString("blockInfo") != null && obj.equals("blockInfo")) {

                            JSONArray jArray = jObject.getJSONArray("blockInfo");
                            System.out.println("Total records blockInfo : "+jArray.length());

                            for (int i=0; i < jArray.length();i++) {

                                BlockInfo blockInfo = new BlockInfo();
                                JSONObject obj2 = (JSONObject) jArray.get(i);

                                blockInfo.setBlockId(obj2.getString("Bid"));
                                blockInfo.setBlockName(obj2.getString("Bname"));
                                blockInfo.setDistrictId(obj2.getString("Did"));
                                blockList.add(blockInfo);
                            }
                        }
                        if (jObject.getString("VillageInfo") != null && obj.equals("VillageInfo")) {

                            JSONArray jArray = jObject.getJSONArray("VillageInfo");
                            System.out.println("Total records VillageInfo : "+jArray.length());

                            for (int i=0; i < jArray.length();i++) {

                                VillageInfo villageInfo = new VillageInfo();
                                JSONObject obj2 = (JSONObject) jArray.get(i);

                                villageInfo.setBlockId(obj2.getString("Bid"));
                                villageInfo.setVillageId(obj2.getString("Vid"));
                                villageInfo.setVillageName(obj2.getString("Vname"));
                                villageList.add(villageInfo);
                            }
                        }

                    if (obj.equals("serverDate")) {
                        System.out.println(jObject.getString("serverDate") + " serverrrrrrrrrrrdateeeeee as Stringg in dmc");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        try {

                            ServerTodaydate = formatter.parse(jObject.getString("serverDate").toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }




                } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }




            }
        }

        System.out.println("Object State : " + stateList.size());
        System.out.println("Object District : " + disttList.size());
        System.out.println("Object Block : " + blockList.size());
        System.out.println("Object Village : " + villageList.size());
        System.out.println("\n########################################\n");

        ArrayList<StateInfo> stateBundle = new ArrayList<StateInfo>();

        for (int i = 0; i < stateList.size(); i++) {

            ArrayList<DistrictInfo> districtBundle = new ArrayList<DistrictInfo>();

            StateInfo state = stateList.get(i);
            String stateId = state.getStateId();

            DistrictInfo district = null;
            for (int j = 0; j <disttList.size(); j++) {

                district =  disttList.get(j);
                String districtId = district.getDistrictId();
                String districtSateId = district.getStateId();

                if(stateId.equals(districtSateId)){
                    // add this district

                    System.out.println("Add District : "+district.getDistrictName());

                    BlockInfo block =null;

                    ArrayList<BlockInfo> blockBundle =  new ArrayList<BlockInfo>();

                    for (int k = 0; k < blockList.size(); k++) {

                        block = blockList.get(k);
                        String blockId = block.getBlockId();
                        String blockDistrictkId = block.getDistrictId();

                        if (districtId.equals(blockDistrictkId)) {
                            // add this block
                            System.out.println("Add Block : "+block.getBlockName());
                            VillageInfo village =null;

                            ArrayList<VillageInfo> villageBundle = new ArrayList<VillageInfo>();

                            for (int l = 0; l < villageList.size(); l++) {

                                village = villageList.get(l);
                                String villageBlockId = village.getBlockId();

                                if(blockId.equals(villageBlockId)){
                                    // add this village
                                    villageBundle.add(village);
                                    System.out.println("Add Village : "+village.getVillageName());
                                }
                            }
                            /// add these village in particular block
                            block.setVillageInfo(villageBundle);
                            System.out.println("Village Bundle added "+ block +" "+ k );
                            System.out.println("Object Village Bundle :: " + villageBundle.size());

                            /// add these block in particular district
                            blockBundle.add(block);
                            district.setBlockInfo(blockBundle);
                            System.out.println("Block Bundle added " + district +" "+ j);
                            System.out.println("Object in Block Bundle :: " + blockBundle.size());

                        }

                    }
                    /// add these district in particular state
                    districtBundle.add(district);
                    state.setDistrictInfo(districtBundle);

                }

            }
            stateBundle.add(state);
            officerInfo.setStateInfo(stateBundle);
            System.out.println("Object in District Bundle :: " + districtBundle.size());

        }
    }


    @Override
    public void menuItemClicked(int itemNo) {

        switch (designation){
            case 1:// Start DMC MENU

                switch (itemNo){
                    case 0:
                        screenTag="NewSputumFragment";

                        loadingFragment(getResources().getString(R.string.progress_txt_2));
                        FragmentTransaction transaction = manager.beginTransaction();
                        NewSputumFragment mNewSputumFragment = new NewSputumFragment();
                        transaction.replace(R.id.main_container, mNewSputumFragment, "NewSputumFragment");
                        transaction.addToBackStack("NewSputumScreen");
                        transaction.commit();
                        mNewSputumFragment.setNewSputumCallbacks(this);
                        break;

                    case 1:
                        screenTag="ExistingPatientSputumSubmitFragment";

                        loadingFragment(getResources().getString(R.string.progress_txt_2));
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        ExistingPatientSputumSubmitFragment mExistingPatientSputumSubmitFragment = new ExistingPatientSputumSubmitFragment();
                        transaction1.replace(R.id.main_container, mExistingPatientSputumSubmitFragment, "ExistingPatientSputumSubmitFragment");
                        transaction1.addToBackStack("ExistingPatientSputumSubmitScreen");
                        transaction1.commit();
                        mExistingPatientSputumSubmitFragment.setExistingPatientSputumSubmitCallbacks(this);


                        break;
                    case 2:
                        screenTag="SputumReportSubmitFragment";

                        loadingFragment(getResources().getString(R.string.progress_txt_2));
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        SputumReportSubmitFragment mSputumReportSubmitFragment = new SputumReportSubmitFragment();
                        transaction2.replace(R.id.main_container, mSputumReportSubmitFragment, "SputumReportSubmitFragment");
                        transaction2.addToBackStack("SputumReportSubmitScreen");
                        transaction2.commit();
                        mSputumReportSubmitFragment.setSputumReportSubmitCallbacks(this);
                        break;
                    case 3:

                        onRequestPatientList();
                        break;

                    default:
                        break;
                }
                // Close DMC MENU
                break;
            case 2:// Start STS MENU

                switch (itemNo) {
                    case 0:
                        onVerifyPendingAddressPetientList();
                        break;

                    default:
                        break;
                }

                // Close STS MENU
                break;
            case 3:// Start MOPHI MENU

                switch (itemNo) {
                    case 0:
                        onRequestMophiPatientList();
                        break;
                    case 1:
                        onRequestAssignCompPatientList();
                        break;
                    case 2:
                        onRequestAssignHealthWorkerPatientList();
                        break;
                    case 3:
                        onRequestExtendDosePatientList();
                        break;
                    case 4:
                        Toast.makeText(this, "coming  soon ", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

                // Close MOPHI MENU
                break;
            default:
            break;

        }
    }

    private void onRequestExtendDosePatientList() {

        loadingFragment(getResources().getString(R.string.progress_txt_2));

        HashMap<String,String> paramsValue = new HashMap<String, String>();

        paramsValue.put("phiId", MainActivity.officerInfo.getOfficerId());

        String serviceName = "patientListforExtendPhase";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        System.out.println("Total records SputumDate :  " + jObject.toString());

                        ArrayList <PatientInfo> patientInfo = new ArrayList<>();
                        if(jObject.get("patientDetails")!= null){

                            if(jObject.get("patientDetails") instanceof Integer){

                                manager.popBackStack();
                                Toast.makeText(MainActivity.this, "There is no more patient", Toast.LENGTH_SHORT).show();

                            }else {

                                JSONArray jArray = jObject.getJSONArray("patientDetails");
                                System.out.println("Total records get :  " + jArray.length());

                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj2 = (JSONObject) jArray.get(i);
                                    PatientInfo info = new PatientInfo();
                                    info.setpId(obj2.getString("tbPatientId"));
                                    info.setpName(obj2.getString("patientName"));
                                    info.setpAddress(obj2.getString("completeAddress"));//completeAddress
                                    info.setpState(obj2.getString("state_name"));//state_name
                                    info.setpCity(obj2.getString("city_name"));//city_name
                                    info.setpVillage(obj2.getString("villageName"));//villageName
                                    patientInfo.add(info);
                                }

                                FragmentTransaction transaction = manager.beginTransaction();
                                ExtendDosePatientListFragment mExtendDosePatientListFragment = new ExtendDosePatientListFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("patients", patientInfo);
                                mExtendDosePatientListFragment.setArguments(args);
                                transaction.replace(R.id.main_container, mExtendDosePatientListFragment, "ExtendDosePatientListFragment");
                                transaction.addToBackStack("ExtendDosePatientListScreen");
                                transaction.commit();
                                mExtendDosePatientListFragment.setExtendDosePatientCallbacks(MainActivity.this);

                            }
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * onRequestMophiPatientList * Exception "+e);
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    private void onRequestAssignHealthWorkerPatientList() {

        loadingFragment(getResources().getString(R.string.progress_txt_2));

        HashMap<String,String> paramsValue = new HashMap<String, String>();
        paramsValue.put("phiId", MainActivity.officerInfo.getOfficerId());
        String serviceName = "assignHealthworker";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        if(jObject.get("patientList")!= null){

                            if(jObject.get("patientList") instanceof Integer){

                                System.out.println("Total records SputumDate :  Pappu" );
                                manager.popBackStack();
                                Toast.makeText(MainActivity.this, "There is no more patient", Toast.LENGTH_SHORT).show();

                            }else {

                                ArrayList <PatientInfo> patientInfo = new ArrayList<>();
                                ArrayList <EmployeInfo> providerInfo = new ArrayList<>();
                                ArrayList <EmployeInfo> observerInfo = new ArrayList<>();

                                JSONArray jsonPatientArray = jObject.getJSONArray("patientList");
                                System.out.println("Total records get :  " + jsonPatientArray.length());

                                for (int i = 0; i < jsonPatientArray.length(); i++) {

                                    JSONObject obj2 = (JSONObject) jsonPatientArray.get(i);
                                    PatientInfo info = new PatientInfo();
                                    info.setpId(obj2.getString("tbPatientId"));
                                    info.setpName(obj2.getString("patientName"));
                                    info.setpSex(obj2.getString("sex"));
                                    info.setpAge(obj2.getString("age"));
                                    info.setpType(obj2.getString("patientType"));
                                    info.setpRegDate(obj2.getString("dateOfRegistration"));
                                    info.setpCompliance(obj2.getString("complianceMethod"));
                                    patientInfo.add(info);
                                }

                                JSONArray jsonProviderArray = jObject.getJSONArray("healthworkerList");
                                System.out.println("Total provider get :  " + jsonPatientArray.length());
                                EmployeInfo empProviderInfo = new EmployeInfo();
                                empProviderInfo.setEmpName("--- Select ---");
                                empProviderInfo.setEmpId("");
                                empProviderInfo.setAshaId("");
                                empProviderInfo.setProviderVillage("");
                                empProviderInfo.setUserName("");
                                providerInfo.add(empProviderInfo);

                                for (int i = 0; i < jsonProviderArray.length(); i++) {

                                        JSONObject obj32 = (JSONObject) jsonProviderArray.get(i);
                                        empProviderInfo = new EmployeInfo();
                                        empProviderInfo.setEmpName(obj32.getString("empName"));
                                        empProviderInfo.setEmpId(obj32.getString("empId"));
                                        empProviderInfo.setAshaId(obj32.getString("ashaId"));
                                        empProviderInfo.setProviderVillage(obj32.getString("villageName"));
                                        empProviderInfo.setUserName(obj32.getString("username"));
                                        providerInfo.add(empProviderInfo);
                                    System.out.println("provider name " + obj32.getString("empName"));

                                }

                                JSONArray jsonObserverArray = jObject.getJSONArray("observerList");
                                System.out.println("Total observer get :  " + jsonPatientArray.length());
                                EmployeInfo empObserverInfo = new EmployeInfo();
                                empObserverInfo.setEmpName("--- Select ---");
                                empObserverInfo.setEmpId("");
                                empObserverInfo.setAshaId("");
                                empObserverInfo.setProviderVillage("");
                                empObserverInfo.setUserName("");
                                observerInfo.add(empObserverInfo);

                                for (int i = 0; i < jsonObserverArray.length(); i++) {

                                    JSONObject obj2 = (JSONObject) jsonObserverArray.get(i);
                                    empObserverInfo = new EmployeInfo();
                                    empObserverInfo.setEmpName(obj2.getString("empName"));
                                    empObserverInfo.setEmpId(obj2.getString("empId"));
                                    empObserverInfo.setAshaId(obj2.getString("ashaId"));
                                    empObserverInfo.setProviderVillage(obj2.getString("villageName"));// same for provider village
                                    empObserverInfo.setUserName(obj2.getString("username"));
                                    observerInfo.add(empObserverInfo);
                                    System.out.println("observer name "+obj2.getString("empName"));
                                }

                                FragmentTransaction transaction = manager.beginTransaction();
                                AssignHealthWorkerPatientListFragment mAssignHealthWorkerPatientListFragment = new AssignHealthWorkerPatientListFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("patients", patientInfo);
                                args.putSerializable("providers", providerInfo);
                                args.putSerializable("observers", observerInfo);
                                mAssignHealthWorkerPatientListFragment.setArguments(args);
                                transaction.replace(R.id.main_container, mAssignHealthWorkerPatientListFragment, "AssignHealthWorkerPatientListFragment");
                                transaction.addToBackStack("MOPHIHealthWorkerPatientListScreen");
                                transaction.commit();
                                mAssignHealthWorkerPatientListFragment.setAssignHealthWorkerPatientCallbacks(MainActivity.this);
                            }
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * onRequestMophiPatientList * Exception "+e);
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    private void onRequestAssignCompPatientList() {

        loadingFragment(getResources().getString(R.string.progress_txt_3));
        HashMap<String,String> paramsValue = new HashMap<String, String>();
        paramsValue.put("phiId", MainActivity.officerInfo.getOfficerId());
        String serviceName = "assignComplianceMethod";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){
                        ArrayList <PatientInfo> patientInfo = new ArrayList<>();
                        if(jObject.get("patientList")!= null){

                            if(jObject.get("patientList") instanceof Integer){

                                manager.popBackStack();
                                Toast.makeText(MainActivity.this, "There is no more patient", Toast.LENGTH_SHORT).show();

                            }else {

                                JSONArray jArray = jObject.getJSONArray("patientList");
                                System.out.println("Total records get :  " + jArray.length());

                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj2 = (JSONObject) jArray.get(i);
                                    PatientInfo info = new PatientInfo();
                                    info.setpId(obj2.getString("tbPatientId"));
                                    info.setpName(obj2.getString("patientName"));
                                    info.setpSex(obj2.getString("sex"));
                                    info.setpAge(obj2.getString("age"));
                                    info.setpType(obj2.getString("patientType"));
                                    info.setpRegDate(obj2.getString("dateOfRegistration"));
                                    patientInfo.add(info);
                                }

                                FragmentTransaction transaction = manager.beginTransaction();
                                AssignCompPatientListFragment mAssignCompPatientListFragment = new AssignCompPatientListFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("patients", patientInfo);
                                mAssignCompPatientListFragment.setArguments(args);
                                transaction.replace(R.id.main_container, mAssignCompPatientListFragment, "AssignCompPatientListFragment");
                                transaction.addToBackStack("MOPHICompPatientListScreen");
                                transaction.commit();
                                mAssignCompPatientListFragment.setAssignCompPatientCallbacks(MainActivity.this);

                            }
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * onRequestMophiPatientList * Exception "+e);
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    private void onRequestMophiPatientList() {

        loadingFragment(getResources().getString(R.string.progress_txt_3));

        HashMap<String,String> paramsValue = new HashMap<String, String>();
        paramsValue.put("phiId", MainActivity.officerInfo.getOfficerId());
        String serviceName = "patientDetailsForMOPHI";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        System.out.println("Total records SputumDate :  " + jObject.toString());
                        ArrayList <PatientInfo> patientInfo = new ArrayList<>();
                        if(jObject.get("patientInfo")!= null){

                            if(jObject.get("patientInfo") instanceof Integer){
                                manager.popBackStack();
                                Toast.makeText(MainActivity.this, "There is no more patient", Toast.LENGTH_SHORT).show();

                            }else {

                                JSONArray jArray = jObject.getJSONArray("patientInfo");
                                System.out.println("Total records SputumDate :  " + jArray.length());

                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj2 = (JSONObject) jArray.get(i);
                                    PatientInfo info = new PatientInfo();
                                    info.setpId(obj2.getString("tbPatientPartialRegistrationId"));
                                    info.setpName(obj2.getString("patientName"));
                                    info.setpAddress(obj2.getString("Address"));
                                    info.setpVillage(obj2.getString("villageName"));
                                    patientInfo.add(info);
                                }

                                FragmentTransaction transaction = manager.beginTransaction();
                                MOPHIpatientListFragment mMOPHIpatientList = new MOPHIpatientListFragment();
                                Bundle args = new Bundle();
                                args.putSerializable("patients", patientInfo);
                                mMOPHIpatientList.setArguments(args);
                                transaction.replace(R.id.main_container, mMOPHIpatientList, "MOPHIpatientListFragment");
                                transaction.addToBackStack("MOPHIpatientListScreen");
                                transaction.commit();
                                mMOPHIpatientList.setMOPHIpatientCallbacks(MainActivity.this);
                            }
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * onRequestMophiPatientList * Exception "+e);
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void onbackPressHandler() {
        manager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void onVerifyPendingAddressPetientList() {

        loadingFragment(getResources().getString(R.string.progress_txt_3));

        HashMap<String,String> paramsValue = new HashMap<String, String>();
        paramsValue.put("empId", MainActivity.officerInfo.getEmpId());
        String serviceName = "submitApprovalOfPatientAddressView";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        System.out.println("Total records SputumDate :  " + jObject.toString());

                        ArrayList <PatientInfo> patientInfo = new ArrayList<>();
                        if(jObject.get("patientList")!= null){

                           if(jObject.get("patientList") instanceof Integer){

                               manager.popBackStack();
                               Toast.makeText(MainActivity.this, "There is no pending verifiction", Toast.LENGTH_SHORT).show();
//                               FragmentTransaction transaction = manager.beginTransaction();
//                               ProgressFragment mProgressFragment = new ProgressFragment();
//                               Bundle args = new Bundle();
//                               args.putString("myProgText", getResources().getString(R.string.progress_txt_3));
//                               mProgressFragment.setArguments(args);
//                               transaction.addToBackStack("ProgressScreen");
//                               transaction.replace(R.id.main_container, mProgressFragment, "ProgressFragment");
//                               transaction.commit();

                           }else {

                               JSONArray jArray = jObject.getJSONArray("patientList");
                               System.out.println("Total records SputumDate :  " + jArray.length());

                               for (int i = 0; i < jArray.length(); i++) {

                                   JSONObject obj2 = (JSONObject) jArray.get(i);
                                   PatientInfo info = new PatientInfo();
                                   info.setpId(obj2.getString("tbPatientPartialRegistrationId"));
                                   info.setpName(obj2.getString("patientName"));
                                   info.setpAddress(obj2.getString("completeAddress"));
                                   info.setpState(obj2.getString("state_name"));
                                   info.setpCity(obj2.getString("city_name"));
                                   info.setpVillage(obj2.getString("villageName"));
                                   patientInfo.add(info);
                               }


                               FragmentTransaction transaction = manager.beginTransaction();
                               VerifyPetientAddressFragment mVerifyPetientAddressFragment = new VerifyPetientAddressFragment();
                               Bundle args = new Bundle();
                               args.putSerializable("patients", patientInfo);
                               mVerifyPetientAddressFragment.setArguments(args);
                               transaction.replace(R.id.main_container, mVerifyPetientAddressFragment, "VerifyPetientAddressFragment");
                               transaction.addToBackStack("VerifyPetientAddressScreen");
                               transaction.commit();
                               mVerifyPetientAddressFragment.setVerifyAddressCallbacks(MainActivity.this);

                           }
                        }


                    }
                    else{
                        screenTag="OfficerMenu";
                        showErrorDialog();

                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    screenTag="OfficerMenu";
                    Utility.EXCEPTION_STRING=e.getMessage();
                    showErrorDialog();

                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    private void onRequestPatientList() {

        loadingFragment(getResources().getString(R.string.progress_txt_2));

        HashMap<String,String> paramsValue = new HashMap<String, String>();
        paramsValue.put("dmcId", MainActivity.officerInfo.getOfficerId());
        String serviceName = "patientNameandIdForView";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){


                        ArrayList <PatientInfo> patientInfo = new ArrayList<>();
                        if(jObject.get("Data")!= null){

                            JSONArray jArray = jObject.getJSONArray("Data");
                            System.out.println("Total records SputumDate :  " + jArray.length());

                            for (int i=0; i < jArray.length();i++) {

                                JSONObject obj2 = (JSONObject) jArray.get(i);
                                PatientInfo info = new PatientInfo();
                                info.setpId(obj2.getString("PId"));
                                info.setpName(obj2.getString("PName"));
                                patientInfo.add(info);
                                System.out.println(" PID " + obj2.getString("PId") +"           "+obj2.getString("PName"));

                            }
                            FragmentTransaction transaction = manager.beginTransaction();
                            PatientListFragment mPatientListFragment = new PatientListFragment();
                            Bundle args = new Bundle();
                            args.putSerializable("patients", patientInfo);
                            mPatientListFragment.setArguments(args);
                            transaction.replace(R.id.main_container, mPatientListFragment, "PatientListFragment");
                            transaction.addToBackStack("PatientListScreen");
                            transaction.commit();
                            mPatientListFragment.setPatientClickCallbacks(MainActivity.this);
                        }
                    }
                    else{

                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void registerClicked(HashMap paramsValue) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "newPatientRegisterWithFirstSputumTest";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        if(jObject.has("message")){

                            String tempPatientId ="Your temporary registration is successful. First sputum already been taken. Collect second sputum and submit results of both. Temporary registration ID is "+ jObject.getString("tempPatientId");
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        OfficerMenu mOfficerMenu = new OfficerMenu();
//                        transaction.replace(R.id.main_container, mOfficerMenu, "OfficerMenu");
//                        transaction.commit();
//                        mOfficerMenu.setOfficerMenuCallbacks(MainActivity.this);
                            manager.popBackStack("NewSputumScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Thank you")
                                    .setMessage(tempPatientId)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            manager.popBackStack();
                                        }
                                    }).setIcon(R.drawable.ic_dialog_alert).show();
                        }
                    }
                    else{
                        showErrorDialog();
//                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Utility.EXCEPTION_STRING=e.getMessage();
                    showErrorDialog();
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void fetchSubmitClicked(final HashMap<String, String> paramsValue) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "patientDetailsForSputumTest";//existingPatientForSputumTest

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {

                    if(jObject != null){

                        if(jObject.has("message")){

                            Toast.makeText(MainActivity.this, getResources().getString(R.string.pIdNotExist), Toast.LENGTH_SHORT).show();
                            manager.popBackStack();
                        }
                        else{
                            FragmentTransaction transaction = manager.beginTransaction();
                            ExistingPatientSputumSubmitFragment mExistingPatientSputumSubmitFragment = new ExistingPatientSputumSubmitFragment();
                            Bundle args = new Bundle();
                            args.putString("patientId", paramsValue.get("pId").toString());
                            args.putString("patientName", jObject.getString("patientName"));
                            args.putString("totalSputumTestdone", jObject.getString("totalSputumTestdone"));
                            args.putString("totalSputumTestResultSubmit", jObject.getString("totalSputumTestResultSubmit"));
                            args.putString("patientState", jObject.getString("State"));
                            args.putString("patientDistrict", jObject.getString("District"));
                            args.putString("patientBlock", jObject.getString("Block"));
                            args.putString("patientVillage", jObject.getString("Village"));
                            args.putString("patientAddress", jObject.getString("patientAddress"));
                            args.putString("TotalDose", jObject.getString("TotalDose"));
                            args.putString("TestCounter", jObject.getString("TestCounter"));

                            mExistingPatientSputumSubmitFragment.setArguments(args);
                            transaction.replace(R.id.main_container, mExistingPatientSputumSubmitFragment, "ExistingPatientSputumSubmitFragment2");
                            transaction.addToBackStack("ExistingPatientSputumSubmitScreen2");
                            transaction.commit();
                            mExistingPatientSputumSubmitFragment.setExistingPatientSputumSubmitCallbacks(MainActivity.this);
                        }
                    }
                    else{

                        AlertDialog.Builder dialog = myAlertDialog(1,"ALERT", Utility.EXCEPTION_STRING );
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                FragmentTransaction transaction1 = manager.beginTransaction();
                                ExistingPatientSputumSubmitFragment mExistingPatientSputumSubmitFragment = new ExistingPatientSputumSubmitFragment();
                                transaction1.replace(R.id.main_container, mExistingPatientSputumSubmitFragment, "ExistingPatientSputumSubmitFragment");
                                transaction1.addToBackStack("ExistingPatientSputumSubmitScreen");
                                transaction1.commit();
                                mExistingPatientSputumSubmitFragment.setExistingPatientSputumSubmitCallbacks(MainActivity.this);
                            }
                        });
                        dialog.show();

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block

                    screenTag="ExistingPatientSputumSubmitFragment";
                    showErrorDialog();

                    System.out.println(" Exception From fetchSubmitClicked  ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void sputumSubmitClicked(HashMap<String, String> paramsValue) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "existingPatientForSputumTest";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        OfficerMenu mOfficerMenu = new OfficerMenu();
//                        transaction.replace(R.id.main_container, mOfficerMenu, "OfficerMenu");
//                        transaction.commit();
//                        mOfficerMenu.setOfficerMenuCallbacks(MainActivity.this);
                        manager.popBackStack("ExistingPatientSputumSubmitScreen2", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        AlertDialog.Builder dialog  = myAlertDialog(1,"Thank You",getResources().getString(R.string.currentSputumCollection));
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                manager.popBackStack();
                            }
                        });
                        dialog.show();
                    }
                    else{

                        Toast.makeText(MainActivity.this, getResources().getString(R.string.pIdNotExist), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    screenTag="ExistingPatientSputumSubmitScreen2";
                    Utility.EXCEPTION_STRING=e.getMessage();
                    showErrorDialog();

                    // TODO Auto-generated catch block
                    System.out.println(" Exception From sputumSubmitClicked  ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    public  AlertDialog.Builder  myAlertDialog(int cases,String title,String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        switch (cases){

            case 1:
                dialog.setTitle(title);
                dialog.setMessage(message);
                dialog.setIcon(R.drawable.ic_dialog_alert);
                break;

            default:
                break;
        }

        return dialog;
    }

    @Override
    public void sputumReportFetchSubmitClicked(final HashMap<String, String> paramsValue) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "patientDetailsForSputumTest";//existingPatientForSputumTest

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null) {

                        if (jObject.has("message")) {

                            Toast.makeText(MainActivity.this, getResources().getString(R.string.pIdNotExist),Toast.LENGTH_SHORT).show();// + jObject.getString("patientId"), Toast.LENGTH_SHORT).show();
                            manager.popBackStack();
                        } else {

                        if (jObject.getString("totalSputumTestdone").equals(jObject.getString("totalSputumTestResultSubmit"))) {

                            AlertDialog.Builder dialog = myAlertDialog(1, "Warning", getResources().getString(R.string.allSputumResultSubmited));
                            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
//                                    FragmentTransaction transaction = manager.beginTransaction();
//                                    OfficerMenu mOfficerMenu = new OfficerMenu();
//                                    transaction.replace(R.id.main_container, mOfficerMenu, "OfficerMenu");
//                                    transaction.commit();
//                                    mOfficerMenu.setOfficerMenuCallbacks(MainActivity.this);

                                    //DMCscreen ///SputumReportSubmitScreen
                                    manager.popBackStack();
                                }
                            });
                            dialog.show();
                        } else {
                            FragmentTransaction transaction = manager.beginTransaction();
                            SputumReportSubmitFragment mSputumReportSubmitFragment = new SputumReportSubmitFragment();
                            Bundle args = new Bundle();
                            args.putString("patientId", paramsValue.get("pId").toString());
                            args.putString("patientName", jObject.getString("patientName"));
                            args.putString("totalSputumTestdone", jObject.getString("totalSputumTestdone"));
                            args.putString("totalSputumTestResultSubmit", jObject.getString("totalSputumTestResultSubmit"));

                            String[] date = null;
                            if (jObject.get("SputumDate") != null) {

                                JSONArray jArray = jObject.getJSONArray("SputumDate");
                                System.out.println("Total records SputumDate :  " + jArray.length());
                                date = new String[jArray.length()];

                                for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject obj2 = (JSONObject) jArray.get(i);
                                    System.out.println(obj2.getString("SputumTakenDate")+ " Date from server in main Activity ");
                                    date[i] = "( " + obj2.getString("SputumTakenDate") + " )" + " / " + obj2.getString("TestNo");
                                }
                            }

                            System.out.println(date.toString()+ " In main Activityyyyyyyyyyyyyyyyyyyyyyyy");
                            args.putStringArray("examineDate", date);
//                            args.putString("patientState", jObject.getString("State"));
//                            args.putString("patientDistrict", jObject.getString("District"));
//                            args.putString("patientBlock", jObject.getString("Block"));
//                            args.putString("patientVillage", jObject.getString("Village"));
//                            args.putString("patientAddress", jObject.getString("patientAddress"));
                            mSputumReportSubmitFragment.setArguments(args);
                            transaction.replace(R.id.main_container, mSputumReportSubmitFragment, "SputumReportSubmitFragment2");
                            transaction.addToBackStack("SputumReportSubmitScreen2");
                            transaction.commit();
                            mSputumReportSubmitFragment.setSputumReportSubmitCallbacks(MainActivity.this);
                        }
                    }
                    }
                    else{

                        Toast.makeText(MainActivity.this, getResources().getString(R.string.pIdNotExist), Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        SputumReportSubmitFragment mSputumReportSubmitFragment = new SputumReportSubmitFragment();
                        transaction1.replace(R.id.main_container, mSputumReportSubmitFragment, "SputumReportSubmitFragment");
                        transaction1.addToBackStack("SputumReportSubmitScreen");
                        transaction1.commit();
                        mSputumReportSubmitFragment.setSputumReportSubmitCallbacks(MainActivity.this);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    showErrorDialog();
                    System.out.println("MainActivity * fetchSubmitClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void sputumReportSputumResultSubmitClicked(HashMap<String, String> paramsValue) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "submitResultOfSputumTest";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

//                        FragmentTransaction transaction = manager.beginTransaction();
//                        OfficerMenu mOfficerMenu = new OfficerMenu();
//                        transaction.replace(R.id.main_container, mOfficerMenu, "OfficerMenu");
//                        transaction.commit();
//                        mOfficerMenu.setOfficerMenuCallbacks(MainActivity.this);

                        manager.popBackStack("SputumReportSubmitScreen2", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        AlertDialog.Builder dialog  = myAlertDialog(1,"Thank You",getResources().getString(R.string.currentSputumResultSubmited));
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                manager.popBackStack();
                            }
                        });
                        dialog.show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.pIdNotExist), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * sputumReportSputumResultSubmitClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void patientItemClicked(HashMap paramsValue) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "patientSputumResultByPatientId";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        // Here we strat a new Activity to show the report of sputum tests

                        sputumInfos = new ArrayList<>();
                        if(jObject.get("Data")!= null){
                            JSONArray jArray = jObject.getJSONArray("Data");
                            System.out.println("Total records SputumDate :  " + jArray.length());

                            for (int i=0; i < jArray.length();i++) {

                                JSONObject obj2 = (JSONObject) jArray.get(i);
                                SputumInfo info = new SputumInfo();
                                info.setSputumCounter(obj2.getString("SputumTestCounter"));
                                info.setExaminDate(obj2.getString("dateOfExamination"));
                                info.setSputumSpecimen(obj2.getString("specimen"));
                                info.setVisualAppearance(obj2.getString("visualAppearance"));
                                info.setResult(obj2.getString("result"));
                                info.setGrading(obj2.getString("positiveGrading"));
                                info.setScanty(obj2.getString("scanty"));
                                info.setSubmitStatus(obj2.getString("SubmitStatus"));
                                sputumInfos.add(info);
                            }
                        }
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        TestReportFragment mTestReportFragment = new TestReportFragment();
                        transaction1.replace(R.id.main_container, mTestReportFragment, "TestReportFragment");
                        transaction1.addToBackStack("TestReportScreen");
                        transaction1.commit();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.popBackStack("ProgressScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackStackChanged() {


    int count = manager.getBackStackEntryCount();
        if(count == 1){
           setTheme(R.style.AppStartTheme);
        }
        for (int i = count-1;i>=0;i--){
            System.out.println("Fragment at " + i + " : " + manager.getBackStackEntryAt(i).getName());
        }
    }

    @Override
    public void verificationClicked(HashMap paramsValue, final int position, final VerifyPetientAddressFragment frag) {

        loadingFragment(getResources().getString(R.string.progress_txt_3));
        System.out.println("PID ---------  " + paramsValue.get("PId"));
        System.out.println("STATUS ---------  " + paramsValue.get("verifiedPatientStatus"));
        String serviceName = "submitApprovalOfPatientAddressModel";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        if(jObject.getInt("success") == 1){
                            frag.refressListOnResponse(position);
                            manager.popBackStack();
                            Toast.makeText(MainActivity.this, "Address Verification Complete", Toast.LENGTH_SHORT).show();
                        }
                        else {
//                            screenTag="VerifyPetientAddressFragment";
//                            showErrorDialog();
                            Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        screenTag="VerifyPetientAddressFragment";
                        showErrorDialog();

//                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Utility.EXCEPTION_STRING=e.getMessage();
                    screenTag="VerifyPetientAddressFragment";
                    showErrorDialog();
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();

    }

    @Override
    public void mophiPatientClicked(PatientInfo pInfo, MOPHIpatientListFragment mophIpatientListFragment, int index) {

        FragmentTransaction transaction = manager.beginTransaction();
        PatientPermanentRegistrationFragment mPatientPermanentRegistrationFragment = PatientPermanentRegistrationFragment.newInstance(mophIpatientListFragment);
        Bundle args = new Bundle();
        args.putSerializable("patientInfo", pInfo);
        args.putInt("index", index);
        mPatientPermanentRegistrationFragment.setArguments(args);
        transaction.replace(R.id.main_container, mPatientPermanentRegistrationFragment, "PatientPermanentRegistrationFragment");
        transaction.addToBackStack("PatientPermanentRegistrationScreen");
        transaction.commit();
    }


    @Override
    public void onMophiPermanentRegisterClicked(HashMap paramsValue, final MOPHIpatientListFragment fragment, final int index) {

        System.out.println("Item Cliced : " + index + "\nDATA  " + paramsValue);
        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "submitFullRegistration";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

//                        System.out.println(" onMophiPermanentRegisterClicked :  " + jObject.toString());
//                        //      here we delete to item from the last selected item from the patient List
//                        fragment.refressListOnResponse(index);
//                        manager.popBackStack("PatientPermanentRegistrationScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);


                        if(jObject.getInt("success") == 1){
                            patientPermanentID=   jObject.getString("tbPatientId");
                            System.out.println(patientPermanentID + "Permanent IDDDDDDDDDDDD");
                            System.out.println(PatientPermanentRegistrationFragment.registeredPatientID+ "Temporary IDDDDDDDDDDDD");
                            AlertDialog.Builder dialog = myAlertDialog(1, "Thank You", "Patient registered successfully."+"\n"+"Patient Permanent ID: "+patientPermanentID);
                            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    fragment.refressListOnResponse(index);
                                    manager.popBackStack("PatientPermanentRegistrationScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);//popBackStack("MOPHIHealthWorkerPatientListScreen",2);
                                  //  manager.popBackStack();
                                }
                            });
                            dialog.show();

                            //      here we delete to item from the last selected item from the patient List
                          //  fragment.refressListOnResponse(index);
                         //   manager.popBackStack("PatientPermanentRegistrationScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);//popBackStack("MOPHIHealthWorkerPatientListScreen",2);
                            // Show the toast msg for something is happen
                        }else{
                            manager.popBackStack("PatientPermanentRegistrationScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            // Show the toast msg for something is happen
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void assignCompPatientClicked(PatientInfo pInfo, AssignCompPatientListFragment assignCompPatientListFragment, int index) {
        // here we assign the compliance method to the patient
        FragmentTransaction transaction = manager.beginTransaction();
        AssignComplianceFragment mAssignCoomplianceFragment = AssignComplianceFragment.newInstance(assignCompPatientListFragment);
        Bundle args = new Bundle();
        args.putSerializable("patientInfo", pInfo);
        args.putInt("index", index);
        mAssignCoomplianceFragment.setArguments(args);
        transaction.replace(R.id.main_container, mAssignCoomplianceFragment, "AssignCoomplianceFragment");
        transaction.addToBackStack("AssignCoomplianceScreen");
        transaction.commit();
    }

    @Override
    public void onAssignComplianceClicked(HashMap paramsValue,final AssignCompPatientListFragment fragment, final int index) {


        System.out.println("VALUE " + index + " : " + paramsValue);
        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "assignComplianceMethodSubmission";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

//                        System.out.println(" Successfully Assign Compliance:  " + jObject.toString());
//                    //      here we delete to item from the last selected item from the patient List
//                        fragment.refressListOnResponse(index);
//                        manager.popBackStack("AssignCoomplianceScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        if(jObject.getInt("success") == 1){
                            //      here we delete to item from the last selected item from the patient List
                            fragment.refressListOnResponse(index);
                            manager.popBackStack("AssignCoomplianceScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);//popBackStack("MOPHIHealthWorkerPatientListScreen",2);
                            // Show the toast msg for something is happen
                        }else{
                            manager.popBackStack("AssignCoomplianceScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            // Show the toast msg for something is happen
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    public void loadingFragment(String progressMsg){

        FragmentTransaction transaction = manager.beginTransaction();
        ProgressFragment mProgressFragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString("myProgText", progressMsg);
        mProgressFragment.setArguments(args);
        transaction.replace(R.id.main_container, mProgressFragment, "ProgressFragment");
        transaction.addToBackStack("ProgressScreen");
        transaction.commit();
    }

    @Override   
    public void assignHealthWorkerPatientClicked(PatientInfo pInfo, ArrayList<EmployeInfo> providers, ArrayList<EmployeInfo> observers, AssignHealthWorkerPatientListFragment assignHealthWorkerPatientListFragment, int index) {

        loadingFragment(getResources().getString(R.string.progress_txt_2));
        FragmentTransaction transaction = manager.beginTransaction();
        AssignHealthWorkerFragment mAssignHealthWorkerFragment = AssignHealthWorkerFragment.newInstance(assignHealthWorkerPatientListFragment);//assignHealthWorkerPatientListFragment
        Bundle args = new Bundle();
        args.putSerializable("patientInfo", pInfo);
        args.putSerializable("providersInfo", providers);
        args.putSerializable("observersInfo", observers);
        args.putInt("index", index);
        mAssignHealthWorkerFragment.setArguments(args);
        transaction.replace(R.id.main_container, mAssignHealthWorkerFragment, "AssignHealthWorkerFragment");
        transaction.addToBackStack("AssignHealthWorkerScreen");
        transaction.commit();
    }

    @Override
    public void onAssignHealthWorkerClicked(HashMap paramsValue,final AssignHealthWorkerPatientListFragment fragment, final int index) {


        System.out.println("VALUE " + index + " : " + paramsValue);
        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "updateHealthworkerPatientDetails";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

//                        System.out.println(" Successfully Assign Health Worker:  " + jObject.toString());
//                        // here we delete to item from the last selected item from the patient List
                        if(jObject.getInt("success") == 1){
                            //      here we delete to item from the last selected item from the patient List

                            AlertDialog.Builder dialog = myAlertDialog(1, "Thank You", "Health worker assigned Successfully");
                            dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    fragment.refressListOnResponse(index);
                                    manager.popBackStack("AssignHealthWorkerScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);//popBackStack("MOPHIHealthWorkerPatientListScreen",2);
                                    manager.popBackStack();
                                }
                            });
                            dialog.show();

//                            fragment.refressListOnResponse(index);
//                            manager.popBackStack("AssignHealthWorkerScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);//popBackStack("MOPHIHealthWorkerPatientListScreen",2);
//                            manager.popBackStack();
                            // Show the toast msg for something is happen
                        }else{
                            manager.popBackStack("AssignHealthWorkerScreen", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            manager.popBackStack();
                            // Show the toast msg for something is happen
                        }
                    }
                    else{

                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }

    @Override
    public void extendDosePatientClicked(HashMap paramsValue, final ExtendDosePatientListFragment fragment, final int index) {

        System.out.println("VALUE " + index + " : " + paramsValue);
        loadingFragment(getResources().getString(R.string.progress_txt_2));
        String serviceName = "extendPhaseForPatient";

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                jObject = (JSONObject) msg.obj;

                try {
                    if(jObject != null ){

                        if(jObject.getInt("success") == 1){
                            //      here we delete to item from the last selected item from the patient List
                            fragment.refressListOnResponse(index);
                            manager.popBackStack();

                        }else{
                            manager.popBackStack();
                        }
                    }
                    else{

                        Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("MainActivity * registerClicked * JSONException ");
                    e.printStackTrace();
                }
            }
        };
        new MyAsyncTask(this, serviceName, paramsValue, 1,0, mHandler).execute();
    }
}
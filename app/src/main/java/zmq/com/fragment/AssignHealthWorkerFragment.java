package zmq.com.fragment;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import zmq.com.adapter.CustomSpinnerAdapter;
import zmq.com.floatlable.R;
import zmq.com.model.EmployeInfo;
import zmq.com.model.PatientInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAssignHealthWorkerListener} interface
 * to handle interaction events.
 * Use the {@link AssignHealthWorkerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignHealthWorkerFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private Toolbar toolbar;
    private EditText edt_pName,edt_sex, edt_age,edt_pType,edt_regDate;
  //  private EditText edt_providerAddress,edt_observerAddress;
    private Button btnAssign, btnDozStartDate;
    private Spinner spnProvider, spnObserver;

    private TextView txtDozStartDate;

    private Calendar myCalendar;
    private  PatientInfo pinfo;

    ArrayList<EmployeInfo> providerList;
    ArrayList<EmployeInfo> observerList;

    private String dotsProviderID;
    private String observerID;

    private String providerArr[];
    private String observerArr[];

    private OnAssignHealthWorkerListener mListener;
    static AssignHealthWorkerPatientListFragment healthWorkerPatientListFragment;

    private String providerUserName;
    private String observerUserName;

    int rowSelectedProvider=1,rowSelectedObserver=1;

    public AssignHealthWorkerFragment() {
        // Required empty public constructor
    }

//    public AssignHealthWorkerFragment(AssignHealthWorkerPatientListFragment assignHealthWorkerPatientListFragment) {
//
//    }

    // TODO: Rename and change types and number of parameters
    public static AssignHealthWorkerFragment newInstance(AssignHealthWorkerPatientListFragment assignHealthWorkerPatientListFragment) {
        AssignHealthWorkerFragment fragment = new AssignHealthWorkerFragment();
        healthWorkerPatientListFragment = assignHealthWorkerPatientListFragment;
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assign_health_worker, container, false);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.oAssignClicked();
//        }
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.healthWorkerToolbar);
        toolbar.setTitle("Assign Health Worker");

        pinfo  = (PatientInfo) getArguments().getSerializable("patientInfo");



        providerList = (ArrayList<EmployeInfo>) getArguments().getSerializable("providersInfo");
        providerArr = new String[providerList.size()];
        for (int i = 0; i < providerList.size(); i++) {
            providerArr[i] =  providerList.get(i).getEmpName();
            System.out.println("Provider providerArr  "+providerArr[i]);
        }
        

        observerList = (ArrayList<EmployeInfo>) getArguments().getSerializable("observersInfo");
        observerArr = new String[observerList.size()];
        for (int i = 0; i < observerList.size(); i++) {
            observerArr[i] =  observerList.get(i).getEmpName();
            System.out.println("observer observerArr "+observerArr[i]);
        }


        spnProvider = (Spinner) getActivity().findViewById(R.id.spinner_health_worker_provider);
        CustomSpinnerAdapter providerAdapter = new CustomSpinnerAdapter(getActivity(),R.layout.spinner_rows, providerList) ;
        spnProvider.setAdapter(providerAdapter);

        spnObserver = (Spinner) getActivity().findViewById(R.id.spinner_health_worker_observer);
        CustomSpinnerAdapter observerAdapter = new CustomSpinnerAdapter(getActivity(),R.layout.spinner_rows, observerList) ;
        spnObserver.setAdapter(observerAdapter);



//        spnProvider = (Spinner) getActivity().findViewById(R.id.spinner_health_worker_provider);
//        ArrayAdapter<String> providerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, providerArr); //selected item will look like a spinner set from XML
//        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnProvider.setAdapter(providerAdapter);
        
//        spnObserver = (Spinner) getActivity().findViewById(R.id.spinner_health_worker_observer);
//        ArrayAdapter<String> observerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, observerArr); //selected item will look like a spinner set from XML
//        observerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnObserver.setAdapter(observerAdapter);




        btnAssign = (Button) getActivity().findViewById(R.id.btn_health_worker_assign);
    //    btnDozStartDate = (Button) getActivity().findViewById(R.id.btn_dose_start_date);

    //    txtDozStartDate = (TextView) getActivity().findViewById(R.id.txt_dose_start_date);

        edt_pName = (EditText) getActivity().findViewById(R.id.edt_health_worker_p_name);
        edt_sex = (EditText) getActivity().findViewById(R.id.edt_health_worker_p_sex);
        edt_age = (EditText) getActivity().findViewById(R.id.edt_health_worker_p_age);
        edt_pType = (EditText) getActivity().findViewById(R.id.edt_health_worker_p_type);
        edt_regDate = (EditText) getActivity().findViewById(R.id.edt_health_worker_reg_date);

//        edt_providerAddress = (EditText) getActivity().findViewById(R.id.edt_health_worker_provider_address);
//        edt_observerAddress = (EditText) getActivity().findViewById(R.id.edt_health_worker_observer_address);


        
        System.out.println("provider  :" + providerList + "\n provider SIZE " + providerList.size() + "\n" + "observer  : " + observerList + "\n observer SIZE " + observerList.size());

        edt_pName.setText(pinfo.getpName());
        edt_sex.setText(pinfo.getpSex());
        edt_age.setText(pinfo.getpAge());
        edt_pType.setText(pinfo.getpType());
        edt_regDate.setText(pinfo.getpRegDate());

//        dotsProviderID = providerList.get(0).getEmpId();
//        observerID = observerList.get(0).getEmpId();

        providerUserName = providerList.get(0).getUserName();
        observerUserName = observerList.get(0).getUserName();
        //String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        spnProvider.setOnItemSelectedListener(this);
        spnObserver.setOnItemSelectedListener(this);

        btnAssign.setOnClickListener(this);

        if(pinfo.getpCompliance().equals("3")){
            //hide the details of observer
//            TableRow row1 = (TableRow) getActivity().findViewById(R.id.tableRow_observer_list);
//            TableRow row2 = (TableRow) getActivity().findViewById(R.id.tableRow_observer_address);

            LinearLayout row1 = (LinearLayout) getActivity().findViewById(R.id.tableRow_observer_list);
        //    LinearLayout row2 = (LinearLayout) getActivity().findViewById(R.id.tableRow_observer_address);

            row1.setVisibility(View.GONE);
        //    row2.setVisibility(View.GONE);
            observerID = null;
        }
 //       btnDozStartDate.setOnClickListener(this);

 //       txtDozStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAssignHealthWorkerListener) {
            mListener = (OnAssignHealthWorkerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAssignHealthWorkerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.spinner_health_worker_provider:

                dotsProviderID = providerList.get(position).getEmpId();
                rowSelectedProvider=position;

//
//                if(position != 0) {
//
//                    dotsProviderID = providerList.get(position).getEmpId();
//                }

                 //parent.getItemAtPosition(position).toString();
            //    edt_providerAddress.setText(providerList.get(position).getProviderVillage());


//                providerUserName = providerList.get(position).getUserName();
//
//                System.out.println(" provider " + dotsProviderID + " observer " + observerID + " provider username "+providerUserName);
//
//                if(providerUserName.equals(observerUserName)){
//
//                    Toast.makeText(getActivity(),"Dots Provider should be different",Toast.LENGTH_SHORT).show();
//                }



               // System.out.print(1compMethod.toString());
                break;
            case R.id.spinner_health_worker_observer:

                observerID = observerList.get(position).getEmpId();
                rowSelectedObserver=position;
//                if(position != 0) {
//
//                    observerID = observerList.get(position).getEmpId();
//
//                }
            //    edt_observerAddress.setText(observerList.get(position).getObserverVillage());


//                observerUserName = observerList.get(position).getUserName();
//
//                System.out.println(" provider " + dotsProviderID + " observer " + observerID + " observer username "+observerUserName);
//
//                if(providerUserName.equals(observerUserName)){
//
//                    Toast.makeText(getActivity(),"Dots Provider should be different",Toast.LENGTH_SHORT).show();
//                }


                break;

            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        switch (parent.getId()) {

            case R.id.spinner_health_worker_provider:
                Toast.makeText(getActivity(),"Please choose the Dots Provider",Toast.LENGTH_SHORT).show();
            //    dotsProviderID = providerList.get(0).getEmpId();// parent.getItemAtPosition(0).toString();
            //    edt_providerAddress.setText(providerList.get(0).getProviderVillage());

                break;
            case R.id.spinner_health_worker_observer:
                Toast.makeText(getActivity(),"Please choose the Observer",Toast.LENGTH_SHORT).show();
             //   observerID = observerList.get(0).getEmpId();
            //    edt_observerAddress.setText(observerList.get(0).getObserverVillage());
                break;

            default:
                break;

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.btn_dose_start_date:
//
//               // pickerDate(txtDozStartDate);
//                break;

            case R.id.btn_health_worker_assign:

                // collect the data here from the input field of screen
//http://localhost/Tb_platform_webtool/DMC_ControllerForAndroid/updateHealthworkerPatientDetails?dotsProviderId=&observerId=&tbPatientId=
if(rowSelectedProvider !=0 && rowSelectedObserver!=0 && rowSelectedProvider!=rowSelectedObserver&& !pinfo.getpCompliance().equals("3"))
{
    HashMap map = new HashMap();
    int index = getArguments().getInt("index");
    map.put("tbPatientId", pinfo.getpId());
    map.put("dotsProviderId", dotsProviderID);
    map.put("observerId", observerID);
    //    map.put("patientDoseStartDate",txtDozStartDate.getText().toString());
    System.out.println(" MAP value ******** " + map);
    mListener.onAssignHealthWorkerClicked(map, healthWorkerPatientListFragment, index);

}
     else
{
    if((rowSelectedProvider ==0 || rowSelectedObserver==0) && !pinfo.getpCompliance().equals("3")) {
        Toast.makeText(getContext(), "Please fill all the fields first", Toast.LENGTH_SHORT).show();
    }



    else if(rowSelectedProvider!=0 && rowSelectedObserver!=0  && rowSelectedProvider==rowSelectedObserver && !pinfo.getpCompliance().equals("3") )
    {
        Toast.makeText(getContext(), "Observer and DOTS Provider cannot be same", Toast.LENGTH_SHORT).show();
    }
}

                if(pinfo.getpCompliance().equals("3") && rowSelectedProvider !=0){
                    System.out.println(rowSelectedProvider+ "  If not 0 ");
                    HashMap map = new HashMap();
                    int index = getArguments().getInt("index");
                    map.put("tbPatientId", pinfo.getpId());
                    map.put("dotsProviderId", dotsProviderID);
                    map.put("observerId", "0");
                    //    map.put("patientDoseStartDate",txtDozStartDate.getText().toString());
                    System.out.println(" MAP value ******** " + map);
                    mListener.onAssignHealthWorkerClicked(map, healthWorkerPatientListFragment, index);
                }
                else if(rowSelectedProvider == 0)
            {

                System.out.println(rowSelectedProvider+ "If 0");
                Toast.makeText(getContext(), "Please select DOTS Provider", Toast.LENGTH_SHORT).show();

            }

                break;
            default:
                break;
        }

    }

//    private void pickerDate(final TextView txt) {
//
//        myCalendar = Calendar.getInstance();
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                String myFormat = "dd/MM/yyyy"; // In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//                txt.setText(sdf.format(myCalendar.getTime()));
//            }
//        };
//
//        new DatePickerDialog(getActivity(), date,
//                myCalendar.get(Calendar.YEAR), myCalendar
//                .get(Calendar.MONTH), myCalendar
//                .get(Calendar.DAY_OF_MONTH)).show();
//
//    }

    public interface OnAssignHealthWorkerListener {
        // TODO: Update argument type and name
        void onAssignHealthWorkerClicked(HashMap map,AssignHealthWorkerPatientListFragment healthWorkerPatientListFragment, int index);
    }
}

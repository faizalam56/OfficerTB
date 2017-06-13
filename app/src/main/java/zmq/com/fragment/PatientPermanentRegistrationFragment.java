package zmq.com.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import zmq.com.activity.MainActivity;
import zmq.com.floatlable.R;
import zmq.com.model.PatientInfo;

import static zmq.com.activity.MainActivity.ServerdateTwoDaysPrevious;


public class PatientPermanentRegistrationFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private Toolbar toolbar;
    private EditText edt_age,edt_pName,edt_others;
    private EditText edt_remarks;
    private Button btnRegister,btnRegDate;
    private Spinner spnSex,spnCat,spnDisease,spnPtype;

    private TextView txtRegDate;

    private Calendar myCalendar;

    private OnPermanentRegListener mListener;
    private String sex;
    private String pType;
    private String disease;
    private String category;
    private PatientInfo pinfo;
   public static String registeredPatientID;
    private HashMap<String,String> valueMap;

    static MOPHIpatientListFragment mMophiPatientListFragment;
    Date selectedDate=null;
    Date severTwoDayOldDate=MainActivity.ServerdateTwoDaysPrevious;
    public PatientPermanentRegistrationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PatientPermanentRegistrationFragment newInstance(MOPHIpatientListFragment mophiPatientListFragment) {
        PatientPermanentRegistrationFragment fragment = new PatientPermanentRegistrationFragment();
        mMophiPatientListFragment = mophiPatientListFragment;
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_permanent_registration, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Permanent Registration");
        myCalendar = Calendar.getInstance();
        valueMap = new HashMap<String, String>();

        spnSex = (Spinner) getActivity().findViewById(R.id.spinner_sex);
        spnPtype = (Spinner) getActivity().findViewById(R.id.spinner_ptype);
        spnDisease = (Spinner) getActivity().findViewById(R.id.spinner_disease);
        spnCat = (Spinner) getActivity().findViewById(R.id.spinner_regiem);

        btnRegister = (Button) getActivity().findViewById(R.id.btn_mophi_register);
        btnRegDate = (Button) getActivity().findViewById(R.id.btn_reg_date);

        txtRegDate = (TextView) getActivity().findViewById(R.id.txt_reg_date);

        edt_pName = (EditText) getActivity().findViewById(R.id.edt_p_name);
        edt_age = (EditText) getActivity().findViewById(R.id.edt_age);
        edt_remarks = (EditText) getActivity().findViewById(R.id.edt_remrks);
        edt_others = (EditText) getActivity().findViewById(R.id.edt_others);


        pinfo  = (PatientInfo) getArguments().getSerializable("patientInfo");

        edt_pName.setText(pinfo.getpName());

        //String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        spnSex.setOnItemSelectedListener(this);
        spnPtype.setOnItemSelectedListener(this);
        spnDisease.setOnItemSelectedListener(this);
        spnCat.setOnItemSelectedListener(this);

        btnRegister.setOnClickListener(this);
        btnRegDate.setOnClickListener(this);

     //   txtRegDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));     by bilal 15/04/2016

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(MainActivity.ServerTodaydate);
        txtRegDate.setText(dateString);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPermanentRegListener) {
            mListener = (OnPermanentRegListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPermanentRegListener");
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

            case R.id.spinner_sex:
                sex = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_ptype:
                pType = parent.getItemAtPosition(position).toString();

                if(pType.equals("Others")){

                    edt_others.setVisibility(View.VISIBLE);
                }else {
                    edt_others.setVisibility(View.GONE);
                }
                break;
            case R.id.spinner_disease:
                disease = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinner_regiem:
                category = parent.getItemAtPosition(position).toString().toLowerCase();
                break;
            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        switch (parent.getId()) {

            case R.id.spinner_sex:
                sex = parent.getItemAtPosition(0).toString();
                break;
            case R.id.spinner_ptype:
                pType = parent.getItemAtPosition(0).toString();
                break;
            case R.id.spinner_disease:
                disease = parent.getItemAtPosition(0).toString();
                break;
            case R.id.spinner_regiem:
                category = parent.getItemAtPosition(0).toString().toLowerCase();
                break;
            default:
                break;

        }

    }

    private void pickerDate(final TextView txt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString1 = dateFormat.format(MainActivity.ServerTodaydate);
        String ster[]= dateString1.split("/");
        String   finalStr=ster[2]+ "/" +ster[1]+ "/" +ster[0];
        System.out.println(finalStr+"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzxccc");

        System.out.println(txtRegDate.getText().toString()+"   In picker date") ;


        myCalendar.set(Integer.parseInt(ster[2]),(Integer.parseInt(ster[1]))-1,Integer.parseInt(ster[0]));
    //    myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; // In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txt.setText(sdf.format(myCalendar.getTime()));
            }
        };

        new DatePickerDialog(getActivity(), date,
                myCalendar.get(Calendar.YEAR), myCalendar
                .get(Calendar.MONTH), myCalendar
                .get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_reg_date:

                pickerDate(txtRegDate);
                break;

            case R.id.btn_mophi_register:

                if(edt_others.getVisibility() == View.VISIBLE){
                    pType = edt_others.getText().toString();
                }else{
                    edt_others.setText("");
                }
if(!edt_age.getText().toString().equals(""))
{

    System.out.println(txtRegDate.getText().toString()+"   After picker date")  ;
    confirmationDialog();
}
 else
{

    Toast.makeText(getContext(), "Please fill all the fields first", Toast.LENGTH_SHORT).show();
}
                break;

            default:
                break;

        }

    }

    private void confirmationDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        //dialog.setTitle(getActivity().getResources().getString(R.string.alert_sputum_submit_title));
        dialog.setMessage(getActivity().getResources().getString(R.string.alert_sputum_submit_message));
        dialog.setIcon(android.R.drawable.ic_dialog_alert);

        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if( compareDates()){
                    dateValidationDialog("Incorrect Date");
                    // Toast.makeText(getActivity(), "please choose another date after today date", Toast.LENGTH_SHORT).show();

                }else {

                    int index = getArguments().getInt("index");
                    // to collect the data from different fields and send to the server
                    valueMap.put("pId", pinfo.getpId());
                    registeredPatientID=pinfo.getpId();
                    valueMap.put("pName", edt_pName.getText().toString());
                    valueMap.put("sex", sex);
                    valueMap.put("age", edt_age.getText().toString());
                    valueMap.put("pType", pType);
                    valueMap.put("regimeorCat", category);
                    valueMap.put("disease", disease);

                    String s=txtRegDate.getText().toString();
                    String ster[]= s.split("/");
                    String finalString=ster[2]+ "/" +ster[1]+ "/" +ster[0];

                  //  valueMap.put("dOR", txtRegDate.getText().toString());
                    valueMap.put("dOR", finalString);
                    valueMap.put("remark", edt_remarks.getText().toString());
                    valueMap.put("phiId", MainActivity.officerInfo.getOfficerId());
                    mListener.onMophiPermanentRegisterClicked(valueMap, mMophiPatientListFragment, index);

              System.out.println( txtRegDate.getText().toString()+ " not Going to server");

                }
            }
        });
        dialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        dialog.show();

    }
    private void dateValidationDialog(String msg) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle(getActivity().getResources().getString(R.string.alert_sputum_submit_title));
        dialog.setMessage(msg);//getActivity().getResources().getString(R.string.alert_sputum_submit_message));
        dialog.setIcon(R.drawable.ic_dialog_alert);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }



    public boolean compareDates(){

     //   int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString1 = dateFormat.format(MainActivity.ServerTodaydate);
        String ster[]= dateString1.split("/");
     String   finalStr=ster[2]+ "/" +ster[1]+ "/" +ster[0];

        String dateString123=    txtRegDate.getText().toString();
        String ster123[]= dateString1.split("/");

        String   finalStr123=ster[2]+ "/" +ster[1]+ "/" +ster[0];


        myCalendar.set(Integer.parseInt(ster123[2]), Integer.parseInt(ster123[1]), Integer.parseInt(ster123[0]));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        try {
            Date dateToday = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            cal1.setTime(ServerdateTwoDaysPrevious);
            cal2.setTime(MainActivity.ServerTodaydate);


String selectedDateString=txtRegDate.getText().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {

           selectedDate= formatter.parse(selectedDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(selectedDate+" Selected date");
if((selectedDate.compareTo(severTwoDayOldDate)<0) ||(selectedDate.compareTo(MainActivity.ServerTodaydate)>0)){
    System.out.println("Date1 is before Date2");
    return true;
}
//            else if(selectedDate.compareTo(MainActivity.ServerTodaydate)>0){
//
//
//    System.out.println("Date1 is after Date2");
//    return true;
//}


//
//            if(myCalendar.before(cal1) || myCalendar.after(cal2) ){
//
//                System.out.println("Date1 is before Date2");
//                return true;
//            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface OnPermanentRegListener {
        // TODO: Update argument type and name
        void onMophiPermanentRegisterClicked(HashMap param, MOPHIpatientListFragment mMophiPatientListFragment, int index);
    }
}

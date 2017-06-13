package zmq.com.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import zmq.com.activity.MainActivity;
import zmq.com.floatlable.R;
import zmq.com.model.PatientInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAssignComplianceListener} interface
 * to handle interaction events.
 * Use the {@link AssignComplianceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignComplianceFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private Toolbar toolbar;
    private EditText edt_pName,edt_sex, edt_age,edt_pType,edt_regDate;
    private Button btnAssign, btnDozStartDate;
    private Spinner spnCompMethod,spnDotsDays,spnDisease,spnPtype;
    HashMap valueMap ;
    private TextView txtDozStartDate;

    private Calendar myCalendar;
    private  PatientInfo pinfo;

    public static String compMethod;
    private String dotsDays;

    private OnAssignComplianceListener mListener;
    String TDate;
    String finalStr;
    static AssignCompPatientListFragment mAssignCompPatientListFragment;
    Date date = null;
    public AssignComplianceFragment() {
        // Required empty public constructor
    }

    public static AssignComplianceFragment newInstance(AssignCompPatientListFragment assignCompPatientListFragment) {
        AssignComplianceFragment fragment = new AssignComplianceFragment();
        mAssignCompPatientListFragment = assignCompPatientListFragment;
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
        return inflater.inflate(R.layout.fragment_assign_coompliance, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        valueMap = new HashMap();
        myCalendar = Calendar.getInstance();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Assign Compliance Method");

        spnCompMethod = (Spinner) getActivity().findViewById(R.id.spinner_comp_method);
        spnDotsDays = (Spinner) getActivity().findViewById(R.id.spinner_dots_days);

        btnAssign = (Button) getActivity().findViewById(R.id.btn_mophi_assign_comp);
        btnDozStartDate = (Button) getActivity().findViewById(R.id.btn_dose_start_date);

        txtDozStartDate = (TextView) getActivity().findViewById(R.id.txt_dose_start_date);

        edt_pName = (EditText) getActivity().findViewById(R.id.edt_p_name1);
        edt_sex = (EditText) getActivity().findViewById(R.id.edt_p_sex1);
        edt_age = (EditText) getActivity().findViewById(R.id.edt_p_age1);
        edt_pType = (EditText) getActivity().findViewById(R.id.edt_p_type1);
        edt_regDate = (EditText) getActivity().findViewById(R.id.edt_reg_date1);


        pinfo  = (PatientInfo) getArguments().getSerializable("patientInfo");

        edt_pName.setText(pinfo.getpName());
        edt_sex.setText(pinfo.getpSex());
        edt_age.setText(pinfo.getpAge());
        edt_pType.setText(pinfo.getpType());
        edt_regDate.setText(pinfo.getpRegDate());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String dateInString= edt_regDate.getText().toString();


        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);

        //String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        spnCompMethod.setOnItemSelectedListener(this);
        spnDotsDays.setOnItemSelectedListener(this);

        btnAssign.setOnClickListener(this);
        btnDozStartDate.setOnClickListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(MainActivity.ServerTodaydate);
        txtDozStartDate.setText(dateString);

    //    txtDozStartDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAssignComplianceListener) {
            mListener = (OnAssignComplianceListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentAssignComplianceListener");
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

            case R.id.spinner_comp_method:
              //  compMethod = String.valueOf(position + 1);//parent.getItemAtPosition(position).toString();

                if(position == 1){
                    compMethod = String.valueOf(3);
                }else{
                    compMethod = String.valueOf(1);
                }

               // System.out.print(compMethod.toString());
                break;
            case R.id.spinner_dots_days:

                if(position == 0){
                    dotsDays = "MWF";
                }
                else{
                    dotsDays = "TTS";
                }
                //dotsDays = parent.getItemAtPosition(position).toString();
                break;

            default:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        switch (parent.getId()) {

            case R.id.spinner_comp_method:
                compMethod =  String.valueOf(1);// parent.getItemAtPosition(0).toString();
                break;
            case R.id.spinner_dots_days:
                //dotsDays = parent.getItemAtPosition(0).toString();
                dotsDays = "MWF";
                break;

            default:
                break;

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_dose_start_date:

                pickerDate(txtDozStartDate);
                break;

            case R.id.btn_mophi_assign_comp:
                // collect the 1data here from the input field of screen

                confirmationDialog();

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

        myCalendar.set(Integer.parseInt(ster[2]),(Integer.parseInt(ster[1]))-1,Integer.parseInt(ster[0]));



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
    private boolean dateValidation(int daysType){

        SimpleDateFormat dayFormat = new SimpleDateFormat("EE", Locale.US);
        String weekDay = dayFormat.format(myCalendar.getTime()).toUpperCase();

        switch (daysType){

            case 1:

                if((weekDay.equals("MON") ||weekDay.equals("WED") ||weekDay.equals("FRI"))){

                    return !compareDates();
                }
                break;
            case 2:
                if((weekDay.equals("TUE") ||weekDay.equals("THU") ||weekDay.equals("SAT"))){
                    return !compareDates();
                }
                break;
            default:
                break;
        }

        //  System.out.println("Day " + weekDay.toUpperCase() + " Day " + myCalendar.get(Calendar.DAY_OF_WEEK));
        return false;
    }

    public boolean compareDates(){

        int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar cal1 = Calendar.getInstance();


        try {
            Date dateToday = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            System.out.println(date+" in compare datesssssssssssssssssssss");

            cal1.setTime(date);


            if(myCalendar.before(cal1)){
              //  dateErrorMessage("Date1 is before Date2");
                System.out.println("Date1 is before Date2");
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface OnAssignComplianceListener {
        // TODO: Update argument type and name
        void onAssignComplianceClicked(HashMap map,AssignCompPatientListFragment assignCompPatientListFragment,int index);
    }

    private void confirmationDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        //dialog.setTitle(getActivity().getResources().getString(R.string.alert_sputum_submit_title));
        dialog.setMessage(getActivity().getResources().getString(R.string.alert_sputum_submit_message));
        dialog.setIcon(android.R.drawable.ic_dialog_alert);

        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                try {
                     TDate =  new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                    String finalString;
                    String s=TDate.toString();
                    String ster[]= s.split("/");
                    finalStr=ster[2]+ "/" +ster[1]+ "/" +ster[0];
                } catch (Exception e) {
                    e.printStackTrace();
                }
              if(false){


              }
              else  {

                  if (dotsDays.equals("MWF")) {
                      if (!dateValidation(1)) {
                          // show the error message
                          System.out.println("Error Message in MWF ");
                          dateErrorMessage("Start date cannot be before " + edt_regDate.getText().toString() + " and the selected day should be MON/WED/FRI");
                      } else {
                          collectData();
                          int index = getArguments().getInt("index");
                          mListener.onAssignComplianceClicked(valueMap, mAssignCompPatientListFragment, index);
                      }
                  } else {
                      if (!dateValidation(2)) {
                          // show the error message
                          System.out.println("Error Message in TTS ");
                          dateErrorMessage("Choose the day as TUE,THU,SAT");
                          dateErrorMessage("Start date cannot be before " + edt_regDate.getText().toString() + " and the selected day should be TUE/THU/SAT");
                      } else {
                          collectData();
                          int index = getArguments().getInt("index");
                          mListener.onAssignComplianceClicked(valueMap, mAssignCompPatientListFragment, index);
                      }
                  }
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

    private void collectData(){

        valueMap.put("tbPatientId",pinfo.getpId());
        valueMap.put("complianceMethod",compMethod);
        valueMap.put("DOTDays", dotsDays);
        String s=txtDozStartDate.getText().toString();
        String ster[]= s.split("/");
        String finalString=ster[2]+ "/" +ster[1]+ "/" +ster[0];
        valueMap.put("patientDoseStartDate",finalString);
        System.out.println(finalString+"qqqqqqqqqqqqqqqqqwefgh");
        System.out.println(txtDozStartDate.getText());
        valueMap.put("phiId", MainActivity.PhiID);
        valueMap.put("empId", MainActivity.EmpID);

    }

    private void dateErrorMessage(String msg) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        //dialog.setTitle(getActivity().getResources().getString(R.string.alert_sputum_submit_title));
        dialog.setMessage(msg);

        dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
            dialog.show();
        }
    }

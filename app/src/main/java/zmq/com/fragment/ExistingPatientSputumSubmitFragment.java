package zmq.com.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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

/**
 * Created by zmq162 on 4/1/16.
 */
public class ExistingPatientSputumSubmitFragment extends Fragment implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText edtPId1,edtPId2,edtPname,edtState,edtDistrict,edtBlock,edtVillage,edtAddress;
    private Button btnFetchSubmit,btnSputumSubmit,btnSputumDate;
    int totalDose,testCounter;
    TextView sputumDate;
    private HashMap<String,String> valueMap;

    private Calendar myCalendar;

    LinearLayout linearLayout1,linearLayout2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_existing_patient_sputum_submit, container, false);
    }

    ExistingPatientSputumSubmitCallbacks comm;



    public interface ExistingPatientSputumSubmitCallbacks{

        void fetchSubmitClicked(HashMap<String,String> valueMap);
        void sputumSubmitClicked(HashMap<String,String> valueMap);
    }

    public void setExistingPatientSputumSubmitCallbacks(ExistingPatientSputumSubmitCallbacks comm) {
        this.comm = comm;
    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Submit Sputum Details");

        myCalendar = Calendar.getInstance();

        valueMap = new HashMap<String, String>();
        edtPId1 = (EditText) getActivity().findViewById(R.id.edt_patient_id_1);
        btnFetchSubmit = (Button) getActivity().findViewById(R.id.btn_submit1);


        linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.linear1);
        linearLayout2 = (LinearLayout) getActivity().findViewById(R.id.linear2);

        edtPId2 = (EditText) getActivity().findViewById(R.id.edt_patient_id_2);
        edtPname = (EditText) getActivity().findViewById(R.id.edt_p_name);
        edtState = (EditText) getActivity().findViewById(R.id.edt_state);
        edtDistrict = (EditText) getActivity().findViewById(R.id.edt_district);
        edtBlock = (EditText) getActivity().findViewById(R.id.edt_block);
        edtVillage = (EditText) getActivity().findViewById(R.id.edt_village);
        edtAddress = (EditText) getActivity().findViewById(R.id.edt_address);
        btnSputumSubmit = (Button) getActivity().findViewById(R.id.btn_submit2);
        btnSputumDate = (Button) getActivity().findViewById(R.id.btn_sputum_date);

        sputumDate = (TextView) getActivity().findViewById(R.id.txt_sputum_date);//

        if (getArguments() != null) {



            edtPId2.setText(getArguments().getString("patientId"));
            edtPname.setText(getArguments().getString("patientName")+" ( "+getArguments().getString("totalSputumTestResultSubmit")+"/"+getArguments().getString("totalSputumTestdone")+" ) ");
            edtState.setText(getArguments().getString("patientState"));
            edtDistrict.setText(getArguments().getString("patientDistrict"));
            edtBlock.setText(getArguments().getString("patientBlock"));
            edtVillage.setText(getArguments().getString("patientVillage"));
            edtAddress.setText(getArguments().getString("patientAddress"));
            totalDose= Integer.parseInt(getArguments().getString("TotalDose"));
            testCounter=Integer.parseInt(getArguments().getString("TestCounter"));
            System.out.println(totalDose + " from serverrrrrrrrrrrrrrrrrrrrr");
            System.out.println(testCounter + " from serverrrrrrrrrrrrrrrrrrrrr");
            if(testCounter==1 && totalDose<22) {
                linearLayout1.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.VISIBLE);
            }
            else if(testCounter==2 && (totalDose>=22 && totalDose<=24)){
                linearLayout1.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.VISIBLE);
            }
            else if(testCounter==3 && (totalDose>=22 && totalDose<=24)){
                linearLayout1.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.VISIBLE);
            }
            else if(testCounter==4 && (totalDose>=34 && totalDose<=36)){
                linearLayout1.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.VISIBLE);
            }
            else if(testCounter==5 && (totalDose>=34 && totalDose<=36)){
                linearLayout1.setVisibility(LinearLayout.GONE);
                linearLayout2.setVisibility(LinearLayout.VISIBLE);
            }


            else{

                Toast.makeText(getContext(),"Sorry,You cannot take new sputum now.",Toast.LENGTH_LONG).show();
            }
        }

        edtPId1.setText(MainActivity.officerInfo.getOfficerId()+".");

//        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
//        sputumDate.setText(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(MainActivity.ServerTodaydate);
        sputumDate.setText(dateString);


        btnFetchSubmit.setOnClickListener(this);
        btnSputumSubmit.setOnClickListener(this);
        btnSputumDate.setOnClickListener(this);
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

//                if( compareDates()){
//                    dateValidationDialog("please choose another date after today date");
//                    // Toast.makeText(getActivity(), "please choose another date after today date", Toast.LENGTH_SHORT).show();
//                    txt.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
//                }else {
//                    txt.setText(sdf.format(myCalendar.getTime()));
//
//                }

            }
        };

        new DatePickerDialog(getActivity(), date,
                myCalendar.get(Calendar.YEAR), myCalendar
                .get(Calendar.MONTH), myCalendar
                .get(Calendar.DAY_OF_MONTH)).show();

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_submit1:

                //existingPatientForSputumTest?pId=&sputumdate=&dmcId=
             //   Toast.makeText(getActivity(), "btn_submit1", Toast.LENGTH_SHORT).show();

                valueMap.put("pId", edtPId1.getText().toString());
                comm.fetchSubmitClicked(valueMap);
                break;

            case R.id.btn_submit2:

             //   Toast.makeText(getActivity(), "btn_submit2", Toast.LENGTH_SHORT).show();

                confirmationDialog();



                break;
            case R.id.btn_sputum_date:
                //Toast.makeText(getActivity(), "btn_sputum_date", Toast.LENGTH_SHORT).show();
                pickerDate(sputumDate);
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

                    valueMap.put("pId", edtPId2.getText().toString());
                    valueMap.put("dmcId", MainActivity.officerInfo.getOfficerId());

                  //  valueMap.put("sputumdate", sputumDate.getText().toString());
                    String s=sputumDate.getText().toString();
                    String ster[]= s.split("/");
                    String finalString=ster[2]+ "/" +ster[1]+ "/" +ster[0];
//                    System.out.println(ster[0] + "after breaking 1");
//                    System.out.println(ster[1] + "after breaking 2");
//                    System.out.println(ster[2] + "after breaking 3");

//                    System.out.println(finalString + " Final String");
                    valueMap.put("sputumdate", finalString);


                    System.out.println("Finally pId : " + edtPId2.getText().toString());
                    System.out.println("Finally dmcId : " + MainActivity.officerInfo.getOfficerId());
                    System.out.println("Finally sputumdate : " + sputumDate.getText().toString());
                    System.out.println("Finally comm : " + comm);
                    if(testCounter==1 && totalDose<=22) {
                        valueMap.put("specimen", "b");


                    }
                    else if(testCounter==2 && (totalDose>=22 && totalDose<=24)){
                        valueMap.put("specimen", "1 a");
                    }
                    else if(testCounter==3 && (totalDose>=22 && totalDose<=24)){
                        valueMap.put("specimen", "1 b");

                    }
                    else if(testCounter==4 && (totalDose>=34 && totalDose<=36)){
                        valueMap.put("specimen", "2 a");
                    }
                    else if(testCounter==5 && (totalDose>=34 && totalDose<=36)){
                        valueMap.put("specimen", "2 b");
                    }

                    comm.sputumSubmitClicked(valueMap);

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


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar cal1 = Calendar.getInstance();

        try {
            Date dateToday = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            cal1.setTime(dateToday);

            if(myCalendar.before(cal1)){
                System.out.println("Date1 is before Date2");
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}

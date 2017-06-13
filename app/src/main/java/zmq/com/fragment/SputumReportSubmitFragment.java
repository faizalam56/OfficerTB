package zmq.com.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import zmq.com.activity.MainActivity;
import zmq.com.floatlable.R;

/**
 * Created by zmq162 on 5/1/16.
 */
public class SputumReportSubmitFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private EditText edtPId1,edtPId2,edtPname,edtScanty;
    private Button btnFetchSubmit,btnSputumSubmit,btnSputumDate;
    TextView txtSputumDate;
    private HashMap<String,String> valueMap;
    Spinner spnExamine,spnApperance,spnResult;
    RadioButton radio1,radio2,radio3,radio4;
    private Calendar myCalendar;

    LinearLayout linearLayout1,linearLayout2;
    int radioButtonNo;

    String pId,pName,examineDate,resultDate,appearance,resultSputum,grading,scanty;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sputum_result_submit, container, false);
    }


    SputumReportSubmitCallbacks comm;

    public interface SputumReportSubmitCallbacks{

        public void sputumReportFetchSubmitClicked(HashMap<String,String> valueMap);
        public void sputumReportSputumResultSubmitClicked(HashMap<String,String> valueMap);
    }

    public void setSputumReportSubmitCallbacks(SputumReportSubmitCallbacks comm) {
        this.comm = comm;
    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Submit Sputum Result");

        valueMap = new HashMap<String, String>();

        linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.linear1);
        linearLayout2 = (LinearLayout) getActivity().findViewById(R.id.linear2);

        edtPId1 = (EditText) getActivity().findViewById(R.id.edt_patient_id_1);
        btnFetchSubmit = (Button) getActivity().findViewById(R.id.btn_submit1);


        linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.linear1);
        linearLayout2 = (LinearLayout) getActivity().findViewById(R.id.linear2);

        edtPId2 = (EditText) getActivity().findViewById(R.id.edt_patient_id_2);
        edtPname = (EditText) getActivity().findViewById(R.id.edt_p_name);
        edtScanty = (EditText) getActivity().findViewById(R.id.edt_scanty);

        txtSputumDate = (TextView) getActivity().findViewById(R.id.txt_sputum_date);

        spnExamine = (Spinner) getActivity().findViewById(R.id.spinner_examin_date);
        spnApperance = (Spinner) getActivity().findViewById(R.id.spinner_appearance);
        spnResult = (Spinner) getActivity().findViewById(R.id.spinner_result);

        radio1 = (RadioButton) getActivity().findViewById(R.id.radioButton1);
        radio2 = (RadioButton) getActivity().findViewById(R.id.radioButton2);
        radio3 = (RadioButton) getActivity().findViewById(R.id.radioButton3);
        radio4 = (RadioButton) getActivity().findViewById(R.id.radioButton4);

        btnSputumDate = (Button) getActivity().findViewById(R.id.btn_examin_date);
        btnSputumSubmit = (Button) getActivity().findViewById(R.id.btn_submit2);


        if (getArguments() != null) {

            linearLayout1.setVisibility(LinearLayout.GONE);
            linearLayout2.setVisibility(LinearLayout.VISIBLE);

            edtPId2.setText(getArguments().getString("patientId"));
            edtPname.setText(getArguments().getString("patientName") + " ( " + getArguments().getString("totalSputumTestResultSubmit") + "/" + getArguments().getString("totalSputumTestdone") + " ) ");
            getArguments().getStringArray("examineDate");
System.out.println( getArguments().getStringArray("examineDate")+ "ssssslslslslsllsllslsllsllslslsllsls");
            ArrayAdapter<String> adapter_date = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getArguments().getStringArray("examineDate"));
            adapter_date.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnExamine.setAdapter(adapter_date);
            spnExamine.setOnItemSelectedListener(this);
            //spnExamine.
        }


        spnApperance.setOnItemSelectedListener(this);
        spnResult.setOnItemSelectedListener(this);
        spnExamine.setOnItemSelectedListener(this);

        btnFetchSubmit.setOnClickListener(this);
        btnSputumSubmit.setOnClickListener(this);
        btnSputumDate.setOnClickListener(this);

        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);
        radio4.setOnClickListener(this);

//        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
//        txtSputumDate.setText(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(MainActivity.ServerTodaydate);
        txtSputumDate.setText(dateString);

        edtPId1.setText(MainActivity.officerInfo.getOfficerId()+".");
        grading = radio1.getText().toString();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {

            case R.id.spinner_examin_date:

              examineDate = parent.getItemAtPosition(position).toString();

                break;
            case R.id.spinner_appearance:
                appearance = parent.getItemAtPosition(position).toString();
               // Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_result:
                resultSputum = parent.getItemAtPosition(position).toString();

                if(position == 1){

                    radio1.setClickable(false);
                    radio2.setClickable(false);
                    radio3.setClickable(false);
                    radio4.setClickable(false);

                    if(radio1.isChecked()){
                        radio1.setChecked(false);
                    }
                    else if(radio2.isChecked()){
                        radio2.setChecked(false);
                    }
                    else if(radio3.isChecked()){
                        radio3.setChecked(false);
                    }
                    else if(radio4.isChecked()){
                        radio4.setChecked(false);
                        edtScanty.setVisibility(EditText.GONE);
                    }
                }
                else{

                    radio1.setClickable(true);
                    radio2.setClickable(true);
                    radio3.setClickable(true);
                    radio4.setClickable(true);
                }

                break;


        }
    }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

            switch (parent.getId()) {

                case R.id.spinner_examin_date:
                    break;
                case R.id.spinner_appearance:
                    break;
                case R.id.spinner_result:
                    break;


            }
        }

    private void pickerDate(final TextView txt) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString1 = dateFormat.format(MainActivity.ServerTodaydate);
        String ster[]= dateString1.split("/");
        String   finalStr=ster[2]+ "/" +ster[1]+ "/" +ster[0];
        System.out.println(finalStr+"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzccc");
        myCalendar = Calendar.getInstance();
        myCalendar.set(Integer.parseInt(ster[2]), (Integer.parseInt(ster[1])) - 1, Integer.parseInt(ster[0]));


   //     myCalendar = Calendar.getInstance();
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
                if( compareDates()){
                    dateValidationDialog("Please choose another date after this date "+examineDate.substring(2, 12));
                   // Toast.makeText(getActivity(), "Please choose another date after today date", Toast.LENGTH_SHORT).show();
                    txt.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                }else {
                    txt.setText(sdf.format(myCalendar.getTime()));

                }
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

            case R.id.btn_submit1:
           //     Toast.makeText(getActivity(), "btn_submit1 clicked", Toast.LENGTH_SHORT).show();
                valueMap.put("pId",edtPId1.getText().toString());
                comm.sputumReportFetchSubmitClicked(valueMap);

                break;
            case R.id.btn_submit2:

            //   Toast.makeText(getActivity(), "btn_submit2 clicked", Toast.LENGTH_SHORT).show();


                System.out.println(radioButtonNo+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                System.out.println(R.id.radioButton4+"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
if(radioButtonNo == R.id.radioButton4)
{
    if(edtScanty.getText().toString().equals(""))
    {
        Toast.makeText(getActivity(), "Please fill appropriate Scanty Value ", Toast.LENGTH_LONG).show();
    }
    else
    {
        confirmationDialog();
    }
}
 else
{
    confirmationDialog();
}

//
//                if(!edtScanty.getText().toString().equals("")  ){
//                    confirmationDialog();
//                }
//                else{
//                    Toast.makeText(getActivity(), "Please fill appropriate Scanty Value ", Toast.LENGTH_LONG).show();
//                }



                break;

            case R.id.btn_examin_date:
                //Toast.makeText(getActivity(), "btn_sputum_date", Toast.LENGTH_SHORT).show();
                pickerDate(txtSputumDate);
                break;

            case R.id.radioButton1:
                Toast.makeText(getActivity(), radio1.getText(), Toast.LENGTH_SHORT).show();
                grading = radio1.getText().toString();
                radioButtonNo= R.id.radioButton1;
                if(edtScanty.getVisibility() == EditText.VISIBLE){
                    edtScanty.setVisibility(EditText.GONE);
                }
                break;
            case R.id.radioButton2:
                Toast.makeText(getActivity(), radio2.getText(), Toast.LENGTH_SHORT).show();
                grading = radio2.getText().toString();
                radioButtonNo=R.id.radioButton2;
                if(edtScanty.getVisibility() == EditText.VISIBLE){
                    edtScanty.setVisibility(EditText.GONE);
                }
                break;
            case R.id.radioButton3:
                Toast.makeText(getActivity(), radio3.getText(), Toast.LENGTH_SHORT).show();

                grading = radio3.getText().toString();
                radioButtonNo=R.id.radioButton3;
                if(edtScanty.getVisibility() == EditText.VISIBLE){
                    edtScanty.setVisibility(EditText.GONE);
                }
                break;
            case R.id.radioButton4:
              //  Toast.makeText(getActivity(), radio4.getText(), Toast.LENGTH_SHORT).show();
                radioButtonNo=v.getId();
                edtScanty.setVisibility(EditText.VISIBLE);
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
                        collectData();
                        comm.sputumReportSputumResultSubmitClicked(valueMap);


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
        dialog.setIcon(android.R.drawable.ic_dialog_alert);

        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void collectData() {

        //submitResultOfSputumTest?pId=&dmcId=&examinationDate=&TestNo=&visualApperance=&result=&grading=&scanty=

        valueMap.put("pId", edtPId2.getText().toString());
        valueMap.put("dmcId", MainActivity.officerInfo.getOfficerId());

     //   valueMap.put("examinationDate", txtSputumDate.getText().toString());  //commented by bilal


//////////////////////

        System.out.println(txtSputumDate.getText().toString());

        String s=txtSputumDate.getText().toString();
     String ster[]= s.split("/");
        System.out.println(ster[0] + "after breaking 1");
        System.out.println(ster[1] + "after breaking 2");
        System.out.println(ster[2] + "after breaking 3");
String finalString=ster[2]+ "/" +ster[1]+ "/" +ster[0];
        System.out.println(finalString + " Final String");
        valueMap.put("examinationDate", finalString);
        System.out.println(txtSputumDate.getText().toString() + " examinationnnnnnn dateeeeeeeeee ");

   //////////////////////////

        String arr[] = examineDate.split(" / ");
        valueMap.put("TestNo",arr[1]);
        valueMap.put("visualApperance",appearance);

        valueMap.put("result",resultSputum);

        if(resultSputum.equals("Negative")){
            grading = "0";
            scanty = "0";
        }
        else{

            if(radio4.isChecked()){

                grading = "0";
                scanty =  edtScanty.getText().toString();
            }else{

                scanty = "0";
                //grading =  edtScanty.getText().toString();
            }
        }
        valueMap.put("grading",grading);
        valueMap.put("scanty", scanty);


        System.out.println("P ID " + edtPId2.getText().toString());
        System.out.println("PID " + edtPname.getText().toString());
        System.out.println("TestNo Date " + arr[1]);
        System.out.println("Result Date  " + txtSputumDate.getText().toString());
        System.out.println("Apperance " + appearance);
        System.out.println("Result " + resultSputum);
        System.out.println("Scanty  "+scanty);

        System.out.println("Grading  "+grading);
    }


    public boolean compareDates(){

        int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//        Calendar cal1 = Calendar.getInstance();


//        try {
//            Date dateToday = sdf.parse(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
//            cal1.setTime(dateToday);

        System.out.println("EXAMINE DATE ____________ " + examineDate.substring(2, 12));
            Calendar  cal1 = getCalanderDate(examineDate.substring(2,12));

            if(myCalendar.before(cal1)){
                System.out.println("Date1 is before Date2");
                return true;
            }


//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    private Calendar getCalanderDate(String startDateString) {
        // start date is a date which were passing in the format dd/MM/yyyy

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");//new SimpleDateFormat("yyyy-dd-MM");

        Date startDate;
        try {
            startDate = df.parse(startDateString);
            cal.setTime(startDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            System.out.println("exceptionnnnn " +e);
            e.printStackTrace();

        }
//        cal.add(Calendar.DAY_OF_YEAR, 1);
//        Date nextDate = cal.getTime();
//        System.out.println("getb My OBJECT DATE "+nextDate);
//        String newNextDateString = df.format(nextDate);
//        System.out.println("get My String DATE " + newNextDateString);

        return cal;
    }

}

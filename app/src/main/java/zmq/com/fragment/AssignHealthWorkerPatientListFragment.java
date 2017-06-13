package zmq.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import zmq.com.adapter.AssignCompAdapter;
import zmq.com.adapter.AssignHealthWorkerAdapter;
import zmq.com.floatlable.R;
import zmq.com.model.EmployeInfo;
import zmq.com.model.PatientInfo;

/**
 * Created by zmq162 on 19/2/16.
 */
public class AssignHealthWorkerPatientListFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView list;
    ArrayList<PatientInfo> patientList;
    ArrayList<EmployeInfo> providers;
    ArrayList<EmployeInfo> observers;
    AssignHealthWorkerAdapter adapter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.assign_health_worker_p_list, container, false);
    }


    AssignHealthWorkerPatientCallbacks comm;

    public interface AssignHealthWorkerPatientCallbacks{

        void  assignHealthWorkerPatientClicked(PatientInfo pInfo, ArrayList<EmployeInfo> providers, ArrayList<EmployeInfo> observers, AssignHealthWorkerPatientListFragment assignHealthWorkerPatientListFragment, int index);
    }

    public void setAssignHealthWorkerPatientCallbacks(AssignHealthWorkerPatientCallbacks comm) {
        this.comm = comm;
    }

//    // TODO: Rename and change types and number of parameters
//    public static AssignHealthWorkerPatientListFragment newInstance() {
//        AssignHealthWorkerPatientListFragment fragment = new AssignHealthWorkerPatientListFragment();
////        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
////        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.assign_health_worker_toolbar);
        toolbar.setTitle("Assign Health Worker");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        patientList = (ArrayList<PatientInfo>) getArguments().getSerializable("patients");

        providers = (ArrayList<EmployeInfo>) getArguments().getSerializable("providers");
        observers = (ArrayList<EmployeInfo>) getArguments().getSerializable("observers");

        list = (ListView) getActivity().findViewById(R.id.assign_health_worker_listView);

        adapter= new AssignHealthWorkerAdapter(getActivity(), R.layout.assign_health_worker_list_item, patientList);

        list.setAdapter(adapter);//new ArrayAdapter(this, R.layout.list_item,R.id.textView1,obj));

        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
        // TODO Auto-generated method stub
    //    Toast.makeText(getActivity(), "ITEM " + position, Toast.LENGTH_SHORT).show();
        comm.assignHealthWorkerPatientClicked(patientList.get(position), providers, observers,this, position);
    }

    public void refressListOnResponse(int position){


        // to remove the given item from list of patient
        System.out.println("#### before Remove Successfully "+position+" list value  "+patientList);
        patientList.remove(position);
        adapter.notifyDataSetChanged();
        System.out.println("**** after Remove Successfully " + position+" list value  "+patientList);

    }

}


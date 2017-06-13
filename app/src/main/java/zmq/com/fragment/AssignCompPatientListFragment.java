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
import zmq.com.adapter.MophiAdapter;
import zmq.com.floatlable.R;
import zmq.com.model.PatientInfo;

/**
 * Created by zmq162 on 19/2/16.
 */
public class AssignCompPatientListFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView list;
    ArrayList<PatientInfo> data;
    AssignCompAdapter adapter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.assign_comp_p_list, container, false);
    }


    AssignCompPatientCallbacks comm;

    public interface AssignCompPatientCallbacks{

        void  assignCompPatientClicked(PatientInfo pInfo, AssignCompPatientListFragment assignCompPatientListFragment, int index);
    }

    public void setAssignCompPatientCallbacks(AssignCompPatientCallbacks comm) {
        this.comm = comm;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.assign_comp_toolbar);
        toolbar.setTitle("Assign Compliance Patients");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        data = (ArrayList<PatientInfo>) getArguments().getSerializable("patients");

        list = (ListView) getActivity().findViewById(R.id.assign_comp_listView);

        adapter= new AssignCompAdapter(getActivity(), R.layout.assign_comp_list_item, data);

        list.setAdapter(adapter);//new ArrayAdapter(this, R.layout.list_item,R.id.textView1,obj));

        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
        // TODO Auto-generated method stub
     //   Toast.makeText(getActivity(), "ITEM " + position, Toast.LENGTH_SHORT).show();
        comm.assignCompPatientClicked(data.get(position), this, position);
    }

    public void refressListOnResponse(int position){

        data.remove(position);
        adapter.notifyDataSetChanged();

    }

}


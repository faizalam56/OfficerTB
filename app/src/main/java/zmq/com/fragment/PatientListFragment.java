package zmq.com.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import zmq.com.floatlable.R;
import zmq.com.model.PatientInfo;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PatientListFragment extends ListFragment {
    ListView poList;

    ArrayList<PatientInfo> infoArrayList;
    String[]pName;
    String[] pId=null;
    String[] pNameAndId=null;

    private PatientListFragmentCallbacks comm;
    private Toolbar toolbar;


    public interface PatientListFragmentCallbacks{

        public void patientItemClicked(HashMap map);
    }

    public void setPatientClickCallbacks(PatientListFragmentCallbacks comm) {
        this.comm = comm;
    }

    public PatientListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_list, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.view_patient_list_toolbar);
        toolbar.setTitle("List of Patients");

        poList = getListView();

        Bundle bundle = getArguments();
        infoArrayList = (ArrayList<PatientInfo>) bundle.getSerializable("patients");

        pName = new String[infoArrayList.size()];
        pId=new String[infoArrayList.size()];
        pNameAndId=new String[infoArrayList.size()];
        for (int i = 0; i < infoArrayList.size(); i++) {

            pName[i] = infoArrayList.get(i).getpName();
            pId[i] = infoArrayList.get(i).getpId();
            pNameAndId[i]=pName[i]+"\n"+pId[i];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pNameAndId);



        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

     //   Toast.makeText(getActivity(),"ITEM "+position,Toast.LENGTH_SHORT).show();

        HashMap<String,String> map = new HashMap<>();
        System.out.println("Patient ID "+infoArrayList.get(position).getpId());
        map.put("PId",infoArrayList.get(position).getpId());
        comm.patientItemClicked(map);
    }

}

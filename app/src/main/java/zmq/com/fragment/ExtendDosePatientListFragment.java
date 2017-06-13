package zmq.com.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import zmq.com.adapter.ExtendDoseAdapter;
import zmq.com.floatlable.R;
import zmq.com.model.PatientInfo;

/**
 * Created by zmq162 on 9/3/16.
 */
public class ExtendDosePatientListFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView list;
    ArrayList<PatientInfo> data;
    ExtendDoseAdapter adapter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.extend_dose_p_list, container, false);
    }


    ExtendDosePatientCallbacks comm;

    public interface ExtendDosePatientCallbacks {

        void extendDosePatientClicked(HashMap paramsValue, ExtendDosePatientListFragment extendDosePatientListFragment, int index);
    }

    public void setExtendDosePatientCallbacks(ExtendDosePatientCallbacks comm) {
        this.comm = comm;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toolbar = (Toolbar) getActivity().findViewById(R.id.extend_dose_toolbar);
        toolbar.setTitle("Extend Dose Patients");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        data = (ArrayList<PatientInfo>) getArguments().getSerializable("patients");

        list = (ListView) getActivity().findViewById(R.id.extend_dose_listView);

        adapter = new ExtendDoseAdapter(getActivity(), R.layout.extend_dose_list_item, data);

        list.setAdapter(adapter);//new ArrayAdapter(this, R.layout.list_item,R.id.textView1,obj));

        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
        // TODO Auto-generated method stub
        //   Toast.makeText(getActivity(), "ITEM " + position, Toast.LENGTH_SHORT).show();


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Extend Doses");
        dialog.setMessage("Do you want to extend the dose");
        dialog.setIcon(R.drawable.ic_dialog_alert);

        final String pId = data.get(position).getpId();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        TextView pName = (TextView) dialogView.findViewById(R.id.text_p_name);
        TextView pState = (TextView) dialogView.findViewById(R.id.text_state);
        TextView pDistt = (TextView) dialogView.findViewById(R.id.text_distt);
        TextView pVillg = (TextView) dialogView.findViewById(R.id.text_vilag);
        TextView pAddrs = (TextView) dialogView.findViewById(R.id.text_addrs);

        pName.setText(data.get(position).getpName());
        pState.setText(data.get(position).getpState());
        pDistt.setText(data.get(position).getpCity());
        pVillg.setText(data.get(position).getpVillage());
        pAddrs.setText(data.get(position).getpAddress());

        dialog.setView(dialogView);

        dialog.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //dialog.cancel();
                        HashMap map = new HashMap();
                        map.put("Pid", pId);
                        comm.extendDosePatientClicked(map, ExtendDosePatientListFragment.this, position);

                    }
                });

        dialog.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        dialog.show();
    }

/*
* @this method remove the element
* */
    public void refressListOnResponse(int position) {

        data.remove(position);
        adapter.notifyDataSetChanged();

    }
}

package zmq.com.fragment;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import zmq.com.activity.MainActivity;
import zmq.com.adapter.MyRecyclerViewAdapter;
import zmq.com.floatlable.R;
import zmq.com.model.DataObject;
import zmq.com.model.OfficerInfo;

public class OfficerMenu extends Fragment implements MyRecyclerViewAdapter.MyClickListener{

    int imageId[] = {R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4};

    private TextView name_txt,desg_txt,contact_txt,date_txt;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;

    private String []officerWork;
    ArrayList<DataObject> dataset;
//    private GridLayoutManager gridLayoutManager;
//    private StaggeredGridLayoutManager staggeredGridLayoutManager
            ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.officer_menu_frag, container, false);
    }

    OfficerMenuCallbacks comm;


    public interface OfficerMenuCallbacks{
        void  menuItemClicked(int position);
        void onbackPressHandler();

    }

    public void setOfficerMenuCallbacks(OfficerMenuCallbacks comm) {
        this.comm = comm;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
/*
//  to make it separate each officer


        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("DMC");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        name_txt = (TextView) getActivity().findViewById(R.id.emp_name);
        desg_txt = (TextView) getActivity().findViewById(R.id.emp_desgnation);
        contact_txt = (TextView) getActivity().findViewById(R.id.emp_contact);
        date_txt = (TextView) getActivity().findViewById(R.id.emp_join_date);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);

        OfficerInfo officerInfo = MainActivity.officerInfo ;

        name_txt.setText(officerInfo.getEmpName());
        desg_txt.setText(officerInfo.getEmpDesignation());
        contact_txt.setText(officerInfo.getContactNo());
        date_txt.setText(officerInfo.getJoiningDate());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
//        gridLayoutManager = new GridLayoutManager(getActivity());
//        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(this);

//        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
//                .MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.i("ZMQ", " Clicked on Item " + position);
//            }
//        });

*/

        name_txt = (TextView) getActivity().findViewById(R.id.emp_name);
        desg_txt = (TextView) getActivity().findViewById(R.id.emp_desgnation);
        contact_txt = (TextView) getActivity().findViewById(R.id.emp_contact);
        date_txt = (TextView) getActivity().findViewById(R.id.emp_join_date);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);


        OfficerInfo officerInfo = MainActivity.officerInfo ;
        int dasignation = Integer.parseInt(officerInfo.getEmpDesignation());

        name_txt.setText(officerInfo.getEmpName());

        contact_txt.setText(officerInfo.getContactNo());
        date_txt.setText(officerInfo.getJoiningDate());


        switch (dasignation){

            case 1:// for DMC

                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
                toolbar.setTitle("DMC");
                toolbar.setNavigationIcon(R.drawable.dmc_blue);
//                toolbar.setNavigationIcon(R.drawable.icon4);
//                toolbar.setBackgroundColor(getResources().getColor(R.color.colorDMCtoolbar));
               // getActivity().setTheme(R.style.DMCtheme);

                desg_txt.setText(getResources().getString(R.string.dmc));
                officerWork = getResources().getStringArray(R.array.dmc_work);
                int imageIdDMC[] = {R.drawable.new_patient_registration,R.drawable.submit_sputum_details,R.drawable.submit_sputum_result,R.drawable.view_patient_report};
                dataset = getDataSet(imageIdDMC,officerWork);
                break;

            case 2:// for STS
                getActivity().setTheme(R.style.STStheme);
                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
                toolbar.setTitle("STS");
                toolbar.setNavigationIcon(R.drawable.sts_green);
             //   toolbar.setBackgroundColor(getResources().getColor(R.color.colorSTStoolbar));
                desg_txt.setText(getResources().getString(R.string.sts));
                officerWork = getResources().getStringArray(R.array.sts_work);
                int imageIdSTS[] = {R.drawable.address_verification};
                dataset = getDataSet(imageIdSTS, officerWork);
                break;

            case 3:// for MOPHI
                getActivity().setTheme(R.style.MOPHItheme);
                toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("MOPHI");
                toolbar.setNavigationIcon(R.drawable.mophi_orange);
              //  toolbar.setBackgroundColor(getResources().getColor(R.color.colorMOPHItoolbar));
                desg_txt.setText(getResources().getString(R.string.mophi));
                officerWork = getResources().getStringArray(R.array.mophi_work);
                int imageIdMOPHI[] = {R.drawable.patient_registration,R.drawable.assign_compliance_method,R.drawable.assign_health_worker,R.drawable.extend_phase_1,R.drawable.view_report};
                dataset = getDataSet(imageIdMOPHI, officerWork);
                break;

            default:
                break;
        }

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(this);


    }


    private ArrayList<DataObject> getDataSet(int imageID[],String officerWork[]) {

        ArrayList results = new ArrayList<DataObject>();

        for (int index = 0; index < officerWork.length; index++) {
            DataObject obj = new DataObject(imageID[index], officerWork[index]);
            results.add(index, obj);
        }
        return results;
    }

    private ArrayList<DataObject> getDataSet() {

        ArrayList results = new ArrayList<DataObject>();
        String dmcWork[] = getResources().getStringArray(R.array.dmc_work);

        for (int index = 0; index < dmcWork.length; index++) {
            DataObject obj = new DataObject(imageId[index], dmcWork[index]);
            results.add(index, obj);
        }
        return results;
    }


    @Override
    public void onItemClick(int position, View v) {

        comm.menuItemClicked(position);
    }

}




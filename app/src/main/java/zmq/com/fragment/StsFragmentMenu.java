package zmq.com.fragment;

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

import java.util.ArrayList;

import zmq.com.activity.MainActivity;
import zmq.com.adapter.MyRecyclerViewAdapter;
import zmq.com.floatlable.R;
import zmq.com.model.DataObject;
import zmq.com.model.OfficerInfo;

/**
 * Created by zmq162 on 6/2/16.
 */
public class StsFragmentMenu extends Fragment implements MyRecyclerViewAdapter.MyClickListener{

    int imageId[] = {R.drawable.icon1};

    private TextView name_txt,desg_txt,contact_txt,date_txt;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;
//    private GridLayoutManager gridLayoutManager;
//    private StaggeredGridLayoutManager staggeredGridLayoutManager
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sts_menu, container, false);
    }

    OfficerMenuCallbacks comm;


    public interface OfficerMenuCallbacks{
         void  menuItemClicked(int position);

    }

    public void setOfficerMenuCallbacks(OfficerMenuCallbacks comm) {
        this.comm = comm;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("STS");

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







    }

    private ArrayList<DataObject> getDataSet() {

        ArrayList results = new ArrayList<DataObject>();
        String stsWork[] = getResources().getStringArray(R.array.sts_work);

        for (int index = 0; index < stsWork.length; index++) {
            DataObject obj = new DataObject(imageId[index], stsWork[index]);
            results.add(index, obj);
        }
        return results;
    }


    @Override
    public void onItemClick(int position, View v) {

        comm.menuItemClicked(position);
    }

}

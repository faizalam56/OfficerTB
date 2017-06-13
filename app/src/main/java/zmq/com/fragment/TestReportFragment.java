package zmq.com.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zmq.com.floatlable.R;


public class TestReportFragment extends Fragment{


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view  = inflater.inflate(R.layout.custom_table, container, false);
		return view;

	}
}

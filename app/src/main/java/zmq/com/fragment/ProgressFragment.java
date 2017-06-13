package zmq.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import zmq.com.floatlable.R;

/**
 * Created by zmq162 on 28/12/15.
 */
public class ProgressFragment extends Fragment{


    private TextView progressTxt;
    private String progTxt= "ASIFFFFF";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.progress_frag, container, false);
        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progTxt = getArguments().getString("myProgText");
        progressTxt = (TextView) getActivity().findViewById(R.id.progress_txt);
        progressTxt.setText(progTxt);
    }
}

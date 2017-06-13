package zmq.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import zmq.com.floatlable.R;
import zmq.com.model.PatientInfo;

/**
 * Created by zmq162 on 19/2/16.
 */
public class AssignHealthWorkerAdapter extends BaseAdapter {

    ArrayList<PatientInfo> mData;
    Context mContext;
    int resElement;

    public AssignHealthWorkerAdapter(Context context, int resource, ArrayList<PatientInfo> data) {
        // TODO Auto-generated constructor stub
        mData = data;
        mContext = context;
        resElement = resource;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyHolder holder = null;
        if(row == null){

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resElement, parent, false);
            holder = new MyHolder(row);
            row.setTag(holder);
        }
        else{
            holder = (MyHolder) row.getTag();
        }

        holder.titleTxt.setText(""+mData.get(position).getpName());
        holder.subtitleTxt1.setText(""+mData.get(position).getpId());
        return row;
    }

    class MyHolder{

        TextView titleTxt;
        TextView subtitleTxt1;

        public MyHolder(View view) {
            titleTxt = (TextView) view.findViewById(R.id.txt_assign_health_worker_p_name);
            subtitleTxt1 = (TextView) view.findViewById(R.id.txt_assign_health_worker_p_id);

        }
    }

}

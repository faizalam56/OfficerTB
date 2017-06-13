package zmq.com.adapter;

import android.content.Context;
import android.provider.UserDictionary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

import zmq.com.floatlable.R;
import zmq.com.model.EmployeInfo;
import zmq.com.model.PatientInfo;

/**
 * Created by zmq162 on 9/3/16.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    ArrayList<EmployeInfo> mData;
    Context mContext;
    int resElement;

    public CustomSpinnerAdapter(Context context, int resource, ArrayList<EmployeInfo> data) {
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


        holder.titleTxt.setText(""+ WordUtils.capitalize(mData.get(position).getEmpName().toLowerCase()));
        holder.subtitleTxt1.setText(""+ WordUtils.capitalize(mData.get(position).getProviderVillage().toLowerCase()));
        return row;
    }

    class MyHolder{

        TextView titleTxt;
        TextView subtitleTxt1;

        public MyHolder(View view) {
            titleTxt = (TextView) view.findViewById(R.id.title_name);
            subtitleTxt1 = (TextView) view.findViewById(R.id.sub_title_name);

        }
    }

}

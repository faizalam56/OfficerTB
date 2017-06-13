package zmq.com.networks;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class NetworkJSON {
	
//	String URL = "http://192.168.1.101/TB_plateform/";
//	String URL = "http://www.connect2mfi.org/TB_plateform/";

//	String URL = "http://192.168.1.201/Tb_plateform_webtool/index.php/PatientController/";

//	String URL = "http://connect2mfi.org/Tb_platform_webtool/index.php/PatientController/";

// replace connect2mfi with 198.143.170.147

//    String URL = "http://connect2mfi.org/TB_platform/DMC_ControllerForAndroid/";// right url for WEB

//   String URL =  "http://192.168.1.5/TB_platform/index.php/DMC_ControllerForAndroid/";
	String URL = "http://192.168.1.10/Tb_platform_webtool/index.php/DMC_ControllerForAndroid/";// right url for Local
//	String URL =  "http://198.143.170.147/TB_platform/DMC_ControllerForAndroid/";
//	String URL = "http://192.168.1.219/Tb_platform_webtool/index.php/PatientController/";


	static JSONArray jArray = null;
	static JSONObject jObject = null;

	String service;
	public NetworkJSON() {
		jObject = null;
	}

	@SuppressWarnings("deprecation")
	public JSONObject getJsonObject(String serviceName, HashMap<String,String> param , int parmChecker, int multiChecker) {
		service = serviceName;
		URL = URL + serviceName;
		StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URL);


		try {

			if(parmChecker != 0 && multiChecker == 0){

				ArrayList<NameValuePair> paramsValue = new ArrayList<NameValuePair>();

				Set keys = param.keySet();
				Iterator itr = keys.iterator();

				String key;
				Object value;

				while(itr.hasNext()) {
					key = (String) itr.next();
					value = param.get(key);

					if(value instanceof String){
						String mStr  = (String) value;
						paramsValue.add(new BasicNameValuePair(key, mStr));
					}
				}
				httpPost.setEntity(new UrlEncodedFormEntity(paramsValue));
			}



			Log.d("before result ==>", " Service Name : "+serviceName + " and Param are : "+ param);

			HttpResponse response = client.execute(httpPost);
			System.out.println(" XXXXXXXX "+response.toString());
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();

			System.out.println("***** StatusCode " + statusCode);

			if (statusCode == 200) {

				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {

				Log.d("after result ==>", " Service Name : "+serviceName + " and Param are : "+ param);
				Log.e("==>", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			System.out.println(" ClientProtocolException Exception " + e);
			e.printStackTrace();
			Utility.EXCEPTION_STRING=e.getMessage();
			return jObject;
		} catch (IOException e) {
			System.out.println("IOException  Exception " +e);
			e.getMessage();
			e.printStackTrace();
			Utility.EXCEPTION_STRING=e.getMessage();
			return jObject;
		}

		System.out.println(" ReSPONSE " + builder.toString());

		// Parse String to JSON object
		try {
			jObject = new JSONObject(builder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException  Exception " +e);
			e.printStackTrace();
			Utility.EXCEPTION_STRING=e.getMessage();
			return jObject;
		}

    return jObject;
		
	}	

}

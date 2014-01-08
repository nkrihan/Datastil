package biz.noip.datastil;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;

import biz.noip.datastil.MainActivity.AsyncJSON;

import android.app.Activity;
import android.content.Context;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


import android.util.Log;

import android.view.Window;

import android.widget.LinearLayout;
import android.widget.TextView;






public class PassInfo extends Activity{
    private static final String TAG_TIME = "time";
    private static final String TAG_DAY = "day";
    private static final String TAG_ACTIVITY = "aktivitet";
    private static final String TAG_BOOKABLE = "bokningsbara";
    private static final String TAG_LOCAL = "lokal";
    private static final String TAG_RES = "resurs";
    private static final String TAG_SCORE = "score";
    private static final String TAG_ID = "id";
    private static String url = "http://datastil.no-ip.biz:12345/class/";
	            private String mData;
	
			
				@Override
                  protected void onCreate(Bundle savedInstanceState) {
                  	 super.onCreate(savedInstanceState);
                     Log.d(requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)+"","");
                     setContentView(R.layout.activitylist_layout);
                     getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                             R.layout.header);
                     Bundle data = getIntent().getExtras();
                     JSONObject json = null;
					try {
						json = new JSONObject(data.getString("json").toString());
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

                     setContentView(R.layout.passinfo_layout);
                     
                     final TextView text = (TextView) findViewById(R.id.passinfotext);
                     try {
						text.setText(json.getString(TAG_ACTIVITY));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                     ASYNCgetPlotData obj= new ASYNCgetPlotData();
                     GraphViewData[] c = null;
                     try {
						c = obj.execute(url+json.getString(TAG_ID)).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                     GraphView graphView = new LineGraphView(this, "Bokningsbar");
                     
                     final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");
                     graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                    	   @Override
                    	   public String formatLabel(double value, boolean isValueX) {
                    	      if (isValueX) {
                    	    	  Date d = new Date((long) value);
                    	    	  Log.e("date value", Double.toString(value));
                    	    	  Log.e("date", d.toString());
                                  return dateFormat.format(d);
                    	         }
                    	      
                    	      return null; // let graphview generate Y-axis label for us
                    	   }
                    	});
                     
                     
                     graphView.addSeries(new GraphViewSeries(c));
                  graphView.setScrollable(false);
                  // optional - activate scaling / zooming
                  graphView.setScalable(false);
          
                  
                  LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
                  layout.addView(graphView);

               }
				
				
				
             	
                public class ASYNCgetPlotData extends AsyncTask<String , GraphViewData[], GraphViewData[]> {
                 	protected GraphViewData[] doInBackground(String... address){
                 		 
                 		 // Creating new JSON Parser
                         JSONParser jParser = new JSONParser();
                  
                         // Getting JSON from URL
                         Log.e("URL", address[0]);
                         JSONArray json = jParser.getJSONFromUrl(address[0]);
                         int num = json.length();
                         GraphViewData[] data = new GraphViewData[num];
                         for(int i=0 ; i< num ;i++){
                        	 try {
                        		JSONObject object = json.getJSONObject(i);
                        		Log.e("Date from JSON", Long.toString(object.getLong(TAG_BOOKABLE)));
								data[i] = new GraphViewData( object.getLong(TAG_TIME) , object.getInt(TAG_BOOKABLE) );
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        	 
                         }
                         return data;
                 	}
                 	
                 	protected void onPostExecute(Context context , JSONObject c) {
                         

                     }
                 }
                 
                
				
				
}
				
	
		
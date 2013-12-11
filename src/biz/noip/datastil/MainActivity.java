package biz.noip.datastil;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;






public class MainActivity extends ListActivity  {
              
                  TextView content;
                  
                  //URL to get JSON Array
                  private static String url = "http://datastil.no-ip.biz:12345/classes/0?filter=53";
               
                  //JSON Node Names
                  private static final String TAG_TIME = "time";
                  private static final String TAG_DAY = "day";
                  private static final String TAG_ACTIVITY = "aktivitet";
                  private static final String TAG_BOOKABLE = "bokningsbara";
                  private static final String TAG_LOCAL = "lokal";
                  private static final String TAG_RES = "resurs";
                  private static final String TAG_SCORE = "score";
                  
                  JSONArray dataJSON = null;
                  
/*				@Override
                  protected void onCreate(Bundle savedInstanceState) {
                  	 super.onCreate(savedInstanceState);
                     Log.d(requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)+"","");
                     setContentView(R.layout.list_example);
                     getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                             R.layout.header);
                     // some code
                     content = (TextView)findViewById(R.id.output);
                     Cursor cursor = getContentResolver().query(Contacts.CONTENT_URI, new String[] {Contacts._ID, Contacts.DISPLAY_NAME, Contacts.HAS_PHONE_NUMBER}, null, null, null);
                     startManagingCursor(cursor);
          
                     // the desired columns to be bound
                     String[] columns = new String[] { Contacts.DISPLAY_NAME, Contacts.HAS_PHONE_NUMBER};
                     // the XML defined views which the data will be bound to
                     int[] to = new int[] { R.id.name_entry, R.id.number_entry };
          
                     // create the adapter using the cursor pointing to the desired data as well as the layout information
                     SimpleCursorAdapter mAdapter = new PassListCursorAdapter(this, R.layout.passlist, cursor, columns, to);
          
                     // set this adapter as your ListActivity's adapter
                     setListAdapter(mAdapter);
                     
                 }*/
				
				@Override
                  protected void onCreate(Bundle savedInstanceState) {
                  	 super.onCreate(savedInstanceState);
                     Log.d(requestWindowFeature(Window.FEATURE_CUSTOM_TITLE)+"","");
                     setContentView(R.layout.activitylist_layout);
                     getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
                             R.layout.header);
                     AsyncJSON obj= new AsyncJSON();
                     JSONArray c = null;
					try {
						c = obj.execute(url).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
             		// some code
					 
                     content = (TextView)findViewById(R.id.output);

                     // the desired columns to be bound
					   String[] from = new String[] { TAG_TIME, TAG_DAY , TAG_ACTIVITY,TAG_BOOKABLE };

                     // the XML defined views which the data will be bound to
                     int[] to = new int[] { R.id.textViewTime, R.id.textViewDay, R.id.textViewClass ,R.id.textViewFree };
          
                     // create the adapter using the cursor pointing to the desired data as well as the layout information
                     //JSONArrayAdapter mAdapter = new JSONArrayAdapter(this, R.layout.passlist, new JSONArray[] {c} , from, to);
                     JSONArrayAdapter mAdapter = new JSONArrayAdapter(this, c , R.layout.item2 , from , to);  
                     // set this adapter as your ListActivity's adapter
                     setListAdapter(mAdapter);
             		

                      	 
                 }				
              

                @Override
                protected void onListItemClick(ListView l, View v, int position, long id) {
                      
                      super.onListItemClick(l, v, position, id);
              
                         // ListView Clicked item index
                         int itemPosition     = position;
                         
                         // ListView Clicked item value
                         JSONObject namestring = (JSONObject) l.getItemAtPosition(position);
                         String itemValue = null;
						try {
							itemValue = (String) namestring.getString(TAG_ACTIVITY);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                         
                            
                         content.setText("Click : \n  Position :"+itemPosition+"  \n  ListItem : " +itemValue);
                         Intent intent = new Intent(this, PassInfo.class);
                         String message = namestring.toString();
                         intent.putExtra("json", message);
                         startActivity(intent);
                         

                         
                }
                
                public class AsyncJSON extends AsyncTask<String , JSONArray, JSONArray> {
                	protected JSONArray doInBackground(String... address){
                		
                		 // Creating new JSON Parser
                        JSONParser jParser = new JSONParser();
                 
                        // Getting JSON from URL
                        Log.e("URL", address[0]);
                        JSONArray json = jParser.getJSONFromUrl(address[0]);
                        return json;
                	}
                	
                	protected void onPostExecute(Context context , JSONObject c) {
                        

                    }
                }
}
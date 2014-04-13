

package com.facebook.samples.sessionlogin;

import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;



import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.facebook.widget.UserSettingsFragment;

public class LoginUsingLoginFragmentActivity extends FragmentActivity {
	//private UserSettingsFragment userSettingsFragment;

    public GraphUser user;
    public ProfilePictureView profilePictureView;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_fragment_activity);
        LoginButton button = (LoginButton) findViewById(R.id.button1);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        
        //TextView events = (TextView) findViewById(R.id.textView1);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	LogIn();
            }
        });
  

    }
	public void LogIn()
	{     
		Session session = Session.getActiveSession();
		boolean enableButtons = (session != null && session.isOpened());
        Log.d ("DOES IT", "REACH HERE");
        final TextView text = (TextView) findViewById(R.id.greeting);
        //final TextView events = (TextView) findViewById (R.id.textView1);

        profilePictureView.setProfileId(null);
        text.setText(null);
        //events.setText(null);
        

        //text.setText("You are now logged in.");
        Bundle b = new Bundle();
        b.putString("access_token", "CAACEdEose0cBADCRO9tXvhgTn5ypLvhAgcl6nVE6HGpxX6UNSDZC3hlpnPOUE6FBAwrpX72XSaWdl0hjup0b9Q7LEDksvypJDZCEGG3ZBZCPLBppf8200uZA1OEmZCI5dMWEVApZBv0xDuCToXWRLiKmSCZAwXRGhoceKApJCzgeIg4oTDU7k5hITQfZClEZAVD2z1cxl6R71OWwZDZD");
        Request r =
        		// make the API call 
        		new Request(
        			null,
        		    "/me/events",
        		    b,
        		    HttpMethod.GET,
        		    new Request.Callback() {
        		        public void onCompleted(Response response) {
        		            // handle the result 
        		            //profilePictureView.setProfileId(user.getId());

        		        	profilePictureView.setProfileId("510750067");
        		            //text.setText("Hello" + user.getFirstName());
        		            text.setText ("Hi Tuan");
        		            //Log.d("print", "" + response);
        		            
        		            GraphObject go = response.getGraphObject(); 
        		            //Log.d ("I'm", "" + go.getInnerJSONObject());
        		            
        		            JSONObject jso = go.getInnerJSONObject();
        		            
        		            
        		            try {
								
        		            	// For DEMO
        		            	final JSONArray arr = jso.getJSONArray("data");
        		            	final JSONObject arrObj = arr.getJSONObject(3);
    		            		String timer = arrObj.getString("start_time");
    		            		String subhr = timer.substring(11, 13);
    		            		String subminu = timer.substring(14, 16);
    		            		final String date = subhr + ":" +subminu + " - " + arrObj.getString("location");
								notifyUser(arrObj.getString("name"), date);

    		                    
    		            		Calendar c = Calendar.getInstance(); 
    		            		int minNow = c.get(Calendar.MINUTE);
    		            		final int hrNow = c.get(Calendar.HOUR) + 12;
    		                    //Log.d ("HOUR ", hrNow + "MIN " + minNow);
    		            		
    		            		
    		            		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
    		            				LoginUsingLoginFragmentActivity.this);
    		             
    		            			// set title
    		            			alertDialogBuilder.setTitle("Check events?");
    		             
    		            			// set dialog message
    		            			alertDialogBuilder
    		            				.setMessage("")
    		            				.setCancelable(false)
    		            				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    		            					public void onClick(DialogInterface dialog,int id) {
    		            		            	for (int i = 0; i < arr.length(); i++)
    		            		            	{
    		            		            		JSONObject arrObj1;
													try {
														arrObj1 = arr.getJSONObject(i);

	    		            		            		String time = arrObj1.getString("start_time");
	    		            		            		int subhour = Integer.parseInt(time.substring(11, 13));
	    		            		            		String submin = time.substring(14, 16);
	    		            		            		String info = subhour + ":" +submin + "\n" + arrObj1.getString("location")
	    		            		            				      + "\n" + arrObj1.getString("name");
	    		            		            		Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT).show();
	    		            		            		//Toast.makeText(getApplicationContext(), "" + hrNow + ":" + minNow, Toast.LENGTH_SHORT).show();
	    		            		            		if (subhour == 1 + hrNow)
	    		            		            			notifyUser(arrObj1.getString("name"), info);
	    		            		            		//Toast.makeText(getApplicationContext(), arrObj.getString("name"), Toast.LENGTH_SHORT).show();
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}    		            		         
    		            		            	}
    		            						//LoginUsingLoginFragmentActivity.this.finish();
    		            					}
    		            				  })
    		            				.setNegativeButton("No",new DialogInterface.OnClickListener() {
    		            					public void onClick(DialogInterface dialog,int id) {
    		            						// if this button is clicked, just close
    		            						// the dialog box and do nothing
    		            						dialog.cancel();
    		            					}
    		            				});
    		             
    		            				// create alert dialog
    		            				AlertDialog alertDialog = alertDialogBuilder.create();
    		             
    		            				// show it
    		            				alertDialog.show();
    		       
        		            		}
        		            	

							
    		            				catch (JSONException e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
            		            Log.d ("I'm", "here error");
							} 
        		            

        		        }


        		    }
        		);
        r.executeAsync();
        
        
        
	}
	
	
	
	private void notifyUser(String s, String date) {
		
		int notificationId = 001;
		Intent viewIntent = new Intent(LoginUsingLoginFragmentActivity.this, myService.class);
		//viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
		PendingIntent viewPendingIntent =
		        PendingIntent.getActivity(this, 0, viewIntent, 0);
		
		BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
		bigStyle.bigText(s);
		
		// TODO Auto-generated method stub
		//Creation of the mBuilder
		NotificationCompat.Builder mBuilder =
		new NotificationCompat.Builder(LoginUsingLoginFragmentActivity.this)
		.setSmallIcon(R.drawable.icon)
		.setContentTitle(date)
		.setContentText(s)
		.setStyle(bigStyle)
		.setContentIntent(viewPendingIntent);

		mBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
		mBuilder.setLights(Color.RED, 3000, 3000);

		NotificationManagerCompat notificationManager =
		        NotificationManagerCompat.from(this);

		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = 
		(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		//mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
		notificationManager.notify(notificationId, mBuilder.build());
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d ("THIS WEIRD THING", "IS CALLED");
		Toast.makeText(getApplicationContext(), "WEIRD", Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), "WEIRD", Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), "WEIRD", Toast.LENGTH_SHORT).show();
		Toast.makeText(getApplicationContext(), "WEIRD", Toast.LENGTH_SHORT).show();

		//userSettingsFragment.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
		//Session.getActiveSession().onActivityResult(this, requestCode,
                //resultCode, data);
	}
	

}

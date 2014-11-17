package com.example.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


public class PhotoActivity extends Activity {

	public static final String CLIENT_ID="526fced0e1c14cf2a32c63d738a37783";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        fetchPopularPhotos();
        
        //Custom Fonts
        // Get access to our TextView
        //TextView txt = (TextView) findViewById(R.id.tvCaption);
        // Create the TypeFace from the TTF asset
        //Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Chantelli_Antiqua.ttf");
        // Assign the typeface to the view
        //txt.setTypeface(font);
    }


    private void fetchPopularPhotos() {
    	photos = new ArrayList<InstagramPhoto>();

    	aPhotos = new InstagramPhotosAdapter(this, photos);
    	
	    ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
	    lvPhotos.setAdapter(aPhotos);
	    // TODO Auto-generated method stub
    	
    	
    	// Setup the popular client URL
    	String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    	
    	// Create the network client
    	AsyncHttpClient client = new AsyncHttpClient();
    	
    	// Trigger the network request
    	client.get(popularUrl, new JsonHttpResponseHandler() {
    		
    		@Override
    		public void onSuccess(int statusCode, org.apache.http.Header[] headers, org.json.JSONObject response) {

    			super.onSuccess(statusCode, headers, response);
    			//Log.i("INFO",response.toString());
    			JSONArray photosJSON = null;
    			
    		    try {
    		    	photos.clear();
					photosJSON = response.getJSONArray("data");
					for (int i=0; i < photosJSON.length(); i++) {
						JSONObject photoJSON = photosJSON.getJSONObject(i);
						InstagramPhoto photo = new InstagramPhoto();
						if (photoJSON.getJSONObject("user") != null) {
						  photo.username = photoJSON.getJSONObject("user").getString("username");
						  photo.profilePicUrl = photoJSON.getJSONObject("user").getString("profile_picture");
						}
				
						if (photoJSON.getJSONObject("caption") != null) {
						  photo.caption = photoJSON.getJSONObject("caption").getString("text");
						}
						
						if (photoJSON.getJSONObject("images") != null) {
						  photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						}
						
						if (photoJSON.getJSONObject("images") != null) {
						  photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						}
						
						if ( photoJSON.getJSONObject("likes") != null) {
						  photo.likes_count = photoJSON.getJSONObject("likes").getInt("count");
						}
						//Log.i("INFO",photo.toString());
						photos.add(photo);
					}
					aPhotos.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}
    		
    	
    		
    		@Override
    		public void onFailure(int statusCode, Header[] headers,
    				Throwable throwable, JSONObject errorResponse) {
    			// TODO Auto-generated method stub
    			super.onFailure(statusCode, headers, throwable, errorResponse);
    		}
    	});
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

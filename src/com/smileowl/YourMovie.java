package com.smileowl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class YourMovie extends ListActivity {
	String id, name, year, genre, known, tag1, tag2, tag3, poster, description, vote, oster ;
	 String ame, ear, enre, nown, ag1, escription, ote, upvote, downvote;

	jParser parser = new jParser();

	ArrayList<HashMap<String, String>> movies;
	
	private static final String urlGetName = "http://smileowl.com/Test21/PDO/getmoviebyname.php";
//	private static final String TAG_SUCCESS = "success";
	JSONArray jArray = null;
	private static final String TAG_PID = "pid";
	private static final String TAG_POSTER = "poster";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_your_movie);
		
		Intent intentip = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getApplicationContext(), YourMovie.class);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
            finish();
	    }
		
		Intent i = getIntent();
		name = i.getStringExtra("name");
		
		
		movies = new ArrayList<HashMap<String, String>>();
		
		new SearchDaMovies().execute();
		
		
	}
	
	class SearchDaMovies extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... args) {
			
			
			try {
		    
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();			
			params.add(new BasicNameValuePair("name", name));
			
			
			JSONObject json = parser.makeHttpRequest(urlGetName, params);
				 jArray = json.getJSONArray("smileowlTable");
				
				 for (int i =0; i<jArray.length();i++){
						
						JSONObject c = jArray.getJSONObject(i);	
						String id = c.getString("pid");
						ame = c.getString("name");
						ear = c.getString("year");
						enre = c.getString("genre");
						nown = c.getString("known");
						ag1 = c.getString("tag1");
					    ote = c.getString("vote");
					    upvote = c.getString("upvote");
						downvote = c.getString("downvote");
						if (ote.length()>14){

						ote = ote.substring(0, ote.length() - 14);
						}

						oster = c.getString("poster");
						escription = c.getString("description");
						
						
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("pid", id);
						map.put("name", ame);
						map.put("year", ear);
						map.put("genre", enre);
						map.put("known",nown);
						map.put("tag1", ag1);
						
						map.put("vote", ote);
						map.put("upvote", upvote);
						map.put("downvote", downvote);
						map.put("poster", oster);
						map.put("description", escription);
						movies.add(map);
						
						
						
		                
					}
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			
			
			return null;
			
		}
        protected void onPostExecute(String result) {
			
			runOnUiThread(new Runnable() {
                public void run() {

                   ListAdapter adapter = new SimpleAdapter(YourMovie.this, movies,
		               R.layout.your_item, new String[] {"pid","poster", "name", "year", "genre", "description", "vote", "tag1"}, 
		               new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8});
		           setListAdapter(adapter);
		           
		           ListView lv = getListView();
		   		lv.setOnItemClickListener(new OnItemClickListener(){

		   			@Override
		   			public void onItemClick(AdapterView<?> parent, View view, int position,
		   					long id) {
		   				String pid =movies.get(position).get("pid");
		   				String poster = movies.get(position).get("poster");
                        String name = movies.get(position).get("name");
                        String year = movies.get(position).get("year");
                        String vote = movies.get(position).get("vote");
                        String genre = movies.get(position).get("genre");
                        String tag1 = movies.get(position).get("tag1");
                        String description = movies.get(position).get("description");
         				String fordetail = "Score: " + vote + "\n" + tag1;	
         				String upvote = movies.get(position).get("upvote");
         				String downvote = movies.get(position).get("downvote");
         				String known = movies.get(position).get("known");
		   				Intent i2 = new Intent(getApplicationContext(), DetailFragment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		   				i2.putExtra(TAG_PID, pid);
		   				i2.putExtra(TAG_POSTER, poster);
		   				i2.putExtra("name", name);
         				i2.putExtra("year", year);
         				i2.putExtra("genre", genre);
         				i2.putExtra("vote", vote);
         				i2.putExtra("known", known);
         				i2.putExtra("tag1", fordetail);
         				i2.putExtra("description", description);
         				i2.putExtra("upvote", upvote);
         				i2.putExtra("downvote", downvote);
                   		
		   				
		   				
		   				startActivityForResult(i2,100);
		   				finish();
		   				
		   			}
		   			
		   		});
		           
                }});
			
	     }
	}    
	
	@Override
	  public void onStart() {
	    super.onStart();
	    // The rest of your onStart() code.
	    EasyTracker.getInstance(this).activityStart(this);  // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	     // The rest of your onStop() code.
	    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	  }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_movie, menu);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.searchthis).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
		return true;
	}
	 @Override
	   public boolean onOptionsItemSelected(MenuItem item)
	   {
	           switch (item.getItemId())
	           {
	           case R.id.item1:
	                   Intent intent=new Intent(YourMovie.this, FindMovie.class);
	                   startActivity(intent);
	                   finish();
	           return true;
	           
	           case R.id.item2:
	                   Intent intent1=new Intent(YourMovie.this, AddMovie.class);
	                   startActivity(intent1);
	                   finish();
	               return true;
	           case R.id.action_settings:
		        	
		  			 Intent intent3 = new Intent(Intent.ACTION_SEND);
		               intent3.setType("text/plain");
		               intent3.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.smileowl");  
		               startActivity(Intent.createChooser(intent3,"Share with:" ));
		  	            return true;   
	               
	          
	                   
	           default:
	                   return super.onOptionsItemSelected(item);
	               }
	       }

}

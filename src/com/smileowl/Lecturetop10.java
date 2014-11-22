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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class Lecturetop10 extends Activity {
	
	String pid, name, vote, upvote, downvote, category, subcategory, description, link, videotext, level, length;
	private ProgressDialog pDialog;
	double foo, x, z, y, up, down;
	private static String upvoteurl = "http://smileowl.com/Test21/PDO/upvotelecture.php";
	private static String downvoteurl = "http://smileowl.com/Test21/PDO/downvotelecture.php";
	
	jParser2 parser2 = new jParser2();
	jParser parser = new jParser();
	ArrayList<HashMap<String, String>> lectures;
	private static final String urlGetPid = "http://smileowl.com/Test21/gettoplectures.php";
	private static final String TAG_SUCCESS = "success";
	JSONArray jArray = null;
	private static final String TAG_PID = "pid";
	int success;
	private CustomAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecture_show);
		
		
		
	
		
		lectures = new ArrayList<HashMap<String, String>>();
		
		new SearchDaMovies().execute();
		Intent intentip = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getApplicationContext(), YourLecture.class);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
            finish();
	    }
		
	//	Toast.makeText(this,"girly men " + category + subcategory + videotext+level +length,Toast.LENGTH_SHORT).show();
		
	}
	
	public class SearchDaMovies extends AsyncTask<String, String, String>{
	
		     
			@Override
			protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Lecturetop10.this);
	            pDialog.setMessage("Loading. Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            
	        }
			
			
			protected String doInBackground(String... args) {
				
				
				
				try {
			    
				// send the query to JSON smileowl
				List<NameValuePair> params = new ArrayList<NameValuePair>();			
				
				
				//receive the JSON data from smileowl.com
				JSONObject json = parser.makeHttpRequest(urlGetPid, params);
				Log.d("the lectures are ", json.toString());
				success = json.getInt(TAG_SUCCESS);
				if (success == 1){
					 jArray = json.getJSONArray("smileowlLectures");
					
					for (int i =0; i<jArray.length();i++){
						
						JSONObject c = jArray.getJSONObject(i);	
						pid = c.getString("pid");
						name = c.getString("name");
						category = c.getString("category");
						subcategory = c.getString("subcategory");
						description = c.getString("description");
						link = c.getString("link");
					    videotext = c.getString("videotext");
					    level = c.getString("level");
					    upvote = c.getString("upvote");
						downvote = c.getString("downvote");
						vote = c.getString("vote");
						length = c.getString("length");
					

					
						
						//
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("pid", pid);
						map.put("name", name);
						map.put("category", category);
						map.put("subcategory", subcategory);
						map.put("description", description);
						map.put("link", link);
						map.put("videotext", videotext);
						map.put("level", level);
						map.put("vote", vote);
						map.put("upvote", upvote);
						map.put("downvote", downvote);
						map.put("length", length);
						
						lectures.add(map);
					}
					 
				
	            }
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
				
				return null;
				
			}
			
			protected void onPostExecute(String result) {
	        	pDialog.dismiss();
	        	
	        	if (success != 0){
	        		runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	 mAdapter = new CustomAdapter(Lecturetop10.this, lectures);
	                	 final ListView lstView1 = (ListView)findViewById(R.id.listView1);
	                	 lstView1.setAdapter(mAdapter);
	                	 
	                	
	                }});
	        	}else{
	        		Toast.makeText(Lecturetop10.this,"No movies found" , Toast.LENGTH_LONG).show();
	            	
				}
		     }
	        
		}
	public class CustomAdapter extends BaseAdapter {
		 
	    
	    private Context context;
	    private ArrayList<HashMap<String, String>> lectures = new ArrayList<HashMap<String, String>>();
	    
	    public CustomAdapter(Context c, ArrayList<HashMap<String, String>> list){
			context = c;
			lectures = list;
			
	    }
	 
	 
	    @Override
	    public int getCount() {
	        return lectures.size();    // total number of elements in the list
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return lectures.get(position);    // single item in the list
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;                   // index number
	    }
	 
	    @Override
	    public View getView(final int position, View view, final ViewGroup parent) {
	 
	        if (view == null) {
	            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
	            view = inflater.inflate(R.layout.listviewcustomadapter, parent, false);
	        }
	 
	        
	 
	        TextView textview1 = (TextView) view.findViewById(R.id.textview1);
	        textview1.setText(lectures.get(position).get("name"));
	 
	        
	        TextView textview2 = (TextView) view.findViewById(R.id.textview2);
	        textview2.setText(lectures.get(position).get("description"));
	        
	        TextView textview3 = (TextView) view.findViewById(R.id.textview3);
	        textview3.setText(lectures.get(position).get("level"));
	        
	        TextView textview4 = (TextView) view.findViewById(R.id.textview4);
	        textview4.setText(lectures.get(position).get("length"));
	        
	        TextView textview5 = (TextView) view.findViewById(R.id.textview5);
	        textview5.setText(lectures.get(position).get("category")+ ", " +lectures.get(position).get("subcategory"));
	        String ote = lectures.get(position).get("vote");
	        if (ote.length()>14){

				ote = ote.substring(0, ote.length() - 14);
				}
	        
	        TextView textview6 = (TextView) view.findViewById(R.id.textview6);
	        textview6.setText(ote);
	        TextView textview7 = (TextView) view.findViewById(R.id.textview7);
	        String ink = lectures.get(position).get("link");
	        if (ink.length()>40){
	        	int size= ink.length() -38;

				ink = ink.substring(0, ink.length() - size);
				ink = ink+"...";
				}
	        textview7.setText(ink);
	        
	        
	        //button for link
	        ImageButton button1 = (ImageButton) view.findViewById(R.id.button1);
	        button1.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            //	String test =lectures.get(position).get("link");
	 
	               // Toast.makeText(parent.getContext(), "the link is: " + lectures.get(position).get("link"), Toast.LENGTH_SHORT).show();
	            	String url = "http://"+lectures.get(position).get("link");
	            	Intent i = new Intent(Intent.ACTION_VIEW);
	            	i.setData(Uri.parse(url));
	            	startActivity(i);
	            }
	        });
	        
	        //the share button 
	        ImageButton button2 = (ImageButton) view.findViewById(R.id.button2);
	        button2.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            
	            	Intent intent = new Intent(Intent.ACTION_SEND);
	                intent.setType("text/plain");
	                intent.putExtra(Intent.EXTRA_TEXT, lectures.get(position).get("name")+ " "+lectures.get(position).get("link") + " found on " + "https://play.google.com/store/apps/details?id=com.smileowl");  
	                startActivity(Intent.createChooser(intent,"Share with:" ));
	            	
	            }
	        });
	        
	        //upvote
	        ImageButton button3 = (ImageButton) view.findViewById(R.id.button3);
	        button3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					up = Double.parseDouble(lectures.get(position).get("upvote"));
					down = Double.parseDouble(lectures.get(position).get("downvote"));
					up = up + 1;
					x = up-down;
					y = up + down;
					z = x/y;
					
					foo = z * 10;
					if (foo<1){
						foo=1;
					}
					new Upvote().execute(lectures.get(position).get("pid"));
					Toast t = Toast.makeText(Lecturetop10.this, "Upvoted ", Toast.LENGTH_SHORT);
						t.show();
					
				}
			});
	        //downvote
	        ImageButton button4 = (ImageButton)view.findViewById(R.id.button4);
	        button4.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					up = Double.parseDouble(lectures.get(position).get("upvote"));
					down = Double.parseDouble(lectures.get(position).get("downvote"));
					down = down + 1;
					x = up-down;
					y = up + down;
					z = x/y;
					foo = z * 10;
					if (foo<1){
						foo=1;
					}
					new Downvote().execute(lectures.get(position).get("pid"));
					Toast t2 = Toast.makeText(Lecturetop10.this, "Downvoted ", Toast.LENGTH_SHORT);
					t2.show();
				}
			});
	 
	        /**/
	 
	 //opens the lecture detail page and sends the information there
	        view.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	
	            	Intent intent = new Intent();
    	            intent.setClass(Lecturetop10.this, LectureDetail.class);
    	            intent.putExtra("pid", lectures.get(position).get("pid"));
    	            intent.putExtra("name", lectures.get(position).get("name"));
    	            intent.putExtra("category", lectures.get(position).get("category"));
    				intent.putExtra("subcategory", lectures.get(position).get("subcategory"));
    				intent.putExtra("videotext", lectures.get(position).get("videotext"));
    				intent.putExtra("vote", lectures.get(position).get("vote"));
    				intent.putExtra("upvote", lectures.get(position).get("upvote"));
    				intent.putExtra("downvote", lectures.get(position).get("downvote"));
    				intent.putExtra("link", lectures.get(position).get("link"));
    				intent.putExtra("level", lectures.get(position).get("level"));
    				intent.putExtra("length", lectures.get(position).get("length"));
    				intent.putExtra("description", lectures.get(position).get("description"));
    	            startActivity(intent);
	                
	 
	               // Toast.makeText(parent.getContext(), "cock: " , Toast.LENGTH_SHORT).show();
	            }
	        });
	        
	        /*
	 */
	        return view;
	    }
	}
	
	class Upvote extends AsyncTask<String, String, String>{

 		@Override
 		protected String doInBackground(String... pids) {
 			
 			

 			String score = Double.toString(foo); 	
 			String scoreup = Double.toString(up); 
 			//String scoredown = Integer.toString(down); 
 			List<NameValuePair> params = new ArrayList<NameValuePair> ();
 			params.add(new BasicNameValuePair("vote" , score ));
 			params.add(new BasicNameValuePair("upvote" , scoreup ));
 			//params.add(new BasicNameValuePair("downvote" , scoredown ));
 			
 			int count = pids.length;
 			for (int i = 0; i < count; i++) {
 			params.add(new BasicNameValuePair("pid" , pids[i] ));
 			}
 			
 			@SuppressWarnings("unused")
			JSONObject json = parser2.makeHttpRequest(upvoteurl, params);
 			return null;
 		}
 		 
 	 }
 	class Downvote extends AsyncTask<String, String, String>{

 		@Override
 		protected String doInBackground(String... pids) {

 			String score = Double.toString(foo); 
 			//String scoreup = Integer.toString(up); 
 			String scoredown = Double.toString(down); 
 			List<NameValuePair> params = new ArrayList<NameValuePair> ();
 			params.add(new BasicNameValuePair("vote" , score ));
 		//	params.add(new BasicNameValuePair("upvote" , scoreup ));
 			params.add(new BasicNameValuePair("downvote" , scoredown ));
 			
 			int count = pids.length;
 			for (int i = 0; i < count; i++) {
 			params.add(new BasicNameValuePair("pid" , pids[i] ));
 			}
 			
 			@SuppressWarnings("unused")
			JSONObject json = parser2.makeHttpRequest(downvoteurl, params);
 			return null;
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
		        	
		            Intent intent=new Intent(this, Lecturetop10.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(intent);
		            return true;
	          
	           
	           case R.id.item2:
	                   Intent intent1=new Intent(this, AddLecture.class);
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
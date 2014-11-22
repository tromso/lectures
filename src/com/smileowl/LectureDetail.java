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
import com.smileowl.DetailListFragment.AddDaComment;
import com.smileowl.DetailListFragment.MovieComments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LectureDetail extends ListActivity {
	String pid, name, vote, upvote, downvote, category, subcategory, description, link, videotext, level, length;
	double foo, x, z, y, up, down;
	private ProgressDialog pDialog;
	
	jParser2 parser2 = new jParser2();
	jParser parser = new jParser();
	private static String upvoteurl = "http://smileowl.com/Test21/PDO/upvotelecture.php";
	private static String downvoteurl = "http://smileowl.com/Test21/PDO/downvotelecture.php";
	private static final String comments = "http://smileowl.com/Test21/lecturecomments.php";
	private static final String commentsshow = "http://smileowl.com/Test21/lecturecommentsshow.php";
	private WebView webView;
	WebView webview;
	
	//comments variables
	int testint, count;
	int success;
	private static final String TAG_TABLE = "smileowlTable";
	private static final String TAG_COMMENTS = "smileowlLectureComments";
	private static final String TAG_SUCCESS = "success";
	
	ArrayList<HashMap<String, String>> lectures;
	JSONArray jArray = null;
	
	private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
  }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lecture_detail);
		
		Intent intentip = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getApplicationContext(), YourLecture.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
	    }
		
		Intent i = getIntent();
		name = i.getStringExtra("name");
		pid = i.getStringExtra("pid");
		category = i.getStringExtra("category");
		subcategory = i.getStringExtra("subcategory");
		videotext = i.getStringExtra("videotext");
		link = i.getStringExtra("link");
		vote = i.getStringExtra("vote");
		upvote = i.getStringExtra("upvote");
		downvote = i.getStringExtra("downvote");
		level = i.getStringExtra("level");
		length = i.getStringExtra("length");
		description = i.getStringExtra("description");
		//Toast.makeText(this, "the stuff is : " + name + pid + category+ subcategory+ videotext+ level, Toast.LENGTH_SHORT).show();
		
		
		TextView textview1 = (TextView) findViewById(R.id.textview1);
        textview1.setText(name);
        TextView textview2 = (TextView) findViewById(R.id.textview2);
        textview2.setText(category + " " + subcategory);
        TextView textview3 = (TextView) findViewById(R.id.textview3);
        textview3.setText(description);
        TextView textview4 = (TextView) findViewById(R.id.textview4);
        textview4.setText(level);
		webview=(WebView)findViewById(R.id.webView1);
        webview.setWebViewClient(new MyWebViewClient());
        openURL();
		
        lectures = new ArrayList<HashMap<String, String>>();
        new MovieComments().execute();
		//ListView lv = getListView();
		Button button3 =(Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			private boolean handledClick = false;
			@Override
			public void onClick(View v) {
				if (!handledClick){
			        handledClick = true;
				new AddDaComment().execute();	
				}
				
			}
		});
		
		ImageButton button1 = (ImageButton)findViewById(R.id.imbutton1);
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				up = Double.parseDouble(upvote);
				down = Double.parseDouble(downvote);
				up = up + 1;
				x = up-down;
				y = up + down;
				z = x/y;
				
				foo = z * 10;
				if (foo<1){
					foo=1;
				}
				new Upvote().execute();
				Toast t = Toast.makeText(LectureDetail.this, "Upvoted ", Toast.LENGTH_SHORT);
					t.show();
				
			}
		});
        ImageButton button2 = (ImageButton)findViewById(R.id.imbutton2);
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				up = Double.parseDouble(upvote);
				down = Double.parseDouble(downvote);
				down = down + 1;
				x = up-down;
				y = up + down;
				z = x/y;
				foo = z * 10;
				if (foo<1){
					foo=1;
				}
				new Downvote().execute();
				Toast t2 = Toast.makeText(LectureDetail.this, "Downvoted ", Toast.LENGTH_SHORT);
				t2.show();
			}
		});
      //the share button 
        ImageButton buttonshare = (ImageButton)findViewById(R.id.imbutton3);
        buttonshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
            	Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, name+ " "+link + " found on " + "https://play.google.com/store/apps/details?id=com.smileowl");  
                startActivity(Intent.createChooser(intent,"Share with:" ));
            	
            }
        });
        ImageButton buttonchrome = (ImageButton)findViewById(R.id.imbutton4);
        buttonchrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
            	String url = "http://"+link;
            	Intent i = new Intent(Intent.ACTION_VIEW);
            	i.setData(Uri.parse(url));
            	startActivity(i);
            	
            }
        });
		
	}
	
	
	
	class Upvote extends AsyncTask<String, String, String>{

 		@Override
 		protected String doInBackground(String... arg0) {

 			String score = Double.toString(foo); 	
 			String scoreup = Double.toString(up); 
 			//String scoredown = Integer.toString(down); 
 			List<NameValuePair> params = new ArrayList<NameValuePair> ();
 			params.add(new BasicNameValuePair("vote" , score ));
 			params.add(new BasicNameValuePair("upvote" , scoreup ));
 			//params.add(new BasicNameValuePair("downvote" , scoredown ));
 			params.add(new BasicNameValuePair("pid" , pid ));
 			
 			
 			@SuppressWarnings("unused")
			JSONObject json = parser2.makeHttpRequest(upvoteurl, params);
 			return null;
 		}
 		 
 	 }
 	class Downvote extends AsyncTask<String, String, String>{

 		@Override
 		protected String doInBackground(String... arg0) {

 			String score = Double.toString(foo); 
 			//String scoreup = Integer.toString(up); 
 			String scoredown = Double.toString(down); 
 			List<NameValuePair> params = new ArrayList<NameValuePair> ();
 			params.add(new BasicNameValuePair("vote" , score ));
 		//	params.add(new BasicNameValuePair("upvote" , scoreup ));
 			params.add(new BasicNameValuePair("downvote" , scoredown ));
 			params.add(new BasicNameValuePair("pid" , pid ));
 			
 			
 			@SuppressWarnings("unused")
			JSONObject json = parser2.makeHttpRequest(downvoteurl, params);
 			return null;
 		}
 		 
 	 }
 	
 	private void openURL() {
 		webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://" +link);
    }
 	
 	//gets the comments back from the database
 			class MovieComments extends AsyncTask<String, String, String>{
 				
 				@Override
 				protected void onPreExecute() {
 		            super.onPreExecute();
 		            pDialog = new ProgressDialog(LectureDetail.this);
 		            pDialog.setMessage("Loading. Please wait...");
 		            pDialog.setIndeterminate(false);
 		            pDialog.setCancelable(false);
 		            pDialog.show();
 		        }

 				@Override
 				protected String doInBackground(String... args) {
 					
 					
 					List<NameValuePair> params = new ArrayList<NameValuePair> ();
 					params.add(new BasicNameValuePair("pid", pid));
 					
 					JSONObject json = parser.makeHttpRequest(commentsshow, params);
 					
 					//Log.d("Single Product Details", json.toString());
 					
 					try {
 						success = json.getInt(TAG_SUCCESS);
 					
 					if (success == 1){
 					
 						try {
 							jArray = json.getJSONArray(TAG_COMMENTS);
 						} catch (JSONException e) {
 							
 							e.printStackTrace();
 						}
 						//testint is the size of the longest movie comments array.
 						testint=jArray.length();
 						
 		                for (int i =0; i<jArray.length();i++){
 							
 							JSONObject c;
 							try {
 								c = jArray.getJSONObject(i);
 							
 							String zid = c.getString("zzz");
 							if (zid !=null && !zid.isEmpty() && zid !="null")
 							{
 							//	in the loop count how many comments you got from the db 
 							count = count +1;	
 							}
 							HashMap<String, String> map = new HashMap<String, String>();
 							
 							map.put("zzz", zid);
 							
 							lectures.add(map);
 							} catch (JSONException e) {
 								// TODO Auto-generated catch block
 								e.printStackTrace();
 							}	
 							
 			                
 						}
 		                }
 					} catch (JSONException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
 					
 					return null;
 				}
 				
 				protected void onPostExecute(String boom){
 					
 					pDialog.dismiss();
 					
 					
 					LectureDetail.this.runOnUiThread(new Runnable() {
 		                public void run() {
 		                	
 		                	//Toast t2 = Toast.makeText(getActivity(), "length " +testint  + " count " + count, 5000);
 	     					//t2.show();
 		                	
 		                	if (success == 1){
 		                	//getListView();
 		                	ListAdapter adapter = new SimpleAdapter(LectureDetail.this, lectures,
 		     		               R.layout.list_item, new String[] {"zzz"}, new int[]{R.id.textView1});
 		     		           setListAdapter(adapter);
 		     		          
 		     					
 		     					ListView lv = getListView();
 		     					
 		     					lv.setOnTouchListener(new ListView.OnTouchListener() {
 		     				        @Override
 		     				        public boolean onTouch(View v, MotionEvent event) {
 		     				            int action = event.getAction();
 		     				            switch (action) {
 		     				            case MotionEvent.ACTION_DOWN:
 		     				                // Disallow ScrollView to intercept touch events.
 		     				                v.getParent().requestDisallowInterceptTouchEvent(true);
 		     				                break;

 		     				            case MotionEvent.ACTION_UP:
 		     				                // Allow ScrollView to intercept touch events.
 		     				                v.getParent().requestDisallowInterceptTouchEvent(false);
 		     				                break;
 		     				            }

 		     				            // Handle ListView touch events.
 		     				            v.onTouchEvent(event);
 		     				            return true;
 		     				        }
 		     				    });
 		            		
 		     		          
 		                }
 		                }
 					});
 					
 				
 				}/**/
 				 
 			 }
 			
 			//posts the comments to the database
 		     class AddDaComment extends AsyncTask<String, String, String>{
 				
 		    	 EditText edittext1 = (EditText)findViewById(R.id.editText1);
 			        String comment = edittext1.getText().toString();
 			   @Override
 		       protected void onPreExecute() {
 			            super.onPreExecute();
 			            pDialog = new ProgressDialog(LectureDetail.this);
 			            pDialog.setMessage("Loading. Please wait...");
 			            pDialog.setIndeterminate(false);
 			            pDialog.setCancelable(false);
 			            pDialog.show();
 			   }
 				 

 				@Override
 				protected String doInBackground(String... args) {
 					
 					
 					if (comment.length()>1){
 						
 					
 					
 					
 					
 					List<NameValuePair> params = new ArrayList<NameValuePair> ();
 					//pid is the movie, if the movie has comments write in that pid table, if not, create a comments table for this movie
 					params.add(new BasicNameValuePair("pid", pid));
 					//if the movie has no comments, make the count say it should be posted at position 1
 					
 					//if this movie isn't the movie with the most comments then post it at the next empty space
 					if (count<testint){
 						count  = count +1;
 						
 						String tid = Integer.toString(count);
 					params.add(new BasicNameValuePair("id", tid));
 					}
 					if (count<1){
 						count=1;
 						String tid = Integer.toString(count);
 						params.add(new BasicNameValuePair("id", tid));
 					}
 					params.add(new BasicNameValuePair("comment", comment));
 					
 					@SuppressWarnings("unused")
 					JSONObject json = parser2.makeHttpRequest(comments, params);
 					}
 					return null;
 					//
 				}
 			
 		          protected void onPostExecute(String zoom){
 		        	  pDialog.dismiss();
 					
 		        	  LectureDetail.this.runOnUiThread(new Runnable() {
 		                public void run() {
 		                	
 		                	//Toast t2 = Toast.makeText(getActivity(), "This will be posted at position: " +count, 5000);
 	     				//	t2.show();
 		                	if (comment.length()>1){
 		                	//Toast z = Toast.makeText(getActivity(), "Comment posted "+ comment.length(), 5000);
 		        			//z.show();
 		                	}else{
 		                		Toast.makeText(LectureDetail.this, "The comment has to be bigger than just one character", Toast.LENGTH_LONG).show();
 		            			
 		                	}
 		                	
 		        			//clear top clears the stack, doesn't make you go back over and over to 1000 back screen activities
 		        			Intent i3 = new Intent(LectureDetail.this, LectureDetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
 		      				//takes you back to the same page but with the comment posted
 		        			i3.putExtra("pid", pid);
 		      				i3.putExtra("link", link);
 		      				i3.putExtra("name", name);
 		      				i3.putExtra("category", category);
 		      				i3.putExtra("subcategory", subcategory);
 		      				i3.putExtra("videotext", videotext);
 	         				i3.putExtra("vote", vote);
 	         				i3.putExtra("length", length);
 	         				i3.putExtra("level", level);
 	         				i3.putExtra("description", description);
 	         				i3.putExtra("upvote", upvote);
 	         				i3.putExtra("downvote", downvote);
 	         				/*
 	         				*/
 		                		startActivityForResult(i3,100);
 		                		finish();
 		            		
 					
 					
 		                }
 					});
 				}/**/
 				
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

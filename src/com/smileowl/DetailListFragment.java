package com.smileowl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.smileowl.ImageCache.ImageCacheParams;
import com.smileowl.ImageGridFragment.Downvote;
import com.smileowl.ImageGridFragment.ImageAdapter;
import com.smileowl.ImageGridFragment.Upvote;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DetailListFragment extends ListFragment implements OnClickListener {
	
	private ProgressDialog pDialog;
	jParser parser = new jParser();
	jParser2 parser2 = new jParser2();
	ArrayList<HashMap<String, String>> movies;
	String name, year, genre, known, tag1, description, vote, upvote, downvote, id, tid, commentscontent, zid ;
	double foo, x, z, y, up, down;
	int testint, count;
	int success;
	
	private static final String IMAGE_CACHE_DIR = "thumbs";

    @SuppressWarnings("unused")
	private int mImageThumbSize;
    @SuppressWarnings("unused")
	private int ImageSize;
    @SuppressWarnings("unused")
	private int mImageThumbSpacing;
    @SuppressWarnings("unused")
	private ImageAdapter mAdapter;
    private ImageFetcher mImageFetcher;
    String pid, poster;
    
    
    private static String upvoteurl = "http://smileowl.com/Test21/PDO/upvote.php";
	private static String downvoteurl = "http://smileowl.com/Test21/PDO/downvote.php";
	private static final String comments = "http://smileowl.com/Test21/comments.php";
	private static final String commentsshow = "http://smileowl.com/Test21/commentsshow.php";
	@SuppressWarnings("unused")
	private static final String TAG_TABLE = "smileowlTable";
	private static final String TAG_COMMENTS = "smileowlComments";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PID = "pid";
	private static final String TAG_POSTER = "poster";
	static int option;
	JSONArray jArray = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);
        ImageSize = getResources().getDimensionPixelSize(R.dimen.image_size);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = 300;
        final int width = displayMetrics.widthPixels;

        // For this sample we'll use half of the longest width to resize our images. As the
        // image scaling ensures the image is larger than this, we should be left with a
        // resolution that is appropriate for both portrait and landscape. For best image quality
        // we shouldn't divide by 2, but this will use more memory and require a larger memory
        // cache.
        final int longest = (height > width ? height : width);

        ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), longest);
       // mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
        
        
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
       
		Bundle extras = getActivity().getIntent().getExtras();
		pid = extras.getString("pid");
		poster = extras.getString("poster");
		name = extras.getString("name");
		year = extras.getString("year");
		genre = extras.getString("genre");
		known = extras.getString("known");
		tag1 = extras.getString("tag1");
		description = extras.getString("description");
		vote = extras.getString("vote");
		upvote = extras.getString("upvote");
		downvote = extras.getString("downvote");
		

		
		View view = inflater.inflate(R.layout.activity_movie_detail, null);
		movies = new ArrayList<HashMap<String, String>>();
		

		
		 ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
	        
			//imageView.getLayoutParams().height = 1000;
			//imageView.getLayoutParams().width = 600;
			//imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			
			mImageFetcher.loadImage(poster + " ", imageView);
			TextView textview1= (TextView)view.findViewById(R.id.textview1);
			textview1.setText(name + " (" + year + ")");
			TextView textview2= (TextView)view.findViewById(R.id.textview2);
			textview2.setText(genre+ "\n" + tag1 + "\n"+ "\n" + description);
			/*
			Button button1 = (Button) view.findViewById(R.id.button1);
	        button1.setOnClickListener(this);
	        Button button2 = (Button) view.findViewById(R.id.button2);
	        button2.setOnClickListener(this);
		*/
	    new MovieComments().execute();
		//ListView lv = getListView();
		Button button3 =(Button)view.findViewById(R.id.button3);
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
		
		//the voting stuff
		//TextView textview10 = (TextView) view.findViewById(R.id.textview10);
		//textview10.setText(vote);
		
		//upvote
        ImageButton buttonup = (ImageButton) view.findViewById(R.id.imbutton1);
        buttonup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
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
				Toast t = Toast.makeText(getActivity(), "Upvoted ", Toast.LENGTH_SHORT);
					t.show();
				
			}
		});
        //downvote
        ImageButton buttondown = (ImageButton)view.findViewById(R.id.imbutton2);
        buttondown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
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
				Toast t2 = Toast.makeText(getActivity(), "Downvoted ", Toast.LENGTH_SHORT);
				t2.show();
			}
		});
		
		
		//googlesearch
        ImageButton button4 =(ImageButton)view.findViewById(R.id.imbutton4);
		button4.setOnClickListener(new OnClickListener() {
			//private boolean handledClick = false;
			@Override
			public void onClick(View v) {
			//	if (!handledClick){
			   //     handledClick = true;
			        //String youtube = ((TextView) v.findViewById(R.id.textView1)).getText().toString();
			        
			        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			        
			        intent.putExtra(SearchManager.QUERY, name + " Movie");
			        getActivity().startActivity(intent);
			       	
				//}
				
			}
		});
		//youtube traler
		ImageButton button5 =(ImageButton)view.findViewById(R.id.imbutton3);
		button5.setOnClickListener(new OnClickListener() {
			//private boolean handledClick = false;
			@Override
			public void onClick(View v) {
				
				
				String videoUrl =  name + " Trailer" ;

				Intent intent = new Intent(Intent.ACTION_SEARCH);
				intent.setPackage("com.google.android.youtube");
				intent.putExtra("query", videoUrl);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			    
				
			}
		});
		//the share button 
        ImageButton buttonshare = (ImageButton) view.findViewById(R.id.imbutton5);
        buttonshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
            	Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, name+ " "+ year + " "+poster + " found on " + "https://play.google.com/store/apps/details?id=com.smileowl");  
                startActivity(Intent.createChooser(intent,"Share with:" ));
            	
            }
        });

        

        return view;
    }
	
	@Override
    public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
       // mAdapter.notifyDataSetChanged();
    }
    /*
*/
    @Override
    public void onPause() {
        super.onPause();
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageFetcher.closeCache();
    }

    
	/*
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        

        
    }
	*/
	@Override
	public void onClick(View view){
		switch(view.getId()){  
		case R.id.button1:
			
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
			Toast t = Toast.makeText(getActivity(), "Upvoted ", Toast.LENGTH_SHORT);
				t.show();
			
			
			
        break;
			//
		case R.id.button2:
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
			Toast t2 = Toast.makeText(getActivity(), "Downvoted ", Toast.LENGTH_SHORT);
			t2.show();
			
			
		}
	}
	
	//gets the comments back from the database
		class MovieComments extends AsyncTask<String, String, String>{
			
			@Override
			protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(getActivity());
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
					//testint is the size of the longest movie comments array
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
						
						movies.add(map);
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
				
				
				getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	//Toast t2 = Toast.makeText(getActivity(), "length " +testint  + " count " + count, 5000);
     					//t2.show();
	                	
	                	if (success == 1){
	                	//getListView();
	                	ListAdapter adapter = new SimpleAdapter(getActivity(), movies,
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
			
	    	 EditText edittext1 = (EditText)getActivity().findViewById(R.id.editText1);
		        String comment = edittext1.getText().toString();
		   @Override
	       protected void onPreExecute() {
		            super.onPreExecute();
		            pDialog = new ProgressDialog(getActivity());
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
				
	        	  getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	//Toast t2 = Toast.makeText(getActivity(), "This will be posted at position: " +count, 5000);
     				//	t2.show();
	                	if (comment.length()>1){
	                	//Toast z = Toast.makeText(getActivity(), "Comment posted "+ comment.length(), 5000);
	        			//z.show();
	                	}else{
	                		Toast.makeText(getActivity(), "The comment has to be bigger than just one character", Toast.LENGTH_LONG).show();
	            			
	                	}
	                	
	        			//clear top clears the stack, doesn't make you go back over and over to 1000 back screen activities
	        			Intent i3 = new Intent(getActivity(), DetailFragment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
	      				//takes you back to the same page but with the comment posted
	        			i3.putExtra(TAG_PID, pid);
	      				i3.putExtra(TAG_POSTER, poster);
	      				i3.putExtra("name", name);
         				i3.putExtra("year", year);
         				i3.putExtra("genre", genre);
         				i3.putExtra("vote", vote);
         				i3.putExtra("known", known);
         				i3.putExtra("tag1", tag1);
         				i3.putExtra("description", description);
         				i3.putExtra("upvote", upvote);
         				i3.putExtra("downvote", downvote);
	                		startActivityForResult(i3,100);
	            		
				
				
	                }
				});
			}/**/
			
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

	

	

}

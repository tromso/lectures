/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.smileowl.LectureShow.Downvote;
import com.smileowl.LectureShow.Upvote;

import android.annotation.TargetApi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * The main fragment that powers the ImageGridActivity screen. Fairly straight forward GridView
 * implementation with the key addition being the ImageWorker class w/ImageCache to load children
 * asynchronously, keeping the UI nice and smooth and caching thumbnails for quick retrieval. The
 * cache is retained over configuration changes like orientation change so the images are populated
 * quickly if, for example, the user rotates the device.
 */
public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    @SuppressWarnings("unused")
	private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private int mImageThumbSize;
    @SuppressWarnings("unused")
	private int mImageThumbSpacing;
    private ImageAdapter mAdapter;
    private ImageFetcher mImageFetcher;
    
    String id, name, year, genre, known, tag1, tag2, tag3, poster, oster, description, vote
    , ame, ear, enre, nown, ag1, escription, ote, upvote, downvote;
    private ProgressDialog pDialog;
	jParser parser = new jParser();
	ArrayList<HashMap<String, String>> movies;
	private static final String urlGetPid = "http://smileowl.com/Test21/getmovie.php";
	private static final String TAG_SUCCESS = "success";
	JSONArray jArray = null;
	private static final String TAG_PID = "pid";
	private static final String TAG_POSTER = "poster";
	int success;
	
	//vote variables
	double foo, x, z, y, up, down;
	private static String upvoteurl = "http://smileowl.com/Test21/PDO/upvote.php";
	private static String downvoteurl = "http://smileowl.com/Test21/PDO/downvote.php";
	jParser2 parser2 = new jParser2();
	

    /**
     * Empty constructor as per the Fragment documentation .
     */
    public ImageGridFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        

        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        

        ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);

        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	movies = new ArrayList<HashMap<String, String>>();
    	

        final View v = inflater.inflate(R.layout.image_grid_fragment, container, false);
       // final ListView mGridView = (ListView) v.findViewById(R.id.listView1);
        
        
        
        Bundle extras = getActivity().getIntent().getExtras();
		name = extras.getString("name");
		year = extras.getString("year");
		known = extras.getString("known");
		genre = extras.getString("genre");
		tag1 = extras.getString("tag1");
		tag2 = extras.getString("tag2");
		tag3 = extras.getString("tag3");
		vote = extras.getString("vote");
		description = extras.getString("description");
		
		
		
		new SearchDaMovies().execute();

        return v;
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

    @TargetApi(16)
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	
    	
       // final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
       // i.putExtra(ImageDetailActivity.EXTRA_IMAGE, (int) id);
        if (Utils.hasJellyBean()) {
            // makeThumbnailScaleUpAnimation() looks kind of ugly here as the loading spinner may
            // show plus the thumbnail image in GridView is cropped. so using
            // makeScaleUpAnimation() instead.
          //  ActivityOptions options =
              //      ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
           // getActivity().startActivity(i, options.toBundle());
        } else {
           // startActivity(i);
        }
        /**/
    }

public class SearchDaMovies extends AsyncTask<String, String, String>{
		
	//String[] arrayS;
	     
		@Override
		protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
		
		protected String doInBackground(String... args) {
			
			
			
			try {
		    
			// send the query to JSON smileowl
			List<NameValuePair> params = new ArrayList<NameValuePair>();			
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("year", year));
			params.add(new BasicNameValuePair("genre", genre));
			params.add(new BasicNameValuePair("known", known));
			params.add(new BasicNameValuePair("tag1", tag1));
			params.add(new BasicNameValuePair("tag2", tag2));
			params.add(new BasicNameValuePair("tag3", tag3));
			params.add(new BasicNameValuePair("vote", vote));
			
			params.add(new BasicNameValuePair("poster", poster));
			params.add(new BasicNameValuePair("description", description));
			
			
			//receive the JSON data from smileowl.com
			JSONObject json = parser.makeHttpRequest(urlGetPid, params);
			//Log.d("the mvoies are ", json.toString());
			success = json.getInt(TAG_SUCCESS);
			if (success == 1){
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
					String escription = c.getString("description");
					
					
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
				 
			
            }
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			
			return null;
			
		}
		
		protected void onPostExecute(String result) {
        	pDialog.dismiss();
        	
        	if (success != 0){
        		getActivity().runOnUiThread(new Runnable() {
                public void run() {
                	
              		    
                	mAdapter = new ImageAdapter(getActivity(), movies);
                      
                	 final ListView lstView1 = (ListView)getActivity().findViewById(R.id.listView1);
                	 lstView1.setAdapter(mAdapter);
                	 
                	 lstView1.setOnScrollListener(new AbsListView.OnScrollListener() {
                         @Override
                         public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                             // Pause fetcher to ensure smoother scrolling when flinging
                             if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                                 mImageFetcher.setPauseWork(true);
                             } else {
                                 mImageFetcher.setPauseWork(false);
                             }
                         }

                         @Override
                         public void onScroll(AbsListView absListView, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                         }
                     });
                	 
                	 /*
                     lstView1.setOnItemClickListener(new OnItemClickListener() {
                   	  
                   	  public void onItemClick(AdapterView<?> parent, View v,
                   	  
                   	  int position, long id) {
                   		 // Toast.makeText(getActivity(),"Text!" + position,Toast.LENGTH_SHORT).show();
                   		
                   		String pid = ((TextView) v.findViewById(R.id.textView1)).getText().toString();
         				String poster = ((TextView) v.findViewById(R.id.textView2)).getText().toString();
         				String ame = ((TextView) v.findViewById(R.id.textView3)).getText().toString();
         				String ag1 = ((TextView) v.findViewById(R.id.textView4)).getText().toString();
         				String upvote = ((TextView) v.findViewById(R.id.textView5)).getText().toString();
         				String downvote = ((TextView) v.findViewById(R.id.textView6)).getText().toString();
         			
                   		 // String pid =Integer.toString(position);

                   		Intent i2 = new Intent(getActivity(), DetailFragment.class);
         				i2.putExtra(TAG_PID, pid);
         				i2.putExtra(TAG_POSTER, poster);
         				i2.putExtra("name", ame);
         				i2.putExtra("year", ear);
         				i2.putExtra("genre", enre);
         				i2.putExtra("vote", ote);
         				i2.putExtra("known", nown);
         				i2.putExtra("tag1", ag1);
         				i2.putExtra("description", escription);
         				i2.putExtra("upvote", upvote);
         				i2.putExtra("downvote", downvote);
                   		startActivityForResult(i2,100);
                   		
                   		//getActivity().finish();
                   		
                   	  
                   	  }
                   	  
                   	  });
                     */
                	
                	
                }});
        	}else{
        		Toast.makeText(getActivity(),"No movies found with that criteria. Try relaxing the conditions." , Toast.LENGTH_LONG).show();
            	
			}
	     }
        
	}



	
//image adaptor class		
		public class ImageAdapter extends BaseAdapter {
			
			private Context context;
			private ArrayList<HashMap<String, String>> movies = new ArrayList<HashMap<String, String>>();
			
			public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list){
				context = c;
				movies = list;
				
		    }

			@Override
			public int getCount() {	
				return movies.size();
			}

			@Override
			public Object getItem(int position) {	
				return position;
			}

			@Override
			public long getItemId(int position) {			
				return position;
			}

			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				
				LayoutInflater inflater = (LayoutInflater) context.
						getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				if (convertView == null){
					convertView = inflater.inflate(R.layout.activity_column, null);
				}
				//colimage
				ImageView imageView = (ImageView) convertView.findViewById(R.id.image1);
				//imageView.getLayoutParams().height = 100;
				//imageView.getLayoutParams().width = 100;
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				mImageFetcher.loadImage(movies.get(position).get("poster"),
	                     imageView);
				
				
				
				//colposition
				TextView txtPid = (TextView) convertView.findViewById(R.id.textView1);
				//txtPid.setPadding(10, 0, 0, 0);
				txtPid.setText(movies.get(position).get("pid"));
				
				TextView txtPoster = (TextView) convertView.findViewById(R.id.textView2);
				//txtPoster.setPadding(10, 0, 0, 0);
				txtPoster.setText( movies.get(position).get("poster"));
				
				TextView txtName = (TextView) convertView.findViewById(R.id.textView3);
				//txtName.setPadding(10, 0, 0, 0);
				txtName.setText( movies.get(position).get("name") + " (" +movies.get(position).get("year")+")" );
				
				TextView txtGenre = (TextView) convertView.findViewById(R.id.textView4);
				//txtGenre.setPadding(10, 0, 0, 0);
				txtGenre.setText( movies.get(position).get("genre") + "\n" + movies.get(position).get("tag1") + "\n" + "\n" + movies.get(position).get("description"));
				
				TextView upvote = (TextView) convertView.findViewById(R.id.textView5);
				//txtPid.setPadding(10, 0, 0, 0);
				upvote.setText(movies.get(position).get("upvote"));
				TextView downvote = (TextView) convertView.findViewById(R.id.textView6);
				//txtPid.setPadding(10, 0, 0, 0);
				downvote.setText(movies.get(position).get("downvote"));
				
				
				//the voting stuff
				TextView textview10 = (TextView) convertView.findViewById(R.id.textview10);
				textview10.setText(movies.get(position).get("vote"));
				
				//upvote
		        ImageButton button3 = (ImageButton) convertView.findViewById(R.id.button3);
		        button3.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View view) {
						up = Double.parseDouble(movies.get(position).get("upvote"));
						down = Double.parseDouble(movies.get(position).get("downvote"));
						up = up + 1;
						x = up-down;
						y = up + down;
						z = x/y;
						
						foo = z * 10;
						if (foo<1){
							foo=1;
						}
						new Upvote().execute(movies.get(position).get("pid"));
						Toast t = Toast.makeText(getActivity(), "Upvoted ", Toast.LENGTH_SHORT);
							t.show();
						
					}
				});
		        //downvote
		        ImageButton button4 = (ImageButton)convertView.findViewById(R.id.button4);
		        button4.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View view) {
						up = Double.parseDouble(movies.get(position).get("upvote"));
						down = Double.parseDouble(movies.get(position).get("downvote"));
						down = down + 1;
						x = up-down;
						y = up + down;
						z = x/y;
						foo = z * 10;
						if (foo<1){
							foo=1;
						}
						new Downvote().execute(movies.get(position).get("pid"));
						Toast t2 = Toast.makeText(getActivity(), "Downvoted ", Toast.LENGTH_SHORT);
						t2.show();
					}
				});
		      //the share button 
		        ImageButton button2 = (ImageButton) convertView.findViewById(R.id.button2);
		        button2.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View view) {
		            
		            	Intent intent = new Intent(Intent.ACTION_SEND);
		                intent.setType("text/plain");
		                intent.putExtra(Intent.EXTRA_TEXT, movies.get(position).get("name")+ " "+ movies.get(position).get("year") + " "+movies.get(position).get("poster") + " found on " + "https://play.google.com/store/apps/details?id=com.smileowl");  
		                startActivity(Intent.createChooser(intent,"Share with:" ));
		            	
		            }
		        });
				
		      //opens the lecture detail page and sends the information there
		        convertView.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View view) {
		            	/*
		            	String pid = ((TextView) v.findViewById(R.id.textView1)).getText().toString();
         				String poster = ((TextView) v.findViewById(R.id.textView2)).getText().toString();
         				String ame = ((TextView) v.findViewById(R.id.textView3)).getText().toString();
         				String ag1 = ((TextView) v.findViewById(R.id.textView4)).getText().toString();
         				String upvote = ((TextView) v.findViewById(R.id.textView5)).getText().toString();
         				String downvote = ((TextView) v.findViewById(R.id.textView6)).getText().toString();
         				*/
                   		 // String pid =Integer.toString(position);

                   		Intent i2 = new Intent(getActivity(), DetailFragment.class);
         				i2.putExtra(TAG_PID, movies.get(position).get("pid"));
         				i2.putExtra(TAG_POSTER, movies.get(position).get("poster"));
         				i2.putExtra("name", movies.get(position).get("name"));
         				i2.putExtra("year", movies.get(position).get("year"));
         				i2.putExtra("genre", movies.get(position).get("genre"));
         				i2.putExtra("vote", movies.get(position).get("vote"));
         				i2.putExtra("known", movies.get(position).get("known"));
         				i2.putExtra("tag1", movies.get(position).get("tag1"));
         				i2.putExtra("description", movies.get(position).get("description"));
         				i2.putExtra("upvote", movies.get(position).get("upvote"));
         				i2.putExtra("downvote", movies.get(position).get("downvote"));
                   		startActivityForResult(i2,100);
		                
		 
		               // Toast.makeText(parent.getContext(), "cock: " , Toast.LENGTH_SHORT).show();
		            }
		        });

				
				
				return convertView;
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
  		
}

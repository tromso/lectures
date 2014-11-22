package com.smileowl;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.Build;
import android.os.Bundle;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SearchView;

public class DetailFragment extends FragmentActivity {
	
	private static final String TAG = "DetailFragment";
	private static final String TAG_PID = "pid";
	private static final String TAG_POSTER = "poster";
	String pid, poster, name, year, genre, known, description, tag1, vote, upvote, downvote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_detail_fragment);
		
		//hide the keyboard if there is an edit text in the activity.
				this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
				//actionbar search
				Intent intentip = getIntent();
			    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
			      String nam = intentip.getStringExtra(SearchManager.QUERY);
			    
			      
			      Intent intent = new Intent(getApplicationContext(), YourMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("name", nam);
		            startActivityForResult(intent,100);
		            //finish();
			    }
			  //actionbar search end.
			    
			    
		
		Intent i2 = getIntent();
		pid = i2.getStringExtra(TAG_PID);
	    poster = i2.getStringExtra(TAG_POSTER);
	    name = i2.getStringExtra("name");
	    year = i2.getStringExtra("year");
	    genre = i2.getStringExtra("genre");
	    tag1 = i2.getStringExtra("tag1");
	    description = i2.getStringExtra("description");
	    vote = i2.getStringExtra("vote");
	    upvote = i2.getStringExtra("upvote");
	    downvote = i2.getStringExtra("downvote");
	    known = i2.getStringExtra("known");
	    //
	    
	    
	    
	    
	    Intent intent = new Intent(getApplicationContext(), DetailListFragment.class);
			intent.putExtra("pid", pid);
			intent.putExtra("poster", poster);
			intent.putExtra("name", name);
			intent.putExtra("year", year);
			intent.putExtra("genre", genre);
			intent.putExtra("known", known);
			intent.putExtra("tag1", tag1);
			intent.putExtra("vote", vote);
			intent.putExtra("upvote", upvote);
			intent.putExtra("downvote", downvote);
			intent.putExtra("description", description);
			
		
		if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new DetailListFragment(), TAG);
            ft.commit();
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
	                   Intent intent=new Intent(DetailFragment.this, FindMovie.class);
	                   startActivity(intent);
	                   finish();
	           return true;
	           
	           case R.id.item2:
	                   Intent intent1=new Intent(DetailFragment.this, AddMovie.class);
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

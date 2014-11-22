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
import android.widget.SearchView;


public class ShowMovies extends FragmentActivity {
	
	private static final String TAG = "ImageGridActivity";
	
	String id, name, year, genre, known, tag1, tag2, tag3, poster, oster, description, vote ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_movies);

		
		Intent intentip = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getApplicationContext(), YourMovie.class);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
	    }
		
	    if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new ImageGridFragment(), TAG);
            ft.commit();
        }
	    
	    
		
		Intent i = getIntent();
		name = i.getStringExtra("name");
		year = i.getStringExtra("year");
		known = i.getStringExtra("known");
		genre = i.getStringExtra("genre");
		tag1 = i.getStringExtra("tag1");
		tag2 = i.getStringExtra("tag2");
		tag3 = i.getStringExtra("tag3");
		vote = i.getStringExtra("vote");
		
		description = i.getStringExtra("description");
		//resend the stuff to the image grid, might as well send them directly from findmovies
		Intent intent = new Intent(getApplicationContext(), ImageGridFragment.class);
		//	intent.putExtra("name", name);
			intent.putExtra("known", known);
			intent.putExtra("genre", genre);
			intent.putExtra("tag1", tag1);
			intent.putExtra("tag2", tag2);
			intent.putExtra("tag3", tag3);
			intent.putExtra("year", year);
			intent.putExtra("vote", vote);	
        
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
	                   Intent intent=new Intent(ShowMovies.this, FindMovie.class);
	                   startActivity(intent);
	                   finish();
	           return true;
	           
	           case R.id.item2:
	                   Intent intent1=new Intent(ShowMovies.this, AddMovie.class);
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

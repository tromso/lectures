package com.smileowl;





import com.google.analytics.tracking.android.EasyTracker;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class FindMovie extends Activity {
	
	
    String name, genre, known2, stars1, year1, tag1, tag2, tag3, barvote;
    Button button1;
    CheckBox checkbox1, checkbox2, checkbox3, checkbox4;
    RatingBar ratingbar;
    SeekBar seekbar, seekbar2;
    TextView textview1, textview2;
	

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_movie);
		
		
		//test to see if there is internet connection
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
		    //notify user you are online
			
		
        //tested to see if there is internet connection, done
		
		 Intent intenti = getIntent();
		    if (Intent.ACTION_SEARCH.equals(intenti.getAction())) {
		      String name = intenti.getStringExtra(SearchManager.QUERY);
		      
		      Intent intent = new Intent(getApplicationContext(), YourMovie.class);
				intent.putExtra("name", name);
                startActivityForResult(intent,100);
                finish();
		     
		    }
		    
		
		
		
		checkbox1=(CheckBox)findViewById(R.id.checkBox1);
		checkbox2=(CheckBox)findViewById(R.id.checkBox2);
		checkbox3=(CheckBox)findViewById(R.id.checkBox3);
		checkbox4=(CheckBox)findViewById(R.id.checkBox4);
		
		
		seekbar=(SeekBar)findViewById(R.id.seekBar1);
		textview1 = (TextView)findViewById(R.id.textView1);
		textview2 = (TextView)findViewById(R.id.textView2);
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                
                
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        
                }
                
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                	
                     
                }
                
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        
                        int value=seekbar.getProgress();
                        value=value+1912;
                        year1 = Integer.toString(value);
                        textview1.setText("Year > " + year1);
                        
                        
                        
                }
                
        });
        
        seekbar2=(SeekBar)findViewById(R.id.seekBar2);
        seekbar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                
                
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        
                }
                
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        
                }
                
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        
                        int seekbarvote=seekbar2.getProgress();
                       // seekbarvote = seekbarvote +1;
                        seekbarvote=seekbarvote/10;
                        barvote = Integer.toString(seekbarvote);
                        textview2.setText("Score > " + barvote + "    ");
                        
                        
                }
        });

      //dropdown1
      		Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
              ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.movie_type, android.R.layout.simple_spinner_item);
              adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spinner1.setAdapter(adapter1);
              
              spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

                      @SuppressLint("DefaultLocale")
					@Override
                      public void onItemSelected(AdapterView<?> adapter1, View arg1,
                                      int position, long arg3) {
                               genre= adapter1.getItemAtPosition(position).toString().toLowerCase();
                              
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> adapter) {
                              // TODO Auto-generated method stub
                              
                      }
              });
            //dropdown menu2
              Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
              ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.movie_tags, android.R.layout.simple_spinner_item);
              adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spinner2.setAdapter(adapter2);
              
              spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

                      @SuppressLint("DefaultLocale")
					@Override
                      public void onItemSelected(AdapterView<?> adapter2, View arg1,
                                      int position, long arg3) {
                               tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
                              
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> adapter) {
                              // TODO Auto-generated method stub
                              
                      }
              });
            //dropdown menu3
              Spinner spinner3=(Spinner)findViewById(R.id.spinner3);
              ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this, R.array.movie_tags, android.R.layout.simple_spinner_item);
              adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spinner3.setAdapter(adapter3);
              
              spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

                      @SuppressLint("DefaultLocale")
					@Override
                      public void onItemSelected(AdapterView<?> adapter3, View arg1,
                                      int position, long arg3) {
                               tag2= adapter3.getItemAtPosition(position).toString().toLowerCase();
                          
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> adapter) {
                              // TODO Auto-generated method stub
                              
                      }
              });
              Spinner spinner4=(Spinner)findViewById(R.id.spinner4);
              ArrayAdapter<CharSequence> adapter4=ArrayAdapter.createFromResource(this, R.array.movie_tags, android.R.layout.simple_spinner_item);
              adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spinner4.setAdapter(adapter4);
              
              spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {

                      @SuppressLint("DefaultLocale")
					@Override
                      public void onItemSelected(AdapterView<?> adapter4, View arg1,
                                      int position, long arg3) {
                               tag3= adapter4.getItemAtPosition(position).toString().toLowerCase();
                           
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> adapter) {
                              // TODO Auto-generated method stub
                              
                      }
              });
        
		Button button1 = (Button)findViewById(R.id.button1);
		
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
				if (year1!=null){
                	
                }else {year1="1950";}
                if (barvote!=null){
                	
                }else {barvote="1";}

				if (checkbox1.isChecked()){
					known2="known";
					
				}
				else if (checkbox4.isChecked() && checkbox3.isChecked()) {
					known2="23";
					
				}
				else if (checkbox2.isChecked() && checkbox3.isChecked()) {
					known2="12";
					
				}
				else if (checkbox2.isChecked() && checkbox4.isChecked()) {
					known2="13";
					
				}
				else if (checkbox2.isChecked()) {
					known2="well";
					
				}
				else if (checkbox3.isChecked()) {
					known2="average";
					
				}
				else if (checkbox4.isChecked()) {
					known2="little";
					
				}
				
				/*
				Intent intent = new Intent(getApplicationContext(), ShowMovies.class);
			//	intent.putExtra("name", name);
				intent.putExtra("known", known2);
				intent.putExtra("genre", genre);
				intent.putExtra("tag1", tag1);
				intent.putExtra("tag2", tag2);
				intent.putExtra("tag3", tag3);
				intent.putExtra("year", year1);
				intent.putExtra("vote", barvote);
				
				startActivityForResult(intent,100);
				*/
				Intent intent = new Intent();
	            intent.setClass(FindMovie.this, ShowMovies.class);
	            intent.putExtra("known", known2);
				intent.putExtra("genre", genre);
				intent.putExtra("tag1", tag1);
				intent.putExtra("tag2", tag2);
				intent.putExtra("tag3", tag3);
				intent.putExtra("year", year1);
				intent.putExtra("vote", barvote);
	            startActivity(intent);
	            
				
				
			}
		});
        
		} else {
			Toast t = Toast.makeText(FindMovie.this, "No internet connection", Toast.LENGTH_LONG);
			    t.show();
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
	
	
//
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
	                   Intent intent=new Intent(FindMovie.this, FindMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                   startActivity(intent);
	           return true;
	           
	           case R.id.item2:
	                   Intent intent1=new Intent(FindMovie.this, AddMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                   startActivity(intent1);
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

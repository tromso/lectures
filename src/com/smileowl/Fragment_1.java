package com.smileowl;


import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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



public class Fragment_1 extends SherlockFragment {

	String name, genre, known2, stars1, year1, tag1, tag2, tag3, barvote;
    Button button1;
    CheckBox checkbox1, checkbox2, checkbox3, checkbox4;
    RatingBar ratingbar;
    SeekBar seekbar, seekbar2;
    TextView textview1, textview2;
    
   // public Fragment_1() {}
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
      //test to see if there is internet connection
      		final ConnectivityManager conMgr =  (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
      		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
      		if (activeNetwork != null && activeNetwork.isConnected()) {
      		    //notify user you are online
      			
      		} else {
    			Toast t = Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG);
    			    t.show();
    		} 
        
    }/*
	*/
	@Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		final View v = inflater.inflate(R.layout.activity_find_movie, container, false);
		
		Intent intentip = getActivity().getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getActivity(), YourMovie.class);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
	    }
	    
	    
		//the asynctask that makes stuff run inside the fragment
		new As1().execute();
		
        
	   
	    
				return v;
		
	}
	
public class As1 extends AsyncTask<String, String, String>{
		

		     
			@Override
			protected void onPreExecute() {
	            super.onPreExecute();
	          
	        }
			
			protected String doInBackground(String... args) {
				
				
				return null;
				
			}
			
			protected void onPostExecute(String result) {
				
				checkbox1=(CheckBox)getActivity().findViewById(R.id.checkBox1);
				checkbox2=(CheckBox)getActivity().findViewById(R.id.checkBox2);
				checkbox3=(CheckBox)getActivity().findViewById(R.id.checkBox3);
				checkbox4=(CheckBox)getActivity().findViewById(R.id.checkBox4);
				
				
				seekbar=(SeekBar)getActivity().findViewById(R.id.seekBar1);
				textview1 = (TextView)getActivity().findViewById(R.id.textView1);
				textview2 = (TextView)getActivity().findViewById(R.id.textView2);
	        	
	        		getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                	
	              		    
	                
	                	
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
	                
	                seekbar2=(SeekBar)getActivity().findViewById(R.id.seekBar2);
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
	              		Spinner spinner1=(Spinner)getActivity().findViewById(R.id.spinner1);
	                      ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getActivity(), R.array.movie_type, android.R.layout.simple_spinner_item);
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
	                      Spinner spinner2=(Spinner)getActivity().findViewById(R.id.spinner2);
	                      ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.movie_tags, android.R.layout.simple_spinner_item);
	                      adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	                      spinner2.setAdapter(adapter2);
	                      
	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

	                              @SuppressLint("DefaultLocale")
	        					@Override
	                              public void onItemSelected(AdapterView<?> adapter2, View arg1,
	                                              int position, long arg3) {
	                            	  //this is actually tag 3, the most to the right
	                                       tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
	                                       if(position>0){
	                                           Toast t = Toast.makeText(getActivity(), "Selecting too many filters makes it unlikely to find any movie.", Toast.LENGTH_LONG);
	                           			    t.show();
	                           			    }
	                              }

	                              @Override
	                              public void onNothingSelected(AdapterView<?> adapter) {
	                                      // TODO Auto-generated method stub
	                                      
	                              }
	                      });
	                    //dropdown menu3
	                      Spinner spinner3=(Spinner)getActivity().findViewById(R.id.spinner3);
	                      ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(getActivity(), R.array.movie_tags, android.R.layout.simple_spinner_item);
	                      adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	                      spinner3.setAdapter(adapter3);
	                      
	                      spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

	                              @SuppressLint("DefaultLocale")
	        					@Override
	                              public void onItemSelected(AdapterView<?> adapter3, View arg1,
	                                              int position, long arg3) {
	                            	  //this is the first tag
	                                       tag2= adapter3.getItemAtPosition(position).toString().toLowerCase();
	                                       
	                              }

	                              @Override
	                              public void onNothingSelected(AdapterView<?> adapter) {
	                                      // TODO Auto-generated method stub
	                                      
	                              }
	                      });
	                      Spinner spinner4=(Spinner)getActivity().findViewById(R.id.spinner4);
	                      ArrayAdapter<CharSequence> adapter4=ArrayAdapter.createFromResource(getActivity(), R.array.movie_tags, android.R.layout.simple_spinner_item);
	                      adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	                      spinner4.setAdapter(adapter4);
	                      
	                      spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {

	                              @SuppressLint("DefaultLocale")
	        					@Override
	                              public void onItemSelected(AdapterView<?> adapter4, View arg1,
	                                              int position, long arg3) {
	                                       tag3= adapter4.getItemAtPosition(position).toString().toLowerCase();
	                                      
	                                       if(position>0){
	                                           Toast t = Toast.makeText(getActivity(), "Selecting too many filters makes it unlikely to find any movie", Toast.LENGTH_LONG);
	                           			    t.show();
	                           			    }
	                              }
	                              

	                              @Override
	                              public void onNothingSelected(AdapterView<?> adapter) {
	                                      // TODO Auto-generated method stub
	                                      
	                              }
	                              
	                      });
	                      
	                
	        		Button button1 = (Button)getActivity().findViewById(R.id.button1);
	        		
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
	        				
	        				
	        				Intent intent = new Intent();
	        	            intent.setClass(getActivity(), ShowMovies.class);
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
	                     
	                	
	                	
	                }});
	        	
		     }
	        
		}
	
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.find_movie, menu);
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.searchthis).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
	    
	    
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.item1:
			Intent i = new Intent(getActivity(), FindMovie.class);
			startActivity(i);
			return true;
		 case R.id.item2:
	        	
	            Intent intent1=new Intent(getActivity(), AddMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent1);
	            return true;
	            
		 case R.id.action_settings:
	        	
			 Intent intent = new Intent(Intent.ACTION_SEND);
             intent.setType("text/plain");
             intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.smileowl");  
             startActivity(Intent.createChooser(intent,"Share with:" ));
	            return true;         
		default:
			return super.onOptionsItemSelected(item);
		}
	    // ...
	}


 
	
}

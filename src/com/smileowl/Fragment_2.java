package com.smileowl;

import java.lang.reflect.Array;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Fragment_2 extends SherlockFragment{
String category, subcategory, videotext, level, length;
       
CheckBox checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7, checkbox9;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true);
        
    }
	
	@Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		final View v = inflater.inflate(R.layout.fragment_2, container, false);
		
		
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
	        	
	        		getActivity().runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	 
	                	
	                	checkbox2=(CheckBox)getActivity().findViewById(R.id.checkBox2);
	                	checkbox3=(CheckBox)getActivity().findViewById(R.id.checkBox3);
	                	checkbox4=(CheckBox)getActivity().findViewById(R.id.checkBox4);
	                	checkbox5=(CheckBox)getActivity().findViewById(R.id.checkBox5);
	                	checkbox6=(CheckBox)getActivity().findViewById(R.id.checkBox6);
	                	checkbox7=(CheckBox)getActivity().findViewById(R.id.checkBox7);
	                	checkbox9=(CheckBox)getActivity().findViewById(R.id.checkBox9);
	                	
	                	
	                	
	                	final Spinner spinner1=(Spinner)getActivity().findViewById(R.id.spinner1);
	                	final Spinner spinner2=(Spinner)getActivity().findViewById(R.id.spinner2);

	                	
	                	ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getActivity(), R.array.lectures, android.R.layout.simple_spinner_item);
	                      adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	                      spinner1.setAdapter(adapter1);
	                      spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

	                              @SuppressLint("DefaultLocale")
	        					@Override
	                              public void onItemSelected(AdapterView<?> adapter1, View arg1,
	                                              int position, long arg3) {
	                            	  
	                            	
	                            	  category = adapter1.getItemAtPosition(position).toString().toLowerCase();
	                            	  if (spinner1.getSelectedItem().equals("Programming")){
	                            		  
		                                 // Toast.makeText(getActivity(),"Text! should be programm" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.programming, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase(); 
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                  
	                                  }else if(spinner1.getSelectedItem().equals("All")){
	                                	  
		                                //  Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.alllectures, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                   
	                                  }
                                      else if(spinner1.getSelectedItem().equals("Others")){
	                                	  
		                                //  Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.others, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase(); 
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                   
	                                  }
	                            	  else if(spinner1.getSelectedItem().equals("Science")){
	                                	  
		                                 // Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.science, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                   
	                                  }
                                     else if(spinner1.getSelectedItem().equals("History")){
	                                	  
		                                //  Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.history, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                   
	                                  }
                                      else if(spinner1.getSelectedItem().equals("Business and Economics")){
	                                	  
		                                //  Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.business, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                   
	                                  }
                                       else if(spinner1.getSelectedItem().equals("Languages")){
	                                	  
		                            //      Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.languages, android.R.layout.simple_spinner_item);
		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		         	                     spinner2.setAdapter(adapter2);
		         	                      
		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		         	                    	  @SuppressLint("DefaultLocale")
		         	        					@Override
		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
		         	                                              int position, long arg3) {
		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
		         	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
		         	                              }

		         	                              @Override
		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
		         	                                      // TODO Auto-generated method stub
		         	                                      
		         	                              }
		         	                      });
	                                   
	                                  }
                                       else if(spinner1.getSelectedItem().equals("Software")){
 	                                	  
 		                                //  Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
 		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.software, android.R.layout.simple_spinner_item);
 		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 		         	                     spinner2.setAdapter(adapter2);
 		         	                      
 		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 		         	                    	  @SuppressLint("DefaultLocale")
 		         	        					@Override
 		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 		         	                                              int position, long arg3) {
 		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 		         	                    		subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 		         	                              }

 		         	                              @Override
 		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
 		         	                                      // TODO Auto-generated method stub
 		         	                                      
 		         	                              }
 		         	                      });
 	                                   
 	                                  }
                                       else if(spinner1.getSelectedItem().equals("Do it Yourself")){
 	                                	  
 		                               //   Toast.makeText(getActivity(),"Text! scien?" + position,Toast.LENGTH_SHORT).show();
 		                                  ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getActivity(), R.array.diy, android.R.layout.simple_spinner_item);
 		         	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 		         	                     spinner2.setAdapter(adapter2);
 		         	                      
 		         	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 		         	                    	  @SuppressLint("DefaultLocale")
 		         	        					@Override
 		         	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 		         	                                              int position, long arg3) {
 		         	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 		         	                    		subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 		         	                              }

 		         	                              @Override
 		         	                              public void onNothingSelected(AdapterView<?> adapter1) {
 		         	                                      // TODO Auto-generated method stub
 		         	                                      
 		         	                              }
 		         	                      });
 	                                   
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
	            				
	            				//youtube, video and text
	            				if (checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked()) {
	            					videotext="format";
	            				}
	            				//video and youtube
	            				else if (checkbox2.isChecked() && checkbox3.isChecked()) {
	            					videotext="12";
	            				}
	            				// video and text
	            				else if (checkbox2.isChecked() && checkbox4.isChecked()) {
	            					videotext="23";
	            				}
	            				// youtube and text
	            				else if (checkbox3.isChecked() && checkbox4.isChecked()) {
	            					videotext="13";
	            				}
	            				
	            				else if (checkbox2.isChecked()){
	    	    					videotext="video";	
	    	    				}
	            				else if (checkbox3.isChecked()){
	    	    					 videotext="youtube";	
	    	    				}
	            				else if (checkbox4.isChecked()){
	    	    					 videotext="text";	
	    	    				}
	            				
	            				
	            				
	            				if (checkbox5.isChecked() && checkbox6.isChecked()) {
	            					level="Level";
	            				}
	            				
	            				else if (checkbox5.isChecked()){
	    	    				    level="beginner";	
	    	    				}
	    	                	else if (checkbox6.isChecked()){
	    	    					level="advanced";	
	    	    				}
	    	                	
	    	                	
	    	                	
	    	                	if (checkbox7.isChecked() && checkbox9.isChecked()) {
	            					length="Length";
	            				}
	    	                	
	    	                	else if (checkbox7.isChecked()){
	    	    					length="short";	
	    	    				}
	    	                	
	    	                	else if (checkbox9.isChecked()){
	    	    					length="long";	
	    	    				}
	    	                	
	    	                	
	    	                	Intent intent = new Intent();
	    	    	            intent.setClass(getActivity(), LectureShow.class);
	    	    	            intent.putExtra("category", category);
	    	    				intent.putExtra("subcategory", subcategory);
	    	    				intent.putExtra("videotext", videotext);
	    	    				intent.putExtra("level", level);
	    	    				intent.putExtra("length", length);
	    	    	            startActivity(intent);
	    	                	
	            				//Toast.makeText(getActivity(),"girly men " + category + subcategory + videotext+level +length,Toast.LENGTH_SHORT).show();
	            				
	            			}
	            			
	            		});
	                	
	                     
	                	
	                	
	                }});
	        	
		     }
	        
		}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    inflater.inflate(R.menu.main, menu);
	   
	    
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		
		 case R.id.item1:
	        	
	            Intent intent=new Intent(getActivity(), Lecturetop10.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
		
		 case R.id.item2:
	        	
	            Intent intent1=new Intent(getActivity(), AddLecture.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

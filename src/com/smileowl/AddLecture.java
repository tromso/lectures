package com.smileowl;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.analytics.tracking.android.EasyTracker;

import android.os.AsyncTask;
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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("DefaultLocale")
public class AddLecture extends Activity {
	
	String name, category, subcategory, description, link, videotext, level, length;
	private EditText edittext1, edittext2, edittext3;
	jParser2 parser = new jParser2();
	Button button1;
	private static String urlNew = "http://smileowl.com/Test21/PDO/insertlecture.php";
	
	//radiobutton youtube or not
    public void onRadioButtonClicked(View view)
    {
             boolean checked = ((RadioButton) view).isChecked();
                
                // Check which radio button was clicked yo
                switch(view.getId()) {
                
                case R.id.radio0:
                if (checked)
                        videotext="format youtube 12 13";
               // Toast.makeText(AddLecture.this, "youtube", 5000).show();
                        break;
                    case R.id.radio1:
                        if (checked)
                        	videotext="format video 12 23";
                   //  Toast.makeText(AddLecture.this, "other vid", 5000).show();      
                        break;
                    case R.id.radio2:
                        if (checked)
                            videotext="format text 13 23";
                    //  Toast.makeText(AddLecture.this, "text", 5000).show();         
                        break;
                }
                
                
               
    }
 
    
  //activity begins
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_lecture);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Intent intentip = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getApplicationContext(), YourLecture.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
	    }
	    //the name
	    edittext1 = (EditText)findViewById(R.id.editText1);
	    //za radio 2
	    RadioGroup rdoGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
	    rdoGroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {


            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio3)
                       {
                	 level="Level beginner";
                     //Toast.makeText(AddLecture.this, "beginner", 5000).show();

                }

                if (checkedId == R.id.radio4)
                       {
                	level="Level advanced";
                  //  Toast.makeText(AddLecture.this, "advanced", 5000).show();  


                }
                
            }
        });
	  //za radio 3
	    RadioGroup rdoGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
	    rdoGroup3.setOnCheckedChangeListener(new OnCheckedChangeListener() {


            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio5)
                       {
                	  length="Length short";
                     // Toast.makeText(AddLecture.this, "Length short", 5000).show();
                           
                }

                if (checkedId == R.id.radio6)
                       {
                	length="Length long";
                   // Toast.makeText(AddLecture.this, "Length long", 5000).show();     

                }
                
                
            }
        });
	    
	    final Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
    	final Spinner spinner2=(Spinner)findViewById(R.id.spinner2);

    	
    	ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.lectures, android.R.layout.simple_spinner_item);
          adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinner1.setAdapter(adapter1);
          spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

                  @SuppressLint("DefaultLocale")
				@Override
                  public void onItemSelected(AdapterView<?> adapter1, View arg1,
                                  int position, long arg3) {
                	  
                	  category = adapter1.getItemAtPosition(position).toString().toLowerCase();
                	  category ="Category "+ category;
                	  if (spinner1.getSelectedItem().equals("Programming")){
                		  
                         // Toast.makeText(this,"Text! should be programm" + position,Toast.LENGTH_SHORT).show();
                          ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.programming, android.R.layout.simple_spinner_item);
 	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	                     spinner2.setAdapter(adapter2);
 	                      
 	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 	                    	  @SuppressLint("DefaultLocale")
 	        					@Override
 	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 	                                              int position, long arg3) {
 	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase(); df
 	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 	                    		subcategory ="Subcategory "+ subcategory;
 	                              }

 	                              @Override
 	                              public void onNothingSelected(AdapterView<?> adapter1) {
 	                                      // TODO Auto-generated method stub
 	                                      
 	                              }
 	                      });
                      
                      }else if(spinner1.getSelectedItem().equals("All")){
                    	  
                         // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                          ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.alllectures, android.R.layout.simple_spinner_item);
 	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	                     spinner2.setAdapter(adapter2);
 	                      
 	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 	                    	  @SuppressLint("DefaultLocale")
 	        					@Override
 	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 	                                              int position, long arg3) {
 	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 	                    		subcategory ="Subcategory "+ subcategory;
 	                              }

 	                              @Override
 	                              public void onNothingSelected(AdapterView<?> adapter1) {
 	                                      // TODO Auto-generated method stub
 	                                      
 	                              }
 	                      });
                       
                      }
                      else if(spinner1.getSelectedItem().equals("Others")){
                    	  
                         // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                          ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.others, android.R.layout.simple_spinner_item);
 	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	                     spinner2.setAdapter(adapter2);
 	                      
 	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 	                    	  @SuppressLint("DefaultLocale")
 	        					@Override
 	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 	                                              int position, long arg3) {
 	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 	                    		subcategory ="Subcategory "+ subcategory;
 	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 	                                      
 	                              }

 	                              @Override
 	                              public void onNothingSelected(AdapterView<?> adapter1) {
 	                                      // TODO Auto-generated method stub
 	                                      
 	                              }
 	                      });
                       
                      }
                	  else if(spinner1.getSelectedItem().equals("Science")){
                    	  
                         // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                          ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.science, android.R.layout.simple_spinner_item);
 	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	                     spinner2.setAdapter(adapter2);
 	                      
 	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 	                    	  @SuppressLint("DefaultLocale")
 	        					@Override
 	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 	                                              int position, long arg3) {
 	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 	                    		subcategory ="Subcategory "+ subcategory;
 	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 	                                      
 	                              }

 	                              @Override
 	                              public void onNothingSelected(AdapterView<?> adapter1) {
 	                                      // TODO Auto-generated method stub
 	                                      
 	                              }
 	                      });
 	                      
                       
                      }
                	  else if(spinner1.getSelectedItem().equals("History")){
                    	  
                          // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                           ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.history, android.R.layout.simple_spinner_item);
  	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	                     spinner2.setAdapter(adapter2);
  	                      
  	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

  	                    	  @SuppressLint("DefaultLocale")
  	        					@Override
  	                              public void onItemSelected(AdapterView<?> adapter1, View v,
  	                                              int position, long arg3) {
  	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
  	                    		subcategory ="Subcategory "+ subcategory;
  	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
  	                                      
  	                              }

  	                              @Override
  	                              public void onNothingSelected(AdapterView<?> adapter1) {
  	                                      // TODO Auto-generated method stub
  	                                      
  	                              }
  	                      });
  	                      
                        
                       }
                      else if(spinner1.getSelectedItem().equals("Business and Economics")){
                    	  
                         // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                          ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.business, android.R.layout.simple_spinner_item);
 	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	                     spinner2.setAdapter(adapter2);
 	                      
 	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 	                    	  @SuppressLint("DefaultLocale")
 	        					@Override
 	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 	                                              int position, long arg3) {
 	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 	                    		subcategory ="Subcategory "+ subcategory;
 	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 	                                      
 	                              }

 	                              @Override
 	                              public void onNothingSelected(AdapterView<?> adapter1) {
 	                                      // TODO Auto-generated method stub
 	                                      
 	                              }
 	                      });
                       
                      }
                       else if(spinner1.getSelectedItem().equals("Languages")){
                    	  
                         // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                          ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.languages, android.R.layout.simple_spinner_item);
 	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	                     spinner2.setAdapter(adapter2);
 	                      
 	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

 	                    	  @SuppressLint("DefaultLocale")
 	        					@Override
 	                              public void onItemSelected(AdapterView<?> adapter1, View v,
 	                                              int position, long arg3) {
 	                    		 subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
 	                    		subcategory ="Subcategory "+ subcategory;
 	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
 	                                      
 	                              }

 	                              @Override
 	                              public void onNothingSelected(AdapterView<?> adapter1) {
 	                                      // TODO Auto-generated method stub
 	                                      
 	                              }
 	                      });
                       
                      }
                       else if(spinner1.getSelectedItem().equals("Software")){
                     	  
                          // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                           ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.software, android.R.layout.simple_spinner_item);
  	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	                     spinner2.setAdapter(adapter2);
  	                      
  	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

  	                    	  @SuppressLint("DefaultLocale")
  	        					@Override
  	                              public void onItemSelected(AdapterView<?> adapter1, View v,
  	                                              int position, long arg3) {
  	                    		subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
  	                    		subcategory ="Subcategory "+ subcategory;
  	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
  	                                      
  	                              }

  	                              @Override
  	                              public void onNothingSelected(AdapterView<?> adapter1) {
  	                                      // TODO Auto-generated method stub
  	                                      
  	                              }
  	                      });
                        
                       }
                       else if(spinner1.getSelectedItem().equals("Do it Yourself")){
                     	  
                          // Toast.makeText(AddLecture.this,"Text! scien?" + position,Toast.LENGTH_SHORT).show();
                           ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(AddLecture.this, R.array.diy, android.R.layout.simple_spinner_item);
  	                     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  	                     spinner2.setAdapter(adapter2);
  	                      
  	                      spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

  	                    	  @SuppressLint("DefaultLocale")
  	        					@Override
  	                              public void onItemSelected(AdapterView<?> adapter1, View v,
  	                                              int position, long arg3) {
  	                    		subcategory = adapter1.getItemAtPosition(position).toString().toLowerCase();
  	                    		subcategory ="Subcategory "+ subcategory;
  	                                     //  tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
  	                                      
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
      
        button1 = (Button)findViewById(R.id.button1);
		
		edittext2 = (EditText)findViewById(R.id.editText2);
		edittext3 = (EditText)findViewById(R.id.editText3);
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AddDaValue().execute();
				
				
			}
		});
		//Toast t = Toast.makeText(getApplicationContext(), "best "+value1, 5000);
		//t.show();//
	}
	
	class AddDaValue extends AsyncTask<String, String, String>{
		
		String name = edittext1.getText().toString(); 
		String link = edittext3.getText().toString();
		String description = edittext2.getText().toString();
		

		@Override
		protected String doInBackground(String... args) {
			
			
			if (name.length()>1 && link.length()>6 && subcategory!=null && videotext!=null
        			&& level!=null && length!=null){
				

			
			List<NameValuePair> params = new ArrayList<NameValuePair> ();
			
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("category", category));
			params.add(new BasicNameValuePair("subcategory", subcategory));
			params.add(new BasicNameValuePair("videotext", videotext));
			params.add(new BasicNameValuePair("level", level));
			params.add(new BasicNameValuePair("link", link));
			params.add(new BasicNameValuePair("description", description));
			params.add(new BasicNameValuePair("length", length));
			@SuppressWarnings("unused")
			JSONObject json = parser.makeHttpRequest(urlNew, params);
			
			}
			return null;
		}
		
		 protected void onPostExecute(String zoom){
			 
				
				runOnUiThread(new Runnable() {
	                public void run() {
	                	
	            
	                	if (name.length()>1 && link.length()>6 && subcategory!=null && videotext!=null
	                			&& level!=null && length!=null){
	                    	
	            			Toast t = Toast.makeText(getApplicationContext(), "The lecture was added", Toast.LENGTH_LONG);
	        				t.show();
	        				  Intent intent=new Intent(AddLecture.this, AddLecture.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                          startActivity(intent);
	                          finish();
	                    	}else{
	                    		Toast z = Toast.makeText(AddLecture.this, "Fill in the fields with valid data", Toast.LENGTH_LONG);
	                			z.show();
	                    	}
	                	
	                }
	                });
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
		// Inflate the menu; this adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.add_menu, menu);
		
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
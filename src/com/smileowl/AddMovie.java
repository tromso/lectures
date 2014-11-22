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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("DefaultLocale")
public class AddMovie extends Activity {
	
	String genre, stars1, known, tag1, tag2, tag3, tags;
	private EditText edittext1, edittext2, edittext3, edittext4;
	jParser2 parser = new jParser2();
	Button button1;
	private static String urlNew = "http://smileowl.com/Test21/PDO/insert.php";
    public void onRadioButtonClicked(View view)
    {
             boolean checked = ((RadioButton) view).isChecked();
                
                // Check which radio button was clicked
                switch(view.getId()) {
                
                case R.id.radio0:
                if (checked)
                        known="well known 12 13";
                //Toast.makeText(AddMovie.this, "Well known", 5000).show();
                        break;
                    case R.id.radio1:
                        if (checked)
                                known="average known 12 23";
                     //   Toast.makeText(AddMovie.this, "Medium", 5000).show();      
                        break;
                    case R.id.radio2:
                        if (checked)
                                known="little known 13 23";
                       // Toast.makeText(AddMovie.this, "Hipster", 5000).show();         
                        break;
                }
               //activity begins
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_movie);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Intent intentip = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intentip.getAction())) {
	      String nam = intentip.getStringExtra(SearchManager.QUERY);
	      
	      Intent intent = new Intent(getApplicationContext(), YourMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("name", nam);
            startActivityForResult(intent,100);
	    }
	    edittext1 = (EditText)findViewById(R.id.editText1);
	    
	    Button button2 = (Button)findViewById(R.id.button2);
	    button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String me = edittext1.getText().toString(); 
				Intent intent = new Intent(getApplicationContext(), YourMovie.class);
				intent.putExtra("name", me);
	            startActivityForResult(intent,100);
	           // Toast.makeText(AddMovie.this, " "+ me, Toast.LENGTH_LONG).show(); 
			}
		});
		
		//dropdown menu
        Spinner spinner=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.movie_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

                @SuppressLint("DefaultLocale")
				@Override
                public void onItemSelected(AdapterView<?> adapter1, View arg1,
                                int position, long arg3) {
                         genre= adapter1.getItemAtPosition(position).toString().toLowerCase();
                         genre="Genre "+genre;
                         
                         
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

                @Override
                public void onItemSelected(AdapterView<?> adapter2, View arg1,
                                int position, long arg3) {
                         tag1= adapter2.getItemAtPosition(position).toString().toLowerCase();
                         
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapter) {
                        // TODO Auto-generated method stub
                        
                }
        });//
      //dropdown menu3
        Spinner spinner3=(Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this, R.array.movie_tags, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        
        spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

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
      //dropdown menu4
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
        button1 = (Button)findViewById(R.id.button1);
		
		edittext2 = (EditText)findViewById(R.id.editText2);
		edittext3 = (EditText)findViewById(R.id.editText3);
		edittext4 = (EditText)findViewById(R.id.editText4);
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
		String year = edittext2.getText().toString();
		String poster = edittext3.getText().toString();
		String description = edittext4.getText().toString();
		

		@Override
		protected String doInBackground(String... args) {
			
			
			tags = tag1 + " " + tag2 + " " + tag3;
			tags = "Tag " + tags;
			
		
			if (year.length()<2){
				year="1";
			}
			int yearInt = Integer.parseInt(year);
			
			
			if(name.length()>1  && year.length()==4 && known!=null && yearInt<2015 && yearInt>1915){
				

			
			List<NameValuePair> params = new ArrayList<NameValuePair> ();
			
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("year", year));
			params.add(new BasicNameValuePair("known", known));
			params.add(new BasicNameValuePair("genre", genre));
			params.add(new BasicNameValuePair("tags", tags));
			params.add(new BasicNameValuePair("poster", poster));
			params.add(new BasicNameValuePair("description", description));
			@SuppressWarnings("unused")
			JSONObject json = parser.makeHttpRequest(urlNew, params);
			
			}
			return null;
		}
		
		 protected void onPostExecute(String zoom){
			 
				
				runOnUiThread(new Runnable() {
	                public void run() {
	                	
	                	
	        			if (year.length()<2){
	        				year="1";
	        			}
	        			int yearInt = Integer.parseInt(year);
	                	if (name.length()>1 && year.length()==4 && known!=null && yearInt<2015 && yearInt>1915){
	                    	
	            			Toast t = Toast.makeText(getApplicationContext(), "The movie was added", Toast.LENGTH_LONG);
	        				t.show();
	        				  Intent intent=new Intent(AddMovie.this, FindMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                          startActivity(intent);
	                          finish();
	                    	}else{
	                    		Toast z = Toast.makeText(AddMovie.this, "Fill in the fields with valid data", Toast.LENGTH_LONG);
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
	                   Intent intent=new Intent(AddMovie.this, FindMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	                   startActivity(intent);
	                   finish();
	           return true;
	           
	           case R.id.item2:
	                   Intent intent1=new Intent(AddMovie.this, AddMovie.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

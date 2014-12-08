package com.digitalsoft.smartmarket;

import java.io.IOException;
import java.util.ArrayList;

import com.digitalsoft.smartmarket.EntityClasses.*;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewCategories extends Activity 
{
	private ListView lv;
	private Activity context;
	private String appTypeName;
	private ArrayList<Category> categories;
	private ProgressDialog pd;
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcategories);
        

        context = this;
        lv = (ListView) findViewById(R.id.categories_listView); 
        HeaderHelper.setHeader(context, true);
        
        appTypeName = getIntent().getExtras().getString("appType");
        loadData();
        
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				Integer selectedItemPosition = arg2;
				String categoryName = lv.getItemAtPosition(selectedItemPosition).toString();
				Intent intent = new Intent(context, ViewApps.class);
				intent.putExtra("appType", appTypeName);
				intent.putExtra("category", categoryName);
				startActivity(intent);			
			}
		});	
    }
	private void loadData()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        protected void onPreExecute() 
	        {
	        	pd = new ProgressDialog(context); 
	        	pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	        protected Void doInBackground(Void... args) 
	        {        	
	        	try 
		        {
	        		categories = WebServiceHelper.getCategories(appTypeName);
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	            pd.dismiss(); 
	            if(connetionValid)
	            {
		            ArrayList<String> categoriesNamesList = new ArrayList<String>();
		            for (Category item : categories) 
		            {
		            	categoriesNamesList.add(item.name);
		            }       
		            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, categoriesNamesList);
		            lv.setAdapter(adapter);
	            }
	            else
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
    }  
	
	
	// to prevent a force close problem when the screen rotate during progress dialog working
	@Override
    protected void onDestroy()
    {
        super.onDestroy();
        pd.dismiss();
    }
	
	// options menu methods must be included in all activities that has the options menu enabled
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	OptionsMenuHelper.onCreateOptionsMenu(context, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) 
    {
    	OptionsMenuHelper.onMenuOpened(context, menu);
    	return super.onMenuOpened(featureId, menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
    	OptionsMenuHelper.onPrepareOptionsMenu(menu, false);  	
    	return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	OptionsMenuHelper.onOptionsItemSelected(context, item, null);
    	return super.onOptionsItemSelected(item);
    }
    // end option menu methods
}

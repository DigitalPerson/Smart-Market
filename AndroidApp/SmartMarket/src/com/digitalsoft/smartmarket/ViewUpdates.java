package com.digitalsoft.smartmarket;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.digitalsoft.smartmarket.EntityClasses.App;
import com.digitalsoft.smartmarket.Helpers.CustomListViewAdapterForUpdates;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;

public class ViewUpdates extends Activity 
{
	private ListView lv;
	private Activity context;
	private ArrayList<PackageInfo> installedPackagesInfo = null;
	private ArrayList<PackageInfo> installedPackagesInfoThatNeedUpdate = null;
	private ArrayList<App> appsThatNeedUpdate = null;
	private ProgressDialog pd;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewupdates);
       
        context = this;
        lv = (ListView) findViewById(R.id.updates_listView);
        HeaderHelper.setHeader(context , true);
        
      
        loadData();
       
        
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{      
				Integer selectedItemPosition = arg2;
				PackageInfo packageInfo = installedPackagesInfoThatNeedUpdate.get(selectedItemPosition);
				Intent intent = new Intent(context, ViewAppDetails.class);
				intent.putExtra("packageName", packageInfo.packageName);
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
	            installedPackagesInfo = GeneralHelper.getInstalledPackagesInfo(context, false);
	            installedPackagesInfoThatNeedUpdate = new ArrayList<PackageInfo>();
	            appsThatNeedUpdate = new ArrayList<App>();
	        }
	        protected Void doInBackground(Void... args) 
	        {
		        try 
		        {
		        	for(PackageInfo pi : installedPackagesInfo)
		        	{
		        		App app = WebServiceHelper.checkForNewerVersion(pi.packageName, pi.versionName);
		        		if(app != null)
		        		{
		        			installedPackagesInfoThatNeedUpdate.add(pi);
		        			appsThatNeedUpdate.add(app);
		        		}
		        	}
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
	            if (connetionValid)
	            {
	            	 CustomListViewAdapterForUpdates adapter = new CustomListViewAdapterForUpdates(context, installedPackagesInfoThatNeedUpdate, appsThatNeedUpdate);
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

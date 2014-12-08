package com.digitalsoft.smartmarket;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.digitalsoft.smartmarket.Helpers.CustomListViewAdapterForMyApps;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;

public class ViewMyApps extends Activity 
{
	private ListView lv;
	private Activity context;
	private ArrayList<PackageInfo> installedPackagesInfo = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmyapps);
       
        context = this;
        lv = (ListView) findViewById(R.id.myApps_listView);
        HeaderHelper.setHeader(context, true);
        
        installedPackagesInfo = GeneralHelper.getInstalledPackagesInfo(context, false);
        CustomListViewAdapterForMyApps adapter = new CustomListViewAdapterForMyApps(context, installedPackagesInfo);
        lv.setAdapter(adapter);
        
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{      
				Integer selectedItemPosition = arg2;
				PackageInfo packageInfo = installedPackagesInfo.get(selectedItemPosition);
				Intent intent = new Intent(context, ViewAppDetails.class);
				intent.putExtra("packageName", packageInfo.packageName);
				startActivity(intent);			
			}
		});        
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

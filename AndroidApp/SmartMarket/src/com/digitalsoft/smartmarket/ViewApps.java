package com.digitalsoft.smartmarket;

import java.io.IOException;
import java.util.ArrayList;
import com.digitalsoft.smartmarket.EntityClasses.*;
import com.digitalsoft.smartmarket.Helpers.Config;
import com.digitalsoft.smartmarket.Helpers.CustomListViewAdapterForNormalApps;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewApps extends Activity 
{
	private ListView lv;
	private ProgressDialog pd;
	private Activity context;
	private String keyword = null;
	private String appTypeName = null;
	private String categoryName = null;
	private String developerID = null;
	private String email = null;
	private String featuredApps = null;
	private String newestApps = null;
	private ArrayList<App> apps = null;
	private ArrayList<App> currentSetOfApps = null;
	private Boolean finishedLoadingFirstSetOfApps = false;
	private Boolean finishedLoadingPrevSetOfApps = true;
	private Boolean allItemsLoaded = false;
	private Integer startIndex = 0;
	private CustomListViewAdapterForNormalApps adapter;
	private View footerView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewapps);
        
        context = this;
        lv = (ListView) findViewById(R.id.apps_listView);
        HeaderHelper.setHeader(context, true);
        
        Intent intent = getIntent();
        if(intent.hasExtra("keyword"))
        {
        	keyword = intent.getExtras().getString("keyword");
        }
        else if (intent.hasExtra("appType") && intent.hasExtra("category"))
        {
	        appTypeName = intent.getExtras().getString("appType");
	    	categoryName = intent.getExtras().getString("category");
        }
        else if (intent.hasExtra("developer"))
        {
        	developerID = intent.getExtras().getString("developer");
        }
        else if (intent.hasExtra("email"))
        {
        	email = intent.getExtras().getString("email");
        }
        else if (intent.hasExtra("featuredApps"))
        {
        	featuredApps = intent.getExtras().getString("featuredApps");
        }
        else if (intent.hasExtra("newestApps"))
        {
        	newestApps = intent.getExtras().getString("newestApps");
        }

        
        apps = new ArrayList<App>();
        adapter = new CustomListViewAdapterForNormalApps(context);
        lv.setAdapter(adapter);
		footerView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listviewfooter, null, false);
		lv.addFooterView(footerView);
		lv.setAdapter(adapter);

		lv.setOnScrollListener(new OnScrollListener()
		{				
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) 
			{					
				int lastInScreen = firstVisibleItem + visibleItemCount;				
				if(lastInScreen == totalItemCount && finishedLoadingPrevSetOfApps && !allItemsLoaded)
				{					
					loadData();	
				}
			}
			public void onScrollStateChanged(AbsListView arg0, int arg1) 
			{
				// TODO Auto-generated method stub
			}
		});
		
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{      
				Integer selectedItemPosition = arg2;
				if(arg1.findViewById(R.id.loadingMore_textView) == null) // disable clicking on "loading more" item
				{
					App app = apps.get(selectedItemPosition);
					Intent intent = new Intent(context, ViewAppDetails.class);
					intent.putExtra("packageName", app.packageName);
					startActivity(intent);
				}
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
	        	if (!finishedLoadingFirstSetOfApps)
	        	{
	        		pd = new ProgressDialog(context);
		        	pd.setMessage(getResources().getText(R.string.waitMessage));
		            pd.setCanceledOnTouchOutside(false);
		            pd.show();
	        	}
	            finishedLoadingPrevSetOfApps = false;
	        }
	        protected Void doInBackground(Void... args) 
	        {
	        	try 
		        {
	        		if (keyword != null)
	        		{
	        			currentSetOfApps = WebServiceHelper.searchForApp(keyword, startIndex.toString(), Config.numberOfAppsPerPage.toString());
	        			apps.addAll(currentSetOfApps); 
	        		}
	        		else if (appTypeName != null && categoryName != null)
	        		{
	        			currentSetOfApps = WebServiceHelper.getApps(appTypeName, categoryName, startIndex.toString(), Config.numberOfAppsPerPage.toString());
	        			apps.addAll(currentSetOfApps); 
	        		}
	        		else if (developerID != null)
	        		{
	        			currentSetOfApps = WebServiceHelper.getAppsForSpecificDeveloper(developerID, startIndex.toString(), Config.numberOfAppsPerPage.toString());
	        			apps.addAll(currentSetOfApps); 
	        		}
	        		else if (email != null)
	        		{
	        			currentSetOfApps = WebServiceHelper.getSuggestedApps(email, startIndex.toString(), Config.numberOfAppsPerPage.toString());
	        			apps.addAll(currentSetOfApps); 
	        		}
	        		else if (featuredApps != null)
	        		{
	        			currentSetOfApps = WebServiceHelper.getFeaturedApps(startIndex.toString(), Config.numberOfAppsPerPage.toString());
	        			apps.addAll(currentSetOfApps); 
	        		}
	        		else if (newestApps != null)
	        		{
	        			currentSetOfApps = WebServiceHelper.getNewestApps(startIndex.toString(), Config.numberOfAppsPerPage.toString());
	        			apps.addAll(currentSetOfApps); 
	        		}
	        		startIndex += Config.numberOfAppsPerPage;
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	        	if(!finishedLoadingFirstSetOfApps)
	        	{
	        		pd.dismiss();
	        		finishedLoadingFirstSetOfApps = true;
	        	}
	            if(connetionValid)
	            {
		            if (apps.size() == 0)
		            {
		            	Toast.makeText(context, context.getResources().getText(R.string.noResultsFoundMessage), Toast.LENGTH_SHORT).show();
		            }
		            for(int i = 0; i < currentSetOfApps.size(); i++)
                    {
                    	App app = currentSetOfApps.get(i);
                    	adapter.add(app);
                    }
	                adapter.notifyDataSetChanged();		
	            	if(currentSetOfApps.size() < Config.numberOfAppsPerPage)
	            	{
	            		allItemsLoaded = true;
	            		lv.removeFooterView(footerView);
	            	}
	    			finishedLoadingPrevSetOfApps = true;
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

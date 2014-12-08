package com.digitalsoft.smartmarket;

import java.io.IOException;
import com.digitalsoft.smartmarket.EntityClasses.App;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends Activity 
{
	private Activity context;
	private ImageView suggestedApps_iv;
	private ImageView apps_iv;
	private ImageView games_iv;
	private ImageView featuredApps_iv;
	private ImageView myApps_iv;
	private ImageView updates_iv;
	private ImageView newestApps_iv;
	private ImageView about_iv;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        context = this;
        //set language
        String language = GeneralHelper.loadStringFromDefaultSharedPreferences(context, "language", null);  
        GeneralHelper.setLocale(context, language);
        //end set language
        setContentView(R.layout.main);      
        HeaderHelper.setHeader(context, false);
        
       
        // check for a newer version of the app
        if(GeneralHelper.loadBooleanFromDefaultSharedPreferences(context, "enableAutoUpgrade", true))
        {
        	checkForUpdate(); 
        }
        //end check
              
        
        suggestedApps_iv = (ImageView) findViewById(R.id.suggestedApss_imageView);
        suggestedApps_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				String email = GeneralHelper.loadStringFromSharedPreferences(context, "email");
				if (email != null)
				{
					Intent intent = new Intent(context, ViewApps.class);
					intent.putExtra("email", email);
					context.startActivity(intent);
				}
				else
				{
					Toast.makeText(context, context.getResources().getText(R.string.signInMessage), Toast.LENGTH_SHORT).show();
				}	
			}
		});
        
        apps_iv = (ImageView) findViewById(R.id.apps_imageView);
        apps_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewCategories.class);
				intent.putExtra("appType", "Application");
				context.startActivity(intent);
			}
		});
        
        games_iv = (ImageView) findViewById(R.id.games_imageView);
        games_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewCategories.class);
				intent.putExtra("appType", "Game");
				context.startActivity(intent);
			}
		});
  
        featuredApps_iv = (ImageView) findViewById(R.id.featuredApps_imageView);
        featuredApps_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewApps.class);
				intent.putExtra("featuredApps", "featuredApps");
				context.startActivity(intent);
			}
		});
        
        myApps_iv = (ImageView) findViewById(R.id.myApps_imageView);
        myApps_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewMyApps.class);
				context.startActivity(intent);
			}
		});
        
        updates_iv = (ImageView) findViewById(R.id.updates_imageView);
        updates_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewUpdates.class);
				context.startActivity(intent);
			}
		});
        
        newestApps_iv = (ImageView) findViewById(R.id.newestApps_imageView);
        newestApps_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewApps.class);
				intent.putExtra("newestApps", "newestApps");
				context.startActivity(intent);
			}
		});
        
        about_iv = (ImageView) findViewById(R.id.about_imageView);
        about_iv.setOnClickListener(new OnClickListener() 
        {		
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, About.class);
				context.startActivity(intent);
			}
		});
    }
    
    private void checkForUpdate()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        private App app = null;
    		private String versionName = null;
    		private String packageName = null;
	        protected void onPreExecute() 
	        {
        		try 
                {
        			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        		} 
                catch (NameNotFoundException e) 
                {
        			e.printStackTrace();
        		}
        		packageName = context.getPackageName();
	        }
	        protected Void doInBackground(Void... args) 
	        {
	        	try 
		        {
	        		app = WebServiceHelper.checkForNewerVersion(packageName, versionName);
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}        	
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	            if(connetionValid)
	            {
	            	if (app != null)
	            	{
	            		//show a dialog
            			AlertDialog.Builder builder = new AlertDialog.Builder(context);	            			
            			builder.setMessage(R.string.thereIsNewerVersionMessage);
            			builder.setCancelable(true);
            			builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() 
            			{
							public void onClick(DialogInterface arg0, int arg1) 
							{
								Intent intent = new Intent(context, ViewAppDetails.class);
		            			intent.putExtra("packageName", context.getPackageName());
		            			context.startActivity(intent);	
							}
						});
            			builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() 
            			{				
							public void onClick(DialogInterface arg0, int arg1) 
							{
								arg0.cancel();		
							}
						});
            			AlertDialog alert = builder.create();
            			alert.show(); 
	            	}
	            }      
	        }  
	    }
		new Worker().execute();
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
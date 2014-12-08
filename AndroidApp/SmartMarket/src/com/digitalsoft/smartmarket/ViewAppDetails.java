package com.digitalsoft.smartmarket;


import java.io.IOException;
import java.util.ArrayList;
import com.digitalsoft.smartmarket.EntityClasses.*;
import com.digitalsoft.smartmarket.Helpers.Downloader;
import com.digitalsoft.smartmarket.Helpers.Config;
import com.digitalsoft.smartmarket.Helpers.DownloadersListHelper;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.ScreenshotsAdapter;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ViewAppDetails extends Activity
{
	private TextView appName_tv;
	private TextView developerName_tv;
	private TextView descritpion_tv;
	private TextView version_tv;
	private TextView installs_tv;
	private TextView developerWebsite_tv;
	private TextView developerSupportEmail_tv;
	private TextView update_tv;
	private TextView size_tv;
	private ImageView icon_iv;
	private Gallery screenshots_gallery;
	private RatingBar averageRating_rb;
	private RatingBar yourRating_rb;
	private Button install_b; //install-cancel-uninstall button
	private Button update_b;
	private Button open_b;
	private ProgressDialog pd;
	private Activity context;
	private String packageName;
	private App app;
	private UserApp userApp;
	private Bitmap icon_bitmap;
	private ArrayList<Bitmap> screenshots_bitmaps;
	private Downloader downloader;
	private PackageInfo installedPackageInfo;
	@Override
	public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewappdetalis);
        
        context = this;
        downloader = null;
        appName_tv = (TextView) findViewById(R.id.appName_textView);
        developerName_tv = (TextView) findViewById(R.id.developerName_textView);
        descritpion_tv = (TextView) findViewById(R.id.description_textView);
        version_tv = (TextView) findViewById(R.id.version_textView);
        installs_tv = (TextView) findViewById(R.id.installs_textView);
        developerWebsite_tv = (TextView) findViewById(R.id.developerWebsite_textView);
        developerSupportEmail_tv = (TextView) findViewById(R.id.developerSupportEmail_textView);
        update_tv = (TextView) findViewById(R.id.versionDate_textView);
        size_tv = (TextView) findViewById(R.id.size_textView);
        icon_iv = (ImageView) findViewById(R.id.icon_imageView);
        screenshots_gallery = (Gallery) findViewById(R.id.screenshots_gallery);
        averageRating_rb = (RatingBar) findViewById(R.id.averageRating_ratingBar);
        yourRating_rb = (RatingBar) findViewById(R.id.yourRate_ratingBar);
        install_b = (Button) findViewById(R.id.install_button);
        update_b = (Button) findViewById(R.id.update_button);
   		update_b.setVisibility(Button.INVISIBLE);
        open_b = (Button) findViewById(R.id.open_button);
   		open_b.setVisibility(Button.INVISIBLE);
   		HeaderHelper.setHeader(context, true);
   
        packageName = GeneralHelper.getPackageNameFromURIs(context);
        if (packageName == null) //Check if the app has URI data and if not take the info from the intent
        {
        	packageName = getIntent().getExtras().getString("packageName");
        }
       	loadData();
       	
        
       	// check if the app is downloading the apk and the users left has the activity and visited it again
       	Downloader tempDownloader = DownloadersListHelper.searchInDownloadersList(packageName);
       	if (tempDownloader != null)
       	{
       		downloader = tempDownloader;
       		downloader.setContext(context);
       		install_b.setText(context.getResources().getText(R.string.cancel));
       	}
       	//end check       	

       	//check if the app is installed
       	installedPackageInfo = GeneralHelper.getPackageInfo(context, packageName);
       	if (installedPackageInfo != null && downloader == null)
       	{
       		install_b.setText(getResources().getText(R.string.uninstall));
       	}
       	//end check

       	
       	open_b.setOnClickListener(new OnClickListener()
       	{		
			public void onClick(View arg0) 
			{
				GeneralHelper.openApp(context, packageName);
			}
		});
        install_b.setOnClickListener(new OnClickListener() 
        {	
			public void onClick(View arg0) 
			{
				String signedInEmail = GeneralHelper.loadStringFromSharedPreferences(context, "email"); 
				if(install_b.getText() == context.getResources().getText(R.string.uninstall))
				{
					GeneralHelper.uninstallAPK(context, packageName);
					context.finish();
				}
				else if(install_b.getText() == context.getResources().getText(R.string.cancel)) // if the app is downloading
				{
					//remember that terminating the AsyncTask using downloader.cancel() doesn't invoke onPostExecute()
					//but it invokes onCancelled()
					//and it doesn't guarantee stopping the execution of doInBackground()
					//so we have to force stopping doInBackground() by using isCancelled() flag
					if (downloader != null)
					{
						downloader.cancel(true);
						downloader.onCancelledCaller(); // just to make sure that onCancelled() has been invoked
					}
				}
				else
				{
					if (signedInEmail != null && app != null)
					{
						if (Environment.getExternalStorageState().equals("mounted") && Integer.parseInt(app.version.minAndroidVersion) <= Build.VERSION.SDK_INT)
						{
							context.finish();
		        			downloader = new Downloader(context, signedInEmail, Config.appsPath + app.version.versionID + ".apk", Config.downloadedAPKsPath, app.name + "_" + app.version.versionNumber + ".apk", app, icon_bitmap);
		        			downloader.execute();
						}
						else if (!Environment.getExternalStorageState().equals("mounted"))
						{
							Toast.makeText(context, context.getResources().getText(R.string.noSDCardMessage), Toast.LENGTH_SHORT).show();
						}
						else if (Integer.parseInt(app.version.minAndroidVersion) > Build.VERSION.SDK_INT)
						{
							Toast.makeText(context, context.getResources().getText(R.string.theAppIsNotCompatibleMessage), Toast.LENGTH_SHORT).show();
						}
					}
					else if (signedInEmail == null)
					{
						Toast.makeText(context, context.getResources().getText(R.string.signInMessage), Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
        update_b.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View arg0) 
			{
				String signedInEmail = GeneralHelper.loadStringFromSharedPreferences(context, "email"); 
				if (signedInEmail != null && app != null)
				{
					if (Environment.getExternalStorageState().equals("mounted") && Integer.parseInt(app.version.minAndroidVersion) <= Build.VERSION.SDK_INT)
					{
						context.finish();
		        		downloader = new Downloader(context, signedInEmail, Config.appsPath + app.version.versionID + ".apk", Config.downloadedAPKsPath, app.name + "_" + app.version.versionNumber + ".apk", app, icon_bitmap);
		        		downloader.execute();
					}
					else if (!Environment.getExternalStorageState().equals("mounted"))
					{
						Toast.makeText(context, context.getResources().getText(R.string.noSDCardMessage), Toast.LENGTH_SHORT).show();
					}
					else if (Integer.parseInt(app.version.minAndroidVersion) > Build.VERSION.SDK_INT)
					{
						Toast.makeText(context, context.getResources().getText(R.string.theAppIsNotCompatibleMessage), Toast.LENGTH_SHORT).show();
					}
				}
				else if (signedInEmail == null)
				{
					Toast.makeText(context, context.getResources().getText(R.string.signInMessage), Toast.LENGTH_SHORT).show();
				}
			}
		});
        screenshots_gallery.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				Integer selectedItemPosition = arg2;
				Intent intent = new Intent(context, ViewImage.class);
				Screenshot screenshot = app.screenshots.get(selectedItemPosition);
				String screenshotID = screenshot.screenshotID.toString();
				String screenshotExtension = screenshot.extension;
				intent.putExtra("imageURL", Config.screenshotsPath + screenshotID + screenshotExtension);
				startActivity(intent);
			}
        });      
        yourRating_rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() 
        {
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) 
			{
				String signedInEmail = GeneralHelper.loadStringFromSharedPreferences(context, "email"); 
				if (arg2 && app != null) // rating from user
				{
					if (signedInEmail != null && (userApp != null || installedPackageInfo != null))
					{
						rateApp(arg0.getRating());
					}
					else if (signedInEmail == null)
					{
						Toast.makeText(context, context.getResources().getText(R.string.signInMessage), Toast.LENGTH_SHORT).show();
						yourRating_rb.setRating(0);
					}
					else if (userApp == null && installedPackageInfo == null)
					{
						Toast.makeText(context, context.getResources().getText(R.string.installAppMessage), Toast.LENGTH_SHORT).show();
						yourRating_rb.setRating(0);
					}
				}
			}
		});
        developerName_tv.setOnClickListener(new OnClickListener() 
        {	
			public void onClick(View arg0) 
			{
				if (app != null)
				{
					Intent intent = new Intent(context , ViewApps.class);
					intent.putExtra("developer", app.developerID.toString());
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
	        private String signedInEmail;
	        protected void onPreExecute() 
	        {
	        	signedInEmail = GeneralHelper.loadStringFromSharedPreferences(context, "email"); 
	        	pd = new ProgressDialog(context);
	        	pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	        protected Void doInBackground(Void... args) 
	        {
	        	try 
		        {
	        		app = WebServiceHelper.getApp(packageName);
	        		if (app != null)
	        		{
	        			icon_bitmap = GeneralHelper.loadBitmapFromUrl(Config.iconsPath + app.appID + ".png");
	        			if(signedInEmail != null)
	        			{
	        				userApp = WebServiceHelper.getUserApp(signedInEmail, app.packageName);
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
	            if(connetionValid)
	            {
	            	if (app != null)
	            	{
			            appName_tv.setText(app.name);
			            developerName_tv.setText(app.developer.name);
			            descritpion_tv.setSingleLine(false);
			            descritpion_tv.append("\n" + app.description);
			            version_tv.append(" " + app.version.versionNumber);
			            installs_tv.append(" " + app.installs.toString());
			            //note that append method cause a problem with the autolink property in a text view
			            developerWebsite_tv.setText(developerWebsite_tv.getText() + " " + app.developer.website);
			            developerSupportEmail_tv.setText(developerSupportEmail_tv.getText() + " " + app.developer.supportEmail);
			            update_tv.append(" " + app.version.date.toString());
			            size_tv.append(" " +  String.format("%.02f", app.version.size) + " " + context.getResources().getString(R.string.versionSizeUnit));         
			            icon_iv.setImageBitmap(icon_bitmap);
			            averageRating_rb.setRating(app.rate);
			            
			            if (downloader == null & installedPackageInfo != null && GeneralHelper.compareVersions(app.version.versionNumber, installedPackageInfo.versionName) > 0)
			            {
			            	update_b.setVisibility(Button.VISIBLE);
			            }
			            else
			            {
			            	update_b.setVisibility(Button.INVISIBLE);
			            	//just remove update button when open button is visible
			            	//because if we remove update button and don't remove open button
			            	//the install button will expand to take the space of the update button
			            	//and if we remove both open and update button the install button
			            	//will expand to take the whole area
			            	if (installedPackageInfo != null) 
			            	{
				            	LinearLayout footerLayout = (LinearLayout) findViewById(R.id.footer_Layout);
				            	footerLayout.removeView(update_b);
			            	}
			            }
			            if (installedPackageInfo != null)
			            {
			            	open_b.setVisibility(Button.VISIBLE);
			            }
			            else
		            	{
		            		open_b.setVisibility(Button.INVISIBLE);
		            	}
			            if (signedInEmail != null && userApp != null)
			            {
			            	yourRating_rb.setRating(userApp.rate);
			            }
			            loadScreenshots();
	            	}
	            	else
	            	{
	            		Toast.makeText(context, context.getResources().getText(R.string.appNotFoundMessage), Toast.LENGTH_SHORT).show();
	            		context.finish();
	            	}
	            }
	            else
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
    } 
	private void loadScreenshots()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        Boolean connetionValid = true;
	        protected Void doInBackground(Void... args) 
	        {
	        	try 
		        {
		            screenshots_bitmaps = new ArrayList<Bitmap>();
		            Bitmap screenshot_bitmap = null;
		            for(Screenshot screenshot : app.screenshots)
		            {
		            	screenshot_bitmap = GeneralHelper.loadBitmapFromUrl(Config.screenshotsPath + screenshot.screenshotID + screenshot.extension);
		            	screenshots_bitmaps.add(screenshot_bitmap);
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
	            if(connetionValid)
	            {
	            	int imageWidth = GeneralHelper.dipToPixel(context, Config.screenshotThumbnailWidth);
	            	int imageHeight = GeneralHelper.dipToPixel(context, Config.screenshotThumbnailHeight);
		            ScreenshotsAdapter imageAdapter = new ScreenshotsAdapter(context, screenshots_bitmaps, imageWidth, imageHeight);
		            screenshots_gallery.setAdapter(imageAdapter);
	            }
	            else
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
    } 
	private void rateApp(final Float rate)
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        private String signedInEmail;
	        protected void onPreExecute() 
	        {
	        	signedInEmail = GeneralHelper.loadStringFromSharedPreferences(context, "email"); 
	        	pd = new ProgressDialog(context);
	        	pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	        protected Void doInBackground(Void... args) 
	        {
	        	try 
		        {
	        		userApp = WebServiceHelper.setRateForApp(signedInEmail, packageName, rate.toString());
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
	            yourRating_rb.setRating(userApp.rate);
	            if (connetionValid)
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.ratingSuccessMessage), Toast.LENGTH_SHORT).show();
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
    	OptionsMenuHelper.onPrepareOptionsMenu(menu, true);  	
    	return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	OptionsMenuHelper.onOptionsItemSelected(context, item, app);
    	return super.onOptionsItemSelected(item);
    }
    // end option menu methods
}

package com.digitalsoft.smartmarket.Helpers;

import java.io.IOException;
import java.util.ArrayList;
import com.digitalsoft.smartmarket.R;
import com.digitalsoft.smartmarket.EntityClasses.App;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapterForNormalApps extends BaseAdapter
{  	  
    private LayoutInflater inflater;
    private ArrayList<App> apps;  
    private ArrayList<Bitmap> icons_bitmaps; 
   
    public CustomListViewAdapterForNormalApps(Activity context) 
    {  
        super();
        this.inflater = LayoutInflater.from(context);
        this.apps = new ArrayList<App>();
        this.icons_bitmaps = new ArrayList<Bitmap>();
    }  
    public int getCount() 
    {  
        return apps.size();  
    }   
    public Object getItem(int position) 
    {   
        return 0;  
    }  
    public long getItemId(int position) 
    {   
        return 0;  
    } 
    public void add(App app)
    {
        apps.add(app);
        icons_bitmaps.add(null);
        notifyDataSetChanged();
    }
    static class ViewHolder 
    {
        ImageView icon_iv;
    	TextView appName_tv;
        TextView developerName_tv;
        TextView installs_tv;
        Integer appID;
    }
    public View getView(int position, View convertView, ViewGroup parent) 
    {     
    	ViewHolder holder;
    	if (convertView == null)
    	{
    		convertView = inflater.inflate(R.layout.customlistviewfornormalapps, null); 
    		holder = new ViewHolder();
    		holder.icon_iv = (ImageView) convertView.findViewById(R.id.icon_imageView);  
    		holder.appName_tv = (TextView) convertView.findViewById(R.id.appName_textView);  
    		holder.developerName_tv = (TextView) convertView.findViewById(R.id.developerName_textView);  
    		holder.installs_tv = (TextView) convertView.findViewById(R.id.installs_textView);
	        convertView.setTag(holder);
	    }
    	else
    	{
    		holder = (ViewHolder) convertView.getTag();
    		holder.icon_iv.setImageBitmap(null); // to clear the image view to deny it from showing icon from previous app
    	}
        App app = apps.get(position); 
        holder.appID = app.appID;
        if (icons_bitmaps.get(position) == null) // check if the icon has been downloaded previously
        {
        	loadBitmap(Config.iconsPath + app.appID + ".png", app, holder, position); //load bitmap and set the icon_iv
        }
        else
        {
        	holder.icon_iv.setImageBitmap(icons_bitmaps.get(position));
        }
        holder.appName_tv.setText(app.name);  
        holder.developerName_tv.setText(app.developer.name);
        holder.installs_tv.setText(app.installs.toString()); 
        return convertView;
    } 
    private void loadBitmap(final String url,final App app, final ViewHolder holder, final int position)
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
			private Bitmap icon_bitmap;
	        protected Void doInBackground(Void... args) 
	        {
	        	try 
		        {
	        		icon_bitmap = GeneralHelper.loadBitmapFromUrl(url);
				} 
		        catch (IOException e) 
				{
		            icon_bitmap = null;
				}        	
	        	icons_bitmaps.set(position, icon_bitmap);
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	        	if (app.appID.equals(holder.appID)) //to fix a problem that some icons will bind in image view of another app
	        	{
	        		holder.icon_iv.setImageBitmap(icon_bitmap);
	        	}
	        } 
	    }
		new Worker().execute();
    } 
} 
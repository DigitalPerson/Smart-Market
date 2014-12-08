package com.digitalsoft.smartmarket.Helpers;

import java.util.ArrayList;
import com.digitalsoft.smartmarket.R;
import com.digitalsoft.smartmarket.EntityClasses.App;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapterForUpdates extends BaseAdapter
{  	  
    private LayoutInflater inflater;
    private ArrayList<PackageInfo> installedPackagesInfoThatNeedUpdate;    
    private ArrayList<App> appsThatNeedUpdate = null;
    private Activity context;
    
    public CustomListViewAdapterForUpdates(Activity context, ArrayList<PackageInfo> installedPackagesInfoThatNeedUpdate, ArrayList<App> Apps) 
    {  
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context); 
        this.installedPackagesInfoThatNeedUpdate =  installedPackagesInfoThatNeedUpdate;  
        this.appsThatNeedUpdate = Apps;
    }  
    public int getCount() 
    {  
        return installedPackagesInfoThatNeedUpdate.size();  
    }   
    public Object getItem(int position) 
    {   
        return 0;  
    }  
    public long getItemId(int position) 
    {   
        return 0;  
    } 
    static class ViewHolder 
    {
        ImageView icon_iv;
    	TextView appName_tv;
    	TextView currentVersionNumber_tv;
    	TextView newestVersionNumber_tv;
    }
    public View getView(int position, View convertView, ViewGroup parent) 
    {     
    	ViewHolder holder;
    	if (convertView == null)
    	{
    		convertView = inflater.inflate(R.layout.customlistviewforupdates, null);
    		holder = new ViewHolder();
            holder.icon_iv = (ImageView) convertView.findViewById(R.id.icon_imageView);
            holder.appName_tv = (TextView) convertView.findViewById(R.id.appName_textView);
            holder.currentVersionNumber_tv = (TextView) convertView.findViewById(R.id.currentVersion_textView); 
            holder.newestVersionNumber_tv = (TextView) convertView.findViewById(R.id.newestVersion_textView); 
            convertView.setTag(holder);
    	}
    	else
    	{
    		 holder = (ViewHolder) convertView.getTag();
    	}
        PackageInfo packageInfo = installedPackagesInfoThatNeedUpdate.get(position);
        App app = appsThatNeedUpdate.get(position);
    	holder.icon_iv.setImageDrawable(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
    	holder.appName_tv.setText(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());  
    	holder.currentVersionNumber_tv.setText(context.getResources().getString(R.string.currentVersion) + " " + packageInfo.versionName);
    	holder.newestVersionNumber_tv.setText(context.getResources().getString(R.string.newestVersion) + " " + app.version.versionNumber);
        return convertView;
    }  
} 
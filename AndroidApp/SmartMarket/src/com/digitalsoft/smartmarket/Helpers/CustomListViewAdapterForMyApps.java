package com.digitalsoft.smartmarket.Helpers;

import java.util.ArrayList;
import com.digitalsoft.smartmarket.R;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapterForMyApps extends BaseAdapter
{  	  
    private LayoutInflater inflater;
    private ArrayList<PackageInfo> installedPackagesInfo;     
    private Activity context;
    
    public CustomListViewAdapterForMyApps(Activity context, ArrayList<PackageInfo> installedPackagesInfo) 
    {  
        super();
        this.context = context;
        this.inflater = LayoutInflater.from(context); 
        this.installedPackagesInfo =  installedPackagesInfo;  
    }  
    public int getCount() 
    {  
        return installedPackagesInfo.size();  
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
        TextView versionNumber_tv;
    }
    public View getView(int position, View convertView, ViewGroup parent) 
    {     
    	ViewHolder holder;
    	if (convertView == null)
    	{
    		convertView = inflater.inflate(R.layout.customlistviewformyapps, null);
    		holder = new ViewHolder();
            holder.icon_iv = (ImageView) convertView.findViewById(R.id.icon_imageView);
            holder.appName_tv = (TextView) convertView.findViewById(R.id.appName_textView);
            holder.versionNumber_tv = (TextView) convertView.findViewById(R.id.currentVersion_textView); 
            convertView.setTag(holder);
    	}
    	else
    	{
    		 holder = (ViewHolder) convertView.getTag();
    	}
        PackageInfo packageInfo = installedPackagesInfo.get(position);
    	holder.icon_iv.setImageDrawable(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
    	holder.appName_tv.setText(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());  
    	holder.versionNumber_tv.setText(context.getResources().getString(R.string.currentVersion) + " " + packageInfo.versionName);
        return convertView;
    }  
} 
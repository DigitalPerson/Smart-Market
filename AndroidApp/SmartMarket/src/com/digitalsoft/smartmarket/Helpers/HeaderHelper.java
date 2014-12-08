package com.digitalsoft.smartmarket.Helpers;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.digitalsoft.smartmarket.Main;
import com.digitalsoft.smartmarket.R;
import com.digitalsoft.smartmarket.ViewAppDetails;
import com.digitalsoft.smartmarket.ViewAppTypes;
import com.digitalsoft.smartmarket.ViewApps;
import com.digitalsoft.smartmarket.ViewMyApps;
import com.digitalsoft.smartmarket.ViewUpdates;
import com.digitalsoft.smartmarket.ViewVoiceRecognitionMatches;

public class HeaderHelper 
{
	public static void setHeader(final Activity context, Boolean showHeaderMenu)
	{
		ImageButton search_b = (ImageButton) context.findViewById(R.id.search_imageButton);
		ImageButton voiceSearch_b = (ImageButton) context.findViewById(R.id.voiceSearch_imageButton);
    	final EditText search_et = (EditText) context.findViewById(R.id.search_editText); 
    	TextView main_tv = (TextView) context.findViewById(R.id.main_textView);
    	TextView categories_tv = (TextView) context.findViewById(R.id.categories_textView);
    	TextView suggestedApps_tv = (TextView) context.findViewById(R.id.suggestedApps_textView);
    	TextView featuredApps_tv = (TextView) context.findViewById(R.id.featuredApps_textView);
    	TextView myApps_tv = (TextView) context.findViewById(R.id.myApps_textView);
    	TextView updates_tv = (TextView) context.findViewById(R.id.updates_textView);
    	TextView newestApps_tv = (TextView) context.findViewById(R.id.newestApps_textView);
    	LinearLayout header_lo = (LinearLayout) context.findViewById(R.id.header_layout);
    	HorizontalScrollView headerMenu_hsv = (HorizontalScrollView) context.findViewById(R.id.headerMenu_horizontalScrollView);
    	
    	if(!showHeaderMenu)
    	{
    		header_lo.removeView(headerMenu_hsv);
    	}
    	main_tv.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, Main.class);
				context.startActivity(intent);				
			}
		});
    	
    	categories_tv.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewAppTypes.class);
				context.startActivity(intent);			
			}
		});
    	
    	suggestedApps_tv.setOnClickListener(new OnClickListener()
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
    	
    	featuredApps_tv.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewApps.class);
				intent.putExtra("featuredApps", "featuredApps");
				context.startActivity(intent);
			}
		});
    	
    	myApps_tv.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewMyApps.class);
				context.startActivity(intent);
			}
		});
    	
    	updates_tv.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewUpdates.class);
				context.startActivity(intent);
			}
		});
    	
    	newestApps_tv.setOnClickListener(new OnClickListener()
    	{
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(context, ViewApps.class);
				intent.putExtra("newestApps", "newestApps");
				context.startActivity(intent);
			}
		});
    	
        voiceSearch_b.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View arg0) 
			{
		        PackageManager pm = context.getPackageManager();
		        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		        if (activities.size() != 0) //check if there is a voice recognition service installed
		        {
		        	Intent intent = new Intent(context, ViewVoiceRecognitionMatches.class);
					context.startActivity(intent);
		        } 
		        else
		        {
		        	//show a dialog
        			AlertDialog.Builder builder = new AlertDialog.Builder(context);	            			
        			builder.setMessage(R.string.noVoiceRecognitionServiceMessage);
        			builder.setCancelable(true);
        			builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() 
        			{
						public void onClick(DialogInterface arg0, int arg1) 
						{
							Intent intent = new Intent(context, ViewAppDetails.class);
	            			intent.putExtra("packageName", Config.voiceRecognitionAppPackageName);
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
		});	
        search_b.setOnClickListener(new OnClickListener()
        {		
			public void onClick(View arg0) 
			{	
				String keyword = search_et.getText().toString();
				if (keyword.length() >= 3)
				{
					Intent intent = new Intent(context, ViewApps.class);
					intent.putExtra("keyword", keyword);
					context.startActivity(intent);
				}
				else
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterMoteCharactersMessage), Toast.LENGTH_SHORT).show();
				}
			}
		});
        search_et.setOnKeyListener(new OnKeyListener() 
        {	
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) 
			{
				if (arg2.getAction() == KeyEvent.ACTION_DOWN && arg1 == KeyEvent.KEYCODE_ENTER) 
				{
					String keyword = search_et.getText().toString();
					if (keyword.length() >= 3)
					{
						Intent intent = new Intent(context, ViewApps.class);
						intent.putExtra("keyword", keyword);
						context.startActivity(intent);
					}
					else
					{
						Toast.makeText(context, context.getResources().getText(R.string.enterMoteCharactersMessage), Toast.LENGTH_SHORT).show();
					}
					return true;
				}
				return false;		
			}
		});
	}
}

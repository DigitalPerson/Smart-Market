package com.digitalsoft.smartmarket.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.digitalsoft.smartmarket.About;
import com.digitalsoft.smartmarket.R;
import com.digitalsoft.smartmarket.Settings;
import com.digitalsoft.smartmarket.SignIn;
import com.digitalsoft.smartmarket.CreateNFCTag;
import com.digitalsoft.smartmarket.SignUp;
import com.digitalsoft.smartmarket.ViewImage;
import com.digitalsoft.smartmarket.EntityClasses.App;

public class OptionsMenuHelper 
{
	public static void onCreateOptionsMenu(Activity context, Menu menu)
	{
    	MenuInflater inflater = context.getMenuInflater();
    	inflater.inflate(R.menu.optionsmenu, menu);
	}
	public static void onMenuOpened(Activity context, Menu menu)
	{
		String signedInEmail = GeneralHelper.loadStringFromSharedPreferences(context, "email");
    	MenuItem item = menu.findItem(R.id.signIn_menuItem);
    	int signUpMenuItemIndex = 1; // remember to change this when changing the order of items in the menu
    	if (signedInEmail == null)
    	{ 		
    		item.setTitle(context.getResources().getString(R.string.signIn));
    		menu.getItem(signUpMenuItemIndex).setVisible(true); // enable sign up
    	}
    	else 
    	{ 		
    		item.setTitle(context.getResources().getString(R.string.signOut));
    		menu.getItem(signUpMenuItemIndex).setVisible(false); // disable sign up
    	}
	}
    public static void onPrepareOptionsMenu(Menu menu, Boolean showAppOtions)
    {
    	if (!showAppOtions)
    	{
    		menu.removeItem(R.id.createNFCTag_menuItem);
    		menu.removeItem(R.id.generateBarcode_menuItem);
    	}
    }
	public static void onOptionsItemSelected(Activity context, MenuItem item, App app)
	{
		if (item.getItemId() == R.id.signIn_menuItem)
    	{
    		if (item.getTitle().equals(context.getResources().getString(R.string.signIn)))
    		{
    			Intent intent = new Intent(context,SignIn.class);
				context.startActivity(intent);
    		}
    		else if (item.getTitle().equals(context.getResources().getString(R.string.signOut)))
    		{
    			GeneralHelper.clearStringFromSharedPreferences(context, "email");
				Toast.makeText(context, context.getResources().getText(R.string.successfulSignOutMessage), Toast.LENGTH_SHORT).show();
    		}
    	}
		else if (item.getItemId() == R.id.signUp_menuItem)
		{
			Intent intent = new Intent(context, SignUp.class);
			context.startActivity(intent);
		}
    	else if (item.getItemId() == R.id.about_menuItem)
    	{
    		Intent intent = new Intent(context, About.class);
			context.startActivity(intent);
    	}
    	else if (item.getItemId() == R.id.settings_menuItem)
    	{
    		Intent intent = new Intent(context, Settings.class);
			context.startActivity(intent);
    	}
    	else if (item.getItemId() == R.id.exit_menuItem)
    	{
    		Intent intent = new Intent();
    		intent.setAction(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    		context.startActivity(intent); 
    		context.finish();   
    	}
    	else if (item.getItemId() == R.id.createNFCTag_menuItem)
    	{
    		if (app != null)
    		{
    			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    			{
	    			Intent intent = new Intent(context, CreateNFCTag.class);
	    			intent.putExtra("packageName", app.packageName);
	    			context.startActivity(intent);
    			}
    			else
    			{
    				Toast.makeText(context, context.getResources().getText(R.string.thisFeatureRquiresICSMessage), Toast.LENGTH_SHORT).show();
    			}
    		}
    	}
    	else if (item.getItemId() == R.id.generateBarcode_menuItem)
    	{
    		if (app != null)
    		{
				Intent intent = new Intent(context, ViewImage.class);		
				intent.putExtra("imageURL", Config.qrCodesPath + app.appID + ".png");
				context.startActivity(intent);
    		}
    	}	
	}
}

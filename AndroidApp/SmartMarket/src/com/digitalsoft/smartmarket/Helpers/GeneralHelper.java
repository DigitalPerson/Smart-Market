package com.digitalsoft.smartmarket.Helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.TypedValue;

public class GeneralHelper 
{
	public static void setLocale(Activity context, String language)
	{
		if (language != null)
		{
			Locale locale = new Locale(language);
	        Locale.setDefault(locale);
	        Configuration config = new Configuration();
	        config.locale = locale;
	        context.getResources().updateConfiguration(config, null);
		}
	}
	public static Bitmap loadBitmapFromUrl(String url) throws IOException
    {
		Bitmap result = null;
		URLConnection conn;
		try
		{	
			conn = new URL(url).openConnection();
			conn.connect();
	        InputStream is = conn.getInputStream();
	        /* Buffered is always good for a performance plus. */
	        BufferedInputStream bis = new BufferedInputStream(is);
	        /* Decode url-data to a bitmap. */
	        result = BitmapFactory.decodeStream(bis);
	        bis.close();
	        is.close();
	        return result;
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
			return null;
		} 
    }
	public static int dipToPixel(Activity context, int dipValue)
	{
        Resources r = context.getResources();
        int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, 
        r.getDisplayMetrics());
        return px;
	}
	public static PackageInfo getPackageInfo(Activity context, String packageName)
    {
        PackageInfo result = null;
        PackageManager pm = context.getPackageManager(); 
        try 
        {
			result = pm.getPackageInfo(packageName, 0);
		} 
        catch (NameNotFoundException e) 
		{
        	result = null;
			e.printStackTrace();
		}
        return result;
    }
    public static ArrayList<PackageInfo> getInstalledPackagesInfo(Activity context, boolean getSysPackages)  /* false = no system packages */
    {
        ArrayList<PackageInfo> result = new ArrayList<PackageInfo>(); 
        List<PackageInfo> installedPackagesInfo = context.getPackageManager().getInstalledPackages(0);
        for(int i = 0; i < installedPackagesInfo.size(); i++) 
        {
            PackageInfo packageInfo = installedPackagesInfo.get(i);
            if (!getSysPackages && packageInfo.versionName == null) 
            {
                continue ;
            }
            else if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)//don't get system apps
            {
            	continue ;
            }
            result.add(packageInfo);
        }
        return result; 
    }
	public static void installAPK(Activity context, String source, String fileName)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		File file = new File(Environment.getExternalStorageDirectory() + source + "/" + fileName);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	public static void uninstallAPK(Activity context, String packageName)
	{
    	Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package", packageName, null));
    	context.startActivity(intent);
    }
	public static void openApp(Activity context, String packageName)
	{
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null); 
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> appsList = pm.queryIntentActivities(mainIntent, 0);
        String className = null;
        for (ResolveInfo info : appsList)
        {
        	if (info.activityInfo.packageName.equals(packageName))
        	{
        		className = info.activityInfo.name;
        		break;
        	}
        }
        if (className != null)
        {
	    	Intent intent = new Intent();
	    	intent.setClassName(packageName, className);
	    	context.startActivity(intent);
        }
	}
	public static void browseURL(Activity context, String url)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
		context.startActivity(intent);
	}
	public static String getPackageNameFromURIs(Activity context)
	{
		try
		{
			Uri data = context.getIntent().getData();
	    	if (!data.equals(null))
	    	{ 
	            int index;
				index = data.toString().indexOf("=");
				String packageName=data.toString().substring(index+1);
				return packageName;
	        } 
	    	return null;
		}
		catch (NullPointerException e) 
		{
			return null;
		}
	}
	public static String getMD5Hash(String str) 
	{
        MessageDigest digest;
        try 
        {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(str.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) 
            {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        { 
        	e.printStackTrace(); 
        }
        return null;
    }
	public static void saveStringInSharedPreferences(Activity context, String key, String value)
	{
		SharedPreferences prefs = context.getSharedPreferences("PREFS", 0);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(key, value);
	    editor.commit();    
	}
	public static String loadStringFromSharedPreferences(Activity context, String key)
	{
		SharedPreferences prefs = context.getSharedPreferences("PREFS", 0);
	    return prefs.getString(key, null);
	}
	public static void clearStringFromSharedPreferences(Activity context, String key)
	{
		SharedPreferences prefs = context.getSharedPreferences("PREFS", 0);
		prefs.edit().remove(key).commit();
	}
	public static Boolean loadBooleanFromDefaultSharedPreferences(Activity context, String key, Boolean defaultVlaue)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    return prefs.getBoolean(key, defaultVlaue);
	}
	public static String loadStringFromDefaultSharedPreferences(Activity context, String key, String defaultVlaue)
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
	    return prefs.getString(key, defaultVlaue);
	}
	public static Integer compareVersions(String v1, String v2)
	    {
	    	class VersionTokenizer 
	    	{
	    	    private final String _versionString;
	    	    private final int _length;
	    	    private int _position;
	    	    private int _number;
	    	    private String _suffix;
	    	    public VersionTokenizer(String versionString) 
	    	    {
	    	        _versionString = versionString;
	    	        _length = versionString.length();
	    	    }
	    	    public int getNumber() 
	    	    {
	    	        return _number;
	    	    }
	    	    public String getSuffix() 
	    	    {
	    	        return _suffix;
	    	    }
	    	    public boolean MoveNext() 
	    	    {
	    	        _number = 0;
	    	        _suffix = "";
	    	        // No more characters
	    	        if (_position >= _length)
	    	        {
	    	            return false;
	    	        }
	    	        while (_position < _length) 
	    	        {
	    	            char c = _versionString.charAt(_position);
	    	            if (c < '0' || c > '9') break;
	    	            _number = _number * 10 + (c - '0');
	    	            _position++;
	    	        }
	    	        int suffixStart = _position;
	    	        while (_position < _length) 
	    	        {
	    	            char c = _versionString.charAt(_position);
	    	            if (c == '.') break;
	    	            _position++;
	    	        }
	    	        _suffix = _versionString.substring(suffixStart, _position);
	    	        if (_position < _length) 
	    	        {	
	    	        	_position++;	
	    	        }
	    	        return true;
	    	    }
	    	}
	    	class VersionComprator
	    	{
	    	    public int compare(Object o1, Object o2) 
	    	    {
	    	        String version1 = (String) o1;
	    	        String version2 = (String) o2;
	    	        VersionTokenizer tokenizer1 = new VersionTokenizer(version1);
	    	        VersionTokenizer tokenizer2 = new VersionTokenizer(version2);
	    	        int number1 = 0, number2 = 0;
	    	        String suffix1 = "", suffix2 = "";
	    	        while (tokenizer1.MoveNext()) 
	    	        {
	    	            if (!tokenizer2.MoveNext()) 
	    	            {
	    	                do 
	    	                {
	    	                    number1 = tokenizer1.getNumber();
	    	                    suffix1 = tokenizer1.getSuffix();
	    	                    if (number1 != 0 || suffix1.length() != 0) 
	    	                    {
	    	                        // Version one is longer than number two, and non-zero
	    	                        return 1;
	    	                    }
	    	                }
	    	                while (tokenizer1.MoveNext());
	    	                // Version one is longer than version two, but zero
	    	                return 0;
	    	            }
	    	            number1 = tokenizer1.getNumber();
	    	            suffix1 = tokenizer1.getSuffix();
	    	            number2 = tokenizer2.getNumber();
	    	            suffix2 = tokenizer2.getSuffix();
	    	            if (number1 < number2) 
	    	            {
	    	                // Number one is less than number two
	    	                return -1;
	    	            }
	    	            if (number1 > number2) 
	    	            {
	    	                // Number one is greater than number two
	    	                return 1;
	    	            }
	    	            boolean empty1 = suffix1.length() == 0;
	    	            boolean empty2 = suffix2.length() == 0;
	    	            if (empty1 && empty2) continue; // No suffixes
//	    	            if (empty1) return 1; // First suffix is empty (1.2 > 1.2b)
//	    	            if (empty2) return -1; // Second suffix is empty (1.2a < 1.2)
	    	            // Lexical comparison of suffixes
	    	            int result = suffix1.compareTo(suffix2);
	    	            if (result != 0) return result;
	    	        }
	    	        if (tokenizer2.MoveNext()) 
	    	        {
	    	            do 
	    	            {
	    	                number2 = tokenizer2.getNumber();
	    	                suffix2 = tokenizer2.getSuffix();
	    	                if (number2 != 0 || suffix2.length() != 0) 
	    	                {
	    	                    // Version one is longer than version two, and non-zero
	    	                    return -1;
	    	                }
	    	            }
	    	            while (tokenizer2.MoveNext());
	    	            // Version two is longer than version one, but zero
	    	            return 0;
	    	        }
	    	        return 0;
	    	    }
	    	} 	
	    	VersionComprator cmp = new VersionComprator();
	    	return cmp.compare(v1, v2);
	    }	
}

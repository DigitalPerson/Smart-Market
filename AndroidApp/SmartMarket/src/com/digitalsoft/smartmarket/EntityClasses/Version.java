package com.digitalsoft.smartmarket.EntityClasses;

import java.sql.Date;

public class Version
{
	public Integer versionID;
	public Integer appID;
	public String versionNumber;
	public Float size;
	public String minAndroidVersion;
	public Date date;
		
	public Version()
	{
		this.versionID = null;
		this.appID = null;
		this.versionNumber = null;
		this.size = null;
		this.minAndroidVersion = null;
		this.date = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
		if (propertyName.equalsIgnoreCase("versionID"))
		{
			versionID = Integer.parseInt(propertyObject.toString());
		}
		else if (propertyName.equalsIgnoreCase("appID"))
    	{
            appID = Integer.parseInt(propertyObject.toString());
    	}
		else if (propertyName.equalsIgnoreCase("versionNumber"))
        {
            versionNumber = propertyObject.toString();
        }
		else if (propertyName.equalsIgnoreCase("size"))
        {
            size = Float.parseFloat(propertyObject.toString());
        }
		else if (propertyName.equalsIgnoreCase("minAndroidVersion"))
        {
            minAndroidVersion = propertyObject.toString();
        }
		else if (propertyName.equalsIgnoreCase("date"))
        {
        	String str1 = propertyObject.toString();
        	String str2= str1.substring(0, 10); //to remove the time from the date
            date = Date.valueOf(str2);
        }
	}
}

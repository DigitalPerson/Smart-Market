package com.digitalsoft.smartmarket.EntityClasses;

public class Screenshot
{
	public Integer screenshotID;
	public Integer appID;
	public String extension;
	
	public Screenshot()
	{
		this.screenshotID = null;
		this.appID = null;
		this.extension = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
		if (propertyName.equalsIgnoreCase("screenshotID"))
		{
			screenshotID = Integer.parseInt(propertyObject.toString());
		}
    	else if (propertyName.equalsIgnoreCase("appID"))
    	{
            appID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("extension"))
        {
            extension = propertyObject.toString();
        }
	}
}

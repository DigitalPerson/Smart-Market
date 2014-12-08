package com.digitalsoft.smartmarket.EntityClasses;

public class AppType
{
	public Integer appTypeID;
	public String name;

	public AppType() 
	{
		this.appTypeID = null;
		this.name = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
    	if (propertyName.equalsIgnoreCase("appTypeID"))
    	{
            appTypeID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("name"))
        {
            name = propertyObject.toString();
        }	
	}
}

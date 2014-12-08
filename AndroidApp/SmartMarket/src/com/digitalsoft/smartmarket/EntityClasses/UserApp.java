package com.digitalsoft.smartmarket.EntityClasses;

public class UserApp
{
	public Integer userID;
	public Integer appID;
	public Float rate;
	public Boolean favorite;
	
	public UserApp()
	{
		this.userID = null;
		this.appID = null;
		this.rate = null;
		this.favorite = null;
	}	
	public void setProperty(String propertyName, Object propertyObject) 
	{
		if (propertyName.equalsIgnoreCase("appID"))
		{
			appID = Integer.parseInt(propertyObject.toString());
		}
    	else if (propertyName.equalsIgnoreCase("userID"))
    	{
            userID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("rate"))
        {
            rate = Float.parseFloat(propertyObject.toString());
        }
    	else if (propertyName.equalsIgnoreCase("favorite"))
        {
            favorite = Boolean.parseBoolean(propertyObject.toString());
        }
	}
}

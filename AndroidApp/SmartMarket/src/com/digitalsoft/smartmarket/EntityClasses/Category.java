package com.digitalsoft.smartmarket.EntityClasses;

public class Category
{
	public Integer categoryID;
	public Integer appTypeID;
	public String name;
	
	public Category()
	{
		this.categoryID = null;
		this.appTypeID = null;
		this.name = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
    	if (propertyName.equalsIgnoreCase("categoryID"))
		{
			categoryID = Integer.parseInt(propertyObject.toString());
		}
    	else if (propertyName.equalsIgnoreCase("appTypeID"))
    	{
            appTypeID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("name"))
        {
            name = propertyObject.toString();
        }
	}
}

package com.digitalsoft.smartmarket.EntityClasses;

public class Country
{
	public Integer countryID;
	public String abbreviation;
	public String name;
	public String code;
	
	public Country()
	{
		this.countryID = null;
		this.abbreviation = null;
		this.name = null;
		this.code = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
		if (propertyName.equalsIgnoreCase("countryID"))
		{
			countryID = Integer.parseInt(propertyObject.toString());
		}
		else if (propertyName.equalsIgnoreCase("abbreviation"))
		{
			abbreviation = propertyObject.toString();
		}
		else if (propertyName.equalsIgnoreCase("name"))
		{
			name = propertyObject.toString();
		}
		else if (propertyName.equalsIgnoreCase("code"))
		{
			code = propertyObject.toString();
	    }	
	}
}

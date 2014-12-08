package com.digitalsoft.smartmarket.EntityClasses;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class User
{
	public Integer userID;
	public String firstName;
	public String lastName;
	public String gender;
	public Integer countryID;
	public String email;
	public String password;
	public Country country;
	public Boolean admin;
	
	public User()
	{
		this.userID = null;
		this.firstName = null;
		this.lastName = null;
		this.gender = null;
		this.countryID = null;
		this.email = null;
		this.password = null;
		this.country = null;
		this.admin = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
    	if (propertyName.equalsIgnoreCase("userID"))
    	{
            userID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("firstName"))
        {
            firstName = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("lastName"))
        {
            lastName = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("gende"))
        {
            gender = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("countryID"))
    	{
            countryID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("email"))
        {
            email = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("password"))
        {
            password = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("country"))
        {
        	SoapObject object = (SoapObject)propertyObject; 	
            Object property = null;
            PropertyInfo propertyInfo = null;
            country = new Country();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
		        	country.setProperty(propertyInfo.name, property);
	        	}
	        }
        }
    	else if (propertyName.equalsIgnoreCase("admin"))
        {
            admin = Boolean.parseBoolean(propertyObject.toString());
        }
	}
}

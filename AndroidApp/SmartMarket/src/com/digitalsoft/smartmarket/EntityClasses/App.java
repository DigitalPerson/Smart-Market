package com.digitalsoft.smartmarket.EntityClasses;

import java.util.ArrayList;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

public class App
{
	public Integer appID;
	public Integer categoryID;
	public Integer developerID;
	public String name;
	public String packageName;
	public Integer installs;
	public String description;
	public String whatIsNew;
	public Float rate;
	public Integer ratesCount;
	public Integer activatedVersionID;
	public Boolean published;
	public ArrayList<Screenshot> screenshots;
	public ArrayList<Version> versions;
	public Version version;
	public Category category; 
	public Developer developer;
	
	public App()
	{
		this.appID = null;
		this.categoryID = null;
		this.developerID = null;
		this.name = null;
		this.packageName = null;
		this.installs = null;
		this.description = null;
		this.whatIsNew = null;
		this.rate = null;
		this.ratesCount = null;
		this.activatedVersionID = null;
		this.published = null;
		this.screenshots = new ArrayList<Screenshot>();
		this.versions = new ArrayList<Version>();
		this.version = null;
		this.category = null;
		this.developer = null;
	}
	public void setProperty(String propertyName, Object propertyObject) 
	{
		if (propertyName.equalsIgnoreCase("appID"))
		{
			appID = Integer.parseInt(propertyObject.toString());
		}
    	else if (propertyName.equalsIgnoreCase("categoryID"))
		{
			categoryID = Integer.parseInt(propertyObject.toString());
		}
    	else if (propertyName.equalsIgnoreCase("developerID"))
    	{
    		developerID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("name"))
        {
            name = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("packageName"))
        {
            packageName = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("installs"))
        {
    		installs = Integer.parseInt(propertyObject.toString());
        }
    	else if (propertyName.equalsIgnoreCase("description"))
        {
            description = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("whatIsNew"))
        {
            whatIsNew = propertyObject.toString();
            if (whatIsNew.equals("anyType{}") )
            {
            	whatIsNew = "";
            }
        }
    	else if (propertyName.equalsIgnoreCase("rate"))
        {
            rate = Float.parseFloat(propertyObject.toString());
        }
    	else if (propertyName.equalsIgnoreCase("ratesCount"))
        {
    		ratesCount = Integer.parseInt(propertyObject.toString());
        }
    	else if (propertyName.equalsIgnoreCase("activatedVersionID"))
        {
            activatedVersionID = Integer.parseInt(propertyObject.toString());
        }
    	else if (propertyName.equalsIgnoreCase("published"))
        {
            published = Boolean.parseBoolean(propertyObject.toString());
        }
    	else if (propertyName.equalsIgnoreCase("screenshots"))
        {
            SoapObject objects = (SoapObject)propertyObject; 	
    		SoapObject object;   
            Object property = null;
            PropertyInfo propertyInfo = null;
            Screenshot screenshot;
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	screenshot = new Screenshot();
		        	for (int j = 0; j < object.getPropertyCount() ; j++)
		        	{
		        		property = object.getProperty(j);
		        		if (property != null)
		        		{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			        		screenshot.setProperty(propertyInfo.name, property);
		        		}
		        	}	        	
		        	screenshots.add(screenshot);
	        	}
	        }
        }
    	else if (propertyName.equalsIgnoreCase("versions"))
        {
            SoapObject objects = (SoapObject)propertyObject; 	
    		SoapObject object; 
            Object property = null;
            PropertyInfo propertyInfo = null;
            Version version;    
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	version = new Version();
		        	for (int j = 0; j < object.getPropertyCount() ; j++)
		        	{
		        		property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			        		version.setProperty(propertyInfo.name, property);
			        	}
		        	}	 
		        	versions.add(version);
	        	}
	        }
        }
    	else if (propertyName.equalsIgnoreCase("version"))
        {
        	SoapObject object = (SoapObject)propertyObject; 
            Object property = null;
            PropertyInfo propertyInfo = null;
            version = new Version();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
		        	version.setProperty(propertyInfo.name, property);
	        	}
	        }
        }
    	else if (propertyName.equalsIgnoreCase("category"))
        {
        	SoapObject object = (SoapObject)propertyObject; 
            Object property = null;
            PropertyInfo propertyInfo = null;
            category = new Category();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
		        	category.setProperty(propertyInfo.name, property);
	        	}
	        }
        }
    	else if (propertyName.equalsIgnoreCase("developer"))
        {
        	SoapObject object = (SoapObject)propertyObject; 	
            Object property = null;
            PropertyInfo propertyInfo = null;
            developer = new Developer();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
		        	developer.setProperty(propertyInfo.name, property);
	        	}
	        }
        }
	}
}

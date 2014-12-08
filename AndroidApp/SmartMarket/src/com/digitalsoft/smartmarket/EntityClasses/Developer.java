package com.digitalsoft.smartmarket.EntityClasses;

public class Developer
{
	public Integer developerID;
	public String name;
	public String website;
	public String phone;
	public String supportEmail;
	
	public Developer()
	{
		this.developerID = null;
		this.name = null;
		this.website = null;
		this.phone = null;
		this.supportEmail = null;
	}	
	public void setProperty(String propertyName, Object propertyObject) 
	{
    	if (propertyName.equalsIgnoreCase("developerID"))
    	{
            developerID = Integer.parseInt(propertyObject.toString());
    	}
    	else if (propertyName.equalsIgnoreCase("name"))
        {
            name = propertyObject.toString();
        }
    	else if (propertyName.equalsIgnoreCase("website"))
        {
            website = propertyObject.toString();
            if (website.equals("anyType{}") )
            {
            	website = "";
            }
        }
    	else if (propertyName.equalsIgnoreCase("phone"))
        {
            phone = propertyObject.toString();
            if (phone.equals("anyType{}") )
            {
            	phone = "";
            }
        }
    	else if (propertyName.equalsIgnoreCase("supportEmail"))
        {
            supportEmail = propertyObject.toString();
            if (supportEmail.equals("anyType{}") )
            {
            	supportEmail = "";
            }
        }
	}
}

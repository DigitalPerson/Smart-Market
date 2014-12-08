package com.digitalsoft.smartmarket.Helpers;

import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.digitalsoft.smartmarket.EntityClasses.*;

public class WebServiceHelper 
{	
	public static ArrayList<AppType> getAppTypes() throws IOException
	{
		String methodName = "GetAppTypes";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<AppType> appTypes = new ArrayList<AppType>();
		AppType appType;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	appType = new AppType();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	appType.setProperty(propertyInfo.name, property);
		            	}
		            }
		            appTypes.add(appType);
	        	}
	        }
		}
        return appTypes;
	}
	public static ArrayList<Category> getCategories(String appTypeName) throws IOException
	{
		String methodName = "GetCategories";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("appTypeName", appTypeName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}	
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		Category category;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	category = new Category();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	category.setProperty(propertyInfo.name, property);
		            	}
		            }
		            categories.add(category);
	        	}
	        }
		}
        return categories;
	}
	public static ArrayList<App> searchForApp(String keyword, String startIndex, String limit) throws IOException
	{
		String methodName = "SearchForApp";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("keyword", keyword);
        request.addProperty("startIndex", startIndex);
        request.addProperty("limit", limit);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<App> apps = new ArrayList<App>();
		App app;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	app = new App();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	app.setProperty(propertyInfo.name, property);
		            	}
		            }
		            apps.add(app);
	        	}
	        }
		}
        return apps;
	}
	public static ArrayList<App> getAppsForSpecificDeveloper(String developerID, String startIndex, String limit) throws IOException
	{
		String methodName = "GetAppsForSpecificDeveloper";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("developerID", developerID);
        request.addProperty("startIndex", startIndex);
        request.addProperty("limit", limit);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<App> apps = new ArrayList<App>();
		App app;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	app = new App();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	app.setProperty(propertyInfo.name, property);
		            	}
		            }
		            apps.add(app);
	        	}
	        }
		}
        return apps;
	}
	public static ArrayList<App> getApps(String appTypeName, String categoryName, String startIndex, String limit) throws IOException
	{
		String methodName = "GetApps";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("appTypeName", appTypeName);
        request.addProperty("categoryName", categoryName);
        request.addProperty("startIndex", startIndex);
        request.addProperty("limit", limit);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<App> apps = new ArrayList<App>();
		App app;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	app = new App();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	app.setProperty(propertyInfo.name, property);
		            	}
		            }
		            apps.add(app);
	        	}
	        }
		}
        return apps;
	}
	public static ArrayList<App> getNewestApps(String startIndex, String limit) throws IOException
	{
		String methodName = "GetNewestApps";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("startIndex", startIndex);
        request.addProperty("limit", limit);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<App> apps = new ArrayList<App>();
		App app;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	app = new App();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	app.setProperty(propertyInfo.name, property);
		            	}
		            }
		            apps.add(app);
	        	}
	        }
		}
        return apps;
	}
	public static ArrayList<App> getSuggestedApps(String email, String startIndex, String limit) throws IOException
	{
		String methodName = "GetSuggestedApps";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("email", email);
        request.addProperty("startIndex", startIndex);
        request.addProperty("limit", limit);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<App> apps = new ArrayList<App>();
		App app;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	app = new App();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	app.setProperty(propertyInfo.name, property);
		            	}
		            }
		            apps.add(app);
	        	}
	        }
		}
        return apps;
	}
	public static ArrayList<App> getFeaturedApps(String startIndex, String limit) throws IOException
	{
		String methodName = "GetFeaturedApps";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("startIndex", startIndex);
        request.addProperty("limit", limit);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<App> apps = new ArrayList<App>();
		App app;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	app = new App();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	app.setProperty(propertyInfo.name, property);
		            	}
		            }
		            apps.add(app);
	        	}
	        }
		}
        return apps;
	}
	public static App getApp(String packageName) throws IOException
	{
		String methodName = "GetApp";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("packageName", packageName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        App app = null;
        if (object != null)
        {
        	app = new App();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	app.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return app;
	}
	public static App checkForNewerVersion(String packageName, String installedVersion) throws IOException
	{
		String methodName = "CheckForNewerVersion";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("packageName", packageName);
        request.addProperty("installedVersion", installedVersion);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        App app = null;
        if (object != null)
        {
        	app = new App();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	app.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return app;
	}
	public static App checkForOlderVersion(String packageName, String installedVersion) throws IOException
	{
		String methodName = "CheckForOlderVersion";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("packageName", packageName);
        request.addProperty("installedVersion", installedVersion);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        App app = null;
        if (object != null)
        {
        	app = new App();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	app.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return app;
	}
	public static UserApp getUserApp(String email, String packageName) throws IOException
	{
		String methodName = "GetUserApp";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("email", email);
        request.addProperty("packageName", packageName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        UserApp userApp = null;
        if (object != null)
        {
        	userApp = new UserApp();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	userApp.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return userApp;
	}
	public static UserApp setRateForApp(String email, String packageName, String rate) throws IOException
	{
		String methodName = "SetRateForApp";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("email", email);
        request.addProperty("packageName", packageName);
        request.addProperty("rate", rate);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        UserApp userApp = null;
        if (object != null)
        {
        	userApp = new UserApp();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	userApp.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return userApp;
	}
	public static UserApp downloadApp(String email, String packageName) throws IOException
	{
		String methodName = "DownloadApp";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("email", email);
        request.addProperty("packageName", packageName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        UserApp userApp = null;
        if (object != null)
        {
        	userApp = new UserApp();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	userApp.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return userApp;
	}
	public static Boolean addUser(String firstName, String lastName, String gender, String countryName, String email, String password) throws IOException
	{
		String methodName = "AddUser";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("firstName", firstName);
        request.addProperty("lastName", lastName);
        request.addProperty("gender", gender);
        request.addProperty("countryName", countryName);
        request.addProperty("email", email);
        request.addProperty("password", password);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapPrimitive primitive = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			primitive = (SoapPrimitive)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Boolean result = false;
        if (primitive != null)
        {
        	result = Boolean.parseBoolean(primitive.toString());
        }
        return result;
	}
	public static User getUser(String email) throws IOException
	{
		String methodName = "GetUser";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        request.addProperty("email", email);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);   
        SoapObject object = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			object = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}
        Object property = null;
    	PropertyInfo propertyInfo = null;
        User user = null;
        if (object != null)
        {
        	user = new User();
	        for (int i = 0; i < object.getPropertyCount(); i++)
	        {
	        	property = object.getProperty(i);
	        	if(property != null)
	        	{
		        	propertyInfo = new PropertyInfo();
	            	object.getPropertyInfo(i, propertyInfo);
	            	user.setProperty(propertyInfo.name, property);
            	}
	        }
        }
        return user;
	}
	public static ArrayList<Country> getCountries() throws IOException
	{
		String methodName = "GetCountries";
		String soapAction = Config.webServiceNamespace + "/" + methodName;
        SoapObject request = new SoapObject(Config.webServiceNamespace, methodName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(Config.webServiceUrl);
        SoapObject objects = null;
		try 
		{
			httpTransport.call(soapAction, envelope);
			objects = (SoapObject)envelope.getResponse();
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		}	
		SoapObject object;
        Object property = null;
    	PropertyInfo propertyInfo = null;
		ArrayList<Country> countries = new ArrayList<Country>();
		Country country;
		if (objects != null)
		{
	        for (int i = 0; i < objects.getPropertyCount(); i++)
	        {
	        	object = (SoapObject)objects.getProperty(i);
	        	if (object != null)
	        	{
		        	country = new Country();
		            for (int j = 0; j < object.getPropertyCount(); j++)
		            {
		            	property = object.getProperty(j);
			        	if(property != null)
			        	{
				        	propertyInfo = new PropertyInfo();
			            	object.getPropertyInfo(j, propertyInfo);
			            	country.setProperty(propertyInfo.name, property);
		            	}
		            }
		            countries.add(country);
	        	}
	        }
		}
        return countries;
	}
}

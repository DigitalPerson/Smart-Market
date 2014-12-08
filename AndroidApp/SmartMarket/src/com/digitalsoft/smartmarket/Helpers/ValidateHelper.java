package com.digitalsoft.smartmarket.Helpers;

public class ValidateHelper 
{
	public static Boolean isValidFirstName(String firstName)
	{
		Boolean result;
		if (firstName.length() >= 3)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
	public static Boolean isValidLastName(String lastName)
	{
		Boolean result;
		if (lastName.length() >= 2)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
	public static Boolean isValidEmail(String email)
	{
		Boolean result = false;
		//result = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(); // this required android 2.2+
		result = email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
		return result;
	}
	public static Boolean isValidPassword(String password)
	{
		Boolean result;
		if (password.length() >= 8)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
	public static Boolean areSamePassword(String password1, String password2)
	{
		Boolean result;
		if (password1.equals(password2))
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
}

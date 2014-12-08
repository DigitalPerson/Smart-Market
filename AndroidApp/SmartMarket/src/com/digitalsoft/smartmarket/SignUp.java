package com.digitalsoft.smartmarket;

import java.io.IOException;
import java.util.ArrayList;
import com.digitalsoft.smartmarket.EntityClasses.Country;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.ValidateHelper;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class SignUp extends Activity 
{
	private Activity context;
	private Button signUp_b;
	private EditText firstName_et;
	private EditText lastName_et;
	private EditText email_et;
	private EditText password_et;
	private EditText confirmPassword_et;
	private Spinner gender_spn;
	private Spinner country_spn;
	private String firstName;
	private String lastName;
	private String gender;
	private String countryName;
	private String email;
	private String password;
	private String confirmPassword;
	private ArrayList<Country> countries;
	private ProgressDialog pd; 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        context = this;   
        
        signUp_b = (Button) findViewById(R.id.signUp_button);
        firstName_et = (EditText) findViewById(R.id.firstName_editText);
        lastName_et = (EditText) findViewById(R.id.lastName_editText);
        email_et = (EditText) findViewById(R.id.email_editText);
        password_et = (EditText) findViewById(R.id.password_editText);
        confirmPassword_et = (EditText) findViewById(R.id.confirmPassword_editText);
        gender_spn = (Spinner) findViewById(R.id.gender_spinner);
        country_spn = (Spinner) findViewById(R.id.country_spinner);
        HeaderHelper.setHeader(context, true);
        
        ArrayAdapter<CharSequence> gendersAdapter = ArrayAdapter.createFromResource(context, R.array.genders, android.R.layout.simple_spinner_item);
        gendersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spn.setAdapter(gendersAdapter);
        
        loadCountries();
        
        signUp_b.setOnClickListener(new OnClickListener() 
        {			
			public void onClick(View arg0) 
			{
				firstName = firstName_et.getText().toString();
				lastName = lastName_et.getText().toString();
				if(gender_spn.getSelectedItemPosition() == 0)
				{
					gender = "m";
				}
				else
				{
					gender = "f";
				}		
				countryName = country_spn.getSelectedItem().toString();
				email = email_et.getText().toString();
				password = password_et.getText().toString();
				confirmPassword = confirmPassword_et.getText().toString();
				if (
						ValidateHelper.isValidFirstName(firstName)
						&& ValidateHelper.isValidLastName(lastName)
						&& ValidateHelper.isValidEmail(email) 
						&& ValidateHelper.isValidPassword(password) 
						&& ValidateHelper.areSamePassword(password, confirmPassword)
				   )
				{
					signUp();
				}
				else if (!ValidateHelper.isValidFirstName(firstName))
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterCorrectFirstNameMessage), Toast.LENGTH_SHORT).show();
				}
				else if (!ValidateHelper.isValidLastName(lastName))
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterCorrectLastnameMessage), Toast.LENGTH_SHORT).show();
				}
				else if (!ValidateHelper.isValidEmail(email))
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterCorrectEmailMessage), Toast.LENGTH_SHORT).show();
				}
				else if (!ValidateHelper.isValidPassword(password))
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterCorrectPasswordMessage), Toast.LENGTH_SHORT).show();
				}
				else if (!ValidateHelper.areSamePassword(password, confirmPassword))
				{
					Toast.makeText(context, context.getResources().getText(R.string.passwordsDonttMatchMessage), Toast.LENGTH_SHORT).show();
				}
			}
		});
        
    }
    
    private void loadCountries()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        protected void onPreExecute() 
	        {
	        	pd = new ProgressDialog(context);
	        	pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	            signUp_b.setEnabled(false);
	        }
	        protected Void doInBackground(Void... args) 
	        {        	
	        	try 
		        {  		
	        		countries = WebServiceHelper.getCountries();
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	            pd.dismiss(); 
	            if(connetionValid)
	            {
		            ArrayList<String> countriesNamesList = new ArrayList<String>();
		            for (Country country : countries) 
		            {
		            	countriesNamesList.add(country.name);
		            }       	            
		            ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, countriesNamesList);
		            countriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		            country_spn.setAdapter(countriesAdapter);
		            signUp_b.setEnabled(true);
	            }
	            else
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
    }  
    
    private void signUp()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        private Boolean addUserSucceed = false;;
	        protected void onPreExecute() 
	        {
	        	pd = new ProgressDialog(context); 
	        	pd.setMessage(getResources().getText(R.string.waitMessage));
	            pd.setCanceledOnTouchOutside(false);
	            pd.show();
	        }
	        protected Void doInBackground(Void... args) 
	        {        	
	        	try 
		        {
	        		addUserSucceed = WebServiceHelper.addUser(firstName, lastName, gender, countryName, email, GeneralHelper.getMD5Hash(password));
				} 
		        catch (IOException e) 
				{
		            connetionValid = false;
				}
		        return null;
	        }
	        protected void onPostExecute(Void unused) 
	        {
	            pd.dismiss(); 
	            if(connetionValid)
	            {
	            	if (addUserSucceed)
	            	{
	            		Toast.makeText(context, context.getResources().getText(R.string.successfulSignUpMessage), Toast.LENGTH_SHORT).show();
	            		context.finish();
	            	}
	            	else
	            	{
	            		Toast.makeText(context, context.getResources().getText(R.string.emailAlreadySignedUpMessage), Toast.LENGTH_SHORT).show();
	            	}
	            }
	            else
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            }
	        }  
	    }
		new Worker().execute();
    }  
    
    
	// to prevent a force close problem when the screen rotate during progress dialog working
	@Override
    protected void onDestroy()
    {
        super.onDestroy();
        pd.dismiss();
    }
	
    // options menu methods must be included in all activities that has the options menu enabled
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	OptionsMenuHelper.onCreateOptionsMenu(context, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) 
    {
    	OptionsMenuHelper.onMenuOpened(context, menu);
    	return super.onMenuOpened(featureId, menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
    	OptionsMenuHelper.onPrepareOptionsMenu(menu, false);  	
    	return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	OptionsMenuHelper.onOptionsItemSelected(context, item, null);
    	return super.onOptionsItemSelected(item);
    }
    // end option menu methods
}
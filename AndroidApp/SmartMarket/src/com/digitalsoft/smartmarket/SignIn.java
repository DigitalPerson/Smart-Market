package com.digitalsoft.smartmarket;

import java.io.IOException;
import com.digitalsoft.smartmarket.EntityClasses.User;
import com.digitalsoft.smartmarket.Helpers.Config;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import com.digitalsoft.smartmarket.Helpers.ValidateHelper;
import com.digitalsoft.smartmarket.Helpers.WebServiceHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignIn extends Activity 
{
	private Activity context;
	private Button signIn_b;
	private EditText email_et;
	private EditText password_et;
	private TextView forgotPasswod_tv;
	private TextView signUp_tv;
	private User user;
	private String email;
	private String password;
	private ProgressDialog pd;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        context = this;   
        
        signIn_b = (Button) findViewById(R.id.signIn_button);
        email_et = (EditText) findViewById(R.id.email_editText);
        password_et = (EditText) findViewById(R.id.password_editText);
        forgotPasswod_tv = (TextView) findViewById(R.id.forgotPassword_textView);
        signUp_tv = (TextView) findViewById(R.id.signUp_textView);
        HeaderHelper.setHeader(context, true);
        
        signIn_b.setOnClickListener(new OnClickListener() 
        {			
			public void onClick(View arg0) 
			{
				email = email_et.getText().toString();
				password = password_et.getText().toString();
				if (ValidateHelper.isValidEmail(email) && ValidateHelper.isValidPassword(password))
				{
					tryToSignIn();
				}
				else if (!ValidateHelper.isValidEmail(email))
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterCorrectEmailMessage), Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(context, context.getResources().getText(R.string.enterCorrectPasswordMessage), Toast.LENGTH_SHORT).show();
				}
			}
		});
        
        forgotPasswod_tv.setOnClickListener(new OnClickListener()
        {	
			public void onClick(View arg0) 
			{		
				GeneralHelper.browseURL(context, Config.forgotPasswordUrl);
			}
		});
        
        signUp_tv.setOnClickListener(new OnClickListener()
        {	
			public void onClick(View arg0) 
			{		
				Intent intent = new Intent(context, SignUp.class);
				startActivity(intent);
			}
		});
    }
    private void tryToSignIn()
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
	        }
	        protected Void doInBackground(Void... args) 
	        {        	
	        	try 
		        {
	        		user = WebServiceHelper.getUser(email);
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
	            	if (user == null)
	            	{
	            		Toast.makeText(context, context.getResources().getText(R.string.invalidSigInInfoMessage), Toast.LENGTH_SHORT).show();
	            	}
	            	else
	            	{
	            		if (user.password.equalsIgnoreCase(GeneralHelper.getMD5Hash(password)))
	            		{
	            			GeneralHelper.saveStringInSharedPreferences(context, "email", user.email);
	            			Toast.makeText(context, context.getResources().getText(R.string.successfulSignInMessage), Toast.LENGTH_SHORT).show();
	            			context.finish();
	            		}
	            		else
	            		{
	            			Toast.makeText(context, context.getResources().getText(R.string.invalidSigInInfoMessage), Toast.LENGTH_SHORT).show();
	            		}
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
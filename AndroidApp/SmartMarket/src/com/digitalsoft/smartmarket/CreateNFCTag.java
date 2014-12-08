package com.digitalsoft.smartmarket;

import java.io.IOException;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateNFCTag extends Activity 
{
	private Button writeNFCTag_b;
	private TextView wrtiteNFCTagStatus_tv;
	private Activity context;
	private NfcAdapter mAdapter;
	private Boolean mInWriteMode;
	private String packageName = null;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnfctag);
        
        context = this;       
        writeNFCTag_b = (Button) findViewById(R.id.writeNFCTag_button);
        wrtiteNFCTagStatus_tv = (TextView) findViewById(R.id.writeNFCTagStatus_textView);           	
        HeaderHelper.setHeader(context, true);
        
        packageName = getIntent().getExtras().getString("packageName");
     
        // grab our NFC Adapter
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mAdapter == null)
        {
        	writeNFCTag_b.setEnabled(false);
        	displayMessage(getResources().getText(R.string.noNFCSupportMessage));
        }
        
        writeNFCTag_b.setOnClickListener(new OnClickListener() 
        {			
			public void onClick(View arg0) 
			{
				displayMessage(getResources().getText(R.string.touchNFCTagToWriteMessage));
				enableWriteMode();
			}
		});         
    }
	@Override
	protected void onPause() 
	{
		super.onPause();
		disableWriteMode();
	}
	//Called when our blank tag is scanned executing the PendingIntent
	@Override
    public void onNewIntent(Intent intent) 
	{
		if(mInWriteMode) 
		{
			mInWriteMode = false;			
			// write to newly scanned tag
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(tag);
		}
    }	
	//Force this Activity to get NFC events first
	private void enableWriteMode() 
	{
		mInWriteMode = true;	
		// set up a PendingIntent to open the app when a tag is scanned
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[] { tagDetected };    
        mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}
	private void disableWriteMode() 
	{
		mAdapter.disableForegroundDispatch(this);
	}
	//Format a tag and write our NDEF message
	private boolean writeTag(Tag tag) 
	{
		// record to launch market if app is not installed and launch the app if it's installed
		NdefRecord appRecord = NdefRecord.createApplicationRecord(packageName);
		NdefMessage message = new NdefMessage(new NdefRecord[] {appRecord});    
		try 
		{
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) 
			{
				ndef.connect();
				if (!ndef.isWritable()) 
				{
					displayMessage(getResources().getText(R.string.readOnlyNFCTageMessage));
					return false;
				}	
				// work out how much space we need for the data
				int size = message.toByteArray().length;
				if (ndef.getMaxSize() < size) 
				{
					displayMessage(getResources().getText(R.string.noSpaceInNFCTageMessage));
					return false;
				}
				ndef.writeNdefMessage(message);
				displayMessage(getResources().getText(R.string.nfcTagWrittenSuccessfullyMessage));
				return true;
			} 
			else 
			{
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) 
				{
					try 
					{
						format.connect();
						format.format(message);
						displayMessage(getResources().getText(R.string.nfcTagFormatedSuccessfullyMessage));
						return true;
					} 
					catch (IOException e) 
					{
						displayMessage(getResources().getText(R.string.unableToFormatNFCTageMessage));
						return false;
					}
				} 
				else 
				{
					displayMessage(getResources().getText(R.string.nfcTagDoesntSupportNDEFMessage));
					return false;
				}
			}
		} catch (Exception e) 
		{
			displayMessage(getResources().getText(R.string.failedToWriteNFCTagMessage));
		}
        return false;
    }
	private void displayMessage(CharSequence charSequence) 
	{
		wrtiteNFCTagStatus_tv.setText(charSequence);
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
package com.digitalsoft.smartmarket;

import java.util.ArrayList;
import com.digitalsoft.smartmarket.Helpers.HeaderHelper;
import com.digitalsoft.smartmarket.Helpers.OptionsMenuHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewVoiceRecognitionMatches extends Activity 
{
	private ListView lv;
	private Activity context;
	private int REQUEST_CODE = 1234;
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewvoicerecognitionmatches);
        
        context = this;
        lv = (ListView) findViewById(R.id.voiceRecognitionMatches_listView); 
        HeaderHelper.setHeader(context, true);
        
        //start the voice recognition screen
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.speakMessage));
        context.startActivityForResult(intent, REQUEST_CODE);
        
        
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				Integer selectedItemPosition = arg2;
				String keyword = lv.getItemAtPosition(selectedItemPosition).toString();
				Intent intent = new Intent(context, ViewApps.class);
				intent.putExtra("keyword", keyword);
				startActivity(intent);	
			}
		});
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        if (requestCode == REQUEST_CODE)
        {
        	if (resultCode == RESULT_OK)
        	{
        		ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            	lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
        	}
        	else
        	{
        		context.finish();
        	}
        }
		super.onActivityResult(requestCode, resultCode, data);
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

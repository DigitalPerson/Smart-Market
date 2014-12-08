package com.digitalsoft.smartmarket;

import java.io.IOException;
import com.digitalsoft.smartmarket.Helpers.GeneralHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ViewImage extends Activity 
{
	private ImageView iv;
	private Activity context;
	private String imageURL;
	private ProgressDialog pd;
	@Override
	public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.viewimage);
        
        context = this;
        imageURL = getIntent().getExtras().getString("imageURL");
        iv = (ImageView)findViewById(R.id.screenshhot_imageView);
        iv.setClickable(true);
        
        loadData();        
        
        
        iv.setOnClickListener(new OnClickListener() 
        {
			public void onClick(View arg0) 
			{
				finish();			
			}
        });  	
    }
	private void loadData()
    {
		class Worker extends AsyncTask<Void, Void, Void> 
	    {
	        private Boolean connetionValid = true;
	        private Bitmap bm;
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
		            bm = GeneralHelper.loadBitmapFromUrl(imageURL);
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
	            if (connetionValid)
	            {
	            	iv.setImageBitmap(bm);
	            }
	            else
	            {
	            	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
	            	context.finish();
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

}

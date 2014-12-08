package com.digitalsoft.smartmarket.Helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.digitalsoft.smartmarket.R;
import com.digitalsoft.smartmarket.ViewAppDetails;
import com.digitalsoft.smartmarket.EntityClasses.App;

public class Downloader extends AsyncTask<Void, Integer, Void> 
{
	private NotificationManager notificationManager;
	private Notification notification;
	private int progress = 0;
	private int prevProgress = 0;
	private long currentTimeMillis;
	private long prevTimeMillis;
    private Boolean connetionValid = true;
    private Activity context;
    public App app;
    private Bitmap icon_bitmap;
    private String destination;
    private String fileName;
    private String urlString;
    private String signedInEmail;
    public Downloader(Activity context, String signedInEmail,String urlString, String destination, String fileName, App app, Bitmap icon_bitmap)
    {
    	this.context = context;
    	this.urlString = urlString;
    	this.app = app;
    	this.icon_bitmap = icon_bitmap;
    	this.destination = destination;
    	this.fileName = fileName;
    	this.signedInEmail = signedInEmail;
    }
    public void setContext(Activity context)
    {
    	this.context = context;
    }
    protected void onPreExecute() 
    {
    	//set the status notification
        Intent intent = new Intent(context, ViewAppDetails.class); // the intent that opens when the user clicks the notification status
        intent.putExtra("packageName", app.packageName);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, app.appID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = android.R.drawable.stat_sys_download;
        notification.tickerText = context.getResources().getText(R.string.downloadingMessage);
        currentTimeMillis = System.currentTimeMillis();
        prevTimeMillis = currentTimeMillis;
        notification.when = currentTimeMillis;     
        notification.contentView = new RemoteViews(context.getPackageName(), R.layout.downloadingstatusnotification);
        notification.contentIntent = pendingIntent;
        notification.contentView.setTextViewText(R.id.status_text, app.name);
        notification.contentView.setImageViewBitmap(R.id.status_icon, icon_bitmap);
        notification.contentView.setProgressBar(R.id.status_progress, 100, 0, false);        
        notificationManager.notify(app.appID, notification);
    	//end set status notification
        DownloadersListHelper.addToDownloadersList(this);
    }
    protected Void doInBackground(Void... voids) 
    {
        try 
        {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();
            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            String sdCardPath = Environment.getExternalStorageDirectory().toString();
            File destinationFullPath = new File(sdCardPath + destination);
            File fileFullPath = new File (destinationFullPath + "/" + fileName);
            if (!destinationFullPath.exists())
            {
            	destinationFullPath.mkdirs();
            }
            OutputStream output = new FileOutputStream(fileFullPath.toString());
            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) 
            {
	            if (isCancelled()) // if the downloader.cancel() method is invoked stop the download
	            {
		            output.flush();
		            output.close();
		            input.close();
		            fileFullPath.delete();
	            	return null;
	            }
                total += count;
                // publishing the progress....
                prevProgress = progress;
                currentTimeMillis = System.currentTimeMillis();
                progress = (int) (total * 100 / fileLength);
                if (prevProgress != progress && currentTimeMillis - prevTimeMillis > 1000) // update the progress bar only once every 1 second
                {
                	publishProgress(progress);
                	prevTimeMillis = currentTimeMillis;
                } 
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            WebServiceHelper.downloadApp(signedInEmail, app.packageName);
        } 
        catch (IOException e) 
        {
        	e.printStackTrace();
        	connetionValid = false;
        }
        return null;
    }
    protected void onProgressUpdate(Integer... progress) 
    {
    	notification.contentView.setProgressBar(R.id.status_progress, 100, progress[0], false);
    	notificationManager.notify(app.appID, notification);
    }
    protected void onPostExecute(Void unused) //invoked when successfully finished download or ended by connection error
    {
		context.finish();
		DownloadersListHelper.removeFromDownloadersList(app.packageName);
    	notificationManager.cancel(app.appID);
        if (connetionValid)
        {
        	GeneralHelper.installAPK(context, Config.downloadedAPKsPath, app.name + "_" + app.version.versionNumber + ".apk");
        }
        else
        {
        	Toast.makeText(context, context.getResources().getText(R.string.noConnectionMessage), Toast.LENGTH_SHORT).show();
        }
    }
    // in early versions of android onCancelled() doesn't invoked when canceling AsyncTask 
    //so we call this function to force call invoking onCancelled()
    public void onCancelledCaller() 
    {
    	onCancelled(null);
    }
    protected void onCancelled(Void unused) //invoked when the download canceled by the user using downloader.cancel()
    {
		context.finish();
		DownloadersListHelper.removeFromDownloadersList(app.packageName);
    	notificationManager.cancel(app.appID);
    }
}
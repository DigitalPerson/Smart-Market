package com.digitalsoft.smartmarket.Helpers;

import java.util.ArrayList;

public class DownloadersListHelper 
{
	public static ArrayList<Downloader> downloadersList = new ArrayList<Downloader>();
	public static Downloader searchInDownloadersList(String packageName)
	{
		for (Downloader downloader : downloadersList)
		{
			if(downloader.app.packageName.equals(packageName))
			{
				return downloader;
			}
		}
		return null;		
	}
	public static void addToDownloadersList(Downloader downloader)
	{
		downloadersList.add(downloader);
	}
	public static void removeFromDownloadersList(String packageName)
	{
		for (int i = 0; i < downloadersList.size(); i++)
		{
			if (downloadersList.get(i).app.packageName.equals(packageName))
			{
				if (downloadersList.get(i) != null)
				{
					downloadersList.get(i).cancel(true); // just to make sure that its cancelled
				}
				downloadersList.remove(i);
			}
		}
	}
}

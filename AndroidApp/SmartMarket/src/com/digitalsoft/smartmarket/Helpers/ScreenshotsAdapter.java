package com.digitalsoft.smartmarket.Helpers;

import java.util.ArrayList;
import com.digitalsoft.smartmarket.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ScreenshotsAdapter extends BaseAdapter 
{
    int galleryItemBackground;
    private Context context;
    private ArrayList<Bitmap> bitmaps;
    private int imageWidth;
    private int imageHeight;
    public ScreenshotsAdapter(Context context, ArrayList<Bitmap> bitmaps, int imageWidth, int imageHeight) 
    {
       this.context = context;
       this.bitmaps = bitmaps;
       this.imageWidth = imageWidth;
       this.imageHeight = imageHeight;
       TypedArray attr = context.obtainStyledAttributes(R.styleable.screenshotsGallery);
       galleryItemBackground = attr.getResourceId(R.styleable.screenshotsGallery_android_galleryItemBackground, 0);
       attr.recycle();
    }
    public int getCount() 
    {
        return bitmaps.size();
    }
    public Object getItem(int position) 
    {
        return position;
    }
    public long getItemId(int position) 
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) 
    {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmaps.get(position));
        imageView.setLayoutParams(new Gallery.LayoutParams(imageWidth, imageHeight));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(galleryItemBackground);
        return imageView;
    }
}
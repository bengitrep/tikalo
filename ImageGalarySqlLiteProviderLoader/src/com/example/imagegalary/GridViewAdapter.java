package com.example.imagegalary;


import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {

	private Context context;
	private int layoutResourceId;
	private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

	public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	ViewHolder holder;
	ImageItem item;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		///ViewHolder holder;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.text);
			holder.image = (ImageView) row.findViewById(R.id.image);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		//ImageItem item = data.get(position);
		item = data.get(position);

		holder.imageTitle.setText(item.getTitle());

		String url = "http://image.tmdb.org/t/p/w300" + item.getPosterName();

		Picasso.with(context) //
		.load(url) //
		.placeholder(android.R.drawable.ic_lock_idle_charging) //
		.error(android.R.drawable.ic_menu_search) //
		.fit()
		.tag(context) //	
		.into(holder.image);


		// ImageItem item = data.get(position);
		// holder.imageTitle.setText(item.getTitle());
		// holder.image.setImageBitmap(item.getImage());
		return row;
	}
	
	/*
	 .into(new Target() {
			@Override
			public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
				//Set it in the ImageView
				holder.image.setImageBitmap(bitmap); 
				item.setImage(bitmap);
			}

			@Override
			public void onBitmapFailed(Drawable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPrepareLoad(Drawable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	 */

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}

	public ArrayList<ImageItem> getData() {
		return data;
	}

	public void setData(ArrayList<ImageItem> data) {
		this.data = data;
	}
}
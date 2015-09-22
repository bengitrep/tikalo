package com.example.imagegalary;



import com.squareup.picasso.Picasso;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);

		Bitmap bitmap = getIntent().getParcelableExtra("image");
		String title = getIntent().getStringExtra("title");
		String poster = getIntent().getStringExtra("poster");

		String year = getIntent().getStringExtra("year");   
		String minutes = getIntent().getStringExtra("minutes");
		String description = getIntent().getStringExtra("description");


		ImageView imageView = (ImageView) findViewById(R.id.image);
		//imageView.setImageBitmap(bitmap);

		String url = "http://image.tmdb.org/t/p/w300" + poster;


		Picasso.with(this) //
		.load(url) //
		.placeholder(android.R.drawable.ic_lock_idle_charging) //
		.error(android.R.drawable.ic_menu_search) //
		
		.tag(this) //	
		.into(imageView);



		TextView titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.setText(title);

		TextView yearTextView = (TextView) findViewById(R.id.year);
		yearTextView.setText(year);

		TextView minutesTextView = (TextView) findViewById(R.id.min);
		minutesTextView.setText(minutes + "min");

		TextView descriptionTextView = (TextView) findViewById(R.id.description);
		descriptionTextView.setText(description);        

	}
}

package com.example.imagegalary;


import java.util.ArrayList;
import java.util.List;

import com.example.imagegalary.GridViewAdapter.ViewHolder;


import provider.MovieProvider;


import sqllite.MoviesDataSourceDAO;
import sqllite.MySQLiteHelper;
import task.SearchPicTask;

 
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;




public class MainActivity extends ActionBarActivity implements
LoaderCallbacks<Cursor> {

	private GridView gridView;
	private GridViewAdapter gridAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getLoaderManager().initLoader(0, null, this);

		gridView = (GridView) findViewById(R.id.gridView);


		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// View row = v;
		        // ViewHolder holder;
	            // holder = (ViewHolder) row.getTag();

				ImageItem item = (ImageItem) parent.getItemAtPosition(position);

				//Create intent
				Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
				
				intent.putExtra("image", item.getImage());
				intent.putExtra("title", item.getTitle());
				intent.putExtra("poster", item.getPosterName());			

				intent.putExtra("year", item.getYear());
				intent.putExtra("minutes", item.getMinutes());
				intent.putExtra("description", item.getDescription());

				
				//Start details activity
				startActivity(intent);
			}
		});
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Log.v("ben", "onCreateLoader");
		String URL = MovieProvider.URL;
		Uri movies = Uri.parse(URL);
		return new CursorLoader(this, movies, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		Log.v("ben", "onLoadFinished");

		ArrayList<ImageItem> data = getAllMovies(cursor);

		if(data!=null && data.size()>4){
			Log.v("ben", "from data base");
			loadData(data);
		}
		else{
			Log.v("ben", "from task");
			ArrayList<String> names =  new ArrayList<String>();
			names.add("spiderman");
			names.add("superman");
			names.add("batman");
			names.add("titanic");
			names.add("antman");
			new SearchPicTask(this, gridAdapter, names).execute();			
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub

	}


	public void loadData(ArrayList<ImageItem> data){
		gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, data);
		gridView.setAdapter(gridAdapter);
	}


	public void onComplete(ArrayList<ImageItem> data){
		for (int i = 0; i < data.size(); i++) {
			addMovieToDB(data.get(i));			
		}
		loadData(data);
	}

	public ArrayList<ImageItem> getAllMovies(Cursor cursor) {
		ArrayList<ImageItem> movies = new ArrayList<ImageItem>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ImageItem imgItm = cursorToMovie(cursor);
			movies.add(imgItm);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return movies;
	}

	private ImageItem cursorToMovie(Cursor cursor) {
		ImageItem movie = new ImageItem();
		movie.setId_db(cursor.getLong(0));
		movie.setId(cursor.getString(1));
		movie.setTitle(cursor.getString(2));
		movie.setPosterName(cursor.getString(3));

		movie.setYear(cursor.getString(4));
		movie.setMinutes(cursor.getString(5));
		movie.setDescription(cursor.getString(6));

		
		return movie;
	}

	//can see changes after re entering the app
	public void addMovieToDB(ImageItem imgItm) {

		String URL = MovieProvider.URL;
		Uri movie = Uri.parse(URL);

		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_MOVIE_ID, imgItm.getId());
		values.put(MySQLiteHelper.COLUMN_TITLE, imgItm.getTitle());
		values.put(MySQLiteHelper.COLUMN_POSTER, imgItm.getPosterName());

		values.put(MySQLiteHelper.COLUMN_YEAR, imgItm.getYear());
		values.put(MySQLiteHelper.COLUMN_MINUTES, imgItm.getMinutes());
		values.put(MySQLiteHelper.COLUMN_DESCRIPTION, imgItm.getDescription());

		Uri uri = getContentResolver().insert(movie, values);

		Toast.makeText(getBaseContext(), 
				"DB: " + uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
	}


	/**
	 * Prepare some dummy data for gridview
	 */
	private ArrayList<ImageItem> getData() {

		final ArrayList<ImageItem> imageItems = new ArrayList<>();
		TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
		//for (int i = 0; i < imgs.length(); i++) {
		for (int i = 0; i < 5; i++) { 
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
			imageItems.add(new ImageItem(bitmap, "Image#" + i));
		}
		return imageItems;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
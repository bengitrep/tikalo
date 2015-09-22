package provider;

import java.util.HashMap;

import sqllite.MoviesDataSourceDAO;
import sqllite.MySQLiteHelper;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MovieProvider extends ContentProvider {
	
	// PROVIDER_NAME need to be also in the manifest
    //	  <provider android:name="provider.CommentProvider" 
    //       	android:authorities="com.tikal.provider.CommentProvider">
    //    </provider>  
    //</application>
	public static final String PROVIDER_NAME = "com.tikal.provider.MovieProvider"; //***
	public static final String URL = "content://" + PROVIDER_NAME + "/movies"; //***
	public static final Uri CONTENT_URI = Uri.parse(URL);


	// integer values used in content URI
	static final int MOVIES = 1; //***

	
	// maps content URI "patterns" to the integer values that were set above
	static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES); //***
	}


	private MoviesDataSourceDAO datasource;

	@Override
	public boolean onCreate() {
		Context context = getContext();
		datasource = new MoviesDataSourceDAO(context);
		datasource.open();
		if(datasource.getDatabase() == null)
			return false;
		else
			return true;	
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		Log.v("ben",  "MovieProvider, query()");

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteHelper.TABLE_MOVIES);

		switch (uriMatcher.match(uri)) {
		case MOVIES: //***
			//queryBuilder.setProjectionMap(BirthMap); // not in use
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		Cursor cursor = queryBuilder.query(datasource.getDatabase(), projection, selection, 
				selectionArgs, null, null, sortOrder);
		
		//cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long row = datasource.getDatabase().insert(MySQLiteHelper.TABLE_MOVIES, "", values);

		// If record is added successfully
		if(row > 0) {
			Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
			getContext().getContentResolver().notifyChange(newUri, null);
			return newUri;
		}
		throw new SQLException("Fail to add a new record into " + uri);
	}
	
	/*
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		

		switch (uriMatcher.match(uri)) {
		case FRIENDS:
			datasource.getAllComments();
			queryBuilder.setProjectionMap(BirthMap);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TABLE_NAME);
		queryBuilder.setProjectionMap(BirthMap);
		Cursor cursor = queryBuilder.query(database, projection, selection, 
				selectionArgs, null, null, sortOrder);
		

		//cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}
	*/
	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)){
		// Get all friend-birthday records 
		case MOVIES:
			return "vnd.android.cursor.dir/vnd.example.friends";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}



	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/*
	

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;

		switch (uriMatcher.match(uri)){
		case FRIENDS:
			count = database.update(TABLE_NAME, values, selection, selectionArgs);
			break;
		case FRIENDS_ID:
			count = database.update(TABLE_NAME, values, ID + 
					" = " + uri.getLastPathSegment() + 
					(!TextUtils.isEmpty(selection) ? " AND (" +
							selection + ')' : ""), selectionArgs);
			break;
		default: 
			throw new IllegalArgumentException("Unsupported URI " + uri );
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;

		switch (uriMatcher.match(uri)){
		case FRIENDS:
			// delete all the records of the table
			count = database.delete(TABLE_NAME, selection, selectionArgs);
			break;
		case FRIENDS_ID:
			String id = uri.getLastPathSegment();	//gets the id
			count = database.delete( TABLE_NAME, ID +  " = " + id + 
					(!TextUtils.isEmpty(selection) ? " AND (" + 
							selection + ')' : ""), selectionArgs);
			break;
		default: 
			throw new IllegalArgumentException("Unsupported URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;


	}
	*/




}

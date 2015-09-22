package sqllite;


import java.util.ArrayList;
import java.util.List;

import com.example.imagegalary.ImageItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MoviesDataSourceDAO {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_MOVIE_ID,			
			MySQLiteHelper.COLUMN_TITLE,
			MySQLiteHelper.COLUMN_POSTER,
			MySQLiteHelper.COLUMN_YEAR,
			MySQLiteHelper.COLUMN_MINUTES,
			MySQLiteHelper.COLUMN_DESCRIPTION};

	public MoviesDataSourceDAO(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
} 

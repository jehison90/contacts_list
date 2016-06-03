package co.com.imaginamos.test.contactslist.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper { 
	
	public static String DATABASE_NAME = "contacts.db";
	
	public static final String TABLE_CONTACTS = "contacts";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_IMAGE = "image";
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
	      + TABLE_CONTACTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " 
	                             + COLUMN_NAME + " TEXT NOT NULL, "
	                             + COLUMN_PHONE + " TEXT NOT NULL,"
	                             + COLUMN_EMAIL + " TEXT NOT NULL,"
	                             + COLUMN_IMAGE + " BLOB"
	      + ")";
	
	public DatabaseOpenHelper(Context context) {
	    super(context, DATABASE_NAME, null, 1);
	  }


	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		// TODO Auto-generated method stub		
		dataBase.execSQL(DATABASE_CREATE);
		Log.d("create db", DATABASE_CREATE);
		
		String insertContact = "INSERT INTO " + TABLE_CONTACTS + " VALUES (" + 1 + ", 'JEHISON PRADA', '3173991889', 'jehison90@gmail.com', '')";
		String insertContact1 = "INSERT INTO " + TABLE_CONTACTS + " VALUES (" + 2 + ", 'PEPE PEREZ', '0317667889', 'pepe@hotmail.com', '')";
		String insertContact2 = "INSERT INTO " + TABLE_CONTACTS + " VALUES (" + 3 + ", 'CARLA CARL', '3455654', 'carl@carl.com.co', '')";
		dataBase.execSQL(insertContact);
		dataBase.execSQL(insertContact1);
		dataBase.execSQL(insertContact2);
		
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}

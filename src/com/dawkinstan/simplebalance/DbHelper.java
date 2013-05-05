package com.dawkinstan.simplebalance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper
{
	
	private static final String LOGCAT = "DBLog";
	
	public static final String DATABASE_NAME = "info.db";
	private static int DATABASE_VERSION = 1;
	
	public static final String TABLE_DATA = "data";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_BEFORE_PURCHASE = "beforePurchase";
	public static final String COLUMN_AFTER_PURCHASE = "afterPurchase";
	public static final String COLUMN_WHERE_SPENT = "whereSpent";
	public static final String COLUMN_AMOUNT_SPENT = "amountSpent";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_IS_DEPOSIT = "isDeposit";
	
	private static final String TABLE_CREATE = "" +
			"CREATE TABLE "+TABLE_DATA+" ("+
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_AMOUNT_SPENT + " REAL, " +
			COLUMN_BEFORE_PURCHASE + " REAL, " + 
			COLUMN_AFTER_PURCHASE + " REAL, " +
			COLUMN_WHERE_SPENT + " TEXT, " +
			COLUMN_TYPE + " INTEGER" + "," +
			COLUMN_TITLE + " TEXT" + ", " +
			COLUMN_IS_DEPOSIT + " INTEGER" + ")";
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_CREATE);
//		Log.(LOGCAT, "Table has been created");
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		onCreate(db);
	}
	
}
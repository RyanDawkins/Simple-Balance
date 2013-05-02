package com.dawkinstan.simplebalance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "simplebalance.db";
    private static final String TABLE_BALANCE = "balancelog";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TRANSACTION_TYPE = "transactionType";
    private static final String COLUMN_PURCHASE_TEXT = "purchaseText";
    private static final String COLUMN_AMOUNT_TRANSACTED = "amountedTransacted";
    
    private static final String TABLE_CREATE = "create table "+TABLE_BALANCE+" ("
    		+COLUMN_ID+" integer primary key autoincrement, " 
    		+COLUMN_TRANSACTION_TYPE+"integer, "
    		+COLUMN_PURCHASE_TEXT+" text not null, "
    		+COLUMN_AMOUNT_TRANSACTED+" real"
    		+");";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(DbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
		        + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BALANCE);
		onCreate(db);
	}
}
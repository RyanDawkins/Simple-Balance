package com.dawkinstan.simplebalance;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataSource {
	
	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase database;
	private static final String[] ALL_COLUMNS = {
		DbHelper.COLUMN_ID,
		DbHelper.COLUMN_IS_DEPOSIT,
		DbHelper.COLUMN_TITLE,
		DbHelper.COLUMN_TYPE,
		DbHelper.COLUMN_WHERE_SPENT,
		DbHelper.COLUMN_AMOUNT_SPENT,
		DbHelper.COLUMN_BEFORE_PURCHASE,
		DbHelper.COLUMN_AFTER_PURCHASE
	};
	
	public DataSource(Context context)
	{
		dbHelper = new DbHelper(context);
	}
	
	public void open()
	{
		database = dbHelper.getWritableDatabase();
	}
	public void close()
	{
		dbHelper.close();
	}
	
	public Transaction create(Transaction t)
	{
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_TITLE, t.getTitle());
		values.put(DbHelper.COLUMN_TYPE, t.getType());
		values.put(DbHelper.COLUMN_AMOUNT_SPENT, t.getAmountSpent());
		values.put(DbHelper.COLUMN_BEFORE_PURCHASE, t.getBeforePurchase());
		values.put(DbHelper.COLUMN_AFTER_PURCHASE, t.getAfterPurchase());
		int isDeposit = t.isDeposit() ? 1 : 0;
		values.put(DbHelper.COLUMN_IS_DEPOSIT, isDeposit);
		values.put(DbHelper.COLUMN_WHERE_SPENT, t.getWhereSpent());
		long insertId = database.insert(DbHelper.TABLE_DATA, null, values);
		t.setId(insertId);
		Log.i("Info", "inserted row with id: "+insertId);
		return t;
	}
	
	public List<Transaction> findAll()
	{
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		Cursor cursor = database.query(DbHelper.TABLE_DATA, ALL_COLUMNS,
				null, null, null, null, null);
		
		if(cursor.getCount() > 0)
		{
			while(cursor.moveToNext())
			{
				Transaction transaction = new Transaction(
						cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_BEFORE_PURCHASE)),
						cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_AFTER_PURCHASE)),
						cursor.getDouble(cursor.getColumnIndex(DbHelper.COLUMN_AMOUNT_SPENT)));
				transaction.setId(cursor.getInt(cursor.getColumnIndex(DbHelper.COLUMN_ID)));
				transaction.setTitle(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TITLE)));
				transaction.setType(cursor.getInt(cursor.getColumnIndex(DbHelper.COLUMN_TYPE)));
				transaction.setWhereSpent(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_WHERE_SPENT)));
				transactions.add(transaction);
			}
		}
		return transactions;
	}
	
}
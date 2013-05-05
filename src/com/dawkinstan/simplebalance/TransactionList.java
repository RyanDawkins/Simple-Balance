package com.dawkinstan.simplebalance;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockListActivity;

public class TransactionList extends SherlockListActivity implements ActionBar.TabListener{

	private ActionBar actionbar;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.actionbar = getSupportActionBar();
        this.actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        ActionBar.Tab balanceTab = this.actionbar.newTab().setText("Balance").setTabListener(this);
        ActionBar.Tab transactionsTab = this.actionbar.newTab().setText("Transactions").setTabListener(this);
        this.actionbar.addTab(balanceTab, false);
        this.actionbar.addTab(transactionsTab, true);
		
		Intent i = getIntent();
		
		DataSource ds = new DataSource(this);
		ds.open();
		List<Transaction> transactions = ds.findAll();
		ds.close();
		
		ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this, android.R.layout.simple_list_item_1, transactions.toArray(new Transaction[transactions.size()]));
		setListAdapter(adapter);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(tab.getText().equals("Balance"))
		{
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		}
		else if(tab.getText().equals("Transactions"))
		{
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}

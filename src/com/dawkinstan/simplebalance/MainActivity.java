/*
 * This file is part of MoneyTracker.

    MoneyTracker is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    MoneyTracker is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with MoneyTracker.  If not, see <http://www.gnu.org/licenses/>.
    
 */

package com.dawkinstan.simplebalance;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.DecimalFormat;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	
	private static boolean isAddition = false;
	private static String PREFS_NAME = "MoneyTracker_Settings";
	private double beforePurchase;
	private double amountSpent;
	private double afterPurchase;
	private DataSource ds;
	private int type;
	private ActionBar actionbar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.actionbar = getSupportActionBar();
        this.actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        this.actionbar.addTab(this.actionbar.newTab().setText("Balance").setTabListener(this));
        this.actionbar.addTab(this.actionbar.newTab().setText("Transactions").setTabListener(this));
        
        this.type = -1;
        
        // Setting display where money is shown as unclickable
        EditText cmDisplay;
        cmDisplay = (EditText) findViewById(R.id.currentAmount);
        cmDisplay.setClickable(false);
        cmDisplay.setFocusable(false);

        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        new Spr();

        // Getting preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	boolean firstLoad = settings.getBoolean("firstLoad", true);
    	ds = new DataSource(this);

        if(firstLoad)
        {
        	// First load intent
        	Intent intent = new Intent(this, MTSettings.class);
        	try{
        		startActivityForResult(intent, 1);
        		SharedPreferences.Editor edit = settings.edit();
        		edit.putBoolean("firstLoad", false);
        		edit.commit();
        	} // Catches exception of the activity not existing
        	catch (Exception e){
        	}
        } else {
        	// Formats string to have money symbol in front.
        	double currentMoney;
        	try
        	{
        		currentMoney = Double.parseDouble(settings.getString("ammount", "0.00").replace("$|,", ""));
        	}
        	catch(Exception e)
        	{
        		currentMoney = 0;
        	}
        	this.setNewAmountInView(currentMoney);
        	this.beforePurchase = currentMoney;
        }
        
        // Sets button as unclickable
        Button updateButton = (Button) findViewById(R.id.button_update);
        updateButton.setEnabled(false);
        
        // Listener to update the view of the ammount of money changed
        updateButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view){
        		onUpdate(view);
        	}
        	
        });
        
    }
        
    // Handles add/subtraction radio button clicks
    public void onRadioClick(View view) {
    	
    	boolean isChecked = ((RadioButton) view).isChecked();
    	
    	Button updateButton = (Button) findViewById(R.id.button_update);
    	updateButton.setEnabled(true);
    	
    	// Handles which operator to use
    	if(isChecked){
    		EditText whereBought = (EditText) findViewById(R.id.where_purchased);
    		int id = view.getId();
    		if(id == R.id.add_money)
    		{
    				isAddition = true;
    				whereBought.setHint(R.string.hint_where_given);
    		}
    		else if(id == R.id.sub_money)
    		{
    				isAddition = false;
    				whereBought.setHint(R.string.hint_where_purchased);
    		}
    	} else {}     	
    }
    
    // Function to handle updates for the new value
    public void onUpdate(View view){
		
    	double currentMoney;
    	double moneyDifference;
    	EditText cm = (EditText) findViewById(R.id.currentAmount);
    	EditText md = (EditText) findViewById(R.id.amount_change);
    	currentMoney = Double.parseDouble(cm.getText().toString().substring(1));
    	
    	try{
    		moneyDifference = Double.parseDouble(md.getText().toString());
    	} catch(Exception e){ // Catches no string exception
    		return;
    	}
    	
    	this.amountSpent = moneyDifference;
    	
    	// Handles math
    	if(isAddition){
			// Get moneys or die trying
			afterPurchase = currentMoney + moneyDifference;
		} else{
			// Loose moneys :(
			afterPurchase = currentMoney - moneyDifference;
			
		}
		
    	// Casts new afterPurchase value to a string
		String moneyString = "" + afterPurchase;
		
		// Stores money in sharedPreferences
		this.storeMoneyInPreferences(moneyString);
		
		// Sets value in view
		this.setNewAmountInView(afterPurchase);
    	
		md.setText("");
		
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	if(requestCode == 1)
    	{
    		if(resultCode == RESULT_OK)
    		{
    			SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    			String ammount = data.getStringExtra("result");
    			SharedPreferences.Editor edit = settings.edit();
    			Log.i("info", ammount);
        		edit.putString("ammount", ammount);
        		edit.commit();
        		EditText cmDisplay = (EditText) findViewById(R.id.currentAmount);
        		cmDisplay.setText("$" + ammount);
    		}
    		else if(resultCode == RESULT_CANCELED)
    		{
    		}
    	}
    }
    
    private void setNewAmountInView(double afterPurchase){
    	EditText cmDisplay = (EditText) findViewById(R.id.currentAmount);
		String formattedString;
    	DecimalFormat d = new DecimalFormat("0.00");
    	formattedString = "$" + d.format(afterPurchase);
    	cmDisplay.setText(formattedString);
    }
    
    private void storeMoneyInPreferences(String moneyString){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor edit = settings.edit();
		edit.putString("ammount", moneyString);
		edit.commit();
		
		EditText title = (EditText) findViewById(R.id.transaction_title);
		EditText where = (EditText) findViewById(R.id.where_purchased);
		
		Transaction transaction = new Transaction(this.beforePurchase, this.afterPurchase, this.amountSpent);
		transaction.setType(this.type);
		try
		{
			String titleString = title.getText().toString();
			title.setText("");
			if(titleString == null)
			{
				titleString = "Not entered";
			}
			transaction.setTitle(titleString);
			String whereString = where.getText().toString();
			where.setText("");
			if(whereString == null)
			{
				whereString = "Not entered";
			}
			transaction.setWhereSpent(whereString);
		}
		catch(Exception e){}
		
		transaction = ds.create(transaction);
		
    }
    protected void onResume()
    {
    	super.onResume();
    	ds.open();
    }
    
    protected void onPause()
    {
    	super.onPause();
    	ds.close();
    }
    private class Spr implements OnItemSelectedListener
    {
    	
    	public Spr()
    	{
    		Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
    		spinner.setOnItemSelectedListener(this);
    	}
    	
    	public void onItemSelected(AdapterView<?> parent, View view, 
                int pos, long id) {
    		String text = parent.getItemAtPosition(pos).toString();
    		if(text.equals("Gas"))
    		{
    			type = Transaction.TYPE_GAS; 
    		}
    		else if(text.equals("Food"))
    		{
    			type = Transaction.TYPE_FOOD;
    		}
    		else if(text.equals("Entertainment"))
    		{
    			type = Transaction.TYPE_ENTERTAINMENT;
    		}
    		else if(text.equals("Bills"))
    		{
    			type = Transaction.TYPE_BILLS;
    		}
    		else if(text.equals("Significant Other"))
    		{
    			type = Transaction.TYPE_SIGNIFICANT_OTHER;
    		}
    		Log.i("TYPE-VAL", ""+type);
        }
    	public void onNothingSelected(AdapterView<?> parent) {
            type = 0;
        }
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(tab.getText().equals("Balance")){}
		else if(tab.getText().equals("Transactions"))
		{
			Intent i = new Intent(this, TransactionList.class);
			startActivity(i);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction f) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
    
}
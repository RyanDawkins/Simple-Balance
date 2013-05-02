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
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
//import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
	
	private static boolean isAddition = false;
	private static String PREFS_NAME = "MoneyTracker_Settings";
	private static double afterMathMoney;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Setting display where money is shown as unclickable
        EditText cmDisplay;
        cmDisplay = (EditText) findViewById(R.id.currentAmount);
        cmDisplay.setClickable(false);
        cmDisplay.setFocusable(false);
        
        // Getting preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	boolean firstLoad = settings.getBoolean("firstLoad", true);
    	
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
        	double currentMoney = Double.parseDouble(settings.getString("ammount", "0.00").replace("$", ""));
        	this.setNewAmountInView(currentMoney);
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
    
    // Future menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
    
    // Handles add/subtraction radio button clicks
    public void onRadioClick(View view) {
    	
    	boolean isChecked = ((RadioButton) view).isChecked();
    	
    	// Allow update to be made since a radio button was checked.
    	Button updateButton = (Button) findViewById(R.id.button_update);
    	updateButton.setEnabled(true);
    	
    	// Handles which operator to use
    	if(isChecked){
    		switch(view.getId()){
    	
    		// Addition radio button
    		case R.id.add_money:
    			isAddition = true;
    			break;
    		// Subtraction radio button
    		case R.id.sub_money:
    			isAddition = false;
    			break;
    		// Default set to subtract money
    		default:
    			isAddition = false;
    			break;
    		}
    	
    	} else {} // Else statement as place-holder
    	
    }
    
    // Function to handle updates for the new value
    public void onUpdate(View view){
		
    	double currentMoney;
    	double moneyDifference;
    	
    	EditText cm = (EditText) findViewById(R.id.currentAmount);
    	EditText md = (EditText) findViewById(R.id.amount_change);

    	// Converts currentMoney to a double by taking the object converting
    	// to a string and then removing the dollar sign by taking the substring
    	currentMoney = Double.parseDouble(cm.getText().toString().substring(1));
    	
    	// Gets text in the field and converts object to a string
    	try{
    		moneyDifference = Double.parseDouble(md.getText().toString());
    	} catch(Exception e){ // Catches no string exception
    		return;
    	}
    	
    	// Handles math
    	if(isAddition){
			// Get moneys or die trying
			afterMathMoney = currentMoney + moneyDifference;
		} else{
			// Loose moneys :(
			afterMathMoney = currentMoney - moneyDifference;
			
		}
		
    	// Casts new afterMathMoney value to a string
		String moneyString = "" + afterMathMoney;
		
		// Stores money in sharedPreferences
		this.storeMoneyInPreferences(moneyString);
		
		// Sets value in view
		this.setNewAmountInView(afterMathMoney);
    	
    	EditText amountChange = (EditText) findViewById(R.id.amount_change);
//    	EditText whatYouBought = (EditText) findViewById(R.id.what_you_bought);
    	amountChange.setText("");
//    	whatYouBought.setText("");
		
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
    
    private void setNewAmountInView(double afterMathMoney){
    	EditText cmDisplay = (EditText) findViewById(R.id.currentAmount);
		String formattedString;
    	DecimalFormat d = new DecimalFormat("0.00");
    	formattedString = "$" + d.format(afterMathMoney);
    	cmDisplay.setText(formattedString);
    }
    
    private void storeMoneyInPreferences(String moneyString){
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor edit = settings.edit();
		edit.putString("ammount", moneyString);
		edit.commit();
    }
}

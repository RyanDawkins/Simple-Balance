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

package com.dawkinstan.moneytracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
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
        
        EditText cmDisplay;
        cmDisplay = (EditText) findViewById(R.id.currentAmount);
        cmDisplay.setClickable(false);
        cmDisplay.setFocusable(false);
        
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    	boolean firstLoad = settings.getBoolean("firstLoad", true);
        if(firstLoad)
        {
        	Intent intent = new Intent(this, MTSettings.class);
        	try{
        		startActivityForResult(intent, 1);
        		SharedPreferences.Editor edit = settings.edit();
        		edit.putBoolean("firstLoad", false);
        		edit.commit();
        	}
        	catch (Exception e){
        	}
        } else {
        	String formattedString;
        	DecimalFormat d = new DecimalFormat("0.00");
        	double currentMoney = Double.parseDouble(settings.getString("ammount", "0.00").replace("$", ""));
        	formattedString = "$" + d.format(currentMoney);
        	cmDisplay.setText(formattedString);
        }
        
        Button updateButton = (Button) findViewById(R.id.button_update);
        updateButton.setEnabled(false);
        updateButton.setOnClickListener(new View.OnClickListener() {

        	public void onClick(View view){
        		onUpdate(view);
        	}
        	
        });
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onRadioClick(View view) {
    	
    	boolean checked = ((RadioButton) view).isChecked();
    	
    	Button updateButton = (Button) findViewById(R.id.button_update);
    	updateButton.setEnabled(true);
    	
    	if(checked){
    		switch(view.getId()){
    	
    		case R.id.add_money:
    			isAddition = true;
    			break;
    		case R.id.sub_money:
    			isAddition = false;
    			break;
    		default:
    			isAddition = false;
    			break;
    		}
    	
    	} else {}
    	
    }
    
    public void onUpdate(View view){
		
    	double currentMoney;
    	double moneyDifference;
    	
    	EditText cm = (EditText) findViewById(R.id.currentAmount);
    	EditText md = (EditText) findViewById(R.id.amount_change);

    	currentMoney = Double.parseDouble(cm.getText().toString().substring(1));
    	moneyDifference = Double.parseDouble(md.getText().toString());
    	
    	if(isAddition){

			// Get moneys
			afterMathMoney = currentMoney + moneyDifference;
			
		} else{
			// Get moneys
			afterMathMoney = currentMoney - moneyDifference;
			
		}
		
		String moneyString = "" + afterMathMoney;
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor edit = settings.edit();
		edit.putString("ammount", moneyString);
		edit.commit();
		
		EditText cmDisplay = (EditText) findViewById(R.id.currentAmount);
		String formattedString;
    	DecimalFormat d = new DecimalFormat("0.00");
    	formattedString = "$" + d.format(afterMathMoney);
    	cmDisplay.setText(formattedString);
    	
    	EditText amountChange = (EditText) findViewById(R.id.amount_change);
    	EditText whatYouBought = (EditText) findViewById(R.id.what_you_bought);
    	amountChange.setText("");
    	whatYouBought.setText("");
		
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
//        		setContentView(cmDisplay);
    		}
    		else if(resultCode == RESULT_CANCELED)
    		{
    		}
    	}
    }
}

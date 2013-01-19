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
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends Activity {
	
	private static boolean isAddition = false;
	private static String PREFS_NAME = "MoneyTracker_Settings";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onRadioClick(View view) {
    	
    	boolean checked = ((RadioButton) view).isChecked();
    	
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
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	if(requestCode == 1)
    	{
    		if(resultCode == RESULT_OK)
    		{
    			SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    			String ammount = data.getStringExtra("result");
    			SharedPreferences.Editor edit = settings.edit();
        		edit.putString("moneyAmmount", ammount);
        		edit.commit();
    		}
    		else if(resultCode == RESULT_CANCELED)
    		{
    		}
    	}
    }
}

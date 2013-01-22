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

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MTSettings extends Activity {

	private static String ammount;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtsettings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mtsettings, menu);
        return true;
    }
    
    public void updateMoney(View view)
	{
    	
    	EditText initialEntry = (EditText) findViewById(R.id.initialBox);
    	double amountFloat = Double.parseDouble(initialEntry.getText().toString());
    	DecimalFormat d = new DecimalFormat("0.00");
    	ammount = d.format(amountFloat);
    	
    	
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", ammount);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
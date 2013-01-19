package com.dawkinstan.moneytracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

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
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", ammount);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}

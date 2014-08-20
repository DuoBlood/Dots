package com.example.dots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private Button but_start;
	private EditText edt_rows, edt_columns;
	private DisplayMetrics metrics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        calc();
        edt_rows = (EditText) findViewById(R.id.editText1);
        edt_columns = (EditText) findViewById(R.id.editText2);
        
//        Log.d("Dots", "resolution: "+(metrics.widthPixels - 100)/App.step+" x "+ (int)(metrics.heightPixels*0.6-100)/App.step);
        
        but_start = (Button) findViewById(R.id.but_start);
        but_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String r = edt_rows.getText().toString();
				String c = edt_columns.getText().toString();
				if (r.length() != 0 && c.length() != 0) {
					int rows = Integer.parseInt(r);
					int columns = Integer.parseInt(c);
					if (columns <= App.maxColumns) {
						if (rows <= App.maxRows ) {
						Intent intent = new Intent(MainActivity.this, SecondActivity.class);
						intent.putExtra("rows", rows);
						intent.putExtra("columns", columns);
						startActivity(intent);
						} else {
							Toast.makeText(MainActivity.this, getString(R.string.max_rows) + " " + App.maxColumns, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.max_columns) + " " + App.maxColumns, Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(MainActivity.this, getString(R.string.not_all_fields_are_filled), Toast.LENGTH_SHORT).show();
				}
			}
		});
    }

    private void calc() {
    	App.maxColumns = (metrics.widthPixels - 100)/App.step;
    	App.maxRows = (int)(metrics.heightPixels*0.6-100)/App.step;
    }
}

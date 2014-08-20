package com.example.dots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends Activity implements OnClickListener{
	
	private DrawView dw;
	private Intent intent;
	private Button but_back, but_clear, but_go1, but_go2, but_go3;
	private EditText edt_add_first, edt_add_second, edt_delete_first, edt_delete_second, edt_check_first, edt_check_second;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		intent = getIntent();
		init();
		
	}
	
	private void init() {
		
		dw = (DrawView) findViewById(R.id.drawiView1);
		dw.setSize(intent.getIntExtra("rows", 0), intent.getIntExtra("columns", 0));
		
		but_back = (Button) findViewById(R.id.but_back);
		but_clear = (Button) findViewById(R.id.but_clear);
		but_go1 = (Button) findViewById(R.id.but_go_add);
		but_go2 = (Button) findViewById(R.id.but_go_delete);
		but_go3 = (Button) findViewById(R.id.but_go_check);
		
		but_back.setOnClickListener(this);
		but_clear.setOnClickListener(this);
		but_go1.setOnClickListener(this);
		but_go2.setOnClickListener(this);
		but_go3.setOnClickListener(this);
		
		edt_add_first = (EditText) findViewById(R.id.edt_add_first);
		edt_add_second = (EditText) findViewById(R.id.edt_add_second);
		
		edt_delete_first = (EditText) findViewById(R.id.edt_delete_first);
		edt_delete_second = (EditText) findViewById(R.id.edt_delete_second);
		
		edt_check_first = (EditText) findViewById(R.id.edt_check_first);
		edt_check_second = (EditText) findViewById(R.id.edt_check_second);
		
	}

	@Override
	public void onClick(View v) {
		String e1 = null;
		String e2 = null;
		int first = 0;
		int second = 0;
		
		switch(v.getId()) {
		case R.id.but_back:
			finish();
			break;
		case R.id.but_clear:
			dw.onlyDots();
			break;
		case R.id.but_go_add:
			e1 = edt_add_first.getText().toString();
			e2 = edt_add_second.getText().toString();
			if (e1.length() > 0 && e2.length() > 0) {
				first = Integer.parseInt(e1);
				second = Integer.parseInt(e2);
				dw.add(first, second);
			}
			break;
		case R.id.but_go_delete:
			e1 = edt_delete_first.getText().toString();
			e2 = edt_delete_second.getText().toString();
			if (e1.length() > 0 && e2.length() > 0) {
				first = Integer.parseInt(e1);
				second = Integer.parseInt(e2);
				dw.delete(first, second);
			}
			break;
		case R.id.but_go_check:
			e1 = edt_check_first.getText().toString();
			e2 = edt_check_second.getText().toString();
			if (e1.length() > 0 && e2.length() > 0) {
				first = Integer.parseInt(e1);
				second = Integer.parseInt(e2);
				dw.check(first, second);
			}
			break;
		}
	}
}

package com.example.listlist;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class DialeActivity extends Activity {
	
	public static final int BACK_UP=1;
	 public static final int BACK_DOWN=2;
	 public static final int BACK_MID=3;
	 public static final int FRONT_MID=4;
	 public static final int FRONT_UP=5;
	 public static final int FRONT_DOWN=6;
	 public static final int FRONT_RED=7;
	 public static final int BACK_RED=8;
	 public TextView tvHint;
	private int requestCode;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		final EditText et_rec = (EditText) findViewById(R.id.et_record);
		et_rec.setInputType(InputType.TYPE_NULL);//
		final Button btn_01 = (Button) findViewById(R.id.btn_01);
		final Button btn_02 = (Button) findViewById(R.id.btn_02);
		final Button btn_03 = (Button) findViewById(R.id.btn_03);
		final Button btn_04 = (Button) findViewById(R.id.btn_04);
		final Button btn_05 = (Button) findViewById(R.id.btn_05);
		
		final Button btn_06 = (Button) findViewById(R.id.btn_06);
		final Button btn_07 = (Button) findViewById(R.id.btn_07);
		final Button btn_08 = (Button) findViewById(R.id.btn_08);
		final Button btn_09 = (Button) findViewById(R.id.btn_09);
		final Button btn_00 = (Button) findViewById(R.id.btn_00);
		final Button btn_clear = (Button) findViewById(R.id.btn_clear);
		final Button btn_confirm = (Button) findViewById(R.id.btn_confirm);
		tvHint=(TextView) findViewById(R.id.tv_hint);
		
		btn_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String str = et_rec.getText().toString();
				et_rec.setText("");
				
			}
		});
		
		btn_01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String str = et_rec.getText().toString();
				et_rec.setText(str+btn_01.getText().toString());
				
			}
		});
		btn_02.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_02.getText().toString());
		
	}
});
		btn_03.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_03.getText().toString());
		
	}
});
		btn_04.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_04.getText().toString());
		
	}
});
		btn_05.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_05.getText().toString());
		
	}
});
		btn_06.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_06.getText().toString());
		
	}
});
		btn_07.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_07.getText().toString());
		
	}
});
		btn_08.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_08.getText().toString());
		
	}
});
		btn_09.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_09.getText().toString());
		
	}
});
		btn_00.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		String str = et_rec.getText().toString();
		et_rec.setText(str+btn_00.getText().toString());
		
	}
});
		
		SharedPreferences pref=getSharedPreferences("data", MODE_PRIVATE);
		requestCode=pref.getInt("requestCode", BACK_UP);
		editor=getSharedPreferences("data", MODE_PRIVATE).edit();
		
		switch (requestCode) {
		case BACK_UP:
			editor.putInt("requestCode",BACK_DOWN);
    		editor.commit();
    		tvHint.setText("后上");
			break;
		case BACK_DOWN:
			editor.putInt("requestCode",BACK_MID);
    		editor.commit();
    		tvHint.setText("后下");
			break;
		case BACK_MID:
			editor.putInt("requestCode",FRONT_MID);
    		editor.commit();
    		tvHint.setText("后中");
			break;				
		case FRONT_MID:
				editor.putInt("requestCode",FRONT_UP);
        		editor.commit();
        		tvHint.setText("前中");
				break;
		case FRONT_UP:
			editor.putInt("requestCode",FRONT_DOWN);
    		editor.commit();
    		tvHint.setText("前上");
			break;
		case FRONT_DOWN:
			editor.putInt("requestCode",FRONT_RED);
    		editor.commit();
    		tvHint.setText("前下");
    		break;
		case FRONT_RED:
			editor.putInt("requestCode",BACK_RED);
    		editor.commit();
    		tvHint.setText("前红");
			break;
		case BACK_RED:
			editor.putInt("requestCode",BACK_UP);
    		editor.commit();
    		tvHint.setText("后红");
			break;
		default:
			break;
		}              
		btn_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                //把返回数据存入Intent
				
				//字符串的转换和传递有问题！！！！！！！
				//字符串的转换和传递有问题！！！！！！！
				//字符串的转换和传递有问题！！！！！！！
               intent.putExtra("NUMBER", et_rec.getText().toString());
              
                
              /*  
        		switch (requestCode) {
				case BACK_UP:
					editor.putInt("requestCode",BACK_DOWN);
	        		editor.commit();
					break;
				case BACK_DOWN:
					editor.putInt("requestCode",BACK_MID);
	        		editor.commit();
					break;
				case BACK_MID:
					editor.putInt("requestCode",FRONT_MID);
	        		editor.commit();
					break;				
				case FRONT_MID:
						editor.putInt("requestCode",FRONT_UP);
		        		editor.commit();
						break;
				case FRONT_UP:
					editor.putInt("requestCode",FRONT_DOWN);
	        		editor.commit();
					break;
				case FRONT_DOWN:
					editor.putInt("requestCode",FRONT_RED);
	        		editor.commit();
	        		break;
				case FRONT_RED:
					editor.putInt("requestCode",BACK_RED);
	        		editor.commit();
					break;
				case BACK_RED:
					editor.putInt("requestCode",BACK_UP);
	        		editor.commit();
					break;
				default:
					break;
				}   
				
				           */
                //设置返回数据
                DialeActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                DialeActivity.this.finish();
				
			}
		});
		
	}
}

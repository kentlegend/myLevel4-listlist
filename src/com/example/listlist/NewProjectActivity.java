package com.example.listlist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;



import android.R.string;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewProjectActivity extends Activity {
private Button btn_new;
private EditText et_pname;
private String pName;
//用于数据库
private CeDuanDBHelper mCeDuanDB;
private Cursor mCursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newproject);
		btn_new=(Button) findViewById(R.id.btn_new);
		et_pname=(EditText) findViewById(R.id.et_pname);
		btn_new.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 点击确定，建立新的工程
				pName=et_pname.getText().toString();
				newProj(pName);
				
				finish();
			}
		});
		
	}
	public void newProj(String pName){
		
		File destDir = new File("/data/data/com.example.level4/"+pName);
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		 Process p;
		int status;
		            
		                try {
							p = Runtime.getRuntime().exec("chmod 777 " +  destDir );
						status = p.waitFor();  
		                if (status == 0) {   
		                    //创建 succeed  
		                    Toast.makeText(this, "chmod succeed", Toast.LENGTH_SHORT).show();
		                } else {   
		                    //创建 failed
		                    Toast.makeText(this, "chmod failed", Toast.LENGTH_SHORT).show();
		                } 
		                } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                }
		  else{
			  Toast.makeText(this, "exist exist exist", Toast.LENGTH_SHORT).show();
		  }
	
		
		  
	      File filey=new File("/data/data/com.example.level4/"+pName+"/"+"12345.txt");
	      if(!filey.exists()){
	      	try {
					filey.createNewFile();
					Toast.makeText(this, "12345创建成功", Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	      
	      
	  	try {
	  		BufferedWriter bw=new BufferedWriter(new FileWriter(filey));
	  		String temp="建立文件夹和文件的实验20170204";
	  		bw.write(temp);
	  		Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
	  		bw.close();
		  	} catch (IOException e) {
		  		// TODO Auto-generated catch block
		  		e.printStackTrace();
		  	}
	     
	  	File filex=new File("/data/data/com.example.level4/"+pName+"/"+"12345.txt");
	      BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(filex));
				//System.out.println("br.readLine="+br.readLine());
				Toast.makeText(this, br.readLine().toString(), Toast.LENGTH_SHORT).show();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "不存在", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}         
		            
	
	}
	
}


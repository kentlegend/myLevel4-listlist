package com.example.listlist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StationActivity extends Activity {
	private CeDuanDBHelper mCeDuanDB;
	private Cursor mCursor;
	private Cursor mCursor3;
	private int CEDUAN_ID = 0;
	
	private TextView ceduanName;
	private TextView cezhanNum;
	private EditText backUp;
	private EditText backDown;
	private EditText backMid;
	private EditText backRed;
	private EditText frontUp;
	private EditText frontDown;
	private EditText frontMid;
	private EditText frontRed;
	
	private Button btObserve;
	private Button saveStationData;
	private Button preStation;
	private Button nextStation;
	 public static final int BACK_UP=1;
	 public static final int BACK_DOWN=2;
	 public static final int BACK_MID=3;
	 public static final int FRONT_MID=4;
	 public static final int FRONT_UP=5;
	 public static final int FRONT_DOWN=6;
	 public static final int FRONT_RED=7;
	 public static final int BACK_RED=8;
	 private int requestCode;
	 public int[] station_datalist;
	 
	 public int k1;
	 public int k2;
	 
	 String from;
	 String to;
	 int id;
	 int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);
		
		// 获取intent传递过来的测段名和点击的条目的位置；
		Intent intent=getIntent();
		 from=intent.getStringExtra("from");
		 to=intent.getStringExtra("to");
		 id=intent.getIntExtra("id", 10000);
		 position=intent.getIntExtra("position", 0);

		//建立数据库，得到它的Cursor
			mCeDuanDB=new CeDuanDBHelper(StationActivity.this, "20170223");
			//*mCursor=mCeDuanDB.select();
			mCursor3=mCeDuanDB.select3(id,from,to);
		
			 initiateView();
			 //实例化数组，用于保存观测值
			 station_datalist=new int[8];

		
		// “前一站”按钮事件
		 preStation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCursor3.getPosition()>0){
					mCursor3.moveToPrevious();
					Toast.makeText(StationActivity.this, "mCursor3的位置"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
					//cezhanNum.setText((mCursor3.getPosition()+1)+"/"+mCursor3.getCount());
					//mCursor3.moveToPrevious();
					displayData();
					
				
				}else{
					Toast.makeText(StationActivity.this, "到头了，已指向第一个站"+"mCursor3的位置"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();	
				}
			}
		});
		// “后一站”按钮事件
		 nextStation.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCursor3.moveToNext()){
					Toast.makeText(StationActivity.this, "mCursor3的位置"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
					displayData();
				
				}else{
					clearObserveData();
					cezhanNum.setText((mCursor3.getPosition()+1)+"/"+(mCursor3.getCount()+1));
					Toast.makeText(StationActivity.this, "mCursor3的位置"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		 
		//“观测”按钮事件
		btObserve.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
				//用SharedPreferences保存一个值，它用于判断当前观测的哪个读数；
				SharedPreferences pref=getSharedPreferences("data", MODE_PRIVATE);
				int requestCode=pref.getInt("requestCode", BACK_UP);
				Intent intentDail=new Intent(StationActivity.this,DialeActivity.class);
				startActivityForResult(intentDail,requestCode);
			}
		});
		
		//“保存”按钮事件
		saveStationData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				/*注意：通过ItemClick 获取条目的position,传递过来；因为position总是从0开始顺序连续的
				而id 是从1开始的，而且如果删除了某个条目，id不顺延，都还是保留原值
				
				moveToPosition是从0开始move的，但是数据库的id是从1开始的；
			 * 
			 */
				
				// 点击保存后，先判断数据是否合格，然后保存至数据库，待完成
				////dataCheck();
				//向测站表添加观测数据。
				Toast.makeText(StationActivity.this, "保存前站数:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
				mCeDuanDB.insert3(id, from, to,station_datalist[0],station_datalist[1],station_datalist[2],station_datalist[3],
						station_datalist[4],station_datalist[5],station_datalist[6],station_datalist[7]);
				mCursor3=mCeDuanDB.select3(id, from, to);
				
				//Toast.makeText(StationActivity.this, "原来id:"+id, Toast.LENGTH_SHORT).show();

				//Toast.makeText(StationActivity.this, "插入成功cezhan:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
				
				//finish();
				//测站观测数据保存到数据库后，要清除当前界面内的观测值以开始下一个测站；
				//clearObserveData()代码暂时未完成，
				
				clearObserveData();
				mCursor3=mCeDuanDB.select3(id,from,to);
				mCursor3.moveToLast();
				Toast.makeText(StationActivity.this, "保存后后站数:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
				Toast.makeText(StationActivity.this, "保存后Position"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
			}

			

			private void dataCheck() {
				// TODO Auto-generated method stub
				//检查观测值，此部分暂时未实现，当前代码为实验
				
				for(int i=0;i<station_datalist.length;i++){
					int y;
					y=station_datalist[i];
					Toast.makeText(StationActivity.this, Integer.toString(y), Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
	}
	
	
	private void clearObserveData() {
		// 清除界面中的观测值和计算值，待完善
		backUp.setText("");
		backDown.setText("");
		backMid.setText("");
		backRed.setText("");
		frontUp.setText("");
		frontDown.setText("");
		frontMid.setText("");
		frontRed.setText("");
		//Toast.makeText(StationActivity.this, "清空编辑框-代码未编写！！", Toast.LENGTH_SHORT).show();
	}
	
	private void displayData(){
		cezhanNum.setText((mCursor3.getPosition()+1)+"/"+mCursor3.getCount());
		backUp.setText(Integer.toString(mCursor3.getInt(1)));
		backDown.setText(Integer.toString(mCursor3.getInt(2)));
		backMid.setText(Integer.toString(mCursor3.getInt(3)));
		frontMid.setText(Integer.toString(mCursor3.getInt(4)));
		frontUp.setText(Integer.toString(mCursor3.getInt(5)));
		frontDown.setText(Integer.toString(mCursor3.getInt(6)));
		frontRed.setText(Integer.toString(mCursor3.getInt(7)));
		backRed.setText(Integer.toString(mCursor3.getInt(8)));
	}
	
	// 初始化本界面的控件
	private void initiateView() {
		
		//用于显示观测值的编辑框；
		backUp=(EditText) findViewById(R.id.backup);
		backDown=(EditText) findViewById(R.id.backdown);
		backMid=(EditText) findViewById(R.id.backmid);
		backRed=(EditText) findViewById(R.id.backred);
		frontUp=(EditText) findViewById(R.id.frontup);
		frontDown=(EditText) findViewById(R.id.frontdown);
		frontMid=(EditText) findViewById(R.id.frontmid);
		frontRed=(EditText) findViewById(R.id.frontred);
		
		//设置当前显示测段名称；
		ceduanName=(TextView) findViewById(R.id.ceduan_name);
		ceduanName.setText("自:"+from+"至:"+to);
		
		
		if(mCursor3.moveToPosition(mCursor3.getCount()-1)){
			//设置显示当前测段的测站及总数//设置显示当前测段的测站及总数
				cezhanNum=(TextView) findViewById(R.id.tv_sta_num);
				cezhanNum.setText(mCursor3.getCount()+"/"+mCursor3.getCount());
			Toast.makeText(StationActivity.this, from+":"+to+"共有测站:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
			//mCursor3.moveToPrevious();
		Toast.makeText(StationActivity.this, from+":"+to+"当前POSITION:"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
		backUp.setText(Integer.toString(mCursor3.getInt(1)));
		backDown.setText(Integer.toString(mCursor3.getInt(2)));
		backMid.setText(Integer.toString(mCursor3.getInt(3)));
		frontMid.setText(Integer.toString(mCursor3.getInt(4)));
		frontUp.setText(Integer.toString(mCursor3.getInt(5)));
		frontDown.setText(Integer.toString(mCursor3.getInt(6)));
		frontRed.setText(Integer.toString(mCursor3.getInt(7)));
		backRed.setText(Integer.toString(mCursor3.getInt(8)));
		Toast.makeText(StationActivity.this, from+":"+to+"AGAIN当前POSITION:"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(StationActivity.this, "当前测段无测站", Toast.LENGTH_SHORT).show();
		}
		
		
		//*mCursor.moveToPosition(position);
		//Toast.makeText(StationActivity.this, Integer.toString(mCursor.getInt(3)), Toast.LENGTH_SHORT).show();
		

		preStation=(Button) findViewById(R.id.pre_sta);
		nextStation=(Button) findViewById(R.id.next_sta);
		btObserve= (Button) findViewById(R.id.btn_observe);
		btObserve.setText("点击观测："+"后尺 上丝");
		saveStationData=(Button) findViewById(R.id.btn_save_station);
		
		
		
		

		
		//通过SharedPreferences获取应当观测的顺序
		SharedPreferences.Editor editor=getSharedPreferences("data", MODE_PRIVATE).edit();
		editor.putInt("requestCode",BACK_UP);
		editor.commit();
	}


	@Override		
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		SharedPreferences.Editor editor=getSharedPreferences("data", MODE_PRIVATE).edit();
		
		switch (requestCode) {
		case 1:
			String result_backup =  data.getExtras().getString("NUMBER");
			station_datalist[0]=Integer.parseInt(result_backup);
			Toast.makeText(StationActivity.this, Integer.toString(station_datalist[0]), Toast.LENGTH_LONG).show();
			
			backUp.setText(result_backup);
			btObserve.setText("点击观测："+"后尺  下丝");

			break;
		case 2:
			String result_backdown = data.getExtras().getString("NUMBER");
			station_datalist[1]=Integer.parseInt(result_backdown);
			int dist=(station_datalist[0]-station_datalist[1]);
			if(dist<=800){
				backDown.setText(result_backdown);
				btObserve.setText("点击观测："+"后尺  中丝");
				break;
			}else{
				Toast.makeText(StationActivity.this, "距离应该小于80米", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",BACK_DOWN);
				editor.commit();
				break;
			}
			
			
		case 3:
			String result_backmid = data.getExtras().getString("NUMBER");
			station_datalist[2]=Integer.parseInt(result_backmid);
			int mid=(station_datalist[0]+station_datalist[1])/2;
			if(Math.abs(mid-station_datalist[2])<10){
				backMid.setText(result_backmid);
				btObserve.setText("点击观测："+"前尺  中丝");
				break;
			}else{
				Toast.makeText(StationActivity.this, "中数与上下丝平均值差异过大", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",BACK_MID);
				editor.commit();
				break;
			}
			
			
		case 4:
			String result_frontmid = data.getExtras().getString("NUMBER");
			station_datalist[3]=Integer.parseInt(result_frontmid);
			
			if(0<station_datalist[3]&&station_datalist[3]<3000){
				frontMid.setText(result_frontmid);
				btObserve.setText("点击观测："+"前尺  上丝");
				break;
			}else{
				Toast.makeText(StationActivity.this, "中丝读数超出范围", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",FRONT_MID);
				editor.commit();
				break;
			}
			
			
			
			
			
		case 5:
			String result_frontup = data.getExtras().getString("NUMBER");
			station_datalist[4]=Integer.parseInt(result_frontup);
			//if(Math.abs(station_datalist[4]-station_datalist[3])<3)
			if(0<station_datalist[4]&&station_datalist[4]<3000){
				frontUp.setText(result_frontup);
				btObserve.setText("点击观测："+"前尺  下丝");
				break;
			}else{
				Toast.makeText(StationActivity.this, "读数超出范围", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",FRONT_UP);
				editor.commit();
				break;
			}
			
			
			
			
		case 6:
			String result_frontdown = data.getExtras().getString("NUMBER");
			station_datalist[5]=Integer.parseInt(result_frontdown);
			int mid_front=(station_datalist[4]+station_datalist[5])/2;
			if(Math.abs(mid_front-station_datalist[3])<10){
				frontDown.setText(result_frontdown);
				btObserve.setText("点击观测："+"前尺  红丝");
				break;
			}else{
				Toast.makeText(StationActivity.this, "中数与上下丝平均值差异过大", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",FRONT_DOWN);
				editor.commit();
				break;
			}
			
			
			
			
			
			
			
		case 7:
			String result_frontred = data.getExtras().getString("NUMBER");
			station_datalist[6]=Integer.parseInt(result_frontred);
			//这3行要删除
			frontRed.setText(result_frontred);
			btObserve.setText("点击观测："+"后尺  红丝");
			break;
			/*	//
			int k=station_datalist[6]-station_datalist[3];
			if(k1!=4687||k1!=4787){
				if(k>4684&&k<4690){
					k1=4687;
					k2=4787;
					frontRed.setText(result_frontred);
					btObserve.setText("点击观测："+"后尺  红丝");
					break;
				}else if(k>4784&&k<4790){
					k1=4787;
					k2=4687;
					frontRed.setText(result_frontred);
					btObserve.setText("点击观测："+"后尺  红丝");
				}else{
					Toast.makeText(StationActivity.this, "基辅差超限", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
				}
				
			}else if(k1==4687){
				if(k>4684&&k<4690){
					frontRed.setText(result_frontred);
					btObserve.setText("点击观测："+"后尺  红丝");
					k2=4787;
					break;
								}else{
					Toast.makeText(StationActivity.this, "基辅差超限", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
			}
			
			} else if(k1==4787){
				if(k>4784&&k<4790){
					frontRed.setText(result_frontred);
					btObserve.setText("点击观测："+"后尺  红丝");
					k2=4687;
					break;
								}else{
					Toast.makeText(StationActivity.this, "基辅差超限", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
								}
			
			}
	
			*/
			
		case 8:
			String result_backred = data.getExtras().getString("NUMBER");
			station_datalist[7]=Integer.parseInt(result_backred);
			
			//这3行要删除
			backRed.setText(result_backred);
			btObserve.setText("点击观测："+"后尺  上丝");
			break;
			/*
			int kb=station_datalist[7]-station_datalist[2];
			 if(k2==4687){
				if(kb>4684&&kb<4690){
					backRed.setText(result_backred);
					btObserve.setText("点击观测："+"后尺  上丝");
					k1=4687;
					k2=4787;
					break;
								}else{
					Toast.makeText(StationActivity.this, "基辅差超限4687", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
			}
			
			} else if(k2==4787){
				if(kb>4784&&kb<4790){
					backRed.setText(result_backred);
					btObserve.setText("点击观测："+"后尺  上丝");
					k1=4787;
					k2=4687;
					break;
					
								}else{
					Toast.makeText(StationActivity.this, "基辅差超限", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",BACK_RED);
					editor.commit();
					break;
			}
			
			}
			
			*/
			
			
			
			
		default:
			break;
		}
		
			}
	/*
	public void addToDB(String from,String to,int backup,int backmid,int backdown){
	
		//!TextUtils.isEmpty
		
		if(TextUtils.isEmpty(from)||TextUtils.isEmpty(to)){
			Toast.makeText(this, "数据不能为空值!", Toast.LENGTH_SHORT).show();
			return;
		}
		mCeDuanDB.insert(from,to,backup,backdown,backmid);
		mCursor.requery();
		Toast.makeText(this, "观测值  Add Successed!", Toast.LENGTH_SHORT).show();
	}*/
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		
	}
}

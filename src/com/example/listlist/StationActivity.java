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
		
		// ��ȡintent���ݹ����Ĳ�����͵������Ŀ��λ�ã�
		Intent intent=getIntent();
		 from=intent.getStringExtra("from");
		 to=intent.getStringExtra("to");
		 id=intent.getIntExtra("id", 10000);
		 position=intent.getIntExtra("position", 0);

		//�������ݿ⣬�õ�����Cursor
			mCeDuanDB=new CeDuanDBHelper(StationActivity.this, "20170223");
			//*mCursor=mCeDuanDB.select();
			mCursor3=mCeDuanDB.select3(id,from,to);
		
			 initiateView();
			 //ʵ�������飬���ڱ���۲�ֵ
			 station_datalist=new int[8];

		
		// ��ǰһվ����ť�¼�
		 preStation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCursor3.getPosition()>0){
					mCursor3.moveToPrevious();
					Toast.makeText(StationActivity.this, "mCursor3��λ��"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
					//cezhanNum.setText((mCursor3.getPosition()+1)+"/"+mCursor3.getCount());
					//mCursor3.moveToPrevious();
					displayData();
					
				
				}else{
					Toast.makeText(StationActivity.this, "��ͷ�ˣ���ָ���һ��վ"+"mCursor3��λ��"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();	
				}
			}
		});
		// ����һվ����ť�¼�
		 nextStation.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCursor3.moveToNext()){
					Toast.makeText(StationActivity.this, "mCursor3��λ��"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
					displayData();
				
				}else{
					clearObserveData();
					cezhanNum.setText((mCursor3.getPosition()+1)+"/"+(mCursor3.getCount()+1));
					Toast.makeText(StationActivity.this, "mCursor3��λ��"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		 
		//���۲⡱��ť�¼�
		btObserve.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View v) {
				//��SharedPreferences����һ��ֵ���������жϵ�ǰ�۲���ĸ�������
				SharedPreferences pref=getSharedPreferences("data", MODE_PRIVATE);
				int requestCode=pref.getInt("requestCode", BACK_UP);
				Intent intentDail=new Intent(StationActivity.this,DialeActivity.class);
				startActivityForResult(intentDail,requestCode);
			}
		});
		
		//�����桱��ť�¼�
		saveStationData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {


				/*ע�⣺ͨ��ItemClick ��ȡ��Ŀ��position,���ݹ�������Ϊposition���Ǵ�0��ʼ˳��������
				��id �Ǵ�1��ʼ�ģ��������ɾ����ĳ����Ŀ��id��˳�ӣ������Ǳ���ԭֵ
				
				moveToPosition�Ǵ�0��ʼmove�ģ��������ݿ��id�Ǵ�1��ʼ�ģ�
			 * 
			 */
				
				// �����������ж������Ƿ�ϸ�Ȼ�󱣴������ݿ⣬�����
				////dataCheck();
				//���վ����ӹ۲����ݡ�
				Toast.makeText(StationActivity.this, "����ǰվ��:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
				mCeDuanDB.insert3(id, from, to,station_datalist[0],station_datalist[1],station_datalist[2],station_datalist[3],
						station_datalist[4],station_datalist[5],station_datalist[6],station_datalist[7]);
				mCursor3=mCeDuanDB.select3(id, from, to);
				
				//Toast.makeText(StationActivity.this, "ԭ��id:"+id, Toast.LENGTH_SHORT).show();

				//Toast.makeText(StationActivity.this, "����ɹ�cezhan:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
				
				//finish();
				//��վ�۲����ݱ��浽���ݿ��Ҫ�����ǰ�����ڵĹ۲�ֵ�Կ�ʼ��һ����վ��
				//clearObserveData()������ʱδ��ɣ�
				
				clearObserveData();
				mCursor3=mCeDuanDB.select3(id,from,to);
				mCursor3.moveToLast();
				Toast.makeText(StationActivity.this, "������վ��:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
				Toast.makeText(StationActivity.this, "�����Position"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
			}

			

			private void dataCheck() {
				// TODO Auto-generated method stub
				//���۲�ֵ���˲�����ʱδʵ�֣���ǰ����Ϊʵ��
				
				for(int i=0;i<station_datalist.length;i++){
					int y;
					y=station_datalist[i];
					Toast.makeText(StationActivity.this, Integer.toString(y), Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
	}
	
	
	private void clearObserveData() {
		// ��������еĹ۲�ֵ�ͼ���ֵ��������
		backUp.setText("");
		backDown.setText("");
		backMid.setText("");
		backRed.setText("");
		frontUp.setText("");
		frontDown.setText("");
		frontMid.setText("");
		frontRed.setText("");
		//Toast.makeText(StationActivity.this, "��ձ༭��-����δ��д����", Toast.LENGTH_SHORT).show();
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
	
	// ��ʼ��������Ŀؼ�
	private void initiateView() {
		
		//������ʾ�۲�ֵ�ı༭��
		backUp=(EditText) findViewById(R.id.backup);
		backDown=(EditText) findViewById(R.id.backdown);
		backMid=(EditText) findViewById(R.id.backmid);
		backRed=(EditText) findViewById(R.id.backred);
		frontUp=(EditText) findViewById(R.id.frontup);
		frontDown=(EditText) findViewById(R.id.frontdown);
		frontMid=(EditText) findViewById(R.id.frontmid);
		frontRed=(EditText) findViewById(R.id.frontred);
		
		//���õ�ǰ��ʾ������ƣ�
		ceduanName=(TextView) findViewById(R.id.ceduan_name);
		ceduanName.setText("��:"+from+"��:"+to);
		
		
		if(mCursor3.moveToPosition(mCursor3.getCount()-1)){
			//������ʾ��ǰ��εĲ�վ������//������ʾ��ǰ��εĲ�վ������
				cezhanNum=(TextView) findViewById(R.id.tv_sta_num);
				cezhanNum.setText(mCursor3.getCount()+"/"+mCursor3.getCount());
			Toast.makeText(StationActivity.this, from+":"+to+"���в�վ:"+mCursor3.getCount(), Toast.LENGTH_SHORT).show();
			//mCursor3.moveToPrevious();
		Toast.makeText(StationActivity.this, from+":"+to+"��ǰPOSITION:"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
		backUp.setText(Integer.toString(mCursor3.getInt(1)));
		backDown.setText(Integer.toString(mCursor3.getInt(2)));
		backMid.setText(Integer.toString(mCursor3.getInt(3)));
		frontMid.setText(Integer.toString(mCursor3.getInt(4)));
		frontUp.setText(Integer.toString(mCursor3.getInt(5)));
		frontDown.setText(Integer.toString(mCursor3.getInt(6)));
		frontRed.setText(Integer.toString(mCursor3.getInt(7)));
		backRed.setText(Integer.toString(mCursor3.getInt(8)));
		Toast.makeText(StationActivity.this, from+":"+to+"AGAIN��ǰPOSITION:"+mCursor3.getPosition(), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(StationActivity.this, "��ǰ����޲�վ", Toast.LENGTH_SHORT).show();
		}
		
		
		//*mCursor.moveToPosition(position);
		//Toast.makeText(StationActivity.this, Integer.toString(mCursor.getInt(3)), Toast.LENGTH_SHORT).show();
		

		preStation=(Button) findViewById(R.id.pre_sta);
		nextStation=(Button) findViewById(R.id.next_sta);
		btObserve= (Button) findViewById(R.id.btn_observe);
		btObserve.setText("����۲⣺"+"��� ��˿");
		saveStationData=(Button) findViewById(R.id.btn_save_station);
		
		
		
		

		
		//ͨ��SharedPreferences��ȡӦ���۲��˳��
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
			btObserve.setText("����۲⣺"+"���  ��˿");

			break;
		case 2:
			String result_backdown = data.getExtras().getString("NUMBER");
			station_datalist[1]=Integer.parseInt(result_backdown);
			int dist=(station_datalist[0]-station_datalist[1]);
			if(dist<=800){
				backDown.setText(result_backdown);
				btObserve.setText("����۲⣺"+"���  ��˿");
				break;
			}else{
				Toast.makeText(StationActivity.this, "����Ӧ��С��80��", Toast.LENGTH_LONG).show();
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
				btObserve.setText("����۲⣺"+"ǰ��  ��˿");
				break;
			}else{
				Toast.makeText(StationActivity.this, "����������˿ƽ��ֵ�������", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",BACK_MID);
				editor.commit();
				break;
			}
			
			
		case 4:
			String result_frontmid = data.getExtras().getString("NUMBER");
			station_datalist[3]=Integer.parseInt(result_frontmid);
			
			if(0<station_datalist[3]&&station_datalist[3]<3000){
				frontMid.setText(result_frontmid);
				btObserve.setText("����۲⣺"+"ǰ��  ��˿");
				break;
			}else{
				Toast.makeText(StationActivity.this, "��˿����������Χ", Toast.LENGTH_LONG).show();
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
				btObserve.setText("����۲⣺"+"ǰ��  ��˿");
				break;
			}else{
				Toast.makeText(StationActivity.this, "����������Χ", Toast.LENGTH_LONG).show();
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
				btObserve.setText("����۲⣺"+"ǰ��  ��˿");
				break;
			}else{
				Toast.makeText(StationActivity.this, "����������˿ƽ��ֵ�������", Toast.LENGTH_LONG).show();
				editor.putInt("requestCode",FRONT_DOWN);
				editor.commit();
				break;
			}
			
			
			
			
			
			
			
		case 7:
			String result_frontred = data.getExtras().getString("NUMBER");
			station_datalist[6]=Integer.parseInt(result_frontred);
			//��3��Ҫɾ��
			frontRed.setText(result_frontred);
			btObserve.setText("����۲⣺"+"���  ��˿");
			break;
			/*	//
			int k=station_datalist[6]-station_datalist[3];
			if(k1!=4687||k1!=4787){
				if(k>4684&&k<4690){
					k1=4687;
					k2=4787;
					frontRed.setText(result_frontred);
					btObserve.setText("����۲⣺"+"���  ��˿");
					break;
				}else if(k>4784&&k<4790){
					k1=4787;
					k2=4687;
					frontRed.setText(result_frontred);
					btObserve.setText("����۲⣺"+"���  ��˿");
				}else{
					Toast.makeText(StationActivity.this, "�������", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
				}
				
			}else if(k1==4687){
				if(k>4684&&k<4690){
					frontRed.setText(result_frontred);
					btObserve.setText("����۲⣺"+"���  ��˿");
					k2=4787;
					break;
								}else{
					Toast.makeText(StationActivity.this, "�������", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
			}
			
			} else if(k1==4787){
				if(k>4784&&k<4790){
					frontRed.setText(result_frontred);
					btObserve.setText("����۲⣺"+"���  ��˿");
					k2=4687;
					break;
								}else{
					Toast.makeText(StationActivity.this, "�������", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
								}
			
			}
	
			*/
			
		case 8:
			String result_backred = data.getExtras().getString("NUMBER");
			station_datalist[7]=Integer.parseInt(result_backred);
			
			//��3��Ҫɾ��
			backRed.setText(result_backred);
			btObserve.setText("����۲⣺"+"���  ��˿");
			break;
			/*
			int kb=station_datalist[7]-station_datalist[2];
			 if(k2==4687){
				if(kb>4684&&kb<4690){
					backRed.setText(result_backred);
					btObserve.setText("����۲⣺"+"���  ��˿");
					k1=4687;
					k2=4787;
					break;
								}else{
					Toast.makeText(StationActivity.this, "�������4687", Toast.LENGTH_LONG).show();
					editor.putInt("requestCode",FRONT_RED);
					editor.commit();
					break;
			}
			
			} else if(k2==4787){
				if(kb>4784&&kb<4790){
					backRed.setText(result_backred);
					btObserve.setText("����۲⣺"+"���  ��˿");
					k1=4787;
					k2=4687;
					break;
					
								}else{
					Toast.makeText(StationActivity.this, "�������", Toast.LENGTH_LONG).show();
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
			Toast.makeText(this, "���ݲ���Ϊ��ֵ!", Toast.LENGTH_SHORT).show();
			return;
		}
		mCeDuanDB.insert(from,to,backup,backdown,backmid);
		mCursor.requery();
		Toast.makeText(this, "�۲�ֵ  Add Successed!", Toast.LENGTH_SHORT).show();
	}*/
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		
	}
}

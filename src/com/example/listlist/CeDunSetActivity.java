 package com.example.listlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class CeDunSetActivity extends Activity {
private Button addData;
private List<CeDuan> ceduanList=new ArrayList<CeDuan>();
private EditText etFrom;
private EditText etTo;
private CeDuanAdapter adapter;
private ListView listView;
protected static final int CONTEXTMENU_DELETEITEM = 0; 
protected static final int CONTEXTMENU_EDITITEM = 1;
private Cursor psi;
private EditText editFrom;
private EditText editTo;
private CeDuan ceduan; //声明测段对象

private CeDuanDBHelper mCeDuanDB;
private Cursor mCursor;
private Cursor mCursor3;
private int CEDUAN_ID = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ceduan_set);
		
		 etFrom=(EditText) findViewById(R.id.et_from);
		 etTo=(EditText) findViewById(R.id.et_to);
		 listView=(ListView) findViewById(R.id.lv);
		 addData=(Button) findViewById(R.id.bt_add);
		 
		//CeDuan cd=new CeDuan("起始点", "到达点");
		//ceduanList.add(cd);
		//建立数据库
		mCeDuanDB=new CeDuanDBHelper(CeDunSetActivity.this, "20170223");
		mCursor=mCeDuanDB.select();
		//mCursor3=mCeDuanDB.select3();
		initListView();//初始化ListView;

	
		

		//添加测段按钮的点击事件
		addData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String from = etFrom.getText().toString();
				String to = etTo.getText().toString();
				
				addToDB(from,to,0,0,0);
				
				CeDuan cd=new CeDuan(from, to);
				////ceduanList.add(cd);
				////adapter.notifyDataSetChanged();
				Toast.makeText(CeDunSetActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
				//相关数据库的操作逻辑也要在这里完成；
				
				//queryFromDB();
			}

			
		});
		
		
	}
	//向数据库里添加测段数据
	public void addToDB(String from,String to,int backup,int backmid,int backdown){
		// TODO Auto-generated method stub
		//!TextUtils.isEmpty
		
		if(TextUtils.isEmpty(from)||TextUtils.isEmpty(to)){
			Toast.makeText(this, "数据不能为空值!", Toast.LENGTH_SHORT).show();
			return;
		}
		mCeDuanDB.insert(from,to,backup,backdown,backmid);
		mCursor.requery();
		initListView();
		listView.invalidateViews();
		//BookName.setText("");
		//BookAuthor.setText("");
		Toast.makeText(this, "SQLite  Add Successed!", Toast.LENGTH_SHORT).show();
	}
	//从数据库里查询测段数据
	private  List<CeDuan> queryFromDB() {
		// TODO Auto-generated method stub
		ArrayList<CeDuan> listCeDuan=new ArrayList<CeDuan>();
		if(mCursor.moveToFirst()){
			
			do{
			String from=mCursor.getString(mCursor.getColumnIndex("from_name"));
			String to=mCursor.getString(mCursor.getColumnIndex("to_name"));
			CeDuan ceduan=new CeDuan(from,to);
			listCeDuan.add(ceduan);
			//Toast.makeText(CeDunSetActivity.this, from, Toast.LENGTH_SHORT).show();
			}while(mCursor.moveToNext());
		}
		return listCeDuan;
	}
	
	
	
	
	//初始化ListView;
	private void initListView() {
		////adapter=new CeDuanAdapter(CeDunSetActivity.this, R.layout.ceduan_item, ceduanList);
		
		
		//BooksList.setAdapter(new BooksListAdapter(this, mCursor));
		//初始化listView的数据，与数据库相联系；
		//首先调用从数据库里查询数据的方法queryFromDB
		List<CeDuan> ceduans=queryFromDB();
		 //ArrayList<CeDuan> list = new ArrayList<CeDuan>();  
		//把查询到的数据保存到ceduanList中
		for(CeDuan ceduan:ceduans){
			//HashMap<String, String>map=new HashMap<String, String>();
			//map.put("from", ceduan.getFrom());
			//map.put("to", ceduan.getTo());
			
			if(ceduanList.contains(ceduan)){
				
			}else{
				ceduanList.add(ceduan);
			}
		}
		////listView.setAdapter(adapter);
	////adapter.notifyDataSetChanged();
		listView.setAdapter(new mAdapter(this, mCursor));////
		
		
		
		
		
		//为listView设置上下文菜单项目；
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {
				conMenu.setHeaderTitle("ContextMenu");
				conMenu.add(0, 0, 0, "Delete this!");
				conMenu.add(0, 1, 1, "Edit this!");
			}
		});
		
		//为listView设置条目的点击监听事件，事先跳转至数据采集的测站界面；
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//ceduan=(CeDuan) listView.getAdapter().getItem(arg2);
				mCursor.moveToPosition(arg2);
				//Toast.makeText(CeDunSetActivity.this, "position:"+arg2, 200).show();
				Intent st=new Intent(CeDunSetActivity.this,StationActivity.class);

				st.putExtra("from", mCursor.getString(1).toString());
				st.putExtra("to", mCursor.getString(2).toString());
				
				st.putExtra("id", mCursor.getInt(0));
				st.putExtra("position", arg2);
				
				startActivity(st);
				Toast.makeText(CeDunSetActivity.this, "id:"+mCursor.getInt(0), 200).show();
			}
		});
		
		//为listView设置条目 的长按弹出对话框事件；
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				mCursor.moveToPosition(position);
				psi=mCursor;
				ceduan=new CeDuan(psi.getString(1), psi.getString(2));
				return false;
			}
		});
		
	}

	
	 @Override
	//为listView设置上下文菜单项目的点击事件；
		public boolean onContextItemSelected(MenuItem item) {
			ContextMenuInfo menuInfo=item.getMenuInfo();
			
			switch(item.getItemId()){
			case CONTEXTMENU_DELETEITEM://点击上下文菜单的删除项目时：
			     new AlertDialog.Builder(CeDunSetActivity.this)
					.setTitle("删除测段")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(CeDunSetActivity.this, "点击了确定", 200).show();
							////CeDuan ceduan=(CeDuan) listView.getAdapter().getItem(psi);
							//删除选中的ListView测段
						     ////ceduanList.remove(ceduan);  
						    //// listView.setAdapter(adapter); 
						    //// adapter.notifyDataSetChanged();
							
						     //在数据库中删除此条目
						     deleteFromDB();
						}

						private void deleteFromDB() {
							// TODO Auto-generated method stub
							CEDUAN_ID=psi.getInt(0);
							if (CEDUAN_ID == 0) {
								return;
								}
								mCeDuanDB.delete(CEDUAN_ID);
								mCursor.requery();
								listView.invalidateViews();
								
								Toast.makeText(CeDunSetActivity.this, "Delete Successed!:"+CEDUAN_ID, Toast.LENGTH_SHORT).show();
						}
					})
					.setNegativeButton("取消",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(CeDunSetActivity.this, "放弃删除", 200).show();
						}
					})
					.show();
			     return true;
			     
			case CONTEXTMENU_EDITITEM:
				//获取点击的listView条目的位置的测段对象实例；
				////ceduan=(CeDuan) listView.getAdapter().getItem(psi.getInt(0));
				//设置弹出的编辑对话框的布局。同时实例化对话框中的两个EditText；
				//也就是把刚才获得的ceduan对象的值写入到EditText中去，准备编辑；
				
				LinearLayout editLayout=(LinearLayout) getLayoutInflater().inflate(R.layout.ceduan_edit, null);
				editFrom = (EditText)editLayout.findViewById(R.id.edit_from);
				editFrom.setText(ceduan.getFrom().toString());
				editTo = (EditText) editLayout.findViewById(R.id.edit_to);
				editTo.setText(ceduan.getTo().toString());
				//弹出对话框，并把刚才的布局文件设置给它；
				new AlertDialog.Builder(CeDunSetActivity.this)
				.setTitle("编辑测段")
				.setView(editLayout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(!TextUtils.isEmpty(editFrom.getText().toString()) && !TextUtils.isEmpty(editTo.getText().toString())){
							ceduan.setFrom(editFrom.getText().toString());
						ceduan.setTo(editTo.getText().toString());
						
						CEDUAN_ID=psi.getInt(0);
						//更新数据库
						mCeDuanDB.update(CEDUAN_ID, ceduan.getFrom(), ceduan.getTo(),0,0,0);
						mCursor.requery();
						listView.invalidateViews();
						Toast.makeText(CeDunSetActivity.this, "Update Successed!", Toast.LENGTH_SHORT).show();
						
						//adapter.notifyDataSetChanged();
						}else{
							Toast.makeText(CeDunSetActivity.this, "测段起止值均不能为空", 200).show();
						}
					
					}
				})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						//点击取消就关闭对话了，什么都不用做
					}
				})
				.show();
				return true;
			}
			
			//return super.onContextItemSelected(item);
			return false;
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}


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
private CeDuan ceduan; //������ζ���

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
		 
		//CeDuan cd=new CeDuan("��ʼ��", "�����");
		//ceduanList.add(cd);
		//�������ݿ�
		mCeDuanDB=new CeDuanDBHelper(CeDunSetActivity.this, "20170223");
		mCursor=mCeDuanDB.select();
		//mCursor3=mCeDuanDB.select3();
		initListView();//��ʼ��ListView;

	
		

		//��Ӳ�ΰ�ť�ĵ���¼�
		addData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String from = etFrom.getText().toString();
				String to = etTo.getText().toString();
				
				addToDB(from,to,0,0,0);
				
				CeDuan cd=new CeDuan(from, to);
				////ceduanList.add(cd);
				////adapter.notifyDataSetChanged();
				Toast.makeText(CeDunSetActivity.this, "������ݳɹ�", Toast.LENGTH_SHORT).show();
				//������ݿ�Ĳ����߼�ҲҪ��������ɣ�
				
				//queryFromDB();
			}

			
		});
		
		
	}
	//�����ݿ�����Ӳ������
	public void addToDB(String from,String to,int backup,int backmid,int backdown){
		// TODO Auto-generated method stub
		//!TextUtils.isEmpty
		
		if(TextUtils.isEmpty(from)||TextUtils.isEmpty(to)){
			Toast.makeText(this, "���ݲ���Ϊ��ֵ!", Toast.LENGTH_SHORT).show();
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
	//�����ݿ����ѯ�������
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
	
	
	
	
	//��ʼ��ListView;
	private void initListView() {
		////adapter=new CeDuanAdapter(CeDunSetActivity.this, R.layout.ceduan_item, ceduanList);
		
		
		//BooksList.setAdapter(new BooksListAdapter(this, mCursor));
		//��ʼ��listView�����ݣ������ݿ�����ϵ��
		//���ȵ��ô����ݿ����ѯ���ݵķ���queryFromDB
		List<CeDuan> ceduans=queryFromDB();
		 //ArrayList<CeDuan> list = new ArrayList<CeDuan>();  
		//�Ѳ�ѯ�������ݱ��浽ceduanList��
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
		
		
		
		
		
		//ΪlistView���������Ĳ˵���Ŀ��
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu conMenu, View view , ContextMenuInfo info) {
				conMenu.setHeaderTitle("ContextMenu");
				conMenu.add(0, 0, 0, "Delete this!");
				conMenu.add(0, 1, 1, "Edit this!");
			}
		});
		
		//ΪlistView������Ŀ�ĵ�������¼���������ת�����ݲɼ��Ĳ�վ���棻
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
		
		//ΪlistView������Ŀ �ĳ��������Ի����¼���
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
	//ΪlistView���������Ĳ˵���Ŀ�ĵ���¼���
		public boolean onContextItemSelected(MenuItem item) {
			ContextMenuInfo menuInfo=item.getMenuInfo();
			
			switch(item.getItemId()){
			case CONTEXTMENU_DELETEITEM://��������Ĳ˵���ɾ����Ŀʱ��
			     new AlertDialog.Builder(CeDunSetActivity.this)
					.setTitle("ɾ�����")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(CeDunSetActivity.this, "�����ȷ��", 200).show();
							////CeDuan ceduan=(CeDuan) listView.getAdapter().getItem(psi);
							//ɾ��ѡ�е�ListView���
						     ////ceduanList.remove(ceduan);  
						    //// listView.setAdapter(adapter); 
						    //// adapter.notifyDataSetChanged();
							
						     //�����ݿ���ɾ������Ŀ
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
					.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(CeDunSetActivity.this, "����ɾ��", 200).show();
						}
					})
					.show();
			     return true;
			     
			case CONTEXTMENU_EDITITEM:
				//��ȡ�����listView��Ŀ��λ�õĲ�ζ���ʵ����
				////ceduan=(CeDuan) listView.getAdapter().getItem(psi.getInt(0));
				//���õ����ı༭�Ի���Ĳ��֡�ͬʱʵ�����Ի����е�����EditText��
				//Ҳ���ǰѸղŻ�õ�ceduan�����ֵд�뵽EditText��ȥ��׼���༭��
				
				LinearLayout editLayout=(LinearLayout) getLayoutInflater().inflate(R.layout.ceduan_edit, null);
				editFrom = (EditText)editLayout.findViewById(R.id.edit_from);
				editFrom.setText(ceduan.getFrom().toString());
				editTo = (EditText) editLayout.findViewById(R.id.edit_to);
				editTo.setText(ceduan.getTo().toString());
				//�����Ի��򣬲��ѸղŵĲ����ļ����ø�����
				new AlertDialog.Builder(CeDunSetActivity.this)
				.setTitle("�༭���")
				.setView(editLayout)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(!TextUtils.isEmpty(editFrom.getText().toString()) && !TextUtils.isEmpty(editTo.getText().toString())){
							ceduan.setFrom(editFrom.getText().toString());
						ceduan.setTo(editTo.getText().toString());
						
						CEDUAN_ID=psi.getInt(0);
						//�������ݿ�
						mCeDuanDB.update(CEDUAN_ID, ceduan.getFrom(), ceduan.getTo(),0,0,0);
						mCursor.requery();
						listView.invalidateViews();
						Toast.makeText(CeDunSetActivity.this, "Update Successed!", Toast.LENGTH_SHORT).show();
						
						//adapter.notifyDataSetChanged();
						}else{
							Toast.makeText(CeDunSetActivity.this, "�����ֵֹ������Ϊ��", 200).show();
						}
					
					}
				})
				.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						//���ȡ���͹رնԻ��ˣ�ʲô��������
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


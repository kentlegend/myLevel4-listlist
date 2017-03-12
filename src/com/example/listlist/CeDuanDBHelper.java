package com.example.listlist;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;

public class CeDuanDBHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = ".db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "ceduan_table";
	private final static String TABLE2_NAME = "cezhan_table";
	public final static String CEDUAN_ID = "ceduan_id";
	public final static String FROM_NAME = "from_name";
	public final static String TO_NAME = "to_name";
	public final static String CEZHAN_ID = "cezhan_id";
	
	public final static String BACK_UP = "back_up";
	public final static String BACK_DOWN = "back_down";
	public final static String BACK_MID = "back_mid";
	public final static String FRONT_MID= "front_mid";
	public final static String FRONT_UP= "front_up";
	public final static String FRONT_DOWN = "front_down";
	public final static String FRONT_RED = "front_red";
	public final static String BACK_RED = "back_red";
	public CeDuanDBHelper(Context context,String name) {
		// 建立数据库
		super(context, name+DATABASE_NAME, null, DATABASE_VERSION);
		
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + CEDUAN_ID
				+ " INTEGER primary key autoincrement, " + FROM_NAME + 
				" text,"+ TO_NAME +" text,"+BACK_UP+" int,"+BACK_DOWN+" int,"+BACK_MID+" int);";
				db.execSQL(sql);
		
		String sql2 = "CREATE TABLE " + TABLE2_NAME + " (" + CEZHAN_ID
						+ " INTEGER primary key autoincrement, " +CEDUAN_ID+" int,"+ FROM_NAME + 
						" text,"+ TO_NAME +" text,"+BACK_UP+" int,"+BACK_DOWN+" int,"+BACK_MID+" int,"
						+FRONT_MID+" int,"+FRONT_UP+" int,"+FRONT_DOWN+" int,"+FRONT_RED+" int,"
								+BACK_RED+" int);";
						db.execSQL(sql2);
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public Cursor select(){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE_NAME, null, null, null, null, null, null);
		
		return cursor;
		
	}
	
	public Cursor select4(){
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cursor=db.query(TABLE2_NAME, null, null, null, null, null, null);
		
		return cursor;
		
	}
	
	
	public Cursor select2(int id,String fromname,String toname){
		SQLiteDatabase db=this.getReadableDatabase();
		String where=CEDUAN_ID+"=? and "+FROM_NAME+"=? and "+TO_NAME+"=?";
		String[] whereValue={Integer.toString(id),fromname,toname};
		
		Cursor cursor=db.query(TABLE_NAME, new String[]{BACK_UP,BACK_DOWN,BACK_MID}, where, whereValue, null, null, null);
		
		return cursor;
		
	}
	
	public Cursor select3(int ceduanid,String fromname,String toname){
		SQLiteDatabase db=this.getReadableDatabase();
		String where=CEDUAN_ID+"=? and "+FROM_NAME+"=? and "+TO_NAME+"=?";
		String[] whereValue={Integer.toString(ceduanid),fromname,toname};
		
		Cursor cursor3=db.query(TABLE2_NAME, new String[]{CEZHAN_ID,BACK_UP,BACK_DOWN,BACK_MID,FRONT_MID,FRONT_UP,FRONT_DOWN,FRONT_RED,BACK_RED}, where, whereValue, null, null, null);
		
		return cursor3;
		
	}
	
	
	
	private  List<CeDuan> queryFromDB() {
		// TODO Auto-generated method stub
		ArrayList<CeDuan> listCeDuan=new ArrayList<CeDuan>();
		Cursor mCursor=select();
		if(mCursor.moveToFirst()){
			
			do{
			String from=mCursor.getString(mCursor.getColumnIndex("from_name"));
			String to=mCursor.getString(mCursor.getColumnIndex("to_name"));
			CeDuan ceduan=new CeDuan(from,to);
			listCeDuan.add(ceduan);
			  }while(mCursor.moveToNext());
		}
		return listCeDuan;
	}
	
	// 插入数据
	public long insert(String fromname,String toname,int backup,int backdown,int backmid){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(FROM_NAME, fromname);
		cv.put(TO_NAME, toname);
		cv.put(BACK_UP, backup);
		cv.put(BACK_DOWN, backdown);
		cv.put(BACK_MID, backmid);
		long row=db.insert(TABLE_NAME, null, cv);
		return row;
		
	}
	//插入测站数据
	public long insert3(int ceduanid,String fromname,String toname,int backup,int backdown,int backmid,int frontmid,int frontup,int frontdown,int frontred,int backred){
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.put(CEDUAN_ID, ceduanid);
		cv.put(FROM_NAME, fromname);
		cv.put(TO_NAME, toname);
		cv.put(BACK_UP, backup);
		cv.put(BACK_DOWN, backdown);
		cv.put(BACK_MID, backmid);
		
		cv.put(FRONT_MID, frontmid);
		cv.put(FRONT_UP, frontup);
		cv.put(FRONT_DOWN, frontdown);
		cv.put(FRONT_RED, frontred);
		cv.put(BACK_RED, backred);
		long row=db.insert(TABLE2_NAME, null, cv);
		return row;
		
	}
	
	
	
	// 删除数据
	public void delete(int id){
		SQLiteDatabase db=this.getWritableDatabase();
		String where=CEDUAN_ID+"=?";
		String[] whereValue={Integer.toString(id)};
	db.delete(TABLE_NAME, where, whereValue);
	
	
	Cursor cursor=db.query(TABLE2_NAME, null, where, whereValue, null, null, null);
	if(cursor.moveToFirst()){
		
		do{
		db.delete(TABLE2_NAME, where, whereValue);
		
		}while (cursor.moveToNext());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	// 修改数据
	public void update(int id,String fromname,String toname,int backup,int backdown,int backmid){
		SQLiteDatabase db=this.getWritableDatabase();
		String where=CEDUAN_ID+"=?";
		String[] whereValue={Integer.toString(id)};
		ContentValues cv=new ContentValues();
		cv.put(FROM_NAME, fromname);
		cv.put(TO_NAME, toname);
		db.update(TABLE_NAME, cv, where, whereValue);
		
	}
	
	public void update2(int id,String fromname,String toname,int backup,int backdown,int backmid){
		SQLiteDatabase db=this.getWritableDatabase();
		String where=CEDUAN_ID+"=? and "+FROM_NAME+"=? and "+TO_NAME+"=?";
		String[] whereValue={Integer.toString(id),fromname,toname};
		ContentValues cv=new ContentValues();
		cv.put(BACK_UP, backup);
		cv.put(BACK_DOWN,backdown);
		cv.put(BACK_MID, backmid);
		db.update(TABLE_NAME, cv, where, whereValue);
		
	}
	
	
}

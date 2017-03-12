package com.example.listlist;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class mAdapter extends BaseAdapter{
private Context mContext;
private Cursor mCursor;

public mAdapter(Context context,Cursor cursor){
mContext = context;
mCursor = cursor;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView mTextView=new TextView(mContext);
		mCursor.moveToPosition(position);
		mTextView.setTextSize(20);
		mTextView.setText(mCursor.getString(1) + "___" + mCursor.getString(2) + "___" + mCursor.getString(3) + "___" + mCursor.getString(4) + "___" + mCursor.getString(5));
		return mTextView;
	}
	
}

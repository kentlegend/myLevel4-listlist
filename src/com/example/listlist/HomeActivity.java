package com.example.listlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
private GridView gvHome;
private String[] mItems=new String[]{"新建工程","设置","开始测量","输出","平差计算","其他功能"};
private int[] mPics=new int[]{R.drawable.home_apps,R.drawable.home_settings,R.drawable.home_tools,R.drawable.home_netmanager,R.drawable.home_taskmanager,R.drawable.home_trojan};
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		gvHome=(GridView) findViewById(R.id.gv_home);
		gvHome.setAdapter(new HomeAdapter());
		gvHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent intent_newProject=new Intent(HomeActivity.this,NewProjectActivity.class);
					startActivity(intent_newProject);
					break;
				
				case 2:
					Intent intent=new Intent(HomeActivity.this,CeDunSetActivity.class);
					startActivity(intent);
					Toast.makeText(HomeActivity.this, "kaishicl", Toast.LENGTH_LONG).show();
					break;

				default:
					break;
				}
			}
		});
	}

	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=View.inflate(HomeActivity.this, R.layout.home_list_item,null);
			ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
			TextView tvItem =  (TextView) view.findViewById(R.id.tv_item);
			tvItem.setText(mItems[position]);
			ivItem.setImageResource(mPics[position]);
			return view;
		}
		
	}
}

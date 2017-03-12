package com.example.listlist;

import android.app.Activity;
import android.os.Bundle;

public class EditCeDuanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ceduan_edit);
	}
//可以考虑在这个编辑对话框的活动结束后，通过判断起止值是否为空，进行数据库的相关更新操作
	//因为编辑测段的逻辑已经写在了AlartDialog中了，避免代码过于拥挤；
}        

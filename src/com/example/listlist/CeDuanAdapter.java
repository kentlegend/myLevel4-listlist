package com.example.listlist;

import java.util.List;

import android.content.Context;
import android.gesture.GestureOverlayView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CeDuanAdapter extends ArrayAdapter<CeDuan> {


	private int resourceId;
private int psi;

	public CeDuanAdapter(Context context, int textViewResourceId, List<CeDuan> objects) {
		super(context, textViewResourceId, objects);
		resourceId=textViewResourceId;
	}
@Override
	public View getView(int position,View convertView ,ViewGroup parent){
		CeDuan ceduan=getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView from=(TextView) view.findViewById(R.id.from_from);
		TextView to=(TextView) view.findViewById(R.id.to_to);
		from.setText(ceduan.getFrom());
		to.setText(ceduan.getTo());
		psi=position;
		return view;
		
	}
}

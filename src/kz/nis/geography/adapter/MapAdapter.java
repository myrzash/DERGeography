package kz.nis.geography.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MapAdapter extends ArrayAdapter<MapItem> {

	private int resLayout;
	private int resId;

	public MapAdapter(Context context, int resLayout, int resId) {
		super(context, resLayout);
		this.resLayout = resLayout;
		this.resId = resId;
	}

	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(resLayout, null);
		}
		view.findViewById(resId).setVisibility(
				getItem(position).getVisibility());
		view.findViewById(resId).setTag(getItem(position).getTag());
		view.findViewById(resId).setOnTouchListener(new TouchListener());
		return view;
	}

	private class TouchListener implements OnTouchListener {

		@SuppressLint({ "NewApi" })
		@Override
		public boolean onTouch(View v, MotionEvent event) {

				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
				v.startDrag(data, shadowBuilder, v, 0);

			return true;
		}

	}

	public void removeAndChange(int tag) {
		int pos = getPosition(tag);
		remove(getItem(pos));
		notifyDataSetChanged();
	}

	public void changeVisible(int tag, int visibility) {
		int pos = getPosition(tag);
		getItem(pos).setVisibility(visibility);
		notifyDataSetChanged();
	}

	private int getPosition(int tag) {
		for (int i = 0; i < getCount(); i++) {
			if (tag == getItem(i).getTag())
				return i;
		}
		return 0;
	}

}

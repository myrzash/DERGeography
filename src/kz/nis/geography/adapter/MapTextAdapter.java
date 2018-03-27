package kz.nis.geography.adapter;

import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MapTextAdapter extends MapAdapter {

	public MapTextAdapter(Context context) {
		super(context, R.layout.item_map_list,R.id.textViewRow);
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = super.getView(position, view, parent);
		TextView title = (TextView) view.findViewById(R.id.textViewRow);
		title.setTypeface(FontFactory.getFont1(getContext()));
		title.setText(getItem(position).getText());
		return view;
	}

//	public MapTextAdapter(Context context) {
//		super(context, 0);
//	}
//
//	
//
//	public View getView(int position, View view, ViewGroup parent) {
//		if (view == null) {
//			view = LayoutInflater.from(getContext()).inflate(
//					R.layout.item_text, null);
//		}
//		TextView title = (TextView) view.findViewById(R.id.textViewRow);
//		title.setText(getItem(position).getText());
//		title.setTag(getItem(position).getTag());
//		title.setOnTouchListener(new TextTouchListener());
//		return view;
//	}
//
//	private class TextTouchListener implements OnTouchListener {
//
//		@SuppressLint("NewApi")
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//
//			ClipData data = ClipData.newPlainText("", "");
//			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
//			v.startDrag(data, shadowBuilder, v, 0);
//
//			return true;
//		}
//
//	}
//	
//	public void removeAndChange(int tag) {
//		int id = getPosition(tag);
//		Log.d("MapTextAdapter","tag:"+tag+", id:"+id);
//		remove(getItem(id));
//		notifyDataSetChanged();
//	}
//
//	private int getPosition(int tag) {
//		for(int i=0;i<getCount();i++){
//			if(tag == getItem(i).getTag()) return i;
//		}
//		return 0;
//	}

}

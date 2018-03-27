package kz.nis.geography.adapter;

import kz.nis.geography.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MapImageAdapter extends MapAdapter {

	
	public MapImageAdapter(Context context) {
		super(context, R.layout.item_image_map,R.id.imageViewRow);
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = super.getView(position, view, parent);
		ImageView icon = (ImageView) view.findViewById(R.id.imageViewRow);
		icon.setImageResource(getItem(position).getIconRes());
		return view;
	}
//	public MapImageAdapter(Context context) {
//		super(context, 0);
//	}
//
//	public View getView(final int position, View view, ViewGroup parent) {
//		if (view == null) {
//			view = LayoutInflater.from(getContext()).inflate(
//					R.layout.item_image_map, null);
//		}
//		ImageView icon = (ImageView) view.findViewById(R.id.imageViewRow);
//		icon.setImageResource(getItem(position).getIconRes());//	icon.setBackgroundResource(getItem(position).getIconRes());
//		icon.setTag(getItem(position).getTag());
//		icon.setOnTouchListener(new ImageTouchListener());
//		return view;
//	}
//
//	private class ImageTouchListener implements OnTouchListener {
//
//		@SuppressLint("NewApi")
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//
//			ClipData data = ClipData.newPlainText("", "");			
//			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);		 			 
//			v.startDrag(data, shadowBuilder, v, 0);
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

package kz.nis.geography.adapter;

import kz.nis.geography.extra.FontFactory;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AttrListAdapter extends ArrayAdapter<String> {

	private Context context;

	public AttrListAdapter(Context context, int resource,
			int textViewResourceId, String[] objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		((TextView)view).setTypeface(FontFactory.getFont1(context));
		return view;
	}

}

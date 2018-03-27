package kz.nis.geography.adapter;

import java.text.MessageFormat;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

		private int SELECT = 0;
		private LayoutInflater layoutInflater;
		private String[] attrs;
		private String[] values;
		private Typeface typeface;

		public ListAdapter(Context context, String[] attrs, String[] values) {
			// TODO Auto-generated constructor stub
			layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.attrs = attrs;	
			this.values = values;	
			this.typeface = FontFactory.getFont1(context);
		}

		@Override
		public int getCount() {
			// Set the count value to the total number of items in the Array
			return attrs.length;
		}

		@Override
		public CharSequence getItem(int position) {
			return MessageFormat.format(attrs[position], Main.LANG)+":";
		}		

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void changeData(int selection){
			this.SELECT = selection;
			notifyDataSetChanged();
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View listItem = convertView;	
			if (listItem == null) {
				listItem = layoutInflater.inflate(R.layout.item_list_info, null);				
			}			

			TextView textAttr = (TextView) listItem.findViewById(R.id.textViewItemAttr);
			textAttr.setTypeface(typeface);			
			textAttr.setText(getItem(position));
			TextView textInfo = (TextView) listItem.findViewById(R.id.textViewItemValue);
			textInfo.setTypeface(typeface);			
			textInfo.setText(MessageFormat.format(values[SELECT], position));
			return listItem;
		}		
	}
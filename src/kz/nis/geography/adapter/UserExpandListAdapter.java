package kz.nis.geography.adapter;

import java.text.MessageFormat;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UserExpandListAdapter extends BaseExpandableListAdapter {

	private String[] NAMES;
	private String[] TIMES;
	private String[] CHILD_ITEMS;
	private double[][] SCORES;
	
	private LayoutInflater inflater;
	private Resources res;
	private Typeface font;
	

	public UserExpandListAdapter(Context context, String[] names, String[] times, double[][] scores) {
		this.NAMES = names;
		this.TIMES = times;
		this.SCORES = scores;
		this.CHILD_ITEMS =  context.getResources().getStringArray(R.array.TITLES_COVERPAGE);
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		res = context.getResources();
		font = FontFactory.getFont1(context);
	}
	
	@Override
	public int getGroupCount() {
		return NAMES.length;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 3;
	}
	
	private int getBkgColor(int groupPosition) {
		if(groupPosition%2==0) return R.color.neutral;
		else return R.color.white;
	}

	@Override
	public String getGroup(int groupPosition) {
		return NAMES[groupPosition];
	}

	@Override
	public Double getChild(int groupPosition, int childPosition) {
		return SCORES[groupPosition][childPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.expandlist_parent_layout, parent,
					false);
		}
		convertView.setBackgroundColor(res.getColor(getBkgColor(groupPosition)));
		
		TextView textUserName = (TextView) convertView
				.findViewById(R.id.textViewUserName);
		textUserName.setTypeface(font);
		textUserName.setText(NAMES[groupPosition]);
		
		TextView textViewSumTime = (TextView) convertView
				.findViewById(R.id.textViewSumTime);
		textViewSumTime.setTypeface(font);
		textViewSumTime.setText(TIMES[groupPosition]);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.expandlist_child_layout, parent, false);
		}
		TextView textViewChild = (TextView) convertView.findViewById(R.id.textViewChild);
		textViewChild.setTypeface(font);
		String attr = MessageFormat.format(CHILD_ITEMS[childPosition],Main.LANG);		
		String text = String.format("%s: %.2f",attr , getChild(groupPosition, childPosition))+"%";
		textViewChild.setText(text);
		((ProgressBar) convertView.findViewById(R.id.progressBarScore)).setProgress((int)Math.round(getChild(groupPosition, childPosition)));

		return convertView;
	}
	

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}

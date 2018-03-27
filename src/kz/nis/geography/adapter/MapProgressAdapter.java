package kz.nis.geography.adapter;

import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MapProgressAdapter extends ArrayAdapter<MapItem> {

	private static Resources resources = null;
	private static int text_size = 18;
	private static int text_size_big = 24;
	public int SELECTED = -1;
	private static final int padding = 10;
	private static int COLOR_PRIMARY = R.color.red;
	private static int COLOR_SECONDARY = R.color.text_color_apptheme;

	public MapProgressAdapter(Context context) {
		super(context, 0);
		if (resources == null)
			resources = context.getResources();
	}

	public View getView(final int position, View view, ViewGroup parent) {
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(
					R.layout.item_map_list, null);
		}
		TextView title = (TextView) view.findViewById(R.id.textViewRow);
		title.setTypeface(FontFactory.getFont1(getContext()));
		title.setGravity(Gravity.CENTER);
		title.setPadding(padding, padding, padding, padding);
		TextView textCount = (TextView) view.findViewById(R.id.textViewCounter);
		textCount.setTypeface(FontFactory.getFont1(getContext()));

		title.setText(getItem(position).getText() + ":");
		textCount.setText("" + getItem(position).getProgress());
		if (position == SELECTED) {
			title.setTextSize(text_size_big);
			title.setTextColor(resources.getColor(COLOR_PRIMARY));
			textCount.setTextSize(text_size_big);
			textCount.setTextColor(resources.getColor(COLOR_PRIMARY));
//			view.setBackgroundResource();
		} else {
			title.setTextSize(text_size);
			title.setTextColor(resources.getColor(COLOR_SECONDARY));
			textCount.setTextSize(text_size);
			textCount.setTextColor(resources.getColor(COLOR_SECONDARY));
//			view.setBackgroundColor(Color.TRANSPARENT);
		}

		return view;
	}

	public void decrementCount(int position) {
		int newCount = getItem(position).getProgress() - 1;
		getItem(position).setProgress(newCount);
		this.notifyDataSetChanged();
	}

	public void remove(int position) {
		this.remove(getItem(position));
		this.SELECTED = -1;
		notifyDataSetChanged();
	}
}

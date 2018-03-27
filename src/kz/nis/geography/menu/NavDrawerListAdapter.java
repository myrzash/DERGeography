package kz.nis.geography.menu;

import java.util.ArrayList;

import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {		
		return navDrawerItems.get(position);
	}
	
	
	public void changeResult(double res) {		
		navDrawerItems.get(3).setCount(res);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        txtTitle.setTypeface(FontFactory.getFont1(context));
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
 	   txtCount.setTypeface(FontFactory.getFont1(context));
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar1);
        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        
        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCountFromString());
        	progressBar.setProgress(navDrawerItems.get(position).getCountFromInt());
        }else{
        	txtCount.setVisibility(View.GONE);
        	progressBar.setVisibility(View.GONE);
        }        
        return convertView;
	}

}

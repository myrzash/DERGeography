package kz.nis.geography;

import kz.nis.geography.adapter.DBAdapter;
import kz.nis.geography.adapter.UserExpandListAdapter;
import kz.nis.geography.extra.FontFactory;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ActivityResults extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		TextView attr1 = (TextView) findViewById(R.id.textViewAttr1);
		attr1.setText(Main.getTranslate(this, R.string.user_name));
		attr1.setTypeface(FontFactory.getFont1(this));

		TextView attr2 = (TextView) findViewById(R.id.textViewAttr2);
		attr2.setText(Main.getTranslate(this, R.string.sum_time));
		attr2.setTypeface(FontFactory.getFont1(this));
		ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.expandableListViewResults);
		
		DBAdapter dbAdapter = new DBAdapter(this);
		Cursor cursor = dbAdapter.getAllUsers();
		int count = cursor.getCount();
		String[] names = new String[count];
		String[] times = new String[count];
		double[][] scores = new double[count][3];
		int pos = -1;
		if (cursor.moveToFirst()) {
			int i = 0;
			do {
				names[i] = cursor.getString(0);
				if (names[i].equals(Main.USER_NAME))
					pos = i;
				times[i] = dbAdapter.getAllTimes(names[i]);
				scores[i][0] = (cursor.getDouble(2)+cursor.getDouble(3)+cursor.getDouble(4) )/3;
				scores[i][1] = (cursor.getDouble(5)+cursor.getDouble(6)+cursor.getDouble(7) )/3;
				scores[i][2] = (cursor.getDouble(8)+cursor.getDouble(9) )/2;
				i++;
			} while (cursor.moveToNext());

			expandListView.setAdapter(new UserExpandListAdapter(this, names,
					times, scores));
			if (pos != -1)
				expandListView.expandGroup(pos);
		}
		if(dbAdapter!=null) dbAdapter.close();
		
		Button btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}

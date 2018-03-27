package kz.nis.geography;

import java.text.MessageFormat;

import kz.nis.geography.extra.FontFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityInfo3 extends ListMapActivity {

	private static int[] BUTTONS = new int[] { R.id.city1, R.id.city3, R.id.city4, R.id.city5, R.id.city6, R.id.city7,
			R.id.city8, R.id.city9, R.id.city10, R.id.city11, 
			R.id.city13, R.id.city14, R.id.city16, R.id.city17,
			R.id.city18, R.id.city19, R.id.city20, R.id.city21, R.id.city22,
			R.id.city23, R.id.city24, R.id.city25, R.id.city27,
			R.id.city28 };

	public ActivityInfo3() {
		super(R.layout.info_3,BUTTONS);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateButtons(getResources().getStringArray(R.array.Cities_name),
				getResources().getStringArray(getArrayRes(Main.LANG)));
	}
	private int getArrayRes(int lang) {
		int arrayRes = 0;
		switch (lang) {
		case 0:
			arrayRes = R.array.Cities_content_kz;
			break;
		case 1:
			arrayRes = R.array.Cities_content_ru;
			break;
		case 2:
			arrayRes = R.array.Cities_content_en;
			break;
		default:
			arrayRes = R.array.Cities_content_kz;
			break;
		}
		return arrayRes;
	}

	private void populateButtons(final String[] titles, final String[] texts) {
		for (int i = 0; i < BUTTONS.length; i++) {
			Button btn = (Button) findViewById(BUTTONS[i]);
			btn.setTypeface(FontFactory.getFont1(getApplicationContext()));
			btn.setText(MessageFormat.format(titles[i],Main.LANG));
			btn.setTag(i);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = (Integer) v.getTag();
					TextView textInfo = (TextView) findViewById(R.id.textViewInform);
					textInfo.setTypeface(FontFactory
							.getFont1(getApplicationContext()));
					textInfo.setText(texts[pos]);
					for (int i = 0; i < BUTTONS.length; i++) {
						findViewById(BUTTONS[i]).setEnabled(false);
					}
					zoomImageFromThumb(v, (MessageFormat.format(titles[pos],Main.LANG)));
				}

			});
		}
	}

}
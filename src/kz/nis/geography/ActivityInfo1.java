package kz.nis.geography;

import java.text.MessageFormat;

import kz.nis.geography.extra.FontFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityInfo1 extends ListMapActivity {

	private static int[] BUTTONS = new int[] { R.id.container1,
			R.id.container2, R.id.container3, R.id.container4, R.id.container5 };
	private static int[] BKGS_ALPHA = new int[] {
			R.drawable.map_obl_vko_entered, R.drawable.map_zapad_entered,
			R.drawable.map_sever_entered, R.drawable.map_obl_karaganda_entered,
			R.drawable.map_iug_entered };

	public ActivityInfo1() {
		super(R.layout.info_1, BUTTONS);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateButtons(getResources().getStringArray(R.array.Countries_name),
				getResources().getStringArray(getArrayRes(Main.LANG)));
	}

	private int getArrayRes(int lang) {
		int arrayRes = 0;
		switch (lang) {
		case 0:
			arrayRes = R.array.Countries_content_kz;
			break;
		case 1:
			arrayRes = R.array.Countries_content_ru;
			break;
		case 2:
			arrayRes = R.array.Countries_content_en;
			break;
		default:
			arrayRes = R.array.Countries_content_kz;
			break;
		}
		return arrayRes;
	}

	private void populateButtons(final String[] titles, final String[] texts) {
		
		Log.d(Main.LOG,"titles:"+titles.length+", texts:"+texts.length);
		for (int i = 0; i < BUTTONS.length; i++) {
			Button btn = (Button) findViewById(BUTTONS[i]);
			btn.setTypeface(FontFactory.getFont1(getApplicationContext()));
			btn.setText(MessageFormat.format(titles[i], Main.LANG));
			btn.setTag(i);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int pos = (Integer) v.getTag();
					animateAlpha(pos, v);
				}

				private void animateAlpha(final int pos, final View view) {
					Animation anim = AnimationUtils.loadAnimation(
							getApplicationContext(), android.R.anim.fade_in);
					anim.setDuration(500);
					final ImageView imageRootMap = (ImageView) findViewById(R.id.imageRootMap);
					imageRootMap.setBackgroundResource(BKGS_ALPHA[pos]);
					anim.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							for (int i = 0; i < BUTTONS.length; i++) {
								findViewById(BUTTONS[i]).setEnabled(false);
							}

							zoomImageFromThumb(view, (MessageFormat.format(
									titles[pos], Main.LANG)));
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
						}

						@Override
						public void onAnimationEnd(Animation animation) {
							TextView textInfo = (TextView) findViewById(R.id.textViewInform);
							textInfo.setTypeface(FontFactory
									.getFont1(getApplicationContext()));
							textInfo.setText(texts[pos]);

							imageRootMap.setBackgroundResource(0);

						}
					});
					imageRootMap.startAnimation(anim);
				}

			});
		}
	}

}
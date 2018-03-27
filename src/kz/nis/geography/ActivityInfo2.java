package kz.nis.geography;

import java.text.MessageFormat;

import kz.nis.geography.adapter.ListAdapter;
import kz.nis.geography.extra.FontFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ActivityInfo2 extends ListMapActivity {

	private static int[] BUTTONS = new int[] { R.id.container1,
			R.id.container2, R.id.container3, R.id.container4, R.id.container5,
			R.id.container6, R.id.container7, R.id.container8, R.id.container9,
			R.id.container10, R.id.container11, R.id.container12,
			R.id.container13, R.id.container14 };

	private static int[] BKGS_ALPHA = new int[] {
			R.drawable.map_obl_akmola_entered,
			R.drawable.map_obl_aktobe_entered,
			R.drawable.map_obl_almaty_entered,
			R.drawable.map_obl_atyrau_entered, R.drawable.map_obl_vko_entered,
			R.drawable.map_obl_jambyl_entered, R.drawable.map_obl_zko_entered,
			R.drawable.map_obl_karaganda_entered,
			R.drawable.map_obl_kostanai_entered,
			R.drawable.map_obl_kyzylorda_entered,
			R.drawable.map_obl_mangystau_entered,
			R.drawable.map_obl_pavlodar_entered,
			R.drawable.map_obl_sko_entered, R.drawable.map_obl_uko_entered };

	public ActivityInfo2() {
		super(R.layout.info_2, BUTTONS);
	}

	// LISTVIEW RESULTS
//	private 
	private static ListAdapter adapterListRes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateListRes();
		// int[] icons = { R.drawable.icon_akmola, 0,
		// R.drawable.icon_almaty, R.drawable.icon_atyrau,
		// R.drawable.icon_vko, R.drawable.icon_zhambyl, 0,
		// R.drawable.icon_karaganda, R.drawable.icon_kostanai,
		// R.drawable.icon_kysylorda, R.drawable.icon_mangystau,
		// R.drawable.icon_pavlodar, R.drawable.icon_sko, R.drawable.icon_uko };
//		ImageView imageMap = (ImageView) findViewById(R.id.imageAnim);
//		AnimationDrawable mapAnim =  (AnimationDrawable) imageMap.getBackground();
//		mapAnim.start();
		
		populateButtons(adapterListRes,
				getResources().getStringArray(R.array.Regions_name));
	}

	private int getArrayRes(int lang) {
		int arrayRes = 0;
		switch (lang) {
		case 0:
			arrayRes = R.array.Regions_content_kz;
			break;
		case 1:
			arrayRes = R.array.Regions_content_ru;
			break;
		case 2:
			arrayRes = R.array.Regions_content_en;
			break;
		default:
			arrayRes = R.array.Regions_content_kz;
			break;
		}
		return arrayRes;
	}
	
	private void populateListRes() {
		String[] attrs = getResources().getStringArray(R.array.ATTRS_REGIONS);
		String[] res = getResources().getStringArray(getArrayRes(Main.LANG));
		adapterListRes = new ListAdapter(this, attrs,res);
		ListView listViewRes = (ListView) findViewById(R.id.listViewRes);
		// listViewRes.setSelector(R.drawable.selector);
		listViewRes.setAdapter(adapterListRes);
	}

	private void populateButtons(final ListAdapter listAdapter,
			final String[] titles) {

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
					ImageView imageRootMap = (ImageView) findViewById(R.id.imageRootMap);
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

							listAdapter.changeData(pos);
							
						}
					});
					imageRootMap.startAnimation(anim);
				}
			});
		}
	}
}
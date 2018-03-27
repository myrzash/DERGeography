package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo2;
import kz.nis.geography.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Activity2_1 extends AbstractTaskOne {

	private static int[] buttonsID = new int[] { R.id.container1,
			R.id.container2, R.id.container3, R.id.container4, R.id.container5,
			R.id.container6, R.id.container7, R.id.container8, R.id.container9,
			R.id.container10, R.id.container11, R.id.container12,
			R.id.container13, R.id.container14 };

	private static int[] regions = new int[] { R.drawable.obl_akmola,
			R.drawable.obl_aktobe, R.drawable.obl_almaty,
			R.drawable.obl_atyrau, R.drawable.obl_vko, R.drawable.obl_jambyl,
			R.drawable.obl_zko, R.drawable.obl_karaganda,
			R.drawable.obl_kostanai, R.drawable.obl_kyzylorda,
			R.drawable.obl_mangystau, R.drawable.obl_pavlodar,
			R.drawable.obl_sko, R.drawable.obl_uko };

	private static int[] bkgsDropped = new int[] { R.drawable.map_obl_akmola,
			R.drawable.map_obl_aktobe, R.drawable.map_obl_almaty,
			R.drawable.map_obl_atyrau, R.drawable.map_obl_vko,
			R.drawable.map_obl_jambyl, R.drawable.map_obl_zko,
			R.drawable.map_obl_karaganda, R.drawable.map_obl_kostanai,
			R.drawable.map_obl_kyzylorda, R.drawable.map_obl_mangystau,
			R.drawable.map_obl_pavlodar, R.drawable.map_obl_sko,
			R.drawable.map_obl_uko };

	private static int[] bkgsEntered = new int[] {
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

	public Activity2_1() {
		super(R.layout.exercise_2_1, buttonsID, bkgsDropped, bkgsEntered,
				regions);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findViewById(R.id.imageHome).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			onBackPressed();
			}
		});
		findViewById(R.id.imageViewInfo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Activity2_1.this, ActivityInfo2.class));
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});
	}
	

	@Override
	public void onBackPressed() {
		double res = (double)PROGRESS/MAX_PROGRESS*100;
		dialogFinish(Activity2_1.this,3,res);
	}
}

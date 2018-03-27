package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo2;
import kz.nis.geography.Main;
import kz.nis.geography.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Activity2_2 extends AbstractTaskTwo {

	private static int[] buttonsID = new int[] { R.id.container1,
		R.id.container2, R.id.container3, R.id.container4, R.id.container5,
		R.id.container6, R.id.container7, R.id.container8, R.id.container9,
		R.id.container10, R.id.container11, R.id.container12,
		R.id.container13, R.id.container14 };
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


private int[] mp3 = new int[]{ 0, R.raw.ru_arrange_name_areas, 0};

	public Activity2_2() {
		super(R.layout.exercise_2_1, buttonsID, bkgsEntered,R.array.Regions_name);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		((AbsoluteLayout) findViewById(R.id.map_kz))
		.setBackgroundResource(R.drawable.map_regions);
		((TextView) findViewById(R.id.textViewTask)).setText(Main.getTranslate(getApplicationContext(), R.string.task_2_2));
		findViewById(R.id.imageHome).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			onBackPressed();
			}
		});
		findViewById(R.id.imageViewInfo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Activity2_2.this, ActivityInfo2.class));
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});
		
		if(mp3[Main.LANG] != 0){
			MediaPlayer media = MediaPlayer.create(getApplicationContext(),mp3[Main.LANG]);
			media.start();
		}
	}
	
	@Override
	public void onBackPressed() {
		double res = (double)PROGRESS/MAX_PROGRESS*100;
		dialogFinish(Activity2_2.this,4,res);
	}
}
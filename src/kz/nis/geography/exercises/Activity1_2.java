package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo1;
import kz.nis.geography.Main;
import kz.nis.geography.R;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Activity1_2 extends AbstractTaskTwo {

	private static int[] buttonsID = new int[] { R.id.container1,
			R.id.container2, R.id.container3, R.id.container4, R.id.container5 };

	private static int[] bkgsEntered = new int[] {
			R.drawable.map_obl_vko_entered, R.drawable.map_zapad_entered,
			R.drawable.map_sever_entered, R.drawable.map_obl_karaganda_entered,
			R.drawable.map_iug_entered };
	private int[] mp3 = new int[]{ 0, R.raw.ru_arrange_name_regions, 0};

	public Activity1_2() {
		super(R.layout.exercise_1_1, buttonsID, bkgsEntered,
				R.array.Countries_name);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((AbsoluteLayout) findViewById(R.id.map_kz))
				.setBackgroundResource(R.drawable.map_countries);
		((TextView) findViewById(R.id.textViewTask)).setText(Main.getTranslate(
				getApplicationContext(), R.string.task_1_2));
		findViewById(R.id.imageHome).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			onBackPressed();
			}
		});
		findViewById(R.id.imageViewInfo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Activity1_2.this, ActivityInfo1.class));
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
		dialogFinish(Activity1_2.this,1,res);
	}
}
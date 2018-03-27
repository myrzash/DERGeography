package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo1;
import kz.nis.geography.R;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Activity1_1 extends AbstractTaskOne {

	private static int[] buttonsID = new int[] { R.id.container1,
			R.id.container2, R.id.container3, R.id.container4, R.id.container5 };

	private static int[] countries = new int[] { R.drawable.obl_vko,
			R.drawable.reg_zapad, R.drawable.reg_sever,
			R.drawable.obl_karaganda, R.drawable.reg_iug };

	private static int[] bkgsDropped = new int[] { R.drawable.map_obl_vko,
			R.drawable.map_zapad, R.drawable.map_sever,
			R.drawable.map_obl_karaganda, R.drawable.map_iug };

	private static int[] bkgsEntered = new int[] {
			R.drawable.map_obl_vko_entered, R.drawable.map_zapad_entered,
			R.drawable.map_sever_entered, R.drawable.map_obl_karaganda_entered,
			R.drawable.map_iug_entered };

	public Activity1_1() {
		super(R.layout.exercise_1_1, buttonsID, bkgsDropped, bkgsEntered,
				countries);
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
				startActivity(new Intent(Activity1_1.this, ActivityInfo1.class));
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});
		
	}

	@Override
	public void onBackPressed() {
		double res = (double)PROGRESS/MAX_PROGRESS*100;
		dialogFinish(Activity1_1.this,0,res);
	}
}

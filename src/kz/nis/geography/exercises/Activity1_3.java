package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo1;
import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.adapter.MapItem;
import kz.nis.geography.adapter.MapProgressAdapter;
import kz.nis.geography.extra.FontFactory;
import kz.nis.geography.extra.SoundManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity1_3 extends DialogActivity{

	private int[] mp3 = new int[]{ 0, R.raw.ru_choose_region, 0};
	public Activity1_3() {
		super(R.layout.exercise_1_3);
	}
	private static int[] MAX_PROGRESS = new int[] { 1, 4, 4, 1, 4 };
	private static int[] buttons = new int[] { R.id.container1,
			R.id.container2, R.id.container3, R.id.container4, R.id.container5,
			R.id.container6, R.id.container7, R.id.container8, R.id.container9,
			R.id.container10, R.id.container11, R.id.container12,
			R.id.container13, R.id.container14 };
	private static final int[] TAGS = { 4, 101, 103, 106, 110, 200, 208, 211,
			212, 307, 402, 405, 409, 413 };

	private static AbsoluteLayout.LayoutParams lParams = new AbsoluteLayout.LayoutParams(
			AbsoluteLayout.LayoutParams.MATCH_PARENT,
			AbsoluteLayout.LayoutParams.MATCH_PARENT,0,0);
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
	private AbsoluteLayout bkgMap;

	private MapProgressAdapter adapter;
	private static int PROGRESS;
	private TextView textTask;
	private ImageView[] buf;
	private View[] btns;
	private TextView textScore;
	private static int POSITIVE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();		
		
		if(mp3[Main.LANG] != 0){
			MediaPlayer media = MediaPlayer.create(getApplicationContext(),mp3[Main.LANG]);
			media.start();
		}
	}

//	private void load() {
//		final ProgressDialog loadingdialog = ProgressDialog.show(
//				Activity1_3.this, null,
//				Main.getTranslate(Activity1_3.this, R.string.wait), true);
//		loadingdialog.setCancelable(false);
//		new Thread() {
//			public void run() {
//				try {
//					
//				} catch (Exception e) {
//					Log.e("threadmessage", e.getMessage());
//				}
//				loadingdialog.dismiss();
////				
//			}
//		}.start();
//	}

	private void init() {
		bkgMap = (AbsoluteLayout) findViewById(R.id.map_kz);
		textTask = (TextView) findViewById(R.id.textViewTask);
		textTask.setTypeface(FontFactory.getFont1(getApplicationContext()));
		textTask.setText(Main.getTranslate(getApplicationContext(),
				R.string.task_1_31));
		textScore = (TextView) findViewById(R.id.textScore);
		textScore.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		textScore.setText("0");
		fillList(R.array.Countries_name, MAX_PROGRESS);
		for (int i = 0; i < buttons.length; i++) {
			((Button) findViewById(buttons[i])).setTag(i);
			((Button) findViewById(buttons[i]))
					.setOnClickListener(new onClickListener());
		}
		findViewById(R.id.imageHome).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			onBackPressed();
			}
		});
		
		findViewById(R.id.imageViewInfo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Activity1_3.this, ActivityInfo1.class));
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});
	}
	
	private void fillList(int arrayRes, int[] MAX_PROGRESS) {
		CharSequence[] menus = getResources().getStringArray(arrayRes);
		adapter = new MapProgressAdapter(getApplicationContext());
		for (int i = 0; i < menus.length; i++) {
			adapter.add(new MapItem(menus[i], MAX_PROGRESS[i], i));
		}
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	
		if (adapter.SELECTED == -1) {
			textTask.setText(Main.getTranslate(getApplicationContext(),
					R.string.task_1_32));
			animationTitle(textTask);
		}
		changeItems(position, adapter.SELECTED);
	}

	private void changeItems(int newPos, int oldPos) {
		if (oldPos == newPos)
			return;
		if (oldPos != -1) {
			adapter.getItem(oldPos).setProgress(PROGRESS);
		}
		adapter.SELECTED = newPos;
		adapter.notifyDataSetChanged();
		PROGRESS = adapter.getItem(newPos).getProgress();
		POSITIVE = PROGRESS;
		buf = new ImageView[POSITIVE];
		btns = new View[PROGRESS];
	}

	private void animationTitle(final View view) {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				android.R.anim.fade_in);
		anim.setFillAfter(true);
		anim.setInterpolator(getApplicationContext(),
				android.R.anim.bounce_interpolator);
		view.startAnimation(anim);
	}

	private class onClickListener implements OnClickListener {

		private Animation animChip = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.chip_oneshot);
		
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			if (adapter.SELECTED == -1) {
				textTask.setText(Main.getTranslate(getApplicationContext(),
						R.string.task_1_31));
				animationTitle(textTask);
				return;
			}

			PROGRESS--;
			int tag = adapter.getItem(adapter.SELECTED).getTag() * 100
					+ (Integer) v.getTag();
			for (int i = 0; i < TAGS.length; i++)
				if (tag == TAGS[i]) {
					POSITIVE--;
				}

			v.setEnabled(false);
			btns[PROGRESS] = v;
			ImageView imageContainer = new ImageView(getApplicationContext());
			imageContainer.setBackground(getResources().getDrawable(
					bkgsEntered[(Integer) v.getTag()]));
			bkgMap.addView(imageContainer, lParams);

			buf[PROGRESS] = imageContainer;
			if (PROGRESS == 0) {

				if (POSITIVE == 0) {
					SoundManager.playGood(getApplicationContext());
					adapter.remove(adapter.SELECTED);
					textTask.setText(Main.getTranslate(getApplicationContext(),
							R.string.task_1_31));
					textScore.setText(getScore(adapter.getCount()));
					textScore.startAnimation(animChip);
					if (adapter.getCount() == 0) {
						onBackPressed();
					}
				} else {
					SoundManager.playError(getApplicationContext());
					 Toast.makeText(getApplicationContext(),
								Main.getTranslate(getApplicationContext(), R.string.wrong), Toast.LENGTH_SHORT).show();
					for (short i = 0; i < buf.length; i++) {
						bkgMap.removeView(buf[i]);
						btns[i].setEnabled(true);
					}
					PROGRESS = MAX_PROGRESS[adapter.getItem(adapter.SELECTED)
							.getTag()];
					POSITIVE = PROGRESS;
					adapter.getItem(adapter.SELECTED).setProgress(PROGRESS);
					adapter.notifyDataSetChanged();

				}
			} else {
				adapter.decrementCount(adapter.SELECTED);
			}
		}
		

		private CharSequence getScore(int progress) {
			double score = (1 - (double)progress/5)*100;	
			return ""+(int)score;
		}
	}

	
	
	@Override
	public void onBackPressed() {
		double res = (1 - (double)adapter.getCount()/5)*100;	
		dialogFinish(Activity1_3.this,2,res);
	}
}
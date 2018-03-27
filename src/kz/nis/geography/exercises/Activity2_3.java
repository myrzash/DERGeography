package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo2;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2_3 extends DialogActivity implements OnClickListener {

	public Activity2_3() {
		super(R.layout.exercise_3_1);
	}

	private static int[] MAX_PROGRESS = new int[] { 2, 1, 2, 1, 2, 1, 1, 4, 1,
			2, 2, 2, 1, 2 };
	// 2,12,15,26
	private static int[] buttons = new int[] { R.id.city1, R.id.city3,
			R.id.city4, R.id.city5, R.id.city6, R.id.city7, R.id.city8,
			R.id.city9, R.id.city10, R.id.city11, R.id.city13, R.id.city14,
			R.id.city16, R.id.city17, R.id.city18, R.id.city19, R.id.city20,
			R.id.city21, R.id.city22, R.id.city23, R.id.city24, R.id.city25,
			R.id.city27, R.id.city28 };
	// private static final int[] TAGS = { 0, 12, 103, 125, 204, 220, 306, 314,
	// 419, 424, 521, 601, 623, 709, 710, 805, 813, 818, 907, 915, 1002,
	// 1008, 1116, 1127, 1217, 1311, 1322, 1326 };

	private static final int[] TAGS = { 0, 10, 102, 203, 217, 305, 416, 421,
			518, 620, 704, 708, 709, 715, 811, 906, 912, 1001, 1007, 1113,
			1123, 1214, 1319, 1322 };
	private MapProgressAdapter adapter;
	private static int PROGRESS;
	private TextView textTask;
	private View[] btns;
	private TextView textScore;
	private static int POSITIVE;

	private int[] mp3 = new int[]{ 0, R.raw.ru_choose_area, 0};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		if(mp3[Main.LANG] != 0){
			MediaPlayer media = MediaPlayer.create(getApplicationContext(),mp3[Main.LANG]);
			media.start();
		}
	}

	private void init() {
		textTask = (TextView) findViewById(R.id.textViewTask);
		textTask.setTypeface(FontFactory.getFont1(getApplicationContext()));
		textTask.setText(Main.getTranslate(getApplicationContext(),
				R.string.task_2_31));
		textScore = (TextView) findViewById(R.id.textScore);
		textScore
				.setTypeface(FontFactory.getFontDigit(getApplicationContext()));

		fillList(R.array.Regions_name, MAX_PROGRESS);
		for (int i = 0; i < buttons.length; i++) {
			((Button) findViewById(buttons[i])).setTag(i);
			((Button) findViewById(buttons[i]))
					.setOnClickListener(new onClickListener());
		}
		findViewById(R.id.buttonUp).setOnClickListener(this);
		findViewById(R.id.buttonDown).setOnClickListener(this);
		findViewById(R.id.imageHome).setOnClickListener(this);		
		findViewById(R.id.imageViewInfo).setOnClickListener(this);
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
					R.string.task_2_32));
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
		btns = new View[PROGRESS];
	}

	private class onClickListener implements OnClickListener {

		private Animation animChip = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.chip_oneshot);

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			if (adapter.SELECTED == -1) {
				textTask.setText(Main.getTranslate(getApplicationContext(),
						R.string.task_2_32));
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

			if (PROGRESS == 0) {

				if (POSITIVE == 0) {
					SoundManager.playGood(getApplicationContext());
					adapter.remove(adapter.SELECTED);
					textTask.setText(Main.getTranslate(getApplicationContext(),
							R.string.task_2_31));
					textScore.setText(getScore(adapter.getCount()));
					textScore.startAnimation(animChip);

					if (adapter.getCount() == 0) {
						onBackPressed();
					}
				} else {
					SoundManager.playError(getApplicationContext());
					Toast.makeText(
							getApplicationContext(),
							Main.getTranslate(getApplicationContext(),
									R.string.wrong), Toast.LENGTH_SHORT).show();
					for (short i = 0; i < btns.length; i++) {
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
			double score = (1 - (double) progress / 14) * 100;
			return "" + (int) score;
		}
	}

	private void animationTitle(final View view) {
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				android.R.anim.fade_in);
		anim.setFillAfter(true);
		anim.setInterpolator(getApplicationContext(),
				android.R.anim.bounce_interpolator);
		view.startAnimation(anim);
	}

	@Override
	public void onBackPressed() {
		double res = (1 - (double) adapter.getCount() / 14) * 100;
		dialogFinish(Activity2_3.this, 5, res);
	}

	@Override
	public void onClick(View v) {
		int index = 0;
		switch (v.getId()) {
		case R.id.buttonUp:
			index = getListView().getFirstVisiblePosition();
			getListView().setSelection(index - 1);
			break;
		case R.id.buttonDown:
			index = getListView().getFirstVisiblePosition();
			getListView().setSelection(index + 1);
			break;
			
		case R.id.imageHome:
			onBackPressed();
			break;
		case R.id.imageViewInfo:
			startActivity(new Intent(Activity2_3.this, ActivityInfo2.class));
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		}
	}
}

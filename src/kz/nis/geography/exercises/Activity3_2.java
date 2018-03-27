package kz.nis.geography.exercises;

import java.text.MessageFormat;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import kz.nis.geography.extra.SoundManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Activity3_2 extends Activity {

	private static float TEXT_SIZE_CARD = 0;
	private LinearLayout containerCorrect;
	private LinearLayout containerRandom;
	private LayoutParams lparams;
	private TextView textViewTitle;
	private TextView textScore;
	private LayoutParams lparams2;
	private static int TEXT_COLOR_CARD = 0;
	private static Typeface typeface = null;
	private static int[] ID_CITIES;
	private static String[] CITIES;
	private static int LENGTH_WORD;
	private static int NUMBER_QUESTION;
	private static int PROGRESS;
	private static int COUNT = 10;
	// TIMER
	private TextView timerValue;
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	private long timeInMilliseconds = 0L;
	private long timeSwapBuff = 0L;
	private long updatedTime = 0L;
	private Animation animSelection;
	int[] mp3 = new int[] { 0, R.raw.ru_arrange_name_city, 0 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_3_2);
		NUMBER_QUESTION = 0;
		PROGRESS = 0;
		init();

		if (mp3[Main.LANG] != 0) {
			MediaPlayer media = MediaPlayer.create(getApplicationContext(),
					mp3[Main.LANG]);
			media.start();
		}
	}

	@Override
	protected void onPause() {
		pauseTimer();
		super.onPause();
	}

	@Override
	protected void onResume() {
		startTimer();
		super.onResume();
	}

	protected void init() {
		if (TEXT_SIZE_CARD == 0)
			TEXT_SIZE_CARD = getResources().getDimension(
					R.dimen.text_size_carte);
		if (TEXT_COLOR_CARD == 0)
			TEXT_COLOR_CARD = getResources().getColor(R.color.brown);
		if (typeface == null)
			typeface = FontFactory.getFont1(getApplicationContext());
		containerCorrect = (LinearLayout) findViewById(R.id.containerCorrect);
		containerRandom = (LinearLayout) findViewById(R.id.containerRandom);
		textScore = (TextView) findViewById(R.id.textViewScore);
		textScore
				.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		textViewTitle = (TextView) findViewById(R.id.textViewTask);
		textViewTitle.setTypeface(typeface);
		textViewTitle.setText(Main.getTranslate(getApplicationContext(),
				R.string.task_3_2));
		findViewById(R.id.imageHome).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lparams.setMargins(0, 0, 0, 0);
		lparams2 = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lparams2.setMargins(20, 20, 20, 20);
		CITIES = new String[COUNT];
		String[] citiesStart = getUpperArray(R.array.Cities_name);
		ID_CITIES = randomWithoutRepetition(COUNT, citiesStart.length, 21);
		for (int i = 0; i < COUNT; i++)
			CITIES[i] = citiesStart[ID_CITIES[i]];
		restartQuest(NUMBER_QUESTION);
		findViewById(R.id.buttonInfo).startAnimation(
				AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.anim_one_place_scale));

		timerValue = (TextView) findViewById(R.id.textViewTime);
		timerValue.setTypeface(FontFactory
				.getFontDigit(getApplicationContext()));
		animSelection = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.decrease);
		startTimer();
	}

	@SuppressLint("DefaultLocale")
	private String[] getUpperArray(int citiesName) {
		String[] array = getResources().getStringArray(citiesName);
		for (int i = 0; i < array.length; i++) {
			array[i] = MessageFormat.format(array[i], Main.LANG);
			array[i] = array[i].trim().toUpperCase();
		}
		return array;
	}

	@SuppressLint("NewApi")
	private void restartQuest(int numberQuest) {
		if (numberQuest == COUNT) {
			dialogFinish(100);
			return;
		}
		PROGRESS = 0;
		containerCorrect.removeAllViews();
		String word = CITIES[numberQuest];
		LENGTH_WORD = word.length();
		int[] rotations = new int[LENGTH_WORD];
		rotations = randomWithoutRepetition(LENGTH_WORD, 40);
		for (int i = 0; i < word.length(); i++) {
			Button card = new Button(this);
			card.setTag(String.valueOf(word.charAt(i)));
			card.setBackgroundResource(R.drawable.icon_card);
			card.setTextSize(TEXT_SIZE_CARD);
			card.setTypeface(typeface);
			card.setTextColor(TEXT_COLOR_CARD);
			card.setGravity(Gravity.CENTER);
			card.setLayoutParams(lparams);
			card.setOnDragListener(new DragListener());
			containerCorrect.addView(card);
		}
		//
		int[] rand = randomWithoutRepetition(LENGTH_WORD, LENGTH_WORD);
		String word2 = "";
		for (int i = 0; i < LENGTH_WORD; i++) {
			word2 = word2 + word.charAt(rand[i]);
		}
		//
		int pdng = 10;
		containerRandom.removeAllViews();
		for (int i = 0; i < word2.length(); i++) {
			TextView card = new TextView(this);
			card.setTypeface(typeface);
			card.setText(String.valueOf(word2.charAt(i)));
			card.setTag(String.valueOf(word2.charAt(i)));
			card.setPadding(pdng, pdng, pdng, pdng);
			card.setRotation(rotations[i] - 20);
			card.setTextSize(TEXT_SIZE_CARD);
			card.setTextColor(TEXT_COLOR_CARD);
			card.setGravity(Gravity.CENTER);
			card.setLayoutParams(lparams2);
			card.setOnTouchListener(new TouchListener());//
			containerRandom.addView(card);
		}
	}

	private CharSequence getScore(int progress) {
		double score = (double) progress / COUNT * 100;
		return "" + (int) score;
	}

	private static int[] randomWithoutRepetition(int arrayLength, int maxValue) {

		if (arrayLength > maxValue)
			return null;
		int[] array = new int[arrayLength];
		int i = 0;
		while (i < arrayLength) {
			array[i] = (int) (Math.random() * maxValue);
			int j = 0;
			while (j < i) {
				if (array[j] == array[i]) {
					i--;
					break;
				}
				j++;
			}
			i++;
		}
		return array;
	}

	private static int[] randomWithoutRepetition(int arrayLength, int maxValue,
			int trueAnswer) {

		if (arrayLength > maxValue)
			return null;
		int[] array = new int[arrayLength];
		int i = 0;
		while (i < arrayLength) {
			array[i] = (int) (Math.random() * maxValue);
			if (array[i] == trueAnswer)
				continue;
			int j = 0;
			while (j < i) {
				if (array[j] == array[i]) {
					i--;
					break;
				}
				j++;
			}
			i++;
		}
		return array;
	}

	private int[] BUTTONS = new int[] { R.id.city1, R.id.city3, R.id.city4,
			R.id.city5, R.id.city6, R.id.city7, R.id.city8, R.id.city9,
			R.id.city10, R.id.city11, R.id.city13, R.id.city14, R.id.city16,
			R.id.city17, R.id.city18, R.id.city19, R.id.city20, R.id.city21,
			R.id.city22, R.id.city23, R.id.city24, R.id.city25, R.id.city27,
			R.id.city28 };

	public void onClickHelp(View v) {
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_helper_cities);
		Log.d("LOGS", "select: " + ID_CITIES[NUMBER_QUESTION]);
		Button selectCity = ((Button) dialog
				.findViewById(BUTTONS[ID_CITIES[NUMBER_QUESTION]]));
		selectCity.setBackgroundResource(R.drawable.star_small);
		selectCity.startAnimation(animSelection);
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.bkg_map);
		dialog.show();
	}

	private class TouchListener implements OnTouchListener {

		@SuppressLint({ "NewApi" })
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			ClipData data = ClipData.newPlainText("", "");
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
			v.startDrag(data, shadowBuilder, v, 0);
			v.setAlpha(0.0f);
			return true;
		}

	}

	@SuppressLint("NewApi")
	private class DragListener implements OnDragListener {

		private Animation animChip = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.chip_oneshot);

		@Override
		public boolean onDrag(View container, DragEvent event) {

			// int tag = (Integer) ((View) event.getLocalState()).getTag();
			View view = (View) event.getLocalState();
			switch (event.getAction()) {
			case DragEvent.ACTION_DROP:

				String tag1 = view.getTag().toString();
				String tag2 = container.getTag().toString();
				Log.d("TAGGER", tag1 + " --- " + tag2);
				if (tag1.equals(tag2)) {
					view.setVisibility(View.INVISIBLE);
					container.setEnabled(false);
					((TextView) container).setText(view.getTag().toString());
					PROGRESS++;
					if (PROGRESS == LENGTH_WORD) {
						textScore.startAnimation(animChip);
						textScore.setText(getScore(NUMBER_QUESTION + 1));
						SoundManager.playGood(getApplicationContext());
						Animation anim = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.slide_to_left);
						anim.setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {

							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								restartQuest(++NUMBER_QUESTION);
								Animation animIn = AnimationUtils
										.loadAnimation(getApplicationContext(),
												R.anim.slide_from_right);
								containerCorrect.startAnimation(animIn);
								containerRandom.startAnimation(animIn);
							}
						});
						containerCorrect.startAnimation(anim);

					}
				} else {
					SoundManager.playError(getApplicationContext());
				}
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				view.setAlpha(1f);
				break;
			default:
				break;
			}
			return true;
		}
	}

	@Override
	public void onBackPressed() {
		double res = (double) NUMBER_QUESTION / COUNT * 100;
		dialogFinish(res);
	}

	private void dialogFinish(final double res) {
		final Dialog dialog = new Dialog(Activity3_2.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_finish);
		dialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				pauseTimer();
			}
		});
		dialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				startTimer();
			}
		});

		if (res == 100) {
			TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
			title.setTypeface(typeface);
			title.setText(Main.getTranslate(Activity3_2.this,
					R.string.mission_completed));
			title.append("\n" + timerValue.getText().toString());
		}
		Button close = (Button) dialog.findViewById(R.id.imageClose);
		close.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});

		ImageView buttonRestart = (ImageView) dialog
				.findViewById(R.id.buttonRestart);
		buttonRestart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity3_2.this, Main.class);
				intent.putExtra(Main.NEXT_INTENT, 7);
				intent.putExtra(Main.ID_INTENT, 7);
				intent.putExtra(Main.SCORE_INTENT, res);
				intent.putExtra(Main.TIME_INTENT, timeInMilliseconds / 1000);
				setResult(RESULT_OK, intent);
				if (dialog != null)
					dialog.dismiss();
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});

		ImageView buttonMainMenu = (ImageView) dialog
				.findViewById(R.id.buttonMainMenu);
		buttonMainMenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity3_2.this, Main.class);
				intent.putExtra(Main.ID_INTENT, 7);
				intent.putExtra(Main.SCORE_INTENT, res);
				intent.putExtra(Main.TIME_INTENT, timeInMilliseconds / 1000);
				setResult(RESULT_OK, intent);
				if (dialog != null)
					dialog.dismiss();
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});

		dialog.findViewById(R.id.buttonNext).setVisibility(View.INVISIBLE);
		dialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		dialog.show();
	}

	// TIMER
	private void startTimer() {
		startTime = SystemClock.uptimeMillis();
		customHandler.postDelayed(timer, 0);
	}

	private void pauseTimer() {
		timeSwapBuff += timeInMilliseconds;
		customHandler.removeCallbacks(timer);
	}

	private Runnable timer = new Runnable() {

		public void run() {

			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;
			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			secs = secs % 60;
			timerValue.setText(String.format("%02d", mins) + ":"
					+ String.format("%02d", secs));
			customHandler.postDelayed(this, 0);
		}

	};
}

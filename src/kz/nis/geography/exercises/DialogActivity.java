package kz.nis.geography.exercises;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class DialogActivity extends ListActivity {

	// TIMER
	private TextView timerValue;
	private int resLayout;
	private long startTime = 0L;
	private Handler customHandler = null;
	private long timeInMilliseconds = 0L;
	private long timeSwapBuff = 0L;
	private long updatedTime = 0L;
	private int[] molodec = new int[] { 0, R.raw.ru_molodec, 0 };

	public DialogActivity(int resLayout) {
		this.resLayout = resLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(resLayout);
		timerValue = (TextView) findViewById(R.id.textViewTime);
		timerValue.setTypeface(FontFactory
				.getFontDigit(getApplicationContext()));
		customHandler = new Handler();
		startTimer();
		findViewById(R.id.imageViewInfo).startAnimation(
				AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.anim_one_place_scale));

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

	public void dialogFinish(Context _context, int _scoreId, double _res) {
		final Context context = _context;
		final int scoreId = _scoreId;
		final double res = _res;
		final Dialog dialog = new Dialog(context);
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

		ImageView buttonNext = (ImageView) dialog.findViewById(R.id.buttonNext);

		if (res == 100) {
			TextView title = (TextView) dialog.findViewById(R.id.dialogTitle);
			title.setTypeface(FontFactory.getFont1(context));
			title.setText(Main
					.getTranslate(context, R.string.mission_completed));
			if (molodec[Main.LANG] != 0) {
				MediaPlayer media = MediaPlayer.create(getApplicationContext(),
						molodec[Main.LANG]);
				media.start();
			}
			

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
				Intent intent = new Intent();
				intent.putExtra(Main.NEXT_INTENT, scoreId);
				intent.putExtra(Main.ID_INTENT, scoreId);
				if (res == 100)
					intent.putExtra(Main.TIME_INTENT, timeInMilliseconds / 1000);
				intent.putExtra(Main.SCORE_INTENT, res);
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
				Intent intent = new Intent();
				intent.putExtra(Main.ID_INTENT, scoreId);
				intent.putExtra(Main.SCORE_INTENT, res);
				intent.putExtra(Main.TIME_INTENT, timeInMilliseconds / 1000);
				if (dialog != null)
					dialog.dismiss();
				setResult(RESULT_OK, intent);
				finish();
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});

		if (scoreId == 2 || scoreId == 5) {
			buttonNext.setVisibility(View.INVISIBLE);
		} else {
			buttonNext.startAnimation(AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.anim_one_place_scale));
			buttonNext.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra(Main.NEXT_INTENT, scoreId + 1);
					intent.putExtra(Main.ID_INTENT, scoreId);
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
		}
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

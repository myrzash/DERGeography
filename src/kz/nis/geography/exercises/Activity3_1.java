package kz.nis.geography.exercises;

import kz.nis.geography.ActivityInfo3;
import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.adapter.MapItem;
import kz.nis.geography.adapter.MapTextAdapter;
import kz.nis.geography.extra.FontFactory;
import kz.nis.geography.extra.SoundManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Activity3_1 extends DialogActivity implements OnClickListener {
	public Activity3_1() {
		super(R.layout.exercise_3_1);
	}

	private static final int[] buttonsID = new int[] { R.id.city1, R.id.city3,
			R.id.city4, R.id.city5, R.id.city6, R.id.city7, R.id.city8,
			R.id.city9, R.id.city10, R.id.city11, R.id.city13, R.id.city14,
			R.id.city16, R.id.city17, R.id.city18, R.id.city19, R.id.city20,
			R.id.city21, R.id.city22, R.id.city23, R.id.city24, R.id.city25,
			R.id.city27, R.id.city28 };
	private static short MAX_PROGRESS;
	private MapTextAdapter adapter;
	private TextView textTask;
	private int PROGRESS = 0;
	private TextView textScore;
	private int[] mp3 = new int[]{ 0, R.raw.ru_arrange_name_cities, 0};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		if(mp3[Main.LANG] != 0){
			MediaPlayer media = MediaPlayer.create(getApplicationContext(),mp3[Main.LANG]);
			media.start();
		}
	}

	// private void load() {
	// final ProgressDialog loadingdialog = ProgressDialog.show(
	// Activity3_1.this, null,
	// Main.getTranslate(Activity3_1.this, R.string.wait), true);
	// loadingdialog.setCancelable(false);
	// new Thread() {
	// public void run() {
	// try {
	// } catch (Exception e) {
	// Log.e("threadmessage", e.getMessage());
	// }
	// loadingdialog.dismiss();
	// // findViewById(R.id.buttonInfo).startAnimation(
	// // AnimationUtils.loadAnimation(getApplicationContext(),
	// // R.anim.anim_one_place_scale));
	// }
	// }.start();
	// }

	private void init() {
		textTask = (TextView) findViewById(R.id.textViewTask);
		textTask.setTypeface(FontFactory.getFont1(getApplicationContext()));
		textTask.setText(Main.getTranslate(getApplicationContext(),
				R.string.task_3_1));

		textScore = (TextView) findViewById(R.id.textScore);
		textScore
				.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		fillList(getApplicationContext(), R.array.Cities_name);
		for (int i = 0; i < buttonsID.length; i++) {
			Button button = (Button) findViewById(buttonsID[i]);
			button.setTypeface(FontFactory.getFont1(getApplicationContext()));
			button.setTag(i);
			button.setOnDragListener(new DragImageListener());
		}

		findViewById(R.id.buttonUp).setOnClickListener(this);
		findViewById(R.id.buttonDown).setOnClickListener(this);
		findViewById(R.id.imageViewInfo).setOnClickListener(this);
		findViewById(R.id.imageHome).setOnClickListener(this);
		Log.d("LOGS", "---> init end: ");
	}

	@Override
	public void onClick(View v) {
		int index = 0;
		switch (v.getId()) {
		case R.id.buttonDown:
			index = getListView().getFirstVisiblePosition();
			getListView().setSelection(index + 1);
			break;
		case R.id.buttonUp:
			index = getListView().getFirstVisiblePosition();
			getListView().setSelection(index - 1);
			break;
		case R.id.imageViewInfo:
			startActivity(new Intent(Activity3_1.this, ActivityInfo3.class));
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.imageHome:
			onBackPressed();
			break;
		}
	}

	private void fillList(Context context, int arrayResId) {
		CharSequence[] menus = getResources().getStringArray(arrayResId);
		MAX_PROGRESS = (short) menus.length;
		adapter = new MapTextAdapter(context);
		for (int i = 0; i < menus.length; i++) {
			adapter.add(new MapItem(i, menus[i]));
		}
		setListAdapter(adapter);
	}

	private class DragImageListener implements OnDragListener {
		boolean dropped = false;
		private Animation animChip = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.chip_oneshot);

		@Override
		public boolean onDrag(View container, DragEvent event) {
			final View textView = (View) event.getLocalState();
			int tag = (Integer) textView.getTag();

			switch (event.getAction()) {

			case DragEvent.ACTION_DRAG_STARTED:
				adapter.changeVisible(tag, View.INVISIBLE);
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				// container.setSelected(true);
				break;
			case DragEvent.ACTION_DROP:
				for (int i = 0; i < buttonsID.length; i++)
					if (tag == i && (Integer) container.getTag() == i) {
						String txt = ((TextView) textView).getText().toString();
						((Button) container).setText(txt);
						// container.setPadding(10, 0, 10, 5);
						container.setEnabled(false);
						container.setBackgroundColor(getResources().getColor(
								R.color.translucent_light));
						adapter.removeAndChange(tag);
						PROGRESS++;
						// if (Main.getResult() < 66)
						// Main.incrementRes();

						textScore.setText(getScore(PROGRESS));
						textScore.startAnimation(animChip);
						if (PROGRESS == MAX_PROGRESS) {
							// textTask.setText(Main.getTranslate(
							// getApplicationContext(),
							// R.string.mission_completed));
							onBackPressed();
							SoundManager.playGood(getApplicationContext());
						}
						dropped = true;
						break;
					}
				if (!dropped)
					SoundManager.playError(getApplicationContext());

				break;
			case DragEvent.ACTION_DRAG_EXITED:
				// container.setSelected(false);
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				if (!dropped)
					adapter.changeVisible(tag, View.VISIBLE);
				// container.setSelected(false);
				break;
			default:
				break;
			}
			return true;
		}

		private CharSequence getScore(int progress) {
			double score = (double) progress / MAX_PROGRESS * 100;
			return "" + (int) score;
		}
	}

	// private class Task extends AsyncTask<Void, Integer, Void> {
	// private ProgressDialog progressDialog;
	//
	// @Override
	// protected void onPreExecute() {
	// progressDialog = new ProgressDialog(Activity3_1.this);
	// progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	// progressDialog.setMessage(Main.getTranslate(
	// getApplicationContext(), R.string.wait));
	// progressDialog.setIndeterminate(true);
	// progressDialog.setCancelable(false);
	// progressDialog.show();
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	// if (progressDialog.isShowing())
	// progressDialog.dismiss();
	//
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	//
	// return null;
	// }
	// }

	@Override
	public void onBackPressed() {
		double res = (double) PROGRESS / MAX_PROGRESS * 100;
		dialogFinish(Activity3_1.this, 6, res);
	}
}

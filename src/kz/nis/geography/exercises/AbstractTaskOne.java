package kz.nis.geography.exercises;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.adapter.MapImageAdapter;
import kz.nis.geography.adapter.MapItem;
import kz.nis.geography.extra.FontFactory;
import kz.nis.geography.extra.SoundManager;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public abstract class AbstractTaskOne extends DialogActivity {

	private int resLayout;
	private int[] buttonsID;
	private int[] bkgsDropped;
	private int[] bkgsEntered;

	protected short MAX_PROGRESS;

	protected short PROGRESS;
	private AbsoluteLayout bkgMap;
	private ImageView imageBkg;
	private TextView titleTask;
	private MapImageAdapter adapter;
	private int[] listDrawables;
	private TextView textScore;
	private static final AbsoluteLayout.LayoutParams lParams = new AbsoluteLayout.LayoutParams(
			AbsoluteLayout.LayoutParams.MATCH_PARENT,
			AbsoluteLayout.LayoutParams.MATCH_PARENT, 0, 0);
	private int[] mp3 = new int[]{ 0, R.raw.ru_soberite_kartu, 0};

	public AbstractTaskOne(int resLayout, int[] buttonsID, int[] bkgsDropped,
			int[] bkgsEntered, int[] listDrawables) {
		super(resLayout);
		this.buttonsID = buttonsID;
		this.bkgsEntered = bkgsEntered;
		this.bkgsDropped = bkgsDropped;
		this.listDrawables = listDrawables;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PROGRESS = 0;
		init();
		if(mp3[Main.LANG] != 0){
			MediaPlayer media = MediaPlayer.create(getApplicationContext(),mp3[Main.LANG]);
			media.start();
		}
		
	}

	// private void load() {
	// final ProgressDialog loadingdialog = ProgressDialog.show(
	// AbstractTaskOne.this, null,
	// Main.getTranslate(AbstractTaskOne.this, R.string.wait), true);
	// loadingdialog.setCancelable(false);
	// new Thread() {
	// public void run() {
	// try {
	//
	// } catch (Exception e) {
	// Log.e("threadmessage", e.getMessage());
	// }
	// loadingdialog.dismiss();
	// // findViewById(R.id.buttonInfo).startAnimation(
	// // AnimationUtils.loadAnimation(getApplicationContext(),
	// // R.anim.anim_one_place_scale));
	//
	// }
	// }.start();
	// }

	private void init() {
		bkgMap = (AbsoluteLayout) findViewById(R.id.map_kz);
		titleTask = (TextView) findViewById(R.id.textViewTask);
		titleTask.setText(Main.getTranslate(getApplicationContext(),
				R.string.task_1_1));
		titleTask.setTypeface(FontFactory.getFont1(getApplicationContext()));

		textScore = (TextView) findViewById(R.id.textScore);
		textScore
				.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		imageBkg = (ImageView) findViewById(R.id.imageRootMap);
		// if (Main.getResult() <= 33) {
		// buttonNext.setVisibility(Button.INVISIBLE);
		// }

		for (int i = 0; i < buttonsID.length; i++) {
			findViewById(buttonsID[i]).setTag(i);
			findViewById(buttonsID[i]).setOnDragListener(
					new DragImageListener());
		}
		fillList(listDrawables);
		findViewById(R.id.buttonUp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int index = getListView().getFirstVisiblePosition();
				getListView().setSelection(index - 1);
			}
		});
		findViewById(R.id.buttonDown).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int index = getListView().getFirstVisiblePosition();
				getListView().setSelection(index + 1);
			}
		});
	}

	private void fillList(int[] imgs) {
		MAX_PROGRESS = (short) imgs.length;
		adapter = new MapImageAdapter(getApplicationContext());
		for (int i = 0; i < imgs.length; i++) {
			adapter.add(new MapItem(i, imgs[i]));
		}
		setListAdapter(adapter);
	}

	private class DragImageListener implements OnDragListener {
		boolean dropped = false;
		private Animation animChip = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.chip_oneshot);

		@Override
		public boolean onDrag(View container, DragEvent event) {

			int tag = (Integer) ((View) event.getLocalState()).getTag();	
			switch (event.getAction()) {

			case DragEvent.ACTION_DRAG_STARTED:
				adapter.changeVisible(tag, View.INVISIBLE);
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				imageBkg.setBackground(getResources().getDrawable(
						bkgsEntered[(Integer) container.getTag()]));
				break;
			case DragEvent.ACTION_DROP:

				for (int i = 0; i < buttonsID.length; i++)
					if (tag == i && (Integer) container.getTag() == i) {

						ImageView imageContainer = new ImageView(
								getApplicationContext());
						imageContainer.setBackground(getResources()
								.getDrawable(bkgsDropped[i]));
						bkgMap.addView(imageContainer, lParams);
						adapter.removeAndChange(tag);
						PROGRESS++;
						textScore.setText(getScore(PROGRESS));
						textScore.startAnimation(animChip);
						if (PROGRESS == MAX_PROGRESS) {
							onBackPressed();
							SoundManager.playGood(getApplicationContext());
						}
						dropped = true;
						break;
					}
				if (!dropped) {
					SoundManager.playError(getApplicationContext());
				}
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				imageBkg.setBackground(getResources().getDrawable(
						android.R.color.transparent));
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				if (!dropped)
					adapter.changeVisible(tag, View.VISIBLE);
				imageBkg.setBackground(getResources().getDrawable(
						android.R.color.transparent));
				break;
			default:
				break;
			}
			return true;
		}

		private CharSequence getScore(short progress) {
			double score = (double) progress / MAX_PROGRESS * 100;
			return "" + (int) score;
		}

	}

}

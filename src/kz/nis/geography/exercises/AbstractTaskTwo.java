package kz.nis.geography.exercises;

import kz.nis.geography.R;
import kz.nis.geography.adapter.MapItem;
import kz.nis.geography.adapter.MapTextAdapter;
import kz.nis.geography.extra.FontFactory;
import kz.nis.geography.extra.SoundManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint({ "NewApi" })
public abstract class AbstractTaskTwo extends DialogActivity {

	private int resLayout;
	private int[] buttonsID;
	private int[] bkgsEntered;
	private ImageView imageBkg;
	private MapTextAdapter adapter;
	private TextView titleTask;
	protected short MAX_PROGRESS;
	protected short PROGRESS;
	private int arrayRes;
	private TextView textScore;

	public AbstractTaskTwo(int resLayout, int[] buttonsID, int[] bkgsEntered,
			int arrayRes) {
		super(resLayout);
		this.buttonsID = buttonsID;
		this.bkgsEntered = bkgsEntered;
		this.arrayRes = arrayRes;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PROGRESS = 0;	
		init();
	}

//	private void load() {
//		final ProgressDialog loadingdialog = ProgressDialog.show(
//				AbstractTaskTwo.this, null,
//				Main.getTranslate(AbstractTaskTwo.this, R.string.wait), true);
//		loadingdialog.setCancelable(false);
//		new Thread() {
//			public void run() {
//				try {
//					init();
//				} catch (Exception e) {
//					Log.e("threadmessage", e.getMessage());
//				}
//				loadingdialog.dismiss();
////				titleTask.startAnimation(
////						AnimationUtils.loadAnimation(getApplicationContext(),
////								R.anim.from_top));
////				findViewById(R.id.buttonInfo).startAnimation(
////						AnimationUtils.loadAnimation(getApplicationContext(),
////								R.anim.anim_one_place_scale));
//			}
//		}.start();
//	}

	private void init() {
		titleTask = (TextView) findViewById(R.id.textViewTask);
		titleTask.setTypeface(FontFactory.getFont1(getApplicationContext()));
		imageBkg = (ImageView) findViewById(R.id.imageRootMap);
		textScore = (TextView) findViewById(R.id.textScore);
		textScore.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		for (int i = 0; i < buttonsID.length; i++) {
			Button button = (Button) findViewById(buttonsID[i]);
			button.setTypeface(FontFactory.getFont1(getApplicationContext()));
			button.setTag(i);
			button.setOnDragListener(new DragImageListener());
		}
		fillList();

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

	private void fillList() {
		CharSequence[] menus = getResources().getStringArray(arrayRes);
		this.MAX_PROGRESS = (short) menus.length;
		adapter = new MapTextAdapter(getApplicationContext());
		for (int i = 0; i < menus.length; i++) {
			adapter.add(new MapItem(i, menus[i]));
		}
		setListAdapter(adapter);
	}

	private class DragImageListener implements OnDragListener {
		boolean dropped = false;
		private Animation animChip = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.chip_oneshot);

		@Override
		public boolean onDrag(View container, DragEvent event) {
			final View textView = (View) event.getLocalState();
			int tag = (Integer) textView.getTag();

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
						String txt = ((TextView) textView).getText().toString();
						((Button) container).setText(txt);
						adapter.removeAndChange(tag);
						PROGRESS++;
//						if (33.33 < Main.getResult()
//								&& Main.getResult() <= 66.67)
//							Main.incrementRes();
						textScore.setText(getScore(PROGRESS));
						textScore.startAnimation(animChip );
						if (PROGRESS == MAX_PROGRESS) {
//							titleTask.setText(MessageFormat.format(
//									getString(R.string.mission_completed),
//									Main.LANG));
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
//				 dropped=false;
				break;
			default:
				break;
			}
			return true;
		}
		
		private CharSequence getScore(short progress) {
			double score = (double)progress/MAX_PROGRESS*100;
			return ""+(int)score;
		}
	}

//	@Override
//	public void onBackPressed() {
//		startActivity(new Intent(getApplicationContext(), Main.class));
//		overridePendingTransition(android.R.anim.fade_in,
//				android.R.anim.fade_out);
//	}
}

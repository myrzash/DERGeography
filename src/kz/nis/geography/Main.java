package kz.nis.geography;

import java.text.MessageFormat;
import java.util.ArrayList;

import kz.nis.geography.adapter.DBAdapter;
import kz.nis.geography.exercises.Activity1_1;
import kz.nis.geography.exercises.Activity1_2;
import kz.nis.geography.exercises.Activity1_3;
import kz.nis.geography.exercises.Activity2_1;
import kz.nis.geography.exercises.Activity2_2;
import kz.nis.geography.exercises.Activity2_3;
import kz.nis.geography.exercises.Activity3_1;
import kz.nis.geography.exercises.Activity3_2;
import kz.nis.geography.extra.FontFactory;
import kz.nis.geography.menu.NavDrawerItem;
import kz.nis.geography.menu.NavDrawerListAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends FragmentActivity implements OnClickListener {

	public static int LANG = 1;
	private DBAdapter dbAdapter;
	public static String USER_NAME;
	public static final String ID_INTENT = "scoreid";
	public static final String SCORE_INTENT = "score";
	public static final String TIME_INTENT = "time";
	public static final String NEXT_INTENT = "next";
	private static final long durationTranslate = 2000;
	public static String LOG = "RESULT";
	// slide menu
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private AnimationDrawable boyHideAnim = null;
	private AnimationDrawable dogHideAnim = null;
	private CheckBox soundView = null;
	private Animation animScale;
	private NavDrawerListAdapter menuAdapter;
	private static MediaPlayer mediaPlayer = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbAdapter = new DBAdapter(Main.this);
		
		USER_NAME = dbAdapter.getLastUserName();		
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.akku);
			mediaPlayer.setVolume(0.5f, 0.5f);
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
			findViewById(R.id.tableRLayoutExercises).startAnimation(
					AnimationUtils.loadAnimation(getApplicationContext(),
							R.anim.from_bottom));
		}
		registerSlidingMenu();
		registerExercises();
		soundView = (CheckBox) findViewById(R.id.checkBoxSound);
		soundView.setChecked(mediaPlayer.isPlaying());
		soundView.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (mediaPlayer == null)
					return;
				if (isChecked) {
					if (!mediaPlayer.isPlaying()) {
						mediaPlayer.start();
					}
				} else {
					if (mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
					}
				}
			}
		});
		animations();
	}
	
	@Override
	protected void onPause() {
		if(mediaPlayer!=null)
		mediaPlayer.setVolume(0.1f, 0.1f);
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		if(mediaPlayer!=null)
		mediaPlayer.setVolume(0.5f, 0.5f);
		super.onResume();
	}


	private void animations() {
		ImageView imageBoyHide = (ImageView) findViewById(R.id.imageBoyHide);
		boyHideAnim = (AnimationDrawable) imageBoyHide.getBackground();
		boyHideAnim.start();

		ImageView imageDogHide = (ImageView) findViewById(R.id.imageDogHide);
		dogHideAnim = (AnimationDrawable) imageDogHide.getBackground();
		dogHideAnim.start();
	}

	public void onClickMenu(View v) {
		mDrawerLayout.openDrawer(mDrawerList);
	}

	private void registerExercises() {
		String[] titles = getResources().getStringArray(
				R.array.TITLES_COVERPAGE);
		int[] idParts = new int[] { R.id.buttonPart1, R.id.buttonPart2,
				R.id.buttonPart3 };
		for (int i = 0; i < idParts.length; i++) {
			TextView part = (TextView) findViewById(idParts[i]);
			part.setTypeface(FontFactory.getFont1(getApplicationContext()));
			part.setText(MessageFormat.format(titles[i], LANG));
		}

		animScale = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.repeat_scale_rotate);

		final int[] idsExercises = new int[] { R.id.buttonExercise1,
				R.id.buttonExercise2, R.id.buttonExercise3,
				R.id.buttonExercise4, R.id.buttonExercise5,
				R.id.buttonExercise6, R.id.buttonExercise7,
				R.id.buttonExercise8 };
		for (int i = 0; i < idsExercises.length; i++) {
			findViewById(idsExercises[i]).setOnClickListener(this);
			findViewById(idsExercises[i]).startAnimation(animScale);
		}
	}

	private void registerSlidingMenu() {
		Log.d("LOGS", "registerSlidingMenu");
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		String[] navMenuTitles = getResources().getStringArray(
				R.array.nav_drawer_items);
		TypedArray navMenuIcons = getResources().obtainTypedArray(
				R.array.nav_drawer_icons);
		ArrayList<NavDrawerItem> menuItems = new ArrayList<NavDrawerItem>();
		menuItems.add(new NavDrawerItem(MessageFormat.format(navMenuTitles[0],
				LANG), navMenuIcons.getResourceId(0, -1)));
		menuItems.add(new NavDrawerItem(MessageFormat.format(navMenuTitles[1],
				LANG), navMenuIcons.getResourceId(1, -1)));
		menuItems.add(new NavDrawerItem(MessageFormat.format(navMenuTitles[2],
				LANG), navMenuIcons.getResourceId(2, -1)));
		menuItems.add(new NavDrawerItem(MessageFormat.format(navMenuTitles[3],
				LANG), navMenuIcons.getResourceId(3, -1), true, dbAdapter
				.getArithmeticScore(USER_NAME)));
		menuItems.add(new NavDrawerItem(MessageFormat.format(navMenuTitles[4],
				LANG), navMenuIcons.getResourceId(4, -1)));
		navMenuIcons.recycle();
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		menuAdapter = new NavDrawerListAdapter(getApplicationContext(),
				menuItems);
		mDrawerList.addHeaderView(getHeaderMenu());
		mDrawerList.setAdapter(menuAdapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_launcher, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			@SuppressLint("NewApi")
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}

			@SuppressLint("NewApi")
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (getIntent().getAction() == "openmenu")
			onClickMenu(null);
	}

	private View getHeaderMenu() {
		View v = getLayoutInflater().inflate(R.layout.header_menu, null);
		TextView textUserName = (TextView) v
				.findViewById(R.id.textViewUserName);
		textUserName.setText(String.format("%S", USER_NAME));
		textUserName.setTypeface(FontFactory.getFont1(getApplicationContext()));
			int[] idsMenuScores = new int[] { R.id.textViewScore1, R.id.textViewScore2,
					R.id.textViewScore3, R.id.textViewScore4 };
			String[] attempts = dbAdapter.getAttempts(USER_NAME);
			for (int i = 0; i < attempts.length; i++) {
				if (attempts[i] == null) 
					break;
				TextView text = (TextView) v.findViewById(idsMenuScores[i]);
				text.setVisibility(View.VISIBLE);
				text.setTypeface(FontFactory.getFontDigit(Main.this));
				text.setText(attempts[i]);
		}

		return v;
	}

	private void animateTranslate() {
		ImageView carte = (ImageView) findViewById(R.id.imageViewStarMain);

		Display display = getWindowManager().getDefaultDisplay();
		int finalX = -display.getWidth() / 2;
		int finalY = -display.getHeight() / 2;

		AnimationSet animSet = new AnimationSet(true);
		animSet.setFillAfter(true);
		animSet.setDuration(durationTranslate);
		animSet.setInterpolator(new AccelerateDecelerateInterpolator());
		TranslateAnimation translate = new TranslateAnimation(0, finalX, 0,
				finalY);
		translate.setDuration(durationTranslate);
		animSet.addAnimation(translate);
		ScaleAnimation scale = new ScaleAnimation(1f, 0.3f, 1f, 0.3f,
				ScaleAnimation.ZORDER_NORMAL, finalX,
				ScaleAnimation.ZORDER_NORMAL, finalY);
		scale.setDuration(durationTranslate);
		animSet.addAnimation(scale);
		animSet.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(getIntent().setAction("openmenu"));
				overridePendingTransition(0, 0);
			}
		});
		carte.startAnimation(animSet);
	}

	private static long back_pressed;

	@Override
	public void onBackPressed() {
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			finish();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} else
			showToast(R.string.toast_exit);
		back_pressed = System.currentTimeMillis();
	}

	@Override
	protected void onDestroy() {

		if (dbAdapter != null)
			dbAdapter.close();
		if (boyHideAnim != null)
			boyHideAnim.stop();
		boyHideAnim = null;
		if (dogHideAnim != null)
			dogHideAnim.stop();
		dogHideAnim = null;
		if (animScale != null) {
			animScale.cancel();
			animScale = null;
		}

		if (mediaPlayer.isPlaying())
			mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;
		super.onDestroy();
	}

	public void onClickLogOut() {
		dbAdapter.updateLogin(USER_NAME, false);
		USER_NAME = null;
		finish();
		startActivity(new Intent(Main.this, ActivityInput.class));
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.buttonExercise1:
			intent = new Intent(getApplicationContext(), Activity1_1.class);
			break;
		case R.id.buttonExercise2:
			intent = new Intent(getApplicationContext(), Activity1_2.class);
			break;
		case R.id.buttonExercise3:
			intent = new Intent(getApplicationContext(), Activity1_3.class);
			break;
		case R.id.buttonExercise4:
			intent = new Intent(getApplicationContext(), Activity2_1.class);
			break;
		case R.id.buttonExercise5:
			intent = new Intent(getApplicationContext(), Activity2_2.class);
			break;
		case R.id.buttonExercise6:
			intent = new Intent(getApplicationContext(), Activity2_3.class);
			break;
		case R.id.buttonExercise7:
			intent = new Intent(getApplicationContext(), Activity3_1.class);
			break;
		case R.id.buttonExercise8:
			intent = new Intent(getApplicationContext(), Activity3_2.class);
			break;
		}
		if (intent != null) {
			startActivityForResult(intent, 1);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (intent == null)
			return;

		int id = intent.getIntExtra(ID_INTENT, -1);

		if (id != -1) {
			double score = intent.getDoubleExtra(SCORE_INTENT, 0);
			long time = intent.getLongExtra(TIME_INTENT, 0);
			dbAdapter.updateScore(score, id, USER_NAME);
			if(score==100){
				 dbAdapter.updateTime(time, id, USER_NAME);
			}			
			menuAdapter.changeResult(dbAdapter.getArithmeticScore(USER_NAME));
			Log.d("RESULT", "id=" + id + ", score=" + score + ", time=" + time);
		}

		int next = intent.getIntExtra(NEXT_INTENT, -1);
		Log.d("RESULT", "next=" + next);
		Intent nextIntent = null;
		switch (next) {
		case 0:
			nextIntent = new Intent(Main.this, Activity1_1.class);
			break;
		case 1:
			nextIntent = new Intent(Main.this, Activity1_2.class);
			break;
		case 2:
			nextIntent = new Intent(Main.this, Activity1_3.class);
			break;
		case 3:
			nextIntent = new Intent(Main.this, Activity2_1.class);
			break;
		case 4:
			nextIntent = new Intent(Main.this, Activity2_2.class);
			break;
		case 5:
			nextIntent = new Intent(Main.this, Activity2_3.class);
			break;
		case 6:
			nextIntent = new Intent(Main.this, Activity3_1.class);
			break;
		case 7:
			nextIntent = new Intent(Main.this, Activity3_2.class);
			break;
		default:
			break;
		}
		if (nextIntent != null) {
			startActivityForResult(nextIntent, 1);
			overridePendingTransition(0, 0);
		} else if (dbAdapter.getArithmeticScore(USER_NAME) == 100) {
			 dbAdapter.addAttempt(USER_NAME);
			 animateTranslate();
		}
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = null;
			switch (position) {
			case 1:
				intent = new Intent(Main.this, ActivityInfo1.class);
				break;
			case 2:
				intent = new Intent(Main.this, ActivityInfo2.class);
				break;
			case 3:
				intent = new Intent(Main.this, ActivityInfo3.class);
				break;
			case 4:
				intent = new Intent(Main.this, ActivityResults.class);
				break;
			case 5:
				onClickLogOut();
				break;
			default:
				break;
			}
			mDrawerLayout.closeDrawer(mDrawerList);
			if (intent != null) {
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void showToast(int messageId) {
		Toast.makeText(getApplicationContext(),
				getTranslate(Main.this, messageId), Toast.LENGTH_SHORT).show();
	}

	public static String getTranslate(Context context, int resId) {
		return MessageFormat.format(context.getString(resId), LANG);
	}

}

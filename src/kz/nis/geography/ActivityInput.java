package kz.nis.geography;

import kz.nis.geography.adapter.DBAdapter;
import kz.nis.geography.cover.CirclePageIndicator;
import kz.nis.geography.cover.CoverPageAdapter;
import kz.nis.geography.cover.CoverPageTransformer;
import kz.nis.geography.extra.FontFactory;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityInput extends FragmentActivity {

	// private static final int REQUEST_CODE_LANG = 1;
	private ViewPager pager;
	private SharedPreferences sharedPreferences;
	public static Button buttonBegin = null;
	public static ImageView imageCharacters = null;
	private static long back_pressed;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input);

		if (getIntent().getAction() == "changelang") {
			Main.LANG = getIntent().getIntExtra("lang", 0);
			savePreferences(Main.LANG);
		} else
			Main.LANG = getLang();

		if (isLastUser()) {
			finish();
			Intent i = new Intent(ActivityInput.this, Main.class);
			startActivity(i);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		} else {
			imageCharacters = (ImageView) findViewById(R.id.imageCharacters);
			buttonBegin = (Button) findViewById(R.id.buttonBegin);
			buttonBegin.setVisibility(View.INVISIBLE);
			buttonBegin.setText(Main.getTranslate(this, R.string.start));
			buttonBegin.setTypeface(FontFactory
					.getFont1(getApplicationContext()));
			buttonBegin.setOnTouchListener(new OnTouchListener() {

				private boolean click = false;

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						v.setScaleX(0.8f);
						v.setScaleY(0.8f);
						click = true;
						break;
					case MotionEvent.ACTION_MOVE:
						click = false;
						break;
					case MotionEvent.ACTION_UP:
						v.setScaleX(1f);
						v.setScaleY(1f);
						if (click) {
							pager.setCurrentItem(4, true);
						}
						break;
					}
					return true;
				}
			});

			registerPager();
			if (getIntent().getAction() == "changelang") {
				pager.setCurrentItem(1, true);
			}
		}
	}

	private boolean isLastUser() {
		DBAdapter dbAdapter = new DBAdapter(ActivityInput.this);
		String user = dbAdapter.getLastUserName();
		dbAdapter.close();
		if (user != null)
			return true;
		else
			return false;
	}

	private void registerPager() {
		Log.d("LOGS", "registerPager");

		pager = (ViewPager) findViewById(R.id.coverViewPager);
		CoverPageAdapter coverPageAdapter = new CoverPageAdapter(this,
				this.getSupportFragmentManager());
		pager.setPageTransformer(false, new CoverPageTransformer());
		pager.setAdapter(coverPageAdapter);
		CirclePageIndicator mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(pager);

	}

	private int getLang() {
		sharedPreferences = getPreferences(MODE_PRIVATE);
		return sharedPreferences.getInt("lang", 0);
	}

	private void savePreferences(int lang) {
		sharedPreferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("lang", lang);
		editor.commit();
	}

	@Override
	public void onBackPressed() {
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		} else
			Toast.makeText(getApplicationContext(),
					Main.getTranslate(ActivityInput.this, R.string.toast_exit),
					Toast.LENGTH_SHORT).show();
		back_pressed = System.currentTimeMillis();
	}
}

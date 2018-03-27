package kz.nis.geography;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.VideoView;

public class SplashActivity extends Activity {
	private static final int SPLASH_TIME_OUT = 7000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		 VideoView mVideoView = (VideoView) findViewById(R.id.videoView1);
		 String uriPath = "android.resource://" + getPackageName() + "/"
		 + R.raw.screen1;
		 Log.d("VIDEO", "riPath:   " + uriPath);
		 Uri uri = Uri.parse(uriPath);
		 mVideoView.setVideoURI(uri);
		 mVideoView.requestFocus();
		 mVideoView.start();
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				finish();
				Intent i =new Intent(SplashActivity.this, ActivityInput.class);
				startActivity( i);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);				
			}
		},SPLASH_TIME_OUT);
	}
}
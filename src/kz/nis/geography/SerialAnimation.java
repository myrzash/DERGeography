//package kz.nis.geography;
//
//import android.os.CountDownTimer;
//import android.view.View;
//import android.view.animation.Animation;
//
//public class SerialAnimation extends CountDownTimer {
//
//	private static final long millisInFuture = 16000;
//	private static final long countDownInterval = 2000;
//	private int pos = 0;
//	private Animation animation;
//	private View[] views;
//
//	public SerialAnimation(long millisInFuture, long countDownInterval) {
//		super(millisInFuture, countDownInterval);
//
//	}
//
//	public SerialAnimation(Animation animation, View[] views) {
//		super(millisInFuture, countDownInterval);
//		this.animation = animation;
//		this.views = views;
//	}
//
//	@Override
//	public void onFinish() {
//		views[pos].startAnimation(animation);
//	}
//
//	@Override
//	public void onTick(long millisUntilFinished) {
//		views[pos++].startAnimation(animation);
//	}
//
//}

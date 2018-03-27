package kz.nis.geography;

import kz.nis.geography.extra.FontFactory;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class ListMapActivity extends Activity {

	private int layoutRes;
	private int[] BUTTONS = null;

	public ListMapActivity(int layoutRes, int[] btns) {
		this.layoutRes = layoutRes;
		this.BUTTONS = btns;
	}

	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	private Button btnBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layoutRes);
		btnBack = (Button) findViewById(R.id.buttonBackGridView);
		mShortAnimationDuration = getResources().getInteger(
				android.R.integer.config_longAnimTime);
		Button btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setTypeface(FontFactory.getFontDigit(getApplicationContext()));
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	@SuppressLint("NewApi")
	public void zoomImageFromThumb(final View thumbView, String messageId) {
		if (mCurrentAnimator != null) {
			mCurrentAnimator.cancel();
		}
		final LinearLayout containerRow = (LinearLayout) findViewById(R.id.containerRow);
		final TextView expandedTextTitle = (TextView) findViewById(R.id.text_title);
		expandedTextTitle.setText(messageId);
		expandedTextTitle.setTypeface(FontFactory
				.getFont1(getApplicationContext()));
		final Rect startBounds = new Rect();
		final Rect finalBounds = new Rect();
		final Point globalOffset = new Point();

		thumbView.getGlobalVisibleRect(startBounds);
		findViewById(R.id.container).getGlobalVisibleRect(finalBounds,
				globalOffset);
		startBounds.offset(-globalOffset.x, -globalOffset.y);
		finalBounds.offset(-globalOffset.x, -globalOffset.y);

		float startScale;
		if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
				.width() / startBounds.height()) {
			// Extend start bounds horizontally
			startScale = (float) startBounds.height() / finalBounds.height();
			float startWidth = startScale * finalBounds.width();
			float deltaWidth = (startWidth - startBounds.width()) / 2;
			startBounds.left -= deltaWidth;
			startBounds.right += deltaWidth;
		} else {
			// Extend start bounds vertically
			startScale = (float) startBounds.width() / finalBounds.width();
			float startHeight = startScale * finalBounds.height();
			float deltaHeight = (startHeight - startBounds.height()) / 2;
			startBounds.top -= deltaHeight;
			startBounds.bottom += deltaHeight;
		}

		thumbView.setAlpha(0f);
		expandedTextTitle.setVisibility(View.VISIBLE);
		expandedTextTitle.setPivotX(0.0f);
		expandedTextTitle.setPivotY(0.0f);

		AnimatorSet set = new AnimatorSet();
		set.play(
				ObjectAnimator.ofFloat(expandedTextTitle, View.X,
						startBounds.left, finalBounds.left))
				.with(ObjectAnimator.ofFloat(expandedTextTitle, View.Y,
						startBounds.top, finalBounds.top))
				.with(ObjectAnimator.ofFloat(expandedTextTitle, View.SCALE_X,
						startScale, 1f))
				.with(ObjectAnimator.ofFloat(expandedTextTitle, View.SCALE_Y,
						startScale, 1f));
		set.setDuration(mShortAnimationDuration);
		set.setInterpolator(new DecelerateInterpolator());
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				mCurrentAnimator = null;
				containerRow.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				mCurrentAnimator = null;
			}
		});
		set.start();
		mCurrentAnimator = set;

		final float startScaleFinal = startScale;
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mCurrentAnimator != null) {
					mCurrentAnimator.cancel();
				}

				AnimatorSet set = new AnimatorSet();
				set.play(
						ObjectAnimator.ofFloat(expandedTextTitle, View.X,
								startBounds.left))
						.with(ObjectAnimator.ofFloat(expandedTextTitle, View.Y,
								startBounds.top))
						.with(ObjectAnimator.ofFloat(expandedTextTitle,
								View.SCALE_X, startScaleFinal))
						.with(ObjectAnimator.ofFloat(expandedTextTitle,
								View.SCALE_Y, startScaleFinal));
				set.setDuration(mShortAnimationDuration);
				set.setInterpolator(new DecelerateInterpolator());
				set.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationStart(Animator animation) {
						containerRow.setVisibility(View.INVISIBLE);
						super.onAnimationStart(animation);
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						for (int i = 0; i < BUTTONS.length; i++) {
							findViewById(BUTTONS[i]).setEnabled(true);
						}
						expandedTextTitle.setVisibility(View.INVISIBLE);
						thumbView.setAlpha(1f);
						mCurrentAnimator = null;
					}

					@Override
					public void onAnimationCancel(Animator animation) {
						thumbView.setAlpha(1f);
						mCurrentAnimator = null;
					}
				});
				set.start();
				mCurrentAnimator = set;
			}
		});
	}
}
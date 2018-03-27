package kz.nis.geography.cover;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.adapter.DBAdapter;
import kz.nis.geography.extra.FontFactory;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FragmentAccount extends Fragment {
	public static Fragment newInstance(Context context) {
		Bundle b = new Bundle();
		return Fragment
				.instantiate(context, FragmentAccount.class.getName(), b);
	}

	private DBAdapter dbAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		FrameLayout view = (FrameLayout) inflater.inflate(
				R.layout.fragment_account, container, false);

		dbAdapter = new DBAdapter(getActivity());
		ArrayList<String> users = dbAdapter.getUserNames();
		final AutoCompleteTextView autoTextView = ((AutoCompleteTextView) view
				.findViewById(R.id.autoCompleteTextViewUserName));
		autoTextView.setTypeface(FontFactory.getFont1(getActivity()));
		autoTextView.setHint(Main.getTranslate(getActivity(),
				R.string.user_name));

		if (users != null) {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_dropdown_item_1line,
					users);
			adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			autoTextView.setAdapter(adapter);
		}
		final EditText editPassword = (EditText) view
				.findViewById(R.id.editTextPassword);
		editPassword.setTypeface(FontFactory.getFont1(getActivity()));
		editPassword.setHint(Main
				.getTranslate(getActivity(), R.string.password));

		final EditText editConfirm = (EditText) view
				.findViewById(R.id.editTextConfirm);
		editConfirm.setTypeface(FontFactory.getFont1(getActivity()));
		editConfirm.setVisibility(View.INVISIBLE);
		editConfirm.setHint(Main.getTranslate(getActivity(),
				R.string.confirm_password));

		final TextView forgot = (TextView) view
				.findViewById(R.id.textViewForgotPassword);
		forgot.setTypeface(FontFactory.getFont1(getActivity()));
		forgot.setText(Main.getTranslate(getActivity(),
				R.string.forgot_password));
		forgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = autoTextView.getText().toString();
				if (dbAdapter.existUser(name)) {
					editPassword.setText(dbAdapter.getPassword(name));
				} else {
					showToast(R.string.user_not_found);
				}
			}
		});

		final ToggleButton btnSignUp = (ToggleButton) view
				.findViewById(R.id.toggleBtnRegister);
		btnSignUp.setTypeface(FontFactory.getFont1(getActivity()));
		btnSignUp.setText(Main.getTranslate(getActivity(),
				R.string.registration));
		btnSignUp.setTextOff(Main.getTranslate(getActivity(),
				R.string.registration));
		btnSignUp.setTextOn(Main.getTranslate(getActivity(), R.string.cancel));
		btnSignUp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					forgot.setVisibility(View.INVISIBLE);
					editConfirm.setVisibility(View.VISIBLE);
				} else {
					forgot.setVisibility(View.VISIBLE);
					editConfirm.setVisibility(View.INVISIBLE);
				}
			}
		});

		
		Button btnLogin = (Button) view.findViewById(R.id.buttonSubmit);
		btnLogin.setTypeface(FontFactory.getFont1(getActivity()));
		btnLogin.setText(Main.getTranslate(getActivity(), R.string.input));
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnSignUp.isChecked()) {
					loginWithRegister();
				} else {
					loginWithoutRegister();
				}
			}

			private void loginWithoutRegister() {
				String name = autoTextView.getText().toString();
				String pswd = editPassword.getText().toString();

				if (dbAdapter.existUser(name)) {
					if (dbAdapter.getPassword(name).equals(pswd)) {
						dbAdapter.updateLogin(name, true);
						login();
					} else {
						showToast(R.string.password_incorrect);
						animateIncorrect(editPassword);
					}
				} else {
					animateIncorrect(autoTextView);
					showToast(R.string.user_not_found);
				}

			}

			private void loginWithRegister() {
				String name = autoTextView.getText().toString();
				String pswd = editPassword.getText().toString();
				String confirm = editConfirm.getText().toString();

				if (!dbAdapter.existUser(name)) {
					if (name.length() < 4) {
						showToast(R.string.username_is_too_short);
						animateIncorrect(autoTextView);
					} else if (pswd.length() < 4) {
						showToast(R.string.password_is_too_short);
						animateIncorrect(editPassword);
					} else if (!pswd.equals(confirm)) {
						showToast(R.string.pswd1_pswd2_not_correct);
						animateIncorrect(editPassword);
						animateIncorrect(editConfirm);
					} else {
						dbAdapter.insertUser(name, pswd);
						dbAdapter.updateLogin(name, true);
						login();
					}
				} else {
					animateIncorrect(autoTextView);
					showToast(R.string.user_exists);
				}
			}
		});

		

		MyTextWatcher textWatcher = new MyTextWatcher(btnLogin, new EditText[]{autoTextView, editPassword});
		autoTextView.addTextChangedListener(textWatcher);
		editPassword.addTextChangedListener(textWatcher);		
		return view;
	}

	private void startMain() {
		getActivity().finish();
		Intent intent = new Intent(getActivity(), Main.class);
		getActivity().startActivity(intent);
	}

	private void animateIncorrect(View v) {
		Animation anim = AnimationUtils.loadAnimation(getActivity(),
				android.R.anim.slide_in_left);
		anim.setFillAfter(true);
		anim.setInterpolator(getActivity(), android.R.anim.bounce_interpolator);
		v.startAnimation(anim);
	}

	private void showToast(int messageId) {
		Toast.makeText(getActivity(),
				Main.getTranslate(getActivity(), messageId), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onStop() {
		dbAdapter.close();
		super.onStop();
	}

	private void login() {
		// final TransparentProgressDialog loadingdialog = new
		// TransparentProgressDialog(getActivity(), R.drawable.world);
		final ProgressDialog loadingdialog = new ProgressDialog(getActivity(),
				R.style.StyledDialog);
		loadingdialog.setCancelable(false);
		loadingdialog.setIndeterminate(true);
		loadingdialog.setMessage(Main
				.getTranslate(getActivity(), R.string.wait));
		loadingdialog.getWindow().setBackgroundDrawableResource(
				android.R.drawable.alert_light_frame);
		loadingdialog.show();
		new Thread() {
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(1500);
				} catch (Exception e) {
					Log.e("threadmessage", e.getMessage());
				}
				loadingdialog.dismiss();
				startMain();
			}
		}.start();
	}

	// private class TransparentProgressDialog extends Dialog {
	//
	// private ImageView iv;
	//
	// public TransparentProgressDialog(Context context, int resourceIdOfImage)
	// {
	// super(context, R.style.TransparentProgressDialog);
	// WindowManager.LayoutParams wlmp = getWindow().getAttributes();
	// wlmp.gravity = Gravity.CENTER_HORIZONTAL;
	// getWindow().setAttributes(wlmp);
	// setTitle(null);
	// setCancelable(false);
	// setOnCancelListener(null);
	// LinearLayout layout = new LinearLayout(context);
	// layout.setOrientation(LinearLayout.VERTICAL);
	// LinearLayout.LayoutParams params = new
	// LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
	// LinearLayout.LayoutParams.WRAP_CONTENT);
	// iv = new ImageView(context);
	// iv.setImageResource(resourceIdOfImage);
	// layout.addView(iv, params);
	// addContentView(layout, params);
	// }
	//
	// @Override
	// public void show() {
	// super.show();
	// RotateAnimation anim = new RotateAnimation(0.0f, 360.0f ,
	// Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
	// anim.setInterpolator(new LinearInterpolator());
	// anim.setRepeatCount(Animation.INFINITE);
	// anim.setDuration(3000);
	// iv.setAnimation(anim);
	// iv.startAnimation(anim);
	// }
	// }
}

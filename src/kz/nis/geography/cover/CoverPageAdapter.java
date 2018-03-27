package kz.nis.geography.cover;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CoverPageAdapter extends FragmentPagerAdapter {
	private static final int PAGES = 5;
	private Context context;

	public CoverPageAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		position = position % PAGES;
		if (position == 0) {
			return FragmentLangs.newInstance(context);
		} else if (position == 4) {
			return FragmentAccount.newInstance(context);
		} else {
			return FragmentCoverPage.newInstance(context, position-1);
		}

	}

	@Override
	public int getCount() {
		return PAGES;
	}

}

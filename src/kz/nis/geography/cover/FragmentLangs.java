package kz.nis.geography.cover;

import kz.nis.geography.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FragmentLangs extends Fragment implements OnClickListener{

	public static Fragment newInstance(Context context) {
		Bundle b = new Bundle();
		return Fragment.instantiate(context, FragmentLangs.class.getName(),
				b);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		FrameLayout view = (FrameLayout) inflater.inflate(
				R.layout.fragment_langs, container, false);
		ImageView imageKZ = (ImageView) view.findViewById(R.id.buttonKZ);
		imageKZ.setOnClickListener(this);
		ImageView imageRU = (ImageView) view.findViewById(R.id.buttonRU);
		imageRU.setOnClickListener(this);
		ImageView imageEN = (ImageView) view.findViewById(R.id.buttonEN);
		imageEN.setOnClickListener(this);		

		return view;
	}

	@Override
	public void onClick(View v) {
			int lang = 0;
			switch (v.getId()) {
			case R.id.buttonKZ:
				lang = 0;
				break;
			case R.id.buttonRU:
				lang = 1;
				break;
			case R.id.buttonEN:
				lang = 2;
				break;
			}				
			Intent intent = getActivity().getIntent();
			intent.setAction("changelang");
			intent.putExtra("lang", lang);
			getActivity().startActivity(intent);	
	}
	

}

package kz.nis.geography.cover;

import java.text.MessageFormat;

import kz.nis.geography.Main;
import kz.nis.geography.R;
import kz.nis.geography.extra.FontFactory;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentCoverPage extends Fragment {

//	private static String[] descriptions = null;
//	private static String[] textImages = null;
//	private static int[] images;

	public static Fragment newInstance(Context context, int pos) {
		Bundle b = new Bundle();
		b.putInt("pos", pos);
//		if(textImages == null)
//		textImages = context.getResources().getStringArray(
//				R.array.TITLES_COVERPAGE);	
//		if(descriptions == null)
//		descriptions = context.getResources().getStringArray(
//				R.array.DESCRIPTIONS_COVERPAGE);		
//		if(images == null)
//		images = new int[] { R.drawable.description_1, R.drawable.description_2,
//					R.drawable.description_3 };
		return Fragment.instantiate(context, FragmentCoverPage.class.getName(),
				b);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		int pos = this.getArguments().getInt("pos");
		if(pos>=0 && pos<3 && getActivity()!=null){
			Context context = getActivity();
			String[] textImages = context.getResources().getStringArray(R.array.TITLES_COVERPAGE);	
			String[] descriptions = context.getResources().getStringArray(
					R.array.DESCRIPTIONS_COVERPAGE);	
			int[] images = new int[] { R.drawable.description_1, R.drawable.description_2,
						R.drawable.description_3 };
			
			
			
			FrameLayout l = (FrameLayout) inflater.inflate(
					R.layout.fragment_coverpage, container, false);

			ImageView content = (ImageView) l.findViewById(R.id.imageCoverPage);
			content.setImageResource(images[pos]);
			
			TextView textImage = (TextView) l
					.findViewById(R.id.textImage);
			textImage.setTypeface(FontFactory.getFont1(getActivity()));
			textImage.setText(MessageFormat.format(textImages[pos],
					Main.LANG));
			
			TextView textDescription = (TextView) l
					.findViewById(R.id.textCoverPage);
			textDescription.setTypeface(FontFactory.getFont1(getActivity()));
			textDescription.setText(MessageFormat.format(descriptions[pos],
					Main.LANG));
			return l;
		}
		
		
		return null;
	}
}

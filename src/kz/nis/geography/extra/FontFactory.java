package kz.nis.geography.extra;

import android.content.Context;
import android.graphics.Typeface;

public class FontFactory {
	
//	private static String FONT1 = "asylbek1.ttf";
//	private final static String FONT1 = "AcademyKZ.ttf";
//	private final static String FONT1 = "PalatinoLinotypeKZ_Bold.ttf";//----<-----	
//	private final static String FONT1 = "KZBookmanOldStyle.ttf";//----<-----
	private final static String FONT1 = "semibold.otf";//----<-----//for menu navigation
	private final static String FONT_DIGIT = "Skater Girls Rock.ttf";
	private static Typeface font1 = null;
	private static Typeface fontDigit = null;
	
	public static Typeface getFont1(Context context) {
		if(font1 == null) {
			font1 = Typeface.createFromAsset(context.getAssets(), FONT1);
		}
		return font1;
	}
	
	public static Typeface getFontDigit(Context context) {
		if(fontDigit == null) {
			fontDigit = Typeface.createFromAsset(context.getAssets(), FONT_DIGIT);
		}
		return fontDigit;
	}
}

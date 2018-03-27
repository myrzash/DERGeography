package kz.nis.geography.extra;

import kz.nis.geography.R;
import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
	
	private static int soundError = R.raw.error2;
	private static int soundGood = R.raw.right;
	private static MediaPlayer mediaPlayer;
	
	
	public static void playError(Context context) {
			mediaPlayer = MediaPlayer.create(context, soundError);
		mediaPlayer.start();	
		
	}
	
	public static void playGood(Context context) {
		mediaPlayer = MediaPlayer.create(context, soundGood);
		mediaPlayer.start();		
	}
}

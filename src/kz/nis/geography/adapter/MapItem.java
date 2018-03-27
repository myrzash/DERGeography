package kz.nis.geography.adapter;

import java.text.MessageFormat;

import kz.nis.geography.Main;


public class MapItem {
	
	private int tag;
	private int iconRes;
	private CharSequence text;
	private int progress;
	private int visibility;

	public MapItem(int tag, int iconRes) {
		this.tag = tag;
		this.iconRes = iconRes;
	}
		
	public MapItem(int tag, CharSequence text) {
		this.tag = tag;
		this.text = text;
	}
	
	public MapItem(CharSequence text, int progress, int tag) {
		this.text = text;
		this.progress = progress;
		this.tag = tag;
	}

	public CharSequence getText() {
		return MessageFormat.format(String.valueOf(text),Main.LANG);
	}
		
	public int getTag() {
		return tag;
	}	

	public int getIconRes() {
		return iconRes;
	}
	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public int getVisibility() {
		return visibility;
	}
	
}

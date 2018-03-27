package kz.nis.geography.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import kz.nis.geography.Main;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private static final String LOG = "LOG DATABASE";
	private static final String TABLE_USER = "users";
	// ATTRS
	public static final String ATTR_ID = "_id";
	public static final String ATTR_USER_NAME = "name";
	public static final String ATTR_USER_PSWD = "pswd";
	public static final String ATTR_TODAY = "today";
	private static final String ATTR_KEY_LOGIN = "login";
	private static final String[] ATTR_SCORES = new String[] { "score11",
			"score12", "score13", "score21", "score22", "score23", "score31",
			"score32" };

	private static final String[] ATTR_TIMES = new String[] { "time11",
			"time12", "time13", "time21", "time22", "time23", "time31",
			"time32" };
	private static final String ATTR_ATTEMPT_COUNT = "resetcount";
	private static final String[] ATTR_ATTEMPTS = new String[] { "attempt1",
			"attempt2", "attempt3", "attempt4" };

	private final Context context;
	private DBOpenHelper dbHelper;
	private SQLiteDatabase DB;
	private ContentValues CV;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		try {
			this.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void open() throws Exception {
		dbHelper = new DBOpenHelper(context);
		this.DB = dbHelper.getWritableDatabase();
		Log.d(LOG, "--- open database ---");
	}

	public void close() {
		if (dbHelper != null)
			dbHelper.close();
		Log.d(LOG, "--- close database ---");
	}

	public Cursor getAllUsers() {
		Cursor cursor = DB.query(TABLE_USER, new String[] { ATTR_USER_NAME,
				ATTR_TODAY, "score11", "score12", "score13", "score21",
				"score22", "score23", "score31", "score32" }, null, null, null,
				null, ATTR_TODAY);
		if (cursor != null)
			cursor.moveToFirst();
		return cursor;
	}

	public ArrayList<String> getUserNames() {
		Cursor cursor = DB.query(TABLE_USER, new String[] { ATTR_USER_NAME },
				null, null, null, null, null);
		if (cursor.moveToFirst()) {
			ArrayList<String> names = new ArrayList<String>();
			do {
				names.add(cursor.getString(0));
			} while (cursor.moveToNext());
			return names;
		} else
			return null;
	}

	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"HH:mm:ss - dd/MM/yyyy", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public boolean existUser(String name) {
		name = "'" + name + "'";
		Cursor cursor = DB.query(TABLE_USER, new String[] { ATTR_USER_NAME },
				ATTR_USER_NAME + "=" + name, null, null, null, null);
		if (cursor.moveToFirst()) {
			Log.w(LOG, "name: " + name + " exists");
			return true;
		}
		return false;
	}

	public void updateLogin(String name, boolean login) {
		name = "'" + name + "'";
		CV = new ContentValues();
		CV.put(ATTR_KEY_LOGIN, String.valueOf(login));
		DB.update(TABLE_USER, CV, ATTR_USER_NAME + "=" + name, null);
		Log.w(LOG, "name=" + name + ", login=" + login);
	}

	public void insertUser(String name, String pswd) {
		if (existUser(name)) {
			return;
		}
		CV = new ContentValues();
		CV.put(ATTR_USER_NAME, name);
		CV.put(ATTR_USER_PSWD, pswd);
		CV.put(ATTR_TODAY, getDateTime());
		DB.insert(TABLE_USER, null, CV);
		Log.d(LOG, "--- insertUser ---");
	}

	public CharSequence getPassword(String name) {
		name = "'" + name + "'";
		Cursor cursor = DB.query(TABLE_USER, new String[] { ATTR_USER_PSWD },
				ATTR_USER_NAME + "=" + name, null, null, null, null);
		if (cursor.moveToFirst())
			return cursor.getString(0);
		return null;
	}

	private Cursor getCursorLastUser() {
		Cursor cursor = DB.query(TABLE_USER, new String[] { ATTR_USER_NAME },
				ATTR_KEY_LOGIN + "='true'", null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();
		return cursor;
	}

	public String getLastUserName() {
		if (getCursorLastUser().moveToFirst())
			return getCursorLastUser().getString(0);
		return null;
	}

	class DBOpenHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "geography";

		public DBOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(LOG, "--- onCreate database ---");
			String execute = "create table " + TABLE_USER + " (" + ATTR_ID
					+ " integer primary key autoincrement, " + ATTR_USER_NAME
					+ " varchar(20), " + ATTR_USER_PSWD + " varchar(20), "
					+ ATTR_TODAY + " datetime, " + ATTR_KEY_LOGIN
					+ " boolean default false, " + ATTR_ATTEMPT_COUNT
					+ " integer default 0";
			for (String attr : ATTR_SCORES) {
				execute = execute + ", " + attr + " real default 0";
			}
			for (String attr : ATTR_TIMES) {
				execute = execute + ", " + attr + " integer default 0";
			}
			for (String attr : ATTR_ATTEMPTS) {
				execute = execute + ", " + attr + " integer default 0";
			}
			execute = execute + ");";
			Log.d(Main.LOG, "execute=" + execute);

			db.execSQL(execute);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	}

	public double getArithmeticScore(String userName) {

		String selection = String.format("%s = '%s'", ATTR_USER_NAME, userName);
		Cursor cursor = DB.query(TABLE_USER, ATTR_SCORES, selection, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			double score = 0;
			short i = 0;
			do {
				score = score + cursor.getDouble(i++);
			} while (i < ATTR_SCORES.length);
			score = (double) score / ATTR_SCORES.length;
			score = Math.rint(100.0 * score) / 100.0;
			Log.d(LOG, "getArithmeticScore score=" + score);
			return score;
		} else
			return 0;
	}

	public void updateScore(double score, int number, String name) {
		if (score > 98)
			score = 100;
		else
			score = Math.rint(100.0 * score) / 100.0;
		String query = "UPDATE " + TABLE_USER + " SET " + ATTR_SCORES[number]
				+ "=" + score + " WHERE " + ATTR_USER_NAME + "='" + name
				+ "' AND " + ATTR_SCORES[number] + " < " + score;
		try {
			DB.execSQL(query);
			Log.d(LOG, "updateScore: " + query);
		} catch (SQLiteException e) {
			Log.e(LOG, "ERROR! updateScore");
			e.printStackTrace();
		}
	}

	// TIMES
	public void updateTime(long time, int number, String name) {
		String query = null;
		if (getTime(name, ATTR_TIMES[number]) == 0) {
			query = String.format("UPDATE %s SET %s=%d WHERE %s='%s'",
					TABLE_USER, ATTR_TIMES[number], time, ATTR_USER_NAME, name);
		} else
			query = String.format(
					"UPDATE %s SET %s=%d WHERE %s='%s' AND %s > %d",
					TABLE_USER, ATTR_TIMES[number], time, ATTR_USER_NAME, name,
					ATTR_TIMES[number], time);
		try {
			DB.execSQL(query);
			Log.d(LOG, "updateTime: " + name + " => " + time);
		} catch (SQLiteException e) {
			Log.e(LOG, "ERROR! updateTime");
			e.printStackTrace();
		}
	}

	private int getTime(String userName, String attrTime) {
		String selection = String.format("%s = '%s'", ATTR_USER_NAME, userName);
		Cursor cursor = DB.query(TABLE_USER, new String[] { attrTime },
				selection, null, null, null, null);
		if (cursor.moveToFirst()) {
			Log.d(LOG, "getTime: " + cursor.getInt(0));
			return cursor.getInt(0);
		} else
			return 0;
	}

	private void reset(String userName) {
		String selection = String.format("%s = '%s'", ATTR_USER_NAME, userName);
		CV = new ContentValues();
		for (String attr : ATTR_SCORES) {
			CV.put(attr, 0);
		}
		for (String attr : ATTR_TIMES) {
			CV.put(attr, 0);
		}
		DB.update(TABLE_USER, CV, selection, null);
		Log.e(LOG, "resetUser=>" + selection);
	}

	private int sumTimes(String userName) {
		String selection = String.format("%s = '%s'", ATTR_USER_NAME, userName);
		Cursor cursor = DB.query(TABLE_USER, ATTR_TIMES, selection, null, null,
				null, null);
		if (cursor.moveToFirst()) {
			int time = 0;
			for (int i = 0; i < ATTR_TIMES.length; i++) {
				time = time + cursor.getInt(i);
				Log.w(LOG, i + "=" + cursor.getInt(i));
			}
			Log.w(LOG, "getAllTimes=" + time);
			return time;
		} else
			return 0;
	}
	
	public String getAllTimes(String userName) {
		int time = sumTimes(userName);
		return toTime(time);
	}

	private int getAttemptCount(String selection) {
		Cursor cursor = DB.query(TABLE_USER,
				new String[] { ATTR_ATTEMPT_COUNT }, selection, null, null,
				null, null);
		if (cursor.moveToFirst())
			return cursor.getInt(0);
		else
			return 0;
	}

	private String toTime(int x) {
		if (x == 0)
			return null;
		String str = null;
		int hour = x / 3600;
		int min = (x - hour * 3600) / 60;
		int sec = x - hour * 3600 - min * 60;
		if (hour == 0)
			str = String.format("%02d:%02d", min, sec);
		else
			str = String.format("%02d:%02d:%02d", hour, min, sec);
		Log.d(LOG, "integerToTime: " + str);
		return str;
	}

	public String[] getAttempts(String userName) {
		String selection = String.format("%s = '%s'", ATTR_USER_NAME, userName);
		Cursor cursor = DB.query(TABLE_USER, ATTR_ATTEMPTS, selection, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			String[] attempts = new String[ATTR_ATTEMPTS.length];
			for (int i = 0; i < ATTR_ATTEMPTS.length; i++) {
				Log.i(LOG, "attempts[" + i + "] = " + cursor.getInt(i));
				attempts[i] = toTime(cursor.getInt(i));
			}
			return attempts;
		} else
			return null;
	}

	public void addAttempt(String userName) {
		String whereClause = String.format("%s = '%s'", ATTR_USER_NAME,
				userName);
		CV = new ContentValues();
		int countAttempt = getAttemptCount(whereClause);
		int sum = sumTimes(userName);
		CV.put(ATTR_ATTEMPTS[countAttempt % 4], sum);
		CV.put(ATTR_ATTEMPT_COUNT, ++countAttempt);
		DB.update(TABLE_USER, CV, whereClause, null);
		reset(userName);
	}

}

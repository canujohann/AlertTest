package com.example.alerttest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlertHelper extends SQLiteOpenHelper{
	// テーブル作成用SQL
	static String TABLE_NAME = "user";
	private static final String CREATE_TABLE_SQL =	"" +
													"create table " + TABLE_NAME +  " (" +
														"rawid integer primary key autoincrement, " +
														"name text not null, " +
														"score integer not null" +
													")";

	// テーブルの削除用SQL
	private static final String DROP_TABLE_SQL = "drop table if exists user";

	/**
	 * コンストラクタ
	 *
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public AlertHelper(Context context, CursorFactory factory, int version){
		super(context, TABLE_NAME, factory, version);
	}

	//テーブル生成
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_TABLE_SQL);

		// レコード
		String INSERT_RECORD1 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"1,'A', 0" + ")";
		String INSERT_RECORD2 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"2,'B', 0" + ")";
		String INSERT_RECORD3 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"3,'C', 0" + ")";
		String INSERT_RECORD4 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"4,'D', 0" + ")";

		// 挿入
		db.execSQL(INSERT_RECORD1);
		db.execSQL(INSERT_RECORD2);
		db.execSQL(INSERT_RECORD3);
		db.execSQL(INSERT_RECORD4);
	}

	//テーブル再定義
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(DROP_TABLE_SQL);
		db.execSQL(CREATE_TABLE_SQL);

		// レコード
		String INSERT_RECORD1 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"1,'A', 0" + ")";
		String INSERT_RECORD2 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"2,'B', 0" + ")";
		String INSERT_RECORD3 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"3,'C', 0" + ")";
		String INSERT_RECORD4 =	"" +
								"insert into " + TABLE_NAME + " values(" +
								"4,'D', 0" + ")";

		// 挿入
		db.execSQL(INSERT_RECORD1);
		db.execSQL(INSERT_RECORD2);
		db.execSQL(INSERT_RECORD3);
		db.execSQL(INSERT_RECORD4);
	}

}
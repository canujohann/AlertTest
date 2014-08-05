package com.example.alerttest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AlertDao{
	// 定数
	private static final String TBL_NAME = "user";
	private static final String USR_ID = "rowId";
	private static final String USR_NAME = "name";
	private static final String USR_SCORE = "score";
	private static final String[] COLUMNS = {USR_ID, USR_NAME, USR_SCORE};

	private SQLiteDatabase db;

	/**
	 * コンストラクタ
	 * @param db
	 */
	public AlertDao(SQLiteDatabase db){
		this.db = db;
	}

	/**
	 * 全データ取得
	 * @return
	 */
	public List<AlertEntity> findAll(){
		List<AlertEntity> entityList = new ArrayList<AlertEntity>();
		Cursor cursor = db.query(TBL_NAME, COLUMNS, null, null, null, null, USR_ID);

		while(cursor.moveToNext()){
			AlertEntity entity = new AlertEntity();
			entity.setRowId(cursor.getInt(0));
			entity.setName(cursor.getString(1));
			entity.setScore(cursor.getInt(2));
			entityList.add(entity);
		}

		return entityList;
	}

	/**
	 * 特定のIDのデータを取得
	 * @param rowId
	 * @return
	 */
	public AlertEntity findById(int rowId){
		String selection = USR_ID + "=" + rowId;
		Cursor cursor = db.query(TBL_NAME, COLUMNS, selection, null, null, null, null);

		cursor.moveToNext();
		AlertEntity entity = new AlertEntity();
		entity.setRowId(cursor.getInt(0));
		entity.setName(cursor.getString(1));
		entity.setScore(cursor.getInt(2));

		return entity;
	}

	/**
	 * データの更新
	 * @param rowId
	 * @param date
	 * @return
	 */
	public int update(AlertEntity entity){
		ContentValues values = new ContentValues();
		values.put(USR_SCORE,entity.getScore());
		String whereClause = USR_ID + "=" + entity.getRowId();

		return db.update(TBL_NAME, values, whereClause, null);
	}

}
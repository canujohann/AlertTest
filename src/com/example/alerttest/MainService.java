package com.example.alerttest;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

public class MainService extends Service{
	private Timer timer;
	private SQLiteDatabase db;

	public static final String ACTION = "MainService Action";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate(){
		AlertHelper helper = new AlertHelper(this);
		db = helper.getWritableDatabase();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		if(intent != null){
			if("start".equals(intent.getAction())){
				if(timer == null){
					timer = new Timer();
					timer.schedule(new MTimer(), 0*1000, 1*1000);
				}
			}else if("stop".equals(intent.getAction())){
				if(timer != null){
					timer.cancel();
					timer = null;
				}
			}
		}
		return Service.START_STICKY;
	}

	/**
	 * タイマー処理
	 */
	class MTimer extends TimerTask{
		@Override
		public void run() {
			//Random
			Random rnd = new Random();
			int ran = rnd.nextInt(4) + 1;
			// 更新
			// テーブル作成用SQL
			String TABLE_NAME = "user";
			String USR_ID;
			USR_ID = String.valueOf(ran);
			String UPDATE_SQL =	"" +
									"update " + TABLE_NAME + " set score= (" +
										"select score from " + TABLE_NAME + " where rawid=" + USR_ID + ") + 1 " +
									"where rawid=" + USR_ID;

			db.execSQL(UPDATE_SQL);

			Intent i = new Intent(ACTION);
			i.putExtra("message", true);
			sendBroadcast(i);
		}
	}
}
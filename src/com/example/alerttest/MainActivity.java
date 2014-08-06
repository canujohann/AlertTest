package com.example.alerttest;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	// dao
	private AlertDao dao;
	// receiver
	private AlertTestReceiver mReceiver = null;
	// helper
	private AlertHelper helper = new AlertHelper(this);
	// listview
	ListView lv;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ボタンイベント
		findViewById(R.id.timerbtn).setOnClickListener(this);
		findViewById(R.id.stopbtn).setOnClickListener(this);

		// データベース
		SQLiteDatabase db = helper.getReadableDatabase();
		dao = new AlertDao(db);

		// listview
		List<AlertEntity> entityList = dao.findAll();
		String[] str = new String[entityList.size()];
		int i = -1;
		for(AlertEntity entity: entityList) {
			i++;
			str[i] = entity.getName().toString() + "(" + String.valueOf(entity.getScore() + ")" );
		}
		lv = (ListView) findViewById(R.id.resultView1);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
		lv.setAdapter(adapter);

		// レシーバー
		mReceiver = new AlertTestReceiver();
		IntentFilter filter = new IntentFilter();
		// 登録
		filter.addAction(MainService.ACTION);
		registerReceiver(mReceiver, filter);
	}

	//表示データの更新
    protected void changeData() {
		adapter.notifyDataSetChanged();

		SQLiteDatabase db = helper.getReadableDatabase();
		dao = new AlertDao(db);

		// リスト化
		createList();
    }

    private void createList(){
        List<AlertEntity> entityList = dao.findAll();
		// listview
		String[] str = new String[entityList.size()];
		int i = -1;
		for(AlertEntity entity: entityList) {
			i++;
			str[i] = entity.getName().toString() + "(" + String.valueOf(entity.getScore() + ")" );
		}
		lv = (ListView) findViewById(R.id.resultView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, str);
		lv.setAdapter(adapter);
    }

	public void onClick(View v){
		if(v.getId() == R.id.timerbtn){
			startTimerService("start");
		}else if(v.getId() == R.id.stopbtn){
			startTimerService("stop");
		}
	}

    private void startTimerService(String action){
    	Intent intent = new Intent(this, MainService.class);
    	intent.setAction(action);
    	startService(intent);
    }

    @Override
    public void onDestroy() {
     super.onDestroy();
     // レシーバーの解除
     unregisterReceiver(mReceiver);
    }

    public class AlertTestReceiver extends BroadcastReceiver{
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		// リスト更新
    		changeData();
    	}
    }
}

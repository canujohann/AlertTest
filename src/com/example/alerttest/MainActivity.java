package com.example.alerttest;

import java.util.ArrayList;
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
	//結果
	ArrayList<String> resultsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ボタンイベント
		findViewById(R.id.timerbtn).setOnClickListener(this);
		findViewById(R.id.stopbtn).setOnClickListener(this);
		
		//DB+DAO
		SQLiteDatabase db = helper.getReadableDatabase();
		dao = new AlertDao(db);

		//ListView設定
		lv = (ListView) findViewById(R.id.resultView1);
		resultsList = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, resultsList);
		lv.setAdapter(adapter);
		
		//データ更新
		changeData();

		// レシーバー
		mReceiver = new AlertTestReceiver();
		IntentFilter filter = new IntentFilter();
		// 登録
		filter.addAction(MainService.ACTION);
		registerReceiver(mReceiver, filter);
	}

	//表示データの更新
    protected void changeData() {
		
    	List<AlertEntity> entityList = dao.findAll();
    	
    	//Stringsを更新
    	resultsList.clear();							
		for(AlertEntity entity: entityList) 
			resultsList.add(entity.getName().toString() + "(" + String.valueOf(entity.getScore() + ")" ));

		adapter.notifyDataSetChanged();
    }

    /*
     * Entity list to ArrayList
     */
    protected ArrayList<String> entityToArrayList(List<AlertEntity> entityList){
    	ArrayList<String> result = new ArrayList<String>();
    	for(AlertEntity entity: entityList) 
    		result.add(entity.getName().toString() + "(" + String.valueOf(entity.getScore() + ")" ));
    	return result;
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

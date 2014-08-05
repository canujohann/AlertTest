package com.example.alerttest;

public class AlertEntity{
	private int rowId;
	private String Name;
	private int Score;

	// rowId
	public void setRowId(int rowId){
		this.rowId = rowId;
	}
	public int getRowId(){
		return rowId;
	}

	// Name
	public void setName(String Name){
		this.Name = Name;
	}
	public String getName(){
		return Name;
	}

	// Score
	public void setScore(int Score){
		this.Score = Score;
	}
	public int getScore(){
		return Score;
	}
}
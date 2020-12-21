package com.apps.kunalfarmah.Spo2Watcher.model;

import com.google.gson.annotations.SerializedName;

public class RegionalItem{

	@SerializedName("loc")
	private String loc;

	@SerializedName("number")
	private String number;

	public String getLoc(){
		return loc;
	}

	public String getNumber(){
		return number;
	}

	@Override
 	public String toString(){
		return 
			"com.apps.kunalfarmah.Spo2Watcher.model.RegionalItem{" +
			"loc = '" + loc + '\'' + 
			",number = '" + number + '\'' + 
			"}";
		}
}

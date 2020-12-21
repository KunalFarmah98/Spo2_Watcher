package com.apps.kunalfarmah.Spo2Watcher.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contacts{

	@SerializedName("regional")
	private List<RegionalItem> regional;

	@SerializedName("primary")
	private Primary primary;

	public List<RegionalItem> getRegional(){
		return regional;
	}

	public Primary getPrimary(){
		return primary;
	}

	@Override
 	public String toString(){
		return 
			"com.apps.kunalfarmah.Spo2Watcher.model.Contacts{" +
			"regional = '" + regional + '\'' + 
			",primary = '" + primary + '\'' + 
			"}";
		}
}

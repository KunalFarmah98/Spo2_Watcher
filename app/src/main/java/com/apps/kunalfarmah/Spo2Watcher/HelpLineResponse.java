package com.apps.kunalfarmah.Spo2Watcher;

import com.google.gson.annotations.SerializedName;

public class HelpLineResponse{

	@SerializedName("lastRefreshed")
	private String lastRefreshed;

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("lastOriginUpdate")
	private String lastOriginUpdate;

	public String getLastRefreshed(){
		return lastRefreshed;
	}

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getLastOriginUpdate(){
		return lastOriginUpdate;
	}

	@Override
 	public String toString(){
		return 
			"com.apps.kunalfarmah.Spo2Watcher.HelpLineResponse{" +
			"lastRefreshed = '" + lastRefreshed + '\'' + 
			",data = '" + data + '\'' + 
			",success = '" + success + '\'' + 
			",lastOriginUpdate = '" + lastOriginUpdate + '\'' + 
			"}";
		}
}

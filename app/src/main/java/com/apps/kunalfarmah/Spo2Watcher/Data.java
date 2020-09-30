package com.apps.kunalfarmah.Spo2Watcher;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("contacts")
	private Contacts contacts;

	public Contacts getContacts(){
		return contacts;
	}

	@Override
 	public String toString(){
		return 
			"com.apps.kunalfarmah.Spo2Watcher.Data{" +
			"contacts = '" + contacts + '\'' + 
			"}";
		}
}

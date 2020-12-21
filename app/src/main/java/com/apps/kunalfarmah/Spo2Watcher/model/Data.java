package com.apps.kunalfarmah.Spo2Watcher.model;

import com.apps.kunalfarmah.Spo2Watcher.model.Contacts;
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
			"com.apps.kunalfarmah.Spo2Watcher.model.Data{" +
			"contacts = '" + contacts + '\'' + 
			"}";
		}
}

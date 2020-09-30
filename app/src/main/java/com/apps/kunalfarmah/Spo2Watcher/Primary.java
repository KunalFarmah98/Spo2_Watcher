package com.apps.kunalfarmah.Spo2Watcher;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Primary{

	@SerializedName("number")
	private String number;

	@SerializedName("twitter")
	private String twitter;

	@SerializedName("number-tollfree")
	private String numberTollfree;

	@SerializedName("facebook")
	private String facebook;

	@SerializedName("media")
	private List<String> media;

	@SerializedName("email")
	private String email;

	public String getNumber(){
		return number;
	}

	public String getTwitter(){
		return twitter;
	}

	public String getNumberTollfree(){
		return numberTollfree;
	}

	public String getFacebook(){
		return facebook;
	}

	public List<String> getMedia(){
		return media;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"com.apps.kunalfarmah.Spo2Watcher.Primary{" +
			"number = '" + number + '\'' + 
			",twitter = '" + twitter + '\'' + 
			",number-tollfree = '" + numberTollfree + '\'' + 
			",facebook = '" + facebook + '\'' + 
			",media = '" + media + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}

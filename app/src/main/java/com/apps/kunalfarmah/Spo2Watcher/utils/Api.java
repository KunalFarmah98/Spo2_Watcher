package com.apps.kunalfarmah.Spo2Watcher.utils;

import com.apps.kunalfarmah.Spo2Watcher.model.HelpLineResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api{
    @GET("/covid19-in/contacts")
    Call<HelpLineResponse>getNumbers();
}

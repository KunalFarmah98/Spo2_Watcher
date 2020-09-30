package com.apps.kunalfarmah.Spo2Watcher;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api{
    @GET("/covid19-in/contacts")
    Call<HelpLineResponse>getNumbers();
}

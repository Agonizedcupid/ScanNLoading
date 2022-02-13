package com.aariyan.scannloading.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIs {

    @GET("users.php")
    Call<ResponseBody> getUser();
}

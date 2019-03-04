package com.example.mypc.donasiid._api;

import com.example.mypc.donasiid._modul.ItemUser;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login/index.php")
    Call<ItemUser> login(@Field("id_email") String id_email , @Field("id_pass") String id_pass);

}

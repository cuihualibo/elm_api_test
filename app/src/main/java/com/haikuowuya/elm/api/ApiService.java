package com.haikuowuya.elm.api;

import com.google.gson.JsonElement;
import com.haikuowuya.elm.http.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiService {
    public  static  final  String BASR_URL="https://openapi.ele.me/v2/";


    /***
     *根据地理位置搜索附近的餐厅
     * @param geo
     * @return
     */
    @GET("restaurants/")
    Observable<HttpResult<String>> searchRestaurants(@Query("geo")String geo);
}

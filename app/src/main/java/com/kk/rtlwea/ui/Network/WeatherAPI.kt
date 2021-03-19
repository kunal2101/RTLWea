package com.kk.rtlwea.ui.Network

import com.kk.rtlwea.ui.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {


    @GET("weather")
   suspend fun getWeather(
        @Query("q") q:String,
        @Query("appid") appId:String
    ): Response<Weather>
}
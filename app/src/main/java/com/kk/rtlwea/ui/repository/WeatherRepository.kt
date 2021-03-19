package com.kk.rtlwea.ui.repository

import com.kk.rtlwea.ui.Network.RetrofitInstance

class WeatherRepository {

suspend fun getWheatherDetail(city : String ) =

    RetrofitInstance.api.getWeather(city,"438d1926c1ea748fa687b8ce94915178")

}
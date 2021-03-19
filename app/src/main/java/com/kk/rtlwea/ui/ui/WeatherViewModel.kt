package com.kk.rtlwea.ui.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.rtlwea.ui.Utils.Resource
import com.kk.rtlwea.ui.model.Weather
import com.kk.rtlwea.ui.repository.WeatherRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class WeatherViewModel (val weatherRepository : WeatherRepository)  : ViewModel(){
    val weatherResponse:MutableLiveData<Resource<Weather>> = MutableLiveData()



    fun searchCity(searchCity : String) = viewModelScope.launch {
        weatherResponse.postValue(Resource.Loading())

        val responce = weatherRepository.getWheatherDetail(searchCity)
        weatherResponse.postValue(handleWeatherResponce(responce))


    }


    private  fun  handleWeatherResponce(responce: Response<Weather>) : Resource<Weather>{
        if(responce.isSuccessful){
            responce.body()?.let { resultresponce ->
                return  Resource.Sucess(resultresponce)
            }
        }

        return Resource.Error(responce.message())
    }


}
package com.kk.rtlwea.ui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kk.rtlwea.R
import com.kk.rtlwea.ui.Utils.Resource
import com.kk.rtlwea.ui.repository.WeatherRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: WeatherViewModel
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val weatherepository = WeatherRepository()
        val viewModelProviderFactory = WeatherViewModelProviderFactory(weatherepository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(WeatherViewModel::class.java)
        initializeSearchView()
        //viewModel.weatherResponse.observe(viewLife)
        viewModel.weatherResponse.observe(this, Observer { response ->
            when (response) {
                is Resource.Sucess -> {

                    response.data?.let { WeatherResponce ->

                        Log.d("responce", WeatherResponce.name + "")
                        if (WeatherResponce.weather[0].description == "clear sky") {
                             Glide.with(this)
                                 .load(R.drawable.clouds)
                                 .into(image)
                        } else
                            if (WeatherResponce.weather[0].description == "haze" || WeatherResponce.weather[0].description == "overcast clouds" || WeatherResponce.weather[0].description == "fog") {
                                Glide.with(this)
                                    .load(R.drawable.haze)
                                    .into(image)
                            } else
                                if (WeatherResponce.weather[0].description == "rain") {
                                    Glide.with(this)
                                        .load(R.drawable.rain)
                                        .into(image)
                                }

                        temp.text = (WeatherResponce.main.temp - 273.15  ).toString().substringBefore(".") + "°C"
                        name.text = WeatherResponce.name
                        description.text = WeatherResponce.weather[0].description
                        speed.text = WeatherResponce.wind.speed.toString()
                        humidity.text = WeatherResponce.main.humidity.toString()
                        degree.text = WeatherResponce.wind.deg.toString()
                    }
                }

                is Resource.Error -> {
                    // hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An Error occured $message")
                    }
                }

                is Resource.Loading -> {

                    //showProgressBar()
                }
            }
        })
/*
        viewModel.weatherResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Sucess -> {
                    //hideProgressBar()
                    response.data?.let { newsResponce ->
                       Log.d("responce",newsResponce.name+"")
                    }
                }

                is Resource.Error -> {
                   // hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG,"An Error occured $message")
                    }
                }

                is Resource.Loading -> {

                    //showProgressBar()
                }
            }
        })
*/

    }

    private fun initializeSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { cityname ->
                    viewModel.searchCity(cityname)

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


    }
}
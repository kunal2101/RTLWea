package com.kk.rtlwea.ui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kk.rtlwea.ui.repository.WeatherRepository

class WeatherViewModelProviderFactory(val newsRepository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  WeatherViewModel(newsRepository  ) as T
    }
}
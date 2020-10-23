package com.nikhil.petsinfoapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikhil.petsinfoapp.ui.MainViewModel
import com.nikhil.petsinfoapp.ui.MainViewRepositoryImpl

object AppModule {

    private val mainViewRepository = MainViewRepositoryImpl()

    var factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                mainViewRepository) as T
        }
    }
}
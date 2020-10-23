package com.nikhil.petsinfoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nikhil.petsinfoapp.model.ApiResponse
import com.nikhil.petsinfoapp.model.Pet
import com.nikhil.petsinfoapp.model.Settings
import com.nikhil.petsinfoapp.util.Constant
import java.util.*

/**
 * View Model for Main Activity
 */
class MainViewModel(repository: MainViewRepository) : ViewModel() {

    private val petInfo = repository.fetchPetInfo()

    private val settings = repository.fetchSettings()

    fun getPetInfo(): LiveData<ApiResponse<List<Pet>>> = petInfo

    fun getSettings(): LiveData<ApiResponse<Settings>> = settings

    /**
     * Check if the office timings are over
     */
    fun checkIfOfficeHours(): Boolean {

        //as the work hours are not properly defined as json parameter so reading the from and
        //to value as hardcoded, but we can read the json values and replace it with actual values.
        settings.value?.data?.workHours.let {
            val from = Constant.hourFrom // Hard coded, we can read from api if required
            val to = Constant.hourTo  // Hard coded, we can read from api if required
            val date = Date()
            val calendar = Calendar.getInstance()
            calendar.time = date
            val time = calendar.get(Calendar.HOUR_OF_DAY) * 100 + calendar.get(Calendar.MINUTE)
            return to > from && time >= from && time <= to || to < from && (time >= from || time <= to)
        }
    }
}
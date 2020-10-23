package com.nikhil.petsinfoapp.ui

import androidx.lifecycle.LiveData
import com.nikhil.petsinfoapp.model.ApiResponse
import com.nikhil.petsinfoapp.model.Pet
import com.nikhil.petsinfoapp.model.Settings

/**
 * Repository used to fetch the data from server
 */
interface MainViewRepository {

    /**
     * Fetch Settings data which will be used to display the UI based on settings value
     */
    fun fetchSettings(): LiveData<ApiResponse<Settings>>

    /**
     * Fetch List of pets which will be display to users
     */
    fun fetchPetInfo(): LiveData<ApiResponse<List<Pet>>>
}
package com.nikhil.petsinfoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikhil.petsinfoapp.di.NetworkModule
import com.nikhil.petsinfoapp.model.ApiResponse
import com.nikhil.petsinfoapp.model.Pet
import com.nikhil.petsinfoapp.model.Settings
import com.nikhil.petsinfoapp.util.Constant
import com.nikhil.petsinfoapp.util.State
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject

class MainViewRepositoryImpl : MainViewRepository {

    override fun fetchSettings(): LiveData<ApiResponse<Settings>> {

        val settings: MutableLiveData<ApiResponse<Settings>> = MutableLiveData()

        NetworkModule.getData(Constant.settingUrl, object : Callback {

            override fun onFailure(call: Call, e: java.io.IOException) {
                settings.postValue(
                    ApiResponse(
                        data = Settings(true, true, Constant.workingHour),
                        state = State.FAIL
                    )
                )
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                try {
                    val json = JSONObject(responseData)
                    val settingJson = json.getJSONObject("settings")
                    val settingsTemp: Settings = Settings(
                        settingJson.getBoolean("isChatEnabled"),
                        settingJson.getBoolean("isCallEnabled"),
                        settingJson.getString("workHours")
                    )
                    settings.postValue(ApiResponse(settingsTemp, State.SUCCESS))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
        return settings
    }

    override fun fetchPetInfo(): LiveData<ApiResponse<List<Pet>>> {

        val pet: MutableLiveData<ApiResponse<List<Pet>>> = MutableLiveData()
        val petList: MutableList<Pet> = ArrayList()

        NetworkModule.getData(Constant.petInfoUrl, object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                pet.postValue(ApiResponse(emptyList(), State.FAIL))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                try {
                    val jsonObj = JSONObject(responseData)

                    // Getting JSON Array node
                    val petsJsonArray = jsonObj.getJSONArray("pets")

                    petsJsonArray?.let {

                        for (i in 0 until it.length()) {
                            val petJsonObject = petsJsonArray.getJSONObject(i)
                            val petsInfo = Pet(
                                petJsonObject.getString("image_url"),
                                petJsonObject.getString("title"),
                                petJsonObject.getString("content_url"),
                                petJsonObject.getString("date_added")
                            )
                            petList.add(petsInfo)
                        }
                        pet.postValue(ApiResponse(petList, State.SUCCESS))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
        return pet
    }
}

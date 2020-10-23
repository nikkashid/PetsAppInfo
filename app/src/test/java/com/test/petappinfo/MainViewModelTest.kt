package com.nikhil.petsinfoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nikhil.petsinfoapp.model.ApiResponse
import com.nikhil.petsinfoapp.model.Pet
import com.nikhil.petsinfoapp.model.Settings
import com.nikhil.petsinfoapp.ui.MainViewModel
import com.nikhil.petsinfoapp.ui.MainViewRepository
import com.nikhil.petsinfoapp.util.State
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var observer: Observer<ApiResponse<List<Pet>>>

    @Mock
    lateinit var observerSettings: Observer<ApiResponse<Settings>>

    @Mock
    private lateinit var mainViewRepository: MainViewRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(mainViewRepository)
    }

    @Test
    fun settingsTest() {

        val settingsData: MutableLiveData<ApiResponse<Settings>> = MutableLiveData()

        val settings = Settings(true, true, "")

        val apiResponse = ApiResponse<Settings>(settings, State.SUCCESS)

        settingsData.value = apiResponse

        `when`(mainViewRepository.fetchSettings()).thenReturn(settingsData)

        viewModel.getSettings()?.observeForever(observerSettings)

        viewModel.getSettings()
        assertNull(viewModel.getSettings()?.value)
    }

    @Test
    fun petListTest() {

        val petInfo: MutableLiveData<ApiResponse<List<Pet>>> = MutableLiveData()

        val pet = Pet("", "", "", "")

        val petInfoList = listOf<Pet>(pet)

        val apiResponse = ApiResponse<List<Pet>>(petInfoList, State.SUCCESS)

        petInfo.value = apiResponse

        `when`(mainViewRepository.fetchPetInfo()).thenReturn(petInfo)

        viewModel.getPetInfo()?.observeForever(observer)

        viewModel.getPetInfo()

        assertNull(viewModel.getPetInfo()?.value)
    }
}
package com.nikhil.petsinfoapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.petsinfoapp.databinding.ActivityMainBinding
import com.nikhil.petsinfoapp.di.AppModule
import com.nikhil.petsinfoapp.model.ApiResponse
import com.nikhil.petsinfoapp.model.Pet
import com.nikhil.petsinfoapp.model.Settings
import com.nikhil.petsinfoapp.util.Constant
import com.nikhil.petsinfoapp.util.State

/**
 * Main Activity used to display list of Pets, Chat and Call options to users.
 */
class MainActivity : AppCompatActivity(), PetInfoAdapter.OnItemClickListener {

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, AppModule.factory).get(MainViewModel::class.java)
    }

    private lateinit var petList: RecyclerView
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var petInfoAdapter: PetInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(
            this,
            com.nikhil.petsinfoapp.R.layout.activity_main
        )
        activityMainBinding.mainViewModel = mainViewModel

        petList = activityMainBinding.rvPetsResult

        petInfoAdapter = PetInfoAdapter(this)
        petList.adapter = petInfoAdapter

        petList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        updateUi()

        activityMainBinding.btnCall.setOnClickListener { displayMessage() }
        activityMainBinding.btnChat.setOnClickListener { displayMessage() }
    }

    private fun displayMessage() {
        Toast.makeText(
            this,
            if (mainViewModel.checkIfOfficeHours()) com.nikhil.petsinfoapp.R.string.within_work_hour else com.nikhil.petsinfoapp.R.string.outside_work_hour,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateUi() {
        // Create the observer which updates the UI.

        val petListObserver = Observer<ApiResponse<List<Pet>>> { petList ->
            // Update the UI

            when (petList.state) {
                State.LOADING -> activityMainBinding.pgLoading.visibility =
                    View.VISIBLE
                State.FAIL -> {
                    activityMainBinding.error.visibility = View.VISIBLE
                    activityMainBinding.pgLoading.visibility = View.GONE
                }
                else -> petList.data.let {
                    petInfoAdapter.setResults(it)
                    petInfoAdapter.notifyDataSetChanged()
                    activityMainBinding.pgLoading.visibility = View.GONE
                }
            }
        }

        val settingsObserver = Observer<ApiResponse<Settings>> { settings ->
            // Update the UI, in this case, a TextView.
            if (settings.state == State.FAIL) {
                activityMainBinding.error.visibility = View.VISIBLE
                activityMainBinding.pgLoading.visibility = View.GONE
            } else {
                activityMainBinding.pgLoading.visibility = View.GONE
                settings.data.let {
                    activityMainBinding.txtOfficeHours.text = String.format(
                        resources.getString(com.nikhil.petsinfoapp.R.string.office_hour_string),
                        it.workHours
                    )
                    activityMainBinding.btnCall.visibility =
                        if (it.isCallEnabled) View.VISIBLE else View.GONE
                    activityMainBinding.btnChat.visibility =
                        if (it.isChatEnabled) View.VISIBLE else View.GONE
                }
            }
        }

        mainViewModel.getPetInfo().observe(this, petListObserver)
        mainViewModel.getSettings().observe(this, settingsObserver)
    }

    override fun onItemClick(item: Pet) {

        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(Constant.contentUrl, item.contentUrl)
            putExtra(Constant.title, item.title)
        }
        startActivity(intent)
    }
}

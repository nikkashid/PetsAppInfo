package com.nikhil.petsinfoapp.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nikhil.petsinfoapp.R
import com.nikhil.petsinfoapp.databinding.ActivityDetailsBinding
import com.nikhil.petsinfoapp.util.Constant

/**
 * Details activity which will display the pet details by fetching the data in web view
 */
class DetailsActivity : AppCompatActivity() {

    lateinit var activityDetailsBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailsBinding = DataBindingUtil.setContentView<ActivityDetailsBinding>(
            this,
            R.layout.activity_details
        )
        activityDetailsBinding.webView.webViewClient = WebViewClient()
        if (intent.hasExtra(Constant.contentUrl))
            activityDetailsBinding.webView.loadUrl(intent.getStringExtra(Constant.contentUrl))
    }
}

package com.vibesoflove.ui.loading

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.vibesoflove.R
import com.vibesoflove.system.BaseActivity
import com.vibesoflove.ui.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject


class SplashActivity : BaseActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, factory).get(SplashViewModel::class.java)
    }


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.preload)
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        videoView.setVideoURI(uri)
        videoView.start()

            observe(viewModel.launchMainScreen){
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
            }
    }

}
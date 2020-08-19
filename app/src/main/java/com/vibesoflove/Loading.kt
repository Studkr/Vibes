package com.vibesoflove

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.VideoView
import java.util.*

class Loading : AppCompatActivity() {
    lateinit var videoView: VideoView
    lateinit var  uri:Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        videoView = findViewById<View>(R.id.videoView) as VideoView
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val params = videoView.layoutParams as android.widget.RelativeLayout.LayoutParams
        params.width = metrics.widthPixels
        params.height = metrics.heightPixels
        params.leftMargin = 0
        videoView.layoutParams = params
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.preload)
        videoView.setVideoURI(uri)
        videoView.start()
        val intent  = Intent(this,MainActivity::class.java)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(intent)
            }
        }, 4000)
    }
}

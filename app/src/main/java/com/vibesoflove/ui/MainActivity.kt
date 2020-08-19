package com.vibesoflove.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vibesoflove.R
import com.vibesoflove.system.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
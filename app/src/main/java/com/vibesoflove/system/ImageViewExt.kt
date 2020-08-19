package com.flipsidegroup.nmt.system

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageCrop(uri:String){
        Glide.with(this).load(uri)
            .centerCrop()
            .into(this)
}
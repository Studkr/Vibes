package com.vibesoflove

import android.content.Context
import android.media.MediaPlayer

class PlayMusik {

    var media:MediaPlayer = MediaPlayer()

    fun play(position :Int,context: Context,fragment:Int){
        if(position==1) {
            when (fragment) {
                1 -> media = MediaPlayer.create(context, R.raw.med_audio)
                2 -> media = MediaPlayer.create(context, R.raw.med_audio)
                3 -> media = MediaPlayer.create(context, R.raw.med_audio)
                4 -> media = MediaPlayer.create(context, R.raw.med_audio)
                5 -> media = MediaPlayer.create(context, R.raw.med_audio)
                6 -> media = MediaPlayer.create(context, R.raw.med_audio)
                7 -> media = MediaPlayer.create(context, R.raw.med_audio)
                8 -> media = MediaPlayer.create(context, R.raw.med_audio)
                9 -> media = MediaPlayer.create(context, R.raw.med_audio)
                10 -> media = MediaPlayer.create(context, R.raw.med_audio)
            }
            media.setOnPreparedListener() { mp -> mp.isLooping = true }
            startPlay()
        }else{
            media.stop()
        }
     }

    fun startPlay(){
        media.start()
    }

    fun stopPlay(){
        media.stop()
    }
}
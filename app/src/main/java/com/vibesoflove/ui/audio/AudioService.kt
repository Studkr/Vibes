package com.vibesoflove.ui.audio

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.vibesoflove.R
import dagger.android.DaggerService
import javax.inject.Inject


class AudioService : DaggerService() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    companion object {
        var running : Int = 0

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, AudioService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
            running = 1
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, AudioService::class.java)
            context.stopService(stopIntent)
            running = 0
        }
    }

    private val notificationId = 1234
    private var notification:Notification? = null
    @Inject
    lateinit var audioPlayer: AudioPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val mediaDescriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                return null
            }

            override fun getCurrentContentText(player: Player): CharSequence? {
                return null
            }

            override fun getCurrentContentTitle(player: Player): CharSequence {
                return player.currentTag.toString()
            }

            override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
            ): Bitmap? {
                return BitmapFactory.decodeResource(resources, R.drawable.logo)
            }

        }

         PlayerNotificationManager.createWithNotificationChannel(
                this,
                "vibration",
                R.string.app_name,
                R.string.app_name,
                notificationId,
                mediaDescriptionAdapter,
                object : PlayerNotificationManager.NotificationListener {
                    override fun onNotificationPosted(
                            notificationId: Int,
                            notification: Notification,
                            ongoing: Boolean
                    ) {
                        if(ongoing){
                            startForeground(notificationId,notification)
                        }else{
                            stopForeground(false)
                        }
                    }
                    override fun onNotificationCancelled(
                            notificationId: Int,
                            dismissedByUser: Boolean
                    ) {
                    }
                }
        ).apply {
            setUseNavigationActions(true)
            setUseNavigationActionsInCompactView(true)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setPlayer(audioPlayer.exoPlayer)
            setSmallIcon(R.drawable.logo)
        }

    }

}
package com.vibesoflove.ui.audio

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import androidx.core.app.NotificationCompat
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

    private val notificationId = 1234

    @Inject
    lateinit var audioPlayer: AudioPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            createNotification()
        }
        return Service.START_STICKY
    }

    private fun createNotification() {

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
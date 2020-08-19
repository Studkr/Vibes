package com.vibesoflove

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.Fragment
import android.util.Log
import android.view.View
import android.widget.*

import com.vibesoflove.Adapter.RecyclerAdapter
import com.vibesoflove.Construct.Bottom_construct
import com.vibesoflove.Construct.Constant
import com.vibesoflove.Construct.Room_constr
import com.vibesoflove.Interface.choiseInterface
import com.vibesoflove.Interface.topInterface
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList
import java.util.concurrent.TimeUnit
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.gms.ads.rewarded.RewardItem


class MainActivity : AppCompatActivity(), topInterface, RewardedVideoAdListener  {


    private val groceryList = ArrayList<Room_constr>()
    private var groceryRecyclerView: RecyclerView? = null
    private var groceryAdapter: RecyclerAdapter? = null
    private val bottom = ArrayList<Bottom_construct>()
    internal var i = 0
    internal var vibrarion_status = 0
    lateinit var air_image: ImageView
    lateinit var imageSound:ImageView
    lateinit var imageVibro:ImageView
    lateinit var imageRate:ImageView
    lateinit var imageShare:ImageView
    lateinit var imageFull:ImageView
    lateinit var imageCart:ImageView
    internal lateinit var v: Vibrator
    internal lateinit var start: ImageView
    lateinit var player: MediaPlayer
    lateinit var  text_room:TextView
    lateinit var  text_about:TextView
    var play = 0
    var vibr_power = 0
    lateinit var manager: AudioManager
    var bottom_choise = 0
    var top_choise = 1
    lateinit var videoView: VideoView
    var vibro_check:Boolean?=null
    lateinit var  uri:Uri
    var check_work:Boolean?=null
    var state = 0
    lateinit var container :RelativeLayout
    lateinit var playMusic:ImageView
    internal  lateinit var roomArray:Array<String>
    internal  lateinit var roomAbout:Array<String>
    internal lateinit var room_name:TextView
    lateinit var  about_room: TextView
   lateinit var thread :Thread
    val APP_PREFERENCES = "powervibro"
    val APP_PREFERENCES_COUNTER = "counter"
    val APP_PERF = "room"
    val APP_ADOFF="adoff"
    var adoff:Boolean= false
    lateinit var pref: SharedPreferences
    lateinit var mAdView : AdView
    var check :Int = 0
    var song:Int=0
    var reclama = 0
    private lateinit var mRewardedVideoAd: RewardedVideoAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        adoff= pref.getBoolean(APP_ADOFF,false)
        setContentView(R.layout.activity_main)

        var get = intent.getIntExtra("Choise",55)
        if(get!=55){
            top_choise=get
        }
        v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        groceryRecyclerView = findViewById(R.id.idRecyclerViewHorizontalList)
        groceryAdapter = RecyclerAdapter(groceryList, applicationContext, this,top_choise)
        val horizontalLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        groceryRecyclerView!!.layoutManager = horizontalLayoutManager
        groceryRecyclerView!!.adapter = groceryAdapter!!
        videoView = findViewById<View>(R.id.videoView) as VideoView
        manager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        container = findViewById<View>(R.id.fragment) as RelativeLayout
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        //предопределяем переменные ботом меню
        imageVibro = findViewById<View>(R.id.imageSound)as ImageView
        imageShare = findViewById<View>(R.id.imageShare)as ImageView
        imageRate=findViewById<View>(R.id.imageRate)as ImageView
        imageFull = findViewById<View>(R.id.imageFuul) as ImageView
        imageCart = findViewById<View>(R.id.imageCart) as ImageView
        playMusic = findViewById<View>(R.id.button_start) as ImageView
        populategroceryList()
        room_name = findViewById<View>(R.id.text_room_name3) as TextView
        about_room = findViewById<View>(R.id.about_room_text3) as TextView
       if(adoff==true){

       }else {
           val adView = AdView(this)
           adView.adSize = AdSize.FULL_BANNER
           adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
           mAdView = findViewById(R.id.adView)
           val adRequest = AdRequest.Builder().build()
           mAdView.loadAd(adRequest)
       }
        roomArray = resources.getStringArray(R.array.rooms)
        roomAbout = resources.getStringArray(R.array.about)
        room_name.text = roomArray[top_choise].toString()
        about_room.text= roomAbout[top_choise].toString()

        playMusic.setOnClickListener{
            playAudio()
            thread = object :Thread(){
                override fun run() {
                    if (vibrarion_status == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibroM()
                                //createWaveFormVibrationUsingVibrationEffectAndAmplitude()
                            } else {
                               vibroM()
                            }
                    }
                }
            }
            thread.start()
        }
        checkPremission()
        onClick()
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()
    }
    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                AdRequest.Builder().build())
    }

    override fun onRewardedVideoAdLeftApplication() {

    }

    override fun onRewardedVideoAdClosed() {

        loadRewardedVideoAd()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {

    }

    override fun onRewardedVideoAdLoaded() {

    }

    override fun onRewardedVideoAdOpened() {

    }

    override fun onRewardedVideoStarted() {

    }

    override fun onRewardedVideoCompleted() {

    }
    fun saveShared(){
        val editor = pref.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, check)
        editor.apply()
    }

    override fun onRewarded(p0: com.google.android.gms.ads.reward.RewardItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private fun onClick(){
        imageVibro.setOnClickListener { view->vibroOffOn() }
        imageRate.setOnClickListener {view ->openMarket() }
        imageCart.setOnClickListener {view->buy()}
        imageShare.setOnClickListener { view->share()}
        imageFull.setOnClickListener{view->full()}
    }

    fun full(){
        if(state ==0){
            imageShare.setVisibility(View.GONE);
            imageCart.visibility=View.GONE
            imageRate.visibility = View.GONE
            imageVibro.visibility = View.GONE
            groceryRecyclerView!!.visibility = View.GONE
            container.visibility = View.GONE
            state =1
        }else{
            imageShare.setVisibility(View.VISIBLE);
            imageCart.visibility=View.VISIBLE
            imageRate.visibility = View.VISIBLE
            imageVibro.visibility = View.VISIBLE
            groceryRecyclerView!!.visibility = View.VISIBLE
            container.visibility = View.VISIBLE
            state = 0
        }
    }

    fun share(){
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND, Uri.parse("https://play.google.com/store/apps/details?id=com.my.artem.superschoolquest&hl=ru"))
        sharingIntent.type = "text/plain"
        val shareBody = "Vibe of love" + " " + "https://play.google.com/store/apps/details?id=com.my.artem.superschoolquest"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    fun buy(){
        if(adoff==false) {
            val fragmentManager = supportFragmentManager
            val ff = BlankFragment()
            ff.show(fragmentManager, "dialog")
        }else{
            messegeToast("Has been purchased")
        }
    }

    fun openMarket(){
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
            onPause()
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
            onPause()
        }
    }

    override fun onResume() {
        val metrics = DisplayMetrics()
        adoff= pref.getBoolean(APP_ADOFF,false)
        windowManager.defaultDisplay.getMetrics(metrics)
        val params = videoView.layoutParams as android.widget.RelativeLayout.LayoutParams
        params.width = metrics.widthPixels
        params.height = metrics.heightPixels
        params.leftMargin = 0
        videoView.layoutParams = params
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }
        room_name.text = roomArray[top_choise].toString()
        about_room.text= roomAbout[top_choise].toString()
        when(top_choise){
            1-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.medita)
                song = R.raw.med_audio
                //player=MediaPlayer.create(this, R.raw.med_audio)
                //player = MediaPlayer.create(this,st)
            }
            0->{
                if(adoff==true){
                    var intent:Intent = Intent(this,Personal::class.java)
                    startActivity(intent)
                }else {
                    val fragmentManager = supportFragmentManager
                    val ff = dialog_personal()
                    ff.show(fragmentManager, "dialog")
                }
            }
            2-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.harmon)
                song = R.raw.harmony
                //player =MediaPlayer.create(this, R.raw.harmony)
            }
            3-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.night)
                song = R.raw.night_city
                //player =MediaPlayer.create(this, R.raw.night_city)
            }
            4-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.ocean)
                song = R.raw.oc
                //player =MediaPlayer.create(this, R.raw.oc)
            }
            5-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.rain_v)
                song = R.raw.rain
                //player =MediaPlayer.create(this, R.raw.rain)
            }
            6-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.rom)
                song = R.raw.romantic
                //  player =MediaPlayer.create(this, R.raw.romantic)
            }
            7-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.star)
                song = R.raw.starfall
                //player =MediaPlayer.create(this, R.raw.starfall)
            }
            8-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.imm)
                song = R.raw.immersion
                //player =MediaPlayer.create(this, R.raw.immersion)
            }
            9-> {
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.fi)
                song = R.raw.fire
                // player =MediaPlayer.create(this, R.raw.fire)
            }
            10->{
                uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.ac)
                song = R.raw.aquarium
                //player =MediaPlayer.create(this, R.raw.aquarium)
            }
        }
        player = MediaPlayer.create(this,song)
        Log.d("CHOISE",""+top_choise)
        videoView.setVideoURI(uri)
        videoView.start()
        reclama++
        showReclamma()
        super.onResume()
    }

    fun showReclamma(){
        if(adoff==false) {
            if (reclama == 6) {
                reclama = 0
                Log.d("RECLAMMA", "reclamma$reclama")
                if (mRewardedVideoAd.isLoaded) {
                    mRewardedVideoAd.show()
                }

            }
        }else{

        }
    }
    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(this)
    }
    override fun onBackPressed() {

    }


    fun playAudio() {
        val i = player.duration
        var time = 0
        if(player.isPlaying){
            player.stop()
        }
        if (play == 0) {
            check_work=true
            playMusic.setImageResource(R.drawable.pause)
            player.setOnPreparedListener() {mp->mp.isLooping=true  }
            player.isLooping = true
            player.start()
            play++
            Log.d("VIBRO","play")
            //v.vibrate(vibr_power.toLong())
        } else {
            Log.d("VIBRO","pause")
            check_work=false
            playMusic.setImageResource(R.drawable.play)
            thread.isDaemon
            player.stop()
            player = MediaPlayer.create(this,song)
            play = 0
            if(v.hasVibrator()){
             v.cancel()
                v.cancel()
            }
           // recreate()
        }
    }


    private fun populategroceryList() {
        val pers = Room_constr(R.drawable.personal, "Personal")
        val potato = Room_constr(R.drawable.meditation, "Meditation")
        val rain = Room_constr(R.drawable.harmony,"Harmony")
        val harmony = Room_constr(R.drawable.night_city,"Night city")
        val medit = Room_constr(R.drawable.ocean,"Ocean")
        val fire = Room_constr(R.drawable.rain,"Rain")
        val evol = Room_constr(R.drawable.romantic,"Romantic")
        val night = Room_constr(R.drawable.white_noise, "Starfall")
        val white = Room_constr(R.drawable.evolution,"Immersion")
        val rom = Room_constr(R.drawable.fire,"Bonfire")
        val acva = Room_constr(R.drawable.aquarium,"Aquarium")

        groceryList.add(pers)
        groceryList.add(potato)
        groceryList.add(rain)
        groceryList.add(harmony)
        groceryList.add(medit)
        groceryList.add(fire)
        groceryList.add(evol)
        groceryList.add(night)
        groceryList.add(white)
        groceryList.add(rom)
        groceryList.add(acva)
        groceryAdapter!!.notifyDataSetChanged()
    }

    private fun vibroM(){
        when(top_choise){
            1-> {
            val pattern = longArrayOf(0, 2000, 1000,2000,1000) //meditation
            v.vibrate(pattern,1)
        }
            2->{
                val pattern = longArrayOf(950,1250,1600,2700,1800,1250,1900,2400,1800,1250,4500, 500, 1200, 500, 5000,500,5000,500,4000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000,400,1000,700,1000,1000,1000)//1000,1400,1000,300,1000,100)  ////harmony
                v.vibrate(pattern,1)
            }
            3->{
                val pattern = longArrayOf(700, 375, 700, 375, 700, 375, 700) // night city
                v.vibrate(pattern,1)
            }
            4->{
                val pattern = longArrayOf(2000, 60, 85, 60, 500, 80, 85, 80,750)  ////ocean
                v.vibrate(pattern,1)
            }
            5-> {
                val pattern = longArrayOf(500, 50, 200, 50, 200, 100,300, 100, 400, 300, 300, 500, 900, 800,250)  ////rain
                v.vibrate(pattern,1)
            }
            6->{
                val pattern = longArrayOf(2000, 900, 850, 1400, 850, 700, 850, 900, 850, 1400, 850, 700, 850)  ////romantic
                v.vibrate(pattern,1)
            }
            7->{
                val pattern = longArrayOf(0, 500, 0,800,0, 1200,0,500,1000, 700, 0, 1600,0, 700,1000)  ////starfall
                v.vibrate(pattern,1)
            }
            8->{
                val pattern = longArrayOf(1000, 2200,1000,1900,1000,1700,1000,1500,1000,1300,1000,1200,1000,1000,1000)  ////immersion
                v.vibrate(pattern,1)
            }
            9-> {
                val pattern = longArrayOf(3250, 200, 30, 100, 30, 100, 100) //fire
                v.vibrate(pattern,1)
            }
            10->{
                val pattern = longArrayOf(1500, 500, 1500) //aquarium
                v.vibrate(pattern,1)
            }
        }
    }


    fun vibroOffOn() {
            if (vibrarion_status == 0) {
                vibrarion_status = 1
                v.vibrate(0)
                imageVibro.setImageResource(R.drawable.vibro_off)
                messegeToast("Vibro Off")
                v.cancel()
            } else {
                Log.d("VIBlllll","Vibro")
                messegeToast("Vibro On")
                if(player.isPlaying){
                    Log.d("VIBlllll","ispay")
                    player.stop()
                    playMusic.setImageResource(R.drawable.play)
                    player = MediaPlayer.create(this,song)
                }else{
                    Log.d("VIBlllll","no play")
                    player = MediaPlayer.create(this,song)
                }
                imageVibro.setImageResource(R.drawable.vibro)
                vibrarion_status = 0
                v.vibrate(400)
            }
    }
    fun messegeToast(messege: String) {
        Toast.makeText(applicationContext, messege, Toast.LENGTH_SHORT).show()
    }


    fun  checkPremission(){
        var pm :PackageManager = packageManager
        var prem :Int = packageManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,packageName)
        if(prem!=PackageManager.PERMISSION_GRANTED){
            val fragmentManager = supportFragmentManager
            val ff = Headset()
            ff.show(fragmentManager, "dialog")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
    }


    override fun top(i: Int): Int {
        top_choise = i
        v.cancel()
        Log.d("Choise",""+top_choise)
        if(player.isPlaying){
            player.stop()
            playMusic.setImageResource(R.drawable.play)
            if(play!=0){
                play=0
            }
        }
        onResume()
        return 0
    }
}



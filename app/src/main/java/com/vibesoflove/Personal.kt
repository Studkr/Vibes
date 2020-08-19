package com.vibesoflove

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.*
import com.vibesoflove.Adapter.RecyclerAdapter
import com.vibesoflove.Adapter.RecyclerAdapterP
import com.vibesoflove.Construct.Room_constr
import com.vibesoflove.Interface.topInterface
import java.io.InputStream
import java.util.ArrayList
import android.R.attr.data
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaDataSource
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import com.bumptech.glide.Glide
import com.sdsmdg.harjot.crollerTest.Croller
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.concurrent.TimeUnit


class Personal : AppCompatActivity(),topInterface {
    private val groceryList = ArrayList<Room_constr>()
    private var groceryRecyclerView: RecyclerView? = null
    private var groceryAdapter: RecyclerAdapterP? = null
    lateinit var video :VideoView
    lateinit var button_choise: ImageView
    lateinit var image:ImageView
    lateinit var song_name :TextView
    lateinit var pley_music:ImageView
    lateinit var choose_music:ImageView
    lateinit var recycler:RelativeLayout
    var position :Int =0
    var prep:Int =0
    lateinit var player: MediaPlayer
    lateinit var croller:Croller
    var vibr_power:Int =0
    lateinit var imageSound:ImageView
    lateinit var imageVibro:ImageView
    lateinit var imageRate:ImageView
    lateinit var imageShare:ImageView
    lateinit var imageFull:ImageView
    lateinit var imageCart:ImageView
    lateinit var manager: AudioManager
    lateinit var container : ConstraintLayout

    internal var i = 0
    internal var vibrarion_status = 0
    internal lateinit var v: Vibrator
    var power:Int = 0
    var state = 0
    var vibro_choise = 0
    lateinit var vibro:Vibrator


    val APP_PREFERENCES = "powervibro"
    val APP_PREFERENCES_COUNTER = "counter"
    val APP_PERF = "room"
    lateinit var pref: SharedPreferences
    val APP_PERF_IMAGE= " image"
    val APP_PERF_AUDIO = "audio"
    val APP_PERF_VIDEO ="video"
    val APP_ADOFF="adoff"
    var adoff:Boolean= false
lateinit var check_audio: Uri
    lateinit var check_image :Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE) //
        //Получаем значения из файла настроек
        var check_audio_s = pref.getString(APP_PERF_AUDIO,null)
        var check_image_s = pref.getString(APP_PERF_IMAGE,null)

        groceryRecyclerView = findViewById(R.id.idRecyclerViewHorizontalList)
        groceryAdapter = RecyclerAdapterP(groceryList, applicationContext, this)
        val horizontalLayoutManager = LinearLayoutManager(this@Personal, LinearLayoutManager.HORIZONTAL, false)
        groceryRecyclerView!!.layoutManager = horizontalLayoutManager
        groceryRecyclerView!!.adapter = groceryAdapter
        button_choise = findViewById<ImageView>(R.id.button_choose_file)
        image = findViewById<ImageView>(R.id.image_personal)
        song_name = findViewById<TextView>(R.id.song_name)
        pley_music = findViewById<ImageView>(R.id.play_music)
        choose_music = findViewById<ImageView>(R.id.button_choise_music)
        imageVibro = findViewById<View>(R.id.imageSound)as ImageView
        imageShare = findViewById<View>(R.id.imageShare)as ImageView
        imageRate=findViewById<View>(R.id.imageRate)as ImageView
        imageFull = findViewById<View>(R.id.imageFuul) as ImageView
        imageCart = findViewById<View>(R.id.imageCart) as ImageView
        v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        manager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

      //  recycler = findViewById<RelativeLayout>(R.id.relatyve_personal)
        container = findViewById<ConstraintLayout>(R.id.button_layout)
        button_choise.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

        }
        choose_music.setOnClickListener {
            val intent = Intent (Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 10)
            prep=0
        }
        player =  MediaPlayer()
        pley_music.setOnClickListener {
            if(prep==0){
             Toast.makeText(applicationContext,"Choose Song",Toast.LENGTH_SHORT).show()
            }else{
                if(player.isPlaying){
                    player.pause()
                    pley_music.setImageResource(R.drawable.play)
                }else {
                    player.start()
                    pley_music.setImageResource(R.drawable.pause)
                }
            }
        }

        croller = findViewById<View>(R.id.croller) as Croller
        val croller = findViewById<View>(R.id.croller) as Croller
        croller.label = "Power"
       // croller.indicatorWidth = 2f
        croller.backCircleColor = Color.parseColor("#EEEEEE")
        croller.mainCircleColor = resources.getColor(R.color.colorMain)
        croller.max = 50
        croller.startOffset = 40
        croller.setIsContinuous(false)
        croller.labelColor = Color.WHITE
        croller.progress=0
        croller.progressPrimaryColor = Color.parseColor("#EC407A")
        croller.indicatorColor = Color.parseColor("#FFFFFF")
        croller.progressSecondaryColor = Color.parseColor("#FFFFFF")
        vibro = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        croller.setOnCrollerChangeListener(object : OnCrollerChangeListener {
            override fun onProgressChanged(croller: Croller, progress: Int) {
              /*  if(progress==0){
                    croller.label = "Power"
                }else{
                    startVibro(progress*2)
                    var label = progress*2
                    croller.label = label.toString()
                 vibro.vibrate(progress.toLong())
                }*/
                Log.d("DEBUGS",""+progress)
                if(progress ==0){
                }else{
                    vibro.cancel()
                    startVibroTest(progress)
                }
            }
            override fun onStartTrackingTouch(croller: Croller) {
                // tracking started
            }
            override fun onStopTrackingTouch(croller: Croller) {
           //     vibro.cancel()
            }
        })
        populategroceryList()
        onClick()
        if(check_audio_s!=null){
            check_audio = Uri.parse(check_audio_s)
            setAudio(check_audio)
        }else{
            check_audio= Uri.parse("android.resource://" + packageName + "/" + R.raw.night)
        }
        if(check_image_s!=null){
            check_image = Uri.parse(check_image_s)
            setImage(check_image)
        }else{
            check_image =Uri.parse("android.resource://" + packageName + "/" + R.drawable.sem)
            setImage(check_image)
        }
    }


    fun startVibro(p:Int){
        vibro_choise = p
        vibro.cancel()
        when (p){
            1->{
                val mass = longArrayOf(0,1000,20)
                vibro.vibrate(mass,1)
            }

            2->{
                val mass = longArrayOf(0,980,20)
                vibro.vibrate(mass,1)
            }
            3->{
                val mass = longArrayOf(0,960,20)
                vibro.vibrate(mass,1)
            }
            4->{
                val mass = longArrayOf(0,940,20)
                vibro.vibrate(mass,1)
            }
            5->{
                val mass = longArrayOf(0,920,20)
                vibro.vibrate(mass,1)
            }
            6->{
                val mass = longArrayOf(0,900,20)
                vibro.vibrate(mass,1)
            }
            7->{
                val mass = longArrayOf(0,880,20)
                vibro.vibrate(mass,1)
            }
            8->{
                val mass = longArrayOf(0,860,20)
                vibro.vibrate(mass,1)
            }
            9->{
                val mass = longArrayOf(0,840,20)
                vibro.vibrate(mass,1)
            }
            10->{
                val mass = longArrayOf(0,820,20)
                vibro.vibrate(mass,1)
            }
            11->{
                val mass = longArrayOf(0,800,20)
                vibro.vibrate(mass,1)
            }
            12->{
                val mass = longArrayOf(0,780,20)
                vibro.vibrate(mass,1)
            }
            13->{
                val mass = longArrayOf(0,760,20)
                vibro.vibrate(mass,1)
            }
            14->{
                val mass = longArrayOf(0,740,20)
                vibro.vibrate(mass,1)
            }
            15->{
                val mass = longArrayOf(0,720,20)
                vibro.vibrate(mass,1)
            }
            16->{
                val mass = longArrayOf(0,700,20)
                vibro.vibrate(mass,1)
            }
            17->{
                val mass = longArrayOf(0,680,20)
                vibro.vibrate(mass,1)
            }
            18->{
                val mass = longArrayOf(0,660,20)
                vibro.vibrate(mass,1)
            }
            19->{
                val mass = longArrayOf(0,640,20)
                vibro.vibrate(mass,1)
            }
            20->{
                val mass = longArrayOf(0,620,20)
                vibro.vibrate(mass,1)
            }
            21->{
                val mass = longArrayOf(0,600,20)
                vibro.vibrate(mass,1)
            }
            22->{
                val mass = longArrayOf(0,580,20)
                vibro.vibrate(mass,1)
            }
            23->{
                val mass = longArrayOf(0,560,20)
                vibro.vibrate(mass,1)

            }
            24->{
                val mass = longArrayOf(0,540,20)
                vibro.vibrate(mass,1)

            }
            25->{
                val mass = longArrayOf(0,520,20)
                vibro.vibrate(mass,1)

            }
            26->{
                val mass = longArrayOf(0,500,20)
                vibro.vibrate(mass,1)

            }
            27->{
                val mass = longArrayOf(0,480,20)
                vibro.vibrate(mass,1)

            }
            28->{
                val mass = longArrayOf(0,460,20)
                vibro.vibrate(mass,1)

            }
            29->{
                val mass = longArrayOf(0,440,20)
                vibro.vibrate(mass,1)

            }
            30->{
                val mass = longArrayOf(0,420,20)
                vibro.vibrate(mass,1)

            }
            31->{
                val mass = longArrayOf(0,400,20)
                vibro.vibrate(mass,1)

            }
            32->{
                val mass = longArrayOf(0,380,20)
                vibro.vibrate(mass,1)

            }
            33->{
                val mass = longArrayOf(0,360,20)
                vibro.vibrate(mass,1)

            }
            34->{
                val mass = longArrayOf(0,340,20)
                vibro.vibrate(mass,1)
            }
            35->{
                val mass = longArrayOf(0,320,20)
                vibro.vibrate(mass,1)
            }
            36->{
                val mass = longArrayOf(0,300,20)
                vibro.vibrate(mass,1)
            }
            37->{
                val mass = longArrayOf(0,280,20)
                vibro.vibrate(mass,1)
            }
            38->{
                val mass = longArrayOf(0,260,20)
                vibro.vibrate(mass,1)
            }
            39->{
                val mass = longArrayOf(0,240,20)
                vibro.vibrate(mass,1)
            }
            40->{
                val mass = longArrayOf(0,220,20)
                vibro.vibrate(mass,1)
            }
            41->{
                val mass = longArrayOf(0,200,20)
                vibro.vibrate(mass,1)
            }
            42->{
                val mass = longArrayOf(0,180,20)
                vibro.vibrate(mass,1)
            }
            43->{
                val mass = longArrayOf(0,160,20)
                vibro.vibrate(mass,1)
            }
            44->{
                val mass = longArrayOf(0,140,20)
                vibro.vibrate(mass,1)
            }
            45->{
                val mass = longArrayOf(0,120,20)
                vibro.vibrate(mass,1)
            }
            46->{
                val mass = longArrayOf(0,100,20)
                vibro.vibrate(mass,1)
            }
            47->{
                val mass = longArrayOf(0,80,20)
                vibro.vibrate(mass,1)
            }
            48-> {
                val mass = longArrayOf(0, 60, 20)
                vibro.vibrate(mass, 1)
            }
                49-> {
                    val mass = longArrayOf(0, 40, 20)
                    vibro.vibrate(mass, 1)
                }
                    50->{
                    val mass = longArrayOf(0,20,20)
                    vibro.vibrate(mass,1)
                }
                }
            }
    fun startVibroTest(p:Int){
        vibro_choise=p
        vibro.cancel()
        when (p){
            1->{
                val mass = longArrayOf(0,2000,20)
                vibro.vibrate(mass,1)
            }

            2->{
                val mass = longArrayOf(0,1800,20)
                vibro.vibrate(mass,1)
            }
            3->{
                val mass = longArrayOf(0,1800,20)
                vibro.vibrate(mass,1)
            }
            4->{
                val mass = longArrayOf(0,1750,20)
                vibro.vibrate(mass,1)
            }
            5->{
                val mass = longArrayOf(0,1750,20)
                vibro.vibrate(mass,1)
            }
            6->{
                val mass = longArrayOf(0,1500,20)
                vibro.vibrate(mass,1)
            }
            7->{
                val mass = longArrayOf(0,1500,20)
                vibro.vibrate(mass,1)
            }
            8->{
                val mass = longArrayOf(0,1450,20)
                vibro.vibrate(mass,1)
            }
            9->{
                val mass = longArrayOf(0,1450,20)
                vibro.vibrate(mass,1)
            }
            10->{
                val mass = longArrayOf(0,1400,20)
                vibro.vibrate(mass,1)
            }
            11->{
                val mass = longArrayOf(0,1400,20)
                vibro.vibrate(mass,1)
            }
            12->{
                val mass = longArrayOf(0,1300,20)
                vibro.vibrate(mass,1)
            }
            13->{
                val mass = longArrayOf(0,1100,20)
                vibro.vibrate(mass,1)
            }
            14->{
                val mass = longArrayOf(0,1100,20)
                vibro.vibrate(mass,1)
            }
            15->{
                val mass = longArrayOf(0,1000,20)
                vibro.vibrate(mass,1)
            }
            16->{
                val mass = longArrayOf(0,1000,20)
                vibro.vibrate(mass,1)
            }
            17->{
                val mass = longArrayOf(0,900,20)
                vibro.vibrate(mass,1)
            }
            18->{
                val mass = longArrayOf(0,900,20)
                vibro.vibrate(mass,1)
            }
            19->{
                val mass = longArrayOf(0,800,20)
                vibro.vibrate(mass,1)
            }
            20->{
                val mass = longArrayOf(0,750,20)
                vibro.vibrate(mass,1)
            }
            21->{
                val mass = longArrayOf(0,650,20)
                vibro.vibrate(mass,1)
            }
            22->{
                val mass = longArrayOf(0,580,20)
                vibro.vibrate(mass,1)
            }
            23->{
                val mass = longArrayOf(0,560,20)
                vibro.vibrate(mass,1)

            }
            24->{
                val mass = longArrayOf(0,540,20)
                vibro.vibrate(mass,1)

            }
            25->{
                val mass = longArrayOf(0,520,20)
                vibro.vibrate(mass,1)

            }
            26->{
                val mass = longArrayOf(0,500,20)
                vibro.vibrate(mass,1)

            }
            27->{
                val mass = longArrayOf(0,480,20)
                vibro.vibrate(mass,1)

            }
            28->{
                val mass = longArrayOf(0,460,20)
                vibro.vibrate(mass,1)

            }
            29->{
                val mass = longArrayOf(0,440,20)
                vibro.vibrate(mass,1)

            }
            30->{
                val mass = longArrayOf(0,420,20)
                vibro.vibrate(mass,1)

            }
            31->{
                val mass = longArrayOf(0,400,20)
                vibro.vibrate(mass,1)

            }
            32->{
                val mass = longArrayOf(0,380,20)
                vibro.vibrate(mass,1)

            }
            33->{
                val mass = longArrayOf(0,360,20)
                vibro.vibrate(mass,1)

            }
            34->{
                val mass = longArrayOf(0,340,20)
                vibro.vibrate(mass,1)
            }
            35->{
                val mass = longArrayOf(0,320,20)
                vibro.vibrate(mass,1)
            }
            36->{
                val mass = longArrayOf(0,300,20)
                vibro.vibrate(mass,1)
            }
            37->{
                val mass = longArrayOf(0,280,20)
                vibro.vibrate(mass,1)
            }
            38->{
                val mass = longArrayOf(0,260,20)
                vibro.vibrate(mass,1)
            }
            39->{
                val mass = longArrayOf(0,240,20)
                vibro.vibrate(mass,1)
            }
            40->{
                val mass = longArrayOf(0,220,20)
                vibro.vibrate(mass,1)
            }
            41->{
                val mass = longArrayOf(0,200,20)
                vibro.vibrate(mass,1)
            }
            42->{
                val mass = longArrayOf(0,180,20)
                vibro.vibrate(mass,1)
            }
            43->{
                val mass = longArrayOf(0,160,20)
                vibro.vibrate(mass,1)
            }
            44->{
                val mass = longArrayOf(0,140,20)
                vibro.vibrate(mass,1)
            }
            45->{
                val mass = longArrayOf(0,120,20)
                vibro.vibrate(mass,1)
            }
            46->{
                val mass = longArrayOf(0,100,20)
                vibro.vibrate(mass,1)
            }
            47->{
                val mass = longArrayOf(0,80,20)
                vibro.vibrate(mass,1)
            }
            48-> {
                val mass = longArrayOf(0, 80, 20)
                vibro.vibrate(mass, 1)
            }
            49-> {
                val mass = longArrayOf(0, 80, 20)
                vibro.vibrate(mass, 1)
            }
            50->{
                val mass = longArrayOf(0,80,20)
                vibro.vibrate(mass,1)
            }
        }
    }

    fun saveShared(){
        val editor = pref.edit()
        Log.d("CHECK",""+check_image.toString())
        editor.putString(APP_PERF_IMAGE,check_image.toString())
        editor.putString(APP_PERF_AUDIO,check_audio.toString())
        editor.apply()
    }
    fun longVibro(){
        var mil = TimeUnit.MINUTES.toMillis(power.toLong())
        val mass = longArrayOf(100,mil,500,mil,1500)
        vibro.vibrate(mass,1)
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
            croller.visibility=View.GONE
        }else{
            imageShare.setVisibility(View.VISIBLE);
            imageCart.visibility=View.VISIBLE
            imageRate.visibility = View.VISIBLE
            imageVibro.visibility = View.VISIBLE
            groceryRecyclerView!!.visibility = View.VISIBLE
            container.visibility = View.VISIBLE
            croller.visibility = View.VISIBLE
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

    fun buy(){messegeToast("open cart")}

    fun messegeToast(messege: String) {
        Toast.makeText(applicationContext, messege, Toast.LENGTH_SHORT).show()
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

    fun vibroOffOn() {
        if (vibrarion_status == 0) {
            vibrarion_status = 1
            v.vibrate(0)
            imageVibro.setImageResource(R.drawable.vibro_off)
            messegeToast("Vibro Off")
            v.cancel()
        } else {
            messegeToast("Vibro On")
            imageVibro.setImageResource(R.drawable.vibro)
            vibrarion_status = 0
            v.vibrate(400)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==111){
            var url:Uri = data!!.data
            Log.d("Test",""+url)
            setImage(url)
            check_image = url

        }
        if(requestCode==10){
            val uriSound = data!!.data
            playSound(this,uriSound)
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(this,data.data)
            val title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            Log.d("TITLE","title=$title")
            song_name.setText("$title")
            check_audio = uriSound
        }
        saveShared()
    }

    fun setImage(uri:Uri){
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .centerInside()
                .into(image)
    }
    fun setAudio(uri:Uri){
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(this,uri)
        val title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        Log.d("TITLE","title=$title")
        song_name.setText("$title")
        playSound(this,uri)
    }


    fun playSound(context:Context,uri:Uri){
        player.reset()
        player.setDataSource(context,uri)
        player.setOnPreparedListener { mp->mp.isLooping=true }
        //var meta:MediaDataSource = MediaDataSource()
        player.prepare()
        prep=1
        startVibro(vibro_choise)
        longVibro()

    }

    override fun onResume() {
        if(position>0){
            var intent = Intent(this,MainActivity::class.java)
            intent.putExtra("Choise",position)
            startActivityForResult(intent,1)
        }
        super.onResume()
    }

    override fun onStart() {
        overridePendingTransition(R.anim.first,R.anim.second)
        super.onStart()
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
    override fun top(i: Int): Int {
        position= i
        onResume()
        return 0
    }
}

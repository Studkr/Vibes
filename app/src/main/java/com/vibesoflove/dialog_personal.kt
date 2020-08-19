package com.vibesoflove


import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.app.DialogFragment
import androidx.core.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.billingclient.api.*
import com.android.billingclient.util.BillingHelper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.vibesoflove.Adapter.PurchaseAdapter
import kotlinx.android.synthetic.main.activity_buy_activity.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class dialog_personal : DialogFragment(), RewardedVideoAdListener,PurchasesUpdatedListener {
    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        println("---------------")
        println("---------------")
        println("onPurchasesUpdated: ${purchases.toString()}")
        println("Details: $responseCode")
        println("---------------")
        println("---------------")
       // allowMultiplePurchases(purchases)
        clearHistory()
        adoff=true
        saveShared()
        if(responseCode==0){
            var intent:Intent = Intent(activity,Personal::class.java)
            startActivity(intent)
        }

    }

    override fun onRewarded(p0: com.google.android.gms.ads.reward.RewardItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    lateinit var button_buy: Button
    lateinit var button_watch:Button
    lateinit var button_close : ImageView
    private lateinit var billingClient: BillingClient
    private val skuList = listOf("vibro")

    val APP_PREFERENCES = "powervibro"
    val APP_ADOFF="adoff"
    lateinit var pref: SharedPreferences
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    var adoff:Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog_personal, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme)
        MobileAds.initialize(activity, "ca-app-pub-3940256099942544~3347511713")

        pref = activity!!.getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE)
        adoff = pref.getBoolean(APP_ADOFF,false)

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this
        loadRewardedVideoAd()
        button_buy = view.findViewById(R.id.button_buy) as Button
        button_watch = view.findViewById(R.id.button_video) as Button
        button_close = view.findViewById(R.id.image_close) as ImageView


        button_watch.setOnClickListener {
          /*  if (mRewardedVideoAd.isLoaded) {
                mRewardedVideoAd.show()
            }*/
            var intent:Intent = Intent(activity,Personal::class.java)
            startActivity(intent)
        }

        button_close.setOnClickListener {
            dialog.dismiss()
            activity!!.recreate()
        }
         button_buy.setOnClickListener {
             //var intent:Intent = Intent(activity,buy_activity::class.java)
             //startActivity(intent)
             beginBuy()
         }

        setupBillingClient()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun setupBillingClient() {
        billingClient = BillingClient
                .newBuilder(activity!!.applicationContext)
                .setListener(this)
                .build()
        println("Trying to start a connection...")
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    println("BILLING | startConnection | RESULT OK")
                } else {
                    println("BILLING | startConnection | RESULT: $billingResponseCode")
                }
            }
            override fun onBillingServiceDisconnected() {
                println("BILLING | onBillingServiceDisconnected | DISCONNECTED")
            }
        })
    }

fun beginBuy(){
    if(billingClient.isReady){
        val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(skuList)
                .setType(BillingClient.SkuType.INAPP)
                .build()
        billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
            if (responseCode == BillingClient.BillingResponse.OK) {
                println("querySkuDetailsAsync, responseCode: $responseCode")
                initProductAdapter(skuDetailsList)
               // launchBuy(,)

            } else {
                println("Can't querySkuDetailsAsync, responseCode: $responseCode")
            }
        }
    } else {
        println("Billing Client not ready...")
    }
}

    private fun initProductAdapter(skuDetailsList: List<SkuDetails>) {
        println("---------")
        println(skuDetailsList.toString())
        Log.d("SKU",""+skuDetailsList.size)
        println("---------")
        val billing = BillingFlowParams
                .newBuilder()
                .setSku("vibro")
                .setType(BillingClient.SkuType.INAPP)
                .build()
        val responseCode = billingClient.launchBillingFlow(activity,billing)
       /* val purchaseAdapter = PurchaseAdapter(skuDetailsList,activity!!.applicationContext) {
            val billingFlowParams = BillingFlowParams
                    .newBuilder()
                    .setSkuDetails(it)
                    .build()
            billingClient.launchBillingFlow(activity, billingFlowParams)*/
//23.16

       // Toast.makeText(this, skuDetailsList.toString(), Toast.LENGTH_LONG).show()

       // products.adapter = purchaseAdapter
    }

    //if billing is successful, responseCode returns 0, else not successful, details: https://developer.android.com/google/play/billing/billing_reference
    private fun clearHistory() {
        billingClient.queryPurchases(BillingClient.SkuType.INAPP).purchasesList
                .forEach {
                    billingClient.consumeAsync(it.purchaseToken) { responseCode, purchaseToken ->
                        if (responseCode == BillingClient.BillingResponse.OK && purchaseToken != null) {
                            println("onPurchases Updated consumeAsync, purchases token removed: $purchaseToken")
                        } else {
                            println("onPurchases some troubles happened: $responseCode")
                        }
                    }
                }
    }



    fun saveShared(){
        val editor = pref.edit()
        editor.putBoolean(APP_ADOFF, adoff)
        editor.apply()
    }


    private  fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                AdRequest.Builder().build())
    }

    override fun onRewardedVideoAdLeftApplication() {

    }

    override fun onRewardedVideoAdClosed() {

    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {

    }

    override fun onRewardedVideoAdLoaded() {
        Toast.makeText(activity, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdOpened() {
        Toast.makeText(activity, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
        Toast.makeText(activity, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoCompleted() {
        var intent:Intent = Intent(activity,Personal::class.java)
        startActivity(intent)
    }
}

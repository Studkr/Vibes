package com.vibesoflove


import android.content.Context
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
import android.widget.TextView
import com.android.billingclient.api.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BlankFragment : DialogFragment() , PurchasesUpdatedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var billingClient: BillingClient
    lateinit var button_buy: Button
    lateinit var sku_text: TextView
    private val skuList = listOf("vibro")
    lateinit var button_close : ImageView
    val APP_PREFERENCES = "powervibro"
    val APP_ADOFF="adoff"
    var adoff:Boolean = false
    lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        println("---------------")
        println("---------------")
        println("onPurchasesUpdated: ${purchases.toString()}")
        println("Details: $responseCode")
        println("---------------")
        println("---------------")
        clearHistory()
        adoff=true
        saveShared()
        if(responseCode==0){
            dialog.dismiss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme)
        button_buy = view.findViewById(R.id.button_b) as Button
        sku_text = view.findViewById(R.id.sku_text) as TextView
        pref = activity!!.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        adoff = pref.getBoolean(APP_ADOFF,false)
        setupBillingClient()
        button_buy.setOnClickListener { beginShop() }
        button_close = view.findViewById(R.id.image_close) as ImageView
        button_close.setOnClickListener {
            dialog.dismiss()
            activity!!.recreate()
        }
    }

    fun saveShared(){
        val editor = pref.edit()
        editor.putBoolean(APP_ADOFF, adoff)
        editor.apply()
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
                    beginBuy()
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
                    // launchBuy()
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
        sku_text.setText(""+skuDetailsList.get(0).price)
    }

    fun beginShop(){
        if(billingClient.isReady){
            val params = SkuDetailsParams
                    .newBuilder()
                    .setSkusList(skuList)
                    .setType(BillingClient.SkuType.INAPP)
                    .build()
            billingClient.querySkuDetailsAsync(params) { responseCode, skuDetailsList ->
                if (responseCode == BillingClient.BillingResponse.OK) {
                    println("querySkuDetailsAsync, responseCode: $responseCode")
                    initProductAdapterd(skuDetailsList)
                    // launchBuy(,)

                } else {
                    println("Can't querySkuDetailsAsync, responseCode: $responseCode")
                }
            }
        } else {
            println("Billing Client not ready...")
        }
    }
    private fun initProductAdapterd(skuDetailsList: List<SkuDetails>) {
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BlankFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}

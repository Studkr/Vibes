package com.vibesoflove


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
import android.widget.TextView
import com.android.billingclient.api.*

/**
 * A simple [Fragment] subclass.
 *
 */
class buy : DialogFragment(), PurchasesUpdatedListener {

    private lateinit var billingClient: BillingClient
    lateinit var button_buy: Button
    lateinit var sku_text:TextView
    private val skuList = listOf("vibro")


    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(DialogFragment.STYLE_NO_FRAME,android.R.style.Theme)
        button_buy = view.findViewById(R.id.button_b) as Button
        sku_text = view.findViewById(R.id.sku_text) as TextView
        setupBillingClient()

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
        sku_text.setText(""+skuDetailsList.get(0).price)
        // Toast.makeText(this, skuDetailsList.toString(), Toast.LENGTH_LONG).show()
        // products.adapter = purchaseAdapter
    }


}

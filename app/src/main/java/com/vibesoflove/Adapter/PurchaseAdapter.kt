package com.vibesoflove.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.billingclient.api.SkuDetails
import com.vibesoflove.R

class PurchaseAdapter(
private val list: List<SkuDetails>, val context: Context,
private val onProductClicked: (SkuDetails) -> Unit
) : RecyclerView.Adapter<PurchaseAdapter.ViewHolder>() {


    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(context).inflate(R.layout.purchase_item, parent, false) as TextView
        val viewHolder = ViewHolder(textView)
        textView.setOnClickListener { onProductClicked(list[viewHolder.adapterPosition]) }
        return viewHolder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position].sku + " - " + list[position].price
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}
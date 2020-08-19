package com.vibesoflove.ui.home.adapter

import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder



import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

import com.vibesoflove.R
import com.vibesoflove.model.ContentModel


@EpoxyModelClass(
        layout = R.layout.item_holder_corousel
)
abstract class PersonModel : EpoxyModelWithHolder<PersonModel.Holder>() {

    @EpoxyAttribute
    lateinit var model:ContentModel

    @EpoxyAttribute(DoNotHash)
    var starListener: View.OnClickListener? = null


    override fun bind(holder: Holder) {
        with(holder) {
            image.setImageResource(model.placeholder)
            text.text = model.roomName
            image.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
            image.setOnClickListener(null)
        }
    }


    class Holder : BaseEpoxyHolder() {
        val image : ImageView by bind(R.id.imageRoom)
        val text : TextView by bind(R.id.textDescription)
    }
}

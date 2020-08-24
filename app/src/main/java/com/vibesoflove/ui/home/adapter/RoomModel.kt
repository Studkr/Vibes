package com.vibesoflove.ui.home.adapter

import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder



import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder

import com.vibesoflove.R
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.ContentModel


@EpoxyModelClass(
        layout = R.layout.item_holder_corousel
)
abstract class PersonModel : EpoxyModelWithHolder<PersonModel.Holder>() {

    @EpoxyAttribute
    lateinit var model:CategoryModel

    @EpoxyAttribute(DoNotHash)
    var starListener: View.OnClickListener? = null


    override fun bind(holder: Holder) {
        with(holder) {
           // image.setImageResource(model.placeholder)
            text.text = model.name
            card.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
            card.setOnClickListener(null)
        }
    }


    class Holder : BaseEpoxyHolder() {
        //val image : ImageView by bind(R.id.imageRoom)
        val text : TextView by bind(R.id.textDescription)
        val card :CardView by bind(R.id.cardList)
    }
}

package com.vibesoflove.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder
import com.vibesoflove.R


@EpoxyModelClass(
        layout = R.layout.item_holder_main_category
)
abstract class HeaderEpoxyModel : EpoxyModelWithHolder<HeaderEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder) {
            text.text = name
            openCategory.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder){
            openCategory.setOnClickListener(null)
        }
    }

    class Holder : BaseEpoxyHolder() {
        val text : TextView by bind(R.id.categoryName)
        val openCategory : ImageView by  bind(R.id.openCategory)
    }
}

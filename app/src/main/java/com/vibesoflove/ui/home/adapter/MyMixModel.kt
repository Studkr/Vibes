package com.vibesoflove.ui.home.adapter

import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder
import com.flipsidegroup.nmt.system.loadImageCrop
import com.vibesoflove.R
import com.vibesoflove.db.DataBaseEntity
import com.vibesoflove.model.VideoPopular

@EpoxyModelClass(
        layout = R.layout.item_holder_my_mix
)
abstract class MyMixModel : EpoxyModelWithHolder<MyMixModel.Holder>() {

    @EpoxyAttribute
    lateinit var model: DataBaseEntity

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder) {
            imageCategory.loadImageCrop(model.placeholder)
            imageCategory.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
            imageCategory.setOnClickListener(null)
        }
    }

    class Holder : BaseEpoxyHolder() {
        val imageCategory : ImageView by bind(R.id.imageVideoCategory)
    }
}

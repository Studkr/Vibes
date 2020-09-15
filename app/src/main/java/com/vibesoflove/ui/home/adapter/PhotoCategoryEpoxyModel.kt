package com.vibesoflove.ui.home.adapter

import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder
import com.flipsidegroup.nmt.system.loadImageCrop
import com.vibesoflove.R
import com.vibesoflove.model.Photo
import com.vibesoflove.model.VideoPopular

@EpoxyModelClass(
        layout = R.layout.item_holder_photo
)
abstract class PhotoCategoryEpoxyModel : EpoxyModelWithHolder<PhotoCategoryEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var model: Photo

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder) {
            imageCategory.loadImageCrop(model.src.large)
            imageCategory.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
            imageCategory.setOnClickListener(null)
        }
    }

    class Holder : BaseEpoxyHolder() {
        val imageCategory : ImageView by bind(R.id.photo)
    }
}

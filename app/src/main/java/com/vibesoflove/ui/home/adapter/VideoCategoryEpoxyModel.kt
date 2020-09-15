package com.vibesoflove.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder
import com.flipsidegroup.nmt.system.loadImageCrop
import com.vibesoflove.R
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.Video
import com.vibesoflove.model.VideoPopular


@EpoxyModelClass(
        layout = R.layout.item_holder_categoty_card_main_screen
)
abstract class VideoCategoryEpoxyModel : EpoxyModelWithHolder<VideoCategoryEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var model: VideoPopular

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder) {
            imageCategory.loadImageCrop(model.image)
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

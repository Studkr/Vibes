package com.vibesoflove.ui.home.category_details.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder
import com.flipsidegroup.nmt.system.loadImageCrop
import com.vibesoflove.R
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.Video


@EpoxyModelClass(
        layout = R.layout.item_holder_video_details
)
abstract class CategoryModule : EpoxyModelWithHolder<CategoryModule.Holder>() {

    @EpoxyAttribute
    lateinit var model: Video

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null


    override fun bind(holder: Holder) {
        with(holder) {
            // image.setImageResource(model.placeholder)
            image.loadImageCrop(model.image)
            play.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
            play.setOnClickListener(null)
        }
    }


    class Holder : BaseEpoxyHolder() {
        val image : ImageView by bind(R.id.videoImage)
        val play :ImageView by bind (R.id.playButton)
    }
}

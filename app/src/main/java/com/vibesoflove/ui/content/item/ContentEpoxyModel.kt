package com.vibesoflove.ui.content.item

import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.flipsidegroup.nmt.screen.app.roh.results.epoxyAdapter.BaseEpoxyHolder
import com.flipsidegroup.nmt.system.loadImageCrop
import com.vibesoflove.R

@EpoxyModelClass(
        layout = R.layout.item_holder_content
)
abstract class ContentEpoxyModel : EpoxyModelWithHolder<ContentEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var model: ContentModel

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null

    override fun bind(holder: Holder) {
        with(holder) {
            imageContent.loadImageCrop(model.placeholder)
            if (model.isSaved) {
                saveButton.setImageResource(R.drawable.ic_saved)
            } else {
                saveButton.setImageResource(R.drawable.ic_save)
            }
            saveButton.setOnClickListener(starListener)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
            saveButton.setOnClickListener(null)
        }
    }

    class Holder : BaseEpoxyHolder() {
        val imageContent: ImageView by bind(R.id.itemImage)
        val saveButton: ImageView by bind(R.id.saveData)
    }
}

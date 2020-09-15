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
import com.vibesoflove.model.AudioFirebaseModel
import com.vibesoflove.model.ContentModel

@EpoxyModelClass(
        layout = R.layout.item_holder_category_audio
)
abstract class AudioEpoxyModel : EpoxyModelWithHolder<AudioEpoxyModel.Holder>() {

    @EpoxyAttribute
    lateinit var name: AudioFirebaseModel

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var starListener: View.OnClickListener? = null
    override fun bind(holder: Holder) {
        with(holder) {
            text.text = name.name
            image.loadImageCrop(name.image)
        }
    }

    override fun unbind(holder: Holder) {
        with(holder) {
        }

    }

    class Holder : BaseEpoxyHolder() {
        val text: TextView by bind(R.id.audioName)
        val image: ImageView by bind(R.id.audioImage)
        val duration: TextView by bind(R.id.audioDuration)
    }
}
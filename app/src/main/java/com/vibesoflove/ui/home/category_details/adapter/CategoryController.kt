package com.vibesoflove.ui.home.category_details.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.Video
import com.vibesoflove.model.VideoModel
import com.vibesoflove.ui.home.adapter.PersonModel_


class CategoryController(
        val onCardClick: (model: Video) -> Unit
) : TypedEpoxyController<VideoModel>() {

    override fun buildModels(data: VideoModel) {
        data.videos.map {
            categoryModule {
                Timber.i{
                 "$it"
                }
                id(it.id)
                model(it)
                starListener { model, parentView, clickedView, position ->
                    onCardClick(model.model)
                }
            }
        }

    }

}
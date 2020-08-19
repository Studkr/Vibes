package com.vibesoflove.ui.home.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.vibesoflove.model.ContentModel

class RoomController(
        val onCardClick: (model: ContentModel) -> Unit
) : TypedEpoxyController<List<ContentModel>>() {

    override fun buildModels(data: List<ContentModel>) {
        carousel {
            id("Room")
            hasFixedSize(true)
            models(data.map {
                PersonModel_()
                        .id(it.id)
                        .model(it)
                        .starListener { model, parentView, clickedView, position ->
                            onCardClick(model.model)
                        }
            })
        }
    }

}
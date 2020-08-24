package com.vibesoflove.ui.home.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.ContentModel

class RoomController(
        val onCardClick: (model: CategoryModel) -> Unit
) : TypedEpoxyController<List<CategoryModel>>() {

    override fun buildModels(data: List<CategoryModel>) {
        carousel {
            id("Room")
            hasFixedSize(true)
            models(data.mapIndexed { index, categoryModel ->
                PersonModel_()
                        .id(index)
                        .model(categoryModel)
                        .starListener { model, parentView, clickedView, position ->
                            onCardClick(model.model)
                        }
            })
        }
    }
}
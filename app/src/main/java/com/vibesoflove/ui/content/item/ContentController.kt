package com.vibesoflove.ui.content.item

import com.airbnb.epoxy.TypedEpoxyController


class ContentController(
        val click: (model: ContentModel) -> Unit,
        val openContent:(model:ContentModel) -> Unit
) : TypedEpoxyController<List<ContentModel>>() {
    override fun buildModels(data: List<ContentModel>) {
        data.mapIndexed { index, contentModel ->
            content {
                id(index)
                model(contentModel)
                starListener { model, parentView, clickedView, position ->
                    click(model.model)
                }
                openContent { model, parentView, clickedView, position ->
                    openContent(model.model)
                }
            }
        }
    }

}
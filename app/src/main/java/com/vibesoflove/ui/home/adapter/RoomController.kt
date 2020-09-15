package com.vibesoflove.ui.home.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.vibesoflove.R
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.model.ContentModel
import com.vibesoflove.model.Photo
import com.vibesoflove.model.VideoPopular
import com.vibesoflove.ui.home.PopularContent

class RoomController(
        val onVideoClicked: (model: VideoPopular) -> Unit,
        val onPhotoClicked: (model: Photo) -> Unit,
        val audioClicked: (model: ContentModel) -> Unit,
        val myMixClicked:(model:String)->Unit
) : TypedEpoxyController<PopularContent>() {

    override fun buildModels(data: PopularContent) {

        header {
            id("MyMix")
            name("My Mix")
            starListener { model, parentView, clickedView, position ->
                myMixClicked(model.name)
            }
        }
        if(data.savedList.isNotEmpty()){
            carousel {
                id("myMix")
                hasFixedSize(true)
                numViewsToShowOnScreen(1f)
                models(data.savedList.mapIndexed {index, model ->
                    MyMixModel_()
                            .id(index)
                            .model(model)
                            .starListener { model, parentView, clickedView, position ->  }
                })
            }
        }else{
            emptyMixController {
                id("EmptyMix")
                model(R.color.add_mix_color)
                starListener { model, parentView, clickedView, position ->

                }
            }
        }

        header {
            id("VideoTitle")
            name("new video")
            starListener { model, parentView, clickedView, position ->
                myMixClicked(model.name)
            }

        }

        carousel {
            id("new video")
            hasFixedSize(true)
            numViewsToShowOnScreen(1.5f)
            models(data.video.mapIndexed { index, videoPopular ->
                VideoCategoryEpoxyModel_()
                        .id(index)
                        .model(videoPopular)
                        .starListener { model, parentView, clickedView, position ->
                            onVideoClicked(model.model)
                        }
            })
        }
        header {
            id("PhotoTitle")
            name("new photo")
            starListener { model, parentView, clickedView, position ->
                myMixClicked(model.name)
            }
        }

        carousel {
            id("Photo")
            hasFixedSize(true)
            numViewsToShowOnScreen(2f)
            models(data.photo.mapIndexed { index, photo ->
                PhotoCategoryEpoxyModel_()
                        .id(index)
                        .model(photo)
                        .starListener { model, parentView, clickedView, position ->
                            onPhotoClicked(model.model)
                        }
            }
            )
        }

        header {
            id("AudioTitle")
            name("new audio")
            starListener { model, parentView, clickedView, position ->
                myMixClicked(model.name)
            }
        }

        data.audio?.mapIndexed { index, contentModel ->
            audio {
                id(index)
                name(contentModel)
            }
        }
    }
}
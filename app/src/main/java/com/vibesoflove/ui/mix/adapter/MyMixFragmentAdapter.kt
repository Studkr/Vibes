package com.vibesoflove.ui.mix.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vibesoflove.ui.mix.item.ItemMixFragment
import com.vibesoflove.ui.mix.item.photo.PhotoMixFragment
import com.vibesoflove.ui.mix.item.video.VideoMixFragment

class MyMixFragmentAdapter(fm:FragmentManager,lifecycle: Lifecycle) :FragmentStateAdapter(fm,lifecycle){
    override fun getItemCount() = Tabs.values().size
    override fun createFragment(position: Int): Fragment =
            when (Tabs.get(position)) {
                Tabs.ItemMixFragment -> ItemMixFragment()
                Tabs.VideoMixFragment -> VideoMixFragment()
                Tabs.AudioMixFrament -> PhotoMixFragment()
            }
}

enum class Tabs (val position :Int,tabName:String){
    ItemMixFragment(0,"My mix"),
    VideoMixFragment(1,"Video"),
    AudioMixFrament(2,"Photo");
    companion object{
        fun get(position: Int) = values().find {
            it.position == position
        } ?:ItemMixFragment
    }
}
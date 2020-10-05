package com.vibesoflove.ui.home.photo

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2


class FadeOutTransformation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.setTranslationX(-position * page.getWidth())
        page.setAlpha(1 - Math.abs(position))
    }
}
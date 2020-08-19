package com.vibesoflove.system

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment, HasAndroidInjector {

    constructor()
    constructor(layoutId: Int) : super(layoutId)


    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        injectSelf()
        super.onAttach(context)
    }

    /**
     * By making this protected it becomes possible to not automatically inject from the
     * parent fragment and let this fragment have it's own component.
     */
    protected open fun injectSelf() = AndroidSupportInjection.inject(this)

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
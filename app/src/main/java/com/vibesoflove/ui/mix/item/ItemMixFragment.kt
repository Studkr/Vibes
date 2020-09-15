package com.vibesoflove.ui.mix.item


import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.github.ajalt.timberkt.Timber
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.saved
import com.vibesoflove.system.BaseFragment
import kotlinx.android.synthetic.main.item_mix_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class ItemMixFragment : BaseFragment(R.layout.item_mix_fragment) {

    companion object {
        fun newInstance() = ItemMixFragment()
    }

    val itemName by argumentDelegate<String>()

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: ItemMixViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // viewModel.setItem(itemName)
       // mixName.text = itemName
        savedDataView.setItemSpacingDp(16)
        savedDataView.layoutManager = GridLayoutManager(requireContext(), 2)

        observe(viewModel.visibleData){
                setData(it)
        }
    }
    private fun setData(list: List<MixModel>) {
        savedDataView.withModels {
            list.mapIndexed { index, contentModel ->
                saved {
                    id(index)
                    viewModel(viewModel)
                    image(contentModel.placeholder)
                }
            }
        }
    }

}
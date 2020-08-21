package com.vibesoflove.ui.home.deatils

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.github.ajalt.timberkt.Timber
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.category
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.system.BaseFragment
import kotlinx.android.synthetic.main.fragment_category_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class FragmentCategory : BaseFragment(R.layout.fragment_category_fragment) {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FragmentCategoryViewModel by viewModels { factory }

    val model: CategoryModel by argumentDelegate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadCategory(model)


        observe(viewModel.openCategory) {
            parentFragment?.findNavController()?.navigate(R.id.toDetailsFragment, bundleOf(
                    "data" to it
            ))
        }

        observe(viewModel.category) {
            setDataToAdapter(it)
        }

    }

    fun setDataToAdapter(categoryList: List<CategoryModel>) {
        categoryView.withModels {
            categoryList.mapIndexed { index, categoryModel ->
                category {
                    id(index)
                    categoryName(categoryModel.name)
                    viewModel(viewModel)
                }
            }
        }
    }


}
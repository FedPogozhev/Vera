package com.fedx.vera.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.fedx.vera.R
import com.fedx.vera.databinding.ProductsFragmentBinding
import kotlinx.android.synthetic.main.products_fragment.*

class ProductsFragment : Fragment() {

    private lateinit var homeViewModel: ProductsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: ProductsFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.products_fragment, container, false)
        homeViewModel =
                ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        binding.llConstructorMaterial.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_navigation_products_to_constructionMaterialFragment)
        }

        binding.llFood.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_navigation_products_to_groupFoodFragment)
        }

        binding.llTableware.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_navigation_products_to_tableWareFragment)
        }
        binding.llBulkMaterial.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_navigation_products_to_bulkMaterialFragment)
        }
        binding.llAuto.setOnClickListener {
            view!!.findNavController()
                .navigate(R.id.action_navigation_products_to_autoProductsFragment)
        }
        return binding.root
    }
}

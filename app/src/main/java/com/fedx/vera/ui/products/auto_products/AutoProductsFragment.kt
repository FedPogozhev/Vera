package com.fedx.vera.ui.products.auto_products

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fedx.vera.MainActivity
import com.fedx.vera.R
import com.fedx.vera.databinding.AutoProductsFragmentBinding
import com.fedx.vera.databinding.BulkMaterialFragmentBinding
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.network.SortGoods
import com.fedx.vera.ui.general.GeneralFragment
import com.fedx.vera.ui.products.bulk_material.BulkMaterialViewModel
import com.fedx.vera.ui.products.tableware.AdapterGoods

class AutoProductsFragment : Fragment() {
    private val viewModel: AutoProductsViewModel by lazy {
        ViewModelProviders.of(this).get(AutoProductsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AutoProductsFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        val toolbar = binding.tbGeneral
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            view!!.findNavController().navigateUp()
        }
        GeoData().getSelectCity(GeneralFragment.cityAdmin)

        binding.photos.adapter = AdapterGoods()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.top_menu_goods, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.top_menu_goods_sort_name_goods ->
                viewModel.getFilterWithSort(GroupsGoods.AUTO_PRODUCTS, SortGoods.NAME)
            R.id.top_menu_goods_sort_price ->
                viewModel.getFilterWithSort(GroupsGoods.AUTO_PRODUCTS, SortGoods.PRICE)
        }
        return true
    }
}

package com.fedx.vera.ui.products.bulk_material

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fedx.vera.MainActivity

import com.fedx.vera.R
import com.fedx.vera.databinding.BulkMaterialFragmentBinding
import com.fedx.vera.databinding.FoamFragmentBinding
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.network.SortGoods
import com.fedx.vera.ui.general.GeneralFragment
import com.fedx.vera.ui.products.group_construction_material.foam.FoamViewModel
import com.fedx.vera.ui.products.tableware.AdapterGoods

class BulkMaterialFragment : Fragment() {
    private val viewModel: BulkMaterialViewModel by lazy {
        ViewModelProviders.of(this).get(BulkMaterialViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = BulkMaterialFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        val toolbar = binding.tbGeneral
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            view!!.findNavController().navigateUp()
        }

        Log.d("city model", "${GeoData().getSelectCity(GeneralFragment.cityAdmin)}")

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
                viewModel.getFilterWithSort(GroupsGoods.BULK_MATERIAL, SortGoods.NAME)
            R.id.top_menu_goods_sort_price ->
                viewModel.getFilterWithSort(GroupsGoods.BULK_MATERIAL, SortGoods.PRICE)
        }
        return true
    }
}

package com.fedx.vera.ui.products.group_construction_material.gloves

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.fedx.vera.R
import com.fedx.vera.databinding.GlovesFragmentBinding
import com.fedx.vera.databinding.ToolsFragmentBinding
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.network.SortGoods
import com.fedx.vera.ui.products.group_construction_material.tools.ToolsViewModel
import com.fedx.vera.ui.products.tableware.AdapterGoods

class GlovesFragment : Fragment() {

    private val viewModel: GlovesViewModel by lazy {
        ViewModelProviders.of(this).get(GlovesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GlovesFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        val toolbar = binding.tbGeneral
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            view!!.findNavController().navigateUp()
        }

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
                viewModel.getFilterWithSort(GroupsGoods.GLOVES, SortGoods.NAME)
            R.id.top_menu_goods_sort_price ->
                viewModel.getFilterWithSort(GroupsGoods.GLOVES, SortGoods.PRICE)
        }
        return true
    }
}

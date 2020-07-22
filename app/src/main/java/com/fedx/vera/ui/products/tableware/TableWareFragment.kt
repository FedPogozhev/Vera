package com.fedx.vera.ui.products.tableware

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.fedx.vera.R
import com.fedx.vera.databinding.TableWareFragmentBinding
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.network.SortGoods

//посуда

class TableWareFragment : Fragment() {

    private val viewModel: TableWareViewModel by lazy {
        ViewModelProviders.of(this).get(TableWareViewModel::class.java)
    }

    lateinit var binding: TableWareFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TableWareFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val toolbar = binding.tbGeneral
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            view!!.findNavController().navigateUp()
        }
        binding.viewModel = viewModel

        binding.searchEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                viewModel.getGeneralsSearch(binding.searchEditText.text.toString())
                val inputMethodManager = (activity as AppCompatActivity)
                    .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })

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
            R.id.top_menu_goods_sort_name_goods -> {
                if (binding.searchEditText.text.equals(""))
                    viewModel.getFilterWithSort(GroupsGoods.TABLEWARE, SortGoods.NAME)
                else
                    viewModel.getGeneralsSearchWithSort(SortGoods.NAME,
                        binding.searchEditText.text.toString())
            }
            R.id.top_menu_goods_sort_price ->{
                if (binding.searchEditText.text.equals(""))
                    viewModel.getFilterWithSort(GroupsGoods.TABLEWARE, SortGoods.PRICE)
                else
                    viewModel.getGeneralsSearchWithSort(SortGoods.PRICE,
                        binding.searchEditText.text.toString())
            }
        }
        return true
    }
}

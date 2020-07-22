package com.fedx.vera.ui.products.group_food

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fedx.vera.R

class GroupFoodFragment : Fragment() {

    companion object {
        fun newInstance() = GroupFoodFragment()
    }

    private lateinit var viewModel: GroupFoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.group_food_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GroupFoodViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

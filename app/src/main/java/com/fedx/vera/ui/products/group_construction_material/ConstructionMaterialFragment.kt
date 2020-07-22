package com.fedx.vera.ui.products.group_construction_material

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import com.fedx.vera.R
import com.fedx.vera.databinding.ConstructionMaterialFragmentBinding
import com.fedx.vera.databinding.FastenersFragmentBinding
import kotlinx.android.synthetic.main.products_fragment.*

class ConstructionMaterialFragment : Fragment() {

    private val viewModel: ConstructionMaterialViewModel by lazy {
        ViewModelProviders.of(this).get(ConstructionMaterialViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ConstructionMaterialFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val toolbar = binding.tbGeneral
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            view!!.findNavController().navigateUp()
        }

        binding.apply {
            llFasteners.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_fastenersFragment)
            }
            llPaints.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_paintsFragment)
            }
            llSanitary.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_sanitaryFragment)
            }
            llTools.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_toolsFragment)
            }
            llPlastic.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_plasticFragment)
            }
            llGloves.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_glovesFragment)
            }
            llMixes.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_mixesFragment)
            }
            llFoam.setOnClickListener {
                view!!.findNavController()
                    .navigate(R.id.action_constructionMaterialFragment_to_foamFragment)
            }
        }
        return binding.root
    }
}

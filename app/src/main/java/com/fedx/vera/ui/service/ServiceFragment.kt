package com.fedx.vera.ui.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fedx.vera.R
import com.fedx.vera.databinding.ServiceFragmentBinding

class ServiceFragment : Fragment() {

    private lateinit var dashboardViewModel: ServiceViewModel

    private var expandableAdapter: ExpandableAdapter? = null
    private var expList: ExpandableListView? = null
    private val parents = arrayOf("IT, информационные технологии", "Безопасность",
        "Организаторы праздников", "Строительство и ремонт", "Транспорт, перевозки, грузчики",
        "Юридические услуги")
    private var itInformationTechnology: ArrayList<String>? = null
    private var security: ArrayList<String>? = null
    private var organizationHoliday: ArrayList<String>? = null
    private var building: ArrayList<String>? = null
    private var transport: ArrayList<String>? = null
    private var legalServices: ArrayList<String>? = null


    internal var expandableListView: ExpandableListView? = null
    internal var adapter: ExpandableListAdapter? = null
    internal var titleList: List<String> ? = null

    val data: HashMap<String, List<String>>
        get() {
            val listData = HashMap<String, List<String>>()

            val itInformationTechnology = ArrayList<String>()
            itInformationTechnology.add("компьютерная помощь")
            itInformationTechnology.add("ремонт телефонов")
            itInformationTechnology.add("создание сайтов")

            val security = ArrayList<String>()
            security.add("монтаж пожарной сигнализации")
            security.add("монтаж систем видеонаблюдения")

            val organizationHoliday = ArrayList<String>()
            organizationHoliday.add("ведущие, тамада")
            organizationHoliday.add("услуги для праздников")

            val building = ArrayList<String>()
            building.add("малярно-штукатурные работы")
            building.add("ремонт квартир")
            building.add("сантехнические работы")

            val transport = ArrayList<String>()
            transport.add("грузчики")
            transport.add("грузовые перевозки")
            transport.add("пассажирские перевозки")
            transport.add("помощь при ДТП")
            transport.add("эвакуатор")

            val legalService = ArrayList<String>()
            legalService.add("адвокаты")

            listData["IT, информационные технологии"] = itInformationTechnology
            listData["Безопасность"] = security
            listData["Организация праздников"] = organizationHoliday
            listData["Строительство и ремонт"] = building
            listData["Транспорт, перевозки, грузчики"] = transport
            listData["Юридические услуги"] = legalService

            return listData
        }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(ServiceViewModel::class.java)
        val binding : ServiceFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.service_fragment, container, false)

        expandableListView = binding.expList as ExpandableListView

        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = activity?.let { ExpandableAdapter(it, titleList as ArrayList<String>, listData) }
            expandableListView!!.setAdapter(adapter)

            expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
                Toast.makeText(activity, "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition), Toast.LENGTH_SHORT).show()
                false
            }
        }
        return binding.root
    }
}

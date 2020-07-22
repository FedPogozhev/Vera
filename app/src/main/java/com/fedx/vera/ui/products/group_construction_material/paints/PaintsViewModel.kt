package com.fedx.vera.ui.products.group_construction_material.paints

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.network.GoodsApi
import com.fedx.vera.network.GoodsProperty
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.network.SortGoods
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class PaintsViewModel : ViewModel() {
    private val _property = MutableLiveData<List<GoodsProperty>>()
    val property: LiveData<List<GoodsProperty>>
        get() = _property

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getPaints(GroupsGoods.PAINTS)
    }

    private fun getPaints(filter: GroupsGoods) {
        coroutineScope.launch {
            var getPropertiesDeferred = GoodsApi.
                retrofitServiceGetSelectGeneral.getProperties(filter.groups, GeoData.sendCity)
            try {
                var listResult = getPropertiesDeferred.await()
                _property.value = listResult
            } catch (e: Exception) {

            }
        }
    }
    fun getFilterWithSort(filter: GroupsGoods, sort: SortGoods){
        coroutineScope.launch {
            var getPropertiesDeferred = GoodsApi
                .retrofitServiceGetSelectGeneral
                .getPropertiesWithSort(filter.groups, GeoData.sendCity, sort.sort)
            try {
                var listResult = getPropertiesDeferred.await()
                Log.i("sort", "$listResult")
                _property.value = listResult
            } catch (e: Exception) {

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

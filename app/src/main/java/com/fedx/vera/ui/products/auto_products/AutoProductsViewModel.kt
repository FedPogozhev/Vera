package com.fedx.vera.ui.products.auto_products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.network.*
import com.fedx.vera.ui.general.GeneralFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class AutoProductsViewModel : ViewModel() {
    private val _property = MutableLiveData<List<GoodsProperty>>()
    val property: LiveData<List<GoodsProperty>>
        get() = _property

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getFilter(GroupsGoods.AUTO_PRODUCTS)
        //getFilterWithSort(GroupsGoods.AUTO_PRODUCTS, SortGoods.NAME)
    }

    private fun getFilter(filter: GroupsGoods){
        coroutineScope.launch {

            var getPropertiesDeferred = GoodsApi.
                retrofitServiceGetSelectGeneral.getProperties(filter.groups, GeoData.sendCity)
            try {
                var listResult = getPropertiesDeferred.await()
                _property.value = listResult
                Log.i("sort model", "$listResult")

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

package com.fedx.vera.ui.products.tableware

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.network.GoodsApi
import com.fedx.vera.network.GoodsProperty
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.network.SortGoods
import com.fedx.vera.ui.general.GeneralFragment
import com.fedx.vera.ui.general.GeneralViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception


class TableWareViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _property = MutableLiveData<List<GoodsProperty>>()
    val property: LiveData<List<GoodsProperty>>
        get() = _property

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getTableWareProperties(GroupsGoods.TABLEWARE)
    }

    private fun getTableWareProperties(filter: GroupsGoods) {
        coroutineScope.launch {
            var getPropertiesDeferred = GoodsApi.
                retrofitServiceGetSelectGeneral.getProperties(filter.groups, GeoData.sendCity)
            try {
                var listResult = getPropertiesDeferred.await()
                Log.d("ViewModel", listResult.toString())
                _property.value = listResult
            } catch (e: Exception) {
                _response.value = e.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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
    //метод для поиска товара
    fun getGeneralsSearch(textSearch: String) {
        coroutineScope.launch {
            var getPropertiesDeferred =
                GoodsApi.retrofitServiceGetSelectGeneral.getPropertiesSearch(
                    GroupsGoods.TABLEWARE.groups,
                    GeoData.sendCity,
                    textSearch)
            try {
                var listResult = getPropertiesDeferred.await()
                _property.value = listResult
            } catch (e: Exception) {

            }
        }
    }
    //метод для поиска товара с сортировкой
    fun getGeneralsSearchWithSort(sort: SortGoods, textSearch: String) {
        coroutineScope.launch {
            var getPropertiesDeferred =
                GoodsApi.retrofitServiceGetSelectGeneral.getPropertiesSearchWithSort(
                    GroupsGoods.TABLEWARE.groups,
                    GeoData.sendCity,
                    textSearch,
                    sort.sort
                )
            try {
                var listResult = getPropertiesDeferred.await()
                _property.value = listResult
            } catch (e: Exception) {

            }
        }
    }
}

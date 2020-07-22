package com.fedx.vera.ui.general

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.fedx.vera.MainActivity
import com.fedx.vera.geo_data.GeoData
import com.fedx.vera.network.GoodsApi
import com.fedx.vera.network.GoodsProperty
import com.fedx.vera.network.GroupsGoods
import com.fedx.vera.ui.general.GeneralFragment.Companion.cityAdmin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.logging.Filter

class GeneralViewModel : ViewModel() {
    private val _property = MutableLiveData<List<GoodsProperty>>()
    val property: LiveData<List<GoodsProperty>>
        get() = _property

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city
    private val _sendCity = MutableLiveData<String>()
    val sendCity: LiveData<String>
        get() = _city

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getCityModel()
    }

    //определяем город
    private fun getCityModel() {
        val timer = object: CountDownTimer(1000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                if (cityAdmin != "") {
                    _city.value = GeoData().getSelectCity(cityAdmin)
                    _sendCity.value = GeoData.sendCity
                    Log.d("Location", "send city ${_sendCity.value}")
                }
                else {
                    _city.value = "ошибка определения города"
                }
            }

            override fun onFinish() {
                getGeneralsProperties(GroupsGoods.ALL)
            }
        }
        timer.start()
    }

    private fun getGeneralsProperties(filter: GroupsGoods) {
        coroutineScope.launch {
            var getPropertiesDeferred =
                GoodsApi.retrofitServiceGetSelectGeneral.getProperties(filter.groups, _sendCity.value.toString())
            try {
                var listResult = getPropertiesDeferred.await()
                _property.value = listResult
            } catch (e: Exception) {

            }
        }
    }

    //метод для поиска товара
    fun getGeneralsSearch(textSearch: String) {
        coroutineScope.launch {
            var getPropertiesDeferred =
                GoodsApi.retrofitServiceGetSelectGeneral.getPropertiesSearch(GroupsGoods.ALL.groups,
                    _sendCity.value.toString(), textSearch)
            try {
                var listResult = getPropertiesDeferred.await()
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

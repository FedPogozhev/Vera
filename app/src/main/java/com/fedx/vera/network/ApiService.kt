package com.fedx.vera.network

import com.fedx.vera.MainActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class GroupsGoods(val groups: String) {
    ALL("all"),
    TABLEWARE("tableware"),
    FASTENERS("fasteners"),
    PAINTS("paints"),
    SANITARY("sanitary"),
    TOOLS("tools"),
    PLASTIC("plastic"),
    GLOVES("gloves"),   //перчатки
    MIXES("mixes"), //сухие смеси
    FOAM("foam"),    //пена
    BULK_MATERIAL("bulk_material"), //сыпучие материалы
    AUTO_PRODUCTS("auto") //авто товары
}

enum class SortGoods(val sort: String) {
    NAME("fullName"),
    PRICE("price")
}

private const val BASE_URL = " http://vera-mobile-directory.000webhostapp.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("selectGoodsGet.php")
    fun getProperties(
        @Query("group") type: String,
        @Query("city") city: String
    ):
            Deferred<List<GoodsProperty>>

    @GET("selectGoodsGet.php")
    fun getPropertiesSearch(
        @Query("group") type: String,
        @Query("city") city: String,
        @Query("search") search: String = ""
    ):
            Deferred<List<GoodsProperty>>

    @GET("selectGoodsGet.php")
    fun getPropertiesWithSort(
        @Query("group") type: String,
        @Query("city") city: String,
        @Query("sort") typeSort: String
    ):
            Deferred<List<GoodsProperty>>

    @GET("selectGoodsGet.php")
    fun getPropertiesSearchWithSort(
        @Query("group") type: String,
        @Query("city") city: String,
        @Query("search") search: String,
        @Query("sort") typeSort: String
    ):
            Deferred<List<GoodsProperty>>
}

object GoodsApi {
    val retrofitServiceGetSelectGeneral: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
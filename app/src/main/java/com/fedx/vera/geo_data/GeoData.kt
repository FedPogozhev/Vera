package com.fedx.vera.geo_data

class GeoData() {
    private lateinit var city: String

    companion object {
        var sendCity: String = "" //переменная для отправки наименования города в адрес урл
    }

        fun getSelectCity(cityAdmin: String): String {
            when (cityAdmin) {
                "Ставропольский край" -> {
                    city = "Поиск в Ставрополе"
                    sendCity = "Ставрополь"
                }
                "Краснодарский край" -> {
                    city = "Поиск в Краснодаре"
                    sendCity = "Краснодар"
                }
                "Республика Адыгея" -> {
                    city = "Поиск в Краснодаре"
                    sendCity = "Краснодар"
                }
                else -> city = "не поддерживается сервисом"
            }
            return city
        }
    }

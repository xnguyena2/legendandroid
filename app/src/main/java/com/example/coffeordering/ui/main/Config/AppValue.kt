package com.example.coffeordering.ui.main.Config

import com.google.gson.Gson

object AppValue {
    lateinit var coffeConfig: AppConfig

    lateinit var tableChange: tableChange

    fun ObserverTableChagne(change: tableChange){
        this.tableChange = change
    }

    fun LoadCoffeConfig(configContent: String){
        var gson = Gson()
        coffeConfig = gson?.fromJson(configContent, AppConfig::class.java)
        tableChange.apply {
            this(coffeConfig.tableNo)
        }
    }
}

typealias tableChange = (Int)->Unit
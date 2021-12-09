package com.me.myapplication.activity.viewModel

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.me.myapplication.R
import com.me.myapplication.activity.SettingActivity


class SettingViewModel(
    namePreserved: String,
    constellationPreserved: String,
    headerPreserved: String
) : ViewModel() {
    val name = MutableLiveData<String>()
    val constellation = MutableLiveData<String>()
    val header = MutableLiveData<String>()

    init {
        name.value = "昵称:${namePreserved}"
        constellation.value = "星座:${constellationPreserved}"
        header.value = headerPreserved
    }

    fun changeHeader(context: Context) {
        header.value =
            context.getSharedPreferences("setting", AppCompatActivity.MODE_PRIVATE)!!
                .getString("header", R.drawable.header.toString())
    }


    fun changeName(context: Context) {
        name.value = "昵称:${
            context.getSharedPreferences("setting", AppCompatActivity.MODE_PRIVATE)!!
                .getString(SettingActivity.NAME, "")
        }"
    }

    fun changeConstellation(context: Context) {
        constellation.value = "星座:${
            context.getSharedPreferences("setting", AppCompatActivity.MODE_PRIVATE)!!
                .getString(SettingActivity.CONSTELLATION, "")
        }"
    }

}
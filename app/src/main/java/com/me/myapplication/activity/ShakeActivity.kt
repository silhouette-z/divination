package com.me.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.me.myapplication.activity.shake.SensorManagerHelper
import com.me.myapplication.R
import com.me.myapplication.activity.shake.closeResultDialog
import com.me.myapplication.activity.shake.showResultDialog
import com.me.myapplication.databinding.ActivityShakeBinding

class ShakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val baguaPath = "https://www.gpbctv.com/uploads/20210410/zip_161804530962TFe3.jpg"
//        val baguaPath = "file:///android_asset/bagua.jpg"
        val viewBinding = ActivityShakeBinding.inflate(LayoutInflater.from(this))
        val mainView: View = viewBinding.root

        Glide.with(this)
            .load(baguaPath)
            .error(R.mipmap.error)
            .into(viewBinding.baguaImage)
        setContentView(mainView)

        var sensorHelper = SensorManagerHelper(this)
        sensorHelper.setOnShakeListener(object : SensorManagerHelper.OnShakeListener {        override fun onShake() {

            closeResultDialog()
            showResultDialog()
        }
        })

    }

}
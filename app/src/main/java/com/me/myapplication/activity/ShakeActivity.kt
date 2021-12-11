package com.me.myapplication.activity

import android.app.Service
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.me.myapplication.activity.shake.SensorManagerHelper
import com.me.myapplication.R
import com.me.myapplication.activity.shake.closeResultDialog
import com.me.myapplication.activity.shake.showResultDialog
import com.me.myapplication.databinding.ActivityShakeBinding

class ShakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mediaPlayer = MediaPlayer.create(this, R.raw.shake_sound)
        val vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        val baguaPath = "https://www.gpbctv.com/uploads/20210410/zip_161804530962TFe3.jpg" //网络图片 太糊了 白底不好看
        val baguaPath = "file:///android_asset/bagua.png"//自己扣的图，透明底，好看！
        val viewBinding = ActivityShakeBinding.inflate(LayoutInflater.from(this))
        val mainView: View = viewBinding.root

        Glide.with(this)
            .load(baguaPath)
            .error(R.mipmap.error)
            .into(viewBinding.baguaImage)
        setContentView(mainView)

        val mLoadingImageView = findViewById<ImageView>(R.id.bagua_image)
        val mLoadingAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim)
        mLoadingImageView.startAnimation(mLoadingAnim)

        var sensorHelper = SensorManagerHelper(this)
        sensorHelper.setOnShakeListener(object : SensorManagerHelper.OnShakeListener {        override fun onShake() {

//            val showTime = System.currentTimeMillis() + 5000

            mediaPlayer.start()
//            mLoadingImageView.startAnimation(mLoadingAnim)
            vibrator.vibrate(500)
            closeResultDialog()
            showResultDialog()


//            val currentUpdateTime = System.currentTimeMillis()
//
//            object : Thread() {
//                override fun run() {
//                    super.run()
//                    mediaPlayer.start()
//                    sleep(500)
////                    showResultDialog()
//                }
//            }.start()
//            object : Thread() {
//                override fun run() {
//                    super.run()
//                    mLoadingImageView.startAnimation(mLoadingAnim)
//                    vibrator.vibrate(500)
////                    sleep(500)
//                }
//            }.start()

//            mLoadingImageView.clearAnimation()
        }

        })

    }

}
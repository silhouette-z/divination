package com.me.myapplication.activity

import android.Manifest
import android.animation.Animator
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.webkit.RenderProcessGoneDetail
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.gson.GsonBuilder
import com.me.myapplication.R
import com.me.myapplication.activity.bean.DailyAstro
import com.me.myapplication.activity.bean.jitang
import com.me.myapplication.activity.interceptor.TimeConsumeInterceptor
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.airbnb.lottie.LottieAnimationView

import android.annotation.SuppressLint
import android.widget.RelativeLayout


class SavePicActivity : AppCompatActivity() {

    private var layout_poster: RelativeLayout? = null
    private var layout_save: LinearLayout? = null
    private var today_info: TextView? = null
    private var mBitmap: Bitmap? = null

    private var animationView: LottieAnimationView? = null


    var mcontext: Context? = null
    val gson = GsonBuilder().create()
    var client: OkHttpClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_pic)

        animationView = findViewById(R.id.animation_view)
        animationView?.visibility = View.GONE


        mcontext = applicationContext

        client = OkHttpClient
            .Builder()
            .build()

        today_info = findViewById<View>(R.id.today_info) as TextView
        click()
        layout_poster = findViewById<View>(R.id.layout_poster) as RelativeLayout
        layout_save = findViewById<View>(R.id.layout_save) as LinearLayout
        layout_save!!.setOnClickListener { // ??????????????????
            savePoster()
            animationStart()
//            animationView?.visibility = View.GONE
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun animationStart() {
        animationView?.visibility = View.VISIBLE
        animationView?.playAnimation()

//        if (animationView?.isAnimating == false)
//            animationView?.visibility = View.GONE
//        var animatorListener = object AnimatorListener(){
            var mAnimatorListener = object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }
                override fun onAnimationEnd(animation: Animator) {
                    animationView?.visibility = View.GONE
                }
                override fun onAnimationCancel(animation: Animator) {

                }
                override fun onAnimationRepeat(animation: Animator) {}
            }
//        }
        animationView?.addAnimatorListener(mAnimatorListener)

    }

    //??????????????????
    private fun savePoster() {

        // 1.View??????
        layout_poster!!.isDrawingCacheEnabled = true
        // ????????????View
        layout_poster!!.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        // 2.????????????Bitmap
        mBitmap = layout_poster!!.drawingCache
        // 3.?????????SD???
        if (mBitmap != null) {
            //???????????????Android 6.0 ????????????????????????????????????????????????????????????
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions()
            } else {
                saveToLocal(mBitmap)
            }
        }
    }
    //????????????
    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // ?????????????????????????????????????????????????????????????????????????????????????????????
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_STATE_CODE
            )
        } else {
            saveToLocal(mBitmap)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_STATE_CODE -> if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                saveToLocal(mBitmap)
            } else {
                Toast.makeText(this, "????????????????????????????????????", Toast.LENGTH_LONG).show()
                return
            }
            else -> {}
        }
    }

    //????????????Bitmap????????????
    private fun saveToLocal(bitmap: Bitmap?) {
        try {
            val appDir = File(Environment.getExternalStorageDirectory(),"Poster")
//            val appDir = mcontext?.filesDir
//            val appDir = File(mcontext?.filesDir,"Poster")
            // ????????????????????????
            if (!appDir?.exists()!!) {
                appDir?.mkdir()
            }
            val file = File(appDir, "view_" + System.currentTimeMillis() + ".jpg")
            val out: FileOutputStream
            try {
                out = FileOutputStream(file)
//                val resolver = applicationContext.contentResolver
                if (bitmap!!.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                    out.flush()
                    out.close()
                    // ??????????????????
                    val uri = Uri.fromFile(file)
                    val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
                    sendBroadcast(scannerIntent)
                    Toast.makeText(this, "???????????????????????????", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val REQUEST_STATE_CODE = 1010
    }

    fun request(url: String, callback: Callback) {
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client?.newCall(request)?.enqueue(callback)
    }

    fun click() {
        val url =
            "http://api.lkblog.net/ws/api.php"

        request(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                today_info?.text = "?????????????????????,???????????????"
            }

            override fun onResponse(call: Call, response: Response) {

                val bodyString = response.body?.string()
                val jitang = gson.fromJson(bodyString, jitang::class.java)
                runOnUiThread {
                    if (jitang.data == null) {
                        today_info?.text = "?????????????????????,???????????????"
                    } else {
                        today_info?.text = jitang.data
                    }
                }
            }
        })
    }

}
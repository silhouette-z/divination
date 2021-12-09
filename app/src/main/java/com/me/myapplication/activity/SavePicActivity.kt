package com.me.myapplication.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
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

class SavePicActivity : AppCompatActivity() {

    private var layout_poster: LinearLayout? = null
    private var layout_save: LinearLayout? = null
    private var today_info: TextView? = null
    private var mBitmap: Bitmap? = null
    private var jt: String? = null

    var mcontext: Context? = null
    val gson = GsonBuilder().create()
    var client: OkHttpClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_pic)
        mcontext = applicationContext

        client = OkHttpClient
            .Builder()
            .build()

        today_info = findViewById<View>(R.id.today_info) as TextView
        click()
        layout_poster = findViewById<View>(R.id.layout_poster) as LinearLayout
        layout_save = findViewById<View>(R.id.layout_save) as LinearLayout
        layout_save!!.setOnClickListener { // 保存海报图片
            savePoster()
        }
    }

    /**
     * 保存海报图片
     */
    private fun savePoster() {

        // 1.View截图
        layout_poster!!.isDrawingCacheEnabled = true
        // 重新测量View
        layout_poster!!.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        // 2.创建一个Bitmap
        mBitmap = layout_poster!!.drawingCache
        // 3.保存到SD卡
        if (mBitmap != null) {
            //判断是否为Android 6.0 以上的系统版本，如果是，需要动态添加权限
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions()
            } else {
                saveToLocal(mBitmap)
            }
        }
    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
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
                Toast.makeText(this, "权限授予失败，请重新授予", Toast.LENGTH_LONG).show()
                return
            }
            else -> {}
        }
    }

    /**
     * 保存一张Bitmap图到本地
     */
    private fun saveToLocal(bitmap: Bitmap?) {
        try {
            val appDir = File(Environment.getExternalStorageDirectory(), "Poster")
            // 没有目录创建目录
            if (!appDir.exists()) {
                appDir.mkdir()
            }
            val file = File(appDir, "view_" + System.currentTimeMillis() + ".jpg")
            val out: FileOutputStream
            try {
                out = FileOutputStream(file)
                if (bitmap!!.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                    out.flush()
                    out.close()
                    // 通知图库更新
                    val uri = Uri.fromFile(file)
                    val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
                    sendBroadcast(scannerIntent)
                    Toast.makeText(this, "保存图片到相册成功", Toast.LENGTH_SHORT).show()
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
                today_info?.text = "您的鸡汤还在熬,稍后再来哦"
            }

            override fun onResponse(call: Call, response: Response) {

                val bodyString = response.body?.string()
                val jitang = gson.fromJson(bodyString, jitang::class.java)
                runOnUiThread {
                    if (jitang.data == null) {
                        today_info?.text = "您的鸡汤还在熬,稍后再来哦"
                    } else {
                        today_info?.text = jitang.data
                    }
                }
            }
        })
    }
}
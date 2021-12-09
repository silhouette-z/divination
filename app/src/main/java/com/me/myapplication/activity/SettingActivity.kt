package com.me.myapplication.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.google.android.material.internal.ContextUtils.getActivity
import com.me.myapplication.R
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.*






class SettingActivity : AppCompatActivity() {
    companion object{
        const val TAKE_PHOTO = 0
        const val CHOOSE_ALBUM = 1
        const val HEADER_FILENAME = "/header.png"
    }
    var dialog: AlertDialog? = null
    var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        headerImage.setOnClickListener {
            initAlertDialog()
            dialog?.show()
            true
        }

    }


    fun initAlertDialog() {
        var list = arrayOf<String>("拍照", "从相册中选择")
        dialog = AlertDialog.Builder(this).setTitle("选择上传方式")
            .setItems(list, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        TAKE_PHOTO -> {

                        }
                        CHOOSE_ALBUM -> {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                            intent.addCategory(Intent.CATEGORY_OPENABLE)
                            intent.type = "image/*"
                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                            startActivityForResult(intent, CHOOSE_ALBUM)
                        }
                    }
                }
            }).create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
         when (requestCode) {
            CHOOSE_ALBUM -> {
                Log.d("uri",intent!!.data.toString())
                Glide.with(context).load(intent.data).into(headerImage)
            }
        }
    }




    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }





}


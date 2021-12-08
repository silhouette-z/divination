package com.me.myapplication.activity


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.me.myapplication.R
import kotlinx.android.synthetic.main.activity_setting.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream


class SettingActivity : AppCompatActivity() {
    companion object{
        const val TAKE_PHOTO = 0
        const val CHOOSE_ALBUM = 1
    }
    var dialog: AlertDialog? = null

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
                            val openAlbumIntent = Intent(
                                Intent.ACTION_GET_CONTENT)
                            openAlbumIntent.setType("image/*")
                            startActivityForResult(openAlbumIntent, CHOOSE_ALBUM)

                        }
                    }
                }

            }).create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CHOOSE_ALBUM -> {
                var file = File("res.drawable.header")
                val outputStream = FileOutputStream(file)
                outputStream.write(data.getByteArrayExtra())
            }
        }
    }
}


package com.me.myapplication.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.me.myapplication.R
import com.me.myapplication.activity.viewModel.SettingViewModel
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    companion object {
        const val TAKE_PHOTO = 0
        const val CHOOSE_ALBUM = 1
        const val EDIT_NAME = 2
        const val EDIT_CONSTELLATION = 3

        const val NAME = "name"
        const val CONSTELLATION = "constellation"
        const val HEADER = "header"
    }

    var dialog: AlertDialog? = null
    var context = this
    lateinit var viewModel: SettingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        viewModel = SettingViewModel(
            getSharedPreferences("setting", MODE_PRIVATE)!!.getString(NAME, "")!!,
            getSharedPreferences("setting", MODE_PRIVATE)!!.getString(CONSTELLATION, "")!!,
            getSharedPreferences("setting", MODE_PRIVATE)!!.getString(HEADER, R.drawable.header.toString())!!
        )

        viewModel.name.observe(this, Observer { name ->
            nameTextView.text = name
        })

        viewModel.constellation.observe(this, Observer { constellation ->
            constellationTextView.text = constellation
        })

        viewModel.header.observe(this, Observer { header->
           Glide.with(this).load(header).error(R.drawable.header).into(headerImage)
        })

        headerImage.setOnClickListener {
            initAlertDialog()
            dialog?.show()
            true
        }

        nameTextView.setOnClickListener {
            var intent = Intent(this, EditActivity::class.java)
            startActivityForResult(intent, EDIT_NAME)

        }

        constellationTextView.setOnClickListener {
            var intent = Intent(this, EditActivity::class.java)
            startActivityForResult(intent, EDIT_CONSTELLATION)
        }

        settingButton.setOnClickListener {
            val sp = getSharedPreferences("setting", MODE_PRIVATE)
            val string = sp.getString(CONSTELLATION, "")
            if (string == "") {
                Toast.makeText(this, "请填写星座", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                setResult(RESULT_OK, intent)
                getSharedPreferences("setting", MODE_PRIVATE).edit()
                    .putBoolean("isFirst", false).apply()
                finish()
            }
        }

    }

    fun updateSetting(text: String, attribute: String) {
        val editor = getSharedPreferences("setting", MODE_PRIVATE).edit()
        editor.putString(attribute, text)
        editor.commit()
        val sp = getSharedPreferences("setting", MODE_PRIVATE)
        val string = sp.getString(attribute, " ")
        Log.d("sp", string.toString())
    }


    fun initAlertDialog() {
        var list = arrayOf<String>("拍照", "从相册中选择")
        dialog = AlertDialog.Builder(this).setTitle("选择上传方式")
            .setItems(list, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when (which) {
                        TAKE_PHOTO -> {
                            TODO()
                        }
                        CHOOSE_ALBUM -> {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                            intent.addCategory(Intent.CATEGORY_OPENABLE)
                            intent.type = "image/*"
                            intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*"
                            )
                            startActivityForResult(intent, CHOOSE_ALBUM)
                        }
                    }
                }
            }).create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if(resultCode == RESULT_OK) {
            when (requestCode) {
                CHOOSE_ALBUM -> {
                    val headerString = intent!!.data?.toString()
                    updateSetting(headerString!!, HEADER)
                    viewModel.changeHeader(this)
                }
                EDIT_NAME -> {
                    val text = intent?.extras?.get("data")?.toString()
                    updateSetting(text!!, NAME)
                    viewModel.changeName(this)
                }
                EDIT_CONSTELLATION -> {
                    val text = intent?.extras?.get("data")?.toString()
                    updateSetting(text!!, CONSTELLATION)
                    viewModel.changeConstellation(this)
                }
            }
        }
    }
}


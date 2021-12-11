package com.me.myapplication.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
        const val EDIT_HEADER = 4

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

        initViewModel()
        requestPermissions()

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
            // 查看是否填写星座，星座为必填
            val sp = getSharedPreferences("setting", MODE_PRIVATE)
            val string = sp.getString(CONSTELLATION, "")
            if (string == "") {
                Toast.makeText(this, "请填写星座", Toast.LENGTH_SHORT).show()
            // 若为首次设置，设置完之后进入主页
            } else if(intent.extras?.get("data") == SplashActivity.FIRST_SETTING){
                val intent = Intent(this, HomepageActivity::class.java)
                getSharedPreferences("setting", MODE_PRIVATE).edit()
                    .putBoolean("isFirst", false).apply()
                startActivity(intent)
                finish()
            // 非首次设置，直接返回
            } else {
                finish()
            }
        }

    }

    fun initViewModel() {
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
    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {

            } else {
                Toast.makeText(this, "权限授予失败", Toast.LENGTH_LONG).show()
                return
            }
            else -> {}
        }
    }

    /**
     * 在sharedPreference中更改偏好设置
     */

    fun updateSetting(text: String, attribute: String) {
        val editor = getSharedPreferences("setting", MODE_PRIVATE).edit()
        editor.putString(attribute, text)
        editor.commit()
        val sp = getSharedPreferences("setting", MODE_PRIVATE)
        val string = sp.getString(attribute, " ")
        Log.d("sp", string.toString())
    }

    /**
     * 上传头像弹窗选择
     */
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
                            // 系统文件选择器的实现方式
//                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
//                            intent.addCategory(Intent.CATEGORY_OPENABLE)
//                            intent.type = "image/*"
//                            intent.setDataAndType(
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                "image/*"
//                            )
//                            startActivityForResult(intent, CHOOSE_ALBUM)
                            // 打开相册
                            var intent = Intent(context, AlbumActivity::class.java)
                            startActivityForResult(intent, CHOOSE_ALBUM)
                        }
                    }
                }
            }).create()
    }

    /**
     *
     * 个人信息改变后调用被observe的方法
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if(resultCode == RESULT_OK) {
            when (requestCode) {
                CHOOSE_ALBUM -> {
                    val headerString = intent?.extras?.get("data")?.toString()
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


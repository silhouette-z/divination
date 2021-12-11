package com.me.myapplication.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.donkingliang.imageselector.utils.ImageSelector
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.me.myapplication.R
import com.me.myapplication.activity.adapter.FaceResultAdapter
import com.me.myapplication.activity.tensorflow.FaceFeature
import com.me.myapplication.activity.tensorflow.FaceManger
import com.me.myapplication.activity.tensorflow.FeatureSearchResult
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_face_compare.*
import kotlinx.android.synthetic.main.layout_bottom_dialog.view.*

class FaceCompareActivity : AppCompatActivity() {
    private val REQ_CODE = 101
    private val REQ_CODE1 = 202
    private var selectorNumber : Int = 0
    private var currentImg = "http://img.szonline.net/2019/0520/20190520024106979.jpeg"
    private var currentFeature = FloatArray(0)
    private lateinit var mmkv: MMKV
    private var firstImage : ArrayList<String>? = null
    private var secondImage : ArrayList<String>? = null
    private var firstImageFirst : String? = null
    private var secondImageFirst : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_compare)

        FaceManger.initModel(applicationContext)

        val rootDir = MMKV.initialize(this)
        mmkv = MMKV.defaultMMKV()

        initView()

    }

    private fun initView() {
        btLocal.setOnClickListener {
//            showInputDialog("输入图片地址") { loadImg1(it) }
            selectorNumber = 0
            ImageSelector.builder().useCamera(true).setSingle(true).setViewImage(true).start(this, REQ_CODE)
//            loadImg(firstImageFirst)
        }

        btLink.setOnClickListener {
//            showInputDialog("输入图片地址") { loadImg(it) }
            selectorNumber = 1
//            Glide.with(this)
//                .load(firstImageFirst)
//                .error(R.mipmap.error)
//                .into(ivFace)
            ImageSelector.builder().useCamera(true).setSingle(true).setViewImage(true).start(this, REQ_CODE)
//            loadImg1(secondImageFirst)
        }

        var firstName = "林"
        btAdd.setOnClickListener {
//            showInputDialog("输入姓名") {
//                firstName = it.trim()
//                if (firstName!!.isNotBlank() && currentFeature.isNotEmpty() && currentImg.isNotBlank()) {
//                    val feature = FaceFeature(firstName!!, currentFeature, currentImg)
//                    mmkv.encode(firstName, feature)
//                    toast("特征入库: $firstName $currentImg")
//                }
//            }
            if (firstName!!.isNotBlank() && currentFeature.isNotEmpty() && currentImg.isNotBlank()) {
                val feature = FaceFeature(firstName!!, currentFeature, currentImg)
                mmkv.encode(firstName, feature)
                toast("确认成功！: $firstName $currentImg")
//                firstName?.let { it1 -> search(currentFeature, it1) }
            }
        }

        btSearch.setOnClickListener {
//            search(currentFeature)
            firstName?.let { it1 -> search(currentFeature, it1) }
        }
    }

    private fun loadImg(src: String?) {
        src?.let { srcStr ->
            Glide.with(this).asBitmap().load(srcStr).listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Bitmap>?, isFirstResource: Boolean
                ): Boolean {
                    println("loadImg onLoadFailed: ${e?.message}")
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?, model: Any?, target: Target<Bitmap>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean {
                    println("loadImg onResourceReady")
                    resource?.let { extractFeature(it, srcStr) }
                    return true
                }

            }).into(ivFace)
        }
    }

    private fun loadImg1(src: String) {

    }

    private fun extractFeature(faceBm: Bitmap, src: String) {
        val result = FaceManger.detectFaceMTCNN(faceBm)
        if (result.faceCount > 0) {
            runOnUiThread {
                ivFace.setImageBitmap(result.faceBm)// 刷新图片，画上人脸框
            }

            if (result.faceCount > 1) {
                toast("检测到多个人脸，仅提取第一个人脸特征")
            }

            FaceManger.getFeature(result.faces.first())?.let { feature ->
                currentFeature = feature
                currentImg = src
                btAdd.isClickable = true
                println("提取特征成功:$currentImg")
                println(currentFeature.asList())
                toast("提取特征成功!")

//                search(currentFeature)
            }
        }
    }

    private fun search(feature: FloatArray, name : String) {
        mmkv.allKeys()?.let { keys ->
            val searchList = mutableListOf<FeatureSearchResult>()
            keys.forEach {
                val localFea = mmkv.decodeParcelable(it, FaceFeature::class.java)
                if (localFea.feature!=null){
                    val distance = FaceManger.getDistance(feature, localFea.feature!!)
                    val similarity = FaceManger.distanceToSimilarity(distance)
                    println("对比:${localFea.name} -> 距离:$distance 相似度:$similarity")
                    searchList.add(FeatureSearchResult(localFea, distance, similarity))
                }
//                val distance = FaceManger.getDistance(feature, localFea.feature)
//                val similarity = FaceManger.distanceToSimilarity(distance)
//                println("对比:${localFea.name} -> 距离:$distance 相似度:$similarity")
//                searchList.add(FeatureSearchResult(localFea, distance, similarity))
            }

//            searchList.sortBy { it.distance }
            searchList.sortBy { it.feature.name == name }

            val bast = searchList.last()
//            toast("相似度:${bast.feature.name} ${bast.similarity}")
//            toast("相似度: ${bast.similarity }%")
            similarity_result.text = "相似度: ${bast.similarity }%"
//            showSearchResult(searchList)
        }
    }

    private fun showInputDialog(title: String, clickEnter: (inputText: String) -> Unit) {
        val editText = EditText(this)
        val inputDialog = AlertDialog.Builder(this)
        inputDialog.setTitle(title).setView(editText)
        inputDialog.setPositiveButton("确定") { dialog, which ->
            clickEnter(editText.text.toString())
        }.show()
    }

    private fun showSearchResult(searchList: MutableList<FeatureSearchResult>) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_bottom_dialog, null)
        view.rvBottom.adapter = FaceResultAdapter(R.layout.item_bottom_dialog, searchList)
        dialog.setContentView(view)
        dialog.show()
    }


    private fun toast(msg: String) {
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        println("toast:$msg")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQ_CODE && data != null) {
            // 只有本次调用相机拍出来的照片，返回时才为true 当为true时，图片返回的结果有且只有一张图片。
            val isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false)
            // 获取图片选择器返回的数据
            val image = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            println("onActivityResult:$image")
//            firstImageFirst = firstImage?.first()
            if (selectorNumber == 0){
                firstImageFirst = image?.first()
                image?.let {
                    Glide.with(this)
                        .load(firstImageFirst)
                        .error(R.mipmap.error)
                        .into(ivFace)
                    loadImg(it.first())
                    btAdd.isClickable = false
                }
            }else{
                secondImageFirst = image?.first()
                image?.let {
                    Glide.with(this)
                        .load(firstImageFirst)
                        .error(R.mipmap.error)
                        .into(ivFace_another)
                    loadImg(it.first())
                    btAdd.isClickable = false
                }
            }
        }
//        if (requestCode === REQ_CODE1 && data != null) {
//            // 只有本次调用相机拍出来的照片，返回时才为true 当为true时，图片返回的结果有且只有一张图片。
//            val isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false)
//            // 获取图片选择器返回的数据
//            secondImage = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
//            println("onActivityResult:$secondImage")
//
//            secondImageFirst = secondImage?.first()
//            secondImage?.let {
//                loadImg1(it.first())
//                btAdd.isClickable = false
//            }
//        }
    }
}
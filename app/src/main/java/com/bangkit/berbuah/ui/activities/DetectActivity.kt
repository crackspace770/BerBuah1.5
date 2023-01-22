package com.bangkit.berbuah.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.berbuah.databinding.ActivityDetectBinding
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.utils.Classifier

class DetectActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetectBinding
    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap
    private val mInputSize = 100
    private val mModelPath = "fruits_model.tflite"
    private val mLabelPath = "fruits_labels.txt"
    private val mSamplePath = "orange.jpg"

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDetectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
           mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
           binding.imageView.setImageBitmap(mBitmap)

        }

        binding.apply {
            buttonCamera.setOnClickListener{camera()}
            buttonGallery.setOnClickListener { gallery() }
            buttonDetect.setOnClickListener { detect(FruitItem()) }
          //  btnDetail.setOnClickListener { showDetail(FruitItem()) }
        }

        // handling permissions
        checkandGetpermissions()
    }


    private fun checkandGetpermissions(){
        if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 100)
            }
        }
        else{
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun gallery(){
        Log.d("mssg", "button pressed")
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 250)
    }

    private fun camera(){
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 200)
    }

    private fun detect(fruitItem: FruitItem){
        val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
        if (results != null) {
            Toast.makeText(this, "Buah " + results.nama, Toast.LENGTH_SHORT).show()
        }

        binding.tvResult.text = results?.nama
       
    }

    private fun emptyState() {
        binding.apply {
            layoutEmptyData.root.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 250){
            binding.imageView.setImageURI(data?.data)

            val uri : Uri?= data?.data
            mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            mBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(mBitmap)
        }

        }



    companion object {
        const val EXTRA_DATA_FRUIT = "extra_data_fruit"
        const val EXTRA_DATA_DETAIL = "extra_data_detail"

        const val RESULT_CODE = 110

    }

    }





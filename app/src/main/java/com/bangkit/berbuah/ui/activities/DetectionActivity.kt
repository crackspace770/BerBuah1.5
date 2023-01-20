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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.berbuah.databinding.ActivityDetectBinding
import com.bangkit.berbuah.model.FruitDetect
import com.bangkit.berbuah.utils.Classifier
import com.bangkit.berbuah.utils.JsonReader.Companion.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetectionActivity:AppCompatActivity() {

    private lateinit var binding: ActivityDetectBinding
    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap
    private val mInputSize = 100
    private val mModelPath = "fruits_model.tflite"
    private val mLabelPath = "fruits_labels.txt"
    private val mSamplePath = "orange.jpg"

    //all reference for getting fruit detection
    var txtTest : Int? = null
    private var fruitData = ArrayList<FruitDetect>()
    private var detection: Intent? = null
    var txtResult: String? = null


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

        // get detail data for every fruit from json
        val jsonFileString  = getJsonDataFromAsset(applicationContext, "data.json")
        Log.i("data", jsonFileString!!)
        val gson = Gson()
        val listPersonType = object : TypeToken<List<FruitDetect>>() {}.type
        fruitData = gson.fromJson(jsonFileString, listPersonType)

        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            binding.imageView.setImageBitmap(mBitmap)

        }

        binding.apply {
            buttonCamera.setOnClickListener{camera()}
            buttonGallery.setOnClickListener { gallery() }
            buttonDetect.setOnClickListener { detect() }

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

    private fun detect(){
        //validate what fruit that detect
        detection = validate()
        val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
        binding.tvResult.text = results?.nama
       // binding.tvResult.text = txtTest.toString()

        if(detection != null){
            startActivity(detection)
        }else{
            Toast.makeText(this, "Buah tidak terdeteksi", Toast.LENGTH_LONG).show()
        }
    }

    fun validate():Intent?{
        if (txtResult == "Alpukat"){
            var detect1 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[0].nama)
                putExtra("desc", fruitData[0].deskripsi)
                putExtra("asal", fruitData[0].namaLatin)
                putExtra("manfaat", fruitData[0].manfaat)
                putExtra("nutrisi", fruitData[0].nutrisi)
                putExtra("gambar", fruitData[0].gambar) }
            return detect1
        } else if (txtResult == "Anggur"){
            var detect2 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[1].nama)
                putExtra("desc", fruitData[1].deskripsi)
                putExtra("asal", fruitData[1].namaLatin)
                putExtra("manfaat", fruitData[1].manfaat)
                putExtra("nutrisi", fruitData[1].nutrisi)
                putExtra("gambar", fruitData[1].gambar) }
            return detect2
        } else if (txtResult == "Apel") {
            var untunt3 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[2].nama)
                putExtra("desc", fruitData[2].deskripsi)
                putExtra("asal", fruitData[2].namaLatin)
                putExtra("manfaat", fruitData[2].manfaat)
                putExtra("nutrisi", fruitData[2].nutrisi)
                putExtra("gambar", fruitData[2].gambar) }
            return untunt3;
        } else if (txtResult == "Aprikot") {
            var untunt4 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[3].nama)
                putExtra("desc", fruitData[3].deskripsi)
                putExtra("asal", fruitData[3].namaLatin)
                putExtra("manfaat", fruitData[3].manfaat)
                putExtra("nutrisi", fruitData[3].nutrisi)
                putExtra("gambar", fruitData[3].gambar) }
            return untunt4;
        } else if (txtResult == "Blewah") {
            var untunt5 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[4].nama)
                putExtra("desc", fruitData[4].deskripsi)
                putExtra("asal", fruitData[4].namaLatin)
                putExtra("manfaat", fruitData[4].manfaat)
                putExtra("nutrisi", fruitData[4].nutrisi)
                putExtra("gambar", fruitData[4].gambar) }
            return untunt5;
        } else if (txtResult == "Blueberry") {
            var untunt6 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[5].nama)
                putExtra("desc", fruitData[5].deskripsi)
                putExtra("asal", fruitData[5].namaLatin)
                putExtra("manfaat", fruitData[5].manfaat)
                putExtra("nutrisi", fruitData[5].nutrisi)
                putExtra("gambar", fruitData[5].gambar) }
            return untunt6;
        } else if (txtResult == "Buah Naga") {
            var untunt7 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[6].nama)
                putExtra("desc", fruitData[6].deskripsi)
                putExtra("asal", fruitData[6].namaLatin)
                putExtra("manfaat", fruitData[6].manfaat)
                putExtra("nutrisi", fruitData[6].nutrisi)
                putExtra("gambar", fruitData[6].gambar) }
            return untunt7;
        } else if (txtResult == "Jambu") {
            val untunt8 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[7].nama)
                putExtra("desc", fruitData[7].deskripsi)
                putExtra("asal", fruitData[7].namaLatin)
                putExtra("manfaat", fruitData[7].manfaat)
                putExtra("nutrisi", fruitData[7].nutrisi)
                putExtra("gambar", fruitData[7].gambar) }
            return untunt8;
        } else if (txtResult == "Jeruk") {
            var untunt9 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[8].nama)
                putExtra("desc", fruitData[8].deskripsi)
                putExtra("asal", fruitData[8].namaLatin)
                putExtra("manfaat", fruitData[8].manfaat)
                putExtra("nutrisi", fruitData[8].nutrisi)
                putExtra("gambar", fruitData[8].gambar) }
            return untunt9;
        } else if (txtResult == "Kelapa") {
            var untunt10 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[9].nama)
                putExtra("desc", fruitData[9].deskripsi)
                putExtra("asal", fruitData[9].namaLatin)
                putExtra("manfaat", fruitData[9].manfaat)
                putExtra("nutrisi", fruitData[9].nutrisi)
                putExtra("gambar", fruitData[9].gambar) }
            return untunt10;
        } else if (txtResult == "Kiwi") {
            var untunt11 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[10].nama)
                putExtra("desc", fruitData[10].deskripsi)
                putExtra("asal", fruitData[10].namaLatin)
                putExtra("manfaat", fruitData[10].manfaat)
                putExtra("nutrisi", fruitData[10].nutrisi)
                putExtra("gambar", fruitData[10].gambar) }
            return untunt11;
        } else if (txtResult == "Kacang") {
            var untunt12 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[11].nama)
                putExtra("desc", fruitData[11].deskripsi)
                putExtra("asal", fruitData[11].namaLatin)
                putExtra("manfaat", fruitData[11].manfaat)
                putExtra("nutrisi", fruitData[11].nutrisi)
                putExtra("gambar", fruitData[11].gambar) }
            return untunt12;
        } else if (txtResult == "Belimbing") {
            var untunt13 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[12].nama)
                putExtra("desc", fruitData[12].deskripsi)
                putExtra("asal", fruitData[12].namaLatin)
                putExtra("manfaat", fruitData[12].manfaat)
                putExtra("nutrisi", fruitData[12].nutrisi)
                putExtra("gambar", fruitData[12].gambar) }
            return untunt13;
        } else if (txtResult == "Leci") {
            var untunt14 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[13].nama)
                putExtra("desc", fruitData[13].deskripsi)
                putExtra("asal", fruitData[13].namaLatin)
                putExtra("manfaat", fruitData[13].manfaat)
                putExtra("nutrisi", fruitData[13].nutrisi)
                putExtra("gambar", fruitData[13].gambar) }
            return untunt14;
        } else if (txtResult == "Lemon") {
            var untunt15 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[14].nama)
                putExtra("desc", fruitData[14].deskripsi)
                putExtra("asal", fruitData[14].namaLatin)
                putExtra("manfaat", fruitData[14].manfaat)
                putExtra("nutrisi", fruitData[14].nutrisi)
                putExtra("gambar", fruitData[14].gambar) }
            return untunt15;
        } else if (txtResult == "Mangga") {
            var untunt16 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[15].nama)
                putExtra("desc", fruitData[15].deskripsi)
                putExtra("asal", fruitData[15].namaLatin)
                putExtra("manfaat", fruitData[15].manfaat)
                putExtra("nutrisi", fruitData[15].nutrisi)
                putExtra("gambar", fruitData[15].gambar) }
            return untunt16
        } else if (txtResult == "Pepaya") {
            var untunt17 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[16].nama)
                putExtra("desc", fruitData[16].deskripsi)
                putExtra("asal", fruitData[16].namaLatin)
                putExtra("manfaat", fruitData[16].manfaat)
                putExtra("nutrisi", fruitData[16].nutrisi)
                putExtra("gambar", fruitData[16].gambar) }
            return untunt17
        } else if (txtResult == "Pisang") {
            var untunt18 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[17].nama)
                putExtra("desc", fruitData[17].deskripsi)
                putExtra("asal", fruitData[17].namaLatin)
                putExtra("manfaat", fruitData[17].manfaat)
                putExtra("nutrisi", fruitData[17].nutrisi)
                putExtra("gambar", fruitData[17].gambar) }
            return untunt18
        } else if (txtResult == "Rambutan") {
            var untunt19 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[18].nama)
                putExtra("desc", fruitData[18].deskripsi)
                putExtra("asal", fruitData[18].namaLatin)
                putExtra("manfaat", fruitData[18].manfaat)
                putExtra("nutrisi", fruitData[18].nutrisi)
                putExtra("gambar", fruitData[18].gambar) }
            return untunt19
        } else if (txtResult == "Salak") {
            var untunt20 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[19].nama)
                putExtra("desc", fruitData[19].deskripsi)
                putExtra("asal", fruitData[19].namaLatin)
                putExtra("manfaat", fruitData[19].manfaat)
                putExtra("nutrisi", fruitData[19].nutrisi)
                putExtra("gambar", fruitData[19].gambar) }
            return untunt20
        } else if (txtResult == "Strawberry") {
            var untunt21 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[20].nama)
                putExtra("desc", fruitData[20].deskripsi)
                putExtra("asal", fruitData[20].namaLatin)
                putExtra("manfaat", fruitData[20].manfaat)
                putExtra("nutrisi", fruitData[20].nutrisi)
                putExtra("gambar", fruitData[20].gambar) }
            return untunt21
        } else if (txtResult == "Manggis") {
            var untunt22 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[21].nama)
                putExtra("desc", fruitData[21].deskripsi)
                putExtra("asal", fruitData[21].namaLatin)
                putExtra("manfaat", fruitData[21].manfaat)
                putExtra("nutrisi", fruitData[21].nutrisi)
                putExtra("gambar", fruitData[21].gambar) }
            return untunt22
        }
        return detection
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
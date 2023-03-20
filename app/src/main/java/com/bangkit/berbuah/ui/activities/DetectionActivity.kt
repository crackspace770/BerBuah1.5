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
import android.view.Gravity
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
    private var fruitData = ArrayList<FruitDetect>()
    private var detection: Intent? = null
    private var txtResult: String? = null


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
            buttonDetail.setOnClickListener { detail() }
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
        //validate what fruit to detect
        val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
        binding.tvResult.text = results?.nama
        txtResult = results?.nama
    }

    private fun detail(){
        detection = validate()
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
                putExtra("deskripsi", fruitData[0].deskripsi)
                putExtra("namaLatin", fruitData[0].namaLatin)
                putExtra("manfaat", fruitData[0].manfaat.toString())
                putExtra("nutrisi", fruitData[0].nutrisi)
                putExtra("gambar", fruitData[0].gambar) }
            return detect1
        } else if (txtResult == "Anggur"){
            var detect2 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[1].nama)
                putExtra("deskripsi", fruitData[1].deskripsi)
                putExtra("namaLatin", fruitData[1].namaLatin)
                putExtra("manfaat", fruitData[1].manfaat.toString())
                putExtra("nutrisi", fruitData[1].nutrisi)
                putExtra("gambar", fruitData[1].gambar) }
            return detect2
        } else if (txtResult == "Apel") {
            var untunt3 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[2].nama)
                putExtra("deskripsi", fruitData[2].deskripsi)
                putExtra("namaLatin", fruitData[2].namaLatin)
                putExtra("manfaat", fruitData[2].manfaat.toString())
                putExtra("nutrisi", fruitData[2].nutrisi)
                putExtra("gambar", fruitData[2].gambar) }
            return untunt3
        } else if (txtResult == "Aprikot") {
            var untunt4 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[3].nama)
                putExtra("deskripsi", fruitData[3].deskripsi)
                putExtra("namaLatin", fruitData[3].namaLatin)
                putExtra("manfaat", fruitData[3].manfaat.toString())
                putExtra("nutrisi", fruitData[3].nutrisi)
                putExtra("gambar", fruitData[3].gambar) }
            return untunt4
        } else if (txtResult == "Blewah") {
            var untunt5 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[4].nama)
                putExtra("deskripsi", fruitData[4].deskripsi)
                putExtra("namaLatin", fruitData[4].namaLatin)
                putExtra("manfaat", fruitData[4].manfaat.toString())
                putExtra("nutrisi", fruitData[4].nutrisi)
                putExtra("gambar", fruitData[4].gambar) }
            return untunt5
        } else if (txtResult == "Blueberry") {
            var untunt6 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[5].nama)
                putExtra("deskripsi", fruitData[5].deskripsi)
                putExtra("namaLatin", fruitData[5].namaLatin)
                putExtra("manfaat", fruitData[5].manfaat.toString())
                putExtra("nutrisi", fruitData[5].nutrisi)
                putExtra("gambar", fruitData[5].gambar) }
            return untunt6;
        } else if (txtResult == "Buah Naga") {
            var untunt7 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[6].nama)
                putExtra("deskripsi", fruitData[6].deskripsi)
                putExtra("namaLatin", fruitData[6].namaLatin)
                putExtra("manfaat", fruitData[6].manfaat.toString())
                putExtra("nutrisi", fruitData[6].nutrisi)
                putExtra("gambar", fruitData[6].gambar) }
            return untunt7;
        } else if (txtResult == "Ceri") {
            val untunt8 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[7].nama)
                putExtra("deskripsi", fruitData[7].deskripsi)
                putExtra("namaLatin", fruitData[7].namaLatin)
                putExtra("manfaat", fruitData[7].manfaat.toString())
                putExtra("nutrisi", fruitData[7].nutrisi)
                putExtra("gambar", fruitData[7].gambar) }
            return untunt8;
        } else if (txtResult == "Jambu") {
            var untunt9 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[8].nama)
                putExtra("deskripsi", fruitData[8].deskripsi)
                putExtra("namaLatin", fruitData[8].namaLatin)
                putExtra("manfaat", fruitData[8].manfaat.toString())
                putExtra("nutrisi", fruitData[8].nutrisi)
                putExtra("gambar", fruitData[8].gambar) }
            return untunt9;
        } else if (txtResult == "Jeruk") {
            var untunt10 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[9].nama)
                putExtra("deskripsi", fruitData[9].deskripsi)
                putExtra("namaLatin", fruitData[9].namaLatin)
                putExtra("manfaat", fruitData[9].manfaat.toString())
                putExtra("nutrisi", fruitData[9].nutrisi)
                putExtra("gambar", fruitData[9].gambar) }
            return untunt10;
        } else if (txtResult == "Kelapa") {
            var untunt11 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[10].nama)
                putExtra("deskripsi", fruitData[10].deskripsi)
                putExtra("namaLatin", fruitData[10].namaLatin)
                putExtra("manfaat", fruitData[10].manfaat.toString())
                putExtra("nutrisi", fruitData[10].nutrisi)
                putExtra("gambar", fruitData[10].gambar) }
            return untunt11;
        } else if (txtResult == "Kiwi") {
            var untunt12 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[11].nama)
                putExtra("deksripsi", fruitData[11].deskripsi)
                putExtra("namaLatin", fruitData[11].namaLatin)
                putExtra("manfaat", fruitData[11].manfaat.toString())
                putExtra("nutrisi", fruitData[11].nutrisi)
                putExtra("gambar", fruitData[11].gambar) }
            return untunt12;
        } else if (txtResult == "Kacang") {
            var untunt13 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[12].nama)
                putExtra("deskripsi", fruitData[12].deskripsi)
                putExtra("namaLatin", fruitData[12].namaLatin)
                putExtra("manfaat", fruitData[12].manfaat.toString())
                putExtra("nutrisi", fruitData[12].nutrisi)
                putExtra("gambar", fruitData[12].gambar) }
            return untunt13;
        } else if (txtResult == "Belimbing") {
            var untunt14 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[13].nama)
                putExtra("deskripsi", fruitData[13].deskripsi)
                putExtra("namaLatin", fruitData[13].namaLatin)
                putExtra("manfaat", fruitData[13].manfaat.toString())
                putExtra("nutrisi", fruitData[13].nutrisi)
                putExtra("gambar", fruitData[13].gambar) }
            return untunt14;
        } else if (txtResult == "Leci") {
            var untunt15 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[14].nama)
                putExtra("deskripsi", fruitData[14].deskripsi)
                putExtra("namaLatin", fruitData[14].namaLatin)
                putExtra("manfaat", fruitData[14].manfaat.toString())
                putExtra("nutrisi", fruitData[14].nutrisi)
                putExtra("gambar", fruitData[14].gambar) }
            return untunt15;
        } else if (txtResult == "Lemon") {
            var untunt16 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[15].nama)
                putExtra("deskripsi", fruitData[15].deskripsi)
                putExtra("namaLatin", fruitData[15].namaLatin)
                putExtra("manfaat", fruitData[15].manfaat.toString())
                putExtra("nutrisi", fruitData[15].nutrisi)
                putExtra("gambar", fruitData[15].gambar) }
            return untunt16
        } else if (txtResult == "Mangga") {
            var untunt17 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[16].nama)
                putExtra("deskripsi", fruitData[16].deskripsi)
                putExtra("namaLatin", fruitData[16].namaLatin)
                putExtra("manfaat", fruitData[16].manfaat.toString())
                putExtra("nutrisi", fruitData[16].nutrisi)
                putExtra("gambar", fruitData[16].gambar) }
            return untunt17
        } else if (txtResult == "Pepaya") {
            var untunt18 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[17].nama)
                putExtra("deskripsi", fruitData[17].deskripsi)
                putExtra("namaLatin", fruitData[17].namaLatin)
                putExtra("manfaat", fruitData[17].manfaat.toString())
                putExtra("nutrisi", fruitData[17].nutrisi)
                putExtra("gambar", fruitData[17].gambar) }
            return untunt18
        } else if (txtResult == "Pisang") {
            var untunt19 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[18].nama)
                putExtra("deskripsi", fruitData[18].deskripsi)
                putExtra("namaLatin", fruitData[18].namaLatin)
                putExtra("manfaat", fruitData[18].manfaat.toString())
                putExtra("nutrisi", fruitData[18].nutrisi)
                putExtra("gambar", fruitData[18].gambar) }
            return untunt19
        } else if (txtResult == "Rambutan") {
            var untunt20 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[19].nama)
                putExtra("deskripsi", fruitData[19].deskripsi)
                putExtra("namaLatin", fruitData[19].namaLatin)
                putExtra("manfaat", fruitData[19].manfaat.toString())
                putExtra("nutrisi", fruitData[19].nutrisi)
                putExtra("gambar", fruitData[19].gambar) }
            return untunt20
        } else if (txtResult == "Salak") {
            var untunt21 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[20].nama)
                putExtra("deskripsi", fruitData[20].deskripsi)
                putExtra("namaLatin", fruitData[20].namaLatin)
                putExtra("manfaat", fruitData[20].manfaat.toString())
                putExtra("nutrisi", fruitData[20].nutrisi)
                putExtra("gambar", fruitData[20].gambar) }
            return untunt21
        } else if (txtResult == "Strawberry") {
            var untunt22 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[21].nama)
                putExtra("deskripsi", fruitData[21].deskripsi)
                putExtra("namaLatin", fruitData[21].namaLatin)
                putExtra("manfaat", fruitData[21].manfaat.toString())
                putExtra("nutrisi", fruitData[21].nutrisi)
                putExtra("gambar", fruitData[21].gambar) }
            return untunt22
        }else if (txtResult == "Manggis"){
            var untunt23 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[22].nama)
                putExtra("deskripsi", fruitData[22].deskripsi)
                putExtra("namaLatin", fruitData[22].namaLatin)
                putExtra("manfaat", fruitData[22].manfaat.toString())
                putExtra("nutrisi", fruitData[22].nutrisi)
                putExtra("gambar", fruitData[22].gambar) }
            return untunt23
        }else if (txtResult == "Persik"){
            var untunt24 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[23].nama)
                putExtra("deskripsi", fruitData[23].deskripsi)
                putExtra("namaLatin", fruitData[23].namaLatin)
                putExtra("manfaat", fruitData[23].manfaat.toString())
                putExtra("nutrisi", fruitData[23].nutrisi)
                putExtra("gambar", fruitData[23].gambar) }
            return untunt24
        }else if (txtResult == "Pir"){
            var untunt25 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[24].nama)
                putExtra("deskripsi", fruitData[24].deskripsi)
                putExtra("namaLatin", fruitData[24].namaLatin)
                putExtra("manfaat", fruitData[24].manfaat.toString())
                putExtra("nutrisi", fruitData[24].nutrisi)
                putExtra("gambar", fruitData[24].gambar) }
            return untunt25
        }else if (txtResult == "Nanas"){
            var untunt26 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[25].nama)
                putExtra("deskripsi", fruitData[25].deskripsi)
                putExtra("namaLatin", fruitData[25].namaLatin)
                putExtra("manfaat", fruitData[25].manfaat.toString())
                putExtra("nutrisi", fruitData[25].nutrisi)
                putExtra("gambar", fruitData[25].gambar) }
            return untunt26
        }
        else if (txtResult == "Tomat"){
            var untunt27 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[26].nama)
                putExtra("deskripsi", fruitData[26].deskripsi)
                putExtra("namaLatin", fruitData[26].namaLatin)
                putExtra("manfaat", fruitData[26].manfaat.toString())
                putExtra("nutrisi", fruitData[26].nutrisi)
                putExtra("gambar", fruitData[26].gambar) }
            return untunt27
        }
        else if (txtResult == "Kurma"){
            var untunt28 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[27].nama)
                putExtra("deskripsi", fruitData[27].deskripsi)
                putExtra("namaLatin", fruitData[27].namaLatin)
                putExtra("manfaat", fruitData[27].manfaat.toString())
                putExtra("nutrisi", fruitData[27].nutrisi)
                putExtra("gambar", fruitData[27].gambar) }
            return untunt28
        }
        else if (txtResult == "Aprikot"){
            var untunt29 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[28].nama)
                putExtra("deskripsi", fruitData[28].deskripsi)
                putExtra("namaLatin", fruitData[28].namaLatin)
                putExtra("manfaat", fruitData[28].manfaat.toString())
                putExtra("nutrisi", fruitData[28].nutrisi)
                putExtra("gambar", fruitData[28].gambar) }
            return untunt29
        }
        else if (txtResult == "Kastanye"){
            var untunt30 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[29].nama)
                putExtra("deskripsi", fruitData[29].deskripsi)
                putExtra("namaLatin", fruitData[29].namaLatin)
                putExtra("manfaat", fruitData[29].manfaat.toString())
                putExtra("nutrisi", fruitData[29].nutrisi)
                putExtra("gambar", fruitData[29].gambar) }
            return untunt30
        }
        else if (txtResult == "Buah Delima"){
            var untunt31 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[30].nama)
                putExtra("deskripsi", fruitData[30].deskripsi)
                putExtra("namaLatin", fruitData[30].namaLatin)
                putExtra("manfaat", fruitData[30].manfaat.toString())
                putExtra("nutrisi", fruitData[30].nutrisi)
                putExtra("gambar", fruitData[30].gambar) }
            return untunt31
        }
        else if (txtResult == "Plum"){
            var untunt32 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[31].nama)
                putExtra("deskripsi", fruitData[31].deskripsi)
                putExtra("namaLatin", fruitData[31].namaLatin)
                putExtra("manfaat", fruitData[31].manfaat.toString())
                putExtra("nutrisi", fruitData[31].nutrisi)
                putExtra("gambar", fruitData[31].gambar) }
            return untunt32
        }
        else if (txtResult == "Pepino"){
            var untunt33 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[32].nama)
                putExtra("deskripsi", fruitData[32].deskripsi)
                putExtra("namaLatin", fruitData[32].namaLatin)
                putExtra("manfaat", fruitData[32].manfaat.toString())
                putExtra("nutrisi", fruitData[32].nutrisi)
                putExtra("gambar", fruitData[32].gambar) }
            return untunt33
        }
        else if (txtResult == "Markisa"){
            var untunt34 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[33].nama)
                putExtra("deskripsi", fruitData[33].deskripsi)
                putExtra("namaLatin", fruitData[33].namaLatin)
                putExtra("manfaat", fruitData[33].manfaat.toString())
                putExtra("nutrisi", fruitData[33].nutrisi)
                putExtra("gambar", fruitData[33].gambar) }
            return untunt34
        }
        else if (txtResult == "Kesemek"){
            var untunt35 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[34].nama)
                putExtra("deskripsi", fruitData[34].deskripsi)
                putExtra("namaLatin", fruitData[34].namaLatin)
                putExtra("manfaat", fruitData[34].manfaat.toString())
                putExtra("nutrisi", fruitData[34].nutrisi)
                putExtra("gambar", fruitData[34].gambar) }
            return untunt35
        }
        else if (txtResult == "Ceplukan"){
            var untunt36 = Intent(this@DetectionActivity, ResultActivity::class.java).apply {
                putExtra("nama", fruitData[35].nama)
                putExtra("deskripsi", fruitData[35].deskripsi)
                putExtra("namaLatin", fruitData[35].namaLatin)
                putExtra("manfaat", fruitData[35].manfaat.toString())
                putExtra("nutrisi", fruitData[35].nutrisi)
                putExtra("gambar", fruitData[35].gambar) }
            return untunt36
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

    private fun bmpCrop(bmp: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        if (maxHeight > 0 && maxWidth > 0) {
            val width = bmp.width
            val height = bmp.height

            //トリミングする幅、高さ、座標の設定
            val startX = (width - maxWidth) / 2
            val startY = (height - maxHeight) / 2

            return Bitmap.createBitmap(bmp, startX, startY, maxWidth, maxHeight, null, true)
        } else {
            return bmp
        }
    }

}
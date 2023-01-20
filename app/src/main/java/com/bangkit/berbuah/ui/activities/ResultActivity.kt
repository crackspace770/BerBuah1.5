package com.bangkit.berbuah.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.berbuah.databinding.ActivityResultBinding
import com.bumptech.glide.Glide

class ResultActivity:AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create reference from parsing intent
        val nama  = intent.getStringExtra("nama")
        val desc  = intent.getStringExtra("desc")
        val asal = intent.getStringExtra("asal")
        val manfaat = intent.getStringExtra("manfaat")
       // val nutrisi = intent.getStringExtra("nutrisi")
        val gambar = intent.getStringExtra("gambar")

        //fetching all data result
        binding.tvNama.text = nama
        binding.tvNamaLatin.text = desc
        binding.tvDeskripsi.text = asal
        binding.tvManfaat.text = manfaat
       // binding.txtNutrisiBuah.text = nutrisi

        Glide.with(this)
            .load(gambar)
            .fitCenter()
            .into(binding.imgFruit)

        //btn home logic

}

}
package com.bangkit.berbuah.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.berbuah.R
import com.bangkit.berbuah.databinding.ActivityResultBinding
import com.bangkit.berbuah.model.FruitDetect
import com.bangkit.berbuah.viewmodel.DetailViewModel
import com.bangkit.berbuah.viewmodel.ResultViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultActivity:AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel
    private lateinit var fruitDetect: FruitDetect


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = obtainViewModel(this)

        // create reference from parsing intent
        val nama  = intent.getStringExtra("nama")
        val deskripsi  = intent.getStringExtra("deskripsi")
        val namaLatin = intent.getStringExtra("namaLatin")
        val manfaat = intent.getStringExtra("manfaat")
       // val nutrisi = intent.getStringExtra("nutrisi")
        val gambar = intent.getStringExtra("gambar")

        binding.apply {
            viewModel.getDetailFruit().observe(this@ResultActivity){ listFruit->
                listFruit?.let { fruit->
                    fruit.forEach {

                        //fetching all data result
                        tvNama.text = nama
                        tvNamaLatin.text = namaLatin
                        tvDeskripsi.text = deskripsi
                        tvManfaat.text = manfaat
                        // binding.txtNutrisiBuah.text = nutrisi

                        var isFavorite = false
                        CoroutineScope(Dispatchers.IO).launch {
                            fruitDetect.nama.let {
                                val count = viewModel.check(it)
                                isFavorite = if (count > 0) {
                                    setStatusFavorite(true)
                                    true
                                } else {
                                    false
                                }
                            }
                        }

                        fabAddFruit.setOnClickListener {
                            fruitDetect.nama.let { fruitId ->
                                isFavorite = !isFavorite
                                if (isFavorite) {
                                    viewModel.insert(
                                        fruitId,
                                        fruitDetect.nama.toString(),
                                        fruitDetect.deskripsi.toString(),
                                        fruitDetect.gambar.toString()
                                    )
                                    toastMessage(getString(R.string.add_successfully))
                                } else {
                                    viewModel.delete(fruitDetect.nama)
                                    toastMessage(getString(R.string.delete_successfully))
                                }
                                setStatusFavorite(isFavorite)
                            }
                        }

                    }

                }

            }

            tvNama.text = nama
            tvNamaLatin.text = namaLatin
            tvDeskripsi.text = deskripsi
            tvManfaat.text = manfaat
        }


        setActionBarTitle(nama.toString())
        Glide.with(this)
            .load(gambar)
            .fitCenter()
            .into(binding.imgFruit)
    }

    private fun obtainViewModel(activity: AppCompatActivity): ResultViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ResultViewModel::class.java]
    }

    /*
    private fun getDataFruit(fruitDetect:FruitDetect){

        val nama  = intent.getStringExtra("nama")
        val deskripsi  = intent.getStringExtra("deskripsi")
        val namaLatin = intent.getStringExtra("namaLatin")
        val manfaat = intent.getStringExtra("manfaat")
        // val nutrisi = intent.getStringExtra("nutrisi")
        val gambar = intent.getStringExtra("gambar")

        binding.apply {
            viewModel.getDetailFruit().observe(this@ResultActivity){ listFruit->

                listFruit?.let { fruit->
                fruit.forEach {
                    //fetching all data result
                    tvNama.text = nama
                    tvNamaLatin.text = namaLatin
                    tvDeskripsi.text = deskripsi
                    tvManfaat.text = manfaat
                    // binding.txtNutrisiBuah.text = nutrisi


                    var isFavorite = false
                    CoroutineScope(Dispatchers.IO).launch {
                        fruitDetect.nama.let {
                            val count = viewModel.check(it)
                            isFavorite = if (count > 0) {
                                setStatusFavorite(true)
                                true
                            } else {
                                false
                            }
                        }
                    }

                    fabAddFruit.setOnClickListener {
                        fruitDetect.nama.let { fruitId ->
                            isFavorite = !isFavorite
                            if (isFavorite) {
                                viewModel.insert(
                                    fruitId,
                                    fruitDetect.nama.toString(),
                                    fruitDetect.deskripsi.toString(),
                                    fruitDetect.gambar.toString()
                                )
                                toastMessage(getString(R.string.add_successfully))
                            } else {
                                viewModel.delete(fruitDetect.nama)
                                toastMessage(getString(R.string.delete_successfully))
                            }
                            setStatusFavorite(isFavorite)
                        }
                    }

                }


                }

            }


        }
    }
*/

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setStatusFavorite(isFavorite: Boolean) {
        binding.apply {
            if (isFavorite) fabAddFruit.setImageResource(R.drawable.ic_favorite_selected)
            else fabAddFruit.setImageResource(R.drawable.ic_favorite_unselected)
        }
    }

    private fun setActionBarTitle(nama: String) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = nama
        }
    }




}
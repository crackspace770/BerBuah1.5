package com.bangkit.berbuah.ui.activities


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.berbuah.R
import com.bangkit.berbuah.databinding.ActivityResultBinding
import com.bangkit.berbuah.model.FruitItem
import com.bangkit.berbuah.viewmodel.ResultViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultActivity:AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var viewModel: ResultViewModel

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
        val gambar = intent.getStringExtra("gambar")

        binding.apply {

            tvNama.text = nama
            tvNamaLatin.text = namaLatin
            tvDeskripsi.text = deskripsi
            tvManfaat.text = manfaat

            fabAddFruit.setOnClickListener{
                addFavorite(FruitItem())
            }
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


    private fun addFavorite(fruitItem: FruitItem){

        val nama = intent.getStringExtra("nama")


        var isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            nama?.let {
                val count = viewModel.check(it)
                isFavorite = if (count > 0) {
                    setStatusFavorite(true)
                    true
                } else {
                    false
                }
            }
        }

        nama?.let { fruitId ->
            isFavorite = !isFavorite
            if (isFavorite) {
                viewModel.insert(
                    fruitId,
                    fruitItem.nama.toString(),
                    fruitItem.deskripsi.toString(),
                    fruitItem.gambar.toString()
                )
                toastMessage(getString(R.string.add_successfully))
            } else {
                viewModel.delete(nama)
                toastMessage(getString(R.string.delete_successfully))
            }
            setStatusFavorite(isFavorite)
    }

    }


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

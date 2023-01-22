package com.bangkit.berbuah.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.berbuah.R
import com.bangkit.berbuah.databinding.ActivityDetailBinding
import com.bangkit.berbuah.model.*
import com.bangkit.berbuah.utils.Constant.Companion.IMAGE_BASE_URL
import com.bangkit.berbuah.utils.Utils.loadImageUrl
import com.bangkit.berbuah.viewmodel.DetailViewModel
import com.bangkit.berbuah.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var fruit: FruitItem
    private lateinit var nutrisi: KandunganBuah

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailViewModel = obtainViewModel(this)
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        fruit = intent.extras?.getParcelable<FruitItem>(EXTRA_DATA_FRUIT) as FruitItem

        fruit.nama.let {
            val nama = Bundle()
            nama.putString(EXTRA_DATA_DETAIL, fruit.nama)
            detailViewModel.setDetailFruit(it.toString())
            setActionBarTitle(fruit.nama.toString())
        }

        getDataFruit(fruit)


    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun getDataFruit(fruitItem: FruitItem) {
        binding.apply {
            detailViewModel.getDetailFruit().observe(this@DetailActivity) { listFruit ->
                listFruit?.let { fruit ->
                    fruit.forEach { detailFruit ->

                      //  imgFruit.loadImageUrl(detailFruit.image.toString())
                        imgFruit.loadImageUrl("${IMAGE_BASE_URL}${detailFruit.image.toString()}")
                        tvNama.text = detailFruit.nama
                        tvNamaLatin.text = detailFruit.namaLatin
                        tvDeskripsi.text = detailFruit.deskripsi
                        tvManfaat.text = detailFruit.manfaat.toString()

                        /*
                        tvAir.text = nutrisi.air
                        tvKalori.text = nutrisi.kalori
                        tvKarbo.text = nutrisi.karbohidrat
                        tvGula.text = nutrisi.gula
                        tvLemak.text = nutrisi.lemak
                        tvProtein.text = nutrisi.protein
                        tvSerat.text = nutrisi.serat
*/
                        var isFavorite = false
                        CoroutineScope(Dispatchers.IO).launch {
                            fruitItem.nama?.let {
                                val count = detailViewModel.check(it)
                                isFavorite = if (count > 0) {
                                    setStatusFavorite(true)
                                    true
                                } else {
                                    false
                                }
                            }
                        }

                        fabAddFruit.setOnClickListener {
                            fruitItem.nama?.let { fruitId ->
                                isFavorite = !isFavorite
                                if (isFavorite) {
                                    detailViewModel.insert(
                                        fruitId,
                                        fruitItem.nama.toString(),
                                        fruitItem.deskripsi.toString(),
                                        fruitItem.gambar.toString()
                                    )
                                    toastMessage(getString(R.string.add_successfully))
                                } else {
                                    detailViewModel.delete(fruitItem.nama)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

        companion object {
        const val EXTRA_DATA_FRUIT = "extra_data_fruit"
        const val EXTRA_DATA_DETAIL = "extra_data_detail"


        }
}
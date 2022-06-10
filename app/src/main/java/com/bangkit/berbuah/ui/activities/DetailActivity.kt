package com.bangkit.berbuah.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.berbuah.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}
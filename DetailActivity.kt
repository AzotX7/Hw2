package com.example.homew1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homew1.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<Item>("item")
        item?.let {
            binding.imageViewDetail.setImageResource(it.imageResId)
            binding.textViewTitle.text = it.text
            binding.textViewDescription.text = "Description for ${it.text}"
        }
    }
}

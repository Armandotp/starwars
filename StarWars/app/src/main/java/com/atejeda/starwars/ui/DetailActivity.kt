package com.atejeda.starwars.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import com.atejeda.starwars.R
import com.atejeda.starwars.data.model.Element
import com.atejeda.starwars.databinding.ActivityDetailBinding
import com.google.android.material.chip.Chip

class DetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Detail"
        var element = intent.getSerializableExtra("element") as Element

        setViews(element)
    }

    fun setViews(element:Element){

        binding.name.text = element.name
        binding.height.text = element.height.toString()
        binding.weight.text = element.mass.toString()
        binding.sex.text = element.gender
        binding.born.text = element.born

        val chip = Chip(this)
        chip.text = element.species

        binding.typesChipGroup.addView(chip)

        element.affiliations.map {
            val chip = Chip(this)
            chip.text = it
            binding.affiliationsChipGroup.addView(chip)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
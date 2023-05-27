package com.atejeda.starwars.ui

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.atejeda.starwars.R
import com.atejeda.starwars.databinding.ActivityStarWarsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarWarsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStarWarsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.supportActionBar?.hide();
        binding = ActivityStarWarsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
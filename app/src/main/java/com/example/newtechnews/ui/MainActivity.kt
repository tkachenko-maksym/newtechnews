package com.example.newtechnews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.newtechnews.R
import com.example.newtechnews.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    if (navController.currentDestination?.id != R.id.nav_news) {
                        navController.navigate(R.id.nav_news)
                    }
                }
                R.id.nav_bookmarks -> {
                    if (navController.currentDestination?.id != R.id.nav_bookmarks) {
                        navController.navigate(R.id.nav_bookmarks)
                    }
                }
            }
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_news, R.id.nav_bookmarks
            )
        )
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
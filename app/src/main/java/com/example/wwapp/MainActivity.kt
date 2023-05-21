package com.example.wwapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView


import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var navController: NavController
    private lateinit var addNoteButton: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        bottomAppBar = findViewById(R.id.bottomAppBar)
        addNoteButton = findViewById(R.id.fabAddNote)


        setSupportActionBar(bottomAppBar)

        // Get the NavHostController
        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host)
        bottomAppBar.setupWithNavController(navController)

        addNoteButton.setOnClickListener {
            navController.navigate(R.id.addEditFragment)


        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addEditFragment -> {
                    bottomAppBar.visibility = BottomAppBar.GONE
                    addNoteButton.visibility = View.GONE
                }

                else -> {
                    bottomAppBar.visibility = BottomAppBar.VISIBLE
                    addNoteButton.visibility = View.VISIBLE

                }
            }

        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.miBreathe -> {
                    // Navigate to the settings fragment
                    navController.navigate(R.id.breatheFragment)
                    true
                }

                R.id.miSearch -> {
                    val searchView = findViewById<SearchView>(R.id.search_view)
                    searchView.setVisibility(View.VISIBLE)
                    true

                }


                else -> false

            }

        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu
        menuInflater.inflate(R.menu.bottom_app_bar, menu)
        return true
    }


}
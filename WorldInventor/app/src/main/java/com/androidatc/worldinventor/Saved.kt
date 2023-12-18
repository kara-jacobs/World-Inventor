package com.androidatc.worldinventor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidatc.worldinventor.databinding.ActivitySavedBinding
import com.google.android.material.navigation.NavigationView

class Saved : AppCompatActivity() {

    private lateinit var binding: ActivitySavedBinding
    private lateinit var db: PlaceDatabaseHelper
    private lateinit var placeAdapter: PlaceAdapter
    lateinit var myToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSharedPreferences()

        db = PlaceDatabaseHelper(this)
        placeAdapter = PlaceAdapter(db.getAllPlaces(), this)

        binding.placeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.placeRecyclerView.adapter = placeAdapter

        binding.backButton.setOnClickListener{
            var intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.addplace.setOnClickListener{
            var intent  = Intent(this,Create::class.java)
            startActivity(intent)
        }


//Code for the Navigation Drawer
        val drawerLayout: DrawerLayout = findViewById(R.id.myDrawerLayout)
        val navView: NavigationView = findViewById(R.id.myDrawerView)
        myToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        binding.myDrawerLayout.addDrawerListener(myToggle)

        myToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.homeId -> {startActivity(Intent(this, MainActivity::class.java))}
                R.id.createId -> {startActivity(Intent(this, Create::class.java))}
                R.id.savedId -> {startActivity(Intent(this, Saved::class.java))}
                R.id.helpId -> {startActivity(Intent(this, Help::class.java))}
                R.id.preferencesId -> {startActivity(Intent(this, Preferences::class.java))}
            }
            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (myToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//Code to Refresh Data
    override fun onResume(){
        super.onResume()
        placeAdapter.refreshData(db.getAllPlaces())
    }

    fun loadSharedPreferences(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",
            Context.MODE_PRIVATE)
        var switchTheme = sharedPreferences.getBoolean("nightModeActivate", false)
        if (switchTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
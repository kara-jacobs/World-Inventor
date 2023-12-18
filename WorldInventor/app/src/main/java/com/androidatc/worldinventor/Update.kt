package com.androidatc.worldinventor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import com.androidatc.worldinventor.databinding.ActivityUpdateBinding
import com.google.android.material.navigation.NavigationView

class Update : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: PlaceDatabaseHelper
    private var placeId: Int = -1
    lateinit var myToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSharedPreferences()

        db = PlaceDatabaseHelper(this)

        binding.backButton.setOnClickListener{
            var intent  = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        placeId = intent.getIntExtra("place_id", -1)
        if (placeId == -1){
            finish()
            return
        }

        val place = db.getPlaceByID(placeId)
        binding.updateNametxt.setText(place.name)
        binding.updateTerraintxt.setText(place.terrain)
        binding.updateWeathertxt.setText(place.weather)
        binding.updateLoreboxtxt.setText(place.lorebox)

        binding.updateSubmitButton.setOnClickListener{
            val newName = binding.updateNametxt.text.toString()
            val newTerrain = binding.updateTerraintxt.text.toString()
            val newWeather = binding.updateWeathertxt.text.toString()
            val newLorebox = binding.updateLoreboxtxt.text.toString()
            val updatedPlace = Place(placeId, newName, newTerrain, newWeather, newLorebox)
            db.updatePlace(updatedPlace)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
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

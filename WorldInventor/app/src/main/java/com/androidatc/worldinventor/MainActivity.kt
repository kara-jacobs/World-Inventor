package com.androidatc.worldinventor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidatc.worldinventor.databinding.ActivityMainBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var myToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSharedPreferences()

//Code for all the Buttons on the Main Page
        binding.helpButton.setOnClickListener{
            var intent  = Intent(this,Help::class.java)
            startActivity(intent)
        }

        binding.prefButton.setOnClickListener{
            var intent  = Intent(this,Preferences::class.java)
            startActivity(intent)
        }

        binding.savedButton.setOnClickListener{
            var intent  = Intent(this,Saved::class.java)
            startActivity(intent)
        }

        binding.scifiButton.setOnClickListener{
            var intent  = Intent(this,Create::class.java)
            startActivity(intent)
        }

        binding.fantasyButton.setOnClickListener{
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

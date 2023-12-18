package com.androidatc.worldinventor


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.androidatc.worldinventor.databinding.ActivityPreferencesBinding
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem

class Preferences : AppCompatActivity() {
    private lateinit var binding: ActivityPreferencesBinding
    lateinit var myToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSharedPreferences()

//Code for dark mode switch
        binding.switcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switcher.text = "Disable dark mode"
                nightmodeActive(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switcher.text = "Enable dark mode"
                nightmodeActive(false)
            }
        }

        binding.backButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
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

    fun nightmodeActive(boolean: Boolean){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("nightModeActivate", boolean)
        }
        .apply()
        loadSharedPreferences()
    }

    fun loadSharedPreferences(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs",
            Context.MODE_PRIVATE)
        var switchTheme = sharedPreferences.getBoolean("nightModeActivate", false)
        if (switchTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.switcher.isChecked = true
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }

}
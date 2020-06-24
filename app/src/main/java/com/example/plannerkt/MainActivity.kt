package com.example.plannerkt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.plannerkt.autorisation.AutorisationActivity
import com.example.plannerkt.listeners.NavigationHost
import com.example.plannerkt.notes.NotesFragment

class MainActivity : AppCompatActivity(), NavigationHost {

    private val fragmentManager: FragmentManager = supportFragmentManager
    private val notesFragment: NotesFragment = NotesFragment()
    private var sharedPreferences: SharedPreferences? = null
    var authToken: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = applicationContext
            .getSharedPreferences("myPreferences", Context.MODE_PRIVATE)


        authToken = sharedPreferences!!.getBoolean("login",false)
        Log.e("authToken main", authToken.toString())


        if(!authToken) {
            startActivity(Intent(this, AutorisationActivity::class.java))
            finish()
        }


        fragmentManager.beginTransaction()
            .add(R.id.main_container,notesFragment)
            .commit()

    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }


}
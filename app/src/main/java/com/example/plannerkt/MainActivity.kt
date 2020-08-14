package com.example.plannerkt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity



import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.plannerkt.autorisation.AutorisationActivity
import com.example.plannerkt.section_chat.ChatSectionFragment
import com.example.plannerkt.listeners.NavigationHost
import com.example.plannerkt.section_notes.NotesSectionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationHost {

    private val fragmentManager: androidx.fragment.app.FragmentManager = supportFragmentManager
    private val notesSectionFragment: NotesSectionFragment = NotesSectionFragment()
    private val chatSectionFragment: ChatSectionFragment = ChatSectionFragment()
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


//        fragmentManager.beginTransaction()
//            .add(R.id.main_container,notesSectionFragment)
//            .commit()

        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                .add(R.id.main_container, notesSectionFragment, "1").commit()
        }

    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_orders -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.main_container,notesSectionFragment).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_chat -> {
                    fragmentManager.beginTransaction()
                        .replace(R.id.main_container, chatSectionFragment, "2").commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
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
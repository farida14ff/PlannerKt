package com.example.plannerkt.autorisation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.plannerkt.R
import com.example.plannerkt.autorisation.login.LoginFragment

class AutorisationActivity : AppCompatActivity() {
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val loginFragment: LoginFragment =
        LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autorisation)

        fragmentManager.beginTransaction()
            .add(R.id.autorisation_container, loginFragment)
            .commit()

    }
}
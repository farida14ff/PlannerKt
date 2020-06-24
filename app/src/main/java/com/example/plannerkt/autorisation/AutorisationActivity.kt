package com.example.plannerkt.autorisation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.plannerkt.R
import com.example.plannerkt.autorisation.login.LoginFragment
import com.example.plannerkt.autorisation.registration.RegistrationFragment
import com.example.plannerkt.notes.NotesFragment
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

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
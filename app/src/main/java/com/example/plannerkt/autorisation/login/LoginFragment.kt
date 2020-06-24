package com.example.plannerkt.autorisation.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.plannerkt.MainActivity
import com.example.plannerkt.R
import com.example.plannerkt.autorisation.registration.RegistrationFragment
import com.example.plannerkt.listeners.NavigationHost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    var sharedPreferences: SharedPreferences? = null
    var editor: Editor? = null
    var authToken: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)

        initSharedPref()
        initViews(view)
        return view
    }

    @SuppressLint("CommitPrefEdits")
    private fun initSharedPref() {
        sharedPreferences = activity?.getSharedPreferences(
            "myPreferences",
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences?.edit()
    }

    private fun initViews(view: View) {
        auth = Firebase.auth

        view.next_button.setOnClickListener {

            if (!isPasswordValid(view.password_edit_text.text)) {
                view.password_edit_text.error = getString(R.string.error_password)

            } else {

                view.password_edit_text.error = null
                val user = Firebase.auth.currentUser
                
                if (user != null) {
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                    editor?.putBoolean("login",true)?.commit()

                    Log.e("login user","Successful")

                } else {
                    editor?.putBoolean("login",false)?.commit()
                    view.password_text_input.error = getString(R.string.login_error)
                    Log.e("login user","failure")


                }
//                (activity as NavigationHost).navigateTo(, false) // Navigate to the next Fragment
            }
        }

        // Clear the error once more than 8 characters are typed.
        view.password_edit_text.setOnKeyListener { _, _, _ ->
            if (isPasswordValid(password_edit_text.text)) {
                password_text_input.error = null //Clear the error
            }
            false
        }

//        val loginBtn = view.findViewById<Button>(R.id.login)
//        loginBtn.setOnClickListener {
//
//        }

//        val registrationTV = view.findViewById<TextView>(R.id.registration)
        view.cancel_button.setOnClickListener {
            val registrationFragment: Fragment = RegistrationFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.autorisation_container, registrationFragment)
                .commit()

        }

    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }


}

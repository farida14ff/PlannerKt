package com.example.plannerkt.autorisation.registration

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.plannerkt.MainActivity
import com.example.plannerkt.R
import com.example.plannerkt.autorisation.login.LoginFragment
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_login.view.cancel_button
import kotlinx.android.synthetic.main.fragment_login.view.email_edit_text
import kotlinx.android.synthetic.main.fragment_login.view.next_button
import kotlinx.android.synthetic.main.fragment_login.view.password_edit_text
import kotlinx.android.synthetic.main.fragment_registration.view.*


class RegistrationFragment: Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view: View =  inflater.inflate(R.layout.fragment_registration, container, false)

        initViews(view)

        return view
    }


    private fun initViews(view: View) {
        auth = Firebase.auth
        var finalPass = " "



        view.cancel_button.setOnClickListener {

                val loginFragment: Fragment = LoginFragment()
                fragmentManager!!.beginTransaction()
                    .replace(R.id.autorisation_container, loginFragment)
                    .commit()
        }

//        val registrationBtn = view.findViewById<Button>(R.id.registration)
        view.next_button.setOnClickListener {
            if (!isPasswordValid(view.password_edit_text.text)) {
                view.password_edit_text.error = getString(R.string.error_password)

            } else {
                view.password_edit_text.error = null
                if(view.password_edit_text.text != view.repeat_password_edit_text.text){
                    finalPass = view.repeat_password_edit_text.text.toString()
                    registerNewUser(view.email_edit_text.text.toString(), finalPass)
                }
                else{
                    view.password_edit_text.setText("")
                    view.repeat_password_edit_text.setText("")
                    view.repeat_password_edit_text.error = "Неверный пароль"
                }
            }



//            val codeFragment: Fragment = CodeFragment()
//            fragmentManager!!.beginTransaction()
//                .replace(R.id.autorisation_container, codeFragment)
//                .commit()
        }


    }

    private fun registerNewUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task ->
                run {
                    if (task.isSuccessful) {
                        Log.e("register user","Successful")
                        startActivity(Intent(context, MainActivity::class.java))
                        activity?.finish()

                    } else {
                        Log.e( TAG,"failure", task.exception)

                    }
                }
        }


    }
    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }


}
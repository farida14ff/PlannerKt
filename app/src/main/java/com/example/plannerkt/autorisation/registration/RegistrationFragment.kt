package com.example.plannerkt.autorisation.registration

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.plannerkt.MainActivity
import com.example.plannerkt.R
import com.example.plannerkt.autorisation.login.LoginFragment
import com.example.plannerkt.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.view.cancel_button
import kotlinx.android.synthetic.main.fragment_login.view.email_edit_text
import kotlinx.android.synthetic.main.fragment_login.view.next_button
import kotlinx.android.synthetic.main.fragment_login.view.password_edit_text
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.view.*
import java.util.*


class RegistrationFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var databaseReference: DatabaseReference? = null
    var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_registration, container, false)

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
//        auth = FirebaseAuth.getInstance()

        var finalPass = " "

        view.next_button.backgroundTintList =
            ContextCompat.getColorStateList(activity!!.applicationContext, R.color.colorMain);

        view.cancel_button.setOnClickListener {

            val loginFragment: Fragment = LoginFragment()
            fragmentManager!!.beginTransaction()
                .replace(R.id.autorisation_container, loginFragment)
                .commit()
        }

        view.password_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Objects.requireNonNull(password_edit_text.text)!!.isNotEmpty()) {
                    password_text_input.error = null
                    view.next_button.isEnabled = true
                }
            }

        })
        view.next_button.setOnClickListener {
            if (!isPasswordValid(view.password_edit_text.text)) {
                view.password_edit_text.error = getString(R.string.error_password)

            } else {
                view.password_edit_text.error = null
                if (view.password_edit_text.text.toString() == view.repeat_password_edit_text.text.toString()) {
//                if(view.password_edit_text.text != view.repeat_password_edit_text.text){
                    finalPass = view.repeat_password_edit_text.text.toString()
                    registerNewUser(view.email_edit_text.text.toString(), finalPass)
                } else {
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

        databaseReference = Firebase.database.getReference("Users")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                run {
                    if (task.isSuccessful) {
                        Log.e("register user", "Successful")
                        val user = User(email, password)
                        Firebase.auth.currentUser?.uid?.let {
                            Firebase.database.getReference("Users")
                                .child(it)
                                .setValue(user)
                                .addOnCompleteListener {
                                    editor?.putBoolean("login",true)?.commit()
                                    Log.e("Registr", "login")
                                    startActivity(Intent(context, MainActivity::class.java))
                                    activity?.finish()
//                                    val token = Firebase.auth.currentUser?.getIdToken()

                                }
                        }
//
//                        editor?.putBoolean("login",true)?.commit()
//                        val userToken  = task.get
//                        editor?.putString("token",)
//                        startActivity(Intent(context, MainActivity::class.java))
//                        activity?.finish()
//
//                    } else {
//                        Log.e( TAG,"failure", task.exception)
//
                    }
                }
            }


    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= 8
    }


}
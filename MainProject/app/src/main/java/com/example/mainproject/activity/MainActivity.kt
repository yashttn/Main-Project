package com.example.mainproject.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.example.mainproject.R
import com.example.mainproject.views.forgot_password.FPDialogFragment
import com.example.mainproject.views.signin.ILoginFinished
import com.example.mainproject.views.signin.LoginFragment
import com.example.mainproject.views.signup.ISignupFinished
import com.example.mainproject.views.signup.SignupFragment

class MainActivity : AppCompatActivity(), ILoginFinished, ISignupFinished {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var loginFragment: LoginFragment
    private lateinit var signupFragment: SignupFragment
    private lateinit var fpDialog: FPDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        loginFragment = LoginFragment()
        loginFragment.loginFinished = this
        fragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_main, loginFragment)
            .commit()
    }

    override fun loginFinished() {
        signupFragment = SignupFragment()
        signupFragment.signupFinished = this
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, signupFragment)
            .commit()
    }

    override fun signupFinished() {
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_main, loginFragment)
            .commit()
    }

    override fun forgotPasswordDialog() {
        fpDialog = FPDialogFragment()
        fpDialog.show(fragmentManager, "fpDialog")
    }
}

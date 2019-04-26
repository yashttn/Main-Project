package com.example.mainproject.views.signin

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mainproject.R
import com.example.mainproject.presenters.signin.ILoginPresenter
import com.example.mainproject.presenters.signin.ILoginView
import com.example.mainproject.presenters.signin.LoginPresenterImpl
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.fragment_signin_screen.*

class LoginFragment : Fragment(), ILoginView, View.OnClickListener {

    lateinit var loginFinished: ILoginFinished
    lateinit var loginPresenter: ILoginPresenter
    lateinit var gso: GoogleSignInOptions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signin_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginPresenter = LoginPresenterImpl(this)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        btn_login.setOnClickListener(this)
        btn_forgot_password.setOnClickListener(this)
        btn_do_signup.setOnClickListener(this)
        btn_login_with_google.setOnClickListener(this)
    }

    override fun loginSuccess() {
        Toast.makeText(activity, "Log In Successful", Toast.LENGTH_SHORT).show()
    }

    override fun loginFailed(exception: String) {
        Toast.makeText(activity, exception, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_forgot_password -> {
                loginFinished.forgotPasswordDialog()
            }
            R.id.btn_login -> {
                loginPresenter.doLoginAuth(
                    et_login_email_id.text.toString(),
                    et_login_password.text.toString()
                )
            }
            R.id.btn_do_signup -> {
                loginFinished.loginFinished()
            }
            R.id.btn_login_with_google -> {
                googleLogin()
            }
        }
    }

    private fun googleLogin() {
        val googleLoginClient = GoogleSignIn.getClient(activity as AppCompatActivity, gso)
        val signInIntent = googleLoginClient.signInIntent
        startActivityForResult(signInIntent, 202)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 202) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null)
                        loginPresenter.doGoogleLoginAuth(account)
                    else
                        Log.v("yash", "failed")
                } catch (e: ApiException) {
                    Log.v("yash", "Google sign in failed")
                }
            }
        }
    }
}
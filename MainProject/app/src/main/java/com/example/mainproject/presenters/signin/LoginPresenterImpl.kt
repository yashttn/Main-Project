package com.example.mainproject.presenters.signin

import android.util.Patterns
import com.example.mainproject.models.firebase.FirebaseInteractorImpl
import com.example.mainproject.models.firebase.IFirebaseInteractor
import com.example.mainproject.views.signin.LoginFragment
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import org.apache.commons.lang3.StringUtils


class LoginPresenterImpl(loginFragment: LoginFragment) : ILoginPresenter,
    IFirebaseInteractor.ILoginAuthFirebase.ILoginAuthFirebaseFinished {

    private var loginAuthFirebase: IFirebaseInteractor.ILoginAuthFirebase = FirebaseInteractorImpl()
    private var loginView: ILoginView = loginFragment
    private lateinit var currentUser: FirebaseUser

    // ILoginPresenter methods are implemented
    // (1st method)
    override fun doLoginAuth(emailId: String, password: String) {
        if (emailPasswordValidation(emailId, password))
            loginAuthFirebase.doLoginAuth(emailId, password, this)
        else
            loginView.loginFailed("Email or password is Invalid!")
    }

    // ILoginPresenter methods are implemented
    // (2nd method)
    override fun doGoogleLoginAuth(account: GoogleSignInAccount) {
        loginAuthFirebase.doGoogleLoginAuth(account, this)
    }

    // IFirebaseInteractor methods are implemented
    // (1st method)
    override fun onLoginAuthSuccess(currentUser: FirebaseUser) {
        this.currentUser = currentUser
        loginView.loginSuccess()
    }

    // IFirebaseInteractor methods are implemented
    // (2nd method)
    override fun onLoginAuthFailure(failedException: Exception) {
        val exception = when (failedException) {
            is FirebaseAuthWeakPasswordException -> "Please Choose a Strong Password!"
            is FirebaseAuthEmailException -> "Invalid Email Address!"
            is FirebaseAuthInvalidUserException -> "Email Id not registered with us!"
            is FirebaseAuthInvalidCredentialsException -> "Email or Password is Invalid!"
            is FirebaseNetworkException -> "No Internet Connection!"
            else -> failedException.toString()
        }
        loginView.loginFailed(exception)
    }

    private fun emailPasswordValidation(emailId: String, password: String): Boolean {
        return if (emailId.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            if (password.length >= 6) {
                StringUtils.isAlphanumeric(password)
            } else {
                false
            }
        } else {
            false
        }
    }
}

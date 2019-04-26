package com.example.mainproject.presenters.signup

import android.graphics.Bitmap
import com.example.mainproject.models.firebase.FirebaseInteractorImpl
import com.example.mainproject.models.firebase.IFirebaseInteractor
import com.example.mainproject.views.signup.SignupFragment
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SignupPresenterImpl(signupFragment: SignupFragment) : ISignupPresenter,
    IFirebaseInteractor.ISignupFirebase.ISignupFirebaseFinished {

    private var signupFirebase: IFirebaseInteractor.ISignupFirebase = FirebaseInteractorImpl()
    private var signupView: ISignupView = signupFragment

    override fun createUser(firstName: String, lastName: String, emailId: String, password: String, profileImage: Bitmap?) {
        if (profileImage != null) {
            signupFirebase.doSignup(firstName, lastName, emailId, password, profileImage, this)
        }
    }

    override fun onSignUpSuccess() {
        signupView.signupSuccess()
    }

    override fun onSignupFailure(failedException: Exception) {
        val exception = when (failedException) {
            is FirebaseAuthWeakPasswordException -> "Please Choose a Strong Password!"
            is FirebaseAuthEmailException -> "Invalid Email Address!"
            is FirebaseAuthInvalidUserException -> "Email Id not registered with us!"
            is FirebaseAuthInvalidCredentialsException -> "Email or Password are Invalid!"
            is FirebaseNetworkException -> "No Internet Connection!"
            else -> failedException.toString()
        }
        signupView.signupFailed(exception)
    }

}
package com.example.mainproject.presenters.forgot_password

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.mainproject.models.firebase.FirebaseInteractorImpl
import com.example.mainproject.models.firebase.IFirebaseInteractor

class FPPresenterImpl : IFPPresenter, IFirebaseInteractor.IForgotPasswordFirebase.IForgotPasswordResetFinished {

    private var fpFirebase: IFirebaseInteractor.IForgotPasswordFirebase = FirebaseInteractorImpl()

    override fun forgotPasswordAuth(emailId: String, activity: AppCompatActivity) {
        fpFirebase.resetPassword(emailId, this)
        Toast.makeText(activity, "Check your email!", Toast.LENGTH_SHORT).show()
    }

    override fun onResetPasswordSuccess() {

    }

    override fun onResetPasswordFailure(failedException: Exception) {

    }

}
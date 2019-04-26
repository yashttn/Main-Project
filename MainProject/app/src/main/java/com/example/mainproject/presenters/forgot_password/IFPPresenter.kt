package com.example.mainproject.presenters.forgot_password

import android.support.v7.app.AppCompatActivity

interface IFPPresenter {
    fun forgotPasswordAuth(emailId: String, activity: AppCompatActivity)
}
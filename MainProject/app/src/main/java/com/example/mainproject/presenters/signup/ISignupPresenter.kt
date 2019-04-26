package com.example.mainproject.presenters.signup

import android.graphics.Bitmap

interface ISignupPresenter {
    fun createUser(firstName: String, lastName: String, emailId: String, password: String, profileImage: Bitmap?)
}
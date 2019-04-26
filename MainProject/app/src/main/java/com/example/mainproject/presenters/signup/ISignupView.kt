package com.example.mainproject.presenters.signup

interface ISignupView {
    fun signupSuccess()
    fun signupFailed(exception: String)
}
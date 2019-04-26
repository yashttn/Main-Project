package com.example.mainproject.presenters.signin

interface ILoginView {
    fun loginSuccess()
    fun loginFailed(exception: String)

}
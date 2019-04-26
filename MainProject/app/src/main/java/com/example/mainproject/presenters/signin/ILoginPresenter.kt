package com.example.mainproject.presenters.signin

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface ILoginPresenter {

    fun doLoginAuth(emailId: String, password: String)
    fun doGoogleLoginAuth(account: GoogleSignInAccount)
}
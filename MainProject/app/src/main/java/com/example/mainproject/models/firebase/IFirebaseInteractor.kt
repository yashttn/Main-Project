package com.example.mainproject.models.firebase

import android.graphics.Bitmap
import com.example.mainproject.models.pojo.Album
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface IFirebaseInteractor {

    interface ILoginAuthFirebase {
        fun doLoginAuth(emailId: String, password: String, loginAuthFirebaseFinished: ILoginAuthFirebaseFinished)
        fun doGoogleLoginAuth(account: GoogleSignInAccount, loginAuthFirebaseFinished: ILoginAuthFirebaseFinished)

        interface ILoginAuthFirebaseFinished {
            fun onLoginAuthSuccess(currentUser: FirebaseUser)
            fun onLoginAuthFailure(failedException: Exception)
        }
    }

    interface IForgotPasswordFirebase {

        fun resetPassword(emailId: String, forgotPasswordResetFinished: IForgotPasswordResetFinished)
        interface IForgotPasswordResetFinished {

            fun onResetPasswordSuccess()
            fun onResetPasswordFailure(failedException: Exception)
        }
    }

    interface ISignupFirebase {
        fun doSignup(
            firstName: String,
            lastName: String,
            emailId: String,
            password: String,
            profileImage: Bitmap?,
            signupFirebaseFinished: ISignupFirebaseFinished
        )

        interface ISignupFirebaseFinished {
            fun onSignUpSuccess()
            fun onSignupFailure(failedException: Exception)
        }
    }

    interface IGetAlbumsList {
        fun getAlbums(albumsListFinished: IAlbumsListFinished)

        interface IAlbumsListFinished {
            fun getAlbumsSuccess(albumsList: ArrayList<Album>)
            fun getAlbumsFailure()
        }
    }
}

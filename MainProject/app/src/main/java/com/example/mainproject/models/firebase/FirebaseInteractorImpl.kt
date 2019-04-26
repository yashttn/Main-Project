package com.example.mainproject.models.firebase

import android.graphics.Bitmap
import android.util.Log
import com.example.mainproject.models.pojo.Album
import com.example.mainproject.models.pojo.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class FirebaseInteractorImpl :
    IFirebaseInteractor.ILoginAuthFirebase, IFirebaseInteractor.ISignupFirebase,
    IFirebaseInteractor.IForgotPasswordFirebase, IFirebaseInteractor.IGetAlbumsList {

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseDB: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var firebaseStorageRef: StorageReference = FirebaseStorage.getInstance().reference.child("profile_pictures")

    override fun doLoginAuth(
        emailId: String,
        password: String,
        loginAuthFirebaseFinished: IFirebaseInteractor.ILoginAuthFirebase.ILoginAuthFirebaseFinished
    ) {
        firebaseAuth.signInWithEmailAndPassword(emailId, password)
            .addOnSuccessListener {
                loginAuthFirebaseFinished.onLoginAuthSuccess(it.user)
            }
            .addOnFailureListener {
                loginAuthFirebaseFinished.onLoginAuthFailure(it)
            }
    }

    override fun doGoogleLoginAuth(
        account: GoogleSignInAccount,
        loginAuthFirebaseFinished: IFirebaseInteractor.ILoginAuthFirebase.ILoginAuthFirebaseFinished
    ) {

        val usersRef = firebaseDB.getReference("users")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                loginAuthFirebaseFinished.onLoginAuthSuccess(it.user)
                val firebaseUID = it.user.uid
                val fullName = it.user.displayName?.split(" ")

                var user: User? = null
                if (fullName?.size == 1) {
                    user = User(it.user.uid, fullName[0], "", it.user.email!!)
                } else if (fullName?.size == 2) {
                    user = User(it.user.uid, fullName[0], fullName[1], it.user.email!!)
                }
                usersRef.child(firebaseUID).setValue(user)

                val profileImage = it.user.photoUrl
                if (profileImage != null) {

                    usersRef.child(firebaseUID).child("profile_image").setValue(profileImage.toString())
                    /*firebaseStorageRef.child(firebaseUID)
                        .putFile(profileImage)
                        .addOnSuccessListener { it1 ->
                            it1.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener { it2 ->
                                    usersRef.child(firebaseUID).child("profile_image").setValue(it2.toString())
                                }
                                .addOnFailureListener {
                                    Log.v("yash", "profile_pic upload failed")
                                }
                        }*/
                }
            }
            .addOnFailureListener {
                loginAuthFirebaseFinished.onLoginAuthFailure(it)
            }
    }

    override fun doSignup(
        firstName: String,
        lastName: String,
        emailId: String,
        password: String,
        profileImage: Bitmap?,
        signupFirebaseFinished: IFirebaseInteractor.ISignupFirebase.ISignupFirebaseFinished
    ) {
        val usersRef = firebaseDB.getReference("users")

        firebaseAuth.createUserWithEmailAndPassword(emailId, password)
            .addOnSuccessListener {
                val firebaseUID = it.user.uid
                val user = User(firebaseUID, firstName, lastName, emailId)
                usersRef.child(firebaseUID).setValue(user)

                if (profileImage != null) {
                    val baos = ByteArrayOutputStream()
                    profileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)

                    firebaseStorageRef.child(firebaseUID)
                        .putBytes(baos.toByteArray())
                        .addOnSuccessListener { it1 ->
                            it1.metadata!!.reference!!.downloadUrl
                                .addOnSuccessListener { it2 ->
                                    usersRef.child(firebaseUID).child("profile_image").setValue(it2.toString())
                                }
                                .addOnFailureListener {
                                    Log.v("yash", "profile_pic upload failed")
                                }
                        }
                }
            }
            .addOnFailureListener {
                signupFirebaseFinished.onSignupFailure(it)
            }
    }

    override fun resetPassword(
        emailId: String,
        forgotPasswordResetFinished: IFirebaseInteractor.IForgotPasswordFirebase.IForgotPasswordResetFinished
    ) {
        firebaseAuth.sendPasswordResetEmail(emailId)
            .addOnSuccessListener {
                forgotPasswordResetFinished.onResetPasswordSuccess()
            }
            .addOnFailureListener {
                forgotPasswordResetFinished.onResetPasswordFailure(it)
            }
    }

    override fun getAlbums(albumsListFinished: IFirebaseInteractor.IGetAlbumsList.IAlbumsListFinished) {
        firebaseDB.getReference("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (user in dataSnapshot.children) {
                        if (user.hasChild("albums")) {
                            val albumsList = ArrayList<Album>()
                            for (album in user.child("albums").children) {
                                albumsList.add(Album(
                                    album.child("album_name").value.toString(),
                                    album.child("album_size").value.toString(),
                                    album.child("album_background").value.toString()))
                            }
                            albumsListFinished.getAlbumsSuccess(albumsList)
                        } else {
                            albumsListFinished.getAlbumsFailure()
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
    }
}
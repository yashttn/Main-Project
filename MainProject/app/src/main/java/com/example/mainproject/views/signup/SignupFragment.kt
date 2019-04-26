package com.example.mainproject.views.signup

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.mainproject.R
import com.example.mainproject.presenters.signup.ISignupPresenter
import com.example.mainproject.presenters.signup.ISignupView
import com.example.mainproject.presenters.signup.SignupPresenterImpl

class SignupFragment : Fragment(), ISignupView, View.OnClickListener {

    private lateinit var firstNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var emailIdET: EditText
    private lateinit var passwordET: EditText
    private lateinit var confirmPasswordET: EditText
    private lateinit var signupBtn: Button
    private lateinit var doLoginBtn: Button
    private lateinit var signupGoogleBtn: Button
    private lateinit var profileImageIV: ImageView
    private var profileImage: Bitmap? = null

    private var firstName = ""
    private var lastName = ""
    private var emailId = ""
    private var password = ""
    private var confirmPassword = ""

    lateinit var signupFinished: ISignupFinished
    lateinit var signupPresenter: ISignupPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        starters(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        signupBtn.setOnClickListener(this)
        doLoginBtn.setOnClickListener(this)
        signupGoogleBtn.setOnClickListener(this)
        profileImageIV.setOnClickListener(this)
    }

    private fun starters(view: View) {
        firstNameET = view.findViewById(R.id.et_signup_first_name)
        lastNameET = view.findViewById(R.id.et_signup_last_name)
        emailIdET = view.findViewById(R.id.et_signup_email_id)
        passwordET = view.findViewById(R.id.et_signup_password)
        confirmPasswordET = view.findViewById(R.id.et_signup_confirm_password)
        signupBtn = view.findViewById(R.id.btn_signup)
        doLoginBtn = view.findViewById(R.id.btn_do_login)
        signupGoogleBtn = view.findViewById(R.id.btn_signup_with_google)
        profileImageIV = view.findViewById(R.id.iv_profile_image)

        signupPresenter = SignupPresenterImpl(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_signup -> {
                textExtractor()
                if (profileImage != null)
                    signupPresenter.createUser(firstName, lastName, emailId, password, profileImage)
            }
            R.id.iv_profile_image -> {
                Log.v("yash", "sdkfj")
                imageExtractor()
            }
        }
    }

    override fun signupSuccess() {
        Toast.makeText(activity, "Signup Successful", Toast.LENGTH_SHORT).show()
    }

    override fun signupFailed(exception: String) {
        Toast.makeText(activity, exception, Toast.LENGTH_SHORT).show()
    }

    private fun textExtractor() {
        firstName = firstNameET.text.toString()
        lastName = lastNameET.text.toString()
        emailId = emailIdET.text.toString()
        password = passwordET.text.toString()
        confirmPassword = confirmPasswordET.text.toString()
    }

    private fun imageExtractor() {
        val browseImageIntent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(browseImageIntent, 505)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 505) {
                if (data != null) {
                    val cropImageIntent = Intent("com.android.camera.action.CROP")
                    cropImageIntent.setDataAndType(data.data, "image/*")
                    cropImageIntent.putExtra("crop", "true")
                    cropImageIntent.putExtra("scale", "true")
                    cropImageIntent.putExtra("aspectX", 1)
                    cropImageIntent.putExtra("aspectY", 1)
                    cropImageIntent.putExtra("outputX", 150)
                    cropImageIntent.putExtra("outputY", 150)
                    cropImageIntent.putExtra("return-data", true)
                    startActivityForResult(cropImageIntent, 606)
                }
            } else if (requestCode == 606) {
                if (data != null) {
                    profileImage = data.extras?.getParcelable("data")!!
                    profileImageIV.setImageBitmap(profileImage)
                }
            }
        }
    }
}
package com.example.mainproject.views.forgot_password

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mainproject.R
import com.example.mainproject.presenters.forgot_password.FPPresenterImpl
import com.example.mainproject.presenters.forgot_password.IFPPresenter

class FPDialogFragment : DialogFragment() {

    private lateinit var emailIdET: EditText
    private lateinit var sendPasswordResetLinkBtn: Button
    private var emailId = ""

    lateinit var fpDialogPresenter: IFPPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fp_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailIdET = view.findViewById(R.id.et_fp_email_id)
        sendPasswordResetLinkBtn = view.findViewById(R.id.btn_fp_send_reset_password)

        fpDialogPresenter = FPPresenterImpl()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sendPasswordResetLinkBtn.setOnClickListener {
            emailId = emailIdET.text.toString()
            if (emailId != "") {
                Toast.makeText(activity, emailId, Toast.LENGTH_SHORT).show()
                fpDialogPresenter.forgotPasswordAuth(emailId, activity as AppCompatActivity)
                dismiss()
            } else {
                Toast.makeText(activity, "Please Enter a Valid Email Address", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
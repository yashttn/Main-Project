package com.example.mainproject.models.pojo

import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName

data class User(
    @get:Exclude val firebaseUserId: String, @get:PropertyName("first_name") val userFirstName: String,
    @get:PropertyName("last_name") val userLastName: String,
    @get:PropertyName("email_id") val userEmailId: String
)
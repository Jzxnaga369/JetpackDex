package com.rmldemo.guardsquare.uat.presentation.profile

import com.rmldemo.guardsquare.uat.domain.model.User

data class ProfileState(
    val user: User = User("", "", "", "", 0),
    val version: String = "",
    val isLoadingUser: Boolean = false,
    val isLoadingVersion: Boolean = false,
    val isLoadingUploadPhoto: Boolean = false,
    val pickImage: Boolean = false,
)

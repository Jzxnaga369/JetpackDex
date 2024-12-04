package com.rmldemo.guardsquare.uat.presentation.profile

import android.net.Uri

sealed class ProfileEvent {
    data object OnGetUser: ProfileEvent()
    data object OnGetVersion: ProfileEvent()
    data object OnLogout: ProfileEvent()
    data class OnPickImage(val isOpen: Boolean): ProfileEvent()
    data class OnChangePhoto(val uri: Uri): ProfileEvent()
}
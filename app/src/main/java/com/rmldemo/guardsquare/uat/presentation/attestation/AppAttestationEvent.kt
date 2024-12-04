package com.rmldemo.guardsquare.uat.presentation.attestation

import android.content.Context

sealed class AppAttestationEvent {
    data object CheckToken : AppAttestationEvent()
}
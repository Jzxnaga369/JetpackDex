package com.rmldemo.guardsquare.uat.presentation.transfer

import androidx.compose.ui.text.input.TextFieldValue

sealed class TransferEvent {
    data object OnTransfer: TransferEvent()
    data class OnAmountChange(val amount: TextFieldValue): TransferEvent()
    data class OnRecipientChange(val recipient: TextFieldValue): TransferEvent()
}
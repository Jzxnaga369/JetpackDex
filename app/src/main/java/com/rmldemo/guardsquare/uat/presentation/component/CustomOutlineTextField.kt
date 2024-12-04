package com.rmldemo.guardsquare.uat.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rmldemo.guardsquare.uat.ui.theme.sfUi

@Composable
fun CustomOutlineTextField(
    hint: String,
    textState: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    keyboardOptions: KeyboardOptions,
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        maxLines = 1,
        placeholder = {
            Text(
                text = hint,
                fontSize = 16.sp,
                fontFamily = sfUi,
                fontWeight = FontWeight.Thin
            )
        },
        keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(
            onGo = {
                focusManager.clearFocus()
            }
        ),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (keyboardOptions.keyboardType != KeyboardType.Password) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = sfUi,
            fontWeight = FontWeight.Medium
        ),
        value = textState,
        onValueChange = onTextChange
    )
}